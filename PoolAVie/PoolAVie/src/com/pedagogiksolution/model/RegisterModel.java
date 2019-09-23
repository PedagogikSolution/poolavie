package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PARAM_NULL;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PASSWORD_ENCRYPTION;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_USERNAME_MATCH;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.PasswordEncryption;

public class RegisterModel {

    // methode qui evalue les parametre recu via le formulaire et qui retourne un message d'erreur approprie si vide,
    // null ou pas valide
    public boolean validationParametre(String nomUtilisateur, String motDePasse, String courriel, HttpServletRequest req) {
	
	// code temporaire pour empecher la creation de nouveau pool (e retirer lorsque commercialise et re-activer le code ci-bas)
	if (courriel.equalsIgnoreCase("info@poolavie.ca")){
	    return false;
	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PARAM_NULL);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return true;
	}
	// verification si null, si oui, on retourne message d'erreur a la page register via beans d'erreur
	
	
	/*if (Strings.isNullOrEmpty(nomUtilisateur) || Strings.isNullOrEmpty(motDePasse) || Strings.isNullOrEmpty(courriel)) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PARAM_NULL);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return true;
	}
	

	// TODO voir TRELLO pour validation REGEX

	// si pas de validation negative, on retourne false et on continu le processus de registration
	return false;
	*/
    	//return false;

    }
    
    public boolean validationParametreDG(String nomUtilisateur, String motDePasse, String courriel, String nomTeam, HttpServletRequest req) {

	// verification si null, si oui, on retourne message d'erreur a la page register via beans d'erreur
	if ( (nomUtilisateur != null && !nomUtilisateur.isEmpty())|| (motDePasse != null && !motDePasse.isEmpty()) || (courriel != null && !courriel.isEmpty()) || (nomTeam != null && !nomTeam.isEmpty())) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PARAM_NULL);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}

	// TODO voir TRELLO pour validation REGEX

	// si pas de validation negative, on retourne false et on continu le processus de registration
	return true;

    }

    public boolean checkIfUsernameExist(String nomUtilisateur, HttpServletRequest req) {
	// on verifie si un objet existe dans le memCache
	
	
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Utilisateur", nomUtilisateur);
	    try {
		datastore.get(clefDatastore);
		// Si username existe, on retourne avec message erreur et true
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_USERNAME_MATCH);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return true;

	    } catch (EntityNotFoundException e) {
		// si existe pas, on continue avec false
		return false;
	    }
	    // si object existe on verifie si le mot de passe est le bon
	

    }

    public String createDatastoreUserEntity(String nomUtilisateur, String motDePasse, String courriel, int teamId, int typeUtilisateur, HttpServletRequest req) {

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

	if ((motDePasseEncrypter != null && !motDePasseEncrypter.isEmpty())) {
	    int poolId;
	    // generation d'un Code de Validation
	    String validationCode = generateValidationCode();

	    // on appel le service
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    if (typeUtilisateur == 1) {
		// on recupere le dernier pool_id du datastore of Kind Utilisateur
		poolId = generatePoolId(datastore);

		if (poolId == 0) {
		    // TODO message d'erreur inatendue
		    return null;
		}

	    } else {
		String temp_poolId = (String) req.getSession().getAttribute("temp_poolId");
		poolId = Integer.parseInt(temp_poolId);
	    }

	    // on recupere la date et place dans un format Date

	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    String dateCreation = dateFormat.format(date);

	    // on cree le beans avec le processus JPA qui va creer le datastore en meme temps
	    
		
		// instanciation du bean Utilisateur
		Utilisateur mBean = new Utilisateur();
		mBean.setNomUtilisateur(nomUtilisateur);
		mBean.setMotDePasse(motDePasseEncrypter);
		mBean.setCourriel(courriel);
		mBean.setCodeValidation(validationCode);
		mBean.setDateCreation(dateCreation);
		mBean.setPoolId(poolId);
		mBean.setTeamId(teamId);
		mBean.setTypeUtilisateur(typeUtilisateur);
		mBean.setValidationReussi(0);
		mBean.setFirstConnexionFinish(0);
		

		// on place le bean dans un attribut de session
		req.getSession().setAttribute("Utilisateur", mBean);
		// on persiste dans le datastore via notre EntityManager
		datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		
		Entity mEntity = mBean.mapBeanToEntityForDatastore(mBean,nomUtilisateur);
		
		datastore.put(mEntity);

	   
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

	    return poolId + 1;
	}
	return 1;

    }

    // TODO methode privee pour generer un code alphanumerique de 8 carateres
    private String generateValidationCode() {

	// genere un code si reussi, return le code, sinon retourne null

	/* presentemetn, on assigne toujours le code pour test de emailing et validation process */
	String fakeCodeForTest;

	fakeCodeForTest = "A1B2C3D4";

	return fakeCodeForTest;

    }

    public void sendingValidationCode(String nomUtilisateur, String courriel, HttpServletRequest req) {
	
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	String code = mBeanUser.getCodeValidation();
	
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	try {
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("pedagogiksolution@gmail.com", "Poolavie.ca"));
	    msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(courriel));
	    msg.setSubject("Validez votre compte", "utf-8");
	    msg.setContent("Bonjour, pour valider votre compte veuillez entrer le code suivant : "+code+".\n\n Si vous n'êtes plus sur la page de validation, vous pouvez la retrouver en vous connectant sur la page principale pour terminer votre inscription", "text/html");
	    Transport.send(msg);
	} catch (AddressException e) {
	    // ...
	} catch (MessagingException e) {
	    // ...
	} catch (UnsupportedEncodingException e) {
	    // ...
	}
    }

    
    
    public boolean validationParametre(String nomUtilisateur, String motDePasse, HttpServletRequest req) {
	if ((nomUtilisateur != null && !nomUtilisateur.isEmpty())|| (motDePasse != null && !motDePasse.isEmpty())) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRegistration(REGISTRATION_ERREUR_PARAM_NULL);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}
	

	// TODO voir TRELLO pour validation REGEX

	// si pas de validation negative, on retourne false et on continu le processus de registration
	return true;
    }

}
