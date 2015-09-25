package com.pedagogiksolution.poolavie.beans;

import java.util.List;

public class DraftPick {
    private List<Integer> draft_id;
    private List<String> equipe;
    private List<Integer> round;
    private List<String> from;
    private List<Integer> role_echange;
    
    
    
   
    public List<Integer> getRound() {
	return round;
    }
    public void setRound(List<Integer> round) {
	this.round = round;
    }
    
    public List<Integer> getRole_echange() {
	return role_echange;
    }
    public void setRole_echange(List<Integer> role_echange) {
	this.role_echange = role_echange;
    }
    public List<String> getEquipe() {
	return equipe;
    }
    public void setEquipe(List<String> equipe) {
	this.equipe = equipe;
    }
    public List<String> getFrom() {
	return from;
    }
    public void setFrom(List<String> from) {
	this.from = from;
    }
    public List<Integer> getDraft_id() {
	return draft_id;
    }
    public void setDraft_id(List<Integer> draft_id) {
	this.draft_id = draft_id;
    }
    
}
