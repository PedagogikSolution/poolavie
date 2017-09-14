package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.datastorebeans.Pool;

public class ClassementDaoImpl implements ClassementDao {

    private static final String CREATE_CLASSEMENT = "CREATE TABLE classement_? LIKE classement";
    private static final String CREATE_CLASSEMENT_ARCHIVES = "CREATE TABLE classement_archive_? LIKE classement";
    private static final String INSERT_TEAM_CLASSEMENT = "INSERT INTO classement_? (equipe,team_id,pool_id,year_of_the_standing) VALUE (?,?,?,?)";
    private static final String CHECK_IF_TEAM_EXIST = "SELECT * FROM classement_? WHERE team_id=?";
    private static final String UPDATE_TEAM_CLASSEMENT = "UPDATE classement_? SET equipe=? WHERE team_id=?";
    private static final String GET_CLASSEMENT_BY_POOL_ID = "SELECT * FROM classement_? ORDER BY points DESC, but DESC";
    private static final String UPDATE_DAILY_STATS = "UPDATE classement_? SET pj=?,but=?,passe=?,points=?,moyenne=points/pj, hier=?,semaine=?,mois=? WHERE team_id=?";
    private static final String UPDATE_DIFFERENCE = "UPDATE classement_? SET difference=? WHERE team_id=?";
    private static final String GET_FIRST_TEAM_PTS = "SELECT * FROM classement_? ORDER BY points DESC LIMIT 1";
    private static final String GET_NEXT_TEAM_PTS = "SELECT * FROM classement_? ORDER BY points DESC LIMIT ?";
    private static final String UPDATE_DAILY_MOUVEMENT = "UPDATE classement_? SET mouvement=? WHERE team_id=?";
    private static final String UPDATE_STATS_AFTER_TRADE = "UPDATE classement_? SET pj=?,but=?,passe=?,points=?,moyenne=? WHERE team_id=?";
	private static final String ARCHIVE_CLASSEMENT_LAST_YEAR = "INSERT INTO classement_archive_? (`equipe`,`pj`,`but`,`passe`,`points`,`hier`,`semaine`,`mois`,`moyenne`,`difference`,`team_id`,`pool_id`,`year_of_the_standing`) SELECT equipe,pj,but,passe,points,hier,semaine,mois,moyenne,difference,team_id,pool_id, year_of_the_standing FROM classement_?";
	private static final String RESET_CLASSEMENT_FOR_NEW_YEARS = "UPDATE classement_? SET pj=0,but=0,passe=0,points=0,hier=0,semaine=0,mois=0,moyenne=0,difference=0,year_of_the_standing=?,mouvement=0";
	private static final String GET_CLASSEMENT_LAST_YEARS_INVERSE_FROM_ARCHIVE = "SELECT * FROM classement_archive_? WHERE year_of_the_standing=? ORDER BY points ASC, moyenne ASC, but ASC";

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
    public void insertTeamInClassement(String nomDuTeam, int teamID, int poolID, int years) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_TEAM_CLASSEMENT, false, poolID, nomDuTeam, teamID, poolID, years);
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
	    preparedStatement = initialisationRequetePreparee(connexion, CHECK_IF_TEAM_EXIST, false, poolID, teamID);
	    ResultSet rs = preparedStatement.executeQuery();

	    if (rs.first()) {
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
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_TEAM_CLASSEMENT, false, poolID, nomDuTeam, teamID);
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
	List<Long> team_id = new ArrayList<Long>();
	List<String> equipe = new ArrayList<String>();
	List<Long> pj = new ArrayList<Long>();
	List<Long> but = new ArrayList<Long>();
	List<Long> passe = new ArrayList<Long>();
	List<Long> points = new ArrayList<Long>();
	List<Long> hier = new ArrayList<Long>();
	List<Long> semaine = new ArrayList<Long>();
	List<Long> mois = new ArrayList<Long>();
	List<Float> moyenne = new ArrayList<Float>();
	List<Long> difference = new ArrayList<Long>();
	List<Long> mouvement = new ArrayList<Long>();
	Classement mBeanClassement = new Classement();
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_CLASSEMENT_BY_POOL_ID, false, poolId);
	    rs = preparedStatement.executeQuery();

	    while (rs.next()) {

		int m_team_id = (rs.getInt("team_id"));
		team_id.add(Long.valueOf(m_team_id));

		String m_equipe = (rs.getString("equipe"));
		equipe.add(m_equipe);

		int m_pj = (rs.getInt("pj"));
		pj.add(Long.valueOf(m_pj));

		int m_but = (rs.getInt("but"));
		but.add(Long.valueOf(m_but));

		int m_passe = (rs.getInt("passe"));
		passe.add(Long.valueOf(m_passe));

		int m_points = (rs.getInt("points"));
		points.add(Long.valueOf(m_points));

		int m_hier = (rs.getInt("hier"));
		hier.add(Long.valueOf(m_hier));

		int m_semaine = (rs.getInt("semaine"));
		semaine.add(Long.valueOf(m_semaine));

		int m_mois = (rs.getInt("mois"));
		mois.add(Long.valueOf(m_mois));

		int m_mouvement = (rs.getInt("mouvement"));
		mouvement.add(Long.valueOf(m_mouvement));

		float m_moyenne = (rs.getFloat("moyenne"));
		BigDecimal a = new BigDecimal(m_moyenne);
		BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_UP);
		moyenne.add(roundOff.floatValue());

		int m_difference = (rs.getInt("difference"));
		difference.add(Long.valueOf(m_difference));

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
	    mBeanClassement.setMouvement(mouvement);

	    return mBeanClassement;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}

    }

    @Override
    public void updateStat(int poolId, int pj, int but, int passe, int pts, int teamId, int hier, int semaine, int mois) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DAILY_STATS, false, poolId, pj, but, passe, pts, hier, semaine, mois, teamId);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateDifference(int poolId, int position,List<Long> mouvementArray) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	int total_pts_first = 0, total_pts_next = 0;
	int difference, teamId = 0;
	if (position == 1) {
	    difference = 0;
	    try {
		connexion = daoFactory.getConnection();
		preparedStatement = initialisationRequetePreparee(connexion, GET_FIRST_TEAM_PTS, false, poolId);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
		    teamId = rs.getInt("team_id");
		}
		preparedStatement.close();
		preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DIFFERENCE, false, poolId, difference, teamId);
		preparedStatement.execute();

	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } finally {
		fermeturesSilencieuses(rs, preparedStatement, connexion);
	    }

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    String datastoreID = poolId + "_" + teamId;
	    Key mKey = KeyFactory.createKey("Classement_Progression", datastoreID);
	    Key mKeyClassement = KeyFactory.createKey("Classement", String.valueOf(poolId));
	    int mouvement;
	    try {
		Entity entity = datastore.get(mKey);
		Entity entityClassement = datastore.get(mKeyClassement);
		

		Long positionHier = (Long) entity.getProperty("position");
		int positionHierId = positionHier.intValue();
		if (1 < positionHierId) {
		    mouvementArray.set(0, (long) 2);
		    mouvement = 2;
		    entityClassement.setProperty("mouvement", mouvementArray);
		} else if (1 > positionHierId) {
		    mouvementArray.set(0, (long) 1);
		    entityClassement.setProperty("mouvement", mouvementArray);
		    mouvement = 1;
		} else {
		    mouvementArray.set(0, (long) 0);
		    entityClassement.setProperty("mouvement", mouvementArray);
		    mouvement = 0;
		}
		entity.setProperty("position", 1);

		datastore.put(entity);

		try {
		    connexion = daoFactory.getConnection();
		    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DAILY_MOUVEMENT, false, poolId,mouvement,teamId);
		    preparedStatement.execute();
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		 } finally {
			fermeturesSilencieuses(rs, preparedStatement, connexion);
		    }

	    } catch (EntityNotFoundException e) {

	    }

	} else {
	    try {
		connexion = daoFactory.getConnection();
		preparedStatement = initialisationRequetePreparee(connexion, GET_FIRST_TEAM_PTS, false, poolId);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
		    total_pts_first = rs.getInt("points");
		}
		preparedStatement = initialisationRequetePreparee(connexion, GET_NEXT_TEAM_PTS, false, poolId, position);
		rs = preparedStatement.executeQuery();
		if (rs.last()) {
		    total_pts_next = rs.getInt("points");
		    teamId = rs.getInt("team_id");
		}
		difference = total_pts_first - total_pts_next;
		preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DIFFERENCE, false, poolId, difference, teamId);
		preparedStatement.execute();

	    } catch (SQLException e) {
		throw new DAOException(e);
	    } finally {
		fermeturesSilencieuses(rs,preparedStatement, connexion);
	    }

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key mKeyClassement = KeyFactory.createKey("Classement", String.valueOf(poolId));
	    String datastoreID = poolId + "_" + teamId;
	    Key mKey = KeyFactory.createKey("Classement_Progression", datastoreID);
	    int mouvement;
	    try {

		Entity entity = datastore.get(mKey);
		Entity entityClassement = datastore.get(mKeyClassement);

		
		if (mouvementArray == null) {
		    mouvementArray = new ArrayList<Long>();
		}
		List<Long> teamIdArray = (List<Long>) entityClassement.getProperty("team_id");
		Long positionHier = (Long) entity.getProperty("position");
		int positionHierId = positionHier.intValue();
		int indexTeamId = teamIdArray.indexOf(Long.valueOf(teamId));
		if (position < positionHierId) {
		    mouvementArray.set(indexTeamId, (long) 2);
		    entityClassement.setProperty("mouvement", mouvementArray);
		    mouvement = 2;
		} else if (position > positionHierId) {
		    mouvementArray.set(indexTeamId, (long) 1);
		    entityClassement.setProperty("mouvement", mouvementArray);
		    mouvement = 1;
		} else {
		    mouvementArray.set(indexTeamId, (long) 0);
		    entityClassement.setProperty("mouvement", mouvementArray);
		    mouvement = 0;
		}
		entity.setProperty("position", position);

		datastore.put(entity);
		datastore.put(entityClassement);
		
		try {
		    connexion = daoFactory.getConnection();
		    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_DAILY_MOUVEMENT, false, poolId,mouvement,teamId);
		    preparedStatement.execute();
		} catch (SQLException e) {
		    
		    e.printStackTrace();
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		    }

	    } catch (EntityNotFoundException e1) {

	    }

	}

    }

    
    @Override
    public void updateStat(int poolId, int pj, int but, int passe, int pts, int teamId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
    int moyenne =0;
    
    if(pj==0) {
    	moyenne=0;
    } else {
    	moyenne=pts/pj;
    }
    
    
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_STATS_AFTER_TRADE, false, poolId, pj, but, passe, pts,moyenne,teamId);
	    preparedStatement.execute();

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
		    preparedStatement = initialisationRequetePreparee(connexion, ARCHIVE_CLASSEMENT_LAST_YEAR, false, Integer.parseInt(poolID), Integer.parseInt(poolID));
		    preparedStatement.execute();

		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		
		
	}

	@Override
	public void resetClassement(String poolID,String years) {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
		    connexion = daoFactory.getConnection();
		    preparedStatement = initialisationRequetePreparee(connexion, RESET_CLASSEMENT_FOR_NEW_YEARS, false, Integer.parseInt(poolID),years);
		    preparedStatement.execute();

		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermeturesSilencieuses(preparedStatement, connexion);
		}
		
	}

	@Override
	public List<Integer> getClassementLastYear(String poolID,String years) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		List<Integer> classementInverse = new ArrayList<Integer>();
		ResultSet rs=null;
		try {
		    connexion = daoFactory.getConnection();
		    preparedStatement = initialisationRequetePreparee(connexion, GET_CLASSEMENT_LAST_YEARS_INVERSE_FROM_ARCHIVE, false, Integer.parseInt(poolID),years);
		    rs =preparedStatement.executeQuery();
		    
		    while(rs.next()) {
		    	classementInverse.add(rs.getInt("team_id"));
		    }

		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermeturesSilencieuses(rs,preparedStatement, connexion);
		}
		return classementInverse;
	}

}
