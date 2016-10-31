package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pedagogiksolution.datastorebeans.Classement;


public class ClassementDaoImpl implements ClassementDao {

    private static final String CREATE_CLASSEMENT = "CREATE TABLE classement_? LIKE classement";
    private static final String CREATE_CLASSEMENT_ARCHIVES = "CREATE TABLE classement_archive_? LIKE classement";
    private static final String INSERT_TEAM_CLASSEMENT = "INSERT INTO classement_? (equipe,team_id,pool_id,year_of_the_standing) VALUE (?,?,?,?)";
    private static final String CHECK_IF_TEAM_EXIST = "SELECT * FROM classement_? WHERE team_id=?";
    private static final String UPDATE_TEAM_CLASSEMENT = "UPDATE classement_? SET equipe=? WHERE team_id=?";
    private static final String GET_CLASSEMENT_BY_POOL_ID = "SELECT * FROM classement_? ORDER BY points DESC";
    private static final String UPDATE_DAILY_STATS = "UPDATE classement_? SET pj=?,but_victoire=?,aide_overtime=?,pts=? WHERE team_id=?";
    private DAOFactory daoFactory;

    ClassementDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createClassementTable(int poolID) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_CLASSEMENT, false, poolID);
	    preparedStatement.execute();
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public void createClassementArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_CLASSEMENT_ARCHIVES, false, poolID);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public void insertTeamInClassement(String nomDuTeam, int teamID, int poolID,int years) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_TEAM_CLASSEMENT, false, poolID,nomDuTeam,teamID,poolID,years);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public Boolean checkIfTeamAlreadyCreate(int teamID, int poolID) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CHECK_IF_TEAM_EXIST, false, poolID,teamID);
	    ResultSet rs = preparedStatement.executeQuery();
	    
	    if(rs.first()){
		return true;
	    } else {
		return false;
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
    }

    @Override
    public void updateTeamInClassement(String nomDuTeam, int teamID, int poolID) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_TEAM_CLASSEMENT, false, poolID,nomDuTeam,teamID);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public Classement cronJobGetClassementbyPoolId(int poolId) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<Integer> team_id = new ArrayList<Integer>();
	List<String> equipe = new ArrayList<String>();
	List<Integer> pj = new ArrayList<Integer>();
	List<Integer> but = new ArrayList<Integer>();
	List<Integer> passe = new ArrayList<Integer>();
	List<Integer> points = new ArrayList<Integer>();
	List<Integer> hier = new ArrayList<Integer>();
	List<Integer> semaine = new ArrayList<Integer>();
	List<Integer> mois = new ArrayList<Integer>();
	List<Integer> moyenne = new ArrayList<Integer>();
	List<Integer> difference = new ArrayList<Integer>();
	Classement mBeanClassement= new Classement();
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_CLASSEMENT_BY_POOL_ID, false, poolId);
	    rs = preparedStatement.executeQuery();
	    
	    while (rs.next()) {

		int m_team_id = (rs.getInt("team_id"));
		team_id.add(m_team_id);

		String m_equipe = (rs.getString("equipe"));
		equipe.add(m_equipe);

		int m_pj = (rs.getInt("pj"));
		pj.add(m_pj);

		int m_but = (rs.getInt("but"));
		but.add(m_but);

		int m_passe = (rs.getInt("passe"));
		passe.add(m_passe);

		int m_points = (rs.getInt("points"));
		points.add(m_points);

		int m_hier = (rs.getInt("hier"));
		hier.add(m_hier);

		int m_semaine = (rs.getInt("semaine"));
		semaine.add(m_semaine);

		int m_mois = (rs.getInt("mois"));
		mois.add(m_mois);

		int m_moyenne = (rs.getInt("moyenne"));
		moyenne.add(m_moyenne);

		int m_difference = (rs.getInt("difference"));
		difference.add(m_difference);

	    }

	    mBeanClassement.setPoolId(Integer.toString(poolId));
	    mBeanClassement.setTeam_id(team_id);
	    mBeanClassement.setEquipe(equipe);
	    mBeanClassement.setPj(pj);
	    mBeanClassement.setBut(but);
	    mBeanClassement.setPasse(passe);
	    mBeanClassement.setPoints(points);
	    mBeanClassement.setHier(hier);
	    mBeanClassement.setSemaine(semaine);
	    mBeanClassement.setMois(mois);
	    mBeanClassement.setMoyenne(moyenne);
	    mBeanClassement.setDifference(difference);
	    
	    return mBeanClassement;
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs,preparedStatement, connexion);
	}
	
    }

    
    @Override
    public void updateStat(int poolId, int pj, int but, int passe, int pts, int teamId) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DAILY_STATS, false, poolId,pj,but,passe,pts,teamId);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	
    }

    @Override
    public void updateDifference(int poolId) throws DAOException {
	// TODO Auto-generated method stub
	
    }

    
    

    
   

   

}
