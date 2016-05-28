package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.cron.model.ClassementCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.CreationPoolModel;
import com.pedagogiksolution.model.LoginModel;

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
	    
	    LoginModel mModelLogin = new LoginModel(req);

	    mModelLogin.createSessionClassementBean();

	    resp.sendRedirect("/Nouvelles");

	}

    }

}
