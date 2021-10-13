package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;

public class AdminTradeServlet extends HttpServlet {
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

		req.getRequestDispatcher("jsp/admin/adminTrade.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String openTradeState = req.getParameter("openTrade");
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 5 || cycleAnnuel == 6) {

			if (openTradeState != null) {
				AdminModel mModelAdmin = new AdminModel();
				switch (openTradeState) {

				case "0":
					mModelAdmin.closeTrade(req);
					req.getRequestDispatcher("jsp/admin/adminTrade.jsp").forward(req, resp);
					break;
				case "1":
					mModelAdmin.openTrade(req);
					req.getRequestDispatcher("jsp/admin/adminTrade.jsp").forward(req, resp);
					break;
				}

			}
		}

	}

}
