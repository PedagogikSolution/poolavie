package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;

public class AdminPoolServlet extends HttpServlet {
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
	    mModelDraft.putDatastoreIntoBean(mBeanPool,req);
	}
	
	switch(cycleAnnuel){
	case 0: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 1: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 2: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 3: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 4: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 5: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 6: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	case 7: req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
	    break;
	}
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    }
    
    
    

}
