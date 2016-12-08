package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TradeOfferDaoImpl implements TradeOfferDao {

    private static final String CREATE_TRADE_OFFER = "CREATE TABLE trade_offer? LIKE trade_offer";
    private static final String CREATE_TRADE_OFFER_ARCHIVES = "CREATE TABLE trade_offer_archive_? LIKE trade_offer";
    private static final String INSERT_TRADE_OFFER = "INSERT INTO trade_offer? (team_1, team_2, t1j1, t1j2, t1j3, t1j4, t1j5,"
		+ " t1j6, t1j7, t2j1, t2j2, t2j3, t2j4, t2j5, t2j6, t2j7, t1p1, t1p2, t1p3, t2p1, t2p2, t2p3,t1_cash," +
		" t2_cash,periode_echange,annee,message)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private DAOFactory daoFactory;

    TradeOfferDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createTradeOfferTable(int poolID) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_TRADE_OFFER, false, poolID);
	    preparedStatement.execute();
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public void createTradeOfferArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_TRADE_OFFER_ARCHIVES, false, poolID);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    
    @Override
    public void insertTradeOffer(String poolID, int teamMakingOfferId, int teamReceivingOfferId, String t1j1, String t1j2, String t1j3, String t1j4, String t1j5, String t1j6, String t1j7, String t2j1, String t2j2, String t2j3, String t2j4, String t2j5, String t2j6, String t2j7, String t1p1, String t1p2, String t1p3, String t2p1, String t2p2, String t2p3, int cashMakingOffer, int cashReceivingOffer, int i,
	    int annee, String messageOffre) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_TRADE_OFFER, false, poolID,teamMakingOfferId, teamReceivingOfferId, t1j1, t1j2, t1j3, t1j4, t1j5,t1j6, t1j7, t2j1, t2j2, t2j3, t2j4, t2j5, t2j6, t2j7, t1p1, t1p2, t1p3, t2p1, t2p2, t2p3,cashMakingOffer,cashReceivingOffer,i,annee,messageOffre);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

  

   

}
