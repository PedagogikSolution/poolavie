package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PARAM_NULL;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PASSWORD_ENCRYPTION;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_USERNAME_PASSWORD_MATCH;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_USERNAME_MATCH;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.base.Strings;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;
import com.pedagogiksolution.utils.PasswordEncryption;

public class RegisterModel {

    // methode qui �value les parametre recu via le formulaire et qui retourne un message d'erreur appropri� si vide,
    // null ou pas valide
    public boolean validationParametre(String nomUtilisateur, String motDePasse, String courriel, HttpServletRequest req) {

	// verification si null, si oui, on retourne message d'erreur a la page register via beans d'erreur
	if (Strings.isNullOrEmpty(nomUtilisateur) || Strings.isNullOrEmpty(motDePasse) || Strings.isNullOrEmpty(courriel)) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PARAM_NULL);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return true;
	}

	// TODO voir TRELLO pour validation REGEX

	// si pas de validation n�gative, on retourne false et on continu le processus de registration
	return false;

    }

    public boolean checkIfUsernameExist(String nomUtilisateur, String motDePasse, HttpServletRequest req) {
	// on verifie si un objet existe dans le memCache
	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Utilisateur", nomUtilisateur);
	Utilisateur mBean = (Utilisateur) memcache.get(clefMemCache);
	
	// si l'objet existe pas, on verifie si existe dans Datastore
	if (mBean == null) {
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Utilisateur", nomUtilisateur);
	    try {
		Entity mEntity = datastore.get(clefDatastore);
		String mEncryptPassword = (String) mEntity.getProperty("motDePasse");
		PasswordEncryption mEncrypt = new PasswordEncryption();
		try {
		    boolean passwordMatch = mEncrypt.validatePassword(motDePasse, mEncryptPassword);
		    if (passwordMatch) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_PASSWORD_MATCH);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);			
			return true;
		    } else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_MATCH);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return true;
		    }
		    		    
		} catch (NoSuchAlgorithmException e) {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_PASSWORD_MATCH);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return true;
		} catch (InvalidKeySpecException e) {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_PASSWORD_MATCH);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return true;
		}
				
		
	    } catch (EntityNotFoundException e) {
		return false;
	    }
	 // si object existe on verifie si le mot de passe est le bon   	    
	} else { 
	    String mPasswordEncrypt = mBean.getMotDePasse();
	    PasswordEncryption mEncrypt = new PasswordEncryption();
	    try {
		boolean passwordMatch = mEncrypt.validatePassword(motDePasse, mPasswordEncrypt);
		if (passwordMatch) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_PASSWORD_MATCH);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			System.out.print(mBean.getCourriel());
			return true;
		    } else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_MATCH);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			System.out.print(mBean.getCourriel());
			return true;
		    }
	    } catch (NoSuchAlgorithmException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return true;
	    } catch (InvalidKeySpecException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return true;
	    }

	}
    }



    public String createDatastoreUserEntity(String nomUtilisateur, String motDePasse, String courriel, HttpServletRequest req) {

	// On encrypte le mot de passe
	PasswordEncryption mEncryptProcess = new PasswordEncryption();
	String motDePasseEncrypter = null;
	try {
	    motDePasseEncrypter = mEncryptProcess.passwordEncryption(motDePasse);
	} catch (NoSuchAlgorithmException e) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return null;
	} catch (InvalidKeySpecException e) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return null;
	}

	if (!Strings.isNullOrEmpty(motDePasseEncrypter)) {

	    // g�n�ration d'un Code de Validation
	    String validationCode = generateValidationCode();

	    // on appel le service
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    // on recupere le dernier pool_id du datastore of Kind Utilisateur
	    int poolId = generatePoolId(datastore);

	    // on recupere la date et place dans un format Date

	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    String dateCreation = dateFormat.format(date);

	    // on cr�e le beans avec le processus JPA qui va cr�er le datastore en m�me temps
	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;
	    try {
		em = emf.createEntityManager();

		// instanciation du bean Utilisateur
		Utilisateur mBean = new Utilisateur();
		mBean.setNomUtilisateur(nomUtilisateur);
		mBean.setMotDePasse(motDePasseEncrypter);
		mBean.setCourriel(courriel);
		mBean.setCodeValidation(validationCode);
		mBean.setDateCreation(dateCreation);
		mBean.setPoolId(poolId);
		mBean.setTeamId(1);
		mBean.setTypeUtilisateur(1);
		mBean.setValidationReussi(0);
		mBean.setFirstConnexionFinish(0);

		// on place le bean dans un attribut de session
		req.getSession().setAttribute("Utilisateur", mBean);
		// on persiste dans le datastore via notre EntityManager
		em.persist(mBean);
		// on persist le datastore/bean dans la MemCache pour appel au pool ID, typeUtilisateur, teamId lors du
// login
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key userPrefsKey = KeyFactory.createKey("Utilisateur", nomUtilisateur);
		memcache.put(userPrefsKey, mBean);

	    } finally {
		// on ferme le manager pour lib�rer la m�moire
		if (em != null)
		    em.close();
	    }

	    // on retourne le code de validation non null au servlet
	    return validationCode;

	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return null;

	}

    }

    private int generatePoolId(DatastoreService datastore) {

	// Use class Query to assemble a query
	Query q = new Query("Utilisateur").addSort("poolId", SortDirection.DESCENDING);

	// Use PreparedQuery interface to retrieve results
	List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));

	for (Entity result : pq) {
	    Long poolIdTemp = (Long) result.getProperty("poolId");
	    int poolId = poolIdTemp.intValue();
	    System.out.println(poolId);

	    return poolId + 1;
	}
	return 1;

    }

    // TODO methode priv�e pour g�n�rer un code alphanum�rique de 8 carateres
    private String generateValidationCode() {

	// genere un code si reussi, return le code, sinon retourne null

	/* presentemetn, on assigne toujours le code pour test de emailing et validation process */
	String fakeCodeForTest;

	fakeCodeForTest = "A1B2C3D4";

	return fakeCodeForTest;

    }

    public boolean sendingValidationCode(String nomUtilisateur, String courriel, HttpServletRequest req) {
	// TODO faire la methode et retourn� true si reussi, false et message d'erreur sinon
	// j'ai lu qu'il n'y avait pas de moyen de savoir si l'envoie a reussi, ni le delai, c'est un queue push hors de nos controles

	// pour developpement, la valeur est par defaut a true pour l'instant
	return true;
    }

}
