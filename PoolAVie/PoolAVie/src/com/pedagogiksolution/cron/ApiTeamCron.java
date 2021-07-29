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
import com.pedagogiksolution.beans.ApiTeams;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.TeamsDao;

public class ApiTeamCron extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660051576620038089L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private TeamsDao teamsDao;

	@Override
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.teamsDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTeamsDao();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		// on vide la bdd
		teamsDao.truncateTeamsFromApiDatabase();
		
		

		HttpGet request = new HttpGet("https://statsapi.web.nhl.com/api/v1/teams");
		
		HttpClient client = HttpClientBuilder.create().build();

		HttpResponse response = client.execute(request);

		HttpEntity mEntity = response.getEntity();

		InputStream instream = mEntity.getContent();

		// create once, reuse
		ObjectMapper mapper = new ObjectMapper();
		JsonNode mNode = mapper.readTree(instream);

		JsonNode mNodeTeams = mNode.path("teams");

		Iterator<JsonNode> iterator = mNodeTeams.elements();
		ArrayList<Integer> id = new ArrayList<>();
		ArrayList<String> teamNameAbbreviation = new ArrayList<>();
		while (iterator.hasNext()) {
			ApiTeams mResult = new ApiTeams();
			JsonNode mNodeTeam = iterator.next();

			mResult = mapper.treeToValue(mNodeTeam, ApiTeams.class);

			id.add(mResult.getId());
			teamNameAbbreviation.add(mResult.getAbbreviation());


		}

		instream.close();

		teamsDao.addTeamsFromApiToDatabase(id, teamNameAbbreviation);
	}

}

