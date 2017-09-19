package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;

public class DraftProcess implements Serializable {

   
    
    /**
     * 
     */
    private static final long serialVersionUID = 5960685510751786185L;

    private String poolID;
    private int currentPick;
    private int currentPicker;
    private int numberPickRestant;
    private int team1IsOpen;
    private int team2IsOpen;
    private int team3IsOpen;
    private int team4IsOpen;
    private int team5IsOpen;
    private int team6IsOpen;
    private int team7IsOpen;
    private int team8IsOpen;
    private int team9IsOpen;
    private int team10IsOpen;
    private int team11IsOpen;
    private int team12IsOpen;
    private int oneFinish;
      
    
    public int getCurrentPick() {
	return currentPick;
    }
    public void setCurrentPick(int currentPick5) {
	this.currentPick = currentPick5;
    }
    public int getCurrentPicker() {
	return currentPicker;
    }
    public void setCurrentPicker(int currentPicker5) {
	this.currentPicker = currentPicker5;
    }
    
    public String getPoolID() {
	return poolID;
    }
    public void setPoolID(String poolID_teamId) {
	this.poolID = poolID_teamId;
    }
    public int getNumberPickRestant() {
	return numberPickRestant;
    }
    public void setNumberPickRestant(int numberPickRestant) {
	this.numberPickRestant = numberPickRestant;
    }
    
    public int getTeam1IsOpen() {
    	return team1IsOpen;
    }
    public void setTeam1IsOpen(int team1IsOpen) {
    	this.team1IsOpen = team1IsOpen;
    }
    public int getTeam2IsOpen() {
    	return team2IsOpen;
    }
    public void setTeam2IsOpen(int team2IsOpen) {
    	this.team2IsOpen = team2IsOpen;
    }
    public int getTeam3IsOpen() {
    	return team3IsOpen;
    }
    public void setTeam3IsOpen(int team3IsOpen) {
    	this.team3IsOpen = team3IsOpen;
    }
    public int getTeam4IsOpen() {
    	return team4IsOpen;
    }
    public void setTeam4IsOpen(int team4IsOpen) {
    	this.team4IsOpen = team4IsOpen;
    }
    public int getTeam5IsOpen() {
    	return team5IsOpen;
    }
    public void setTeam5IsOpen(int team5IsOpen) {
    	this.team5IsOpen = team5IsOpen;
    }
    public int getTeam6IsOpen() {
    	return team6IsOpen;
    }
    public void setTeam6IsOpen(int team6IsOpen) {
    	this.team6IsOpen = team6IsOpen;
    }
    public int getTeam7IsOpen() {
    	return team7IsOpen;
    }
    public void setTeam7IsOpen(int team7IsOpen) {
    	this.team7IsOpen = team7IsOpen;
    }
    public int getTeam8IsOpen() {
    	return team8IsOpen;
    }
    public void setTeam8IsOpen(int team8IsOpen) {
    	this.team8IsOpen = team8IsOpen;
    }
    public int getTeam9IsOpen() {
    	return team9IsOpen;
    }
    public void setTeam9IsOpen(int team9IsOpen) {
    	this.team9IsOpen = team9IsOpen;
    }
    public int getTeam10IsOpen() {
    	return team10IsOpen;
    }
    public void setTeam10IsOpen(int team10IsOpen) {
    	this.team10IsOpen = team10IsOpen;
    }
    public int getTeam11IsOpen() {
    	return team11IsOpen;
    }
    public void setTeam11IsOpen(int team11IsOpen) {
    	this.team11IsOpen = team11IsOpen;
    }
    public int getTeam12IsOpen() {
    	return team12IsOpen;
    }
    public void setTeam12IsOpen(int team12IsOpen) {
    	this.team12IsOpen = team12IsOpen;
    }

