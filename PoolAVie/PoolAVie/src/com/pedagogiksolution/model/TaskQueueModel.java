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
		
		String playerID = req.getParameter("playerID");
		int playerId = Integer.parseInt(playerID);
		String salaireID = req.getParameter("salaireID");
		int salaireId = Integer.parseInt(salaireID);
		String poolID = req.getParameter("poolID");
		int poolId = Integer.parseInt(poolID);
		String teamID = req.getParameter("teamID");
		int teamId = Integer.parseInt(teamID);
		String clubEcole = req.getParameter("clubEcole");
		int clubEcoleId = Integer.parseInt(clubEcole);
		String acquireYearsID = req.getParameter("acquireYearsID");
		int acquireYearsId = Integer.parseInt(acquireYearsID);
		
		
		playersDao.persistPlayerPick(playerId,salaireId,poolId,teamId,clubEcoleId,acquireYearsId);
		
		
		
	}
	


	public void persistDraftRound() {
		// TODO Auto-generated method stub
		
	}
	
	

}
