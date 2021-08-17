package com.pedagogiksolution.cron;

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
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.ApiTeams;
import com.pedagogiksolution.beans.PlayersFeed;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.TeamsDao;

public class SportFeedApiTestCron extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5777703798823859657L;

	public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayersDao playersDao;
	private TeamsDao teamsDao;
	
	@Override
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
		this.teamsDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeamsDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		ApiTeams mBeanTeams = new ApiTeams();
		
		mBeanTeams= teamsDao.getAllTeamsId();
		
		playersDao.truncateTeamsFromApiDatabase();
		
		
		ArrayList<Integer> id = new ArrayList<>();
		ArrayList<String> nom = new ArrayList<>();
		ArrayList<String> abbreviation = new ArrayList<>();
		
		
		
		
		for(int i=0;i<mBeanTeams.getAllId().size();i++) {
			
			
		int teamId = mBeanTeams.getAllId().get(i);
		
		
		
		
		String url = "https://statsapi.web.nhl.com/api/v1/teams/"+teamId+"/roster/fullRoster";

		HttpGet request = new HttpGet(url);
		//String auth = "3a0e9a0a-861e-4065-bd34-c6670d" + ":" + "MYSPORTSFEEDS";
		//byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		//String authHeader = "Basic " + new String(encodedAuth);
		//request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		//request.setHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");

		HttpClient client = HttpClientBuilder.create().build();

		HttpResponse response = client.execute(request);

		// int statusCode = response.getStatusLine().getStatusCode();

		// System.out.println(statusCode);

		HttpEntity mEntity = response.getEntity();

		InputStream instream = mEntity.getContent();

		// create once, reuse
		ObjectMapper mapper = new ObjectMapper();
		JsonNode mNode = mapper.readTree(instream);

		JsonNode mNodePlayers = mNode.path("roster");

		Iterator<JsonNode> iterator = mNodePlayers.elements();
		
		while (iterator.hasNext()) {
			PlayersFeed mResult = new PlayersFeed();
			JsonNode mNodePlayer = iterator.next();

			JsonNode mPlayer = mNodePlayer.path("person");

			mResult = mapper.treeToValue(mPlayer, PlayersFeed.class);

			id.add(mResult.getId());
			nom.add(mResult.getFullName());
			abbreviation.add(mBeanTeams.getAllAbbreviation().get(i));
			
			// on ajoute a bdd
			// Queue queue = QueueFactory.getDefaultQueue();
			// queue.add(TaskOptions.Builder.withUrl(url))

		}

		instream.close();

		
		
		}
		
		playersDao.addPlayersFromSportFeed(id, nom,abbreviation );		
		// on cherche dans tout les peoples id ceux qui sont active false et RosterStatus Y pour trouver les joueurs autonomes n'appartenant pas à aucune équipe
		
		// depart a 8466137 fini a dernier id de la table api
		
		int lastId = playersDao.getLastIdFromApi();
		
		int firstId=8466137;
		
		
		int total = lastId-firstId;
		
		int interval = total/25;
		
		
			for(int i=1;i<26;i++) {
			if(i>1) {
				firstId= lastId+1;
			}
			lastId= firstId+interval;	
				
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/TaskQueueGetNHLPlayersIdFromAPI")
					.param("firstId", String.valueOf(firstId))
					.param("lastId", String.valueOf(lastId)));
			
		
			}
		
	}

}
