package com.pedagogiksolution.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.DraftOnline;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.firebase.FirebaseChannel;

public class DraftPlayersModel {
	int ascDescOrder;
	String segment, sort;
	HttpServletRequest req;
	HttpServletResponse resp;
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

	public DraftPlayersModel(HttpServletRequest req, String segment, String sort) {
		this.segment = segment;
		this.sort = sort;
		this.req = req;
	}

	public DraftPlayersModel() {
		// TODO Auto-generated constructor stub
	}

	public DraftPlayersModel(HttpServletRequest req2, HttpServletResponse resp2) {
		this.req = req2;
		this.resp = resp2;
	}

	public void showPlayersSortByParameter() {
		NonSessionPlayers mBean = new NonSessionPlayers();
		mBean = getDraftPlayersFromDatastore(sort, segment, mBean);

		req.getSession().setAttribute("Segment", segment);
		req.getSession().setAttribute("Sort", sort);
		req.setAttribute("SegmentSort", segment);
		req.setAttribute("NonSessionPlayers", mBean);

	}

	private NonSessionPlayers getDraftPlayersFromDatastore(String sort2, String segment2, NonSessionPlayers mBean) {

		List<Integer> players_id = new ArrayList<Integer>();
		List<String> nom = new ArrayList<String>();
		List<String> teamOfPlayer = new ArrayList<String>();
		List<Integer> pj = new ArrayList<Integer>();
		List<Integer> but_victoire = new ArrayList<Integer>();
		List<Integer> aide_overtime = new ArrayList<Integer>();
		List<Integer> blanchissage = new ArrayList<Integer>();
		List<Integer> pts = new ArrayList<Integer>();
		List<Integer> projection = new ArrayList<Integer>();
		List<String> position = new ArrayList<String>();
		List<Integer> can_be_rookie = new ArrayList<Integer>();
		List<Integer> salaire_draft = new ArrayList<Integer>();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		String datastoreID = "Players_" + poolID;
		Query q = null;
		Filter noContrat;
		Filter byPosition;
		Filter filterCanBeRookie;
		CompositeFilter mFiltre;

		if (req.getSession().getAttribute("ascDescOrder") == null) {
			ascDescOrder = 0;
		} else {
			ascDescOrder = 0;
		}
		Boolean fromMenu = false;
		String menu = req.getParameter("from");
		if (menu != null) {
			fromMenu = true;
		}
		
		switch (segment2) {
		case "all":

			
			Filter allPlayers = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
			if (ascDescOrder == 0 || fromMenu) {
				q = new Query(datastoreID).setFilter(allPlayers).addSort(sort2, Query.SortDirection.DESCENDING);
				req.getSession().setAttribute("ascDescOrder", 1);
			} else {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(allPlayers);
				req.getSession().setAttribute("ascDescOrder", 0);
			}

			break;
		case "foward":
			
			

		
			noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
			byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "attaquant");
			mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
			if (ascDescOrder == 0 || fromMenu) {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 1);
			} else {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 0);
			}
			break;
		case "defenseur":
			

		
			noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
			byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "defenseur");
			mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
			if (ascDescOrder == 0 || fromMenu) {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 1);
			} else {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 0);
			}
			break;
		case "goaler":
			

		
			noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
			byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "gardien");
			mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
			if (ascDescOrder == 0 || fromMenu) {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 1);
			} else {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 0);
			}
			break;
		case "rookie":
			
			noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
			filterCanBeRookie = new FilterPredicate("can_be_rookie", FilterOperator.EQUAL, 1);
			mFiltre = CompositeFilterOperator.and(noContrat, filterCanBeRookie);
			if (ascDescOrder == 0 || fromMenu) {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 1);
			} else {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
				req.getSession().setAttribute("ascDescOrder", 0);
			}
			break;

		default:
			
			Filter allPlayers2 = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);

			if (ascDescOrder == 0 || fromMenu) {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(allPlayers2);
				req.getSession().setAttribute("ascDescOrder", 1);
			} else {
				q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(allPlayers2);
				req.getSession().setAttribute("ascDescOrder", 0);
			}
			break;

		}

		// Use PreparedQuery interface to retrieve results
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Iterable<Entity> pq = datastore.prepare(q).asIterable(FetchOptions.Builder.withChunkSize(50));

		for (Entity result : pq) {

			Long m_players_id = (Long) result.getProperty("players_id");
			players_id.add(m_players_id.intValue());

			String m_nom = (String) result.getProperty("nom");
			nom.add(m_nom);

			String m_teamOfPlayer = (String) result.getProperty("teamOfPlayer");
			teamOfPlayer.add(m_teamOfPlayer);

			Long m_pj = (Long) result.getProperty("pj");
			pj.add(m_pj.intValue());

			Long m_but_victoire = (Long) result.getProperty("but_victoire");
			but_victoire.add(m_but_victoire.intValue());

			Long m_aide_overtime = (Long) result.getProperty("aide_overtime");
			aide_overtime.add(m_aide_overtime.intValue());

			Long m_blanchissage = (Long) result.getProperty("blanchissage");
			blanchissage.add(m_blanchissage.intValue());

			Long m_pts = (Long) result.getProperty("pts");
			pts.add(m_pts.intValue());

			Long m_projection = (Long) result.getProperty("projection");
			projection.add(m_projection.intValue());

			String m_position = (String) result.getProperty("position");
			position.add(m_position);

			Long m_can_be_rookie = (Long) result.getProperty("can_be_rookie");
			can_be_rookie.add(m_can_be_rookie.intValue());

			Long m_salaire_draft = (Long) result.getProperty("salaire_draft");
			salaire_draft.add(m_salaire_draft.intValue());

		}
		mBean.setPlayers_id(players_id);
		mBean.setNom(nom);
		mBean.setTeamOfPlayer(teamOfPlayer);
		mBean.setPj(pj);
		mBean.setBut_victoire(but_victoire);
		mBean.setAide_overtime(aide_overtime);
		mBean.setBlanchissage(blanchissage);
		mBean.setPts(pts);
		mBean.setProjection(projection);
		mBean.setPosition(position);
		mBean.setCan_be_rookie(can_be_rookie);
		mBean.setSalaire_draft(salaire_draft);

		return mBean;

	}

	public void createDraftDatastoreForThatPool(Pool mBean) {

		String poolID = mBean.getPoolID();
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);

		Entity mEntity = new Entity(mKey);
		mEntity.setProperty("currentPick", 0);
		mEntity.setProperty("currentPicker", 0);
		mEntity.setProperty("numberPickRestant", 0);
		mEntity.setProperty("team1IsOpen", 0);
		mEntity.setProperty("team2IsOpen", 0);
		mEntity.setProperty("team3IsOpen", 0);
		mEntity.setProperty("team4IsOpen", 0);
		mEntity.setProperty("team5IsOpen", 0);
		mEntity.setProperty("team6IsOpen", 0);
		mEntity.setProperty("team7IsOpen", 0);
		mEntity.setProperty("team8IsOpen", 0);
		mEntity.setProperty("team9IsOpen", 0);
		mEntity.setProperty("team10IsOpen", 0);
		mEntity.setProperty("team11IsOpen", 0);
		mEntity.setProperty("team12IsOpen", 0);
		mEntity.setProperty("oneFinish", 0);
		datastore.put(mEntity);
	}

	public Boolean checkIfDatastoreCreate(Pool mBean) {

		String poolID = mBean.getPoolID();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);

		try {
			datastore.get(mKey);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void putDatastoreIntoBean(Pool mBean, HttpServletRequest req2) {

		int numberOfTeam = mBean.getNumberTeam();
		int numberOfTeam2 = numberOfTeam * 2;
		int currentPick2 = 0;
		int draftPickNo = 0;
		DraftProcess mBeanDraft = new DraftProcess();
		String poolID = mBean.getPoolID();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);

		try {
			Entity mEntity = datastore.get(mKey);
			Long currentPick = (Long) mEntity.getProperty("currentPick");
			currentPick2 = currentPick.intValue();

			mBeanDraft.setCurrentPick(currentPick2);

		} catch (EntityNotFoundException e) {

		}

		DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
		Key mKey2 = KeyFactory.createKey("DraftRound", poolID);

		try {
			Entity mEntity = datastore2.get(mKey2);
			List<Long> currentPickerArray = new ArrayList<Long>();
			
			currentPickerArray = (List<Long>) mEntity.getProperty("team_id");
			Long currentPicker = currentPickerArray.get(currentPick2);
			int currentPicker2 = currentPicker.intValue();
			mBeanDraft.setCurrentPicker(currentPicker2);
			
			List<Long> draftPickNoArray = new ArrayList<Long>();
			draftPickNoArray = (List<Long>) mEntity.getProperty("draft_pick_no");
			draftPickNo = draftPickNoArray.size();

		} catch (EntityNotFoundException e) {

		}

		if (numberOfTeam2 < (draftPickNo - currentPick2)) {

			mBeanDraft.setNumberPickRestant(numberOfTeam2);
		} else {
			mBeanDraft.setNumberPickRestant((draftPickNo - currentPick2));
		}

		req2.setAttribute("DraftBean", mBeanDraft);

	}

	public void checkIfDraftIsPossible() {
		Players mBeanPlayer = new Players();
		String teamID = req.getParameter("team_id");
		int teamId = Integer.parseInt(teamID);
		// 0- son tour a drafter2
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int currentPick2 = 0;
		int currentPicker2 = 0;
		String poolID = mBeanPool.getPoolID();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);

		try {
			Entity mEntity = datastore.get(mKey);
			Long currentPick = (Long) mEntity.getProperty("currentPick");
			currentPick2 = currentPick.intValue();

		} catch (EntityNotFoundException e) {

		}

		DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
		Key mKey2 = KeyFactory.createKey("DraftRound", poolID);

		try {
			Entity mEntity = datastore2.get(mKey2);

			@SuppressWarnings("unchecked")
			List<Long> currentPickerArray = (List<Long>) mEntity.getProperty("team_id");
			Long currentPicker = currentPickerArray.get(currentPick2);
			currentPicker2 = currentPicker.intValue();

		} catch (EntityNotFoundException e) {

		}

		if (teamId == currentPicker2) {

		} else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurDraft(
					"Ce n'est pas votre tour de draft. Vous avez peut-être tenté de ré-envoyer un formulaire de draft précédent en cliquant sur refresh. Nous travaillons à rsoudre ce problème. Merci de notifier cette erreur dans le formulaire de rapport d'erreur  de la BETA TEST.");
			req.setAttribute("messageErreur", mBeanMessageErreur);

			return;
		}
		// 1- joueur encore dispo
		String playersID = req.getParameter("draft_player_id");
		String datastoreName = "Players_" + poolID;

		boolean playersStillAvailable = checkIfPlayersStillAvailable(playersID, datastoreName);

		if (playersStillAvailable) {

		} else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurDraft(
					"Ce joueur n'est plus disponible. Désolé de cette erreur. Merci de bien vouloir remplir le formualire de la BETA test pour nous le signaler");
			req.setAttribute("messageErreur", mBeanMessageErreur);
			return;
		}

		// 4- manque recrue
		String can_be_rookie = req.getParameter("can_be_rookie");
		String salaire_draft = req.getParameter("salaire");
		Entity mEntity = getEntityEquipe(poolID, teamID);
		if (can_be_rookie.equals("0")) {
			Boolean checkIfWillMissRookie = checkIfWillMissRookie(mEntity);
			if (!checkIfWillMissRookie) {

			} else {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurDraft(
						"Vous devez choisir un joueur recrue. Il ne vous reste de la place que pour les recrues dans votre équipe");
				req.setAttribute("messageErreur", mBeanMessageErreur);
				return;
			}

		}

		// 2- assez d'argent

		boolean checkIfCashIsOk = true;
		if (can_be_rookie.equals("0")) {
			checkIfCashIsOk = checkIfCashStillAvailable(mEntity, salaire_draft);
			if (checkIfCashIsOk) {

			} else {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurDraft(
						"Vous n'avez pas l'argent pour repécher ce joueur sans éventuellement dépasser votre budget disponible au draft");
				req.setAttribute("messageErreur", mBeanMessageErreur);
				return;
			}
		} else {

			checkIfCashIsOk = checkIfCashStillAvailable(mEntity, salaire_draft);
			if (checkIfCashIsOk) {

			} else {
				checkIfCashIsOk = false;
			}

		}

		// 3- manque position
		String position = req.getParameter("position");
		Boolean checkIfPositionIsFull = false;
		String positionJoueur = checkIfPositionWillBeOk(mEntity, position);
		if (can_be_rookie.equals("0")) {

			if (positionJoueur == null) {

			} else {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurDraft(
						"Vous ne pouvez prendre ce joueur car ceci aurait pour influence de vous empêcher de repêcher le nombre de joueur minimum requis par position");
				req.setAttribute("messageErreur", mBeanMessageErreur);
				return;
			}

		} else {

			if (positionJoueur == null) {

			} else {
				checkIfPositionIsFull = true;
			}

		}

		// C'est bon, on met les info dans le bean et on envoie pour validation
		String nom = req.getParameter("nom");
		String salaire = req.getParameter("salaire");
		String team = req.getParameter("team");

		mBeanPlayer.setPlayers_id(Integer.parseInt(playersID));
		mBeanPlayer.setNom(nom);
		mBeanPlayer.setPosition(position);
		mBeanPlayer.setTeamOfPlayer(team);
		mBeanPlayer.setSalaire_draft(Integer.parseInt(salaire));
		mBeanPlayer.setCan_be_rookie(Integer.parseInt(can_be_rookie));

		// // 5- si deja huit rookie, on offre plus le choix de mettre dans club ecole
		Long manquant_recrue = (Long) mEntity.getProperty("manquant_recrue");
		int manquantRecrue = manquant_recrue.intValue();
		if (manquantRecrue <= 0) {
			mBeanPlayer.setCan_be_rookie(0);

		}
		if (checkIfPositionIsFull) {
			mBeanPlayer.setCan_be_rookie(2);
		}

		if (!checkIfCashIsOk) {
			mBeanPlayer.setCan_be_rookie(2);
		}

		if (manquantRecrue <= 0 && !checkIfCashIsOk) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurDraft(
					"Vous ne pouvez drafter ce joueur car vous n'avez pas assez d'argent et vous n'avez pu de place dans votre club ecole");
			req.setAttribute("messageErreur", mBeanMessageErreur);
			return;
		}

		req.setAttribute("confirmationPick", mBeanPlayer);

	}

	public void persistenceDraftPickRegulier() throws ServletException, IOException {
		// on recupere tous les variables utiles
		Key entityKey = null;
		Attaquant mBeanAttaquant = null;
		Defenseur mBeanDefenseur = null;
		Gardien mBeanGardien = null;
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		String salaire = req.getParameter("salaire");
		int salaireId = Integer.parseInt(salaire);
		String playersID = req.getParameter("draft_player_id");
		int playersId = Integer.parseInt(playersID);
		String teamID = req.getParameter("team_id");
		String position = req.getParameter("position");
		String nom = req.getParameter("nom");
		String team = req.getParameter("team");
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);
		int currentPick = 0;

		Entity draftRoundEntity = null, players = null, entity = null, equipeEntity = null;
		Key equipeKey;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Entity mEntity = datastore.get(mKey);
			Long currentPick2 = (Long) mEntity.getProperty("currentPick");
			currentPick = currentPick2.intValue();

		} catch (EntityNotFoundException e) {

		}
		String playersEntityName = "Players_" + poolID;
		String datastoreID = poolID + "_" + teamID;

		// on commence une transaction pour mettre a jour les datastores
		// Players, Attaquant, Defenseur, Gardien, Equipe,
		// draftRound, DraftPick,DraftProcess
		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		Transaction txn = datastore.beginTransaction(options);
		try {

			// table players_X
			Key playersKey = KeyFactory.createKey(playersEntityName, playersID);

			try {
				players = datastore.get(playersKey);

				players.setProperty("contrat", 1);

				datastore.put(txn, players);

			} catch (EntityNotFoundException e) {

			}

			// table Attaquant, Defenseur ou Gardien selon le pick

			switch (position) {
			case "attaquant":
				entityKey = KeyFactory.createKey("Attaquant", datastoreID);
				break;
			case "defenseur":
				mBeanDefenseur = new Defenseur();
				entityKey = KeyFactory.createKey("Defenseur", datastoreID);
				break;
			case "gardien":
				mBeanGardien = new Gardien();
				entityKey = KeyFactory.createKey("Gardien", datastoreID);
				break;
			}

			try {

				entity = datastore.get(entityKey);

				mapDatastoreInTempArray(entity, position);

				players_id.add(Long.valueOf(playersId));
				entity.setProperty("players_id", players_id);

				nom2.add(nom);
				entity.setProperty("nom", nom2);

				teamOfPlayer.add(team);
				entity.setProperty("teamOfPlayer", teamOfPlayer);

				pj.add(Long.valueOf(0));

				but_victoire.add(Long.valueOf(0));

				aide_overtime.add(Long.valueOf(0));
				if (position.equalsIgnoreCase("Gardien")) {

					blanchissage.add(Long.valueOf(0));
				}

				pts.add(Long.valueOf(0));

				entity.setProperty("pj", pj);

				entity.setProperty("but_victoire", but_victoire);

				entity.setProperty("aide_overtime", aide_overtime);

				if (position.equalsIgnoreCase("Gardien")) {

					entity.setProperty("blanchissage", blanchissage);
				}

				entity.setProperty("pts", pts);

				years_1.add(salaire);
				entity.setProperty("years_1", years_1);

				years_2.add("JA");
				entity.setProperty("years_2", years_2);

				years_3.add("A");
				entity.setProperty("years_3", years_3);

				years_4.add("A");
				entity.setProperty("years_4", years_4);

				years_5.add("A");
				entity.setProperty("years_5", years_5);

				datastore.put(txn, entity);

				String jspName;
				switch (position) {
				case "attaquant":
					jspName = "Attaquant" + teamID;
					mBeanAttaquant = (Attaquant) req.getSession().getAttribute(jspName);
					mBeanAttaquant.setAide_overtime(aide_overtime);
					mBeanAttaquant.setBlanchissage(blanchissage);
					mBeanAttaquant.setBut_victoire(but_victoire);
					mBeanAttaquant.setNom(nom2);
					mBeanAttaquant.setPj(pj);
					mBeanAttaquant.setPts(pts);
					mBeanAttaquant.setTeamOfPlayer(teamOfPlayer);
					mBeanAttaquant.setYears_1(years_1);
					mBeanAttaquant.setYears_2(years_2);
					mBeanAttaquant.setYears_3(years_3);
					mBeanAttaquant.setYears_4(years_4);
					mBeanAttaquant.setYears_5(years_5);

					req.getSession().setAttribute(jspName, mBeanAttaquant);

					break;
				case "defenseur":
					jspName = "Defenseur" + teamID;
					mBeanDefenseur = (Defenseur) req.getSession().getAttribute(jspName);
					mBeanDefenseur.setAide_overtime(aide_overtime);
					mBeanDefenseur.setBlanchissage(blanchissage);
					mBeanDefenseur.setBut_victoire(but_victoire);
					mBeanDefenseur.setNom(nom2);
					mBeanDefenseur.setPj(pj);
					mBeanDefenseur.setPts(pts);
					mBeanDefenseur.setTeamOfPlayer(teamOfPlayer);
					mBeanDefenseur.setYears_1(years_1);
					mBeanDefenseur.setYears_2(years_2);
					mBeanDefenseur.setYears_3(years_3);
					mBeanDefenseur.setYears_4(years_4);
					mBeanDefenseur.setYears_5(years_5);
					req.getSession().setAttribute(jspName, mBeanDefenseur);
					break;
				case "gardien":
					jspName = "Gardien" + teamID;
					mBeanGardien = (Gardien) req.getSession().getAttribute(jspName);
					mBeanGardien.setAide_overtime(aide_overtime);
					mBeanGardien.setBlanchissage(blanchissage);
					mBeanGardien.setBut_victoire(but_victoire);
					mBeanGardien.setNom(nom2);
					mBeanGardien.setPj(pj);
					mBeanGardien.setPts(pts);
					mBeanGardien.setTeamOfPlayer(teamOfPlayer);
					mBeanGardien.setYears_1(years_1);
					mBeanGardien.setYears_2(years_2);
					mBeanGardien.setYears_3(years_3);
					mBeanGardien.setYears_4(years_4);
					mBeanGardien.setYears_5(years_5);
					req.getSession().setAttribute(jspName, mBeanGardien);
					break;
				}

			} catch (EntityNotFoundException e) {

			}

			// Table Equipe
			equipeKey = KeyFactory.createKey("Equipe", datastoreID);

			try {
				equipeEntity = datastore.get(equipeKey);

				Long temp_budget_restant = (Long) equipeEntity.getProperty("budget_restant");
				int budget_restant = temp_budget_restant.intValue();

				Long temp_manquant_att = (Long) equipeEntity.getProperty("manquant_att");
				int manquant_att = temp_manquant_att.intValue();

				Long temp_manquant_def = (Long) equipeEntity.getProperty("manquant_def");
				int manquant_def = temp_manquant_def.intValue();

				Long temp_manquant_equipe = (Long) equipeEntity.getProperty("manquant_equipe");
				int manquant_equipe = temp_manquant_equipe.intValue();

				Long temp_manquant_gardien = (Long) equipeEntity.getProperty("manquant_gardien");
				int manquant_gardien = temp_manquant_gardien.intValue();

				Long temp_moy_sal_restant_draft = (Long) equipeEntity.getProperty("moy_sal_restant_draft");
				int moy_sal_restant_draft = temp_moy_sal_restant_draft.intValue();

				Long temp_nb_attaquant = (Long) equipeEntity.getProperty("nb_attaquant");
				int nb_attaquant = temp_nb_attaquant.intValue();

				Long temp_nb_defenseur = (Long) equipeEntity.getProperty("nb_defenseur");
				int nb_defenseur = temp_nb_defenseur.intValue();

				Long temp_nb_equipe = (Long) equipeEntity.getProperty("nb_equipe");
				int nb_equipe = temp_nb_equipe.intValue();

				Long temp_nb_gardien = (Long) equipeEntity.getProperty("nb_gardien");
				int nb_gardien = temp_nb_gardien.intValue();

				Long temp_total_salaire_now = (Long) equipeEntity.getProperty("total_salaire_now");
				int total_salaire_now = temp_total_salaire_now.intValue();
				
				Long argent_recu = (Long) equipeEntity.getProperty("argent_recu");
				int argentRecu = argent_recu.intValue();

				if(budget_restant<salaireId) {
					
					int argent_manquant = budget_restant - salaireId;
					argentRecu= argentRecu+argent_manquant;
					budget_restant=0;
					
				} else {
					budget_restant = budget_restant - salaireId;
				}
				
				
				
				
				manquant_equipe = manquant_equipe - 1;
				nb_equipe = nb_equipe + 1;
				switch (position) {
				case "attaquant":
					manquant_att = manquant_att - 1;
					nb_attaquant = nb_attaquant + 1;
					equipeEntity.setProperty("manquant_att", manquant_att);
					equipeEntity.setProperty("nb_attaquant", nb_attaquant);
					break;
				case "defenseur":
					manquant_def = manquant_def - 1;
					nb_defenseur = nb_defenseur + 1;
					equipeEntity.setProperty("manquant_def", manquant_def);
					equipeEntity.setProperty("nb_defenseur", nb_defenseur);
					break;
				case "gardien":
					manquant_gardien = manquant_gardien - 1;
					nb_gardien = nb_gardien + 1;
					equipeEntity.setProperty("manquant_gardien", manquant_gardien);
					equipeEntity.setProperty("nb_gardien", nb_gardien);
					break;
				}
				total_salaire_now = total_salaire_now + salaireId;
				if (manquant_equipe == 0) {
					moy_sal_restant_draft = 0;
				} else {
					moy_sal_restant_draft = (budget_restant + argentRecu) / manquant_equipe;
				}

				equipeEntity.setProperty("budget_restant", budget_restant);
				equipeEntity.setProperty("manquant_equipe", manquant_equipe);
				equipeEntity.setProperty("nb_equipe", nb_equipe);
				equipeEntity.setProperty("total_salaire_now", total_salaire_now);
				equipeEntity.setProperty("moy_sal_restant_draft", moy_sal_restant_draft);

				datastore.put(txn, equipeEntity);

				String jspName;
				jspName = "Equipe" + teamID;
				Equipe mBeanEquipe = new Equipe();
				mBeanEquipe = (Equipe) req.getSession().getAttribute(jspName);

				mBeanEquipe.setBudget_restant(budget_restant);
				mBeanEquipe.setManquant_equipe(manquant_equipe);
				mBeanEquipe.setNb_equipe(nb_equipe);
				mBeanEquipe.setTotal_salaire_now(total_salaire_now);
				mBeanEquipe.setMoy_sal_restant_draft(moy_sal_restant_draft);
				switch (position) {
				case "attaquant":
					mBeanEquipe.setManquant_att(manquant_att);
					mBeanEquipe.setNb_attaquant(nb_attaquant);
					break;
				case "defenseur":
					mBeanEquipe.setManquant_def(manquant_def);
					mBeanEquipe.setNb_defenseur(nb_defenseur);
					break;
				case "gardien":
					mBeanEquipe.setManquant_gardien(manquant_gardien);
					mBeanEquipe.setNb_gardien(nb_gardien);
					break;
				}
				req.getSession().setAttribute(jspName, mBeanEquipe);

			} catch (EntityNotFoundException e) {

			}

			// Table Draft_round
			Key draftRoundKey = KeyFactory.createKey("DraftRound", poolID);

			try {
				draftRoundEntity = datastore.get(draftRoundKey);

				DraftRound mBeanDraftRound = new DraftRound();
				mBeanDraftRound = mBeanDraftRound.mapDraftRoundFromDatastore(draftRoundEntity, mBeanDraftRound);
				
				List<String> player_drafted = (List<String>)mBeanDraftRound.getPlayer_drafted();
				
				player_drafted.set((currentPick), nom);
				
				mBeanDraftRound.setPlayer_drafted(player_drafted);
				
				draftRoundEntity.setProperty("player_drafted", player_drafted);
	
				req.getSession().setAttribute("DraftRound", mBeanDraftRound);

				datastore.put(txn, draftRoundEntity);

			} catch (EntityNotFoundException e) {

			}

			// Table DraftProcess
			Key draftProcessKey = KeyFactory.createKey("DraftProcess", poolID);
			Entity draftProcessEntity = null;

			try {
				draftProcessEntity = datastore.get(draftProcessKey);

				currentPick = currentPick + 1;
				draftProcessEntity.setProperty("currentPick", currentPick);

				datastore.put(txn, draftProcessEntity);

			} catch (EntityNotFoundException e) {

			}

			

			String clubEcole = "0";
			int acquireYearsId = mBeanPool.getPoolYear();
			String acquireYearsID = String.valueOf((acquireYearsId));

			persistenceDraftInDatabase(poolID, salaire, playersID, teamID, nom, team, position, clubEcole,
					acquireYearsID, currentPick);

			// commit
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();

			}
		}

	}

	public void persistenceDraftPickRookie() throws ServletException, IOException {

		// on recupere tous les variables utiles
		Key entityKey = null;
		Recrue mBeanRecrue = null;
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		String salaire = req.getParameter("salaire");
		int salaireId = Integer.parseInt(salaire);
		String playersID = req.getParameter("draft_player_id");
		int playersId = Integer.parseInt(playersID);
		String teamID = req.getParameter("team_id");
		String position = req.getParameter("position");
		String nom = req.getParameter("nom");
		String team = req.getParameter("team");
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);
		int currentPick = 0;

		Entity draftRoundEntity = null, players = null, entity = null, equipeEntity = null;
		Key equipeKey;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Entity mEntity = datastore.get(mKey);
			Long currentPick2 = (Long) mEntity.getProperty("currentPick");
			currentPick = currentPick2.intValue();

		} catch (EntityNotFoundException e) {

		}
		String playersEntityName = "Players_" + poolID;
		String datastoreID = poolID + "_" + teamID;

		// on commence une transaction pour mettre a jour les datastores
		// Players, Attaquant, Defenseur, Gardien, Equipe,
		// draftRound, DraftPick,DraftProcess
		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		Transaction txn = datastore.beginTransaction(options);
		try {

			// table players_X
			// table players_X
			Key playersKey = KeyFactory.createKey(playersEntityName, playersID);

			try {
				players = datastore.get(playersKey);

				players.setProperty("contrat", 1);
				players.setProperty("club_ecole", 1);

				datastore.put(txn, players);

			} catch (EntityNotFoundException e) {

			}

			// table Attaquant, Defenseur ou Gardien selon le pick

			mBeanRecrue = new Recrue();
			entityKey = KeyFactory.createKey("Recrue", datastoreID);

			try {

				entity = datastore.get(entityKey);

				mapDatastoreInTempArray(entity, position);

				players_id.add(Long.valueOf(playersId));
				entity.setProperty("players_id", players_id);

				nom2.add(nom);
				entity.setProperty("nom", nom2);

				teamOfPlayer.add(team);
				entity.setProperty("teamOfPlayer", teamOfPlayer);

				pj.add(Long.valueOf(0));

				but_victoire.add(Long.valueOf(0));

				aide_overtime.add(Long.valueOf(0));

				blanchissage.add(Long.valueOf(0));

				pts.add(Long.valueOf(0));

				entity.setProperty("pj", pj);

				entity.setProperty("but_victoire", but_victoire);

				entity.setProperty("aide_overtime", aide_overtime);

				entity.setProperty("blanchissage", blanchissage);

				entity.setProperty("pts", pts);

				salaire_contrat.add(Long.valueOf(salaireId));
				entity.setProperty("salaire_contrat", salaire_contrat);

				years_1.add(salaire);
				entity.setProperty("years_1", years_1);

				years_2.add(salaire);
				entity.setProperty("years_2", years_2);

				years_3.add(salaire);
				entity.setProperty("years_3", years_3);

				years_4.add(salaire);
				entity.setProperty("years_4", years_4);

				years_5.add(salaire);
				entity.setProperty("years_5", years_5);

				datastore.put(txn, entity);

				String jspName;

				jspName = "Recrue" + teamID;
				mBeanRecrue = (Recrue) req.getSession().getAttribute(jspName);
				mBeanRecrue.setAide_overtime(aide_overtime);
				mBeanRecrue.setBlanchissage(blanchissage);
				mBeanRecrue.setBut_victoire(but_victoire);
				mBeanRecrue.setNom(nom2);
				mBeanRecrue.setPj(pj);
				mBeanRecrue.setPts(pts);
				mBeanRecrue.setTeamOfPlayer(teamOfPlayer);
				mBeanRecrue.setYears_1(years_1);
				mBeanRecrue.setYears_2(years_2);
				mBeanRecrue.setYears_3(years_3);
				mBeanRecrue.setYears_4(years_4);
				mBeanRecrue.setYears_5(years_5);

			} catch (EntityNotFoundException e) {

			}

			// Table Equipe
			equipeKey = KeyFactory.createKey("Equipe", datastoreID);

			try {
				equipeEntity = datastore.get(equipeKey);

				Long temp_manquant_recrue = (Long) equipeEntity.getProperty("manquant_recrue");
				int manquant_recrue = temp_manquant_recrue.intValue();

				Long temp_nb_recrue = (Long) equipeEntity.getProperty("nb_rookie");
				int nb_recrue = temp_nb_recrue.intValue();

				manquant_recrue = manquant_recrue - 1;
				nb_recrue = nb_recrue + 1;
				equipeEntity.setProperty("manquant_recrue", manquant_recrue);
				equipeEntity.setProperty("nb_rookie", nb_recrue);

				datastore.put(txn, equipeEntity);

				String jspName;
				jspName = "Equipe" + teamID;
				Equipe mBeanEquipe = new Equipe();
				mBeanEquipe = (Equipe) req.getSession().getAttribute(jspName);

				mBeanEquipe.setManquant_recrue(manquant_recrue);
				mBeanEquipe.setNb_rookie(nb_recrue);

			} catch (EntityNotFoundException e) {

			}

			// Table Draft_round
			Key draftRoundKey = KeyFactory.createKey("DraftRound", poolID);

			try {
				draftRoundEntity = datastore.get(draftRoundKey);

				DraftRound mBeanDraftRound = new DraftRound();
				mBeanDraftRound = mBeanDraftRound.mapDraftRoundFromDatastore(draftRoundEntity, mBeanDraftRound);
				
				List<String> player_drafted = (List<String>)mBeanDraftRound.getPlayer_drafted();
				
				player_drafted.set((currentPick), nom);
				
				mBeanDraftRound.setPlayer_drafted(player_drafted);
				
				draftRoundEntity.setProperty("player_drafted", player_drafted);
	
				req.getSession().setAttribute("DraftRound", mBeanDraftRound);

				datastore.put(txn, draftRoundEntity);

			} catch (EntityNotFoundException e) {

			}

			// Table DraftProcess
			Key draftProcessKey = KeyFactory.createKey("DraftProcess", poolID);
			Entity draftProcessEntity = null;

			try {
				draftProcessEntity = datastore.get(draftProcessKey);

				currentPick = currentPick + 1;
				draftProcessEntity.setProperty("currentPick", currentPick);

				datastore.put(txn, draftProcessEntity);

			} catch (EntityNotFoundException e) {

			}

			
			String clubEcole = "1";
			int acquireYearsId = mBeanPool.getPoolYear();
			String acquireYearsID = String.valueOf((acquireYearsId));

			persistenceDraftInDatabaseRookie(poolID, salaire, playersID, teamID, nom, team, position, clubEcole,
					acquireYearsID, currentPick);

			// commit
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();

			}
		}

	}

	public void channelMessage(int tagForDraftFinish) {

		String currentPick = "";
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		int numberOfTeam = mBeanPool.getNumberTeam();
		String salaire = req.getParameter("salaire");
		String teamID = req.getParameter("team_id");
		int teamId = Integer.parseInt(teamID);
		String position = req.getParameter("position");
		String nom = req.getParameter("nom");
		String team = req.getParameter("team");
		String teamThatDraft = "???";
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Entity mEntity = datastore.get(mKey);
			Long currentPick2 = (Long) mEntity.getProperty("currentPick");
			
			currentPick = currentPick2.toString();

		} catch (EntityNotFoundException e) {

		}

		switch (teamId) {
		case 1:
			teamThatDraft = mBeanPool.getNomTeam1();
			break;
		case 2:
			teamThatDraft = mBeanPool.getNomTeam2();
			break;
		case 3:
			teamThatDraft = mBeanPool.getNomTeam3();
			break;
		case 4:
			teamThatDraft = mBeanPool.getNomTeam4();
			break;
		case 5:
			teamThatDraft = mBeanPool.getNomTeam5();
			break;
		case 6:
			teamThatDraft = mBeanPool.getNomTeam6();
			break;
		case 7:
			teamThatDraft = mBeanPool.getNomTeam7();
			break;
		case 8:
			teamThatDraft = mBeanPool.getNomTeam8();
			break;
		case 9:
			teamThatDraft = mBeanPool.getNomTeam9();
			break;
		case 10:
			teamThatDraft = mBeanPool.getNomTeam10();
			break;
		case 11:
			teamThatDraft = mBeanPool.getNomTeam11();
			break;
		case 12:
			teamThatDraft = mBeanPool.getNomTeam12();
			break;

		}
		DraftOnline mBeanDraftOnline = new DraftOnline();
		
		
		
		
		switch (tagForDraftFinish) {
		case 1:
			mBeanDraftOnline.setDraftPickMade("1");
			break;
		case 2:
			mBeanDraftOnline.setDraftPickMade("2");
			break;
		case 3:
			mBeanDraftOnline.setDraftPickMade("3");
			break;
		case 4:
			mBeanDraftOnline.setDraftPickMade("4");
			break;
		}
		
		
		mBeanDraftOnline.setPickNumber(currentPick);
		mBeanDraftOnline.setPlayerDrafted(nom);
		mBeanDraftOnline.setPosition(position);
		mBeanDraftOnline.setTeamOfPlayer(team);
		mBeanDraftOnline.setSalaire(salaire);
		mBeanDraftOnline.setTeamThatDraft(teamThatDraft);


		for (int i = 1; i < (numberOfTeam + 1); i++) {

			if (teamId == i) {

			} else {
				String tokenString = poolID + "_" + i;				
				try {
					FirebaseChannel.getInstance(req.getSession().getServletContext()).sendFirebaseMessage(tokenString,mBeanDraftOnline);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public Boolean checkIfDraftFinish(DraftDao draftDao) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		String teamID = req.getParameter("team_id");

		Entity entity = getEntityEquipe(poolID, teamID);

		Long manquant_equipe2 = (Long) entity.getProperty("manquant_equipe");
		int manquant_equipe = manquant_equipe2.intValue();
		Long manquant_recrue2 = (Long) entity.getProperty("manquant_recrue");
		int manquant_recrue = manquant_recrue2.intValue();

		if (manquant_equipe == 0 && manquant_recrue == 0) {
			
			// on retire les pick restant du DraftRound
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key mKey = KeyFactory.createKey("DraftProcess", poolID);
			String currentPick = null;
			Entity mEntity;
			try {
				mEntity = datastore.get(mKey);
				Long currentPickL = (Long) mEntity.getProperty("currentPick");
				currentPick = currentPickL.toString();
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/TaskQueueFinDraft").param("poolID", poolID).param("teamID", teamID).param("currentPick", currentPick));
		
			return true;
		} else {
			return false;
		}

	}

	public void persistDraftFinishForUser() {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		String teamID = req.getParameter("team_id");

		String teamName = teamID + "isFinish";
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("DraftProcess", poolID);
		try {
			Entity mEntity = datastore.get(mKey);
			mEntity.setProperty(teamName, 1);
			mEntity.setProperty("oneFinish", 1);
			datastore.put(mEntity);

		} catch (EntityNotFoundException e) {

		}

	}

	public Boolean checkifDraftFinishForAll() {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		int numberOfTeam = mBeanPool.getNumberTeam();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		for(int i=1;i<numberOfTeam+1;i++) {
			
			String name = poolID+"_"+i;
			Key mKeyEquipe = KeyFactory.createKey("Equipe", name);
			
			Entity mEquipe;
			try {
				mEquipe = datastore.get(mKeyEquipe);
				Long manquant_recrueL = (Long) mEquipe.getProperty("manquant_recrue");
				Long manquant_equipeL = (Long) mEquipe.getProperty("manquant_equipe");
				int checkIfFinish= manquant_recrueL.intValue()+manquant_equipeL.intValue();
				if(checkIfFinish==0) {
					
				} else {
					return false;
				}
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
		}
		
				
		return true;
	}

	public void changeCycleAnnuelToSignature4() {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		mBeanPool.setCycleAnnuel(4);

		req.getSession().setAttribute("Pool", mBeanPool);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


		Entity mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, mBeanPool.getPoolID());

		datastore.put(mEntity);
		
		Key keyPool = KeyFactory.createKey("Pool", mBeanPool.getPoolID());

		try {
			Entity mEntityPool = datastore.get(keyPool);

			mEntityPool.setProperty("draftDate", "2999-10-21");

			datastore.put(mEntityPool);
		} catch (EntityNotFoundException e) {

		}

	}

	/*
	 * ******************************************* Methode prive
	 * ************************************************
	 */

	

	private Entity getEntityEquipe(String poolID, String teamID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String datastoreNameEquipeTable = poolID + "_" + teamID;
		Key mKey = KeyFactory.createKey("Equipe", datastoreNameEquipeTable);
		try {
			Entity mEntity = datastore.get(mKey);
			return mEntity;
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	private boolean checkIfPlayersStillAvailable(String playersID, String datastoreName) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		int playersId = Integer.parseInt(playersID);
		Filter selectPlayerById = new FilterPredicate("players_id", FilterOperator.EQUAL, playersId);
		// Use class Query to assemble a query
		Query q = new Query(datastoreName).setFilter(selectPlayerById);

		// Use PreparedQuery interface to retrieve results
		Entity pq = datastore.prepare(q).asSingleEntity();

		Long contrat = (Long) pq.getProperty("contrat");

		if (contrat == 1) {
			return false;
		} else {
			return true;
		}

	}

	private boolean checkIfCashStillAvailable(Entity mEntity, String salaire_draft) {

		int salaireDraft = Integer.parseInt(salaire_draft);
		int budgetRestant = 0;
		int manquantEquipe = 0;
		int argentRecu =0;

		Long budget_restant = (Long) mEntity.getProperty("budget_restant");
		budgetRestant = budget_restant.intValue();
		Long manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
		manquantEquipe = manquant_equipe.intValue();
		Long argent_recu = (Long) mEntity.getProperty("argent_recu");
		argentRecu = argent_recu.intValue();

		if ((manquantEquipe - 1) == 0) {
			if (budgetRestant + argentRecu < salaireDraft) {
				return false;
			} else {
				return true;
			}
		}

		if (budgetRestant + argentRecu < salaireDraft || ((budgetRestant + argentRecu - salaireDraft) / (manquantEquipe - 1) < 1000000)) {
			return false;
		} else {
			return true;
		}
	}

	private String checkIfPositionWillBeOk(Entity mEntity, String position) {

		int manquantAttaquant = 0;
		int manquantDefenseur = 0;
		int manquantGardien = 0;
		int manquantEquipe = 0;

		Long manquant_att = (Long) mEntity.getProperty("manquant_att");
		manquantAttaquant = manquant_att.intValue();
		Long manquant_def = (Long) mEntity.getProperty("manquant_def");
		manquantDefenseur = manquant_def.intValue();
		Long manquant_gardien = (Long) mEntity.getProperty("manquant_gardien");
		manquantGardien = manquant_gardien.intValue();
		Long manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
		manquantEquipe = manquant_equipe.intValue();

		switch (position) {
		case "attaquant":
			if ((manquantEquipe - 1) < manquantDefenseur || (manquantEquipe - 1) < manquantGardien
					|| (manquantEquipe - 1) < (manquantGardien + manquantDefenseur)) {
				return position;
			} else {
				return null;
			}

		case "defenseur":
			if ((manquantEquipe - 1) < manquantAttaquant || (manquantEquipe - 1) < manquantGardien
					|| (manquantEquipe - 1) < (manquantGardien + manquantAttaquant)) {
				return position;
			} else {
				return null;
			}
		case "gardien":
			if ((manquantEquipe - 1) < manquantDefenseur || (manquantEquipe - 1) < manquantAttaquant
					|| (manquantEquipe - 1) < (manquantAttaquant + manquantDefenseur)) {
				return position;
			} else {
				return null;
			}

		}
		return null;

	}

	private Boolean checkIfWillMissRookie(Entity mEntity) {
		Long manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
		int manquantEquipe = manquant_equipe.intValue();

		if (manquantEquipe == 0) {
			return true;
		} else {
			return false;
		}

	}

	private void persistenceDraftInDatabase(String poolID, String salaire, String playersID, String teamID, String nom,
			String team, String position, String clubEcole, String acquireYearsID, int currentPick) {

		// persister dans les databases draft(x) et player_(x) via un taskqueue
		// process

		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(
				TaskOptions.Builder.withUrl("/TaskQueueDraftPlayer").param("isRookie", "0").param("salaireID", salaire)
						.param("poolID", poolID).param("playersID", playersID).param("teamID", teamID).param("nom", nom)
						.param("team", team).param("acquireYearsID", acquireYearsID).param("club_ecole", clubEcole)
						.param("position", position).param("currentPick", String.valueOf(currentPick-1)));

	}

	private void persistenceDraftInDatabaseRookie(String poolID, String salaire, String playersID, String teamID,
			String nom, String team, String position, String clubEcole, String acquireYearsID, int currentPick) {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(
				TaskOptions.Builder.withUrl("/TaskQueueDraftPlayer").param("isRookie", "1").param("salaireID", salaire)
						.param("poolID", poolID).param("playersID", playersID).param("teamID", teamID).param("nom", nom)
						.param("team", team).param("acquireYearsID", acquireYearsID).param("club_ecole", clubEcole)
						.param("position", position).param("currentPick", String.valueOf(currentPick-1)));

	}

	@SuppressWarnings("unchecked")
	private void mapDatastoreInTempArray(Entity entity, String position) {

		List<Integer> testIfEmpty = (List<Integer>) entity.getProperty("players_id");

		if (testIfEmpty != null) {

			players_id.addAll((List<Long>) entity.getProperty("players_id"));
			nom2.addAll((List<String>) entity.getProperty("nom"));
			teamOfPlayer.addAll((List<String>) entity.getProperty("teamOfPlayer"));
			pj.addAll((List<Long>) entity.getProperty("pj"));
			but_victoire.addAll((List<Long>) entity.getProperty("but_victoire"));
			aide_overtime.addAll((List<Long>) entity.getProperty("aide_overtime"));
			if (position.equalsIgnoreCase("Gardien")) {
				blanchissage.addAll((List<Long>) entity.getProperty("blanchissage"));
			}
			pts.addAll((List<Long>) entity.getProperty("pts"));
			years_1.addAll((List<String>) entity.getProperty("years_1"));
			years_2.addAll((List<String>) entity.getProperty("years_2"));
			years_3.addAll((List<String>) entity.getProperty("years_3"));
			years_4.addAll((List<String>) entity.getProperty("years_4"));
			years_5.addAll((List<String>) entity.getProperty("years_5"));
		}
	}
}
