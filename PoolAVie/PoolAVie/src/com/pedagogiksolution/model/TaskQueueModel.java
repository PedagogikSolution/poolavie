package com.pedagogiksolution.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.utils.EMF;

public class TaskQueueModel {

	PlayersDao playersDao;
	DraftDao draftDao;
	HttpServletRequest req;
	
	public TaskQueueModel(DraftDao draftDao, PlayersDao playerDao, HttpServletRequest req) {
		
		this.playersDao=playerDao;
		this.draftDao=draftDao;
		this.req=req;
	}

	public TaskQueueModel(HttpServletRequest req2) {
	    this.req=req2;
	}

	public void persistPlayer() {
		String poolID = req.getParameter("poolID");
		int poolId = Integer.parseInt(poolID);
		String playersID = req.getParameter("playersID");
		int playersId = Integer.parseInt(playersID);
		String salaireID = req.getParameter("salaireID");
		int salaireId = Integer.parseInt(salaireID);		
		String teamID = req.getParameter("teamID");
		int teamId = Integer.parseInt(teamID);
		String clubEcole = req.getParameter("club_ecole");
		int clubEcoleId = Integer.parseInt(clubEcole);
		String acquireYearsID = req.getParameter("acquireYearsID");
		int acquireYearsId = Integer.parseInt(acquireYearsID);
		
		
		
		playersDao.persistPlayerPick(playersId,salaireId,poolId,teamId,clubEcoleId,acquireYearsId);
		
		
		
	}
	


	public void persistDraftRound() {
		String poolID = req.getParameter("poolID");
		int poolId = Integer.parseInt(poolID);
		String nom = req.getParameter("nom");
		String currentPick = req.getParameter("currentPick");
		int currentPickId = Integer.parseInt(currentPick);
		int currentPickReel = currentPickId-1;
		
		
		
		draftDao.persistPlayerPick(nom,currentPickReel,poolId);
		
		
	}

	
	
	public void createDatastoreEquipe() {
	    
	    String counter = req.getParameter("counter");
	    String poolID = req.getParameter("poolID");
	    String jspSessionName = poolID+"_"+counter;
	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;
	    try {
	
		em = emf.createEntityManager();
		Equipe mBean = new Equipe();
		//mBean = (Equipe) req.getSession().getAttribute(jspSessionName);
		mBean.setPoolTeamId(jspSessionName);
		mBean.setBudget_restant(52000000);
		em.persist(mBean);
	    } finally {

		// on ferme le manager pour libérer la mémoire
		if (em != null) {
		    em.close();

		}
	    }
	    
	}
	
	

}
