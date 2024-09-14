package com.pedagogiksolution.cron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Pool;

public class UpdatePlayersTemplateStatsCron extends HttpServlet {



	/**
	 * 
	 */
	private static final long serialVersionUID = -4436325833265593227L;
	
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayersDao playersDao;
	
	@Override
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		//on recupere l'année
	//	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		
	//	String thisYear = mBeanPool.getThisYear();
		
	//	String thisYearForApi = thisYear.replace("-", "");
		
		String thisYearForApi = "20242025";
		//TODO CRON JOB SEASON API
		
		
		// get all the players id from players template
		
		List<Integer> idList = new ArrayList<>();
		
		NonSessionPlayers mBeanPlayersId = new NonSessionPlayers();
		
		mBeanPlayersId = playersDao.getAllPlayersID();	
		
		idList=mBeanPlayersId.getPlayers_id();
		
		playersDao.updatePlayersWithNoStats();
		
		
			for(int i=0;i<idList.size();i++) {
			
				
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/TaskQueueUpdatePlayersStats")
					.param("player_id", String.valueOf(idList.get(i))).param("thisyear", thisYearForApi));
			
		
			}
		
		
		
		
		
	
		
	}

}
