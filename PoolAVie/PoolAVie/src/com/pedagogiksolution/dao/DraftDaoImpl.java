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

import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Pool;

public class DraftDaoImpl implements DraftDao {

    private static final String CREATE_DRAFT = "CREATE TABLE draft? LIKE draft";
    private static final String CREATE_DRAFT_ARCHIVES = "CREATE TABLE draft_archive_? LIKE draft";
    private static final String INSERT_DRAFT_FIRST_YEAR = "INSERT INTO draft? (draft_pick_no,team_id,ronde,team_count,pool_id,follow_up,year_of_draft,equipe) VALUE(?,?,?,?,?,?,?,?)";
    private static final String GET_DRAFT_ROUND_ORDER = "SELECT * FROM draft? ORDER BY draft_pick_no ASC";
	private static final String UPDATE_DRAFT_ROUND_AFTER_DRAFT_PICK = "UPDATE draft? SET player_drafted=? WHERE _id=?";
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
	    int years_of_the_draft=1;
	    String nomTeam = null;

	    for (int teamId : permutation) {
		switch(teamId){
		case 1: nomTeam =mBeanPool.getNomTeam1();
		    break;
		case 2: nomTeam =mBeanPool.getNomTeam2();
		    break;
		case 3: nomTeam =mBeanPool.getNomTeam3();
		    break;
		case 4: nomTeam =mBeanPool.getNomTeam4();
		    break;
		case 5: nomTeam =mBeanPool.getNomTeam5();
		    break;
		case 6: nomTeam =mBeanPool.getNomTeam6();
		    break;
		case 7: nomTeam =mBeanPool.getNomTeam7();
		    break;
		case 8: nomTeam =mBeanPool.getNomTeam8();
		    break;
		case 9: nomTeam =mBeanPool.getNomTeam9();
		    break;
		case 10: nomTeam =mBeanPool.getNomTeam10();
		    break;
		case 11: nomTeam =mBeanPool.getNomTeam11();
		    break;
		case 12: nomTeam =mBeanPool.getNomTeam12();
		    break;
		    
		}
		preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false,poolId,draft_pick_no, teamId, ronde, ronde, poolId, 0,years_of_the_draft,nomTeam);
		preparedStatement.execute();
		draft_pick_no++;
	    }
	    
	    ronde++;

	    for (ronde = 2; ronde < 32; ronde++) {
		
		Collections.reverse(permutation);
		for (int teamId : permutation) {
		    switch(teamId){
			case 1: nomTeam =mBeanPool.getNomTeam1();
			    break;
			case 2: nomTeam =mBeanPool.getNomTeam2();
			    break;
			case 3: nomTeam =mBeanPool.getNomTeam3();
			    break;
			case 4: nomTeam =mBeanPool.getNomTeam4();
			    break;
			case 5: nomTeam =mBeanPool.getNomTeam5();
			    break;
			case 6: nomTeam =mBeanPool.getNomTeam6();
			    break;
			case 7: nomTeam =mBeanPool.getNomTeam7();
			    break;
			case 8: nomTeam =mBeanPool.getNomTeam8();
			    break;
			case 9: nomTeam =mBeanPool.getNomTeam9();
			    break;
			case 10: nomTeam =mBeanPool.getNomTeam10();
			    break;
			case 11: nomTeam =mBeanPool.getNomTeam11();
			    break;
			case 12: nomTeam =mBeanPool.getNomTeam12();
			    break;
			    
			}

		    preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false,poolId,draft_pick_no, teamId, ronde, ronde, poolId, 0,years_of_the_draft,nomTeam);
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
	    List<Integer> draft_pick_no = new ArrayList<Integer>();
		List<String> equipe = new ArrayList<String>();
		List<Integer> ronde = new ArrayList<Integer>();
		List<Integer> team_id = new ArrayList<Integer>();
		List<String> from_who = new ArrayList<String>();
		List<Integer> team_id_from = new ArrayList<Integer>();
		List<Integer> team_count = new ArrayList<Integer>();
		List<Integer> follow_up = new ArrayList<Integer>();
		List<String> player_drafted = new ArrayList<String>();
		List<Integer> year_of_draft = new ArrayList<Integer>();

		
		    while (rs.next()) {
			int draft_pick_no2 = rs.getInt("draft_pick_no");
			draft_pick_no.add(draft_pick_no2);
			String equipe2 = rs.getString("equipe");
			equipe.add(equipe2);
			int ronde2 = rs.getInt("ronde");
			ronde.add(ronde2);
			int team_id2 = rs.getInt("team_id");
			team_id.add(team_id2);
			String from_who2 = rs.getString("from_who");
			from_who.add(from_who2);
			int team_id_from2 = rs.getInt("team_id_from");
			team_id_from.add(team_id_from2);
			int team_count2 = rs.getInt("team_count");
			team_count.add(team_count2);
			int follow_up2 = rs.getInt("follow_up");
			follow_up.add(follow_up2);
			String player_drafted2 = rs.getString("player_drafted");
			player_drafted.add(player_drafted2);
			int year_of_draft2 = rs.getInt("year_of_draft");
			year_of_draft.add(year_of_draft2);
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
    	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DRAFT_ROUND_AFTER_DRAFT_PICK, false, poolId,nom,currentPickId);
    	    preparedStatement.executeUpdate();

    	} catch (SQLException e) {
    	    throw new DAOException(e);
    	} finally {
    	    fermeturesSilencieuses(preparedStatement, connexion);
    	}
		
	}

}
