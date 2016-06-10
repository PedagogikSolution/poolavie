package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DraftRound")
public class DraftRound implements Serializable {
    

    
    /**
     * 
     */
    private static final long serialVersionUID = 4099491969522931254L;
    
    
    @Id
    private String poolId;
    private List<Integer> draft_pick_no;   
    private List<String> equipe;
    private List<Integer> ronde;
    private List<Integer> team_id;
    private List<String> from_who;
    private List<Integer> team_id_from;
    private List<Integer> team_count;
    private List<Integer> follow_up;
    private List<String> player_drafted;
    private List<Integer> year_of_draft;
    
    public String getPoolId() {
	return poolId;
    }
    public void setPoolId(String poolId) {
	this.poolId = poolId;
    }
    public List<Integer> getDraft_pick_no() {
	return draft_pick_no;
    }
    public void setDraft_pick_no(List<Integer> draft_pick_no) {
	this.draft_pick_no = draft_pick_no;
    }
    public List<String> getEquipe() {
	return equipe;
    }
    public void setEquipe(List<String> equipe) {
	this.equipe = equipe;
    }
    public List<Integer> getRonde() {
	return ronde;
    }
    public void setRonde(List<Integer> ronde) {
	this.ronde = ronde;
    }
    public List<Integer> getTeam_id() {
	return team_id;
    }
    public void setTeam_id(List<Integer> team_id) {
	this.team_id = team_id;
    }
    public List<String> getFrom_who() {
	return from_who;
    }
    public void setFrom_who(List<String> from_who) {
	this.from_who = from_who;
    }
    public List<Integer> getTeam_id_from() {
	return team_id_from;
    }
    public void setTeam_id_from(List<Integer> team_id_from) {
	this.team_id_from = team_id_from;
    }
    public List<Integer> getTeam_count() {
	return team_count;
    }
    public void setTeam_count(List<Integer> team_count) {
	this.team_count = team_count;
    }
    public List<Integer> getFollow_up() {
	return follow_up;
    }
    public void setFollow_up(List<Integer> follow_up) {
	this.follow_up = follow_up;
    }
    public List<String> getPlayer_drafted() {
	return player_drafted;
    }
    public void setPlayer_drafted(List<String> player_drafted) {
	this.player_drafted = player_drafted;
    }
    public List<Integer> getYear_of_draft() {
	return year_of_draft;
    }
    public void setYear_of_draft(List<Integer> year_of_draft) {
	this.year_of_draft = year_of_draft;
    }
    
    

}