    public int getOneFinish() {
    	return oneFinish;
    }
    public void setOneFinish(int oneFinish) {
    	this.oneFinish = oneFinish;
    }
    
public Entity mapBeanToEntityForDatastore(DraftProcess mBean, String name) {
		
		Entity mEntity = new Entity("DraftProcess", name);
		
		mEntity.setProperty("currentPick", mBean.getCurrentPick());
		
		mEntity.setProperty("currentPicker", mBean.getCurrentPicker());
		
		mEntity.setProperty("numberPickRestant", mBean.getNumberPickRestant());
		
		mEntity.setProperty("team1IsOpen", mBean.getTeam1IsOpen());
		
		mEntity.setProperty("team2IsOpen", mBean.getTeam5IsOpen());
		
		mEntity.setProperty("team3IsOpen", mBean.getTeam5IsOpen());
		
		mEntity.setProperty("team4IsOpen", mBean.getTeam5IsOpen());
		
		mEntity.setProperty("team5IsOpen", mBean.getTeam5IsOpen());
		
		mEntity.setProperty("team6IsOpen", mBean.getTeam6IsOpen());
		
		mEntity.setProperty("team7IsOpen", mBean.getTeam7IsOpen());
		
		mEntity.setProperty("team8IsOpen", mBean.getTeam8IsOpen());
		
		mEntity.setProperty("team9IsOpen", mBean.getTeam9IsOpen());
		
		mEntity.setProperty("team10IsOpen", mBean.getTeam10IsOpen());
		
		mEntity.setProperty("team11IsOpen", mBean.getTeam11IsOpen());
		
		mEntity.setProperty("team12IsOpen", mBean.getTeam12IsOpen());
		
		mEntity.setProperty("oneFinish", mBean.getOneFinish());
		
		return mEntity;
	}

public DraftProcess mapDatastoreToBean(Entity mEntity, DraftProcess mBeanDraftProcess) {
	
	Long currentPick = (Long) mEntity.getProperty("currentPick");
	mBeanDraftProcess.setCurrentPick(currentPick.intValue());
	
	Long currentPicker = (Long) mEntity.getProperty("currentPicker");
	mBeanDraftProcess.setCurrentPicker(currentPicker.intValue());
	
	Long numberPickRestant = (Long) mEntity.getProperty("numberPickRestant");
	mBeanDraftProcess.setNumberPickRestant(numberPickRestant.intValue());
	
	Long team1IsOpen = (Long) mEntity.getProperty("team1IsOpen");
	mBeanDraftProcess.setTeam1IsOpen(team1IsOpen.intValue());
	
	Long team2IsOpen = (Long) mEntity.getProperty("team2IsOpen");
	mBeanDraftProcess.setTeam2IsOpen(team2IsOpen.intValue());
	
	Long team3IsOpen = (Long) mEntity.getProperty("team3IsOpen");
	mBeanDraftProcess.setTeam3IsOpen(team3IsOpen.intValue());
	
	Long team4IsOpen = (Long) mEntity.getProperty("team4IsOpen");
	mBeanDraftProcess.setTeam4IsOpen(team4IsOpen.intValue());
	
	Long team5IsOpen = (Long) mEntity.getProperty("team5IsOpen");
	mBeanDraftProcess.setTeam5IsOpen(team5IsOpen.intValue());
	
	Long team6IsOpen = (Long) mEntity.getProperty("team6IsOpen");
	mBeanDraftProcess.setTeam6IsOpen(team6IsOpen.intValue());
	
	Long team7IsOpen = (Long) mEntity.getProperty("team7IsOpen");
	mBeanDraftProcess.setTeam7IsOpen(team7IsOpen.intValue());
	
	Long team8IsOpen = (Long) mEntity.getProperty("team8IsOpen");
	mBeanDraftProcess.setTeam8IsOpen(team8IsOpen.intValue());
	
	Long team9IsOpen = (Long) mEntity.getProperty("team9IsOpen");
	mBeanDraftProcess.setTeam9IsOpen(team9IsOpen.intValue());
	
	Long team10IsOpen = (Long) mEntity.getProperty("team10IsOpen");
	mBeanDraftProcess.setTeam10IsOpen(team10IsOpen.intValue());
	
	Long team11IsOpen = (Long) mEntity.getProperty("team11IsOpen");
	mBeanDraftProcess.setTeam11IsOpen(team11IsOpen.intValue());
	
	Long team12IsOpen = (Long) mEntity.getProperty("team12IsOpen");
	mBeanDraftProcess.setTeam12IsOpen(team12IsOpen.intValue());
	
	Long oneFinish = (Long) mEntity.getProperty("oneFinish");
	mBeanDraftProcess.setOneFinish(oneFinish.intValue());
	
	
	
	return mBeanDraftProcess;
	
}




}
