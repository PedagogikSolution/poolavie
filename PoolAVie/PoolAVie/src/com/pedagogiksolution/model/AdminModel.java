package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PASSWORD_ENCRYPTION;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.pedagogiksolution.beans.Article;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.cron.model.DraftPickCronModel;
import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.PasswordEncryption;

public class AdminModel {

	private DraftDao draftDao;

	public AdminModel() {

	}

	public AdminModel(DraftDao draftDao) {
		this.draftDao = draftDao;
	}

	public void setDraftTime(String dateDraft, String heureDraft, HttpServletRequest req) {

		// on met dans bon format de persistence, on change valeur de cycle a 2, on
		// persist dans POOL la date et l'heure

		StringBuilder sb = new StringBuilder(dateDraft);
		sb.append(" ").append(heureDraft);

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		mBeanPool.setDraftDate(sb.toString());
		mBeanPool.setCycleAnnuel(2);

		req.getSession().setAttribute("Pool", mBeanPool);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, mBeanPool.getPoolID());

		datastore.put(mEntity);
	}

	public void checkIfDraftDay(Pool mBeanPool, HttpServletRequest req) {
		// on verifie si c'est la date du draft, si oui, on met le cycle annuel a 3
		String dateDraft = mBeanPool.getDraftDate();
		int poolYearId = mBeanPool.getPoolYear();

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		DateTime dt = formatter.parseDateTime(dateDraft);

		if (dt.isBeforeNow()) {

			mBeanPool.setCycleAnnuel(3);

			if (poolYearId == 0) {
				mBeanPool.setPoolYear((poolYearId + 1));
			}

			req.getSession().setAttribute("Pool", mBeanPool);

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			Entity mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, mBeanPool.getPoolID());

			datastore.put(mEntity);

		}

	}

	public void determineOrderOfDraft(HttpServletRequest req) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolID);

		int numberTeam = mBeanPool.getNumberTeam();
		ArrayList<Integer> permutation2 = new ArrayList<>();
		List<Integer> permutation=null;

		
		if (poolId == 4) {
			switch (numberTeam) {
			
			case 10:
				permutation2.add(1);
				permutation2.add(2);
				permutation2.add(4);
				permutation2.add(5);
				permutation2.add(6);
				permutation2.add(7);
				permutation2.add(8);
				permutation2.add(9);
				permutation2.add(10);
				Collections.shuffle(permutation2);
				permutation2.add(3);
				break;
			
			}
			

			draftDao.populateFirstYearsDraft(poolId, permutation2, mBeanPool);
			
			
		} else {
			switch (numberTeam) {
			case 8:
				permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
				Collections.shuffle(permutation);
				break;
			case 9:
				permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
				Collections.shuffle(permutation);
				break;
			case 10:
				permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
				Collections.shuffle(permutation);
				break;
			case 11:
				permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
				Collections.shuffle(permutation);
				break;
			case 12:
				permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
				Collections.shuffle(permutation);
				break;
			}

			draftDao.populateFirstYearsDraft(poolId, permutation, mBeanPool);
			
		}
		// On persist dans Database et dans Datastore et dans MemCache et dans Bean


		DraftRound mBeanDraft = draftDao.setDraftRoundOrder(poolId);

		req.getSession().setAttribute("DraftRound", mBeanDraft);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity mEntity = mBeanDraft.mapBeanToEntityForDatastore(mBeanDraft, mBeanDraft.getPoolId());

		datastore.put(mEntity);

	}

	public void annulationStartDraft(HttpServletRequest req) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		mBeanPool.setCycleAnnuel(2);

		req.getSession().setAttribute("Pool", mBeanPool);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, mBeanPool.getPoolID());

		datastore.put(mEntity);

	}

	public void envoieCourrielDateEtOrdreDeDraft(HttpServletRequest req, String dateDraft, String heureDraft) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolID);

		// on appel le service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Use class Query to assemble a query
		Query q = new Query("Utilisateur");
		q.setFilter(new Query.FilterPredicate("poolId", Query.FilterOperator.EQUAL, poolId));

		PreparedQuery pq = datastore.prepare(q);
		List<Entity> entity = pq.asList(FetchOptions.Builder.withDefaults());

		for (Entity results : entity) {

			// Long typeUser = (Long) results.getProperty("typeUtilisateur");

			String courriel = (String) results.getProperty("courriel");
			String nomTeam = (String) results.getProperty("teamName");
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("pedagogiksolution@gmail.com", "Poolavie.ca"));
				msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(courriel));
				msg.setSubject("Date et Ordre de Draft", "utf-8");
				msg.setContent("Bonjour " + nomTeam
						+ ", votre administrateur de pool a déterminé une date pour votre draft. \n\n"
						+ "Celui-ci aura lieu le " + dateDraft + " à " + heureDraft
						+ ". Vous pourrez alors vous connecter au serveur de draft"
						+ " via l'onglet Draft ou en cliquant sur l'alerte en rouge lorsque vous vous connecterez"
						+ " sur le site et que vous arriverez à la page des nouvelles. \n\n Vous trouverez immédiatement aussi dans la section Draft Center l'ordre de draft des 20 premiers picks.\n\n Votre administrateur",
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

	public void resetCycleAnnuelTo2(HttpServletRequest req) {

	}

	@SuppressWarnings("unchecked")
	public Boolean changeCredential(String username, String password, String email, String teamName, String logoTeam,
			HttpServletRequest req) {
		Entity entityUser, entityPool, entityClassement, entityDraftPick;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key mUserKey1 = null, mUserKey2;

		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		Transaction txn = datastore.beginTransaction(options);

		try {

			// 1- si username pas vide, on le change
			if (username != null && username != "") {

				RegisterModel mModelRegister = new RegisterModel();
				Boolean checkIfUsernameExist = mModelRegister.checkIfUsernameExist(username, req);

				if (checkIfUsernameExist) {

					req.setAttribute("messageErreurChangementUsername",
							"Ce nom d'utilisateur existe déjà, merci de bien vouloir en choisir un différent");
					return false;
				}

				Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
				String nomUtilisateur = mBeanUser.getNomUtilisateur();
				mUserKey1 = KeyFactory.createKey("Utilisateur", nomUtilisateur);
				Utilisateur mNewBeanUser = new Utilisateur();
				try {
					entityUser = datastore.get(mUserKey1);

					mUserKey2 = KeyFactory.createKey("Utilisateur", username);
					Entity newEntityUser = new Entity(mUserKey2);
					newEntityUser.setPropertiesFrom(entityUser);
					datastore.delete(mUserKey1);
					datastore.put(txn, newEntityUser);

					// on map pour mettre dans Bean de session et memcache
					mNewBeanUser = mNewBeanUser.mapUtilisateurFromDatastore(newEntityUser, mBeanUser);

					// on place le bean dans un attribut de session
					req.getSession().setAttribute("Utilisateur", mNewBeanUser);
					// on persist le datastore/bean dans la MemCache pour appel au pool ID,
					// typeUtilisateur, teamId lors
					// du

				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				req.setAttribute("messageConfirmationChangementUsername", "Votre nom d'utilisateur a été modifié");

			}

			// 2- Si password pas vide, on le change
			if (password != null && password != "") {
				// On encrypte le mot de passe
				PasswordEncryption mEncryptProcess = new PasswordEncryption();
				String motDePasseEncrypter = null;
				try {
					motDePasseEncrypter = mEncryptProcess.passwordEncryption(password);
				} catch (NoSuchAlgorithmException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
					req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				} catch (InvalidKeySpecException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
					req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

				// on recupere le nouveau beanUser ou le vieux, selon si etape 1 fait ou pas
				Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
				String nomUtilisateur = mBeanUser.getNomUtilisateur();
				mUserKey1 = KeyFactory.createKey("Utilisateur", nomUtilisateur);

				try {
					entityUser = datastore.get(mUserKey1);

					// on change le mot de passe
					entityUser.setProperty("motDePasse", motDePasseEncrypter);

					datastore.put(txn, entityUser);

					// on map pour mettre dans Bean de session et memcache
					mBeanUser = mBeanUser.mapUtilisateurFromDatastore(entityUser, mBeanUser);

					// on place le bean dans un attribut de session
					req.getSession().setAttribute("Utilisateur", mBeanUser);
					// on persist le datastore/bean dans la MemCache pour appel au pool ID,
					// typeUtilisateur, teamId lors
					// du

				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				req.setAttribute("messageConfirmationChangementPassword", "Votre mot de passe a été modifié");

			}

			// 3- Si courriel pas vide on le change
			if (email != null && email != "") {

				// on recupere le nouveau beanUser ou le vieux, selon si etape 1 fait ou pas
				Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
				String nomUtilisateur = mBeanUser.getNomUtilisateur();
				mUserKey1 = KeyFactory.createKey("Utilisateur", nomUtilisateur);

				try {
					entityUser = datastore.get(mUserKey1);

					// on change le mot de passe
					entityUser.setProperty("courriel", email);

					datastore.put(txn, entityUser);

					// on map pour mettre dans Bean de session et memcache
					mBeanUser = mBeanUser.mapUtilisateurFromDatastore(entityUser, mBeanUser);

					// on place le bean dans un attribut de session
					req.getSession().setAttribute("Utilisateur", mBeanUser);
					// on persist le datastore/bean dans la MemCache pour appel au pool ID,
					// typeUtilisateur, teamId lors
					// du

				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				req.setAttribute("messageConfirmationChangementCourriel", "Votre courriel a été modifié");
			}
			// 4- Si teamName pas vide on le change
			if (teamName != null && teamName != "") {

				// A- on change la valeur dans le datastore Utilisateur
				Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
				String nomUtilisateur = mBeanUser.getNomUtilisateur();
				mUserKey1 = KeyFactory.createKey("Utilisateur", nomUtilisateur);

				try {
					entityUser = datastore.get(mUserKey1);

					// on change le mot de passe
					entityUser.setProperty("teamName", teamName);

					datastore.put(txn, entityUser);

					// on map pour mettre dans Bean de session et memcache
					mBeanUser = mBeanUser.mapUtilisateurFromDatastore(entityUser, mBeanUser);

					// on place le bean dans un attribut de session
					req.getSession().setAttribute("Utilisateur", mBeanUser);
					// on persist le datastore/bean dans la MemCache pour appel au pool ID,
					// typeUtilisateur, teamId lors
					// du

				} catch (EntityNotFoundException e) {
					e.printStackTrace();
				}

				// B- on change la valeur dans le datastore Pool
				Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
				String poolID = mBeanPool.getPoolID();
				int teamId = mBeanUser.getPoolId();
				String nomPropertyTeamName = switchToGetPropertyTeamName(teamId);

				mUserKey1 = KeyFactory.createKey("Pool", poolID);

				try {
					entityPool = datastore.get(mUserKey1);

					// on change le mot de passe
					entityPool.setProperty(nomPropertyTeamName, teamName);

					datastore.put(txn, entityPool);

					// on map pour mettre dans Bean de session et memcache
					mBeanPool = mBeanPool.mapPoolFromDatastore(entityPool, mBeanPool);

					// on place le bean dans un attribut de session
					req.getSession().setAttribute("Pool", mBeanPool);
					// on persist le datastore/bean dans la MemCache pour appel au pool ID,
					// typeUtilisateur, teamId lors
					// du

				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// C- on change la valeur dans le datastore Classement
				Classement mBeanClassement = (Classement) req.getSession().getAttribute("Classement");
				mUserKey1 = KeyFactory.createKey("Classement", poolID);
				String teamNameToReplace = mBeanUser.getTeamName();
				try {
					entityClassement = datastore.get(mUserKey1);

					// on change le mot de passe
					List<String> equipe = (List<String>) entityClassement.getProperty("equipe");
					int indexTeamName = equipe.indexOf(teamNameToReplace);
					equipe.set(indexTeamName, teamName);
					entityClassement.setProperty("equipe", equipe);

					datastore.put(txn, entityClassement);

					// on map pour mettre dans Bean de session et memcache
					mBeanClassement = mBeanClassement.mapClassementFromDatastore(entityClassement, mBeanClassement);

					// on place le bean dans un attribut de session
					req.getSession().setAttribute("Classement", mBeanClassement);
					// on persist le datastore/bean dans la MemCache pour appel au pool ID,
					// typeUtilisateur, teamId lors
					// du

				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// D- on change la valeur dans le datastore DraftPick
				DraftPick mBeanDraftPick = (DraftPick) req.getSession().getAttribute("DraftPick");

				int numberOfTeam = mBeanPool.getNumberTeam();

				for (int i = 1; i < numberOfTeam + 1; i++) {
					String nomClefDraftPick = poolID + "_" + i;
					mUserKey1 = KeyFactory.createKey("DraftPick", nomClefDraftPick);

					try {
						entityDraftPick = datastore.get(mUserKey1);

						// on recupere la property a modifier
						List<String> teamNameOriginalPick = (List<String>) entityDraftPick
								.getProperty("teamNameOriginalPick");

						ListIterator<String> iterator = teamNameOriginalPick.listIterator();
						int counter = 1;
						while (iterator.hasNext()) {
							iterator.next();
							if (iterator.equals(teamNameToReplace)) {

								teamNameOriginalPick.set(counter, teamName);
							}
						}

						entityDraftPick.setProperty("teamNameOriginalPick", teamNameOriginalPick);

						datastore.put(txn, entityDraftPick);

						// on map pour mettre dans Bean de session et memcache
						mBeanDraftPick = mBeanDraftPick.mapDraftPickFromDatastore(entityDraftPick, mBeanDraftPick);

						// on place le bean dans un attribut de session
						req.getSession().setAttribute("DraftPick", mBeanDraftPick);
						// on persist le datastore/bean dans la MemCache pour appel au pool ID,
						// typeUtilisateur, teamId
						// lors

					} catch (EntityNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// E- on change la valeur dans le datastore Articles
				Article mBeanArticles = (Article) req.getSession().getAttribute("Articles");
				List<String> titre = new ArrayList<String>();
				List<String> body = new ArrayList<String>();
				List<String> dateCreation = new ArrayList<String>();
				List<String> writerName = new ArrayList<String>();
				List<String> writerLogo = new ArrayList<String>();
				Filter allArticle = new FilterPredicate("writerName", FilterOperator.EQUAL, teamNameToReplace);
				Query q = new Query("Articles").setFilter(allArticle)
						.setAncestor(KeyFactory.createKey("Nouvelles", poolID));
				List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());

				for (Entity result : results) {
					result.setProperty("writerName", teamName);
					datastore.put(txn, result);
					String titre2 = (String) result.getProperty("titre");
					titre.add(titre2);
					String body2 = (String) result.getProperty("body");
					body.add(body2);
					Date dateCreation2 = (Date) result.getProperty("date");
					String dateCreation3 = dateCreation2.toString();
					dateCreation.add(dateCreation3);
					String writerName2 = (String) result.getProperty("writerName");
					writerName.add(writerName2);
					String writerLogo2 = (String) result.getProperty("writerLogo");
					writerLogo.add(writerLogo2);

				}

				// on place le bean dans un attribut de session
				mBeanArticles.setTitre(titre);
				mBeanArticles.setBody(body);
				mBeanArticles.setDateCreation(dateCreation);
				mBeanArticles.setWriterName(writerName);
				mBeanArticles.setWriterLogo(writerLogo);

				req.getSession().setAttribute("Articles", mBeanArticles);

				req.setAttribute("messageConfirmationChangementNomTeam", "Votre nom d'équipe a été modifié");

				// F- on change la valeur dans la BDD classement

				// G- on change la valeur dans la BDD Draft

				// 5- Si logoTeam pas vide on le change

			}

			txn.commit();
			return true;
		}

		finally {
			if (txn.isActive()) {
				txn.rollback();

			}

		}

	}

	public void sendConfirmationEmailAfterChange(String email, HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	public void openTrade(HttpServletRequest req) {

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolId = mBeanUser.getPoolId();
		String poolID = String.valueOf(poolId);
		Pool mBeanPool = new Pool();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Pool", poolID);

		try {
			Entity mEntity = datastore.get(mKey);
			Long cycleAnnuel = Long.valueOf(6);
			mEntity.setProperty("cycleAnnuel", cycleAnnuel);
			datastore.put(mEntity);

			mBeanPool = mBeanPool.mapPoolFromDatastore(mEntity, mBeanPool);
			mBeanPool.setCycleAnnuel(6);
			req.getSession().removeAttribute("Pool");
			req.getSession().setAttribute("Pool", mBeanPool);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void closeTrade(HttpServletRequest req) {
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolId = mBeanUser.getPoolId();
		String poolID = String.valueOf(poolId);
		Pool mBeanPool = new Pool();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Pool", poolID);

		try {
			Entity mEntity = datastore.get(mKey);
			Long cycleAnnuel = Long.valueOf(5);
			mEntity.setProperty("cycleAnnuel", cycleAnnuel);
			mBeanPool = mBeanPool.mapPoolFromDatastore(mEntity, mBeanPool);

			datastore.put(mEntity);
			mBeanPool.setCycleAnnuel(5);

			req.getSession().removeAttribute("Pool");
			req.getSession().setAttribute("Pool", mBeanPool);

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeCycleAnnuel(HttpServletRequest req, int i) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		String name = mBeanPool.getPoolID();

		mBeanPool.setCycleAnnuel(i);

		Entity entity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, name);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		datastore.put(entity);

	}

	public void writeNewsAndEmailForWinner(HttpServletRequest req) {
		// TODO envoyer courriel et ecrire news

	}

	public void updateAgeForRookie(HttpServletRequest req, PlayersDao playersDao) {
		playersDao.updateAgeForRookie(req);

	}

	public void archivageFinSaison(HttpServletRequest req, ClassementDao classementDao, PlayersDao playersDao,
			TradeMadeDao tradeMadeDao, DraftDao draftDao) {

		classementDao.insertionDansArchives(req);
		tradeMadeDao.insertionDansArchives(req);
		draftDao.insertionDansArchives(req);

		playersDao.insertionDansArchives(req);

	}

	public void resetDatastorePoolEntity(HttpServletRequest req) {
		// on doit faire le update du datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		String poolID = mBeanPool.getPoolID();

		Key mKey = KeyFactory.createKey("Pool", poolID);

		try {
			Entity entity = datastore.get(mKey);

			mBeanPool = mBeanPool.mapPoolFromDatastore(entity, mBeanPool);

			String yearString = mBeanPool.getThisYear().substring(5);
			int yearInt = Integer.parseInt(yearString);

			String fifthYear = (yearInt + 4) + "-" + (yearInt + 5);

			mBeanPool.setThisYear(mBeanPool.getSecondYear());
			mBeanPool.setSecondYear(mBeanPool.getThirdYear());
			mBeanPool.setThirdYear(mBeanPool.getFourthYear());
			mBeanPool.setFourthYear(mBeanPool.getFifthYear());
			mBeanPool.setFifthYear(fifthYear);
			mBeanPool.setPoolYear(mBeanPool.getPoolYear() + 1);

			entity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, poolID);

			datastore.put(entity);
			

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void resetDatastoreEquipeEntity(HttpServletRequest req, PlayersDao playersDao) {
		// on remet les budgets en mode debut d'année
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		String poolID = mBeanPool.getPoolID();

		int numberOfTeam = mBeanPool.getNumberTeam();

		for (int i = 1; i < numberOfTeam + 1; i++) {

			String keyTeam = poolID + "_" + i;
			Key mKey = KeyFactory.createKey("Equipe", keyTeam);
			Equipe mBeanEquipe = new Equipe();
			try {
				Entity entity = datastore.get(mKey);

				mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(entity, mBeanEquipe);

				// on transfert argent recu, ajoute bonus ou malus et ensuite on reset a 52
				// Millions et recalcule les positions par team
				int argent_recu = mBeanEquipe.getArgent_recu();
				int total_salaire_now = getSalaireTotalOnTeam(playersDao, poolID, i);
				int nb_contrat = getNbContrat(playersDao, poolID, i);
				int nb_attaquant = getNbAttaquant(playersDao, poolID, i);
				int nb_defenseur = getNbDefenseur(playersDao, poolID, i);
				int nb_gardien = getNbGardien(playersDao, poolID, i);
				int nb_rookie = getNbRookie(playersDao, poolID, i);

				int nb_equipe = nb_attaquant + nb_defenseur + nb_gardien;
				int manquant_att = 0;
				int manquant_def = 0;
				int manquant_gardien = 0;
				int manquant_recrue = 0;
				int manquant_equipe = 0;
				int moy_sal_restant_draft = 0;
				if (nb_attaquant < 8) {
					manquant_att = 8 - nb_attaquant;
				}
				if (nb_defenseur < 5) {
					manquant_def = 5 - nb_defenseur;
				}
				if (nb_gardien < 2) {
					manquant_gardien = 2 - nb_gardien;
				}
				if (nb_rookie < 8) {
					manquant_recrue = 8 - nb_rookie;
				}
				if (nb_equipe < 22) {
					manquant_equipe = 22 - nb_equipe;
				}

				int bonus_malus = getBonusMalusFromFinalStanding(datastore, poolID, i);

				int max_salaire_begin = 52000000 + argent_recu + bonus_malus;

				int budget_restant = max_salaire_begin - total_salaire_now;

				mBeanEquipe.setArgent_recu(0);
				mBeanEquipe.setBonus_5m(0);
				mBeanEquipe.setBonus_penalite(0);
				mBeanEquipe.setBudget_restant(budget_restant);
				mBeanEquipe.setMax_salaire_begin(max_salaire_begin);
				mBeanEquipe.setNb_attaquant(nb_attaquant);
				mBeanEquipe.setNb_defenseur(nb_defenseur);
				mBeanEquipe.setNb_gardien(nb_gardien);
				mBeanEquipe.setNb_rookie(nb_rookie);
				mBeanEquipe.setNb_equipe(nb_equipe);
				mBeanEquipe.setNb_contrat(nb_contrat);
				mBeanEquipe.setManquant_att(manquant_att);
				mBeanEquipe.setManquant_def(manquant_def);
				mBeanEquipe.setManquant_gardien(manquant_gardien);
				mBeanEquipe.setManquant_recrue(manquant_recrue);
				mBeanEquipe.setManquant_equipe(manquant_equipe);
				mBeanEquipe.setTotal_salaire_now(total_salaire_now);

				if (manquant_equipe != 0) {
					moy_sal_restant_draft = budget_restant / manquant_equipe;
				}

				mBeanEquipe.setMoy_sal_restant_draft(moy_sal_restant_draft);

				entity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, keyTeam);

				datastore.put(entity);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private int getNbRookie(PlayersDao playersDao, String poolID, int i) {
		int nb_rookie = playersDao.getNbRookie(poolID, i);
		return nb_rookie;
	}

	private int getNbGardien(PlayersDao playersDao, String poolID, int i) {
		int nb_gardien = playersDao.getNbGardien(poolID, i);
		return nb_gardien;
	}

	private int getNbDefenseur(PlayersDao playersDao, String poolID, int i) {
		int nb_defenseur = playersDao.getNbDefenseur(poolID, i);
		return nb_defenseur;
	}

	private int getNbAttaquant(PlayersDao playersDao, String poolID, int i) {
		int nb_attaquant = playersDao.getNbAttaquant(poolID, i);
		return nb_attaquant;
	}

	private int getNbContrat(PlayersDao playersDao, String poolID, int i) {
		int nb_contrat = playersDao.getNbContrat(poolID, i);
		return nb_contrat;
	}

	private int getSalaireTotalOnTeam(PlayersDao playersDao, String poolID, int i) {
		int total_salaire_now = playersDao.getTotalSalaireNow(poolID, i);
		return total_salaire_now;
	}

	@SuppressWarnings("unchecked")
	private int getBonusMalusFromFinalStanding(DatastoreService datastore, String poolID, int i) {
		Key mKey = KeyFactory.createKey("Classement", poolID);
		int bonus_malus = 0;
		int positionClassement = 0;
		int numberOfTeam = 8;
		try {
			Entity mEntity = datastore.get(mKey);

			List<Long> m_team_id = (List<Long>) mEntity.getProperty("team_id");

			positionClassement = m_team_id.indexOf(Long.valueOf(i));
			numberOfTeam = m_team_id.size();

		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (numberOfTeam) {

		case 8:
			switch (positionClassement) {

			case 0:
				bonus_malus = 3000000;
				break;

			case 1:
				bonus_malus = 2000000;
				break;
			case 2:
				bonus_malus = 1000000;
				break;

			case 5:
				bonus_malus = -1000000;
				break;
			case 6:
				bonus_malus = -2000000;
				break;
			case 7:
				bonus_malus = -3000000;
				break;

			default:
				bonus_malus = 0;
			}
			break;

		case 9:
			switch (positionClassement) {

			case 0:
				bonus_malus = 3000000;
				break;
			case 1:
				bonus_malus = 2000000;
				break;
			case 3:
				bonus_malus = 1000000;
				break;

			case 6:
				bonus_malus = -500000;
				break;
			case 7:
				bonus_malus = -1000000;
				break;
			case 8:
				bonus_malus = -2000000;
				break;
			default:
				bonus_malus = 0;
				break;
			}
			break;

		case 10:
			switch (positionClassement) {

			case 0:
				bonus_malus = 3000000;
				break;
			case 1:
				bonus_malus = 2000000;
				break;
			case 2:
				bonus_malus = 1000000;
				break;
			case 3:
				bonus_malus = 500000;
				break;
			case 6:
				bonus_malus = -500000;
				break;
			case 7:
				bonus_malus = -1000000;
				break;
			case 8:
				bonus_malus = -2000000;
				break;
			case 9:
				bonus_malus = -3000000;
				break;
			default:
				bonus_malus = 0;
				break;
			}
			break;

		case 11:
			switch (positionClassement) {

			case 0:
				bonus_malus = 3000000;
				break;
			case 1:
				bonus_malus = 2000000;
				break;
			case 2:
				bonus_malus = 1000000;
				break;
			case 3:
				bonus_malus = 500000;
				break;
			case 7:
				bonus_malus = -500000;
				break;
			case 8:
				bonus_malus = -1000000;
				break;
			case 9:
				bonus_malus = -2000000;
				break;
			case 10:
				bonus_malus = -3000000;
				break;
			default:
				bonus_malus = 0;
				break;
			}
			break;

		case 12:
			switch (positionClassement) {

			case 0:
				bonus_malus = 3000000;
				break;
			case 1:
				bonus_malus = 2000000;
				break;
			case 2:
				bonus_malus = 1000000;
				break;
			case 3:
				bonus_malus = 500000;
				break;
			case 8:
				bonus_malus = -500000;
				break;
			case 9:
				bonus_malus = -1000000;
				break;
			case 10:
				bonus_malus = -2000000;
				break;
			case 11:
				bonus_malus = -3000000;
				break;
			default:
				bonus_malus = 0;
				break;
			}
			break;

		}

		return bonus_malus;

	}

	public void dropJoueurJAETX(HttpServletRequest req, PlayersDao playersDao) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		playersDao.dropPlayersJaAndX(poolID);

	}

	public void resetFinAnneePlayers(HttpServletRequest req, PlayersDao playersDao, SalaireDao salaireDao) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();

		playersDao.updateProjection(poolID);

		playersDao.setCanBeRookie(poolID);

		playersDao.setTakeProj(poolID);

		playersDao.migratePtsToLastYear(poolID);

		playersDao.setSalaireForRookie(poolID, salaireDao);

		playersDao.moveYearsToYearsContract(poolID);

		playersDao.setSalaireDraft(poolID);

	}

	public void vidageEtResetTableBDD(HttpServletRequest req, ClassementDao classementDao, TradeMadeDao tradeMadeDao,
			DraftDao draftDao2, TradeOfferDao tradeOfferDao) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		String thisYear = mBeanPool.getThisYear();
		String years = thisYear.substring(0, 4);
		classementDao.resetClassement(poolID, years);
		tradeMadeDao.resetTradeMade(poolID);
		tradeOfferDao.truncateAfterYears(poolID);
	}

	public void dropAB(HttpServletRequest req, PlayersDao playersDao) {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		playersDao.dropPlayersAandB(poolID);

	}

	public Boolean preparationNouveauDraft(DraftDao draftDao2, HttpServletRequest req, PlayersDao playersDao,
			ClassementDao classementDao, DraftPickDao draftPickDao) {
		// drop rookie C et D pas monter
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		String thisYear = mBeanPool.getThisYear();
		String years = thisYear.substring(0, 4);
		int lastYear = Integer.parseInt(years) - 1;

		int nombreEquipe = mBeanPool.getNumberTeam();
		int numPickByTeam = 30;

		playersDao.dropPlayersCetD(req, poolID);
		
		// on compte le nombre de rookie par club et on ajuste
		
		// on remet les budgets en mode debut d'année
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			

				for (int i = 1; i < nombreEquipe + 1; i++) {

					String keyTeam = poolID + "_" + i;
					Key mKey = KeyFactory.createKey("Equipe", keyTeam);
					Equipe mBeanEquipe = new Equipe();
					try {
						Entity entity = datastore.get(mKey);

						mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(entity, mBeanEquipe);

						int nb_contrat = getNbContrat(playersDao, poolID, i);
						int nb_attaquant = getNbAttaquant(playersDao, poolID, i);
						int nb_defenseur = getNbDefenseur(playersDao, poolID, i);
						int nb_gardien = getNbGardien(playersDao, poolID, i);
						int nb_rookie = getNbRookie(playersDao, poolID, i);

						int nb_equipe = nb_attaquant + nb_defenseur + nb_gardien;
						
						int manquant_att = 0;
						int manquant_def = 0;
						int manquant_gardien = 0;
						int manquant_recrue = 0;
						int manquant_equipe = 0;
					
						if (nb_attaquant < 8) {
							manquant_att = 8 - nb_attaquant;
						}
						if (nb_defenseur < 5) {
							manquant_def = 5 - nb_defenseur;
						}
						if (nb_gardien < 2) {
							manquant_gardien = 2 - nb_gardien;
						}
						if (nb_rookie < 8) {
							manquant_recrue = 8 - nb_rookie;
						}
						if (nb_equipe < 22) {
							manquant_equipe = 22 - nb_equipe;
						}

					
						mBeanEquipe.setNb_attaquant(nb_attaquant);
						mBeanEquipe.setNb_defenseur(nb_defenseur);
						mBeanEquipe.setNb_gardien(nb_gardien);
						mBeanEquipe.setNb_rookie(nb_rookie);
						mBeanEquipe.setNb_equipe(nb_equipe);
						mBeanEquipe.setNb_contrat(nb_contrat);
						mBeanEquipe.setManquant_att(manquant_att);
						mBeanEquipe.setManquant_def(manquant_def);
						mBeanEquipe.setManquant_gardien(manquant_gardien);
						mBeanEquipe.setManquant_recrue(manquant_recrue);
						mBeanEquipe.setManquant_equipe(manquant_equipe);			

						entity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, keyTeam);

						datastore.put(entity);
					} catch (EntityNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
		
		

		PlayersCronModel mModel = new PlayersCronModel(playersDao);
		int numberOfTeam = mModel.getNumberOfTeamByPool(Integer.parseInt(poolID));
		mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "attaquant", 1, "6");

		mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "defenseur", 1, "6");

		mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "gardien", 1, "6");
		
		// TODO envoyer courriel au DG qui n'ont pas le bon nombre pour commencer la
		// nouvelle année et stopper le processus de new years
		// return false;

		// populate draftRound from DraftPick
		List<Integer> classementInverseLastYears = classementDao.getClassementLastYear(poolID,
				String.valueOf(lastYear));
		draftDao2.populationDraftRoundFromDraftPick(poolID, classementInverseLastYears, years, req);

		// reset draftPick to initial value
		draftPickDao.truncateTableDraftPick(Integer.parseInt(poolID));
		draftPickDao.insertPickByTeam(Integer.parseInt(poolID), nombreEquipe, numPickByTeam);

		// reset draftProcess
		Key keyDraftProcess = KeyFactory.createKey("DraftProcess", poolID);

		try {
			Entity mEntity = datastore.get(keyDraftProcess);

			mEntity.setProperty("currentPick", 0);
			mEntity.setProperty("1isFinish", 0);
			mEntity.setProperty("2isFinish", 0);
			mEntity.setProperty("3isFinish", 0);
			mEntity.setProperty("4isFinish", 0);
			mEntity.setProperty("5isFinish", 0);
			mEntity.setProperty("6isFinish", 0);
			mEntity.setProperty("7isFinish", 0);
			mEntity.setProperty("8isFinish", 0);
			mEntity.setProperty("9isFinish", 0);
			mEntity.setProperty("10isFinish", 0);
			mEntity.setProperty("11isFinish", 0);
			mEntity.setProperty("12isFinish", 0);
			mEntity.setProperty("oneFinish", 0);

			datastore.put(mEntity);
		} catch (EntityNotFoundException e) {

			e.printStackTrace();
			return false;
		}

		// runCronJobDraftRound and draftPick

		DraftPickCronModel mModelDraftPick = new DraftPickCronModel(draftPickDao);

		mModelDraftPick.putDatabaseInDatastore(Integer.parseInt(poolID), nombreEquipe, "7");

		draftDao2.putDatabaseInDatastore(poolID);

		// reset draftTime from pool datatstore to avoid jumping from cycle 2 to 3

		Key keyPool = KeyFactory.createKey("Pool", poolID);

		try {
			Entity mEntity = datastore.get(keyPool);

			mEntity.setProperty("draftDate", "2999-10-21");

			datastore.put(mEntity);
		} catch (EntityNotFoundException e) {

			e.printStackTrace();
			return false;
		}

		playersDao.cronJobPlayersAvailableForDraft(Integer.parseInt(poolID));

		return true;

	}

	public void addCashForYears(HttpServletRequest req) {
		// on remet les budgets en mode debut d'année
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		String poolID = mBeanPool.getPoolID();

		int numberOfTeam = mBeanPool.getNumberTeam();

		for (int i = 1; i < numberOfTeam + 1; i++) {

			String keyTeam = poolID + "_" + i;
			Key mKey = KeyFactory.createKey("Equipe", keyTeam);
			Equipe mBeanEquipe = new Equipe();
			try {
				Entity entity = datastore.get(mKey);

				mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(entity, mBeanEquipe);

				// on transfert argent recu, ajoute bonus ou malus et ensuite on reset a 52
				// Millions et recalcule les positions par team

				int max_salaire_begin = mBeanEquipe.getMax_salaire_begin();

				int budget_restant = mBeanEquipe.getBudget_restant();

				mBeanEquipe.setBudget_restant(budget_restant + 3000000);
				mBeanEquipe.setMax_salaire_begin(max_salaire_begin + 3000000);

				entity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, keyTeam);

				datastore.put(entity);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void resetPlayersStats(HttpServletRequest req, PlayersDao playersDao) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		String poolID = mBeanPool.getPoolID();

		String thisYear = mBeanPool.getThisYear();

		String years_for_archive = thisYear.substring(0, 4);

		playersDao.resetStatsToZeroForNewYear(poolID, years_for_archive);

	}

	public void addPlayerDuringDraft(String poolID, String nom, String team, String position, String birthday,
			PlayersDao playersDao, HttpServletRequest req) {
		// on ajoute le joueur avec valeur de depart
		playersDao.addPlayer(poolID, nom, team, position, birthday);
		// on check si can_be_rookie et modifie en consequence
		playersDao.updateAgeForRookie(req);
		playersDao.setCanBeRookie(poolID);
		// on ajoute dans datastore pour dispo au draft
		playersDao.putNewPlayersInDatastore(poolID);

	}

	/*****************************************************
	 * private method
	 ******************************************/

	private String switchToGetPropertyTeamName(int teamId) {
		String nomPropertyTeamName = null;
		switch (teamId) {
		case 1:
			nomPropertyTeamName = "teamName1";
			break;
		case 2:
			nomPropertyTeamName = "teamName2";
			break;
		case 3:
			nomPropertyTeamName = "teamName3";
			break;
		case 4:
			nomPropertyTeamName = "teamName4";
			break;
		case 5:
			nomPropertyTeamName = "teamName5";
			break;
		case 6:
			nomPropertyTeamName = "teamName6";
			break;
		case 7:
			nomPropertyTeamName = "teamName7";
			break;
		case 8:
			nomPropertyTeamName = "teamName8";
			break;
		case 9:
			nomPropertyTeamName = "teamName9";
			break;
		case 10:
			nomPropertyTeamName = "teamName10";
			break;
		case 11:
			nomPropertyTeamName = "teamName11";
			break;
		case 12:
			nomPropertyTeamName = "teamName12";
			break;

		}

		return nomPropertyTeamName;
	}

}
