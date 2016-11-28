package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.CREATE_POOL_PAS_FINI;
import static com.pedagogiksolution.constants.MessageErreurConstants.LOGIN_PASSWORD_NOT_GOOD;
import static com.pedagogiksolution.constants.MessageErreurConstants.LOGIN_USERNAME_DONT_EXIST;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PASSWORD_ENCRYPTION;
import static com.pedagogiksolution.constants.MessageErreurConstants.VALIDATION_CODE_ERREUR_PAS_ENCORE_FAIT;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;
import com.pedagogiksolution.utils.PasswordEncryption;

public class LoginModel {

    // variable d'instance
    private String username;
    private String password;
    private HttpServletRequest requestObject;

    // contructeur pour le Servlet Login
    public LoginModel(String nomUtilisateur, String motDePasse, HttpServletRequest req) {
	username = nomUtilisateur;
	password = motDePasse;
	requestObject = req;
    }

    public LoginModel(HttpServletRequest req) {
	requestObject = req;
    }

    // Methode qui valide les identifiants (username et password) et retourne
    // message d'erreur si pas bon
    // Création du Session Bean Utilisateur, avec valeur de login à 1
    public Boolean validateCredential() {

	// si l'objet n'existe pas, on verifie si il existe dans le Datastore

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key clefDatastore = KeyFactory.createKey("Utilisateur", username);
	try {
	    // si existe, aucun EntityNotFoundException, donc on recupère
	    // l'info pour tester password
	    Entity mEntity = datastore.get(clefDatastore);
	    String mEncryptPassword = (String) mEntity.getProperty("motDePasse");
	    Utilisateur mBean = new Utilisateur();
	    PasswordEncryption mEncrypt = new PasswordEncryption();
	    try {
		boolean passwordMatch = mEncrypt.validatePassword(password, mEncryptPassword);
		if (passwordMatch) {
		    // si match on place dans bean Utilisateur
		    mBean = mBean.mapUtilisateurFromDatastore(mEntity, mBean);
		    // on met la valeur de LoginReussi a 1
		    mBean.setLoginReussi(1);
		    // on place dans la session le Bean Utilisateur
		    requestObject.getSession().setAttribute("Utilisateur", mBean);
		    // on place dans la Memcache pour connexion futur

		    return true;
		} else {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_PASSWORD_NOT_GOOD);
		    requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		}

	    } catch (NoSuchAlgorithmException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    } catch (InvalidKeySpecException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	} catch (EntityNotFoundException e) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_USERNAME_DONT_EXIST);
	    requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}
	// si le bean existe on verifie si le mot de passe est le bon, si
	// bon on place dans session et retourne true

    }

    // Méthode qui vérifie si le processus de validation du compte est terminé,
    // si pas fini, on envoie à la page de
    // validation
    public Boolean checkIfValidateAccount() {
	Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	int validationReussi = mBean.getValidationReussi();
	if (validationReussi == 1) {
	    return true;
	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurCodeValidationType2(VALIDATION_CODE_ERREUR_PAS_ENCORE_FAIT);
	    requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}

    }

    // Méthode pour initialiser le Session Bean Pool et Equipe
    public Boolean createSessionPoolBean() {

	// on recupere le poolID et le teamId du Session Bean Utilisateur
	Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");

	// int teamId = mBean.getTeamId();
	int poolId = mBean.getPoolId();

	Pool mBeanPool = new Pool();

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key clefDatastore = KeyFactory.createKey("Pool", Integer.toString(poolId));
	try {
	    // si existe, aucun EntityNotFoundException, donc on recupère
	    // l'info pour tester password
	    Entity mEntity = datastore.get(clefDatastore);

	    // on met dans SessionBean
	    mBeanPool = mBeanPool.mapPoolFromDatastore(mEntity, mBeanPool);
	    requestObject.getSession().setAttribute("Pool", mBeanPool);

	    return true;

	} catch (EntityNotFoundException e) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
	    requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}
	// si le bean existe on verifie si le mot de passe est le bon, si
	// bon on place dans session et retourne true

    }

    public Boolean createSessionClassementBean() {
	// on recupere le poolID et le teamId du Session Bean Utilisateur
	Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");

	// int teamId = mBean.getTeamId();
	int poolId = mBean.getPoolId();
	String poolID = String.valueOf(poolId);

	Classement mBeanClassement = new Classement();

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key clefDatastore = KeyFactory.createKey("Classement", poolID);
	try {
	    // si existe, aucun EntityNotFoundException, donc on recupère
	    // l'info pour tester password
	    Entity mEntity = datastore.get(clefDatastore);

	    // on met dans SessionBean
	    mBeanClassement = mBeanClassement.mapClassementFromDatastore(mEntity, mBeanClassement);
	    requestObject.getSession().setAttribute("Classement", mBeanClassement);

	    return true;

	} catch (EntityNotFoundException e) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
	    requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}
	// si le bean existe on verifie si le mot de passe est le bon, si
	// bon on place dans session et retourne true

    }

    public Boolean createSessionEquipeBean() {

	Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	// on reparse en int pour le stockage
	int poolId = mBeanUser.getPoolId();
	int nombreEquipe = mBeanPool.getNumberTeam();

	for (int i = 1; i < (nombreEquipe + 1); i++) {

	    String jspSessionName = "Equipe" + i;
	    String datastoreId = poolId + "_" + i;

	    Equipe mBeanEquipe = new Equipe();

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Equipe", datastoreId);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);

		// on met dans SessionBean
		mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(mEntity, mBeanEquipe);
		requestObject.getSession().setAttribute(jspSessionName, mBeanEquipe);

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
	return true;

    }

    public Boolean createSessionDraftRoundBean() {
	// on recupere le poolID et le teamId du Session Bean Utilisateur
	Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");

	// int teamId = mBean.getTeamId();
	int poolId = mBean.getPoolId();
	String poolID = String.valueOf(poolId);

	DraftRound mBeanDraftRound = new DraftRound();

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key clefDatastore = KeyFactory.createKey("DraftRound", poolID);
	try {
	    // si existe, aucun EntityNotFoundException, donc on recupère
	    // l'info pour tester password
	    Entity mEntity = datastore.get(clefDatastore);

	    // on met dans SessionBean
	    mBeanDraftRound = mBeanDraftRound.mapDraftRoundFromDatastore(mEntity, mBeanDraftRound);
	    requestObject.getSession().setAttribute("DraftRound", mBeanDraftRound);

	    return true;

	} catch (EntityNotFoundException e) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
	    requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}
	// si le bean existe on verifie si le mot de passe est le bon, si
	// bon on place dans session et retourne true

    }

    public Boolean createSessionDraftPickBean() {

	Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	// on reparse en int pour le stockage
	int poolId = mBeanUser.getPoolId();
	int nombreEquipe = mBeanPool.getNumberTeam();

	for (int i = 1; i < (nombreEquipe + 1); i++) {

	    String jspSessionName = "DraftPick" + i;
	    String datastoreId = poolId + "_" + i;

	    DraftPick mBeanDraftPick = new DraftPick();

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("DraftPick", datastoreId);
	    try {

		Entity mEntity = datastore.get(clefDatastore);

		// on met dans SessionBean
		mBeanDraftPick = mBeanDraftPick.mapDraftPickFromDatastore(mEntity, mBeanDraftPick);
		requestObject.getSession().setAttribute(jspSessionName, mBeanDraftPick);

		// on met dans memcache

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
	return true;
    }

    public Boolean createSessionAttaquantBean() {
	Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	// on reparse en int pour le stockage
	int poolId = mBeanUser.getPoolId();
	int nombreEquipe = mBeanPool.getNumberTeam();

	for (int i = 1; i < (nombreEquipe + 1); i++) {

	    String jspSessionName = "Attaquant" + i;
	    String datastoreId = poolId + "_" + i;

	    Attaquant mBeanAttaquant = new Attaquant();

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Attaquant", datastoreId);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);

		// on met dans SessionBean
		mBeanAttaquant = mBeanAttaquant.mapAttaquantFromDatastore(mEntity, mBeanAttaquant);
		requestObject.getSession().setAttribute(jspSessionName, mBeanAttaquant);

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
	return true;
    }

    public Boolean createSessionDefenseurBean() {
	Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	// on reparse en int pour le stockage
	int poolId = mBeanUser.getPoolId();
	int nombreEquipe = mBeanPool.getNumberTeam();

	for (int i = 1; i < (nombreEquipe + 1); i++) {

	    String jspSessionName = "Defenseur" + i;
	    String datastoreId = poolId + "_" + i;

	    Defenseur mBeanDefenseur = new Defenseur();

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Defenseur", datastoreId);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);

		// on met dans SessionBean
		mBeanDefenseur = mBeanDefenseur.mapDefenseurFromDatastore(mEntity, mBeanDefenseur);
		requestObject.getSession().setAttribute(jspSessionName, mBeanDefenseur);

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
	return true;
    }

    public Boolean createSessionGardienBean() {
	Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	// on reparse en int pour le stockage
	int poolId = mBeanUser.getPoolId();
	int nombreEquipe = mBeanPool.getNumberTeam();

	for (int i = 1; i < (nombreEquipe + 1); i++) {

	    String jspSessionName = "Gardien" + i;
	    String datastoreId = poolId + "_" + i;

	    Gardien mBeanGardien = new Gardien();

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Gardien", datastoreId);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);

		// on met dans SessionBean
		mBeanGardien = mBeanGardien.mapGardienFromDatastore(mEntity, mBeanGardien);
		requestObject.getSession().setAttribute(jspSessionName, mBeanGardien);

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
	return true;
    }

    public Boolean createSessionRecrueBean() {
	Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	// on reparse en int pour le stockage
	int poolId = mBeanUser.getPoolId();
	int nombreEquipe = mBeanPool.getNumberTeam();

	for (int i = 1; i < (nombreEquipe + 1); i++) {

	    String jspSessionName = "Recrue" + i;
	    String datastoreId = poolId + "_" + i;

	    Recrue mBeanRecrue = new Recrue();

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Recrue", datastoreId);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);

		// on met dans SessionBean
		mBeanRecrue = mBeanRecrue.mapRecrueFromDatastore(mEntity, mBeanRecrue);
		requestObject.getSession().setAttribute(jspSessionName, mBeanRecrue);

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
	return true;
    }

    public void resetConnexionOpen() {
	Utilisateur mUserBean = new Utilisateur();
	Pool mBeanPool = new Pool();
	mUserBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
	int teamId = mUserBean.getTeamId();
	mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	DraftProcess mBeanDraftProcess = new DraftProcess();

	Key clefDatastore = KeyFactory.createKey("DraftProcess", poolID);

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {

	    em = emf.createEntityManager();
	    mBeanDraftProcess = em.find(DraftProcess.class, clefDatastore);
	    switch (teamId) {
	    case 1:
		mBeanDraftProcess.setTeam1IsOpen(0);
		break;
	    case 2:
		mBeanDraftProcess.setTeam2IsOpen(0);
		break;
	    case 3:
		mBeanDraftProcess.setTeam3IsOpen(0);
		break;
	    case 4:
		mBeanDraftProcess.setTeam4IsOpen(0);
		break;
	    case 5:
		mBeanDraftProcess.setTeam5IsOpen(0);
		break;
	    case 6:
		mBeanDraftProcess.setTeam6IsOpen(0);
		break;
	    case 7:
		mBeanDraftProcess.setTeam7IsOpen(0);
		break;
	    case 8:
		mBeanDraftProcess.setTeam8IsOpen(0);
		break;
	    case 9:
		mBeanDraftProcess.setTeam9IsOpen(0);
		break;
	    case 10:
		mBeanDraftProcess.setTeam10IsOpen(0);
		break;
	    case 11:
		mBeanDraftProcess.setTeam11IsOpen(0);
		break;
	    case 12:
		mBeanDraftProcess.setTeam12IsOpen(0);
		break;

	    }

	    mBeanDraftProcess = em.merge(mBeanDraftProcess);
	} finally {
	    if (em != null)
		em.close();
	}

    }

    
}
