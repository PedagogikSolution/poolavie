package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;

public class PlayersDaoImpl implements PlayersDao {

	private static final String CREATE_PLAYERS = "CREATE TABLE players_? LIKE players_template";
	private static final String INSERT_PLAYERS = "INSERT INTO players_? SELECT * FROM players_template";
	private static final String CREATE_PLAYERS_ARCHIVES = "CREATE TABLE players_archive_? LIKE players_template";
	private static final String GET_PLAYERS_BY_POOL_ID_AND_POSITION = "SELECT * FROM players_? WHERE team_id=? AND position=? AND club_ecole=? ORDER BY pts DESC";
	private static final String GET_PLAYERS_FOR_DRAFT = "SELECT * FROM players_?";
	private static final String GET_PLAYERS_BY_POOL_ID_FOR_ROOKIE = "SELECT * FROM players_? WHERE team_id=? AND club_ecole=? ORDER BY pts DESC";
	private static final String UPDATE_PLAYERS_AFTER_DRAFT_PICK = "UPDATE players_? SET team_id=?,contrat=?,club_ecole=?,years_1=?,years_2='JA',years_3='A',years_4='A',years_5='A' WHERE _id=?";
	private static final String GET_PLAYERS_FOR_SIGNATURE_AFTER_DRAFT = "SELECT * FROM players_? WHERE contrat=1 AND club_ecole=0 AND team_id=? AND years_2='JA'";
	private static final String UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT = "UPDATE players_? SET contrat=1,years_1=?,years_2=?,years_3=?,years_4=?,years_5=? WHERE _id=?";
	private static final String GET_FOWARD_PJ_TOP_X = "SELECT SUM(pj) AS sommePts FROM (SELECT pj FROM players_? WHERE position='attaquant' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_DEFENSE_PJ_TOP_X = "SELECT SUM(pj)AS sommePts FROM (SELECT pj FROM players_? WHERE position='defenseur' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_GOALER_PJ_TOP_X = "SELECT SUM(pj) AS sommePts FROM (SELECT pj FROM players_? WHERE position='gardien' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_FOWARD_BUT_TOP_X = "SELECT SUM(but_victoire) AS sommePts FROM (SELECT but_victoire FROM players_? WHERE position='attaquant' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_DEFENSE_BUT_TOP_X = "SELECT SUM(but_victoire)AS sommePts FROM (SELECT but_victoire FROM players_? WHERE position='defenseur' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_GOALER_BUT_TOP_X = "SELECT SUM(but_victoire) AS sommePts FROM (SELECT but_victoire FROM players_? WHERE position='gardien' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_FOWARD_PASSE_TOP_X = "SELECT SUM(aide_overtime) AS sommePts FROM (SELECT aide_overtime FROM players_? WHERE position='attaquant' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_DEFENSE_PASSE_TOP_X = "SELECT SUM(aide_overtime)AS sommePts FROM (SELECT aide_overtime FROM players_? WHERE position='defenseur' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_GOALER_PASSE_TOP_X = "SELECT SUM(aide_overtime) AS sommePts FROM (SELECT aide_overtime FROM players_? WHERE position='gardien' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_FOWARD_PTS_TOP_X = "SELECT SUM(pts) AS sommePts FROM (SELECT pts FROM players_? WHERE position='attaquant' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_DEFENSE_PTS_TOP_X = "SELECT SUM(pts)AS sommePts FROM (SELECT pts FROM players_? WHERE position='defenseur' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_GOALER_PTS_TOP_X = "SELECT SUM(pts) AS sommePts FROM (SELECT pts FROM players_? WHERE position='gardien' AND team_id=? AND club_ecole=0 ORDER BY pts DESC LIMIT ?) AS subquery";
	private static final String GET_PLAYERS_BY_ID = "SELECT * FROM players_? WHERE _id=? AND club_ecole=?";
	private static final String GET_PLAYERS_BY_ID_ALL = "SELECT * FROM players_? WHERE _id=?";
	private static final String GET_PLAYERS_BY_ID_AND_TEAM = "SELECT * FROM players_? WHERE team_id=? AND _id=?";
	private static final String UPDATE_PLAYERS = "UPDATE players_? SET team_id=?,years_2='B',years_3='B',years_4='B',years_5='B' WHERE _id=?";
	private static final String SELECT_FOR_TRADE_A = "SELECT years_1,years_2,years_3,years_4,years_5, club_ecole,position FROM players_? WHERE _id=?";
	private static final String UPDATE_PLAYERS2 = "UPDATE players_? SET team_id=?,years_2='A',years_3='A',years_4='A',years_5='A' WHERE _id=?";
	private static final String UPDATE_PLAYERS3 = "UPDATE players_? SET team_id=?,years_2='B',years_3='B',years_4='B',years_5='B' WHERE _id=?";
	private static final String UPDATE_PLAYERS4 = "UPDATE players_? SET team_id=?,years_2='C',years_3='C',years_4='C',years_5='C' WHERE _id=?";
	private static final String UPDATE_PLAYERS5 = "UPDATE players_? SET team_id=?,years_3='A',years_4='A',years_5='A' WHERE _id=?";
	private static final String UPDATE_PLAYERS6 = "UPDATE players_? SET team_id=?,years_4='A',years_5='A' WHERE _id=?";
	private static final String UPDATE_PLAYERS7 = "UPDATE players_? SET team_id=?,years_5='A' WHERE _id=?";
	private static final String UPDATE_PLAYERS8 = "UPDATE players_? SET team_id=? WHERE _id=?";
	private static final String GET_PLAYERS_FOR_RACHAT_AFTER_SEASON = "SELECT * FROM players_? WHERE years_2>? AND club_ecole=? AND team_id=?";
	private static final String GET_PLAYERS_FOR_RACHAT_DEBUT_SEASON = "SELECT * FROM players_? WHERE years_1>? AND club_ecole=? AND team_id=?";
	private static final String GET_UNIQUE_PLAYERS_BY_ID_AND_POOL = "SELECT * FROM players_? WHERE _id=?";
	private static final String DELETE_PLAYERS_FROM_TEAM = "UPDATE players_? SET team_id=null,contrat=0,"
			+ "club_ecole=0,years_1=0,years_2=0,years_3=0,years_4=0,years_5=0 WHERE _id=?";
	private static final String GET_POSITION_OF_PLAYERS = "SELECT position FROM players_? WHERE _id=?";
	private static final String UPDATE_AGE_FOR_ROOKIE = "UPDATE players_? SET age = 1 WHERE birthday<?";
	private static final String GET_ROOKIE_THAT_CAN_GO_DOWN = "SELECT * FROM players_? WHERE age=? AND club_ecole=? AND team_id=? AND (years_2='A' OR years_2='B' OR years_2>1)";
	private static final String GET_PLAYER_NAME = "SELECT nom FROM players_? WHERE _id=?";
	private static final String PUT_PLAYERS_IN_CLUB_ECOLE = "UPDATE players_? SET club_ecole=1 WHERE _ID=?";
	private static final String ARCHIVE_PLAYERS_LAST_YEAR = "INSERT INTO players_archive_? SELECT * FROM players_? WHERE team_id IS NOT NULL";
	private static final String UPDATE_PLAYERS_REMOVE_JA_X = "UPDATE players_? SET team_id=NULL WHERE (years_2='JA' OR years_2='X') AND club_ecole=0";
	private static final String UPDATE_PROJECTION = "UPDATE players_? SET projection=((pts+pts_1_years_ago+pts_2_years_ago)/(pj+pj_1_years_ago+pj_2_years_ago)*82) WHERE (pj+pj_1_years_ago+pj_2_years_ago)>81";
	private static final String UPDATE_PROJECTION_2 = "UPDATE players_? SET projection=0 WHERE (pj+pj_1_years_ago+pj_2_years_ago)<82";
	private static final String UPDATE_PROJECTION_3 = "UPDATE players_? SET projection=((pts+pts_1_years_ago+pts_2_years_ago)/(pj+pj_1_years_ago+pj_2_years_ago)*60) WHERE (pj+pj_1_years_ago+pj_2_years_ago)>81 AND position='gardien'";
	private static final String UPDATE_PROJECTION_4 = "UPDATE players_? SET projection=0 WHERE (pj+pj_1_years_ago+pj_2_years_ago)<82 AND position='gardien'";
	private static final String SET_CAN_BE_ROOKIE = "UPDATE players_? SET can_be_rookie=1 WHERE age=0";
	private static final String SET_TAKE_PROJ = "UPDATE players_? SET take_proj=1 WHERE pj<50";
	private static final String MIGRATE_PTS_FOR_YEARS_AGO_1 = "UPDATE players_? SET pj_2_years_ago=pj_1_years_ago,pts_2_years_ago=pts_1_years_ago";
	private static final String MIGRATE_PTS_FOR_YEARS_AGO_2 = "UPDATE players_? SET pj_1_years_ago=pj,pts_1_years_ago=pts ";
	private static final String MOVE_YEARS_TO_YEARS = "UPDATE players_? SET years_0=years_1,years_1=years_2,years_2=years_3,years_3=years_4,years_4=years_5,years_5='X'";
	private static final String SET_TAKE_PROJ_0 = "UPDATE players_? SET take_proj=0";
	private static final String UPDATE_YEARS_TO_YEARS = "UPDATE players_? SET years_0=0,years_1=0,years_2=0,years_3=0,years_4=0,years_5=0,contrat=0,club_ecole=0 WHERE years_1='JA' OR years_1='X'";
	private static final String SET_SALAIRE_ATTAQUANT = "UPDATE players_? AS p INNER JOIN salaire? AS s ON p.pts=s.points SET p.salaire_draft = s.salaire"
			+ " WHERE s.position=1 AND p.position='attaquant' AND take_proj=0";
	private static final String SET_SALAIRE_DEFENSEUR = "UPDATE players_? AS p INNER JOIN salaire? AS s ON p.pts=s.points SET p.salaire_draft = s.salaire"
			+ " WHERE s.position=2 AND p.position='defenseur' AND take_proj=0";
	private static final String SET_SALAIRE_GARDIEN = "UPDATE players_? AS p INNER JOIN salaire? AS s ON p.pts=s.points SET p.salaire_draft = s.salaire"
			+ " WHERE s.position=3 AND p.position='gardien' AND take_proj=0";
	private static final String SET_SALAIRE_ATTAQUANT2 = "UPDATE players_? AS p INNER JOIN salaire? AS s ON p.projection=s.points SET p.salaire_draft = s.salaire"
			+ " WHERE s.position=1 AND p.position='attaquant' AND take_proj=1 AND super_rookie=0";
	private static final String SET_SALAIRE_DEFENSEUR2 = "UPDATE players_? AS p INNER JOIN salaire? AS s ON p.projection=s.points SET p.salaire_draft = s.salaire"
			+ " WHERE s.position=2 AND p.position='defenseur' AND take_proj=1 AND super_rookie=0";
	private static final String SET_SALAIRE_GARDIEN2 = "UPDATE players_? AS p INNER JOIN salaire? AS s ON p.projection=s.points SET p.salaire_draft = s.salaire"
			+ " WHERE s.position=3 AND p.position='gardien' AND take_proj=1 AND super_rookie=0";
	private static final String RESET_CAN_BE_ROOKIE = "UPDATE players_? SET can_be_rookie=0";
	private static final String GET_NB_ATTAQUANT = "SELECT COUNT(_id) FROM players_? WHERE years_1>1 AND team_id=? AND position='attaquant' AND club_ecole=0";
	private static final String GET_NB_DEFENSEUR = "SELECT COUNT(_id) FROM players_? WHERE years_1>1 AND team_id=? AND position='defenseur' AND club_ecole=0";
	private static final String GET_NB_GARDIEN = "SELECT COUNT(_id) FROM players_? WHERE years_1>1 AND team_id=? AND position='gardien' AND club_ecole=0";
	private static final String GET_NB_ROOKIE = "SELECT COUNT(_id) FROM players_? WHERE years_1>1 AND team_id=? AND club_ecole=1";
	private static final String GET_NB_CONTRAT = "SELECT COUNT(_id) FROM players_? WHERE years_1>1 AND team_id=? AND club_ecole=0";
	private static final String GET_TOTAL_SALAIRE_NOW = "SELECT sum(years_1) FROM players_? WHERE years_1>1 AND team_id=? AND club_ecole=0";
	private static final String GET_PLAYERS_FOR_SIGNATURE_AFTER_SEASON = "SELECT * FROM players_? WHERE team_id=? AND club_ecole=0 AND (years_1='A' OR years_1='B')";
	private static final String DROP_PLAYERS_A_B = "UPDATE players_? SET contrat=0,club_ecole=0,"
			+ "years_1=0,years_2=0,years_3=0,years_4=0,years_5=0,"
			+ "team_id=null WHERE (years_1='A' OR years_1='B') AND club_ecole=0";
	private static final String GET_PLAYERS_IN_CLUB_ECOLE = "SELECT * FROM players_? WHERE club_ecole=? AND team_id=?";
	private static final String DROP_ROOKIE = "UPDATE players_? SET team_id=null,club_ecole=0,years_1=0,years_2=0,years_3=0,years_4=0,years_5=0,contrat=0 WHERE _id=?";
	private static final String MONTER_ROOKIE = "UPDATE players_? SET club_ecole=0,contrat=1,years_1=?,years_2=?,years_3=?,years_4=?,years_5=? WHERE _id=?";
	private static final String GET_ROOKIE_AB = "SELECT * FROM players_1 WHERE club_ecole=1 AND (years_1='A' OR years_1='B')";
	private static final String UPDATE_SALAIRE_ROOKIE = "UPDATE players_? SET years_1=?,years_2=?,years_3=?,years_4=?,years_5=? WHERE _id=?";
	private static final String GET_YEARS_0 = "SELECT years_0 FROM players_? WHERE _id=?";
	private static final String DROP_PLAYERS_C_D = "UPDATE players_? SET contrat=0,club_ecole=0,"
			+ "years_1=0,years_2=0,years_3=0,years_4=0,years_5=0,"
			+ "team_id=null WHERE (years_1='C' OR years_1='D')";

	private DAOFactory daoFactory;

	PlayersDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void createPlayersTable(int poolID) throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, CREATE_PLAYERS, false, poolID);
			preparedStatement.execute();
			preparedStatement.close();
			preparedStatement = initialisationRequetePreparee(connexion, INSERT_PLAYERS, false, poolID);
			preparedStatement.execute();
			preparedStatement.close();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void createPlayersArchiveTable(int poolID) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, CREATE_PLAYERS_ARCHIVES, false, poolID);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void cronJobGetPlayersbyPoolIdAndPosition(int poolId, int numberOfTeam, String positionString, int isRookie,
			String fromTag) {

		for (int i = 1; i < (numberOfTeam + 1); i++) {
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool").param("isRookie", String.valueOf(isRookie))
					.param("positionString", positionString).param("counter", String.valueOf(i))
					.param("poolID", String.valueOf(poolId)).param("fromTag", fromTag)

			);

		}

	}

	@Override
	public void cronJobPlayersAvailableForDraft(int poolId) {

		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool").param("poolID", String.valueOf(poolId))
				.param("fromTag", "2"));

	}

	@Override
	public void persistPlayerPick(int playerId, int salaireId, int poolId, int teamId, int clubEcoleId) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int contrat = 1;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_AFTER_DRAFT_PICK, false, poolId,
					teamId, contrat, clubEcoleId, salaireId, playerId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void getPlayersForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter, String positionString,
			int isRookie) {
		String datastoreId = poolID + "_" + counter;
		int poolId = Integer.parseInt(poolID);

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Long> players_id = new ArrayList<Long>();
		List<Long> team_id = new ArrayList<Long>();
		List<String> nom = new ArrayList<String>();
		List<String> teamOfPlayer = new ArrayList<String>();
		List<Long> pj = new ArrayList<Long>();
		List<Long> but_victoire = new ArrayList<Long>();
		List<Long> aide_overtime = new ArrayList<Long>();
		List<Long> blanchissage = new ArrayList<Long>();
		List<Long> pts = new ArrayList<Long>();
		List<Long> projection = new ArrayList<Long>();
		List<String> position = new ArrayList<String>();
		List<Date> birthday = new ArrayList<Date>();
		List<Long> can_be_rookie = new ArrayList<Long>();
		List<Long> take_proj = new ArrayList<Long>();
		List<Long> salaire_draft = new ArrayList<Long>();
		List<Long> contrat = new ArrayList<Long>();
		List<Long> club_ecole = new ArrayList<Long>();
		List<String> years_1 = new ArrayList<String>();
		List<String> years_2 = new ArrayList<String>();
		List<String> years_3 = new ArrayList<String>();
		List<String> years_4 = new ArrayList<String>();
		List<String> years_5 = new ArrayList<String>();

		try {

			connexion = daoFactory.getConnection();

			if (isRookie == 0) {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_POOL_ID_AND_POSITION, false,
						poolId, counter, positionString, isRookie);
			} else {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_POOL_ID_FOR_ROOKIE, false,
						poolId, counter, isRookie);
			}

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int m_players_id = rs.getInt("_id");
				players_id.add(Long.valueOf(m_players_id));

