package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.PlayersDao;

public class TaskQueueModel {

	PlayersDao playersDao;
	DraftDao draftDao;
	HttpServletRequest req;
	
	public TaskQueueModel(DraftDao draftDao, PlayersDao playerDao, HttpServletRequest req) {
		
		this.playersDao=playerDao;
		this.draftDao=draftDao;
		this.req=req;
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
	
	

}
