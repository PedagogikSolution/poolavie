package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DraftProcess")
public class DraftProcess implements Serializable {

   
    
    /**
     * 
     */
    private static final long serialVersionUID = 4960685510731786182L;
    
    @Id
    private String poolID;
    private int currentPick;
    private int currentPicker;
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
    
    
    
    public int getCurrentPick() {
	return currentPick;
    }
    public void setCurrentPick(int currentPick2) {
	this.currentPick = currentPick2;
    }
    public int getCurrentPicker() {
	return currentPicker;
    }
    public void setCurrentPicker(int currentPicker2) {
	this.currentPicker = currentPicker2;
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
    public String getPoolID() {
	return poolID;
    }
    public void setPoolID(String poolID_teamId) {
	this.poolID = poolID_teamId;
    }
    
    

}
