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
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.DraftRound;
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
			mBeanPool.setPoolYear((poolYearId + 1));

			req.getSession().setAttribute("Pool", mBeanPool);

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Entity mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool,mBeanPool.getPoolID());
			
			datastore.put(mEntity);

		}

	}

	public void determineOrderOfDraft(HttpServletRequest req) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolID);

		int numberTeam = mBeanPool.getNumberTeam();
		List<Integer> permutation = null;
		switch (numberTeam) {
		case 8:
			permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
			break;
		case 9:
			permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
			break;
		case 10:
			permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			break;
		case 11:
			permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
			break;
		case 12:
			permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
			break;
		}

		Collections.shuffle(permutation);

		// On persist dans Database et dans Datastore et dans MemCache et dans Bean

		draftDao.populateFirstYearsDraft(poolId, permutation, mBeanPool);

		DraftRound mBeanDraft = draftDao.setDraftRoundOrder(poolId);

		req.getSession().setAttribute("DraftRound", mBeanDraft);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Entity mEntity = mBeanDraft.mapBeanToEntityForDatastore(mBeanDraft,mBeanDraft.getPoolId());
		
		datastore.put(mEntity);

	}

	public void annulationStartDraft(HttpServletRequest req) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

		mBeanPool.setCycleAnnuel(2);

		req.getSession().setAttribute("Pool", mBeanPool);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		Entity mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool,mBeanPool.getPoolID());
		
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
						+ ", votre administrateur de pool � d�terminer une date pour votre draft. \n\n"
						+ "Celui-ci aura lieu le " + dateDraft + " � " + heureDraft
						+ ". Vous pourrez alors vous connecter au serveur de draft"
						+ " via l'onglet Draft ou en cliquant sur l'alerte en rouge lorsque vous vous connecterez"
						+ " sur le site et que vous arriverez � la page des nouvelles. \n\n Vous trouverez im�diatement aussi dans la section Draft Center l'ordre de draft des 20 premiers picks.\n\n Votre administrateur",
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
							"Ce nom d'utilisateur existe d�ja, merci de bien vouloir en choisir un diff�rent");
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

				req.setAttribute("messageConfirmationChangementUsername", "Votre nom d'utilisateur a �t� modifi�");

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

				// on recupere le nouveau beanUser ou le vieux, selon si �tape 1 fait ou pas
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

				req.setAttribute("messageConfirmationChangementPassword", "Votre mot de passe a �t� modifi�");

			}

			// 3- Si courriel pas vide on le change
			if (email != null && email != "") {

				// on recupere le nouveau beanUser ou le vieux, selon si �tape 1 fait ou pas
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
				req.setAttribute("messageConfirmationChangementCourriel", "Votre courriel a �t� modifi�");
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
					// TODO Auto-generated catch block
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

				req.setAttribute("messageConfirmationChangementNomTeam", "Votre nom d'�quipe a �t� modifi�");

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
			Long cycleAnnuel = Long.valueOf(7);
			mEntity.setProperty("cycleAnnuel", cycleAnnuel);
			mBeanPool = mBeanPool.mapPoolFromDatastore(mEntity, mBeanPool);

			datastore.put(mEntity);
			mBeanPool.setCycleAnnuel(7);

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
