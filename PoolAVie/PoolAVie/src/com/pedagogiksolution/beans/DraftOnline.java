package com.pedagogiksolution.beans;

import java.util.UUID;

public class DraftOnline implements java.io.Serializable  {

    /**
     * 
     */
    private static final long serialVersionUID = -3186412766432464016L;
    
    private String id;
    private String token;
    private String channelId;
    private String draftPickMade;
    private String pickNumber;
    private String playerDrafted;
    private String teamOfPlayer;
    private String salaire;
    private String position;
    private String teamThatDraft;
    private String channelKey2;

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	
	public String getDraftPickMade() {
		return draftPickMade;
	}

	public void setDraftPickMade(String draftPickMade) {
		this.draftPickMade = draftPickMade;
	}

	public String getPickNumber() {
		return pickNumber;
	}

	public void setPickNumber(String pickNumber) {
		this.pickNumber = pickNumber;
	}

	public String getPlayerDrafted() {
		return playerDrafted;
	}

	public void setPlayerDrafted(String playerDrafted) {
		this.playerDrafted = playerDrafted;
	}

	public String getTeamOfPlayer() {
		return teamOfPlayer;
	}

	public void setTeamOfPlayer(String teamOfPlayer) {
		this.teamOfPlayer = teamOfPlayer;
	}

	public String getSalaire() {
		return salaire;
	}

	public void setSalaire(String salaire) {
		this.salaire = salaire;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTeamThatDraft() {
		return teamThatDraft;
	}

	public void setTeamThatDraft(String teamThatDraft) {
		this.teamThatDraft = teamThatDraft;
	}
	
	public String getChannelKey(String userId) {
		this.id = UUID.randomUUID().toString();
		return userId + id;
	}

	public String getChannelKey2() {
		return channelKey2;
	}

	public void setChannelKey2(String channelKey2) {
		this.channelKey2 = channelKey2;
	}

}
