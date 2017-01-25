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
    private List<Long> team_id; 
    private List<Long> pick_no;
    private List<Long> original_pick_id;
    private List<Long> total_pick_number;
    private List<String> teamNameOriginalPick;
    
    
    public List<Long> getTeam_id() {
	return team_id;
    }
    public void setTeam_id(List<Long> team_id) {
	this.team_id = team_id;
    }
   
    public String getPoolTeamId() {
	return poolTeamId;
    }
    public void setPoolTeamId(String poolTeamId) {
	this.poolTeamId = poolTeamId;
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
    
    @SuppressWarnings("unchecked")
    public DraftPick mapDraftPickFromDatastore(com.google.appengine.api.datastore.Entity mEntity, DraftPick mBeanDraftPick) {
	
		List<Long> team_id=(List<Long>) mEntity.getProperty("team_id");
		mBeanDraftPick.setTeam_id(team_id);
	     
		List<Long> pick_no=(List<Long>) mEntity.getProperty("pick_no");
		mBeanDraftPick.setPick_no(pick_no);
	     
		List<Long> original_pick_id=(List<Long>) mEntity.getProperty("original_pick_id");
		mBeanDraftPick.setOriginal_pick_id(original_pick_id);
		
		List<Long> totalPickNumber=(List<Long>) mEntity.getProperty("total_pick_number");
		mBeanDraftPick.setTotal_pick_number(totalPickNumber);
		
		List<String> teamNameOriginalPick=(List<String>) mEntity.getProperty("teamNameOriginalPick");
		mBeanDraftPick.setTeamNameOriginalPick(teamNameOriginalPick);
	    	     
	
	     return mBeanDraftPick;
    }
    public List<Long> getTotal_pick_number() {
	return total_pick_number;
    }
    public void setTotal_pick_number(List<Long> total_pick_number) {
	this.total_pick_number = total_pick_number;
    }

}
