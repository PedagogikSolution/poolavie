package com.pedagogiksolution.cron;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.utils.EMF;

public class ResetForTestCron extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1938855431213358344L;

    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	DraftProcess mBeanDraftProcess = new DraftProcess();

	Key clefDatastore = KeyFactory.createKey("DraftProcess", "1");

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {

	    em = emf.createEntityManager();
	    mBeanDraftProcess = em.find(DraftProcess.class, clefDatastore);
	    mBeanDraftProcess.setTeam1IsOpen(0);
	    mBeanDraftProcess.setTeam2IsOpen(0);
	    mBeanDraftProcess.setTeam3IsOpen(0);
	    mBeanDraftProcess.setTeam4IsOpen(0);
	    mBeanDraftProcess.setTeam5IsOpen(0);
	    mBeanDraftProcess.setTeam6IsOpen(0);
	    mBeanDraftProcess.setTeam7IsOpen(0);
	    mBeanDraftProcess.setTeam8IsOpen(0);
	    mBeanDraftProcess.setTeam9IsOpen(0);
	    mBeanDraftProcess.setTeam10IsOpen(0);
	    mBeanDraftProcess.setTeam11IsOpen(0);
	    mBeanDraftProcess.setTeam12IsOpen(0);
	    mBeanDraftProcess.setCurrentPick(1);
	    mBeanDraftProcess = em.merge(mBeanDraftProcess);
	} finally {
	    if (em != null)
		em.close();
	}
	
	req.getSession().invalidate();

    }
}
