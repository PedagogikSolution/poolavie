package com.pedagogiksolution.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.DraftProcessModel;

public class ConnectDraftServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 54388717965389157L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String sTestIfOpen = req.getParameter("testIfOpen");
	String errorChannel = req.getParameter("error");
	String closeChannel = req.getParameter("close");

	if (sTestIfOpen != null) {
	    if (sTestIfOpen.equalsIgnoreCase("1")) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		String poolID = mBeanPool.getPoolID();

		DraftProcessModel mModel = new DraftProcessModel(teamId, poolID);
		Boolean isAlreadyOpen = mModel.testIfConnectionOpen();

		if (isAlreadyOpen) {

		} else {
		    String tokenString = poolID + "_" + teamId;
		    Map<String, String> messageToClient = new HashMap<String, String>();
		    messageToClient.put("testIfOpen", "1");
		    messageToClient.put("draftPickMade", "0");
		    JSONObject JSONmessage = new JSONObject(messageToClient);
		    String message = JSONmessage.toString();

		    ChannelService channelService = ChannelServiceFactory.getChannelService();
		    channelService.sendMessage(new ChannelMessage(tokenString, message));
		}
	    }
	}

	 if (errorChannel != null) {
	    if (errorChannel.equalsIgnoreCase("1")) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		String poolID = mBeanPool.getPoolID();

		DraftProcessModel mModel = new DraftProcessModel(teamId, poolID);
		mModel.resetConnection(req);

	    }
	}
	if (closeChannel != null) {
	    if (closeChannel.equalsIgnoreCase("1")) {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		String poolID = mBeanPool.getPoolID();

		DraftProcessModel mModel = new DraftProcessModel(teamId, poolID);
		mModel.resetConnection(req);

	    }
	} 

    }

}
