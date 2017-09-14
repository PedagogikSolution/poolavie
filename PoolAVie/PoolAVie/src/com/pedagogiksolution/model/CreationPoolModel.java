package com.pedagogiksolution.model;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class CreationPoolModel {

	private ClassementDao classementDao;
	private PlayersDao playersDao;
	private DraftDao draftDao;
	private TradeMadeDao tradeMadeDao;
	private TradeOfferDao tradeOfferDao;
	private SalaireDao salaireDao;
	private DraftPickDao draftPickDao;

	public CreationPoolModel(ClassementDao classementDao, PlayersDao playersDao, DraftDao draftDao,
			TradeMadeDao tradeMadeDao, TradeOfferDao tradeOfferDao, SalaireDao salaireDao, DraftPickDao draftPickDao) {
		this.classementDao = classementDao;
		this.playersDao = playersDao;
		this.draftDao = draftDao;
		this.tradeMadeDao = tradeMadeDao;
		this.tradeOfferDao = tradeOfferDao;
		this.salaireDao = salaireDao;
		this.draftPickDao = draftPickDao;
	}

	public CreationPoolModel() {
		// TODO Auto-generated constructor stub
	}

	String nomDuPool, nombreEquipe, typeTrade, typeDraft, nomDuTeam, urlLogoTeam;
	String email1, email2, email3, email4, email5, email6, email7, email8, email9, email10, email11;
	int team_id, max_salaire_begin, total_salaire_now, budget_restant, moy_sal_restant_draft, nb_attaquant;
	int nb_defenseur, nb_gardien, nb_rookie, nb_contrat, nb_equipe, manquant_equipe, manquant_att, manquant_def;
	int manquant_gardien, manquant_recrue, bonus_5m, argent_recu, bonus_penalite;

	public Boolean validationFormulaireCreation(HttpServletRequest req) {

		// TODO verifier si les courriels sont REGEX validate et retourner message
		// d'erreur mais avec informations
		// encore bonne re-afficher

		return true;
	}

	public void createPoolBean(HttpServletRequest req) {

		// initialisation des parametres recu du formulaire
		initParamFromFormulaire(req);

		// on initialise les variables non recu via le formulaire ou pas encore
		int typePool = 1;
		int numTeamCreate = 1;
		int poolYear = 0;

		// TODO Generate a Code for the Pool
		String codeValidation = generateValidationCode();

		// on recupere le pool_id
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		String poolID = Integer.toString(mBeanUser.getPoolId());

		// on recupere la date et place dans un format Date

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateCreation = dateFormat.format(date);
		String yearString = dateCreation.substring(0, 4);
		int yearInt = Integer.parseInt(yearString);
		String thisYear = yearString + "-" + (yearInt + 1);
		String secondYear = (yearInt + 1) + "-" + (yearInt + 2);
		String thirdYear = (yearInt + 2) + "-" + (yearInt + 3);
		String fourthYear = (yearInt + 3) + "-" + (yearInt + 4);
		String fifthYear = (yearInt + 4) + "-" + (yearInt + 5);

		// on cree le beans avec le processus JPA qui va creer le datastore en meme
		// temps

		// instanciation du bean Utilisateur
		Pool mBean = new Pool();
		mBean.setPoolID(poolID);
		mBean.setPoolName(nomDuPool);
		mBean.setCreationDate(dateCreation);
		mBean.setDraftType(Integer.parseInt(typeDraft));
		mBean.setTradeType(Integer.parseInt(typeTrade));
		mBean.setPoolType(typePool);
		mBean.setNumberTeam(Integer.parseInt(nombreEquipe));
		mBean.setNumTeamCreate(numTeamCreate);
		mBean.setPoolYear(poolYear);
		mBean.setNomTeam1(nomDuTeam);
		mBean.setCodeValidationPool(codeValidation);
		mBean.setCycleAnnuel(0);
		mBean.setDraftDate(null);
		mBean.setThisYear(thisYear);
		mBean.setFirstYear(thisYear);
		mBean.setSecondYear(secondYear);
		mBean.setThirdYear(thirdYear);
		mBean.setFourthYear(fourthYear);
		mBean.setFifthYear(fifthYear);
		mBean.setLogoTeam1(urlLogoTeam);

		// on place le bean dans un attribut de session
		req.getSession().setAttribute("Pool", mBean);
		// on persiste dans le datastore via notre EntityManager
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity mEntity = mBean.mapBeanToEntityForDatastore(mBean, mBean.getPoolID());

		datastore.put(mEntity);
		// on persist le datastore/bean dans la MemCache

		// instanciation du bean Utilisateur
		mBeanUser.setUrlTeamLogo(urlLogoTeam);

		// on place le bean dans un attribut de session
		req.getSession().setAttribute("Utilisateur", mBeanUser);
		// on persiste dans le datastore via notre EntityManager
		datastore = DatastoreServiceFactory.getDatastoreService();


		mEntity = mBeanUser.mapBeanToEntityForDatastore(mBeanUser, mBeanUser.getNomUtilisateur());

		datastore.put(mEntity);

	}

	public void createEquipeBean(HttpServletRequest req) {

		// valeur de depart de la table Equipe
		initEquipeStorage(req);

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		String poolID = Integer.toString(mBeanUser.getPoolId());

		// on reparse en int pour le stockage
		int poolId = Integer.parseInt(poolID);

		nombreEquipe = req.getParameter("nombreEquipe");

		for (int i = 1; i < (Integer.parseInt(nombreEquipe) + 1); i++) {

			// on cree le beans avec le processus JPA qui va creer le datastore en meme
			// temps

			// instanciation du bean Utilisateur
			Equipe mBean = new Equipe();

			String jspSessionName = "Equipe" + i;
			String datastoreId = poolID + "_" + i;

			mBean.setPoolTeamId(datastoreId);
			mBean.setPoolId(poolId);
			mBean.setTeamId(i);
			mBean.setMax_salaire_begin(max_salaire_begin);
			mBean.setTotal_salaire_now(total_salaire_now);
			mBean.setBudget_restant(budget_restant);
			mBean.setMoy_sal_restant_draft(moy_sal_restant_draft);
			mBean.setNb_attaquant(nb_attaquant);
			mBean.setNb_defenseur(nb_defenseur);
			mBean.setNb_gardien(nb_gardien);
			mBean.setNb_rookie(nb_rookie);
			mBean.setNb_contrat(nb_contrat);
			mBean.setNb_equipe(nb_equipe);
			mBean.setManquant_att(manquant_att);
			mBean.setManquant_def(manquant_def);
			mBean.setManquant_gardien(manquant_gardien);
			mBean.setManquant_recrue(manquant_recrue);
			mBean.setManquant_equipe(manquant_equipe);
			mBean.setArgent_recu(argent_recu);
			mBean.setBonus_5m(bonus_5m);
			mBean.setBonus_penalite(bonus_penalite);
			mBean.setClassement_last_year(0);
			mBean.setMeilleur_classement(0);
			mBean.setNum_annee(1);
			mBean.setNum_champion(0);

			// on place le bean dans un attribut de session
			req.getSession().setAttribute(jspSessionName, mBean);
			// on persiste dans le datastore via notre EntityManager
			String counter = String.valueOf(i);
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool").param("counter", counter)
					.param("poolID", poolID).param("teamId", String.valueOf(i))
					.param("max_salaire_begin", String.valueOf(max_salaire_begin))
					.param("moy_sal_restant_draft", String.valueOf(moy_sal_restant_draft))
					.param("nb_attaquant", String.valueOf(nb_attaquant))
					.param("nb_defenseur", String.valueOf(nb_defenseur)).param("nb_gardien", String.valueOf(nb_gardien))
					.param("nb_rookie", String.valueOf(nb_rookie)).param("nb_contrat", String.valueOf(nb_contrat))
					.param("nb_equipe", String.valueOf(nb_equipe)).param("manquant_att", String.valueOf(manquant_att))
					.param("manquant_def", String.valueOf(manquant_def))
					.param("manquant_gardien", String.valueOf(manquant_gardien))
					.param("manquant_recrue", String.valueOf(manquant_recrue))
					.param("manquant_equipe", String.valueOf(manquant_equipe))
					.param("argent_recu", String.valueOf(argent_recu)).param("bonus_5m", String.valueOf(bonus_5m))
					.param("bonus_penalite", String.valueOf(bonus_penalite))
					.param("classement_last_year", String.valueOf(0)).param("meilleur_classement", String.valueOf(0))
					.param("num_annee", String.valueOf(1)).param("num_champion", String.valueOf(0))
					.param("budget_restant", String.valueOf(budget_restant))
					.param("total_salaire_now", String.valueOf(total_salaire_now)).param("fromTag", "1"));

		}

	}

	public void sendEmail(HttpServletRequest req) {
		initEmailFromFormulaire(req);

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolId = mBeanUser.getPoolId();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String code = mBeanPool.getCodeValidationPool();

		int teamCount = Integer.parseInt(nombreEquipe);
		String courriel = "info@poolavie.ca";
		for (int i = 1; i <= (teamCount - 1); i++) {

			switch (i) {
			case 1:
				if (email1 != null) {
					courriel = email1;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 2:
				if (email2 != null) {
					courriel = email2;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 3:
				if (email3 != null) {
					courriel = email3;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 4:
				if (email4 != null) {
					courriel = email4;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 5:
				if (email5 != null) {
					courriel = email5;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 6:
				if (email6 != null) {
					courriel = email6;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 7:
				if (email7 != null) {
					courriel = email7;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 8:
				if (email8 != null) {
					courriel = email8;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 9:
				if (email9 != null) {
					courriel = email9;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 10:
				if (email10 != null) {
					courriel = email10;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;
			case 11:
				if (email11 != null) {
					courriel = email11;
				} else {
					courriel = "info@poolavie.ca";
				}
				break;

			}

			int playerId = i + 1;

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("pedagogiksolution@gmail.com", "Poolavie.ca"));
				msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(courriel));
				msg.setSubject("Invitation pour un Pool à vie", "utf-8");
				msg.setContent(
						"Bonjour, un de vos amis vous invite à participer à un pool de hockey sur la plateforme de www.poolavie.ca."
								+ "\n\n Vous pouvez dès maintenant aller créer les bases de votre équipe en suivant le lien ci-bas."
								+ "\n\n <a href='http://www.poolavie.ca/CreationDirecteurGeneral?po=" + poolId + "&pl="
								+ playerId + "&co=" + code + "&fo=1'>Créer mon équipe</a>",
						"text/html");
				Transport.send(msg);
			} catch (AddressException e) {
				// ...
			} catch (MessagingException e) {
				// ...
			} catch (UnsupportedEncodingException e) {
				// ...
			}

		}

	}

	public void createDatabase(HttpServletRequest req) {

		// on recupere le nom du team
		nomDuTeam = req.getParameter("nomDuTeam");
		// on recupere le numero du Pool et de l'equipe
		Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolID = mBean.getPoolId();
		int teamID = mBean.getTeamId();
		// on trouve la date de l'annee
		// TODO rendre dynamique
		int years = 2017;

		// on recupere le nombre d'equipe et le nombre de joueurs par equipe
		nombreEquipe = req.getParameter("nombreEquipe");
		int numTeam = Integer.parseInt(nombreEquipe);
		int numPickByTeam = 30;

		// on cree les bases de donnee classement et insere la ligne
		classementDao.createClassementTable(poolID);
		classementDao.createClassementArchiveTable(poolID);
		classementDao.insertTeamInClassement(nomDuTeam, teamID, poolID, years);

		// on cree les bases de donnee player
		playersDao.createPlayersTable(poolID);
		playersDao.createPlayersArchiveTable(poolID);
		
		playersDao.setCanBeRookie(String.valueOf(poolID));

		draftDao.createDraftTable(poolID);
		draftDao.createDraftArchiveTable(poolID);

		tradeMadeDao.createTradeMadeTable(poolID);
		tradeMadeDao.createTradeMadeArchiveTable(poolID);

		tradeOfferDao.createTradeOfferTable(poolID);
		tradeOfferDao.createTradeOfferArchiveTable(poolID);

		salaireDao.createSalaireTable(poolID);

		draftPickDao.createDraftPickTable(poolID);
		draftPickDao.insertPickByTeam(poolID, numTeam, numPickByTeam);

	}

	public void createSucceed(HttpServletRequest req) {
		// TODO on ajuste la valeur du Bean Pool firstConnexionFinish pour la valeur 1
		// dans tous les storages

		// check if session exist and parse,modify and persist if yes
		Utilisateur mBean = new Utilisateur();
		mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");

		String nomDuTeam = req.getParameter("nomDuTeam");

		if (mBean != null) {
			// bean
			mBean.setFirstConnexionFinish(1);
			mBean.setTeamName(nomDuTeam);
			// session
			req.getSession().setAttribute("Utilisateur", mBean);

			// Datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Entity mEntity = mBean.mapBeanToEntityForDatastore(mBean,mBean.getNomUtilisateur());
			
			datastore.put(mEntity);

		} else {
			// TODO need to be connect, return erreur
		}

	}

	/**********************************
	 * Methode privée de la classe
	 *******************************/

	private void initParamFromFormulaire(HttpServletRequest req) {

		// on recupere les inputs du formulaire de la page creationnouveaupool.jsp
		nomDuPool = req.getParameter("nomDuPool");
		nombreEquipe = req.getParameter("nombreEquipe");
		typeTrade = req.getParameter("typeTrade");
		typeDraft = req.getParameter("typeDraft");
		nomDuTeam = req.getParameter("nomDuTeam");
		urlLogoTeam = req.getParameter("logoUrlTeam");

	}

	private void initEmailFromFormulaire(HttpServletRequest req) {

		nombreEquipe = req.getParameter("nombreEquipe");

		email1 = req.getParameter("email1");
		email2 = req.getParameter("email2");
		email3 = req.getParameter("email3");
		email4 = req.getParameter("email4");
		email5 = req.getParameter("email5");
		email6 = req.getParameter("email6");
		email7 = req.getParameter("email7");
		if (nombreEquipe.equals("9")) {
			email8 = req.getParameter("email8");
		}
		if (nombreEquipe.equals("10")) {
			email9 = req.getParameter("email9");
		}
		if (nombreEquipe.equals("11")) {
			email10 = req.getParameter("email10");
		}
		if (nombreEquipe.equals("12")) {
			email11 = req.getParameter("email11");
		}

	}

	private void initEquipeStorage(HttpServletRequest req) {

		max_salaire_begin = 52000000;
		total_salaire_now = 0;
		budget_restant = 52000000;
		moy_sal_restant_draft = 0;
		nb_attaquant = 0;
		nb_defenseur = 0;
		nb_gardien = 0;
		nb_rookie = 0;
		nb_contrat = 0;
		nb_equipe = 0;
		manquant_equipe = 22;
		manquant_att = 8;
		manquant_def = 5;
		manquant_gardien = 2;
		manquant_recrue = 8;
		bonus_5m = 0;
		argent_recu = 0;
		bonus_penalite = 0;

	}

	// TODO methode privee pour generer un code alphanumerique de 8 carateres
	private String generateValidationCode() {

		// genere un code si reussi, return le code, sinon retourne null

		/*
		 * presentemetn, on assigne toujours le code pour test de emailing et validation
		 * process
		 */
		String fakeCodeForTest;

		fakeCodeForTest = "A1B1C1D1";

		return fakeCodeForTest;

	}

	public Boolean checkIfDateIsGoodForNewPool() {
		// TODO si entre 1 janvier et 1 juillet, retourner false pour fermer les
		// inscriptions
		return true;
	}

}