				int m_team_id = rs.getInt("team_id");
				team_id.add(Long.valueOf(m_team_id));

				String m_nom = rs.getString("nom");
				nom.add(m_nom);

				String m_teamOfPlayer = rs.getString("team");
				teamOfPlayer.add(m_teamOfPlayer);

				int m_pj = rs.getInt("pj");
				pj.add(Long.valueOf(m_pj));

				int m_but_victoire = rs.getInt("but_victoire");
				but_victoire.add(Long.valueOf(m_but_victoire));

				int m_aide_overtime = rs.getInt("aide_overtime");
				aide_overtime.add(Long.valueOf(m_aide_overtime));

				int m_blanchissage = rs.getInt("blanchissage");
				blanchissage.add(Long.valueOf(m_blanchissage));

				int m_pts = rs.getInt("pts");
				pts.add(Long.valueOf(m_pts));

				int m_projection = rs.getInt("projection");
				projection.add(Long.valueOf(m_projection));

				String m_position = rs.getString("position");
				position.add(m_position);

				Date m_birthday = rs.getDate("birthday");
				birthday.add(m_birthday);

				int m_can_be_rookie = rs.getInt("can_be_rookie");
				can_be_rookie.add(Long.valueOf(m_can_be_rookie));

				int m_take_proj = rs.getInt("take_proj");
				take_proj.add(Long.valueOf(m_take_proj));

				int m_salaire_draft = rs.getInt("salaire_draft");
				salaire_draft.add(Long.valueOf(m_salaire_draft));

