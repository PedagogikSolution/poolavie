package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.RECUPERATION_COURRIEL_INEXISTANT;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PASSWORD_ENCRYPTION;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.pedagogiksolution.beans.GestionCompte;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;
import com.pedagogiksolution.utils.PasswordEncryption;

public class RecuperationModel {

    public Boolean checkIfEmailExist(String courriel, HttpServletRequest req) {

	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Use class Query to assemble a query
	Query q = new Query("Utilisateur");
	q.setFilter(new Query.FilterPredicate("courriel", Query.FilterOperator.EQUAL, courriel));

	PreparedQuery pq = datastore.prepare(q);
	int countEntity = pq.countEntities(FetchOptions.Builder.withDefaults());
	if (countEntity >= 1) {
	    return true;

	} else {

	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRecuperation(RECUPERATION_COURRIEL_INEXISTANT);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return null;
	}

    }

    public void sendInfoForRecuperation(HttpServletRequest req, String courriel, String codeValidation) {

	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	try {
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("pedagogiksolution@gmail.com", "Poolavie.ca"));
	    msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(courriel));
	    msg.setSubject("Récupération de vos identifiants", "utf-8");
	    msg.setContent("Bonjour, pour récupérer votre compte veuillez cliquez sur le lien qui suit.\n\n <a href='http://2-dot-zeta-anthem-715.appspot.com/recuperation?courriel=" + courriel + "&codeValidation=" + codeValidation + "'>Récupérer mon compte</a>", "text/html");
	    Transport.send(msg);
	} catch (AddressException e) {
	    // ...
	} catch (MessagingException e) {
	    // ...
	} catch (UnsupportedEncodingException e) {
	    // ...
	}

    }

    public void recuperationDesUsernameAssocier(String email, HttpServletRequest req) {

    }

    public Boolean checkIfValidationCodeIsGood(HttpServletRequest req, String email, String codeValidation2) {
	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Use class Query to assemble a query
	Query q = new Query("Utilisateur");
	q.setFilter(new Query.FilterPredicate("courriel", Query.FilterOperator.EQUAL, email));

	PreparedQuery pq = datastore.prepare(q);
	List<Entity> entity = pq.asList(FetchOptions.Builder.withDefaults());
	for (Entity results : entity) {
	    String codeValidationTemp = (String) results.getProperty("codeValidation");
	    if (codeValidationTemp.equalsIgnoreCase(codeValidation2)) {
		return true;

	    }

	}

	MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	mBeanMessageErreur.setErreurFormulaireRecuperation("Le code de validation n'est pas bon, veuillez ré-essayer ou nous contacter si cela persiste");
	req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	return false;

    }

    public void prepareResetOfAllAccount(HttpServletRequest req, String email) {
	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	GestionCompte mBean = new GestionCompte();
	List<String> usernameList = new ArrayList<String>();
	// Use class Query to assemble a query
	Query q = new Query("Utilisateur");
	q.setFilter(new Query.FilterPredicate("courriel", Query.FilterOperator.EQUAL, email));

	PreparedQuery pq = datastore.prepare(q);
	List<Entity> entity = pq.asList(FetchOptions.Builder.withDefaults());
	for (Entity results : entity) {
	    String username = (String) results.getKey().getName();
	    usernameList.add(username);

	}

	mBean.setUsername(usernameList);

	req.setAttribute("Recuperation", mBean);

    }

    public void changePassword(String username, String password, HttpServletRequest req) {
		// on encrypte le mot de passe
		PasswordEncryption mEncryptProcess = new PasswordEncryption();
		String motDePasseEncrypter = null;
		try {
		    motDePasseEncrypter = mEncryptProcess.passwordEncryption(password);
		} catch (NoSuchAlgorithmException e) {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		   
		} catch (InvalidKeySpecException e) {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		   
		}
		// on recupere le datastore Utilisateur et on change le mot de passe
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key mKey = KeyFactory.createKey("Utilisateur", username);
		
		Entity entity = null;
		try {
		    entity = datastore.get(mKey);
		    entity.setProperty("motDePasse", motDePasseEncrypter);
		    datastore.put(entity);
		} catch (EntityNotFoundException e) {
		   
		}
		Utilisateur mBean = new Utilisateur();
		mBean = mapUtilisateurFromDatastore(entity, mBean);
		
		
			
	
    }
    
    /* ******************  private methode   ************* */
    private Utilisateur mapUtilisateurFromDatastore(Entity mEntity, Utilisateur mBean) {

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
		em = emf.createEntityManager();
		mBean = em.find(Utilisateur.class, mEntity.getKey());
	} finally {
		if (em != null)
			em.close();
	}

	return mBean;
}
}
