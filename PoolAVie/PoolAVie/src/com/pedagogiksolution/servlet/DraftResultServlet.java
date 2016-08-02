package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.LoginModel;

public class DraftResultServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2767768156351834050L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Pool mBean = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBean.getCycleAnnuel();

	if (cycleAnnuel == 3) {
	    LoginModel mModel = new LoginModel(req);
	    mModel.createSessionDraftRoundBean();
	    mModel.createSessionEquipeBean();
	    mModel.createSessionDraftPickBean();
	    mModel.createSessionAttaquantBean();
	    mModel.createSessionDefenseurBean();
	    mModel.createSessionGardienBean();
	    mModel.createSessionRecrueBean();
	}
	req.getRequestDispatcher("jsp/draft/draft_result.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
