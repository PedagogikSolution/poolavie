package com.pedagogiksolution.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedagogiksolution.beans.PlayersFeed;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;

public class TaskQueueUpdateBirthdayAndPosition extends HttpServlet {
   
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6798830506473906534L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	
    private PlayersDao playerDao;

    @Override
    public void init() throws ServletException {
	/* Recuperation d'une instance de notre DAO Utilisateur */
	this.playerDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	String player_id = req.getParameter("player_id");
    	int playerId=Integer.parseInt(player_id);	
    	
    	
    
    	
					
		String url = "https://statsapi.web.nhl.com/api/v1/people/"+playerId;

		HttpGet request = new HttpGet(url);

		HttpClient client = HttpClientBuilder.create().build();

		HttpResponse response = client.execute(request);

		HttpEntity mEntity = response.getEntity();

		InputStream instream = mEntity.getContent();

		// create once, reuse
		ObjectMapper mapper = new ObjectMapper();
		JsonNode mNode = mapper.readTree(instream);

		JsonNode mNodePlayers = mNode.path("people");

		Iterator<JsonNode> iterator = mNodePlayers.elements();
		
		while (iterator.hasNext()) {
			PlayersFeed mResult = new PlayersFeed();
			JsonNode mNodePlayer = iterator.next();

			

			mResult = mapper.treeToValue(mNodePlayer, PlayersFeed.class);

			
			JsonNode mNodePosition = mResult.getPrimaryPosition();
			
			String positionAbbreviation = mNodePosition.get("abbreviation").asText();
			
			String birthDate = mResult.getBirthDate();
			
			
			
			
				playerDao.updateBirthdayAndPosition(playerId,positionAbbreviation,birthDate);
			
			
			

		}

		instream.close();
		
		
		
    
    }
    
   

}
