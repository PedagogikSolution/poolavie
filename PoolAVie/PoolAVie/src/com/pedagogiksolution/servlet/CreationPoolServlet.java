package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.cron.model.ClassementCronModel;
import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.CreationPoolModel;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;
import com.pedagogiksolution.model.NouvellesModel;

public class CreationPoolServlet extends HttpServlet {

    private static final long serialVersionUID = -2677090836243950997L;

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClassementDao classementDao;
    private PlayersDao playersDao;
    private DraftDao draftDao;
    private TradeMadeDao tradeMadeDao;
    private TradeOfferDao tradeOfferDao;
    private SalaireDao salaireDao;
    private DraftPickDao draftPickDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre nos DAO */
	this.classementDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClassementDao();
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
	this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();
	this.tradeMadeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeMadeDao();
	this.tradeOfferDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeOfferDao();
	this.salaireDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSalaireDao();
	this.draftPickDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftPickDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	// initilisation du métier
	CreationPoolModel mModel = new CreationPoolModel(classementDao, playersDao, draftDao, tradeMadeDao, tradeOfferDao, salaireDao, draftPickDao);

	// test des parametres formulaire bien rempli
	Boolean validationFormulaireCreation = mModel.validationFormulaireCreation(req);

	// si false, retourne a la page de creation avec message d'erreur approprié
	if (!validationFormulaireCreation) {
	    req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);
	} else {
	    
	    // on crée les datastores POOL et EQUIPE
	    mModel.createPoolBean(req);
	    mModel.createEquipeBean(req);
	    
	    // on envoie les courriels au futur DG
	    // mModel.sendEmail(req);
	    
	    // on créer les bases de donnée du POOL
	    mModel.createDatabase(req);
	    mModel.createSucceed(req);
	    
	    // on met les database dans objet session, memcache et datastore
	    ClassementCronModel mModelClassement = new ClassementCronModel(classementDao);
	    // on recupere le poolID de ce pool via le bean de Session Utilisateur
	    Utilisateur mBeanUser  = (Utilisateur)req.getSession().getAttribute("Utilisateur");
	    int poolId = mBeanUser.getPoolId();
	    
	    mModelClassement.putDatabaseInDatastore(poolId);
	    
	    PlayersCronModel mModelPlayers = new PlayersCronModel(playersDao);
	    
	    int numberOfTeam = mModelPlayers.getNumberOfTeamByPool(poolId);
	    
	    // creation du datastore player_X
	   // mModelPlayers.putDatabaseInDatastore(poolId);
	    
	    // creation du datastore des teams par position
	    mModelPlayers.putDatabaseInDatastore(poolId, numberOfTeam, "attaquant", 0,"3");
	    mModelPlayers.putDatabaseInDatastore(poolId, numberOfTeam, "defenseur", 0,"4");
	    mModelPlayers.putDatabaseInDatastore(poolId, numberOfTeam, "gardien", 0,"5");
	    mModelPlayers.putDatabaseInDatastore(poolId, numberOfTeam, "recrue", 1,"6");
	    
	    // creation du datastore pour les draft pick par equipe
	    //DraftPickCronModel mDraftModel = new DraftPickCronModel(draftPickDao);	    
	    //mDraftModel.putDatabaseInDatastore(poolId, numberOfTeam);
	    
	    
	    
	    
	    //	 creation des bean de Session   
	    LoginModel mModelLogin = new LoginModel(req);

	    mModelLogin.createSessionClassementBean();
	   // mModelLogin.createSessionDraftPickBean();
	    
	    NouvellesModel mModelNouvelles = new NouvellesModel();
	    // initialisation du message du bienvenue
	    String titre = "Bienvenue dans poolavie.ca";
	    String body = "L'équipe de poolavie.ca vous souhaite la bienvenue dans votre nouveau pool." +
	    		" Nous espérons que vous apprécierez votre expérience. Si ce n'est pas encore fait," +
	    		" vous recevrez sous peu un courriel vous indiquant la date de votre premier repêchage" +
	    		" une fois que le commissaire de votre pool l'aura déterminer." +
	    		" Il ne reste qu'à vous souhaitez la meilleure des chances!!!";
	    
	    mModelNouvelles.createMessageForNewsBySystem(titre, body, req);
	    
	    mModelNouvelles.putNewsInBean(req);
	    
	    DraftPlayersModel mModelDraft = new DraftPlayersModel();
	    Pool mBean = (Pool) req.getSession().getAttribute("Pool");
	    Boolean checkIfDatastoreCreate=  mModelDraft.checkIfDatastoreCreate(mBean);
	    if(!checkIfDatastoreCreate){
		mModelDraft.createDraftDatastoreForThatPool(mBean);
	    } 
	  //connectFilter Session Attribute
	    req.getSession().setAttribute("connectUser", 1);
	    resp.sendRedirect("/Nouvelles");

	}

    }

}
