package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SalaireDaoImpl implements SalaireDao {

    private static final String CREATE_SALAIRE = "CREATE TABLE salaire? LIKE salaire";
    private static final String INSERT_SALAIRE = "INSERT INTO salaire? SELECT * FROM salaire";
	private static final String GET_SALAIRE_TABLE = "SELECT * FROM salaire? WHERE position=? AND points=?";
    private DAOFactory daoFactory;

    SalaireDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createSalaireTable(int poolID) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_SALAIRE, false, poolID);
	    preparedStatement.execute();
	    
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_SALAIRE, false, poolID);
	    preparedStatement.execute();
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

	@Override
	public int getSalaireTable(int poolId, String m_position, int m_pts) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs;
		int position = 0;
		int salaire=0;
		
		switch(m_position) {
		case "attaquant": position=1;
		break;
		case "defenseur": position=2;
		break;
		case "gardien": position=3;
		break;
		
		}
		try {
		    connexion = daoFactory.getConnection();
		    preparedStatement = initialisationRequetePreparee(connexion, GET_SALAIRE_TABLE, false, poolId,position,m_pts);
		    rs =preparedStatement.executeQuery();
		    
		    while (rs.next()) {
				
				salaire = rs.getInt("salaire");
				
			}
		    
		    	    
		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermeturesSilencieuses(preparedStatement, connexion);
		}
		return salaire;
	}

   

}
