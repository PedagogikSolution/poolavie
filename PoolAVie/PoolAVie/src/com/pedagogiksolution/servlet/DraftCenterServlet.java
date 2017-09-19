package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.EquipeModel;
import com.pedagogiksolution.model.LoginModel;

public class DraftCenterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private DraftDao draftDao;

	@Override
	public void init() throws ServletException {

		this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginModel mModel2 = new LoginModel(req);
		mModel2.createSessionEquipeBean();
		mModel2.createSessionAttaquantBean();
		mModel2.createSessionDefenseurBean();
		mModel2.createSessionGardienBean();
		mModel2.createSessionRecrueBean();
		mModel2.createSessionDraftPickBean();
		mModel2.createSessionDraftRoundBean();
		mModel2.createSessionPoolBean();

		Pool mBean = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBean.getCycleAnnuel();
		String poolID = mBean.getPoolID();

		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBean, req);

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key mKey = KeyFactory.createKey("DraftProcess", poolID);
			try {
				Entity mEntity = datastore.get(mKey);
				Long oneFinish = (Long) mEntity.getProperty("oneFinish");

				if (oneFinish.intValue() == 1) {
					draftDao.putDatabaseInDatastore(poolID);
				}

			} catch (EntityNotFoundException e) {

			}

		}

		if (cycleAnnuel >= 1) {
			EquipeModel mModelEquipe = new EquipeModel();
			mModelEquipe.getBeanByTeam(req);
		}

		req.getRequestDispatcher("jsp/draft/draft_center.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
