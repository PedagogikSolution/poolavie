package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;

public class AdminPoolServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;

	public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayersDao playersDao;

	@Override
	public void init() throws ServletException {
		/* R�cup�ration d'une instance de notre nos DAO */
		this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBeanPool, req);
		}

		switch (cycleAnnuel) {
		case 0:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 1:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 2:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 3:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 4:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 5:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 6:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 7:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 8:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 9:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 10:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 11:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 12:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AdminModel mAdminModel;
		String adminButton = req.getParameter("adminButton");
		int adminButtonInt = Integer.parseInt(adminButton);

		switch (adminButtonInt) {

		case 1: // fin de la saison reguliere, on envoie message courriel et ajoute news, on
				// change cycle du pool pour le 7, ouvrant ainsi la période de rachat

			// on change le cycle du pool a 7
			mAdminModel = new AdminModel();

			mAdminModel.changeCycleAnnuel(req, 7);

			mAdminModel.writeNewsAndEmailForWinner(req);

			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 2: // on change le cycle a 8
			mAdminModel = new AdminModel();

			mAdminModel.changeCycleAnnuel(req, 8);

			mAdminModel.updateAgeForRookie(req,playersDao);

			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 3:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 4:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 5:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 6:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 7:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 8:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 9:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 10:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 11:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 12:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		}

	}

}
