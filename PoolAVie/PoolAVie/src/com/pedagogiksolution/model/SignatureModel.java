package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class SignatureModel {
	List<Long> players_id = new ArrayList<Long>();
	List<String> nom2 = new ArrayList<String>();
	List<String> teamOfPlayer = new ArrayList<String>();
	List<Long> pj = new ArrayList<Long>();
	List<Long> but_victoire = new ArrayList<Long>();
	List<Long> aide_overtime = new ArrayList<Long>();
	List<Long> blanchissage = new ArrayList<Long>();
	List<Long> pts = new ArrayList<Long>();
	List<String> years_1 = new ArrayList<String>();
	List<String> years_2 = new ArrayList<String>();
	List<String> years_3 = new ArrayList<String>();
	List<String> years_4 = new ArrayList<String>();
	List<String> years_5 = new ArrayList<String>();
	List<Long> salaire_contrat = new ArrayList<Long>();
	private PlayersDao playersDao;

	public SignatureModel(PlayersDao playersDao) {
		this.playersDao = playersDao;
	}

	public void putPlayersThatCanBeSignInBean(HttpServletRequest req, SalaireDao salaireDao) {

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int poolId = mBeanUser.getPoolId();

		playersDao.getPlayersThatCanBeSign(teamId, poolId, req, salaireDao);

	}

	public void preparationRachatApresSaison(HttpServletRequest req) {
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int poolId = mBeanUser.getPoolId();
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();

		playersDao.getPlayersThatCanBeRachatAfterSeason(teamId, poolId, req, cycleAnnuel);

	}

	public void preparationRookieBackToClubEcole(HttpServletRequest req) {
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int poolId = mBeanUser.getPoolId();

		playersDao.getRookieThatCanDropInClubEcoleAfterSeason(teamId, poolId, req);
	}

	@SuppressWarnings("unchecked")
	public void signatureAfterDraft(HttpServletRequest req) {
		String nombreAnneeSignature = req.getParameter("nombreAnneeSignature");
		int numberOfYear = 0;
		if (nombreAnneeSignature != null) {
			numberOfYear = Integer.parseInt(nombreAnneeSignature);
		} else {
			req.setAttribute("messageErreurs", "Vous n'avez pas choisi de notre d'année au contrat");
			return;
		}

		String draft_player_id = req.getParameter("draft_player_id");
		String salaire = req.getParameter("salaire");
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int poolId = mBeanUser.getPoolId();
		String datastoreID = poolId + "_" + teamId;

		Key equipeKey = KeyFactory.createKey("Equipe", datastoreID);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// A: on change la valeur des years_x dans la bdd players_x
		playersDao.signPlayerAfterDraft(teamId, poolId, draft_player_id, salaire, numberOfYear);

		// B: on modifie les datatstore attaquant/def/goal/
		String position = req.getParameter("position");

		switch (position) {

		case "attaquant":
			String jspSessionName = "Attaquant" + teamId;
			String datastoreId = poolId + "_" + teamId;

			Attaquant mBeanAttaquant = new Attaquant();

			datastore = DatastoreServiceFactory.getDatastoreService();
			Key clefDatastore = KeyFactory.createKey("Attaquant", datastoreId);
			try {

				Entity mEntity = datastore.get(clefDatastore);
				players_id = (List<Long>) mEntity.getProperty("players_id");
				int playersIdPosition = players_id.indexOf(Long.valueOf(draft_player_id));

				years_1 = (ArrayList<String>) mEntity.getProperty("years_1");

				years_2 = (ArrayList<String>) mEntity.getProperty("years_2");
				years_3 = (ArrayList<String>) mEntity.getProperty("years_3");
				years_4 = (ArrayList<String>) mEntity.getProperty("years_4");
				years_5 = (ArrayList<String>) mEntity.getProperty("years_5");

				switch (numberOfYear) {
				case 1:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, "X");
					years_3.set(playersIdPosition, "X");
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");

					break;
				case 2:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, "X");
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");

					break;
				case 3:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");
					break;
				case 4:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, salaire);
					years_5.set(playersIdPosition, "X");

					break;
				case 5:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, salaire);
					years_5.set(playersIdPosition, salaire);

					break;
				}

				mEntity.setProperty("years_1", years_1);
				mEntity.setProperty("years_2", years_2);
				mEntity.setProperty("years_3", years_3);
				mEntity.setProperty("years_4", years_4);
				mEntity.setProperty("years_5", years_5);

				datastore.put(mEntity);

				mBeanAttaquant = (Attaquant) req.getSession().getAttribute(jspSessionName);

				mBeanAttaquant.setYears_1(years_1);
				mBeanAttaquant.setYears_2(years_2);
				mBeanAttaquant.setYears_3(years_3);
				mBeanAttaquant.setYears_4(years_4);
				mBeanAttaquant.setYears_5(years_5);

				req.getSession().setAttribute(jspSessionName, mBeanAttaquant);

				Entity equipeEntity = datastore.get(equipeKey);
				Equipe mBeanEquipe = new Equipe();
				mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(equipeEntity, mBeanEquipe);

				mBeanEquipe.setNb_contrat(mBeanEquipe.getNb_contrat() + 1);
				
				if (cycleAnnuel == 10) {
					mBeanEquipe.setNb_attaquant(mBeanEquipe.getNb_attaquant() + 1);
					mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() + 1);
					mBeanEquipe.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now() + Integer.parseInt(salaire));
					mBeanEquipe.setBudget_restant(mBeanEquipe.getBudget_restant() - Integer.parseInt(salaire));
					mBeanEquipe.setManquant_att(mBeanEquipe.getManquant_att() - 1);
					mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() - 1);
					if (mBeanEquipe.getManquant_equipe() != 0) {
						mBeanEquipe.setMoy_sal_restant_draft(
								mBeanEquipe.getBudget_restant() / mBeanEquipe.getManquant_equipe());
					}

				}
				Entity entityEquipe = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, datastoreID);
				datastore.put(entityEquipe);

			} catch (EntityNotFoundException e) {

			}
			break;

		case "defenseur":
			String jspSessionName2 = "Defenseur" + teamId;
			String datastoreId2 = poolId + "_" + teamId;

			Defenseur mBeanDefenseur = new Defenseur();

			Key clefDatastore2 = KeyFactory.createKey("Defenseur", datastoreId2);
			try {
				// si existe, aucun EntityNotFoundException, donc on
				// recupere l'info pour tester password
				Entity mEntity = datastore.get(clefDatastore2);
				players_id = (List<Long>) mEntity.getProperty("players_id");
				int playersIdPosition = players_id.indexOf(Long.valueOf(draft_player_id));

				years_1 = (ArrayList<String>) mEntity.getProperty("years_1");
				years_2 = (ArrayList<String>) mEntity.getProperty("years_2");
				years_3 = (ArrayList<String>) mEntity.getProperty("years_3");
				years_4 = (ArrayList<String>) mEntity.getProperty("years_4");
				years_5 = (ArrayList<String>) mEntity.getProperty("years_5");

				switch (numberOfYear) {
				case 1:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, "JA");
					years_3.set(playersIdPosition, "X");
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");

					break;
				case 2:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, "X");
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");

					break;
				case 3:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");
					break;
				case 4:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, salaire);
					years_5.set(playersIdPosition, "X");

					break;
				case 5:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, salaire);
					years_5.set(playersIdPosition, salaire);

					break;
				}

				mEntity.setProperty("years_1", years_1);
				mEntity.setProperty("years_2", years_2);
				mEntity.setProperty("years_3", years_3);
				mEntity.setProperty("years_4", years_4);
				mEntity.setProperty("years_5", years_5);

				datastore.put(mEntity);

				mBeanDefenseur = (Defenseur) req.getSession().getAttribute(jspSessionName2);

				mBeanDefenseur.setYears_1(years_1);
				mBeanDefenseur.setYears_2(years_2);
				mBeanDefenseur.setYears_3(years_3);
				mBeanDefenseur.setYears_4(years_4);
				mBeanDefenseur.setYears_5(years_5);

				req.getSession().setAttribute(jspSessionName2, mBeanDefenseur);

				Entity equipeEntity = datastore.get(equipeKey);
				Equipe mBeanEquipe = new Equipe();
				mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(equipeEntity, mBeanEquipe);

				mBeanEquipe.setNb_contrat(mBeanEquipe.getNb_contrat() + 1);
				if (cycleAnnuel == 10) {
					mBeanEquipe.setNb_defenseur(mBeanEquipe.getNb_defenseur() + 1);
					mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() + 1);
					mBeanEquipe.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now() + Integer.parseInt(salaire));
					mBeanEquipe.setBudget_restant(mBeanEquipe.getBudget_restant() - Integer.parseInt(salaire));
					mBeanEquipe.setManquant_def(mBeanEquipe.getManquant_def() - 1);
					mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() - 1);
					if (mBeanEquipe.getManquant_equipe() != 0) {
						mBeanEquipe.setMoy_sal_restant_draft(
								mBeanEquipe.getBudget_restant() / mBeanEquipe.getManquant_equipe());
					}
				}

				Entity entityEquipe = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, datastoreID);
				datastore.put(entityEquipe);

			} catch (EntityNotFoundException e) {

			}
			break;
		case "gardien":
			String jspSessionName3 = "Gardien" + teamId;
			String datastoreId3 = poolId + "_" + teamId;

			Gardien mBeanGardien = new Gardien();
			datastore = DatastoreServiceFactory.getDatastoreService();
			Key clefDatastore3 = KeyFactory.createKey("Gardien", datastoreId3);
			try {
				// si existe, aucun EntityNotFoundException, donc on
				// recupere l'info pour tester password
				Entity mEntity = datastore.get(clefDatastore3);
				players_id = (List<Long>) mEntity.getProperty("players_id");
				int playersIdPosition = players_id.indexOf(Long.valueOf(draft_player_id));

				years_1 = (ArrayList<String>) mEntity.getProperty("years_1");
				years_2 = (ArrayList<String>) mEntity.getProperty("years_2");
				years_3 = (ArrayList<String>) mEntity.getProperty("years_3");
				years_4 = (ArrayList<String>) mEntity.getProperty("years_4");
				years_5 = (ArrayList<String>) mEntity.getProperty("years_5");

				switch (numberOfYear) {
				case 1:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, "JA");
					years_3.set(playersIdPosition, "X");
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");

					break;
				case 2:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, "X");
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");

					break;
				case 3:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, "X");
					years_5.set(playersIdPosition, "X");
					break;
				case 4:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, salaire);
					years_5.set(playersIdPosition, "X");

					break;
				case 5:
					years_1.set(playersIdPosition, salaire);
					years_2.set(playersIdPosition, salaire);
					years_3.set(playersIdPosition, salaire);
					years_4.set(playersIdPosition, salaire);
					years_5.set(playersIdPosition, salaire);

					break;
				}

				mEntity.setProperty("years_1", years_1);
				mEntity.setProperty("years_2", years_2);
				mEntity.setProperty("years_3", years_3);
				mEntity.setProperty("years_4", years_4);
				mEntity.setProperty("years_5", years_5);

				datastore.put(mEntity);

				mBeanGardien = (Gardien) req.getSession().getAttribute(jspSessionName3);

				mBeanGardien.setYears_1(years_1);
				mBeanGardien.setYears_2(years_2);
				mBeanGardien.setYears_3(years_3);
				mBeanGardien.setYears_4(years_4);
				mBeanGardien.setYears_5(years_5);

				req.getSession().setAttribute(jspSessionName3, mBeanGardien);

				Entity equipeEntity = datastore.get(equipeKey);
				Equipe mBeanEquipe = new Equipe();
				mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(equipeEntity, mBeanEquipe);

				mBeanEquipe.setNb_contrat(mBeanEquipe.getNb_contrat() + 1);
				if (cycleAnnuel == 10) {
					mBeanEquipe.setNb_gardien(mBeanEquipe.getNb_gardien() + 1);
					mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() + 1);
					mBeanEquipe.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now() + Integer.parseInt(salaire));
					mBeanEquipe.setBudget_restant(mBeanEquipe.getBudget_restant() - Integer.parseInt(salaire));
					mBeanEquipe.setManquant_gardien(mBeanEquipe.getManquant_gardien() - 1);
					mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() - 1);
					if (mBeanEquipe.getManquant_equipe() != 0) {
						mBeanEquipe.setMoy_sal_restant_draft(
								mBeanEquipe.getBudget_restant() / mBeanEquipe.getManquant_equipe());
					}
				}

				Entity entityEquipe = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, datastoreID);
				datastore.put(entityEquipe);

			} catch (EntityNotFoundException e) {

			}
			break;

		}

	}

	public Boolean checkIfSignatureIsPossible(HttpServletRequest req) {
		Long nb_contrat = null;
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		String nomClef = poolID + "_" + teamId;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		Entity mEntity;
		try {
			mEntity = datastore.get(mKey);
			nb_contrat = (Long) mEntity.getProperty("nb_contrat");
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (nb_contrat >= 12) {
			return false;
		} else {
			return true;
		}

	}

	public void rachatApresSaison(HttpServletRequest req) {

		// on retire argent de equipe

		Players mBeanRachat = (Players) req.getSession().getAttribute("beanConfirmationRachat");

		int playersId = mBeanRachat.get_id();
		String position = mBeanRachat.getPosition();
		int total_cout_rachat = mBeanRachat.getSalaire_contrat();

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		String nomClef = poolID + "_" + teamId;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		try {
			Entity mEntity = datastore.get(mKey);

			Equipe mBeanEquipe = new Equipe();
			mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(mEntity, mBeanEquipe);

			int budget_restant = mBeanEquipe.getBudget_restant();

			int new_budget_restant = budget_restant - total_cout_rachat;

			if (new_budget_restant >= 0) {

				mBeanEquipe.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now() - total_cout_rachat);
				mBeanEquipe.setBudget_restant(new_budget_restant);
				mBeanEquipe.setNb_contrat(mBeanEquipe.getNb_contrat() - 1);
				mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() - 1);
				mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() + 1);
				mBeanEquipe.setMoy_sal_restant_draft(mBeanEquipe.getBudget_restant() / mBeanEquipe.getManquant_equipe());

				switch (position) {

				case "attaquant":

					mBeanEquipe.setNb_attaquant(mBeanEquipe.getNb_attaquant() - 1);
					mBeanEquipe.setManquant_att(mBeanEquipe.getManquant_att() + 1);
					break;
				case "defenseur":
					mBeanEquipe.setNb_defenseur(mBeanEquipe.getNb_defenseur() - 1);
					mBeanEquipe.setManquant_def(mBeanEquipe.getManquant_def() + 1);
					break;
				case "gardien":
					mBeanEquipe.setNb_gardien(mBeanEquipe.getNb_gardien() - 1);
					mBeanEquipe.setManquant_gardien(mBeanEquipe.getManquant_gardien() + 1);
					break;

				}

				mEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, nomClef);

				datastore.put(mEntity);

			} else {
				mBeanEquipe.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now() - total_cout_rachat);
				mBeanEquipe.setBudget_restant(0);
				mBeanEquipe.setNb_contrat(mBeanEquipe.getNb_contrat() - 1);
				mBeanEquipe.setArgent_recu(mBeanEquipe.getArgent_recu() + new_budget_restant);
				mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() - 1);
				mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() + 1);
				mBeanEquipe
						.setMoy_sal_restant_draft(mBeanEquipe.getBudget_restant() / mBeanEquipe.getManquant_equipe());

				switch (position) {

				case "attaquant":

					mBeanEquipe.setNb_attaquant(mBeanEquipe.getNb_attaquant() - 1);
					mBeanEquipe.setManquant_att(mBeanEquipe.getManquant_att() + 1);
					break;
				case "defenseur":
					mBeanEquipe.setNb_defenseur(mBeanEquipe.getNb_defenseur() - 1);
					mBeanEquipe.setManquant_def(mBeanEquipe.getManquant_def() + 1);
					break;
				case "gardien":
					mBeanEquipe.setNb_gardien(mBeanEquipe.getNb_gardien() - 1);
					mBeanEquipe.setManquant_gardien(mBeanEquipe.getManquant_gardien() + 1);
					break;

				}

				mEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, nomClef);

				datastore.put(mEntity);

			}

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// on retire joueur de equipe

		playersDao.removePlayersFromTeamAfterRachat(playersId, poolID);

		// on relance Attaquant, Def, Goaler cron job

		PlayersCronModel mModel = new PlayersCronModel(playersDao);
		LoginModel mModelLogin = new LoginModel(req);
		int numberOfTeam = mModel.getNumberOfTeamByPool(Integer.parseInt(poolID));

		switch (position) {
		case "attaquant":
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 0, "3");
			mModelLogin.createSessionAttaquantBean();
			mModelLogin.createSessionEquipeBean();

			break;
		case "defenseur":
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 0, "4");
			mModelLogin.createSessionDefenseurBean();
			mModelLogin.createSessionEquipeBean();

			break;
		case "gardien":
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 0, "5");
			mModelLogin.createSessionGardienBean();
			mModelLogin.createSessionEquipeBean();
			break;

		}

	}

	public Boolean checkIfCashAvailablePourRachat(HttpServletRequest req) {

		String player_id = req.getParameter("player_id");
		String position = req.getParameter("position");
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int checkForMoyenne = 0;
		if (mBeanPool.getCycleAnnuel() == 9 || mBeanPool.getCycleAnnuel() == 12) {
			checkForMoyenne = 1;
		}

		Boolean checkIfCashAvailablePourRachat = playersDao.getUniquePlayersById(player_id, poolID, teamId, req,
				position, checkForMoyenne);

		return checkIfCashAvailablePourRachat;

	}

	public Boolean checkIfCashIsGoodForRookieDrop(HttpServletRequest req) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int budget_restant = 0;
		int argent_recu = 0;
		String nomClef = poolID + "_" + teamId;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		Entity mEntity;
		try {
			mEntity = datastore.get(mKey);
			Long budget_restantL = (Long) mEntity.getProperty("budget_restant");
			budget_restant = budget_restantL.intValue();
			Long argent_recuL = (Long) mEntity.getProperty("argent_recu");
			argent_recu = argent_recuL.intValue();

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (budget_restant > 999999
				|| (budget_restant < 1000000 && argent_recu > 999999 || (budget_restant + argent_recu) > 999999)) {
			Players mBean = new Players();
			String player_id = req.getParameter("player_id");

			String nom = playersDao.getPlayersName(player_id, poolID);

			mBean.setNom(nom);

			mBean.set_id(Integer.parseInt(player_id));

			req.getSession().setAttribute("beanConfirmation", mBean);
			return true;
		} else {
			return false;
		}

	}

	public void retrocessionRookieDansClubEcole(HttpServletRequest req) {
		// retrait de l'argent
		Players mBeanRachat = (Players) req.getSession().getAttribute("beanConfirmation");

		int playersId = mBeanRachat.get_id();
		int total_cout_rachat = 1000000;

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		String nomClef = poolID + "_" + teamId;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		// on retire joueur de equipe

		String position = playersDao.putPlayersInClubEcole(playersId, poolID);

		try {
			Entity mEntity = datastore.get(mKey);

			Equipe mBeanEquipe = new Equipe();
			mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(mEntity, mBeanEquipe);

			int budget_restant = mBeanEquipe.getBudget_restant();
			int argent_recu = mBeanEquipe.getArgent_recu();
			int new_budget_restant = 0;
			if (budget_restant < 1000000) {

				budget_restant = budget_restant - total_cout_rachat;
				argent_recu = argent_recu + budget_restant;
				new_budget_restant = 0;

			} else {
				new_budget_restant = budget_restant - total_cout_rachat;
			}

			switch (position) {
			case "attaquant":
				mBeanEquipe.setManquant_att(mBeanEquipe.getManquant_att() + 1);
				mBeanEquipe.setNb_attaquant(mBeanEquipe.getNb_attaquant() - 1);
				break;
			case "defenseur":
				mBeanEquipe.setManquant_def(mBeanEquipe.getManquant_def() + 1);
				mBeanEquipe.setNb_defenseur(mBeanEquipe.getNb_defenseur() - 1);
				break;
			case "gardien":
				mBeanEquipe.setManquant_gardien(mBeanEquipe.getManquant_gardien() + 1);
				mBeanEquipe.setNb_gardien(mBeanEquipe.getNb_gardien() - 1);
				break;

			}
			mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() + 1);
			mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() - 1);
			mBeanEquipe.setArgent_recu(argent_recu);
			mBeanEquipe.setBudget_restant(new_budget_restant);

			mEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, nomClef);

			datastore.put(mEntity);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// on verifie si joueur de type C et on ajuste les salaires

		int checkC = playersDao.checkIfPlayersWillHaveMoreThan25DuringContract(Integer.parseInt(poolID), playersId);

		playersDao.updateCAfterRetro(playersId, poolID, teamId, checkC);

		// on relance Attaquant, Def, Goaler et Rookie selon la position cron job

		PlayersCronModel mModel = new PlayersCronModel(playersDao);

		int numberOfTeam = mModel.getNumberOfTeamByPool(Integer.parseInt(poolID));

		switch (position) {
		case "attaquant":
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 0, "3");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 1, "6");

			break;
		case "defenseur":
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 0, "4");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 1, "6");

			break;
		case "gardien":
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 0, "5");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, position, 1, "6");

			break;

		}

	}

	public void preparationClubEcole(HttpServletRequest req) {

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		int poolId = mBeanUser.getPoolId();

		playersDao.getRookieInClubEcole(teamId, poolId, req);

	}

	public void dropRookie(HttpServletRequest req) {
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolId = mBeanUser.getPoolId();
		int team_id = mBeanUser.getTeamId();

		String players_id = (String) req.getParameter("draft_player_id");
		String position = (String) req.getParameter("position");
		String years_1 = playersDao.getYears1(poolId, players_id);
		// on ajuste la bdd players
		playersDao.dropRookie(poolId, players_id);

		// on ajuste le datastore Recrue
		PlayersCronModel mModel = new PlayersCronModel(playersDao);

		int numberOfTeam = mModel.getNumberOfTeamByPool(poolId);

		switch (position) {
		case "attaquant":
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 1, "6");

			break;
		case "defenseur":
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 1, "6");

			break;
		case "gardien":
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 1, "6");

			break;

		}

		// on ajuste les stats du datastore Equipe

		String nomClef = poolId + "_" + team_id;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		try {
			Entity mEntity = datastore.get(mKey);

			Equipe mBeanEquipe = new Equipe();
			mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(mEntity, mBeanEquipe);

			

			if (years_1.equalsIgnoreCase("C")) {

			} else {
				mBeanEquipe.setManquant_recrue(mBeanEquipe.getManquant_recrue() + 1);
				mBeanEquipe.setNb_rookie(mBeanEquipe.getNb_rookie() - 1);
			}

			mEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, nomClef);

			datastore.put(mEntity);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void signatureRookie(HttpServletRequest req) {
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolId = mBeanUser.getPoolId();
		int team_id = mBeanUser.getTeamId();

		String players_id = (String) req.getParameter("draft_player_id");
		String position = (String) req.getParameter("position");
		String nombreAnneeSignature = (String) req.getParameter("nombreAnneeSignature");
		int numberOfYearSign = Integer.parseInt(nombreAnneeSignature);
		String salaire = (String) req.getParameter("salaire");
		String years_1 = req.getParameter("years_1");
		String years_2 = req.getParameter("years_2");
		String years_3 = req.getParameter("years_3");
		String years_4 = req.getParameter("years_4");
		String years_5 = req.getParameter("years_5");

		// on ajuste la bdd players
		int salaireInt = playersDao.monterRookie(poolId, players_id, numberOfYearSign, salaire, playersDao,years_1,years_2,years_3,years_4,years_5);

		// on ajuste le datastore Recrue
		PlayersCronModel mModel = new PlayersCronModel(playersDao);

		int numberOfTeam = mModel.getNumberOfTeamByPool(poolId);

		switch (position) {
		case "attaquant":
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 0, "3");
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 1, "6");

			break;
		case "defenseur":
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 0, "4");
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 1, "6");

			break;
		case "gardien":
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 0, "5");
			mModel.putDatabaseInDatastore(poolId, numberOfTeam, position, 1, "6");

			break;

		}

		// on ajuste les stats du datastore Equipe

		String nomClef = poolId + "_" + team_id;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		try {
			Entity mEntity = datastore.get(mKey);

			Equipe mBeanEquipe = new Equipe();
			mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(mEntity, mBeanEquipe);

			if(years_1.equalsIgnoreCase("C")) {
				
			} else {
			mBeanEquipe.setManquant_recrue(mBeanEquipe.getManquant_recrue() + 1);
			mBeanEquipe.setNb_rookie(mBeanEquipe.getNb_rookie() - 1);
			}

			mBeanEquipe.setNb_contrat(mBeanEquipe.getNb_contrat() + 1);

			mBeanEquipe.setManquant_equipe(mBeanEquipe.getManquant_equipe() - 1);
			mBeanEquipe.setNb_equipe(mBeanEquipe.getNb_equipe() + 1);

			switch (position) {
			case "attaquant":
				mBeanEquipe.setNb_attaquant(mBeanEquipe.getNb_attaquant() + 1);
				mBeanEquipe.setManquant_att(mBeanEquipe.getManquant_att() - 1);

				break;
			case "defenseur":
				mBeanEquipe.setNb_defenseur(mBeanEquipe.getNb_defenseur() + 1);
				mBeanEquipe.setManquant_def(mBeanEquipe.getManquant_def() - 1);
				break;
			case "gardien":
				mBeanEquipe.setNb_gardien(mBeanEquipe.getNb_gardien() + 1);
				mBeanEquipe.setManquant_gardien(mBeanEquipe.getManquant_gardien() - 1);

				break;

			}

			mBeanEquipe.setBudget_restant(mBeanEquipe.getBudget_restant() - salaireInt);
			mBeanEquipe.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now() + salaireInt);

			mBeanEquipe
					.setMoy_sal_restant_draft((mBeanEquipe.getBudget_restant()) / (mBeanEquipe.getManquant_equipe()));

			mEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, nomClef);

			datastore.put(mEntity);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean checkIfCashIsGoodToGo(HttpServletRequest req) {
		Long manquant_equipe = null;
		Long budget_restant = null;
		String salaire = req.getParameter("salaire");
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		String nomClef = poolID + "_" + teamId;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Equipe", nomClef);

		Entity mEntity;
		try {
			mEntity = datastore.get(mKey);
			manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
			budget_restant = (Long) mEntity.getProperty("budget_restant");
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int moyenne_restante = ((budget_restant.intValue() - Integer.parseInt(salaire)) / (manquant_equipe.intValue())
				+ 1);

		if (moyenne_restante >= 1000000) {
			return true;
		} else {
			return false;
		}

	}

}
