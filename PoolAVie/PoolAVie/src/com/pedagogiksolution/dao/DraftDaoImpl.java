package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Pool;

public class DraftDaoImpl implements DraftDao {

	private static final String CREATE_DRAFT = "CREATE TABLE draft? LIKE draft";
	private static final String CREATE_DRAFT_ARCHIVES = "CREATE TABLE draft_archive_? LIKE draft";
	private static final String INSERT_DRAFT_FIRST_YEAR = "INSERT INTO draft? (draft_pick_no,team_id,ronde,team_count,pool_id,follow_up,year_of_draft,equipe) VALUE(?,?,?,?,?,?,?,?)";
	private static final String GET_DRAFT_ROUND_ORDER = "SELECT * FROM draft? ORDER BY draft_pick_no ASC";
	private static final String UPDATE_DRAFT_ROUND_AFTER_DRAFT_PICK = "UPDATE draft? SET player_drafted=? WHERE _id=?";
	private static final String ARCHIVE_DRAFT_LAST_YEAR = "INSERT INTO draft_archive_? (`draft_pick_no`,`ronde`,`equipe`,`team_id`,`from_who`,`team_id_from`,`team_count`,`follow_up`,`player_drafted`,`year_of_draft`,`pool_id`) SELECT draft_pick_no,ronde,equipe,team_id,from_who,team_id_from,team_count,follow_up,player_drafted,year_of_draft,pool_id FROM draft?";

	private static final String RESET_FOR_NEW_YEARS = "UPDATE draft? SET equipe=null,team_id=null,from_who=null,team_id_from=null,team_count=null,follow_up=0,player_drafted=null,year_of_draft=?";
	private static final String UPDATE_FROM_DRAFT_PICK = "UPDATE draft? AS a INNER JOIN draft_pick? AS b ON (a.ronde=b.pick_no AND a.team_id=b.original_team_id) SET a.team_id=b.team_id,a.team_id_from=a.team_id,a.from_who=a.equipe";
	private static final String TRUNCATE_TABLE = "TRUNCATE draft?";
	private static final String UPDATE_TEAM_NAME = "UPDATE draft? SET equipe=? WHERE team_id=?";

	private DAOFactory daoFactory;

	DraftDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void createDraftTable(int poolID) throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, CREATE_DRAFT, false, poolID);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void createDraftArchiveTable(int poolID) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, CREATE_DRAFT_ARCHIVES, false, poolID);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void populateFirstYearsDraft(int poolId, List<Integer> permutation, Pool mBeanPool) throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {

			connexion = daoFactory.getConnection();

			int draft_pick_no = 1;
			int ronde = 1;
			int years_of_the_draft = 1;
			String nomTeam = null;

			for (int teamId : permutation) {
				switch (teamId) {
				case 1:
					nomTeam = mBeanPool.getNomTeam1();
					break;
				case 2:
					nomTeam = mBeanPool.getNomTeam2();
					break;
				case 3:
					nomTeam = mBeanPool.getNomTeam3();
					break;
				case 4:
					nomTeam = mBeanPool.getNomTeam4();
					break;
				case 5:
					nomTeam = mBeanPool.getNomTeam5();
					break;
				case 6:
					nomTeam = mBeanPool.getNomTeam6();
					break;
				case 7:
					nomTeam = mBeanPool.getNomTeam7();
					break;
				case 8:
					nomTeam = mBeanPool.getNomTeam8();
					break;
				case 9:
					nomTeam = mBeanPool.getNomTeam9();
					break;
				case 10:
					nomTeam = mBeanPool.getNomTeam10();
					break;
				case 11:
					nomTeam = mBeanPool.getNomTeam11();
					break;
				case 12:
					nomTeam = mBeanPool.getNomTeam12();
					break;

				}
				preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false, poolId,
						draft_pick_no, teamId, ronde, ronde, poolId, 0, years_of_the_draft, nomTeam);
				preparedStatement.execute();
				draft_pick_no++;
			}

			ronde++;

			for (ronde = 2; ronde < 31; ronde++) {

				Collections.reverse(permutation);
				for (int teamId : permutation) {
					switch (teamId) {
					case 1:
						nomTeam = mBeanPool.getNomTeam1();
						break;
					case 2:
						nomTeam = mBeanPool.getNomTeam2();
						break;
					case 3:
						nomTeam = mBeanPool.getNomTeam3();
						break;
					case 4:
						nomTeam = mBeanPool.getNomTeam4();
						break;
					case 5:
						nomTeam = mBeanPool.getNomTeam5();
						break;
					case 6:
						nomTeam = mBeanPool.getNomTeam6();
						break;
					case 7:
						nomTeam = mBeanPool.getNomTeam7();
						break;
					case 8:
						nomTeam = mBeanPool.getNomTeam8();
						break;
					case 9:
						nomTeam = mBeanPool.getNomTeam9();
						break;
					case 10:
						nomTeam = mBeanPool.getNomTeam10();
						break;
					case 11:
						nomTeam = mBeanPool.getNomTeam11();
						break;
					case 12:
						nomTeam = mBeanPool.getNomTeam12();
						break;

					}

					preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false, poolId,
							draft_pick_no, teamId, ronde, ronde, poolId, 0, years_of_the_draft, nomTeam);
					preparedStatement.execute();
					draft_pick_no++;
				}

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public DraftRound setDraftRoundOrder(int poolId) throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_DRAFT_ROUND_ORDER, false, poolId);
			rs = preparedStatement.executeQuery();
			List<Long> draft_pick_no = new ArrayList<Long>();
			List<String> equipe = new ArrayList<String>();
			List<Long> ronde = new ArrayList<Long>();
			List<Long> team_id = new ArrayList<Long>();
			List<String> from_who = new ArrayList<String>();
			List<Long> team_id_from = new ArrayList<Long>();
			List<Long> team_count = new ArrayList<Long>();
			List<Long> follow_up = new ArrayList<Long>();
			List<String> player_drafted = new ArrayList<String>();
			List<Long> year_of_draft = new ArrayList<Long>();

			while (rs.next()) {
				int draft_pick_no2 = rs.getInt("draft_pick_no");
				draft_pick_no.add(Long.valueOf(draft_pick_no2));
				String equipe2 = rs.getString("equipe");
				equipe.add(equipe2);
				int ronde2 = rs.getInt("ronde");
				ronde.add(Long.valueOf(ronde2));
				int team_id2 = rs.getInt("team_id");
				team_id.add(Long.valueOf(team_id2));
				String from_who2 = rs.getString("from_who");
				from_who.add(from_who2);
				int team_id_from2 = rs.getInt("team_id_from");
				team_id_from.add(Long.valueOf(team_id_from2));
				int team_count2 = rs.getInt("team_count");
				team_count.add(Long.valueOf(team_count2));
				int follow_up2 = rs.getInt("follow_up");
				follow_up.add(Long.valueOf(follow_up2));
				String player_drafted2 = rs.getString("player_drafted");
				player_drafted.add(player_drafted2);
				int year_of_draft2 = rs.getInt("year_of_draft");
				year_of_draft.add(Long.valueOf(year_of_draft2));
			}

			DraftRound mBeanDraft = new DraftRound();
			mBeanDraft.setPoolId(String.valueOf(poolId));
			mBeanDraft.setDraft_pick_no(draft_pick_no);
			mBeanDraft.setEquipe(equipe);
			mBeanDraft.setRonde(ronde);
			mBeanDraft.setTeam_id(team_id);
			mBeanDraft.setFrom_who(from_who);
			mBeanDraft.setTeam_id_from(team_id_from);
			mBeanDraft.setTeam_count(team_count);
			mBeanDraft.setFollow_up(follow_up);
			mBeanDraft.setPlayer_drafted(player_drafted);
			mBeanDraft.setYear_of_draft(year_of_draft);

			return mBeanDraft;

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		}

	}

	@Override
	public void persistPlayerPick(String nom, int currentPickId, int poolId) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DRAFT_ROUND_AFTER_DRAFT_PICK, false,
					poolId, nom, currentPickId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void insertionDansArchives(HttpServletRequest req) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, ARCHIVE_DRAFT_LAST_YEAR, false,
					Integer.parseInt(poolID), Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void resetDraft(String poolID, String years) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, RESET_FOR_NEW_YEARS, false,
					Integer.parseInt(poolID), years);
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

	}

	@Override
	public void populationDraftRoundFromDraftPick(String poolID, List<Integer> classementInverseLastYears, String years, HttpServletRequest req) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int draft_pick_no = 1;
		int ronde = 1;
		int years_of_the_draft = Integer.parseInt(years);
		String nomTeam = null;
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, TRUNCATE_TABLE, false, Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);

		}

		try {
			connexion = daoFactory.getConnection();
			for (ronde = 1; ronde < 31; ronde++) {

				for (int teamId : classementInverseLastYears) {
					switch (teamId) {
					case 1:
						nomTeam = mBeanPool.getNomTeam1();
						break;
					case 2:
						nomTeam = mBeanPool.getNomTeam2();
						break;
					case 3:
						nomTeam = mBeanPool.getNomTeam3();
						break;
					case 4:
						nomTeam = mBeanPool.getNomTeam4();
						break;
					case 5:
						nomTeam = mBeanPool.getNomTeam5();
						break;
					case 6:
						nomTeam = mBeanPool.getNomTeam6();
						break;
					case 7:
						nomTeam = mBeanPool.getNomTeam7();
						break;
					case 8:
						nomTeam = mBeanPool.getNomTeam8();
						break;
					case 9:
						nomTeam = mBeanPool.getNomTeam9();
						break;
					case 10:
						nomTeam = mBeanPool.getNomTeam10();
						break;
					case 11:
						nomTeam = mBeanPool.getNomTeam11();
						break;
					case 12:
						nomTeam = mBeanPool.getNomTeam12();
						break;

					}

					preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false, Integer.parseInt(poolID),
							draft_pick_no, teamId, ronde, ronde, Integer.parseInt(poolID), 0, years_of_the_draft, nomTeam);
					preparedStatement.execute();
					draft_pick_no++;
				}

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_FROM_DRAFT_PICK, false, Integer.parseInt(poolID),Integer.parseInt(poolID));
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);

		}
		
		
		// on update nom des teams
		try {
			connexion = daoFactory.getConnection();
			
		for (int teamId = 1;teamId<(mBeanPool.getNumberTeam()+1);teamId++) {
			switch (teamId) {
			case 1:
				nomTeam = mBeanPool.getNomTeam1();
				break;
			case 2:
				nomTeam = mBeanPool.getNomTeam2();
				break;
			case 3:
				nomTeam = mBeanPool.getNomTeam3();
				break;
			case 4:
				nomTeam = mBeanPool.getNomTeam4();
				break;
			case 5:
				nomTeam = mBeanPool.getNomTeam5();
				break;
			case 6:
				nomTeam = mBeanPool.getNomTeam6();
				break;
			case 7:
				nomTeam = mBeanPool.getNomTeam7();
				break;
			case 8:
				nomTeam = mBeanPool.getNomTeam8();
				break;
			case 9:
				nomTeam = mBeanPool.getNomTeam9();
				break;
			case 10:
				nomTeam = mBeanPool.getNomTeam10();
				break;
			case 11:
				nomTeam = mBeanPool.getNomTeam11();
				break;
			case 12:
				nomTeam = mBeanPool.getNomTeam12();
				break;

			}
			preparedStatement = initialisationRequetePreparee(connexion, UPDATE_TEAM_NAME, false, Integer.parseInt(poolID),nomTeam,teamId);
			preparedStatement.execute();
		}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);

		}
		
		
	

	}

	@Override
	public void putDatabaseInDatastore(String poolID) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, GET_DRAFT_ROUND_ORDER, false, Integer.parseInt(poolID));
			rs = preparedStatement.executeQuery();
			List<Long> draft_pick_no = new ArrayList<Long>();
			List<String> equipe = new ArrayList<String>();
			List<Long> ronde = new ArrayList<Long>();
			List<Long> team_id = new ArrayList<Long>();
			List<String> from_who = new ArrayList<String>();
			List<Long> team_id_from = new ArrayList<Long>();
			List<Long> team_count = new ArrayList<Long>();
			List<Long> follow_up = new ArrayList<Long>();
			List<String> player_drafted = new ArrayList<String>();
			List<Long> year_of_draft = new ArrayList<Long>();
			DraftRound mBeanDraftRound = new DraftRound();
			
			while (rs.next()) {
				int draft_pick_no2 = rs.getInt("draft_pick_no");
				draft_pick_no.add(Long.valueOf(draft_pick_no2));
				String equipe2 = rs.getString("equipe");
				equipe.add(equipe2);
				int ronde2 = rs.getInt("ronde");
				ronde.add(Long.valueOf(ronde2));
				int team_id2 = rs.getInt("team_id");
				team_id.add(Long.valueOf(team_id2));
				String from_who2 = rs.getString("from_who");
				from_who.add(from_who2);
				int team_id_from2 = rs.getInt("team_id_from");
				team_id_from.add(Long.valueOf(team_id_from2));
				int team_count2 = rs.getInt("team_count");
				team_count.add(Long.valueOf(team_count2));
				int follow_up2 = rs.getInt("follow_up");
				follow_up.add(Long.valueOf(follow_up2));
				String player_drafted2 = rs.getString("player_drafted");
				player_drafted.add(player_drafted2);
				int year_of_draft2 = rs.getInt("year_of_draft");
				year_of_draft.add(Long.valueOf(year_of_draft2));
			}
			
			mBeanDraftRound.setDraft_pick_no(draft_pick_no);
			mBeanDraftRound.setEquipe(equipe);
			mBeanDraftRound.setRonde(ronde);
			mBeanDraftRound.setTeam_id(team_id);
			mBeanDraftRound.setFrom_who(from_who);
			mBeanDraftRound.setTeam_id_from(team_id_from);
			mBeanDraftRound.setTeam_count(team_count);
			mBeanDraftRound.setFollow_up(follow_up);
			mBeanDraftRound.setPlayer_drafted(player_drafted);
			mBeanDraftRound.setYear_of_draft(year_of_draft);
			
			
			Entity mEntity = mBeanDraftRound.mapBeanToEntityForDatastore(mBeanDraftRound, poolID);
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
						
			datastore.put(mEntity);
			
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);

		}

		
	}

}
