package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DraftPick")
public class DraftPick implements Serializable {
    
  
    
  
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -2404958577056623673L;
    @Id
    private String poolTeamId;
    private List<Integer> team_id; 
    private List<Integer> pick_no;
    private List<Integer> original_pick_id;
    private List<Integer> orderForTheRound;
    
    
    public List<Integer> getTeam_id() {
	return team_id;
    }
    public void setTeam_id(List<Integer> team_id) {
	this.team_id = team_id;
    }
   
    public String getPoolTeamId() {
	return poolTeamId;
    }
    public void setPoolTeamId(String poolTeamId) {
	this.poolTeamId = poolTeamId;
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

}
