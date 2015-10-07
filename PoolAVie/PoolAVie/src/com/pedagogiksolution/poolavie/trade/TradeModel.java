package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.poolavie.beans.DraftPick;
import com.pedagogiksolution.poolavie.beans.TeamMakingOffer;
import com.pedagogiksolution.poolavie.beans.TeamReceivingOffer;
import com.pedagogiksolution.poolavie.beans.TradeBeans;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class TradeModel {
    TradeBeans mBean = new TradeBeans();
    TeamMakingOffer mBeanMaking = new TeamMakingOffer();
    TeamReceivingOffer mBeanReceiving = new TeamReceivingOffer();

    public boolean isThereAOfferForMe(HttpServletRequest req) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM trade_offer WHERE team_2=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, m_team_id);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		rs.close();
		mPreparedStatement.close();
		return true;
	    } else {
		rs.close();
		mPreparedStatement.close();
		return false;
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}
	return false;

    }

    public Boolean isThereAOfferMadeByMe(HttpServletRequest req) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM trade_offer WHERE team_1=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, m_team_id);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		rs.close();
		mPreparedStatement.close();
		return true;
	    } else {
		rs.close();
		mPreparedStatement.close();
		return false;
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}
	return false;

    }

    public void getTheOfferThatIReceived(HttpServletRequest req) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	List<Integer> trade_id = new ArrayList<Integer>();
	List<String> nom_equipe_trading_to = new ArrayList<String>();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM trade_offer WHERE team_2=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, m_team_id);
	    rs = mPreparedStatement.executeQuery();

	    while (rs.next()) {
		int t_id = (rs.getInt("_id"));
		trade_id.add(t_id);
		mBean.setTradeOfferId(trade_id);

		String team2Id = (rs.getString("team_1"));
		int team2 = Integer.parseInt(team2Id);
		String nom = getTeamNameFromString(team2);
		nom_equipe_trading_to.add(nom);
		mBean.setTradeOfferNameTeamTradeWith(nom_equipe_trading_to);
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	req.setAttribute("beanAffichageOfferMade", mBean);

    }

    public void getTheOfferThatIMade(HttpServletRequest req) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	List<Integer> trade_id = new ArrayList<Integer>();
	List<String> nom_equipe_trading_to = new ArrayList<String>();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM trade_offer WHERE team_1=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, m_team_id);
	    rs = mPreparedStatement.executeQuery();

	    while (rs.next()) {
		int t_id = (rs.getInt("_id"));
		trade_id.add(t_id);
		mBean.setTradeOfferId(trade_id);

		String team2Id = (rs.getString("team_2"));
		int team2 = Integer.parseInt(team2Id);
		String nom = getTeamNameFromString(team2);
		nom_equipe_trading_to.add(nom);
		mBean.setTradeOfferNameTeamTradeWith(nom_equipe_trading_to);
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	req.setAttribute("beanAffichageOfferMade", mBean);

    }

    private String getTeamNameFromString(int team2) {
	String nom_equipe_qui_trade = null;
	switch (team2) {
	case 0:
	    nom_equipe_qui_trade = "Los Angeles";
	    break;
	case 1:
	    nom_equipe_qui_trade = "Détroit";
	    break;
	case 2:
	    nom_equipe_qui_trade = "Montréal";
	    return nom_equipe_qui_trade;
	case 3:
	    nom_equipe_qui_trade = "Chicago";
	    break;
	case 4:
	    nom_equipe_qui_trade = "New York";
	    break;
	case 5:
	    nom_equipe_qui_trade = "Philadelphie";
	    break;
	case 6:
	    nom_equipe_qui_trade = "Toronto";
	    break;
	case 7:
	    nom_equipe_qui_trade = "St-Louis";
	    break;
	case 8:
	    nom_equipe_qui_trade = "Boston";
	    break;
	case 9:
	    nom_equipe_qui_trade = "Pittsburgh";
	    break;
	}
	return nom_equipe_qui_trade;
    }

    public void getTheTeamsThatTrade(int team_to_trade2, int team_that_trade, HttpServletRequest req) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;

	List<Integer> player_id = new ArrayList<Integer>();
	List<String> nom2 = new ArrayList<String>();
	List<String> team_name2 = new ArrayList<String>();
	List<String> position2 = new ArrayList<String>();
	List<String> years_12 = new ArrayList<String>();
	List<String> years_22 = new ArrayList<String>();
	List<String> years_32 = new ArrayList<String>();
	List<String> years_42 = new ArrayList<String>();
	List<String> years_52 = new ArrayList<String>();
	List<Integer> club_ecole2 = new ArrayList<Integer>();
	List<Integer> points2 = new ArrayList<Integer>();

	List<Integer> player_id3 = new ArrayList<Integer>();
	List<String> nom3 = new ArrayList<String>();
	List<String> team_name3 = new ArrayList<String>();
	List<String> position3 = new ArrayList<String>();
	List<String> years_13 = new ArrayList<String>();
	List<String> years_23 = new ArrayList<String>();
	List<String> years_33 = new ArrayList<String>();
	List<String> years_43 = new ArrayList<String>();
	List<String> years_53 = new ArrayList<String>();
	List<Integer> club_ecole3 = new ArrayList<Integer>();
	List<Integer> points3 = new ArrayList<Integer>();

	conn = dbHelper.open();

	QueryA = "SELECT * FROM players WHERE team_id=? AND years_1>?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, team_that_trade);
	    mPreparedStatement.setInt(2, 1);
	    rs = mPreparedStatement.executeQuery();

	    mBeanMaking.setTeamMakingOfferId(team_that_trade);
	    mBeanReceiving.setTeamReceivingOfferId(team_to_trade2);

	    while (rs.next()) {
		int p_id = (rs.getInt("_id"));
		player_id.add(p_id);
		mBeanMaking.setPlayer_id(player_id);

		String nom = (rs.getString("nom"));
		nom2.add(nom);
		mBeanMaking.setNom(nom2);

		String team_name = (rs.getString("team"));
		team_name2.add(team_name);
		mBeanMaking.setTeam_name(team_name2);

		String position = (rs.getString("position"));
		position2.add(position);
		mBeanMaking.setPosition(position2);

		String years_1 = (rs.getString("years_1"));
		years_12.add(years_1);
		mBeanMaking.setYears_1(years_12);

		String years_2 = (rs.getString("years_2"));
		years_22.add(years_2);
		mBeanMaking.setYears_2(years_22);

		String years_3 = (rs.getString("years_3"));
		years_32.add(years_3);
		mBeanMaking.setYears_3(years_32);

		String years_4 = (rs.getString("years_4"));
		years_42.add(years_4);
		mBeanMaking.setYears_4(years_42);

		String years_5 = (rs.getString("years_5"));
		years_52.add(years_5);
		mBeanMaking.setYears_5(years_52);

		int club_ecole = (rs.getInt("club_ecole"));
		club_ecole2.add(club_ecole);
		mBeanMaking.setClub_ecole(club_ecole2);

		int points = (rs.getInt("pts"));
		points2.add(points);
		mBeanMaking.setPoints(points2);

	    }
	    rs.close();
	    mPreparedStatement.close();
	    req.setAttribute("teamMakingOffer", mBeanMaking);

	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, team_to_trade2);
	    mPreparedStatement.setInt(2, 1);
	    rs = mPreparedStatement.executeQuery();

	    while (rs.next()) {
		int p_id = (rs.getInt("_id"));
		player_id3.add(p_id);
		mBeanReceiving.setPlayer_id(player_id3);

		String nom = (rs.getString("nom"));
		nom3.add(nom);
		mBeanReceiving.setNom(nom3);

		String team_name = (rs.getString("team"));
		team_name3.add(team_name);
		mBeanReceiving.setTeam_name(team_name3);

		String position = (rs.getString("position"));
		position3.add(position);
		mBeanReceiving.setPosition(position3);

		String years_1 = (rs.getString("years_1"));
		years_13.add(years_1);
		mBeanReceiving.setYears_1(years_13);

		String years_2 = (rs.getString("years_2"));
		years_23.add(years_2);
		mBeanReceiving.setYears_2(years_23);

		String years_3 = (rs.getString("years_3"));
		years_33.add(years_3);
		mBeanReceiving.setYears_3(years_33);

		String years_4 = (rs.getString("years_4"));
		years_43.add(years_4);
		mBeanReceiving.setYears_4(years_43);

		String years_5 = (rs.getString("years_5"));
		years_53.add(years_5);
		mBeanReceiving.setYears_5(years_53);

		int club_ecole = (rs.getInt("club_ecole"));
		club_ecole3.add(club_ecole);
		mBeanReceiving.setClub_ecole(club_ecole3);

		int points = (rs.getInt("pts"));
		points3.add(points);
		mBeanReceiving.setPoints(points3);

	    }
	    rs.close();
	    mPreparedStatement.close();
	    req.setAttribute("teamReceivingOffer", mBeanReceiving);

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public void getDraftPick(int team_to_trade, int team_that_trade, HttpServletRequest req) {
	String QueryA, nom_equipe_qui_trade = null;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	DraftPick mBeanDraftPick = new DraftPick();
	List<Integer> round = new ArrayList<Integer>();
	List<String> from = new ArrayList<String>();
	List<Integer> role_echange = new ArrayList<Integer>();
	List<Integer> draft_pick = new ArrayList<Integer>();

	conn = dbHelper.open();

	QueryA = "SELECT * FROM draft_pick_current_year WHERE team_id=? OR team_id=? ORDER BY pick_no";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, team_that_trade);
	    mPreparedStatement.setInt(2, team_to_trade);
	    rs = mPreparedStatement.executeQuery();
	    while (rs.next()) {
		int pick_id = rs.getInt("_id");
		draft_pick.add(pick_id);
		mBeanDraftPick.setDraft_id(draft_pick);

		int team_id = rs.getInt("team_id");

		int pick_no = (rs.getInt("pick_no"));
		round.add(pick_no);
		mBeanDraftPick.setRound(round);

		int team_from = (rs.getInt("original_team_id"));
		switch (team_from) {
		case 0:
		    nom_equipe_qui_trade = "Los Angeles";
		    break;
		case 1:
		    nom_equipe_qui_trade = "Détroit";
		    break;
		case 2:
		    nom_equipe_qui_trade = "Montréal";
		    break;
		case 3:
		    nom_equipe_qui_trade = "Chicago";
		    break;
		case 4:
		    nom_equipe_qui_trade = "New York";
		    break;
		case 5:
		    nom_equipe_qui_trade = "Philadelphie";
		    break;
		case 6:
		    nom_equipe_qui_trade = "Toronto";
		    break;
		case 7:
		    nom_equipe_qui_trade = "St-Louis";
		    break;
		case 8:
		    nom_equipe_qui_trade = "Boston";
		    break;
		case 9:
		    nom_equipe_qui_trade = "Pittsburgh";
		    break;
		}
		from.add(nom_equipe_qui_trade);
		mBeanDraftPick.setFrom(from);

		if (team_that_trade == team_id) {
		    int indicateur = 1;
		    role_echange.add(indicateur);
		    mBeanDraftPick.setRole_echange(role_echange);
		} else {
		    int indicateur = 2;
		    role_echange.add(indicateur);
		    mBeanDraftPick.setRole_echange(role_echange);

		}

	    }
	    rs.close();
	    mPreparedStatement.close();
	    req.setAttribute("draft_pick_for_exchange", mBeanDraftPick);

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public boolean validationTradeEte(HttpServletRequest req) {

	String QueryA, QueryB, QueryC;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;

	int budget_restant_make_offer = 0;
	int manquant_equipe_make_offer = 0;
	int nb_contrat_make_offer = 0;

	int budget_restant_received_offer = 0;
	int manquant_equipe_received_offer = 0;
	int nb_contrat_received_offer = 0;

	int total_salaire_team_making_offer = 0;
	int total_salaire_team_receiving_offer = 0;

	int nbPlayersTeamThatOffer = 0;
	int nbRookieTeamThatOffer = 0;
	int nbDraftPickTeamThatOffer = 0;
	int nbPlayersTeamThatReceived = 0;
	int nbRookieTeamThatReceived = 0;
	int nbDraftPickTeamThatReceived = 0;

	String teamMakingOfferId = req.getParameter("teamMakingOfferId");
	int teamThatTrade = Integer.parseInt(teamMakingOfferId);
	String teamReceivingOfferId = req.getParameter("teamReceivingOfferId");
	int teamToTrade = Integer.parseInt(teamReceivingOfferId);

	String argentOfferTeamThatTradeString = req.getParameter("cash_trade_by");
	int argentOfferTeamThatTrade = Integer.parseInt(argentOfferTeamThatTradeString);

	String argentOfferTeamThatReceivedOfferString = req.getParameter("cash_trade_to");
	int argentOfferTeamThatReceivedOffer = Integer.parseInt(argentOfferTeamThatReceivedOfferString);

	String[] playersTeamThatOffer = req.getParameterValues("players_id_my_team");

	String[] draftPickTeamThatOffer = req.getParameterValues("draft_pick_my_team");

	String[] playersTeamThatReceived = req.getParameterValues("players_id_other_team");
	String[] draftPickTeamThatReceived = req.getParameterValues("draft_pick_other_team");

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

// checking for to many players trade max 7
	if (nbPlayersTeamThatOffer > 7 || nbPlayersTeamThatReceived > 7) {
	    mBean.setCodeErreurOffreTrade(9);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}
// checking for to many draft pick trade max 3
	if (nbDraftPickTeamThatOffer > 3 || nbDraftPickTeamThatReceived > 3) {
	    mBean.setCodeErreurOffreTrade(10);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}

// checking for cash on the two side
	if (argentOfferTeamThatTrade > 0 && argentOfferTeamThatReceivedOffer > 0) {
	    mBean.setCodeErreurOffreTrade(7);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}
// checking if there no players on the two side
	if (nbPlayersTeamThatOffer < 1 && nbPlayersTeamThatReceived < 1) {
	    mBean.setCodeErreurOffreTrade(8);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}

	QueryA = "SELECT * FROM equipes WHERE team_id=?";

	conn = dbHelper.open();

	try {

	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamThatTrade);
	    rs = mPreparedStatement.executeQuery();
	    if (rs.next()) {
		budget_restant_make_offer = rs.getInt("budget_restant");
		manquant_equipe_make_offer = rs.getInt("manquant_equipe");
		nb_contrat_make_offer = rs.getInt("nb_contrat");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamToTrade);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		budget_restant_received_offer = rs.getInt("budget_restant");
		manquant_equipe_received_offer = rs.getInt("manquant_equipe");
		nb_contrat_received_offer = rs.getInt("nb_contrat");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatOffer != null) {
		int i = 0;
		for (String s : playersTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=0";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int salaire_joueur_temp = rs.getInt("years_1");
			total_salaire_team_making_offer = total_salaire_team_making_offer + salaire_joueur_temp;
			String nomMakingOfferString = rs.getString("nom");
			nomMakingOffer[i] = nomMakingOfferString;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatOffer != null) {
		int i = 0;
		for (String s : playersTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=1";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			nbRookieTeamThatOffer = nbRookieTeamThatOffer + 1;
			String nomMakingOfferString = rs.getString("nom");
			nomMakingOffer[i] = nomMakingOfferString;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {
		int i = 0;
		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=0";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			int salaire_joueur_temp = rs.getInt("years_1");
			total_salaire_team_receiving_offer = total_salaire_team_receiving_offer + salaire_joueur_temp;
			String nomReceivingOfferString = rs.getString("nom");
			nomReceivingOffer[i] = nomReceivingOfferString;
			i++;
		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {
		int i = 0;
		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=1";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			nbRookieTeamThatReceived = nbRookieTeamThatReceived + 1;
			String nomReceivingOfferString = rs.getString("nom");
			nomReceivingOffer[i] = nomReceivingOfferString;
			i++;
		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

// check for nombre contrat trop elevé dans une des deux equipes

	    if ((nb_contrat_make_offer + nbPlayersTeamThatReceived - nbPlayersTeamThatOffer - nbRookieTeamThatReceived + nbRookieTeamThatOffer > 12)
		    || (nb_contrat_received_offer + nbPlayersTeamThatOffer - nbPlayersTeamThatReceived + nbRookieTeamThatReceived - nbRookieTeamThatOffer > 12)) {
		mBean.setCodeErreurOffreTrade(1);
		req.setAttribute("messageErreur", mBean);
		return false;

	    }

// check for argent dispo pour faire draft apres echange

	    int new_budget_restant_team_making_offer = budget_restant_make_offer + total_salaire_team_making_offer - total_salaire_team_receiving_offer - argentOfferTeamThatTrade
		    + argentOfferTeamThatReceivedOffer;
	    int new_budget_restant_team_receiving_offer = budget_restant_received_offer + total_salaire_team_receiving_offer - total_salaire_team_making_offer
		    + argentOfferTeamThatTrade - argentOfferTeamThatReceivedOffer;

	    int new_manquant_team_making_offer = manquant_equipe_make_offer + nbPlayersTeamThatOffer - nbPlayersTeamThatReceived + nbRookieTeamThatReceived - nbRookieTeamThatOffer;
	    int new_manquant_team_receiving_offer = manquant_equipe_received_offer + nbPlayersTeamThatReceived - nbPlayersTeamThatOffer - nbRookieTeamThatReceived
		    + nbRookieTeamThatOffer;

	    if (((new_budget_restant_team_making_offer / new_manquant_team_making_offer) < 1000000)
		    || ((new_budget_restant_team_receiving_offer / new_manquant_team_receiving_offer) < 1000000)) {
		mBean.setCodeErreurOffreTrade(2);
		req.setAttribute("messageErreur", mBean);
		return false;
	    }

	    if (draftPickTeamThatOffer != null) {
		int i = 0;
		for (String s : draftPickTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryC = "SELECT * FROM draft_pick_current_year WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryC);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int round_temp = rs.getInt("pick_no");
			int from_temp = rs.getInt("original_team_id");
			String from_temp2 = null;
			switch (from_temp) {
			case 0:
			    from_temp2 = "Los Angeles";
			    break;
			case 1:
			    from_temp2 = "Détroit";
			    break;
			case 2:
			    from_temp2 = "Montréal";
			    break;
			case 3:
			    from_temp2 = "Chicago";
			    break;
			case 4:
			    from_temp2 = "New York";
			    break;
			case 5:
			    from_temp2 = "Philadelphie";
			    break;
			case 6:
			    from_temp2 = "Toronto";
			    break;
			case 7:
			    from_temp2 = "St-Louis";
			    break;
			case 8:
			    from_temp2 = "Boston";
			    break;
			case 9:
			    from_temp2 = "Pittsburgh";
			    break;
			}
			String round_temp2 = Integer.toString(round_temp);

			RoundPickMakingOffer[i] = round_temp2;
			FromPickMakingOffer[i] = from_temp2;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (draftPickTeamThatReceived != null) {
		int i = 0;
		for (String s : draftPickTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryC = "SELECT * FROM draft_pick_current_year WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryC);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int round_temp = rs.getInt("pick_no");
			int from_temp = rs.getInt("original_team_id");
			String from_temp2 = null;
			switch (from_temp) {
			case 0:
			    from_temp2 = "Los Angeles";
			    break;
			case 1:
			    from_temp2 = "Détroit";
			    break;
			case 2:
			    from_temp2 = "Montréal";
			    break;
			case 3:
			    from_temp2 = "Chicago";
			    break;
			case 4:
			    from_temp2 = "New York";
			    break;
			case 5:
			    from_temp2 = "Philadelphie";
			    break;
			case 6:
			    from_temp2 = "Toronto";
			    break;
			case 7:
			    from_temp2 = "St-Louis";
			    break;
			case 8:
			    from_temp2 = "Boston";
			    break;
			case 9:
			    from_temp2 = "Pittsburgh";
			    break;
			}
			String round_temp2 = Integer.toString(round_temp);
			RoundPickReceivingOffer[i] = round_temp2;
			FromPickReceivingOffer[i] = from_temp2;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	mBean.setPlayerIdMakingOffer(playersTeamThatOffer);
	mBean.setPlayerIdReceivingOffer(playersTeamThatReceived);
	mBean.setPickNumMakingOffer(draftPickTeamThatOffer);
	mBean.setPickNumReceivingOffer(draftPickTeamThatReceived);
	mBean.setCashMakingOffer(argentOfferTeamThatTrade);
	mBean.setCashReceivingOffer(argentOfferTeamThatReceivedOffer);
	mBean.setNomMakingOffer(nomMakingOffer);
	mBean.setNomReceivingOffer(nomReceivingOffer);
	mBean.setRoundPickMakingOffer(RoundPickMakingOffer);
	mBean.setRoundPickReceivingOffer(RoundPickReceivingOffer);
	mBean.setFromPickMakingOffer(FromPickMakingOffer);
	mBean.setFromPickReceivingOffer(FromPickReceivingOffer);
	mBean.setTeamThatTrade(teamThatTrade);
	mBean.setTeamTradeTo(teamToTrade);

	req.getSession().setAttribute("tradeOfferBean", mBean);

	return true;
    }

    public boolean validationTradeAnnée(HttpServletRequest req) {
	// TODO Auto-generated method stub
	return false;
    }

    public boolean validationTradeDraft(HttpServletRequest req) {
	String QueryA, QueryB, QueryC;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;

	int budget_restant_make_offer = 0;
	int manquant_equipe_make_offer = 0;
	int nb_contrat_make_offer = 0;

	int budget_restant_received_offer = 0;
	int manquant_equipe_received_offer = 0;
	int nb_contrat_received_offer = 0;

	int total_salaire_team_making_offer = 0;
	int total_salaire_team_receiving_offer = 0;

	int nbPlayersTeamThatOffer = 0;
	int nbRookieTeamThatOffer = 0;
	int nbDraftPickTeamThatOffer = 0;
	int nbPlayersTeamThatReceived = 0;
	int nbRookieTeamThatReceived = 0;
	int nbDraftPickTeamThatReceived = 0;

	String teamMakingOfferId = req.getParameter("teamMakingOfferId");
	int teamThatTrade = Integer.parseInt(teamMakingOfferId);
	String teamReceivingOfferId = req.getParameter("teamReceivingOfferId");
	int teamToTrade = Integer.parseInt(teamReceivingOfferId);

	String argentOfferTeamThatTradeString = req.getParameter("cash_trade_by");
	int argentOfferTeamThatTrade = Integer.parseInt(argentOfferTeamThatTradeString);

	String argentOfferTeamThatReceivedOfferString = req.getParameter("cash_trade_to");
	int argentOfferTeamThatReceivedOffer = Integer.parseInt(argentOfferTeamThatReceivedOfferString);

	String[] playersTeamThatOffer = req.getParameterValues("players_id_my_team");

	String[] draftPickTeamThatOffer = req.getParameterValues("draft_pick_my_team");

	String[] playersTeamThatReceived = req.getParameterValues("players_id_other_team");
	String[] draftPickTeamThatReceived = req.getParameterValues("draft_pick_other_team");

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

// checking for to many players trade max 7
	if (nbPlayersTeamThatOffer > 7 || nbPlayersTeamThatReceived > 7) {
	    mBean.setCodeErreurOffreTrade(9);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}
// checking for to many draft pick trade max 3
	if (nbDraftPickTeamThatOffer > 3 || nbDraftPickTeamThatReceived > 3) {
	    mBean.setCodeErreurOffreTrade(10);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}

// checking for cash on the two side
	if (argentOfferTeamThatTrade > 0 && argentOfferTeamThatReceivedOffer > 0) {
	    mBean.setCodeErreurOffreTrade(7);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}
// checking if there no players on the two side
	if (nbPlayersTeamThatOffer < 1 && nbPlayersTeamThatReceived < 1) {
	    mBean.setCodeErreurOffreTrade(8);
	    req.setAttribute("messageErreur", mBean);
	    return false;
	}

	QueryA = "SELECT * FROM equipes WHERE team_id=?";

	conn = dbHelper.open();

	try {

	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamThatTrade);
	    rs = mPreparedStatement.executeQuery();
	    if (rs.next()) {
		budget_restant_make_offer = rs.getInt("budget_restant");
		manquant_equipe_make_offer = rs.getInt("manquant_equipe");
		nb_contrat_make_offer = rs.getInt("nb_contrat");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamToTrade);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		budget_restant_received_offer = rs.getInt("budget_restant");
		manquant_equipe_received_offer = rs.getInt("manquant_equipe");
		nb_contrat_received_offer = rs.getInt("nb_contrat");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatOffer != null) {
		int i = 0;
		for (String s : playersTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=0";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int salaire_joueur_temp = rs.getInt("years_1");
			total_salaire_team_making_offer = total_salaire_team_making_offer + salaire_joueur_temp;
			String nomMakingOfferString = rs.getString("nom");
			nomMakingOffer[i] = nomMakingOfferString;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatOffer != null) {
		int i = 0;
		for (String s : playersTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=1";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			nbRookieTeamThatOffer = nbRookieTeamThatOffer + 1;
			String nomMakingOfferString = rs.getString("nom");
			nomMakingOffer[i] = nomMakingOfferString;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {
		int i = 0;
		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=0";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			int salaire_joueur_temp = rs.getInt("years_1");
			total_salaire_team_receiving_offer = total_salaire_team_receiving_offer + salaire_joueur_temp;
			String nomReceivingOfferString = rs.getString("nom");
			nomReceivingOffer[i] = nomReceivingOfferString;
			i++;
		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {
		int i = 0;
		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=1";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			nbRookieTeamThatReceived = nbRookieTeamThatReceived + 1;
			String nomReceivingOfferString = rs.getString("nom");
			nomReceivingOffer[i] = nomReceivingOfferString;
			i++;
		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

// check for nombre contrat trop elevé dans une des deux equipes

	    if ((nb_contrat_make_offer + nbPlayersTeamThatReceived - nbPlayersTeamThatOffer - nbRookieTeamThatReceived + nbRookieTeamThatOffer > 12)
		    || (nb_contrat_received_offer + nbPlayersTeamThatOffer - nbPlayersTeamThatReceived + nbRookieTeamThatReceived - nbRookieTeamThatOffer > 12)) {
		mBean.setCodeErreurOffreTrade(1);
		req.setAttribute("messageErreur", mBean);
		return false;

	    }

// check for argent dispo pour faire draft apres echange

	    int new_budget_restant_team_making_offer = budget_restant_make_offer + total_salaire_team_making_offer - total_salaire_team_receiving_offer - argentOfferTeamThatTrade
		    + argentOfferTeamThatReceivedOffer;
	    int new_budget_restant_team_receiving_offer = budget_restant_received_offer + total_salaire_team_receiving_offer - total_salaire_team_making_offer
		    + argentOfferTeamThatTrade - argentOfferTeamThatReceivedOffer;

	    int new_manquant_team_making_offer = manquant_equipe_make_offer + nbPlayersTeamThatOffer - nbPlayersTeamThatReceived + nbRookieTeamThatReceived - nbRookieTeamThatOffer;
	    int new_manquant_team_receiving_offer = manquant_equipe_received_offer + nbPlayersTeamThatReceived - nbPlayersTeamThatOffer - nbRookieTeamThatReceived
		    + nbRookieTeamThatOffer;

	    if (((new_budget_restant_team_making_offer / new_manquant_team_making_offer) < 1000000)
		    || ((new_budget_restant_team_receiving_offer / new_manquant_team_receiving_offer) < 1000000)) {
		mBean.setCodeErreurOffreTrade(2);
		req.setAttribute("messageErreur", mBean);
		return false;
	    }

	    if (draftPickTeamThatOffer != null) {
		int i = 0;
		for (String s : draftPickTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryC = "SELECT * FROM draft_pick_current_year WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryC);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int round_temp = rs.getInt("pick_no");
			int from_temp = rs.getInt("original_team_id");
			String from_temp2 = null;
			switch (from_temp) {
			case 0:
			    from_temp2 = "Los Angeles";
			    break;
			case 1:
			    from_temp2 = "Détroit";
			    break;
			case 2:
			    from_temp2 = "Montréal";
			    break;
			case 3:
			    from_temp2 = "Chicago";
			    break;
			case 4:
			    from_temp2 = "New York";
			    break;
			case 5:
			    from_temp2 = "Philadelphie";
			    break;
			case 6:
			    from_temp2 = "Toronto";
			    break;
			case 7:
			    from_temp2 = "St-Louis";
			    break;
			case 8:
			    from_temp2 = "Boston";
			    break;
			case 9:
			    from_temp2 = "Pittsburgh";
			    break;
			}
			String round_temp2 = Integer.toString(round_temp);

			RoundPickMakingOffer[i] = round_temp2;
			FromPickMakingOffer[i] = from_temp2;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (draftPickTeamThatReceived != null) {
		int i = 0;
		for (String s : draftPickTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryC = "SELECT * FROM draft_pick_current_year WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryC);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int round_temp = rs.getInt("pick_no");
			int from_temp = rs.getInt("original_team_id");
			String from_temp2 = null;
			switch (from_temp) {
			case 0:
			    from_temp2 = "Los Angeles";
			    break;
			case 1:
			    from_temp2 = "Détroit";
			    break;
			case 2:
			    from_temp2 = "Montréal";
			    break;
			case 3:
			    from_temp2 = "Chicago";
			    break;
			case 4:
			    from_temp2 = "New York";
			    break;
			case 5:
			    from_temp2 = "Philadelphie";
			    break;
			case 6:
			    from_temp2 = "Toronto";
			    break;
			case 7:
			    from_temp2 = "St-Louis";
			    break;
			case 8:
			    from_temp2 = "Boston";
			    break;
			case 9:
			    from_temp2 = "Pittsburgh";
			    break;
			}
			String round_temp2 = Integer.toString(round_temp);
			RoundPickReceivingOffer[i] = round_temp2;
			FromPickReceivingOffer[i] = from_temp2;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	mBean.setPlayerIdMakingOffer(playersTeamThatOffer);
	mBean.setPlayerIdReceivingOffer(playersTeamThatReceived);
	mBean.setPickNumMakingOffer(draftPickTeamThatOffer);
	mBean.setPickNumReceivingOffer(draftPickTeamThatReceived);
	mBean.setCashMakingOffer(argentOfferTeamThatTrade);
	mBean.setCashReceivingOffer(argentOfferTeamThatReceivedOffer);
	mBean.setNomMakingOffer(nomMakingOffer);
	mBean.setNomReceivingOffer(nomReceivingOffer);
	mBean.setRoundPickMakingOffer(RoundPickMakingOffer);
	mBean.setRoundPickReceivingOffer(RoundPickReceivingOffer);
	mBean.setFromPickMakingOffer(FromPickMakingOffer);
	mBean.setFromPickReceivingOffer(FromPickReceivingOffer);
	mBean.setTeamThatTrade(teamThatTrade);
	mBean.setTeamTradeTo(teamToTrade);

	req.getSession().setAttribute("tradeOfferBean", mBean);

	return true;
    }

    public void persistOffer(HttpServletRequest req) {
	mBean = (TradeBeans) req.getSession().getAttribute("tradeOfferBean");
	String[] playersIdMakingOffer = mBean.getPlayerIdMakingOffer();
	String[] playersIdReceivingOffer = mBean.getPlayerIdReceivingOffer();
	String[] pickMakingOffer = mBean.getPickNumMakingOffer();
	String[] pickReceivingOffer = mBean.getPickNumReceivingOffer();
	int cashMakingOffer = mBean.getCashMakingOffer();
	int cashReceivingOffer = mBean.getCashReceivingOffer();
	int teamMakingOfferId = mBean.getTeamThatTrade();
	int teamReceivingOfferId = mBean.getTeamTradeTo();

	String t1j1 = null, t1j2 = null, t1j3 = null, t1j4 = null, t1j5 = null, t1j6 = null, t1j7 = null, t2j1 = null, t2j2 = null, t2j3 = null, t2j4 = null, t2j5 = null, t2j6 = null, t2j7 = null, t1p1 = null, t1p2 = null, t1p3 = null, t2p1 = null, t2p2 = null, t2p3 = null;
	int number_of_player_trade_by = 0;
	int number_of_pick_trade_by = 0;
	int number_of_player_trade_to = 0;
	int number_of_pick_trade_to = 0;

	if (playersIdMakingOffer != null) {
	    number_of_player_trade_by = playersIdMakingOffer.length;
	}
	if (pickMakingOffer != null) {
	    number_of_pick_trade_by = pickMakingOffer.length;
	}
	if (playersIdReceivingOffer != null) {
	    number_of_player_trade_to = playersIdReceivingOffer.length;
	}
	if (pickReceivingOffer != null) {
	    number_of_pick_trade_to = pickReceivingOffer.length;
	}

	if (playersIdMakingOffer != null) {
	    switch (number_of_player_trade_by) {

	    case 1:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = null;
		t1j3 = null;
		t1j4 = null;
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 2:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = playersIdMakingOffer[1];
		t1j3 = null;
		t1j4 = null;
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 3:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = playersIdMakingOffer[1];
		t1j3 = playersIdMakingOffer[2];
		t1j4 = null;
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 4:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = playersIdMakingOffer[1];
		t1j3 = playersIdMakingOffer[2];
		t1j4 = playersIdMakingOffer[3];
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 5:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = playersIdMakingOffer[1];
		t1j3 = playersIdMakingOffer[2];
		t1j4 = playersIdMakingOffer[3];
		t1j5 = playersIdMakingOffer[4];
		t1j6 = null;
		t1j7 = null;

		break;
	    case 6:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = playersIdMakingOffer[1];
		t1j3 = playersIdMakingOffer[2];
		t1j4 = playersIdMakingOffer[3];
		t1j5 = playersIdMakingOffer[4];
		t1j6 = playersIdMakingOffer[5];
		t1j7 = null;

		break;
	    case 7:
		t1j1 = playersIdMakingOffer[0];
		t1j2 = playersIdMakingOffer[1];
		t1j3 = playersIdMakingOffer[2];
		t1j4 = playersIdMakingOffer[3];
		t1j5 = playersIdMakingOffer[4];
		t1j6 = playersIdMakingOffer[5];
		t1j7 = playersIdMakingOffer[6];
		break;

	    }

	}

	if (pickMakingOffer != null) {
	    switch (number_of_pick_trade_by) {
	    case 1:
		t1p1 = pickMakingOffer[0];
		t1p2 = null;
		t1p3 = null;

		break;
	    case 2:
		t1p1 = pickMakingOffer[0];
		t1p2 = pickMakingOffer[1];
		t1p3 = null;

		break;
	    case 3:
		t1p1 = pickMakingOffer[0];
		t1p2 = pickMakingOffer[1];
		t1p3 = pickMakingOffer[2];

		break;

	    }

	}

	if (playersIdReceivingOffer != null) {
	    switch (number_of_player_trade_to) {

	    case 1:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = null;
		t2j3 = null;
		t2j4 = null;
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 2:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = playersIdReceivingOffer[1];
		t2j3 = null;
		t2j4 = null;
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 3:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = playersIdReceivingOffer[1];
		t2j3 = playersIdReceivingOffer[2];
		t2j4 = null;
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 4:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = playersIdReceivingOffer[1];
		t2j3 = playersIdReceivingOffer[2];
		t2j4 = playersIdReceivingOffer[3];
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 5:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = playersIdReceivingOffer[1];
		t2j3 = playersIdReceivingOffer[2];
		t2j4 = playersIdReceivingOffer[3];
		t2j5 = playersIdReceivingOffer[4];
		t2j6 = null;
		t2j7 = null;

		break;
	    case 6:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = playersIdReceivingOffer[1];
		t2j3 = playersIdReceivingOffer[2];
		t2j4 = playersIdReceivingOffer[3];
		t2j5 = playersIdReceivingOffer[4];
		t2j6 = playersIdReceivingOffer[5];
		t2j7 = null;

		break;
	    case 7:
		t2j1 = playersIdReceivingOffer[0];
		t2j2 = playersIdReceivingOffer[1];
		t2j3 = playersIdReceivingOffer[2];
		t2j4 = playersIdReceivingOffer[3];
		t2j5 = playersIdReceivingOffer[4];
		t2j6 = playersIdReceivingOffer[5];
		t2j7 = playersIdReceivingOffer[6];
		break;

	    }

	}

	if (pickReceivingOffer != null) {
	    switch (number_of_pick_trade_to) {
	    case 1:
		t2p1 = pickReceivingOffer[0];
		t2p2 = null;
		t2p3 = null;

		break;
	    case 2:
		t2p1 = pickReceivingOffer[0];
		t2p2 = pickReceivingOffer[1];
		t2p3 = null;

		break;
	    case 3:
		t2p1 = pickReceivingOffer[0];
		t2p2 = pickReceivingOffer[1];
		t2p3 = pickReceivingOffer[2];

		break;

	    }

	}

	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;

	conn = dbHelper.open();

	QueryA = "INSERT INTO trade_offer (team_1, team_2, t1j1, t1j2, t1j3, t1j4, t1j5,"
		+ " t1j6, t1j7, t2j1, t2j2, t2j3, t2j4, t2j5, t2j6, t2j7, t1p1, t1p2, t1p3, t2p1, t2p2, t2p3,t1_cash, t2_cash,periode_echange,annee)"
		+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamMakingOfferId);
	    mPreparedStatement.setInt(2, teamReceivingOfferId);
	    mPreparedStatement.setString(3, t1j1);
	    mPreparedStatement.setString(4, t1j2);
	    mPreparedStatement.setString(5, t1j3);
	    mPreparedStatement.setString(6, t1j4);
	    mPreparedStatement.setString(7, t1j5);
	    mPreparedStatement.setString(8, t1j6);
	    mPreparedStatement.setString(9, t1j7);
	    mPreparedStatement.setString(10, t2j1);
	    mPreparedStatement.setString(11, t2j2);
	    mPreparedStatement.setString(12, t2j3);
	    mPreparedStatement.setString(13, t2j4);
	    mPreparedStatement.setString(14, t2j5);
	    mPreparedStatement.setString(15, t2j6);
	    mPreparedStatement.setString(16, t2j7);
	    mPreparedStatement.setString(17, t1p1);
	    mPreparedStatement.setString(18, t1p2);
	    mPreparedStatement.setString(19, t1p3);
	    mPreparedStatement.setString(20, t2p1);
	    mPreparedStatement.setString(21, t2p2);
	    mPreparedStatement.setString(22, t2p3);
	    mPreparedStatement.setInt(23, cashMakingOffer);
	    mPreparedStatement.setInt(24, cashReceivingOffer);
	    mPreparedStatement.setInt(25, 0);
	    mPreparedStatement.setInt(26, 2016);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public void annulerOffre(HttpServletRequest req) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);

	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;

	conn = dbHelper.open();

	QueryA = "DELETE FROM trade_offer WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, trade_id);
	    mPreparedStatement.executeUpdate();

	} catch (SQLException e) {

	} finally {
	    dbHelper.close(conn);
	}

    }

    public void showOfferNumberX(HttpServletRequest req) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);

	String QueryA, QueryB, QueryC;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement = null;
	ResultSet rs = null;
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

	conn = dbHelper.open();

	QueryA = "SELECT * FROM trade_offer WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, trade_id);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {

		argentOfferTeamThatTrade = rs.getInt("t1_cash");
		argentOfferTeamThatReceivedOffer = rs.getInt("t2_cash");

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
	    rs.close();
	    mPreparedStatement.close();

	} catch (SQLException e) {

	} finally {
	    dbHelper.close(conn);
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

	conn = dbHelper.open();

	try {
	    if (playersTeamThatOffer != null) {
		int i = 0;
		for (String s : playersTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=?";

		    mPreparedStatement = conn.prepareStatement(QueryB);

		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			String nomMakingOfferString = rs.getString("nom");
			nomMakingOffer[i] = nomMakingOfferString;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {
		int i = 0;
		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			String nomReceivingOfferString = rs.getString("nom");
			nomReceivingOffer[i] = nomReceivingOfferString;
			i++;
		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (draftPickTeamThatOffer != null) {
		int i = 0;
		String from_temp2 = null;
		for (String s : draftPickTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryC = "SELECT * FROM draft_pick_current_year WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryC);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int round_temp = rs.getInt("pick_no");
			int from_temp = rs.getInt("original_team_id");

			switch (from_temp) {
			case 0:
			    from_temp2 = "Los Angeles";
			    break;
			case 1:
			    from_temp2 = "Détroit";
			    break;
			case 2:
			    from_temp2 = "Montréal";
			    break;
			case 3:
			    from_temp2 = "Chicago";
			    break;
			case 4:
			    from_temp2 = "New York";
			    break;
			case 5:
			    from_temp2 = "Philadelphie";
			    break;
			case 6:
			    from_temp2 = "Toronto";
			    break;
			case 7:
			    from_temp2 = "St-Louis";
			    break;
			case 8:
			    from_temp2 = "Boston";
			    break;
			case 9:
			    from_temp2 = "Pittsburgh";
			    break;
			}
			String round_temp2 = Integer.toString(round_temp);

			RoundPickMakingOffer[i] = round_temp2;
			FromPickMakingOffer[i] = from_temp2;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (draftPickTeamThatReceived != null) {
		int i = 0;
		String from_temp2 = null;
		for (String s : draftPickTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryC = "SELECT * FROM draft_pick_current_year WHERE _id=?";
		    mPreparedStatement = conn.prepareStatement(QueryC);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int round_temp = rs.getInt("pick_no");
			int from_temp = rs.getInt("original_team_id");
			switch (from_temp) {
			case 0:
			    from_temp2 = "Los Angeles";
			    break;
			case 1:
			    from_temp2 = "Détroit";
			    break;
			case 2:
			    from_temp2 = "Montréal";
			    break;
			case 3:
			    from_temp2 = "Chicago";
			    break;
			case 4:
			    from_temp2 = "New York";
			    break;
			case 5:
			    from_temp2 = "Philadelphie";
			    break;
			case 6:
			    from_temp2 = "Toronto";
			    break;
			case 7:
			    from_temp2 = "St-Louis";
			    break;
			case 8:
			    from_temp2 = "Boston";
			    break;
			case 9:
			    from_temp2 = "Pittsburgh";
			    break;
			}
			String round_temp2 = Integer.toString(round_temp);
			RoundPickReceivingOffer[i] = round_temp2;
			FromPickReceivingOffer[i] = from_temp2;
			i++;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	mBean.setPickNumMakingOffer(draftPickTeamThatOffer);
	mBean.setPickNumReceivingOffer(draftPickTeamThatReceived);
	mBean.setCashMakingOffer(argentOfferTeamThatTrade);
	mBean.setCashReceivingOffer(argentOfferTeamThatReceivedOffer);
	mBean.setNomMakingOffer(nomMakingOffer);
	mBean.setNomReceivingOffer(nomReceivingOffer);
	mBean.setRoundPickMakingOffer(RoundPickMakingOffer);
	mBean.setRoundPickReceivingOffer(RoundPickReceivingOffer);
	mBean.setFromPickMakingOffer(FromPickMakingOffer);
	mBean.setFromPickReceivingOffer(FromPickReceivingOffer);
	mBean.setTrade_id(trade_id);

	req.getSession().setAttribute("tradeOfferBean", mBean);

    }

    public Boolean checkIfTradeIsStillPossible(HttpServletRequest req) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);

	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String t1j1 = null, t1j2 = null, t1j3 = null, t1j4 = null, t1j5 = null, t1j6 = null, t1j7 = null, t2j1 = null, t2j2 = null, t2j3 = null, t2j4 = null, t2j5 = null, t2j6 = null, t2j7 = null, t1p1 = null, t1p2 = null, t1p3 = null, t2p1 = null, t2p2 = null, t2p3 = null, team1 = null, team2 = null;
	int casht1 = 0, casht2 = 0;
	ArrayList<String> playersTeamThatOfferTemp = new ArrayList<String>();
	ArrayList<String> playersTeamThatReceivedTemp = new ArrayList<String>();
	String[] playersTeamThatOffer = null;
	String[] playersTeamThatReceived = null;
	
	
	conn = dbHelper.open();

	String QueryA;

	QueryA = "SELECT * FROM trade_offer WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, trade_id);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {

		t1j1 = rs.getString("t1j1");
		t1j2 = rs.getString("t1j2");
		t1j3 = rs.getString("t1j3");
		t1j4 = rs.getString("t1j4");
		t1j5 = rs.getString("t1j5");
		t1j6 = rs.getString("t1j6");
		t1j7 = rs.getString("t1j7");
		t2j1 = rs.getString("t2j1");
		t2j2 = rs.getString("t2j2");
		t2j3 = rs.getString("t2j3");
		t2j4 = rs.getString("t2j4");
		t2j5 = rs.getString("t2j5");
		t2j6 = rs.getString("t2j6");
		t2j7 = rs.getString("t2j7");
		t1p1 = rs.getString("t1p1");
		t1p2 = rs.getString("t1p2");
		t1p3 = rs.getString("t1p3");
		t2p1 = rs.getString("t2p1");
		t2p2 = rs.getString("t2p2");
		t2p3 = rs.getString("t2p3");
		casht1 = rs.getInt("t1_cash");
		casht2 = rs.getInt("t2_cash");
		team1 = rs.getString("team_1");
		team2 = rs.getString("team_2");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (t1j1 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j1, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j1);
	    }
	    if (t1j2 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j2, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j2);
	    }
	    if (t1j3 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j3, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j3);
	    }
	    if (t1j4 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j4, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j4);
	    }
	    if (t1j5 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j5, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j5);
	    }
	    if (t1j6 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j6, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j6);
	    }
	    if (t1j7 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team1, t1j7, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatOfferTemp.add(t1j7);
	    }
	    if (t2j1 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j1, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatReceivedTemp.add(t2j1);
	    }
	    if (t2j2 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j2, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatReceivedTemp.add(t2j2);
	    }
	    if (t2j3 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j3, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatReceivedTemp.add(t2j3);
	    }
	    if (t2j4 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j4, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatReceivedTemp.add(t2j4);
	    }
	    if (t2j5 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j5, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatReceivedTemp.add(t2j5);
	    }
	    if (t2j6 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j6, conn);
		if (!isStillInTeam) {
		    return false;
		}
		playersTeamThatReceivedTemp.add(t2j6);
	    }
	    if (t2j7 != null) {
		Boolean isStillInTeam = checkIfplayerStillInTeam(team2, t2j7, conn);
		if (!isStillInTeam) {
		    return false;
		}
	    }
	    
	    if (t1p1 != null) {
		Boolean isStillInPick = checkIfPickStillInTeam(team1, t1p1, conn);
		if (!isStillInPick) {
		    return false;
		}

	    }
	   
	    if (t1p2 != null) {
		Boolean isStillInPick = checkIfPickStillInTeam(team1, t1p2, conn);
		if (!isStillInPick) {
		    return false;
		}

	    }
	    if (t1p3 != null) {
		Boolean isStillInPick = checkIfPickStillInTeam(team1, t1p3, conn);
		if (!isStillInPick) {
		    return false;
		}

	    }
	    if (t2p1 != null) {
		Boolean isStillInPick = checkIfPickStillInTeam(team1, t2p1, conn);
		if (!isStillInPick) {
		    return false;
		}

	    }
	    if (t2p2 != null) {
		Boolean isStillInPick = checkIfPickStillInTeam(team1, t2p2, conn);
		if (!isStillInPick) {
		    return false;
		}

	    }
	    if (t2p3 != null) {
		Boolean isStillInPick = checkIfPickStillInTeam(team1, t2p3, conn);
		if (!isStillInPick) {
		    return false;
		}

	    }

	} catch (SQLException e) {

	} finally {
	    dbHelper.close(conn);
	}

	playersTeamThatOffer = playersTeamThatOfferTemp.toArray(new String[playersTeamThatOfferTemp.size()]);
	playersTeamThatReceived = playersTeamThatReceivedTemp.toArray(new String[playersTeamThatReceivedTemp.size()]);

	boolean checkIfTradeIsStillValidateByRule = checkIfTradeIsStillValidateByRule(req, team1, team2, playersTeamThatOffer, playersTeamThatReceived, casht1, casht2);

	if (!checkIfTradeIsStillValidateByRule) {
	    return false;
	}

	return true;
    }

    private boolean checkIfTradeIsStillValidateByRule(HttpServletRequest req, String team1, String team2, String[] playersTeamThatOffer, String[] playersTeamThatReceived,
	    int casht1, int casht2) {
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String QueryA, QueryB;
	int teamThatTrade = Integer.parseInt(team1);
	int teamToTrade = Integer.parseInt(team2);
	int budget_restant_make_offer = 0, manquant_equipe_make_offer = 0, nb_contrat_make_offer = 0, budget_restant_received_offer = 0, manquant_equipe_received_offer = 0, nb_contrat_received_offer = 0;
	int total_salaire_team_making_offer = 0, total_salaire_team_receiving_offer = 0, nbPlayersTeamThatReceived = 0, nbPlayersTeamThatOffer = 0;
	int nbRookieTeamThatOffer = 0;
	int nbRookieTeamThatReceived = 0;

	QueryA = "SELECT * FROM equipes WHERE team_id=?";

	conn = dbHelper.open();

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamThatTrade);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		budget_restant_make_offer = rs.getInt("budget_restant");
		manquant_equipe_make_offer = rs.getInt("manquant_equipe");
		nb_contrat_make_offer = rs.getInt("nb_contrat");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamToTrade);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		budget_restant_received_offer = rs.getInt("budget_restant");
		manquant_equipe_received_offer = rs.getInt("manquant_equipe");
		nb_contrat_received_offer = rs.getInt("nb_contrat");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatOffer != null) {
		nbPlayersTeamThatOffer = playersTeamThatOffer.length;
	    }

	    if (playersTeamThatReceived != null) {
		nbPlayersTeamThatReceived = playersTeamThatReceived.length;
	    }

	    if (playersTeamThatOffer != null) {

		for (String s : playersTeamThatOffer) {
		    
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=0";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			int salaire_joueur_temp = rs.getInt("years_1");
			total_salaire_team_making_offer = total_salaire_team_making_offer + salaire_joueur_temp;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();
	    if (playersTeamThatOffer != null) {
		for (String s : playersTeamThatOffer) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=1";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();

		    if (rs.next()) {
			nbRookieTeamThatOffer = nbRookieTeamThatOffer + 1;

		    }
		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {

		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=0";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			int salaire_joueur_temp = rs.getInt("years_1");
			total_salaire_team_receiving_offer = total_salaire_team_receiving_offer + salaire_joueur_temp;

		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (playersTeamThatReceived != null) {
		for (String s : playersTeamThatReceived) {
		    int toInt = Integer.parseInt(s);
		    QueryB = "SELECT * FROM players WHERE _id=? AND club_ecole=1";
		    mPreparedStatement = conn.prepareStatement(QueryB);
		    mPreparedStatement.setInt(1, toInt);
		    rs = mPreparedStatement.executeQuery();
		    if (rs.next()) {
			nbRookieTeamThatReceived = nbRookieTeamThatReceived + 1;

		    }

		}
	    }
	    rs.close();
	    mPreparedStatement.close();

	    // check for nombre contrat trop elevé dans une des deux equipes

	    if ((nb_contrat_make_offer + nbPlayersTeamThatReceived - nbPlayersTeamThatOffer - nbRookieTeamThatReceived + nbRookieTeamThatOffer > 12)
		    || (nb_contrat_received_offer + nbPlayersTeamThatOffer - nbPlayersTeamThatReceived + nbRookieTeamThatReceived - nbRookieTeamThatOffer > 12)) {
		mBean.setCodeErreurOffreTrade(1);
		req.setAttribute("messageErreur", mBean);
		return false;

	    }

	    // check for argent dispo pour faire draft apres echange

	    int new_budget_restant_team_making_offer = budget_restant_make_offer + total_salaire_team_making_offer - total_salaire_team_receiving_offer - casht1 + casht2;
	    int new_budget_restant_team_receiving_offer = budget_restant_received_offer + total_salaire_team_receiving_offer - total_salaire_team_making_offer + casht2 - casht1;

	    int new_manquant_team_making_offer = manquant_equipe_make_offer + nbPlayersTeamThatOffer - nbPlayersTeamThatReceived + nbRookieTeamThatReceived - nbRookieTeamThatOffer;
	    int new_manquant_team_receiving_offer = manquant_equipe_received_offer + nbPlayersTeamThatReceived - nbPlayersTeamThatOffer - nbRookieTeamThatReceived
		    + nbRookieTeamThatOffer;

	    if (((new_budget_restant_team_making_offer / new_manquant_team_making_offer) < 1000000)
		    || ((new_budget_restant_team_receiving_offer / new_manquant_team_receiving_offer) < 1000000)) {
		mBean.setCodeErreurOffreTrade(2);
		req.setAttribute("messageErreur", mBean);
		return false;
	    }

	} catch (SQLException e) {

	} finally {
	    dbHelper.close(conn);
	}

	return true;
    }

    private Boolean checkIfPickStillInTeam(String team, String identifiant, Connection conn) {
	int teamId = Integer.parseInt(team);
	int pickId = Integer.parseInt(identifiant);

	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String QueryA;

	QueryA = "SELECT * FROM draft_pick_current_year WHERE team_id=? AND _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamId);
	    mPreparedStatement.setInt(2, pickId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		return true;
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return false;

    }

    private Boolean checkIfplayerStillInTeam(String team, String identifiant, Connection conn) {
	int teamId = Integer.parseInt(team);
	int playerId = Integer.parseInt(identifiant);

	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String QueryA;

	QueryA = "SELECT * FROM players WHERE team_id=? AND _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamId);
	    mPreparedStatement.setInt(2, playerId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		return true;
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return false;
    }

    public void makeTrade(HttpServletRequest req) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);
	String t1j1 = null, t1j2 = null, t1j3 = null, t1j4 = null, t1j5 = null, t1j6 = null, t1j7 = null, t2j1 = null, t2j2 = null, t2j3 = null, t2j4 = null, t2j5 = null, t2j6 = null, t2j7 = null, t1p1 = null, t1p2 = null, t1p3 = null, t2p1 = null, t2p2 = null, t2p3 = null;
	int casht1 = 0, casht2 = 0;
	String team1 = null, team2 = null;
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;

	conn = dbHelper.open();

	QueryA = "SELECT * FROM trade_offer WHERE _id=?";
	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, trade_id);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {

		t1j1 = rs.getString("t1j1");
		t1j2 = rs.getString("t1j2");
		t1j3 = rs.getString("t1j3");
		t1j4 = rs.getString("t1j4");
		t1j5 = rs.getString("t1j5");
		t1j6 = rs.getString("t1j6");
		t1j7 = rs.getString("t1j7");
		t2j1 = rs.getString("t2j1");
		t2j2 = rs.getString("t2j2");
		t2j3 = rs.getString("t2j3");
		t2j4 = rs.getString("t2j4");
		t2j5 = rs.getString("t2j5");
		t2j6 = rs.getString("t2j6");
		t2j7 = rs.getString("t2j7");
		t1p1 = rs.getString("t1p1");
		t1p2 = rs.getString("t1p2");
		t1p3 = rs.getString("t1p3");
		t2p1 = rs.getString("t2p1");
		t2p2 = rs.getString("t2p2");
		t2p3 = rs.getString("t2p3");
		casht1 = rs.getInt("t1_cash");
		casht2 = rs.getInt("t2_cash");
		team1 = rs.getString("team_1");
		team2 = rs.getString("team_2");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (t1j1 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j1);

	    }
	    if (t1j2 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j2);
	    }
	    if (t1j3 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j3);
	    }
	    if (t1j4 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j4);
	    }
	    if (t1j5 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j5);
	    }
	    if (t1j6 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j6);
	    }
	    if (t1j7 != null) {
		movePlayersFromFirstToSecondTeam(team1, team2, t1j7);
	    }
	    if (t2j1 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j1);
	    }
	    if (t2j2 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j2);
	    }
	    if (t2j3 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j3);
	    }
	    if (t2j4 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j4);
	    }
	    if (t2j5 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j5);
	    }
	    if (t2j6 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j6);
	    }
	    if (t2j7 != null) {
		movePlayersFromFirstToSecondTeam(team2, team1, t2j7);
	    }
	    if (t1p1 != null) {
		movePickFromFirstToSecondTeam(team2,t1p1);
	    }
	    if (t1p2 != null) {
		movePickFromFirstToSecondTeam(team2,t1p1);
	    }
	    if (t1p3 != null) {
		movePickFromFirstToSecondTeam(team2,t1p1);
	    }
	    if (t2p1 != null) {
		movePickFromFirstToSecondTeam(team1,t2p1);
	    }
	    if (t2p2 != null) {
		movePickFromFirstToSecondTeam(team1,t2p2);
	    }
	    if (t2p3 != null) {
		movePickFromFirstToSecondTeam(team1,t2p3);
	    }
	    if (casht1 > 0) {
		movCashFromTeamtoArgentRecu(team1, team2, casht1);
	    }
	    if (casht2 > 0) {
		movCashFromTeamtoArgentRecu(team2, team1, casht2);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    private void movCashFromTeamtoArgentRecu(String team1, String team2, int cash) {
	String QueryA,QueryB;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	int teamId1 = Integer.parseInt(team1);
	int teamId2 = Integer.parseInt(team2);

	conn = dbHelper.open();
	
	QueryA="UPDATE equipes SET max_salaire_begin=max_salaire_begin-?,budget_restant=budget_restant-?,moy_sal_restant_draft=budget_restant/manquant_equipe WHERE team_id=?";
	QueryB="UPDATE equipes SET max_salaire_begin=max_salaire_begin+?,budget_restant=budget_restant+?,moy_sal_restant_draft=budget_restant/manquant_equipe WHERE team_id=?";

	 try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, cash);
	    mPreparedStatement.setInt(2, cash);
	    mPreparedStatement.setInt(3, teamId1);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();
	    
	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, cash);
	    mPreparedStatement.setInt(2, cash);
	    mPreparedStatement.setInt(3, teamId2);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}
	    
	
	
    }

    private void movePickFromFirstToSecondTeam(String team, String round) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	int teamId1 = Integer.parseInt(team);
	int roundId2 = Integer.parseInt(round);

	conn = dbHelper.open();
	
	QueryA = "UPDATE draft_pick_current_year SET team_id=? WHERE _id=?";
	
	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamId1);
	    mPreparedStatement.setInt(2, roundId2);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();
	    
	   
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    private void movePlayersFromFirstToSecondTeam(String team1, String team2, String playerId) {
	String QueryA, QueryB, QueryC, QueryC2, QueryD, QueryE, QueryF, QueryD2, QueryE2, QueryF2;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	int teamId1 = Integer.parseInt(team1);
	int teamId2 = Integer.parseInt(team2);
	int playerId2 = Integer.parseInt(playerId);
	int salaire = 0;
	int club_ecole = 0;
	String position = null;
	conn = dbHelper.open();

	QueryA = "UPDATE players SET team_id=? WHERE _id=?";
	QueryB = "SELECT years_1,club_ecole,position FROM players WHERE _id=?";
	QueryC = "UPDATE equipes SET total_salaire_now=total_salaire_now-?,budget_restant=budget_restant+?,manquant_equipe=manquant_equipe+1,nb_equipe=nb_equipe-1,"
		+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat-1,nb_attaquant=nb_attaquant-1,manquant_att=manquant_att+1 WHERE team_id=?";
	QueryD = "UPDATE equipes SET total_salaire_now=total_salaire_now-?,budget_restant=budget_restant+?,manquant_equipe=manquant_equipe+1,nb_equipe=nb_equipe-1,"
		+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat-1,nb_defenseur=nb_defenseur-1,manquant_def=manquant_def+1 WHERE team_id=?";
	QueryE = "UPDATE equipes SET total_salaire_now=total_salaire_now-?,budget_restant=budget_restant+?,manquant_equipe=manquant_equipe+1,nb_equipe=nb_equipe-1,"
		+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat-1,nb_gardien=nb_gardien-1,manquant_gardien=manquant_gardien+1 WHERE team_id=?";
	QueryF = "UPDATE equipes SET nb_rookie=nb_rookie-1,manquant_recrue=manquant_recrue+1 WHERE team_id=?";

	QueryC2 = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,manquant_equipe=manquant_equipe-1,nb_equipe=nb_equipe+1,"
		+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat+1,nb_attaquant=nb_attaquant+1,manquant_att=manquant_att-1 WHERE team_id=?";
	QueryD2 = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,manquant_equipe=manquant_equipe-1,nb_equipe=nb_equipe+1,"
		+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat+1,nb_defenseur=nb_defenseur+1,manquant_def=manquant_def-1 WHERE team_id=?";
	QueryE2 = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,manquant_equipe=manquant_equipe-1,nb_equipe=nb_equipe+1,"
		+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat+1,nb_gardien=nb_gardien+1,manquant_gardien=manquant_gardien-1 WHERE team_id=?";

	QueryF2 = "UPDATE equipes SET nb_rookie=nb_rookie+1,manquant_recrue=manquant_recrue-1 WHERE team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, teamId2);
	    mPreparedStatement.setInt(2, playerId2);
	    mPreparedStatement.executeUpdate();

	    mPreparedStatement.close();

	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, playerId2);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		club_ecole = rs.getInt("club_ecole");
		salaire = rs.getInt("years_1");
		position = rs.getString("position");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (club_ecole == 0 && position.equalsIgnoreCase("attaquant")) {
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryC);
		mPreparedStatement.setInt(1, salaire);
		mPreparedStatement.setInt(2, salaire);
		mPreparedStatement.setInt(3, teamId1);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryC2);
		mPreparedStatement.setInt(1, salaire);
		mPreparedStatement.setInt(2, salaire);
		mPreparedStatement.setInt(3, teamId2);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();

	    } else if (club_ecole == 0 && position.equalsIgnoreCase("defenseur")) {
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryD);
		mPreparedStatement.setInt(1, salaire);
		mPreparedStatement.setInt(2, salaire);
		mPreparedStatement.setInt(3, teamId1);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryD2);
		mPreparedStatement.setInt(1, salaire);
		mPreparedStatement.setInt(2, salaire);
		mPreparedStatement.setInt(3, teamId2);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();

	    } else if (club_ecole == 0 && position.equalsIgnoreCase("gardien")) {
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryE);
		mPreparedStatement.setInt(1, salaire);
		mPreparedStatement.setInt(2, salaire);
		mPreparedStatement.setInt(3, teamId1);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryE2);
		mPreparedStatement.setInt(1, salaire);
		mPreparedStatement.setInt(2, salaire);
		mPreparedStatement.setInt(3, teamId2);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();

	    } else {
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryF);
		mPreparedStatement.setInt(1, teamId1);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryF2);
		mPreparedStatement.setInt(1, teamId2);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();

	    }

	} catch (SQLException e) {

	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public void persistTrade(HttpServletRequest req) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	
	conn=dbHelper.open();
	
	QueryA = "INSERT INTO trade_made SELECT * FROM trade_offer WHERE _id=?";
	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, trade_id);
	    mPreparedStatement.executeUpdate();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }
 
}
