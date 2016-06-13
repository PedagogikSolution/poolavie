package com.pedagogiksolution.beans;

import java.io.Serializable;
import java.util.List;


public class NonSessionDraftPick implements Serializable {
    

    /**
     * 
     */
    private static final long serialVersionUID = 5563922177699273804L;
    
    private List<Integer> team_id; 
    private List<Integer> pick_no;
    private List<Integer> original_pick_id;
    private List<Integer> orderForTheRound;
    private List<String> teamNameOriginalPick;
    
    
    public List<Integer> getTeam_id() {
	return team_id;
    }
    public void setTeam_id(List<Integer> team_id) {
	this.team_id = team_id;
    }
   
 
    public List<Integer> getPick_no() {
	return pick_no;
    }
    public void setPick_no(List<Integer> pick_no) {
	this.pick_no = pick_no;
    }
    public List<Integer> getOriginal_pick_id() {
	return original_pick_id;
    }
    public void setOriginal_pick_id(List<Integer> original_pick_id) {
	this.original_pick_id = original_pick_id;
    }
    public List<Integer> getOrderForTheRound() {
	return orderForTheRound;
    }
    public void setOrderForTheRound(List<Integer> orderForTheRound) {
	this.orderForTheRound = orderForTheRound;
    }
    public List<String> getTeamNameOriginalPick() {
	return teamNameOriginalPick;
    }
    public void setTeamNameOriginalPick(List<String> teamNameOriginalPick) {
	this.teamNameOriginalPick = teamNameOriginalPick;
    }

}