				int m_contrat = rs.getInt("contrat");
				contrat.add(Long.valueOf(m_contrat));

				int m_club_ecole = rs.getInt("club_ecole");
				club_ecole.add(Long.valueOf(m_club_ecole));

				String m_years_1 = rs.getString("years_1");
				years_1.add(m_years_1);

				String m_years_2 = rs.getString("years_2");
				years_2.add(m_years_2);

				String m_years_3 = rs.getString("years_3");
				years_3.add(m_years_3);

				String m_years_4 = rs.getString("years_4");
				years_4.add(m_years_4);

				String m_years_5 = rs.getString("years_5");
				years_5.add(m_years_5);

			}

		} catch (SQLException e) {

			throw new DAOException(e);

		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);

		}

		switch (isRookie) {
		case 0:

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			switch (positionString) {

			case "attaquant":
				Attaquant mBeanA = new Attaquant();

				mBeanA.setPlayers_id(players_id);
				mBeanA.setAide_overtime(aide_overtime);
				mBeanA.setBlanchissage(blanchissage);
				mBeanA.setBut_victoire(but_victoire);
				mBeanA.setCan_be_rookie(can_be_rookie);
				mBeanA.setClub_ecole(club_ecole);
				mBeanA.setContrat(contrat);
				mBeanA.setNom(nom);
				mBeanA.setPj(pj);
				mBeanA.setPoolTeamId(datastoreId);
				mBeanA.setPosition(position);
				mBeanA.setPts(pts);
				mBeanA.setSalaire_draft(salaire_draft);
				mBeanA.setTeam_id(team_id);
				mBeanA.setTeamOfPlayer(teamOfPlayer);
				mBeanA.setYears_1(years_1);
				mBeanA.setYears_2(years_2);
				mBeanA.setYears_3(years_3);
				mBeanA.setYears_4(years_4);
				mBeanA.setYears_5(years_5);

				// on cr�e le beans avec le processus JPA qui va cr�er le datastore en m�me
				// temps

				Entity mEntity = mBeanA.mapBeanToEntityForDatastore(mBeanA, mBeanA.getPoolTeamId());

				datastore.put(mEntity);

				break;

			case "defenseur":
				Defenseur mBeanD = new Defenseur();

				mBeanD.setPlayers_id(players_id);
				mBeanD.setAide_overtime(aide_overtime);
				mBeanD.setBlanchissage(blanchissage);
				mBeanD.setBut_victoire(but_victoire);
				mBeanD.setCan_be_rookie(can_be_rookie);
				mBeanD.setClub_ecole(club_ecole);
				mBeanD.setContrat(contrat);
				mBeanD.setNom(nom);
				mBeanD.setPj(pj);
				mBeanD.setPoolTeamId(datastoreId);
				mBeanD.setPosition(position);
				mBeanD.setProjection(projection);
				mBeanD.setPts(pts);
				mBeanD.setSalaire_draft(salaire_draft);
				mBeanD.setTeam_id(team_id);
				mBeanD.setTeamOfPlayer(teamOfPlayer);
				mBeanD.setYears_1(years_1);
				mBeanD.setYears_2(years_2);
				mBeanD.setYears_3(years_3);
				mBeanD.setYears_4(years_4);
				mBeanD.setYears_5(years_5);

				// on cr�e le beans avec le processus JPA qui va cr�er le datastore en m�me
				// temps

				datastore = DatastoreServiceFactory.getDatastoreService();

				mEntity = mBeanD.mapBeanToEntityForDatastore(mBeanD, mBeanD.getPoolTeamId());

				datastore.put(mEntity);

				break;
			case "gardien":
				Gardien mBeanG = new Gardien();

				mBeanG.setPlayers_id(players_id);
				mBeanG.setAide_overtime(aide_overtime);
				mBeanG.setBlanchissage(blanchissage);
				mBeanG.setBut_victoire(but_victoire);
				mBeanG.setCan_be_rookie(can_be_rookie);
				mBeanG.setClub_ecole(club_ecole);
				mBeanG.setContrat(contrat);
				mBeanG.setNom(nom);
				mBeanG.setPj(pj);
				mBeanG.setPoolTeamId(datastoreId);
				mBeanG.setPosition(position);
				mBeanG.setProjection(projection);
				mBeanG.setPts(pts);
				mBeanG.setSalaire_draft(salaire_draft);
				mBeanG.setTeam_id(team_id);
				mBeanG.setTeamOfPlayer(teamOfPlayer);
				mBeanG.setYears_1(years_1);
				mBeanG.setYears_2(years_2);
				mBeanG.setYears_3(years_3);
				mBeanG.setYears_4(years_4);
				mBeanG.setYears_5(years_5);

				// on cr�e le beans avec le processus JPA qui va cr�er le datastore en m�me
				// temps

				datastore = DatastoreServiceFactory.getDatastoreService();

				mEntity = mBeanG.mapBeanToEntityForDatastore(mBeanG, mBeanG.getPoolTeamId());

				datastore.put(mEntity);

				break;
			}
			break;

		case 1:
			Recrue mBeanR = new Recrue();

			mBeanR.setPlayers_id(players_id);
			mBeanR.setAide_overtime(aide_overtime);
			mBeanR.setBlanchissage(blanchissage);
			mBeanR.setBut_victoire(but_victoire);
			mBeanR.setCan_be_rookie(can_be_rookie);
			mBeanR.setClub_ecole(club_ecole);
			mBeanR.setContrat(contrat);
			mBeanR.setNom(nom);
			mBeanR.setPj(pj);
			mBeanR.setPoolTeamId(datastoreId);
			mBeanR.setPosition(position);
			mBeanR.setProjection(projection);
			mBeanR.setPts(pts);
			mBeanR.setSalaire_draft(salaire_draft);
			mBeanR.setTeam_id(team_id);
			mBeanR.setTeamOfPlayer(teamOfPlayer);
			mBeanR.setYears_1(years_1);
			mBeanR.setYears_2(years_2);
			mBeanR.setYears_3(years_3);
			mBeanR.setYears_4(years_4);
			mBeanR.setYears_5(years_5);

			// on cr�e le beans avec le processus JPA qui va cr�er le datastore en m�me
			// temps

			datastore = DatastoreServiceFactory.getDatastoreService();

			Entity mEntity = mBeanR.mapBeanToEntityForDatastore(mBeanR, mBeanR.getPoolTeamId());

			datastore.put(mEntity);

			break;
		}

	}

	@Override
	public void getPlayersForDatastoreFromPoolId(String poolID) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int _id;
		int players_id = 0;
		int team_id = 0;
		String nom = null;
		String teamOfPlayer = null;
		int pj = 0;
		int but_victoire = 0;
		int aide_overtime = 0;
		int blanchissage = 0;
		int pts = 0;
		int projection = 0;
		String position = null;
		Date birthday = null;
		int can_be_rookie = 0;
		int take_proj = 0;
		int salaire_draft = 0;
		int contrat = 0;
		int club_ecole = 0;
		String years_1 = null;
		String years_2 = null;
		String years_3 = null;
		String years_4 = null;
		String years_5 = null;
		int age = 0;

		try {

			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_DRAFT, false,
					Integer.parseInt(poolID));
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				_id = rs.getInt("_id");
				players_id = rs.getInt("_id");
				team_id = rs.getInt("team_id");
				nom = rs.getString("nom");
				teamOfPlayer = rs.getString("team");
				pj = rs.getInt("pj");
				but_victoire = rs.getInt("but_victoire");
				aide_overtime = rs.getInt("aide_overtime");
				blanchissage = rs.getInt("blanchissage");
				pts = rs.getInt("pts");
				projection = rs.getInt("projection");
				position = rs.getString("position");
				birthday = rs.getDate("birthday");
				can_be_rookie = rs.getInt("can_be_rookie");
				take_proj = rs.getInt("take_proj");
				salaire_draft = rs.getInt("salaire_draft");
				contrat = rs.getInt("contrat");
				club_ecole = rs.getInt("club_ecole");
				years_1 = rs.getString("years_1");
				years_2 = rs.getString("years_2");
				years_3 = rs.getString("years_3");
				years_4 = rs.getString("years_4");
				years_5 = rs.getString("years_5");

				Players mBean = new Players();

				mBean.set_id(_id);
				mBean.setPlayers_id(players_id);
				mBean.setAge(age);
				mBean.setAide_overtime(aide_overtime);
				mBean.setBirthday(birthday);
				mBean.setBlanchissage(blanchissage);
				mBean.setBut_victoire(but_victoire);
				mBean.setCan_be_rookie(can_be_rookie);
				mBean.setClub_ecole(club_ecole);
				mBean.setContrat(contrat);
				mBean.setNom(nom);
				mBean.setPj(pj);
				mBean.setPosition(position);
				mBean.setProjection(projection);
				mBean.setPts(pts);
				mBean.setSalaire_draft(salaire_draft);
				mBean.setTake_proj(take_proj);
				mBean.setTeam_id(team_id);
				mBean.setTeamOfPlayer(teamOfPlayer);
				mBean.setYears_1(years_1);
				mBean.setYears_2(years_2);
				mBean.setYears_3(years_3);
				mBean.setYears_4(years_4);
				mBean.setYears_5(years_5);

				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool")
						.param("players_id", String.valueOf(players_id)).param("nom", nom)
						.param("teamOfPlayer", teamOfPlayer).param("pj", String.valueOf(pj))
						.param("but_victoire", String.valueOf(but_victoire))
						.param("aide_overtime", String.valueOf(aide_overtime))
						.param("blanchissage", String.valueOf(blanchissage)).param("pts", String.valueOf(pts))
						.param("projection", String.valueOf(projection)).param("position", position)
						.param("can_be_rookie", String.valueOf(can_be_rookie))
						.param("salaire_draft", String.valueOf(salaire_draft)).param("poolID", String.valueOf(poolID))
						.param("fromTag", "8").param("contrat", String.valueOf(contrat))
						.param("club_ecole", String.valueOf(club_ecole))

				);
			}

		} catch (SQLException e) {

			throw new DAOException(e);

		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);

		}

	}

	@Override
	public void getPlayersThatCanBeSign(int teamId, int poolId, HttpServletRequest req, SalaireDao salaireDao)
			throws DAOException {

		NonSessionPlayers mBeanPlayers = new NonSessionPlayers();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Integer> players_id = new ArrayList<Integer>();
		List<Integer> team_id = new ArrayList<Integer>();
		List<String> nom = new ArrayList<String>();
		List<String> teamOfPlayer = new ArrayList<String>();
		List<Integer> pj = new ArrayList<Integer>();
		List<Integer> but_victoire = new ArrayList<Integer>();
		List<Integer> aide_overtime = new ArrayList<Integer>();
		List<Integer> blanchissage = new ArrayList<Integer>();
		List<Integer> pts = new ArrayList<Integer>();
		List<Integer> projection = new ArrayList<Integer>();
		List<String> position = new ArrayList<String>();
		List<Date> birthday = new ArrayList<Date>();
		List<Integer> can_be_rookie = new ArrayList<Integer>();
		List<Integer> take_proj = new ArrayList<Integer>();
		List<Integer> salaire_draft = new ArrayList<Integer>();
		List<Integer> contrat = new ArrayList<Integer>();
		List<Integer> club_ecole = new ArrayList<Integer>();
		List<Integer> years_0 = new ArrayList<Integer>();
		List<String> years_1 = new ArrayList<String>();
		List<String> years_2 = new ArrayList<String>();
		List<String> years_3 = new ArrayList<String>();
		List<String> years_4 = new ArrayList<String>();
		List<String> years_5 = new ArrayList<String>();
		List<Integer> team_was_update = new ArrayList<Integer>();
		List<Integer> age = new ArrayList<Integer>();
		List<Integer> hier = new ArrayList<Integer>();
		List<Integer> semaine = new ArrayList<Integer>();
		List<Integer> mois = new ArrayList<Integer>();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();

		try {
			connexion = daoFactory.getConnection();
			if (cycleAnnuel == 4) {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_SIGNATURE_AFTER_DRAFT,
						false, poolId, teamId);
			} else if (cycleAnnuel == 10) {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_SIGNATURE_AFTER_SEASON,
						false, poolId, teamId);
			}
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int m_players_id = rs.getInt("_id");
				players_id.add(m_players_id);

				int m_team_id = rs.getInt("team_id");
				team_id.add(m_team_id);

				String m_nom = rs.getString("nom");
				nom.add(m_nom);

				String m_teamOfPlayer = rs.getString("team");
				teamOfPlayer.add(m_teamOfPlayer);

				int m_pj = rs.getInt("pj");
				pj.add(m_pj);

				int m_but_victoire = rs.getInt("but_victoire");
				but_victoire.add(m_but_victoire);

				int m_aide_overtime = rs.getInt("aide_overtime");
				aide_overtime.add(m_aide_overtime);

				int m_blanchissage = rs.getInt("blanchissage");
				blanchissage.add(m_blanchissage);

				int m_pts = rs.getInt("pts");
				pts.add(m_pts);

				int m_projection = rs.getInt("projection");
				projection.add(m_projection);

				String m_position = rs.getString("position");
				position.add(m_position);

				Date m_birthday = rs.getDate("birthday");
				birthday.add(m_birthday);

				int m_can_be_rookie = rs.getInt("can_be_rookie");
				can_be_rookie.add(m_can_be_rookie);

				int m_take_proj = rs.getInt("take_proj");
				take_proj.add(m_take_proj);

				int m_contrat = rs.getInt("contrat");
				contrat.add(m_contrat);

				int m_club_ecole = rs.getInt("club_ecole");
				club_ecole.add(m_club_ecole);

				int m_years_0 = rs.getInt("years_0");
				years_0.add(m_years_0);

				String m_years_1 = rs.getString("years_1");
				years_1.add(m_years_1);

				String m_years_2 = rs.getString("years_2");
				years_2.add(m_years_2);

				String m_years_3 = rs.getString("years_3");
				years_3.add(m_years_3);

				String m_years_4 = rs.getString("years_4");
				years_4.add(m_years_4);

				String m_years_5 = rs.getString("years_5");
				years_5.add(m_years_5);

				int m_team_was_update = rs.getInt("team_was_update");
				team_was_update.add(m_team_was_update);

				int m_age = rs.getInt("age");
				age.add(m_age);

				int m_hier = rs.getInt("hier");
				hier.add(m_hier);

				int m_semaine = rs.getInt("semaine");
				semaine.add(m_semaine);

				int m_mois = rs.getInt("mois");
				mois.add(m_mois);

				// On distingue le salaire possible selon type A ou B
				if (cycleAnnuel == 4) {

					int m_salaire_draft = rs.getInt("salaire_draft");
					salaire_draft.add(m_salaire_draft);

				} else if (cycleAnnuel == 10) {

					int salaire = 0;
					int salaireB = 0;

					if (years_1.equals("A")) {
						if (m_take_proj == 0) {

							salaire = salaireDao.getSalaireTable(poolId, m_position, m_pts);

						} else {
							salaire = salaireDao.getSalaireTable(poolId, m_position, m_projection);
						}

					} else {

						if (m_take_proj == 0) {
							salaire = salaireDao.getSalaireTable(poolId, m_position, m_pts);
							salaireB = m_years_0 + 1000000;

						} else {
							salaire = salaireDao.getSalaireTable(poolId, m_position, m_projection);
							salaireB = m_years_0 + 1000000;

						}

						if (salaireB < salaire + 1000000) {
							salaire = salaireB;
						} else {
							salaire = salaire + 1000000;
						}

					}

					salaire_draft.add(salaire);

				}

			}
			mBeanPlayers.setPlayers_id(players_id);
			mBeanPlayers.setAge(age);
			mBeanPlayers.setAide_overtime(aide_overtime);
			mBeanPlayers.setBlanchissage(blanchissage);
			mBeanPlayers.setBut_victoire(but_victoire);
			mBeanPlayers.setCan_be_rookie(can_be_rookie);
			mBeanPlayers.setClub_ecole(club_ecole);
			mBeanPlayers.setContrat(contrat);

			mBeanPlayers.setHier(hier);
			mBeanPlayers.setMois(mois);
			mBeanPlayers.setNom(nom);
			mBeanPlayers.setPj(pj);
			mBeanPlayers.setPosition(position);
			mBeanPlayers.setProjection(projection);
			mBeanPlayers.setPts(pts);

			mBeanPlayers.setSalaire_draft(salaire_draft);
			mBeanPlayers.setSemaine(semaine);
			mBeanPlayers.setTake_proj(take_proj);
			mBeanPlayers.setTeam_id(team_id);
			mBeanPlayers.setTeam_was_update(team_was_update);
			mBeanPlayers.setTeamOfPlayer(teamOfPlayer);

			mBeanPlayers.setYears_1(years_1);
			mBeanPlayers.setYears_2(years_2);
			mBeanPlayers.setYears_3(years_3);
			mBeanPlayers.setYears_4(years_4);
			mBeanPlayers.setYears_5(years_5);

			req.getSession().setAttribute("NonSessionPlayers", mBeanPlayers);
		} catch (

		SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	@Override
	public void signPlayerAfterDraft(int teamId, int poolId, String draft_player_id, String salaire, int numberOfYear)
			throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			switch (numberOfYear) {
			case 1:
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT,
						false, poolId, salaire, "JA", "X", "X", "X", draft_player_id);
				break;
			case 2:
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT,
						false, poolId, salaire, salaire, "X", "X", "X", draft_player_id);
				break;
			case 3:
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT,
						false, poolId, salaire, salaire, salaire, "X", "X", draft_player_id);
				break;
			case 4:
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT,
						false, poolId, salaire, salaire, salaire, salaire, "X", draft_player_id);
				break;
			case 5:
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT,
						false, poolId, salaire, salaire, salaire, salaire, salaire, draft_player_id);
				break;

			}
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void persistPlayerPickRookie(int playersId, int salaireId, int poolId, int teamId, int clubEcoleId)
			throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int contrat = 1;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_AFTER_DRAFT_PICK, false, poolId,
					teamId, contrat, clubEcoleId, salaireId, playersId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public int getPj(int teamId, int poolId) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		int pts = 0;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_PJ_TOP_X, false, poolId, teamId, 8);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_PJ_TOP_X, false, poolId, teamId,
					5);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_PJ_TOP_X, false, poolId, teamId, 2);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		return pts;
	}

	@Override
	public int getBut(int teamId, int poolId) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		int pts = 0;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_BUT_TOP_X, false, poolId, teamId,
					8);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_BUT_TOP_X, false, poolId, teamId,
					5);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_BUT_TOP_X, false, poolId, teamId,
					2);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		return pts;
	}

	@Override
	public int getPasse(int teamId, int poolId) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		int pts = 0;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_PASSE_TOP_X, false, poolId, teamId,
					8);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_PASSE_TOP_X, false, poolId, teamId,
					5);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_PASSE_TOP_X, false, poolId, teamId,
					2);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		return pts;
	}

	@Override
	public int getPts(int teamId, int poolId) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		int pts = 0;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_PTS_TOP_X, false, poolId, teamId,
					8);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_PTS_TOP_X, false, poolId, teamId,
					5);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_PTS_TOP_X, false, poolId, teamId,
					2);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String pts_temp = rs.getString("sommePts");
				int ptsTemp = Integer.parseInt(pts_temp);
				pts = pts + ptsTemp;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		return pts;

	}

	@Override
	public TradeBeanTemp getPlayersById(String poolID, int toInt, int club_ecole) throws DAOException {
		Connection connexion = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		TradeBeanTemp mBean = new TradeBeanTemp();
		try {
			connexion = daoFactory.getConnection();
			if (club_ecole == 2) {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_ID_ALL, false,
						Integer.parseInt(poolID), toInt);
			} else {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_ID, false,
						Integer.parseInt(poolID), toInt, club_ecole);
			}
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int salaire_joueur_temp = rs.getInt("years_1");
				mBean.setTotal_salaire_team_making_offer(salaire_joueur_temp);
				mBean.setNomMakingOfferString(rs.getString("nom"));
				mBean.setPositionDuJoueurTrade(rs.getString("position"));
				mBean.setClubEcole(rs.getInt("club_ecole"));
			}

			return mBean;

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

	}

	@Override
	public Boolean checkIfPlayersStillInTeam(int poolId, int teamId, int playerId) throws DAOException {
		Connection connexion = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_ID_AND_TEAM, false, poolId,
					teamId, playerId);

			rs = preparedStatement.executeQuery();
			if (rs.first()) {
				return true;
			}

			return false;

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

	}

	@SuppressWarnings("resource")
	@Override
	public void makeTrade(Pool mBeanPool, int teamId1, int teamId2, int playerId2) throws DAOException {

		String contrat = "null";
		String years_3 = "null", years_4 = "null", years_5 = "null";
		int salaire = 0;
		int club_ecole = 0;
		String position = null;
		String poolID = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolID);

		Connection connexion = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, SELECT_FOR_TRADE_A, false, poolId, playerId2);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				club_ecole = rs.getInt("club_ecole");
				String salaireString = rs.getString("years_1");
				salaire = Integer.parseInt(salaireString);
				contrat = rs.getString("years_2");
				years_3 = rs.getString("years_3");
				years_4 = rs.getString("years_4");
				years_5 = rs.getString("years_5");
				position = rs.getString("position");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();

			if (contrat.equalsIgnoreCase("JA")) {
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS, false, poolId, teamId2,
						playerId2);
			}

			else if (contrat.equalsIgnoreCase("X")) {
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS2, false, poolId, teamId2,
						playerId2);
			}

			else if (contrat.equalsIgnoreCase("B")) {
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS3, false, poolId, teamId2,
						playerId2);
			} else if (contrat.equalsIgnoreCase("C")) {
				preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS4, false, poolId, teamId2,
						playerId2);
			} else {

				if (years_3.equalsIgnoreCase("X")) {
					preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS5, false, poolId,
							teamId2, playerId2);
				} else if (years_4.equalsIgnoreCase("X")) {
					preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS6, false, poolId,
							teamId2, playerId2);
				} else if (years_5.equalsIgnoreCase("X")) {
					preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS7, false, poolId,
							teamId2, playerId2);
				}

				else {
					preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS8, false, poolId,
							teamId2, playerId2);

				}

			}

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		String nomEquipeA = poolID + "_" + teamId1;
		String nomEquipeB = poolID + "_" + teamId2;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKeyEquipeA = KeyFactory.createKey("Equipe", nomEquipeA);
		Key mKeyEquipeB = KeyFactory.createKey("Equipe", nomEquipeB);

		if (club_ecole == 0 && position.equalsIgnoreCase("attaquant")) {

			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeA);

				mEntityEquipe.setProperty("total_salaire_now",
						(((Long) mEntityEquipe.getProperty("total_salaire_now")) - salaire));
				mEntityEquipe.setProperty("budget_restant",
						(((Long) mEntityEquipe.getProperty("budget_restant")) + salaire));
				mEntityEquipe.setProperty("nb_equipe", (((Long) mEntityEquipe.getProperty("nb_equipe")) - 1));
				if (contrat.equalsIgnoreCase("JA") || contrat.equalsIgnoreCase("X") || contrat.equalsIgnoreCase("B")
						|| contrat.equalsIgnoreCase("A")) {

				} else {
					mEntityEquipe.setProperty("nb_contrat", (((Long) mEntityEquipe.getProperty("nb_contrat")) - 1));
				}
				mEntityEquipe.setProperty("nb_attaquant", (((Long) mEntityEquipe.getProperty("nb_attaquant")) - 1));
				mEntityEquipe.setProperty("manquant_att", (((Long) mEntityEquipe.getProperty("manquant_att")) + 1));

				datastore.put(mEntityEquipe);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeB);

				mEntityEquipe.setProperty("total_salaire_now",
						(((Long) mEntityEquipe.getProperty("total_salaire_now")) + salaire));
				mEntityEquipe.setProperty("budget_restant",
						(((Long) mEntityEquipe.getProperty("budget_restant")) - salaire));

				mEntityEquipe.setProperty("nb_equipe", (((Long) mEntityEquipe.getProperty("nb_equipe")) + 1));
				if (contrat.equalsIgnoreCase("JA") || contrat.equalsIgnoreCase("X") || contrat.equalsIgnoreCase("B")
						|| contrat.equalsIgnoreCase("A")) {

				} else {
					mEntityEquipe.setProperty("nb_contrat", (((Long) mEntityEquipe.getProperty("nb_contrat")) + 1));
				}
				mEntityEquipe.setProperty("nb_attaquant", (((Long) mEntityEquipe.getProperty("nb_attaquant")) + 1));
				mEntityEquipe.setProperty("manquant_att", (((Long) mEntityEquipe.getProperty("manquant_att")) - 1));

				datastore.put(mEntityEquipe);

			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

		} else if (club_ecole == 0 && position.equalsIgnoreCase("defenseur")) {
			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeA);

				mEntityEquipe.setProperty("total_salaire_now",
						(((Long) mEntityEquipe.getProperty("total_salaire_now")) - salaire));
				mEntityEquipe.setProperty("budget_restant",
						(((Long) mEntityEquipe.getProperty("budget_restant")) + salaire));
				mEntityEquipe.setProperty("nb_equipe", (((Long) mEntityEquipe.getProperty("nb_equipe")) - 1));
				if (contrat.equalsIgnoreCase("JA") || contrat.equalsIgnoreCase("X") || contrat.equalsIgnoreCase("B")
						|| contrat.equalsIgnoreCase("A")) {

				} else {
					mEntityEquipe.setProperty("nb_contrat", (((Long) mEntityEquipe.getProperty("nb_contrat")) - 1));
				}
				mEntityEquipe.setProperty("nb_defenseur", (((Long) mEntityEquipe.getProperty("nb_defenseur")) - 1));
				mEntityEquipe.setProperty("manquant_def", (((Long) mEntityEquipe.getProperty("manquant_def")) + 1));

				datastore.put(mEntityEquipe);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeB);

				mEntityEquipe.setProperty("total_salaire_now",
						(((Long) mEntityEquipe.getProperty("total_salaire_now")) + salaire));
				mEntityEquipe.setProperty("budget_restant",
						(((Long) mEntityEquipe.getProperty("budget_restant")) - salaire));

				mEntityEquipe.setProperty("nb_equipe", (((Long) mEntityEquipe.getProperty("nb_equipe")) + 1));
				if (contrat.equalsIgnoreCase("JA") || contrat.equalsIgnoreCase("X") || contrat.equalsIgnoreCase("B")
						|| contrat.equalsIgnoreCase("A")) {

				} else {
					mEntityEquipe.setProperty("nb_contrat", (((Long) mEntityEquipe.getProperty("nb_contrat")) + 1));
				}
				mEntityEquipe.setProperty("nb_defenseur", (((Long) mEntityEquipe.getProperty("nb_defenseur")) + 1));
				mEntityEquipe.setProperty("manquant_def", (((Long) mEntityEquipe.getProperty("manquant_def")) - 1));

				datastore.put(mEntityEquipe);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (club_ecole == 0 && position.equalsIgnoreCase("gardien")) {
			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeA);

				mEntityEquipe.setProperty("total_salaire_now",
						(((Long) mEntityEquipe.getProperty("total_salaire_now")) - salaire));
				mEntityEquipe.setProperty("budget_restant",
						(((Long) mEntityEquipe.getProperty("budget_restant")) + salaire));
				mEntityEquipe.setProperty("nb_equipe", (((Long) mEntityEquipe.getProperty("nb_equipe")) - 1));
				if (contrat.equalsIgnoreCase("JA") || contrat.equalsIgnoreCase("X") || contrat.equalsIgnoreCase("B")
						|| contrat.equalsIgnoreCase("A")) {

				} else {
					mEntityEquipe.setProperty("nb_contrat", (((Long) mEntityEquipe.getProperty("nb_contrat")) - 1));
				}
				mEntityEquipe.setProperty("nb_gardien", (((Long) mEntityEquipe.getProperty("nb_gardien")) - 1));
				mEntityEquipe.setProperty("manquant_gardien",
						(((Long) mEntityEquipe.getProperty("manquant_gardien")) + 1));
				datastore.put(mEntityEquipe);

			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeB);

				mEntityEquipe.setProperty("total_salaire_now",
						(((Long) mEntityEquipe.getProperty("total_salaire_now")) + salaire));
				mEntityEquipe.setProperty("budget_restant",
						(((Long) mEntityEquipe.getProperty("budget_restant")) - salaire));

				mEntityEquipe.setProperty("nb_equipe", (((Long) mEntityEquipe.getProperty("nb_equipe")) + 1));
				if (contrat.equalsIgnoreCase("JA") || contrat.equalsIgnoreCase("X")) {

				} else {
					mEntityEquipe.setProperty("nb_contrat", (((Long) mEntityEquipe.getProperty("nb_contrat")) + 1));
				}
				mEntityEquipe.setProperty("nb_gardien", (((Long) mEntityEquipe.getProperty("nb_gardien")) + 1));
				mEntityEquipe.setProperty("manquant_gardien",
						(((Long) mEntityEquipe.getProperty("manquant_gardien")) - 1));
				datastore.put(mEntityEquipe);

			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeA);

				mEntityEquipe.setProperty("nb_rookie", (((Long) mEntityEquipe.getProperty("nb_rookie")) - 1));
				mEntityEquipe.setProperty("manquant_recrue",
						(((Long) mEntityEquipe.getProperty("manquant_recrue")) + 1));
				datastore.put(mEntityEquipe);

			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

			try {
				Entity mEntityEquipe = datastore.get(mKeyEquipeB);

				mEntityEquipe.setProperty("nb_rookie", (((Long) mEntityEquipe.getProperty("nb_rookie")) + 1));
				mEntityEquipe.setProperty("manquant_recrue",
						(((Long) mEntityEquipe.getProperty("manquant_recrue")) - 1));
				datastore.put(mEntityEquipe);

			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void getPlayersThatCanBeRachatAfterSeason(int teamId, int poolId, HttpServletRequest req, int cycleAnnuel) {
		NonSessionPlayers mBeanPlayers = new NonSessionPlayers();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Integer> players_id = new ArrayList<Integer>();
		List<Integer> team_id = new ArrayList<Integer>();
		List<String> nom = new ArrayList<String>();
		List<String> teamOfPlayer = new ArrayList<String>();
		List<Integer> pj = new ArrayList<Integer>();
		List<Integer> but_victoire = new ArrayList<Integer>();
		List<Integer> aide_overtime = new ArrayList<Integer>();
		List<Integer> blanchissage = new ArrayList<Integer>();
		List<Integer> pts = new ArrayList<Integer>();
		List<Integer> projection = new ArrayList<Integer>();
		List<String> position = new ArrayList<String>();
		List<Date> birthday = new ArrayList<Date>();
		List<Integer> can_be_rookie = new ArrayList<Integer>();
		List<Integer> take_proj = new ArrayList<Integer>();
		List<Integer> salaire_draft = new ArrayList<Integer>();
		List<Integer> contrat = new ArrayList<Integer>();
		List<Integer> club_ecole = new ArrayList<Integer>();
		List<String> years_1 = new ArrayList<String>();
		List<String> years_2 = new ArrayList<String>();
		List<String> years_3 = new ArrayList<String>();
		List<String> years_4 = new ArrayList<String>();
		List<String> years_5 = new ArrayList<String>();
		List<Integer> team_was_update = new ArrayList<Integer>();
		List<Integer> age = new ArrayList<Integer>();
		List<Integer> hier = new ArrayList<Integer>();
		List<Integer> semaine = new ArrayList<Integer>();
		List<Integer> mois = new ArrayList<Integer>();

		try {
			connexion = daoFactory.getConnection();

			if (cycleAnnuel == 9 || cycleAnnuel == 12) {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_RACHAT_DEBUT_SEASON, false,
						poolId, 1, 0, teamId);

			} else if (cycleAnnuel == 7) {
				preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_RACHAT_AFTER_SEASON, false,
						poolId, 1, 0, teamId);
			}
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int m_players_id = rs.getInt("_id");
				players_id.add(m_players_id);

				int m_team_id = rs.getInt("team_id");
				team_id.add(m_team_id);

				String m_nom = rs.getString("nom");
				nom.add(m_nom);

				String m_teamOfPlayer = rs.getString("team");
				teamOfPlayer.add(m_teamOfPlayer);

				int m_pj = rs.getInt("pj");
				pj.add(m_pj);

				int m_but_victoire = rs.getInt("but_victoire");
				but_victoire.add(m_but_victoire);

				int m_aide_overtime = rs.getInt("aide_overtime");
				aide_overtime.add(m_aide_overtime);

				int m_blanchissage = rs.getInt("blanchissage");
				blanchissage.add(m_blanchissage);

				int m_pts = rs.getInt("pts");
				pts.add(m_pts);

				int m_projection = rs.getInt("projection");
				projection.add(m_projection);

				String m_position = rs.getString("position");
				position.add(m_position);

				Date m_birthday = rs.getDate("birthday");
				birthday.add(m_birthday);

				int m_can_be_rookie = rs.getInt("can_be_rookie");
				can_be_rookie.add(m_can_be_rookie);

				int m_take_proj = rs.getInt("take_proj");
				take_proj.add(m_take_proj);

				int m_salaire_draft = rs.getInt("salaire_draft");
				salaire_draft.add(m_salaire_draft);

				int m_contrat = rs.getInt("contrat");
				contrat.add(m_contrat);

				int m_club_ecole = rs.getInt("club_ecole");
				club_ecole.add(m_club_ecole);

				String m_years_1 = rs.getString("years_1");
				years_1.add(m_years_1);

				String m_years_2 = rs.getString("years_2");
				years_2.add(m_years_2);

				String m_years_3 = rs.getString("years_3");
				years_3.add(m_years_3);

				String m_years_4 = rs.getString("years_4");
				years_4.add(m_years_4);

				String m_years_5 = rs.getString("years_5");
				years_5.add(m_years_5);

				int m_team_was_update = rs.getInt("team_was_update");
				team_was_update.add(m_team_was_update);

				int m_age = rs.getInt("age");
				age.add(m_age);

				int m_hier = rs.getInt("hier");
				hier.add(m_hier);

				int m_semaine = rs.getInt("semaine");
				semaine.add(m_semaine);

				int m_mois = rs.getInt("mois");
				mois.add(m_mois);

			}
			mBeanPlayers.setPlayers_id(players_id);
			mBeanPlayers.setAge(age);
			mBeanPlayers.setAide_overtime(aide_overtime);
			mBeanPlayers.setBlanchissage(blanchissage);
			mBeanPlayers.setBut_victoire(but_victoire);
			mBeanPlayers.setCan_be_rookie(can_be_rookie);
			mBeanPlayers.setClub_ecole(club_ecole);
			mBeanPlayers.setContrat(contrat);
			mBeanPlayers.setHier(hier);
			mBeanPlayers.setMois(mois);
			mBeanPlayers.setNom(nom);
			mBeanPlayers.setPj(pj);
			mBeanPlayers.setPosition(position);
			mBeanPlayers.setProjection(projection);
			mBeanPlayers.setPts(pts);
			mBeanPlayers.setSalaire_draft(salaire_draft);
			mBeanPlayers.setSemaine(semaine);
			mBeanPlayers.setTake_proj(take_proj);
			mBeanPlayers.setTeam_id(team_id);
			mBeanPlayers.setTeam_was_update(team_was_update);
			mBeanPlayers.setTeamOfPlayer(teamOfPlayer);
			mBeanPlayers.setYears_1(years_1);
			mBeanPlayers.setYears_2(years_2);
			mBeanPlayers.setYears_3(years_3);
			mBeanPlayers.setYears_4(years_4);
			mBeanPlayers.setYears_5(years_5);

			req.getSession().setAttribute("NonSessionPlayers", mBeanPlayers);

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

	}

	@Override
	public Boolean getUniquePlayersById(String player_id, String poolID, int teamId, HttpServletRequest req,
			String position) {
		Players mBean = new Players();
		int coutDuRachat = 0;
		ResultSet rs = null;
		String sYears1 = null, sYears2 = null, sYears3 = null, sYears4 = null, sYears5 = null;
		Boolean bYears1 = false, bYears2 = false, bYears3 = null, bYears4 = null, bYears5 = null;
		int iYears1 = 0, iYears2 = 0, iYears3 = 0, iYears4 = 0, iYears5 = 0;
		String nomDuJoueurRachat = "Bug de récupération du nom";
		int budget_restant = 0;
		int argent_recu = 0;
		int total_argent = 0;
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, GET_UNIQUE_PLAYERS_BY_ID_AND_POOL, false,
					Integer.parseInt(poolID), player_id);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				sYears1 = rs.getString("years_1");
				sYears2 = rs.getString("years_2");
				sYears3 = rs.getString("years_3");
				sYears4 = rs.getString("years_4");
				sYears5 = rs.getString("years_5");
				nomDuJoueurRachat = rs.getString("nom");
			}

			/***********   ************/

			if (sYears1.equals("A") || sYears1.equals("B") || sYears1.equals("X")) {
				bYears1 = false;
			} else {
				bYears1 = true;
				iYears1 = Integer.parseInt(sYears1);
			}
			if (sYears2.equals("A") || sYears2.equals("B") || sYears2.equals("X")) {
				bYears2 = false;
			} else {
				bYears2 = true;
				iYears2 = Integer.parseInt(sYears2);
			}
			if (sYears3.equals("A") || sYears3.equals("B") || sYears3.equals("X")) {
				bYears3 = false;
			} else {
				bYears3 = true;
				iYears3 = Integer.parseInt(sYears3);
			}
			if (sYears4.equals("A") || sYears4.equals("B") || sYears4.equals("X")) {
				bYears4 = false;
			} else {
				bYears4 = true;
				iYears4 = Integer.parseInt(sYears4);
			}
			if (sYears5.equals("A") || sYears5.equals("B") || sYears5.equals("X")) {
				bYears5 = false;
			} else {
				bYears5 = true;
				iYears5 = Integer.parseInt(sYears5);
			}

			/***********   ************/

			if (bYears1 && bYears2 && !bYears3 && !bYears4 && !bYears5) {
				coutDuRachat = (iYears1 + iYears2) / 2;
			}
			if (bYears1 && bYears2 && bYears3 && !bYears4 && !bYears5) {
				coutDuRachat = (iYears1 + iYears2 + iYears3) / 2;
			}
			if (bYears1 && bYears2 && bYears3 && bYears4 && !bYears5) {
				coutDuRachat = (iYears1 + iYears2 + iYears3 + iYears4) / 2;
			}
			if (bYears1 && bYears2 && bYears3 && bYears4 && bYears5) {
				coutDuRachat = (iYears1 + iYears2 + iYears3 + iYears4 + iYears5) / 2;
			}

			String nomClef = poolID + "_" + teamId;

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key mKey = KeyFactory.createKey("Equipe", nomClef);

			Entity mEntity = datastore.get(mKey);

			Long budget_restantL = (Long) mEntity.getProperty("budget_restant");
			budget_restant = budget_restantL.intValue();
			Long argent_recuL = (Long) mEntity.getProperty("argent_recu");
			argent_recu = argent_recuL.intValue();

			total_argent = budget_restant + argent_recu;

		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

		if ((budget_restant >= coutDuRachat) || (total_argent >= coutDuRachat)) {

			mBean.setSalaire_contrat(coutDuRachat);
			mBean.setNom(nomDuJoueurRachat);
			mBean.setPosition(position);
			mBean.set_id(Integer.parseInt(player_id));

			req.getSession().setAttribute("beanConfirmationRachat", mBean);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public void removePlayersFromTeamAfterRachat(int playersId, String poolID) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, DELETE_PLAYERS_FROM_TEAM, false,
					Integer.parseInt(poolID), playersId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void updateAgeForRookie(HttpServletRequest req) {
		DateTime dt = new DateTime();

		int year = dt.getYear() - 25;
		String birthday = year + "-09-15";
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_AGE_FOR_ROOKIE, false,
					Integer.parseInt(poolID), birthday);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void getRookieThatCanDropInClubEcoleAfterSeason(int teamId, int poolId, HttpServletRequest req) {
		NonSessionPlayers mBeanPlayers = new NonSessionPlayers();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Integer> players_id = new ArrayList<Integer>();

		List<String> nom = new ArrayList<String>();
		List<String> teamOfPlayer = new ArrayList<String>();

		List<String> position = new ArrayList<String>();

		List<String> years_1 = new ArrayList<String>();
		List<String> years_2 = new ArrayList<String>();
		List<String> years_3 = new ArrayList<String>();
		List<String> years_4 = new ArrayList<String>();
		List<String> years_5 = new ArrayList<String>();
		List<Integer> age = new ArrayList<Integer>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_ROOKIE_THAT_CAN_GO_DOWN, false, poolId, 0,
					0, teamId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int m_players_id = rs.getInt("_id");
				players_id.add(m_players_id);

				String m_nom = rs.getString("nom");
				nom.add(m_nom);

				String m_teamOfPlayer = rs.getString("team");
				teamOfPlayer.add(m_teamOfPlayer);

				String m_position = rs.getString("position");
				position.add(m_position);

				String m_years_1 = rs.getString("years_1");
				years_1.add(m_years_1);

				String m_years_2 = rs.getString("years_2");
				years_2.add(m_years_2);

				String m_years_3 = rs.getString("years_3");
				years_3.add(m_years_3);

				String m_years_4 = rs.getString("years_4");
				years_4.add(m_years_4);

				String m_years_5 = rs.getString("years_5");
				years_5.add(m_years_5);

				int m_age = rs.getInt("age");
				age.add(m_age);

			}
			mBeanPlayers.setPlayers_id(players_id);
			mBeanPlayers.setAge(age);
			mBeanPlayers.setNom(nom);
			mBeanPlayers.setPosition(position);
			mBeanPlayers.setTeamOfPlayer(teamOfPlayer);
			mBeanPlayers.setYears_1(years_1);
			mBeanPlayers.setYears_2(years_2);
			mBeanPlayers.setYears_3(years_3);
			mBeanPlayers.setYears_4(years_4);
			mBeanPlayers.setYears_5(years_5);

			req.getSession().setAttribute("NonSessionPlayers", mBeanPlayers);

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

	}

	@Override
	public String getPlayersName(String players_id, String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String nom = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYER_NAME, false,
					Integer.parseInt(poolID), Integer.parseInt(players_id));
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				nom = rs.getString("nom");

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return nom;
	}

	@Override
	public String putPlayersInClubEcole(int playersId, String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String position = null;
		ResultSet rs = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, PUT_PLAYERS_IN_CLUB_ECOLE, false,
					Integer.parseInt(poolID), playersId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_POSITION_OF_PLAYERS, false,
					Integer.parseInt(poolID), playersId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				position = rs.getString("position");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return position;
	}

	@Override
	public void insertionDansArchives(HttpServletRequest req) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, ARCHIVE_PLAYERS_LAST_YEAR, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void dropPlayersJaAndX(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_REMOVE_JA_X, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void updateProjection(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PROJECTION, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PROJECTION_2, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PROJECTION_3, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PROJECTION_4, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void setCanBeRookie(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, RESET_CAN_BE_ROOKIE, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_CAN_BE_ROOKIE, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void setTakeProj(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_TAKE_PROJ_0, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_TAKE_PROJ, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void migratePtsToLastYear(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, MIGRATE_PTS_FOR_YEARS_AGO_1, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, MIGRATE_PTS_FOR_YEARS_AGO_2, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void moveYearsToYearsContract(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, MOVE_YEARS_TO_YEARS, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_YEARS_TO_YEARS, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void setSalaireDraft(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_SALAIRE_ATTAQUANT, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_SALAIRE_DEFENSEUR, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_SALAIRE_GARDIEN, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_SALAIRE_ATTAQUANT2, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_SALAIRE_DEFENSEUR2, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SET_SALAIRE_GARDIEN2, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	@Override
	public int getTotalSalaireNow(String poolID, int i) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_TOTAL_SALAIRE_NOW, false,
					Integer.parseInt(poolID), i);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return result;

	}

	@Override
	public int getNbAttaquant(String poolID, int i) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_NB_ATTAQUANT, false,
					Integer.parseInt(poolID), i);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return result;
	}

	@Override
	public int getNbDefenseur(String poolID, int i) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_NB_DEFENSEUR, false,
					Integer.parseInt(poolID), i);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return result;
	}

	@Override
	public int getNbGardien(String poolID, int i) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_NB_GARDIEN, false,
					Integer.parseInt(poolID), i);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return result;
	}

	@Override
	public int getNbRookie(String poolID, int i) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_NB_ROOKIE, false, Integer.parseInt(poolID),
					i);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return result;
	}

	@Override
	public int getNbContrat(String poolID, int i) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_NB_CONTRAT, false,
					Integer.parseInt(poolID), i);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}
		return result;
	}

	@Override
	public void dropPlayersAandB(String poolID) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, DROP_PLAYERS_A_B, false,
					Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void getRookieInClubEcole(int teamId, int poolId, HttpServletRequest req) throws DAOException {
		NonSessionPlayers mBeanPlayers = new NonSessionPlayers();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Integer> players_id = new ArrayList<Integer>();
		List<Integer> team_id = new ArrayList<Integer>();
		List<String> nom = new ArrayList<String>();
		List<String> teamOfPlayer = new ArrayList<String>();
		List<Integer> pj = new ArrayList<Integer>();
		List<Integer> but_victoire = new ArrayList<Integer>();
		List<Integer> aide_overtime = new ArrayList<Integer>();
		List<Integer> blanchissage = new ArrayList<Integer>();
		List<Integer> pts = new ArrayList<Integer>();
		List<Integer> projection = new ArrayList<Integer>();
		List<String> position = new ArrayList<String>();
		List<Date> birthday = new ArrayList<Date>();
		List<Integer> can_be_rookie = new ArrayList<Integer>();
		List<Integer> take_proj = new ArrayList<Integer>();
		List<Integer> salaire_draft = new ArrayList<Integer>();
		List<Integer> contrat = new ArrayList<Integer>();
		List<Integer> club_ecole = new ArrayList<Integer>();
		List<String> years_1 = new ArrayList<String>();
		List<String> years_2 = new ArrayList<String>();
		List<String> years_3 = new ArrayList<String>();
		List<String> years_4 = new ArrayList<String>();
		List<String> years_5 = new ArrayList<String>();
		List<Integer> team_was_update = new ArrayList<Integer>();
		List<Integer> age = new ArrayList<Integer>();
		List<Integer> hier = new ArrayList<Integer>();
		List<Integer> semaine = new ArrayList<Integer>();
		List<Integer> mois = new ArrayList<Integer>();

		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_IN_CLUB_ECOLE, false, poolId, 1,
					teamId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int m_players_id = rs.getInt("_id");
				players_id.add(m_players_id);

				int m_team_id = rs.getInt("team_id");
				team_id.add(m_team_id);

				String m_nom = rs.getString("nom");
				nom.add(m_nom);

				String m_teamOfPlayer = rs.getString("team");
				teamOfPlayer.add(m_teamOfPlayer);

				int m_pj = rs.getInt("pj");
				pj.add(m_pj);

				int m_but_victoire = rs.getInt("but_victoire");
				but_victoire.add(m_but_victoire);

				int m_aide_overtime = rs.getInt("aide_overtime");
				aide_overtime.add(m_aide_overtime);

				int m_blanchissage = rs.getInt("blanchissage");
				blanchissage.add(m_blanchissage);

				int m_pts = rs.getInt("pts");
				pts.add(m_pts);

				int m_projection = rs.getInt("projection");
				projection.add(m_projection);

				String m_position = rs.getString("position");
				position.add(m_position);

				Date m_birthday = rs.getDate("birthday");
				birthday.add(m_birthday);

				int m_can_be_rookie = rs.getInt("can_be_rookie");
				can_be_rookie.add(m_can_be_rookie);

				int m_take_proj = rs.getInt("take_proj");
				take_proj.add(m_take_proj);

				int m_salaire_draft = rs.getInt("salaire_draft");
				salaire_draft.add(m_salaire_draft);

				int m_contrat = rs.getInt("contrat");
				contrat.add(m_contrat);

				int m_club_ecole = rs.getInt("club_ecole");
				club_ecole.add(m_club_ecole);

				String m_years_1 = rs.getString("years_1");
				years_1.add(m_years_1);

				String m_years_2 = rs.getString("years_2");
				years_2.add(m_years_2);

				String m_years_3 = rs.getString("years_3");
				years_3.add(m_years_3);

				String m_years_4 = rs.getString("years_4");
				years_4.add(m_years_4);

				String m_years_5 = rs.getString("years_5");
				years_5.add(m_years_5);

				int m_team_was_update = rs.getInt("team_was_update");
				team_was_update.add(m_team_was_update);

				int m_age = rs.getInt("age");
				age.add(m_age);

				int m_hier = rs.getInt("hier");
				hier.add(m_hier);

				int m_semaine = rs.getInt("semaine");
				semaine.add(m_semaine);

				int m_mois = rs.getInt("mois");
				mois.add(m_mois);

			}
			mBeanPlayers.setPlayers_id(players_id);
			mBeanPlayers.setAge(age);
			mBeanPlayers.setAide_overtime(aide_overtime);
			mBeanPlayers.setBlanchissage(blanchissage);
			mBeanPlayers.setBut_victoire(but_victoire);
			mBeanPlayers.setCan_be_rookie(can_be_rookie);
			mBeanPlayers.setClub_ecole(club_ecole);
			mBeanPlayers.setContrat(contrat);
			mBeanPlayers.setHier(hier);
			mBeanPlayers.setMois(mois);
			mBeanPlayers.setNom(nom);
			mBeanPlayers.setPj(pj);
			mBeanPlayers.setPosition(position);
			mBeanPlayers.setProjection(projection);
			mBeanPlayers.setPts(pts);
			mBeanPlayers.setSalaire_draft(salaire_draft);
			mBeanPlayers.setSemaine(semaine);
			mBeanPlayers.setTake_proj(take_proj);
			mBeanPlayers.setTeam_id(team_id);
			mBeanPlayers.setTeam_was_update(team_was_update);
			mBeanPlayers.setTeamOfPlayer(teamOfPlayer);
			mBeanPlayers.setYears_1(years_1);
			mBeanPlayers.setYears_2(years_2);
			mBeanPlayers.setYears_3(years_3);
			mBeanPlayers.setYears_4(years_4);
			mBeanPlayers.setYears_5(years_5);

			req.getSession().setAttribute("NonSessionPlayers", mBeanPlayers);

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

	}

	@Override
	public void dropRookie(int poolId, String players_id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, DROP_ROOKIE, false, poolId, players_id);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public int monterRookie(int poolId, String players_id, int numberOfYearSign, String salaire, PlayersDao playersDao) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String years1 = null, years2 = null, years3 = null, years4 = null, years5 = null;
		int salaireInt=0;
		if(salaire.equalsIgnoreCase("C")||salaire.equalsIgnoreCase("D")) {
			salaireInt = playersDao.getYears0(poolId,players_id);
		} else {
			salaireInt= Integer.parseInt(salaire);
		}
		
		switch (numberOfYearSign) {
		case 2:
			years1 = String.valueOf(salaireInt);
			years2 = String.valueOf(salaireInt);
			years3 = "X";
			years4 = "X";
			years5 = "X";

			break;
		case 3:
			years1 = String.valueOf(salaireInt);
			years2 = String.valueOf(salaireInt);
			years3 = String.valueOf(salaireInt);
			years4 = "X";
			years5 = "X";

			break;
		case 4:
			years1 = String.valueOf(salaireInt);
			years2 = String.valueOf(salaireInt);
			years3 = String.valueOf(salaireInt);
			years4 = String.valueOf(salaireInt);
			years5 = "X";

			break;
		case 5:
			years1 = String.valueOf(salaireInt);
			years2 = String.valueOf(salaireInt);
			years3 = String.valueOf(salaireInt);
			years4 = String.valueOf(salaireInt);
			years5 = String.valueOf(salaireInt);

			break;
		}

		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, MONTER_ROOKIE, false, poolId, years1, years2,
					years3, years4, years5, players_id);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return salaireInt;

	}

	@Override
	public void setSalaireForRookie(String poolID, SalaireDao salaireDao) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs;
		String years_2=null;
		String years_3=null;
		String years_4=null;
		String years_5=null;
		
		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, GET_ROOKIE_AB, false, poolID);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int players_id = rs.getInt("_id");
				String years_1 = rs.getString("years_1");
				String position = rs.getString("position");
				int take_proj = rs.getInt("take_proj");
				int projection = rs.getInt("projection");
				int pts = rs.getInt("pts");
				int salaire = 0;
				int salaireB = 0;
				String years_0 = rs.getString("years_0");
				int lastYearsSalaire = Integer.parseInt(years_0);
				years_2 = rs.getString("years_2");
				years_3 = rs.getString("years_3");
				years_4 = rs.getString("years_4");
				years_5 = rs.getString("years_5");

				if (years_1.equals("A")) {
					if (take_proj == 0) {

						salaire = salaireDao.getSalaireTable(Integer.parseInt(poolID), position, pts);

					} else {
						salaire = salaireDao.getSalaireTable(Integer.parseInt(poolID), position, projection);
					}

				} else {

					if (take_proj == 0) {
						salaire = salaireDao.getSalaireTable(Integer.parseInt(poolID), position, pts);
						salaireB = lastYearsSalaire + 1000000;

					} else {
						salaire = salaireDao.getSalaireTable(Integer.parseInt(poolID), position, projection);
						salaireB = lastYearsSalaire + 1000000;

					}

					if (salaireB < salaire + 1000000) {
						salaire = salaireB;
					} else {
						salaire = salaire + 1000000;
					}

				}
		
					
					if (years_2.equalsIgnoreCase("X")) {

						preparedStatement = initialisationRequetePreparee(connexion, UPDATE_SALAIRE_ROOKIE, false, poolID,
								salaire, "X", "X", "X", "X", players_id);
						preparedStatement.execute();
					} else if (years_3.equalsIgnoreCase("X")) {
						preparedStatement = initialisationRequetePreparee(connexion, UPDATE_SALAIRE_ROOKIE, false, poolID,
								salaire, salaire, "X", "X", "X", players_id);
						preparedStatement.execute();

					} else if (years_4.equalsIgnoreCase("X")) {
						preparedStatement = initialisationRequetePreparee(connexion, UPDATE_SALAIRE_ROOKIE, false, poolID,
								salaire, salaire, salaire, "X", "X", players_id);
						preparedStatement.execute();

					} else if (years_5.equalsIgnoreCase("X")) {
						preparedStatement = initialisationRequetePreparee(connexion, UPDATE_SALAIRE_ROOKIE, false, poolID,
								salaire, salaire, salaire, salaire, players_id, "X");
						preparedStatement.execute();

					}
				

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		

	}

	@Override
	public int getYears0(int poolId, String players_id) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs=null;
		String salaire=null;
		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, GET_YEARS_0, false, poolId, players_id);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				salaire = rs.getString("years_0");
				
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs,preparedStatement, connexion);
		}
		return Integer.parseInt(salaire);
	}

	@Override
	public void dropPlayersCetD(HttpServletRequest req, String poolID) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();

			preparedStatement = initialisationRequetePreparee(connexion, DROP_PLAYERS_C_D, false, poolID);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		
	}

}
