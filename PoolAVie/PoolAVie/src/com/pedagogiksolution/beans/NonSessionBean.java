package com.pedagogiksolution.beans;

public class NonSessionBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2722819452610321938L;

    private int teamNumberToShow;
    private String nomDuTeam;
    private String logoDuTeam;
    private int positionDuTeam;
    private int pointTotalDuTeam;
    private int pointHierDuTeam;
    
    

    public int getTeamNumberToShow() {
	return teamNumberToShow;
    }

    public void setTeamNumberToShow(int teamNumberToShow) {
	this.teamNumberToShow = teamNumberToShow;
    }

    public String getNomDuTeam() {
	return nomDuTeam;
    }

    public void setNomDuTeam(String nomDuTeam) {
	this.nomDuTeam = nomDuTeam;
    }

    public String getLogoDuTeam() {
	return logoDuTeam;
    }

    public void setLogoDuTeam(String logoDuTeam) {
	this.logoDuTeam = logoDuTeam;
    }

    public int getPositionDuTeam() {
	return positionDuTeam;
    }

    public void setPositionDuTeam(int positionDuTeam) {
	this.positionDuTeam = positionDuTeam;
    }

    public int getPointTotalDuTeam() {
	return pointTotalDuTeam;
    }

    public void setPointTotalDuTeam(int pointTotalDuTeam) {
	this.pointTotalDuTeam = pointTotalDuTeam;
    }

    public int getPointHierDuTeam() {
	return pointHierDuTeam;
    }

    public void setPointHierDuTeam(int pointHierDuTeam) {
	this.pointHierDuTeam = pointHierDuTeam;
    }

}
