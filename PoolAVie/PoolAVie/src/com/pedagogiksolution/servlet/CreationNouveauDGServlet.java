package com.pedagogiksolution.servlet;

import static com.pedagogiksolution.constants.MessageErreurConstants.CREATE_NEW_USER_NO_GOOD;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.cron.model.ClassementCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.model.CreationDGModel;
import com.pedagogiksolution.model.LoginModel;
import com.pedagogiksolution.model.RegisterModel;

public class CreationNouveauDGServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -3882270204686448272L;

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClassementDao classementDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.classementDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClassementDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String poolID = req.getParameter("po");
	String teamID = req.getParameter("pl");
	String codeValidationPool = req.getParameter("co");
	String provenance = req.getParameter("fo");

	if (poolID == null || teamID == null || codeValidationPool == null || provenance == null) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    req.getRequestDispatcher("/jsp/accueil/creationnouveaudg.jsp").forward(req, resp);

	} else {
	   

		CreationDGModel mModel = new CreationDGModel();

		Boolean validation = mModel.validationNewDG(poolID, teamID, codeValidationPool, req);

		if (validation) {
		    req.getSession().setAttribute("temp_poolId", poolID);
		    req.getSession().setAttribute("temp_teamId", teamID);

		    req.getRequestDispatcher("/jsp/accueil/creationnouveaudg.jsp").forward(req, resp);
		} else {
		    req.getRequestDispatcher("/jsp/accueil/creationnouveaudg.jsp").forward(req, resp);
		}

	   

	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	// recuperation des 3 inputs du formulaire de la page register
	String nomUtilisateur = req.getParameter("username");
	String motDePasse = req.getParameter("password");
	String courriel = req.getParameter("email");
	String nomDuTeam = req.getParameter("nomDuTeam");
	String temp_teamId = (String) req.getSession().getAttribute("temp_teamId");
	int teamId = Integer.parseInt(temp_teamId);
	String team_poolId = (String) req.getSession().getAttribute("temp_poolId");
	int poolId = Integer.parseInt(team_poolId);
	String urlLogoTeam = req.getParameter("logoUrlTeam");
	
	// Instantiation de la classe métier pour le processus de registration
	RegisterModel mModel = new RegisterModel();

	// validation des parametres du formulaire
	boolean mValidation = mModel.validationParametreDG(nomUtilisateur, motDePasse, courriel, nomDuTeam, req);

	if (mValidation) {
	    req.getRequestDispatcher("jsp/accueil/creationnouveaudg.jsp").forward(req, resp);
	    return;
	}

	// on verifie si username existe dans le Datastore, si existe on verifie si password match, sinon on crée un
// nouveau compte client admin

	if (mModel.checkIfUsernameExist(nomUtilisateur, req)) {

	    req.getRequestDispatcher("jsp/accueil/creationnouveaudg.jsp").forward(req, resp);

	} else {

	    // on check si utilisateur exist deja, si oui et que le mot de passe match, on update,sinon on crée

	    String validationCode = mModel.createDatastoreUserEntity(nomUtilisateur, motDePasse, courriel, teamId, 2, req);

	    // étape 2 on ajoute les info dans les storage Pool et Utilisateur
	    CreationDGModel mModel2 = new CreationDGModel(classementDao);
	    mModel2.storePoolAndUserInfo(nomDuTeam, poolId,urlLogoTeam, req);

	    // si le code est retourné, c'est que tout à réussi, donc on envoie un courriel avec Code Validation à
// l'utilisateur
	    if (validationCode != null) {

		boolean sendingEmail = mModel.sendingValidationCode(nomUtilisateur, courriel, req);
		// si succes du courriel, on envoie vers la page permettant a l'utilisateur d'entrée son code de
// Validation et ainsi confirmer son abonnement
		if (sendingEmail) {

		    ClassementCronModel mModelClassement = new ClassementCronModel(classementDao);

		    mModelClassement.putDatabaseInDatastore(poolId);
		    LoginModel mModelLogin = new LoginModel(req);

		    mModelLogin.createSessionClassementBean();
		    
		    
		    
		    resp.sendRedirect("/validation");
		} else {
		    req.getRequestDispatcher("jsp/accueil/creationnouveaudg.jsp").forward(req, resp);
		}

	    } else {
		req.getRequestDispatcher("jsp/accueil/creationnouveaudg.jsp").forward(req, resp);
	    }

	}
    }

}
