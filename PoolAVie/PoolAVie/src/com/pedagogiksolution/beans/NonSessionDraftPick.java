package com.pedagogiksolution.beans;

import java.io.Serializable;
import java.util.List;


public class NonSessionDraftPick implements Serializable {
    

    /**
     * 
     */
    private static final long serialVersionUID = 5563922177699273804L;
    
    private List<Long> team_id; 
    private List<Long> pick_no;
    private List<Long> original_pick_id;
    private List<Long> ronde;
    private List<String> teamNameOriginalPick;
    
    
    public List<Long> getTeam_id() {
	return team_id;
    }
    public void setTeam_id(List<Long> team_id) {
	this.team_id = team_id;
    }
   
 
    public List<Long> getPick_no() {
	return pick_no;
    }
    public void setPick_no(List<Long> pick_no) {
	this.pick_no = pick_no;
    }
    public List<Long> getOriginal_pick_id() {
	return original_pick_id;
    }
    public void setOriginal_pick_id(List<Long> original_pick_id) {
	this.original_pick_id = original_pick_id;
    }
    public List<String> getTeamNameOriginalPick() {
	return teamNameOriginalPick;
    }
    public void setTeamNameOriginalPick(List<String> teamNameOriginalPick) {
	this.teamNameOriginalPick = teamNameOriginalPick;
    }
    public List<Long> getRonde() {
	return ronde;
    }
    public void setRonde(List<Long> ronde) {
	this.ronde = ronde;
    }

}
