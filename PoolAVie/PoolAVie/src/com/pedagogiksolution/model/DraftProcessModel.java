package com.pedagogiksolution.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.utils.EMF;

public class DraftProcessModel {
    int teamId;
    String poolID;
    int poolId;

    public DraftProcessModel(int teamId, String poolID) {
	this.teamId = teamId;
	this.poolID = poolID;
    }

    public Boolean testIfConnectionOpen() {
	int isOpen = 0;
	DraftProcess mBeanDraftProcess = new DraftProcess();
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	Key clefDatastore = KeyFactory.createKey("DraftProcess", poolID);
	try {
	    em = emf.createEntityManager();
	    mBeanDraftProcess = em.find(DraftProcess.class, clefDatastore);

	    switch (teamId) {
	    case 1:
		isOpen = mBeanDraftProcess.getTeam1IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam1IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 2:
		isOpen = mBeanDraftProcess.getTeam2IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam2IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 3:
		isOpen = mBeanDraftProcess.getTeam3IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam3IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 4:
		isOpen = mBeanDraftProcess.getTeam4IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam4IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 5:
		isOpen = mBeanDraftProcess.getTeam5IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam5IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 6:
		isOpen = mBeanDraftProcess.getTeam6IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam6IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 7:
		isOpen = mBeanDraftProcess.getTeam7IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam7IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 8:
		isOpen = mBeanDraftProcess.getTeam8IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam8IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 9:
		isOpen = mBeanDraftProcess.getTeam9IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam9IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 10:
		isOpen = mBeanDraftProcess.getTeam10IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam10IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 11:
		isOpen = mBeanDraftProcess.getTeam11IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam11IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    case 12:
		isOpen = mBeanDraftProcess.getTeam12IsOpen();
		if (isOpen == 1) {
		    return true;
		} else {
		    mBeanDraftProcess.setTeam12IsOpen(1);
		    em.merge(mBeanDraftProcess);
		    return false;
		}
	    }

	   

	} finally {
	    if (em != null)
		em.close();
	}

	return false;
    }

    public void resetConnection(HttpServletRequest req) {
	req.getSession().removeAttribute("DraftOnline");
	DraftProcess mBeanDraftProcess = new DraftProcess();
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	Key clefDatastore = KeyFactory.createKey("DraftProcess", poolID);
	try {
	    em = emf.createEntityManager();
	    mBeanDraftProcess = em.find(DraftProcess.class, clefDatastore);

	    switch (teamId) {
	    case 1: mBeanDraftProcess.setTeam1IsOpen(0);
		
	    case 2:
		mBeanDraftProcess.setTeam2IsOpen(0);
	    case 3:
		mBeanDraftProcess.setTeam3IsOpen(0);
	    case 4:
		mBeanDraftProcess.setTeam4IsOpen(0);
	    case 5:
		mBeanDraftProcess.setTeam5IsOpen(0);
	    case 6:
		mBeanDraftProcess.setTeam6IsOpen(0);
	    case 7:
		mBeanDraftProcess.setTeam7IsOpen(0);
	    case 8:
		mBeanDraftProcess.setTeam8IsOpen(0);
	    case 9:
		mBeanDraftProcess.setTeam9IsOpen(0);
	    case 10:
		mBeanDraftProcess.setTeam10IsOpen(0);
	    case 11:
		mBeanDraftProcess.setTeam11IsOpen(0);
	    case 12:
		mBeanDraftProcess.setTeam12IsOpen(0);
	    }

	   em.merge(mBeanDraftProcess);

	} finally {
	    if (em != null)
		em.close();
	}
	
	MessageErreurBeans mBeanErreur = new MessageErreurBeans();
	mBeanErreur.setErreurConnectionDraft("Vous avez été déconnecté du serveur de draft. Cliquez sur cete boite pour vous reconnecter");
	req.setAttribute("messageErreur", mBeanErreur);
    }

}
