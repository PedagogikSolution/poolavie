package com.pedagogiksolution.cron;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.PlayersFeed;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;

public class SportFeedApiTestCron extends HttpServlet {

   
    /**
	 * 
	 */
	private static final long serialVersionUID = -5777703798823859657L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
    private PlayersDao playersDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// initilisation du métier
    	
    	HttpGet request = new HttpGet("https://api.mysportsfeeds.com/v2.1/pull/nhl/players.json?season=upcoming");
    	String auth = "3a0e9a0a-861e-4065-bd34-c6670d" + ":" + "MYSPORTSFEEDS";
    	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
    	String authHeader = "Basic " + new String(encodedAuth);
    	request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
    	request.setHeader(HttpHeaders.ACCEPT_ENCODING,"gzip");

    	HttpClient client = HttpClientBuilder.create().build();
    	
    	HttpResponse response = client.execute(request);

    	//int statusCode = response.getStatusLine().getStatusCode();
    	
    	//System.out.println(statusCode);
    	
    	HttpEntity mEntity = response.getEntity();
    	
    	InputStream instream = mEntity.getContent();
    	
    	 // create once, reuse
    	 ObjectMapper mapper = new ObjectMapper();
    	 JsonNode mNode = mapper.readTree(instream);
    	 
    	 JsonNode mNodePlayers = mNode.path("players");
    	 
    	 Iterator<JsonNode> iterator = mNodePlayers.elements();
    	 ArrayList<Integer> id = new ArrayList<>();
    	 ArrayList<String> nom = new ArrayList<>();
    	 ArrayList<String> status = new ArrayList<>();
    	 while (iterator.hasNext()) {
    		 PlayersFeed mResult = new PlayersFeed();
    		 JsonNode mNodePlayer = iterator.next();
    		 
    		 JsonNode mPlayer = mNodePlayer.path("player");
    		 ObjectMapper mapper2 = new ObjectMapper();
    		 mResult = mapper2.treeToValue(mPlayer, PlayersFeed.class);
    		 
    		 id.add( mResult.getId());
    	 	 nom.add(mResult.getFirstName()+" " + mResult.getLastName());
    	 	 status.add(mResult.getCurrentRosterStatus());
    		 // on ajoute a bdd
    		//Queue queue = QueueFactory.getDefaultQueue();
    		// queue.add(TaskOptions.Builder.withUrl(url))
    		 
    		 
    		 
          }
   	
    	
    	instream.close();
   
    	playersDao.addPlayersFromSportFeed(id,nom,status);
    }

	
	
}
