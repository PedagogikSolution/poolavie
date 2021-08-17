package com.pedagogiksolution.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;

public class UpdatePlayersTemplateFromApiCron extends HttpServlet {



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
	
		
		playersDao.truncatePlayersTemplate();
		
		
		playersDao.addPlayersFromApiToPlayersTemplate();
		
		
		
		
		
	
		
	}

}
