package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.beans.TradeBeans;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class TradeMadeDaoImpl implements TradeMadeDao {

    private static final String CREATE_TRADE_MADE = "CREATE TABLE trade_made? LIKE trade_made";
    private static final String CREATE_TRADE_MADE_ARCHIVES = "CREATE TABLE trade_made_archive_? LIKE trade_made";
    private static final String INSERT_TRADE_OFFER_INTO_TRADE_MADE = "INSERT INTO trade_made? SELECT * FROM trade_offer? WHERE _id=?";
    private static final String GET_NUMBER_TRADE_MADE_BY_ME = "SELECT COUNT(*) FROM trade_made? WHERE team_1 =? OR team_2 =?";
    private static final String GET_NUMBER_TRADE_MADE_BY_ALL = "SELECT COUNT(*) FROM trade_made?";
    private static final String GET_TRADE_MADE_BY_ME = "SELECT * FROM trade_made? WHERE team_1 =? OR team_2 =? ORDER BY date_heure";
    private static final String GET_TRADE_MADE_BY_ALL = "SELECT * FROM trade_made? ORDER BY date_heure";
    private static final String SHOW_TRADE_Y = "SELECT * FROM trade_made? WHERE _id=?";
    private DAOFactory daoFactory;

    TradeMadeDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createTradeMadeTable(int poolID) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_TRADE_MADE, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void createTradeMadeArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_TRADE_MADE_ARCHIVES, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void insertTradeMade(int poolId, int trade_id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_TRADE_OFFER_INTO_TRADE_MADE, false, poolId, poolId, trade_id);
	    preparedStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public int getNumberTradeMadeByMe(String poolID, int teamId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	int nbOfferMadeByMe = 0;
	ResultSet rs = null;
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_NUMBER_TRADE_MADE_BY_ME, false, Integer.parseInt(poolID), teamId, teamId);
	    rs = preparedStatement.executeQuery();
	    while (rs.next()) {
		nbOfferMadeByMe = rs.getInt(1);
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}
	return nbOfferMadeByMe;
    }

    @Override
    public TradeBeans getTradeMadeByMe(Pool mBeanPool, String poolID, int teamId, int i) throws DAOException {
	Connection connexion = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	TradeBeans mBean = new TradeBeans();
	List<Integer> trade_id = new ArrayList<Integer>();
	List<String> nom_equipe_trading_to = new ArrayList<String>();
	List<String> nom_equipe_connect = new ArrayList<String>();
	List<String> dateTradeOfferArray = new ArrayList<String>();
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_TRADE_MADE_BY_ME, false, Integer.parseInt(poolID), teamId, teamId);
	    rs = preparedStatement.executeQuery();
	    while (rs.next()) {
		trade_id.add(rs.getInt("_id"));
		dateTradeOfferArray.add(rs.getDate("date_heure").toString());
		String team2Id = (rs.getString("team_2"));
		int team2 = Integer.parseInt(team2Id);
		String team1Id = (rs.getString("team_1"));
		int team1 = Integer.parseInt(team1Id);
		if (teamId == team1) {
		    String nom = getTeamNameFromString(mBeanPool, team2);
		    nom_equipe_trading_to.add(nom);
		    String nom2 = getTeamNameFromString(mBeanPool, team1);
		    nom_equipe_connect.add(nom2);
		} else {
		    String nom = getTeamNameFromString(mBeanPool, team1);
		    nom_equipe_trading_to.add(nom);
		    String nom2 = getTeamNameFromString(mBeanPool, team2);
		    nom_equipe_connect.add(nom2);
		}

		mBean.setTradeOfferId(trade_id);
		mBean.setTradeOfferNameTeamTradeWith(nom_equipe_trading_to);
		mBean.setTradeOfferNameTeamTradeWith2(nom_equipe_connect);
		mBean.setDateTradeOfferArray(dateTradeOfferArray);

	    }

	    return mBean;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}
    }

    @Override
    public int getNumberTradeMadeByAll(String poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	int nbOfferMadeByMe = 0;
	ResultSet rs = null;
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_NUMBER_TRADE_MADE_BY_ALL, false, Integer.parseInt(poolID));
	    rs = preparedStatement.executeQuery();
	    while (rs.next()) {
		nbOfferMadeByMe = rs.getInt(1);
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}
	return nbOfferMadeByMe;
    }

    @Override
    public TradeBeans getTradeMadeByAll(Pool mBeanPool, String poolID, int teamId, int i) throws DAOException {
	Connection connexion = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	TradeBeans mBean = new TradeBeans();
	List<Integer> trade_id = new ArrayList<Integer>();
	List<String> nom_equipe_trading_1 = new ArrayList<String>();
	List<String> nom_equipe_trading_2 = new ArrayList<String>();
	List<String> dateTradeOfferArray = new ArrayList<String>();
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_TRADE_MADE_BY_ALL, false, Integer.parseInt(poolID));
	    rs = preparedStatement.executeQuery();
	    while (rs.next()) {
		trade_id.add(rs.getInt("_id"));
		dateTradeOfferArray.add(rs.getDate("date_heure").toString());
		String team2Id = (rs.getString("team_2"));
		int team2 = Integer.parseInt(team2Id);
		String team1Id = (rs.getString("team_1"));
		int team1 = Integer.parseInt(team1Id);
		String nom1 = getTeamNameFromString(mBeanPool, team1);
		nom_equipe_trading_1.add(nom1);
		String nom2 = getTeamNameFromString(mBeanPool, team2);
		nom_equipe_trading_2.add(nom2);

		mBean.setTradeOfferId(trade_id);
		mBean.setTradeOfferNameTeamTradeWith(nom_equipe_trading_1);
		mBean.setTradeOfferNameTeamTradeWith2(nom_equipe_trading_2);
		mBean.setDateTradeOfferArray(dateTradeOfferArray);

	    }

	    return mBean;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}
    }

    private String getTeamNameFromString(Pool mBeanPool, int team1) {
	String from_temp2 = "Erreur,voir Frank";
	switch (team1) {

	case 1:
	    from_temp2 = mBeanPool.getNomTeam1();
	    break;
	case 2:
	    from_temp2 = mBeanPool.getNomTeam2();
	    break;
	case 3:
	    from_temp2 = mBeanPool.getNomTeam3();
	    break;
	case 4:
	    from_temp2 = mBeanPool.getNomTeam4();
	    break;
	case 5:
	    from_temp2 = mBeanPool.getNomTeam5();
	    break;
	case 6:
	    from_temp2 = mBeanPool.getNomTeam6();
	    break;
	case 7:
	    from_temp2 = mBeanPool.getNomTeam7();
	    break;
	case 8:
	    from_temp2 = mBeanPool.getNomTeam8();
	    break;
	case 9:
	    from_temp2 = mBeanPool.getNomTeam9();
	    break;
	case 10:
	    from_temp2 = mBeanPool.getNomTeam10();
	    break;
	case 11:
	    from_temp2 = mBeanPool.getNomTeam11();
	    break;
	case 12:
	    from_temp2 = mBeanPool.getNomTeam12();
	    break;
	}
	return from_temp2;
    }

    @Override
    public TradeBeans showOfferX(Pool mBeanPool, Utilisateur mBeanUser, int trade_id, PlayersDao playersDao, DraftPickDao draftPickDao) {
	Connection connexion = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	TradeBeans mBean = new TradeBeans();

	ArrayList<String> playersTeamThatOfferTemp = new ArrayList<String>();
	ArrayList<String> draftPickTeamThatOfferTemp = new ArrayList<String>();
	ArrayList<String> playersTeamThatReceivedTemp = new ArrayList<String>();
	ArrayList<String> draftPickTeamThatReceivedTemp = new ArrayList<String>();
	String[] playersTeamThatOffer = null;
	String[] draftPickTeamThatOffer = null;
	String[] playersTeamThatReceived = null;
	String[] draftPickTeamThatReceived = null;
	int nbPlayersTeamThatOffer = 0;
	int nbDraftPickTeamThatOffer = 0;
	int nbPlayersTeamThatReceived = 0;
	int nbDraftPickTeamThatReceived = 0;
	int argentOfferTeamThatTrade = 0;
	int argentOfferTeamThatReceivedOffer = 0;
	String messageOffre = "";
	String nom1 = "hum!!!", nom2 = "hum!!!";
	int total_salaire_team_making_offer=0;
	int total_salaire_team_receiving_offer=0;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SHOW_TRADE_Y, false, Integer.parseInt(mBeanPool.getPoolID()), trade_id);
	    rs = preparedStatement.executeQuery();
	    while (rs.next()) {

		argentOfferTeamThatTrade = rs.getInt("t1_cash");
		argentOfferTeamThatReceivedOffer = rs.getInt("t2_cash");

		int teamLogIn = mBeanUser.getTeamId();

		int team2Id = rs.getInt("team_2");
		int team1Id = rs.getInt("team_1");

		if (teamLogIn == team1Id) {
		    nom2 = getTeamNameFromString(mBeanPool, team2Id);
		    nom1 = getTeamNameFromString(mBeanPool, teamLogIn);
		    String t1j1 = rs.getString("t1j1");
			String t1j2 = rs.getString("t1j2");
			String t1j3 = rs.getString("t1j3");
			String t1j4 = rs.getString("t1j4");
			String t1j5 = rs.getString("t1j5");
			String t1j6 = rs.getString("t1j6");
			String t1j7 = rs.getString("t1j7");
			String t2j1 = rs.getString("t2j1");
			String t2j2 = rs.getString("t2j2");
			String t2j3 = rs.getString("t2j3");
			String t2j4 = rs.getString("t2j4");
			String t2j5 = rs.getString("t2j5");
			String t2j6 = rs.getString("t2j6");
			String t2j7 = rs.getString("t2j7");
			String t1p1 = rs.getString("t1p1");
			String t1p2 = rs.getString("t1p2");
			String t1p3 = rs.getString("t1p3");
			String t2p1 = rs.getString("t2p1");
			String t2p2 = rs.getString("t2p2");
			String t2p3 = rs.getString("t2p3");
			messageOffre = rs.getString("message");

			if (t1j1 != null) {
			    playersTeamThatReceivedTemp.add(t1j1);
			}
			if (t1j2 != null) {
			    playersTeamThatReceivedTemp.add(t1j2);
			}
			if (t1j3 != null) {
			    playersTeamThatReceivedTemp.add(t1j3);
			}
			if (t1j4 != null) {
			    playersTeamThatReceivedTemp.add(t1j4);
			}
			if (t1j5 != null) {
			    playersTeamThatReceivedTemp.add(t1j5);
			}
			if (t1j6 != null) {
			    playersTeamThatReceivedTemp.add(t1j6);
			}
			if (t1j7 != null) {
			    playersTeamThatReceivedTemp.add(t1j7);
			}
			if (t2j1 != null) {
			    playersTeamThatOfferTemp.add(t2j1);
			}
			if (t2j2 != null) {
			    playersTeamThatOfferTemp.add(t2j2);
			}
			if (t2j3 != null) {
			    playersTeamThatOfferTemp.add(t2j3);
			}
			if (t2j4 != null) {
			    playersTeamThatOfferTemp.add(t2j4);
			}
			if (t2j5 != null) {
			    playersTeamThatOfferTemp.add(t2j5);
			}
			if (t2j6 != null) {
			    playersTeamThatOfferTemp.add(t2j6);
			}
			if (t2j7 != null) {
			    playersTeamThatOfferTemp.add(t2j7);
			}
			if (t1p1 != null) {
			    draftPickTeamThatReceivedTemp.add(t1p1);
			}
			if (t1p2 != null) {
			    draftPickTeamThatReceivedTemp.add(t1p2);
			}
			if (t1p3 != null) {
			    draftPickTeamThatReceivedTemp.add(t1p3);
			}
			if (t2p1 != null) {
			    draftPickTeamThatOfferTemp.add(t2p1);
			}
			if (t2p2 != null) {
			    draftPickTeamThatOfferTemp.add(t2p2);
			}
			if (t2p3 != null) {
			    draftPickTeamThatOfferTemp.add(t2p3);
			}
		} else {
		    nom1 = getTeamNameFromString(mBeanPool, teamLogIn);
		    nom2 = getTeamNameFromString(mBeanPool, team1Id);
		    String t1j1 = rs.getString("t1j1");
			String t1j2 = rs.getString("t1j2");
			String t1j3 = rs.getString("t1j3");
			String t1j4 = rs.getString("t1j4");
			String t1j5 = rs.getString("t1j5");
			String t1j6 = rs.getString("t1j6");
			String t1j7 = rs.getString("t1j7");
			String t2j1 = rs.getString("t2j1");
			String t2j2 = rs.getString("t2j2");
			String t2j3 = rs.getString("t2j3");
			String t2j4 = rs.getString("t2j4");
			String t2j5 = rs.getString("t2j5");
			String t2j6 = rs.getString("t2j6");
			String t2j7 = rs.getString("t2j7");
			String t1p1 = rs.getString("t1p1");
			String t1p2 = rs.getString("t1p2");
			String t1p3 = rs.getString("t1p3");
			String t2p1 = rs.getString("t2p1");
			String t2p2 = rs.getString("t2p2");
			String t2p3 = rs.getString("t2p3");
			messageOffre = rs.getString("message");

			if (t1j1 != null) {
			    playersTeamThatOfferTemp.add(t1j1);
			}
			if (t1j2 != null) {
			    playersTeamThatOfferTemp.add(t1j2);
			}
			if (t1j3 != null) {
			    playersTeamThatOfferTemp.add(t1j3);
			}
			if (t1j4 != null) {
			    playersTeamThatOfferTemp.add(t1j4);
			}
			if (t1j5 != null) {
			    playersTeamThatOfferTemp.add(t1j5);
			}
			if (t1j6 != null) {
			    playersTeamThatOfferTemp.add(t1j6);
			}
			if (t1j7 != null) {
			    playersTeamThatOfferTemp.add(t1j7);
			}
			if (t2j1 != null) {
			    playersTeamThatReceivedTemp.add(t2j1);
			}
			if (t2j2 != null) {
			    playersTeamThatReceivedTemp.add(t2j2);
			}
			if (t2j3 != null) {
			    playersTeamThatReceivedTemp.add(t2j3);
			}
			if (t2j4 != null) {
			    playersTeamThatReceivedTemp.add(t2j4);
			}
			if (t2j5 != null) {
			    playersTeamThatReceivedTemp.add(t2j5);
			}
			if (t2j6 != null) {
			    playersTeamThatReceivedTemp.add(t2j6);
			}
			if (t2j7 != null) {
			    playersTeamThatReceivedTemp.add(t2j7);
			}
			if (t1p1 != null) {
			    draftPickTeamThatOfferTemp.add(t1p1);
			}
			if (t1p2 != null) {
			    draftPickTeamThatOfferTemp.add(t1p2);
			}
			if (t1p3 != null) {
			    draftPickTeamThatOfferTemp.add(t1p3);
			}
			if (t2p1 != null) {
			    draftPickTeamThatReceivedTemp.add(t2p1);
			}
			if (t2p2 != null) {
			    draftPickTeamThatReceivedTemp.add(t2p2);
			}
			if (t2p3 != null) {
			    draftPickTeamThatReceivedTemp.add(t2p3);
			}
		}

		

	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}
	playersTeamThatOffer = playersTeamThatOfferTemp.toArray(new String[playersTeamThatOfferTemp.size()]);
	playersTeamThatReceived = playersTeamThatReceivedTemp.toArray(new String[playersTeamThatReceivedTemp.size()]);
	draftPickTeamThatOffer = draftPickTeamThatOfferTemp.toArray(new String[draftPickTeamThatOfferTemp.size()]);
	draftPickTeamThatReceived = draftPickTeamThatReceivedTemp.toArray(new String[draftPickTeamThatReceivedTemp.size()]);

	if (playersTeamThatOffer != null) {
	    nbPlayersTeamThatOffer = playersTeamThatOffer.length;
	}
	if (draftPickTeamThatOffer != null) {
	    nbDraftPickTeamThatOffer = draftPickTeamThatOffer.length;
	}

	if (playersTeamThatReceived != null) {
	    nbPlayersTeamThatReceived = playersTeamThatReceived.length;
	}
	if (draftPickTeamThatReceived != null) {
	    nbDraftPickTeamThatReceived = draftPickTeamThatReceived.length;
	}

	String[] nomMakingOffer = new String[nbPlayersTeamThatOffer];
	String[] nomReceivingOffer = new String[nbPlayersTeamThatReceived];
	String[] RoundPickMakingOffer = new String[nbDraftPickTeamThatOffer];
	String[] RoundPickReceivingOffer = new String[nbDraftPickTeamThatReceived];
	String[] FromPickMakingOffer = new String[nbDraftPickTeamThatOffer];
	String[] FromPickReceivingOffer = new String[nbDraftPickTeamThatReceived];
	int[] salaireMakingOffer = new int[nbPlayersTeamThatOffer];
	int[] salaireReceivingOffer = new int[nbPlayersTeamThatReceived];
	int[] salaireRookieMakingOffer = new int[nbPlayersTeamThatOffer];
	int[] salaireRookieReceivingOffer = new int[nbPlayersTeamThatReceived];
	String[] nomRookieMakingOffer = new String[nbPlayersTeamThatOffer];
	String[] nomRookieReceivingOffer = new String[nbPlayersTeamThatReceived];

	int i = 0;
	int compteurRookie = 0;
	int compteurJoueur = 0;
	if (playersTeamThatOffer != null) {

	    for (String s : playersTeamThatOffer) {
		int toInt = Integer.parseInt(s);
		TradeBeanTemp mBeanTemp = new TradeBeanTemp();
		mBeanTemp = playersDao.getPlayersById(mBeanPool.getPoolID(), toInt, 2);

		if (mBeanTemp.getNomMakingOfferString() != null) {

		    if (mBeanTemp.getClubEcole() == 1) {
			nomRookieMakingOffer[compteurRookie] = mBeanTemp.getNomMakingOfferString();
			salaireRookieMakingOffer[compteurRookie] = mBeanTemp.getTotal_salaire_team_making_offer();

			compteurRookie++;
		    } else {
			nomMakingOffer[compteurJoueur] = mBeanTemp.getNomMakingOfferString();
			salaireMakingOffer[compteurJoueur] = mBeanTemp.getTotal_salaire_team_making_offer();
			total_salaire_team_making_offer = total_salaire_team_making_offer+mBeanTemp.getTotal_salaire_team_making_offer();
			compteurJoueur++;
		    }
		}
	    }
	    i++;

	}

	String[] nomRookieMakingOffer2 = new String[compteurRookie];
	String[] nomMakingOffer2 = new String[compteurJoueur];

	i = 0;
	for (i = 0; i < compteurJoueur; i++) {
	    nomMakingOffer2[i] = nomMakingOffer[i];

	}
	i = 0;
	for (i = 0; i < compteurRookie; i++) {
	    nomRookieMakingOffer2[i] = nomRookieMakingOffer[i];

	}

	// on calcul le total d'Argent des salaire des joueurs de l'Equipe qui recoit et on ajoute leur nom dans un
	// array
	int j = 0;
	int compteurRookieRec = 0;
	int compteurJoueurRec = 0;
	if (playersTeamThatReceived != null) {

	    for (String s : playersTeamThatReceived) {
		int toInt = Integer.parseInt(s);
		TradeBeanTemp mBeanTemp = new TradeBeanTemp();
		mBeanTemp = playersDao.getPlayersById(mBeanPool.getPoolID(), toInt, 2);

		if (mBeanTemp.getNomMakingOfferString() != null) {

		    if (mBeanTemp.getClubEcole() == 1) {
			nomRookieReceivingOffer[compteurRookieRec] = mBeanTemp.getNomMakingOfferString();
			salaireRookieReceivingOffer[compteurRookieRec] = mBeanTemp.getTotal_salaire_team_making_offer();
			compteurRookieRec++;
		    } else {
			nomReceivingOffer[compteurJoueurRec] = mBeanTemp.getNomMakingOfferString();
			salaireReceivingOffer[compteurJoueurRec] = mBeanTemp.getTotal_salaire_team_making_offer();
			total_salaire_team_receiving_offer = total_salaire_team_receiving_offer+mBeanTemp.getTotal_salaire_team_making_offer();
			compteurJoueurRec++;
		    }

		}
	    }

	    j++;

	}

	String[] nomRookieReceivingOffer2 = new String[compteurRookieRec];
	String[] nomReceivingOffer2 = new String[compteurJoueurRec];

	j = 0;
	for (j = 0; j < compteurJoueurRec; j++) {
	    nomReceivingOffer2[j] = nomReceivingOffer[j];

	}
	j = 0;
	for (j = 0; j < compteurRookieRec; j++) {
	    nomRookieReceivingOffer2[j] = nomRookieReceivingOffer[j];

	}

	// roundpick et frompick a faire pour la persistence
	if (draftPickTeamThatOffer != null) {
	    int k = 0;
	    for (String s : draftPickTeamThatOffer) {
		int toInt = Integer.parseInt(s);
		TradeBeanTemp mBeanTemp = new TradeBeanTemp();
		mBeanTemp = draftPickDao.getNameOfTeam(mBeanPool.getPoolID(), toInt, mBeanPool);

		RoundPickMakingOffer[k] = mBeanTemp.getRoundPick();
		FromPickMakingOffer[k] = mBeanTemp.getFromPick();

		k++;

	    }
	}

	if (draftPickTeamThatReceived != null) {
	    int l = 0;
	    for (String s : draftPickTeamThatReceived) {
		int toInt = Integer.parseInt(s);
		TradeBeanTemp mBeanTemp = new TradeBeanTemp();
		mBeanTemp = draftPickDao.getNameOfTeam(mBeanPool.getPoolID(), toInt, mBeanPool);

		RoundPickReceivingOffer[l] = mBeanTemp.getRoundPick();
		FromPickReceivingOffer[l] = mBeanTemp.getFromPick();

		l++;

	    }
	}

	mBean.setMessageOffre(messageOffre);
	mBean.setPickNumMakingOffer(draftPickTeamThatOffer);
	mBean.setPickNumReceivingOffer(draftPickTeamThatReceived);
	mBean.setCashMakingOffer(argentOfferTeamThatTrade);
	mBean.setCashReceivingOffer(argentOfferTeamThatReceivedOffer);
	mBean.setNomMakingOffer(nomMakingOffer2);
	mBean.setNomReceivingOffer(nomReceivingOffer2);
	mBean.setRoundPickMakingOffer(RoundPickMakingOffer);
	mBean.setRoundPickReceivingOffer(RoundPickReceivingOffer);
	mBean.setFromPickMakingOffer(FromPickMakingOffer);
	mBean.setFromPickReceivingOffer(FromPickReceivingOffer);
	mBean.setTrade_id(trade_id);
	mBean.setSalaireMakingOffer(salaireMakingOffer);
	mBean.setSalaireReceivingOffer(salaireReceivingOffer);
	mBean.setSalaireRookieMakingOffer(salaireRookieMakingOffer);
	mBean.setSalaireRookieReceivingOffer(salaireRookieReceivingOffer);
	mBean.setRookieNomMakingOffer(nomRookieMakingOffer2);
	mBean.setRookieNomReceivingOffer(nomRookieReceivingOffer2);
	mBean.setTeamThatTradeName(nom1);
	mBean.setTeamTradeToName(nom2);
	
	// ajout du total de l'argent donné
			int budgetMakingOffer = (total_salaire_team_making_offer - argentOfferTeamThatTrade)-(total_salaire_team_receiving_offer - argentOfferTeamThatReceivedOffer);
			int budgetReceivingOffer = (total_salaire_team_receiving_offer - argentOfferTeamThatReceivedOffer)-(total_salaire_team_making_offer - argentOfferTeamThatTrade); 
			mBean.setBudgetMakingOffer(budgetMakingOffer);
			mBean.setBudgetReceivingOffer(budgetReceivingOffer);

	return mBean;
    }

}
