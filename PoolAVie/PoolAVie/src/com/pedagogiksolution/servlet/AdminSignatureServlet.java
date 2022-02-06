package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;

public class AdminSignatureServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBeanPool, req);
		}

		req.getRequestDispatcher("jsp/admin/adminSignature.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AdminModel mAdminModel;
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		String numeroFormulaire = req.getParameter("numeroFormulaire");
		int numeroFormulaireId = Integer.parseInt(numeroFormulaire);

		if (cycleAnnuel == 5 || cycleAnnuel == 6) {

			switch (numeroFormulaireId) {

			case 1:
				mAdminModel = new AdminModel();
				mAdminModel.setupWaiverDate(poolID,req);
				break;

			case 2:
				mAdminModel = new AdminModel();
				mAdminModel.setupRookieDate(poolID,req);
				break;

			}

		} else {
			
		}
		
		req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);

	}

}
