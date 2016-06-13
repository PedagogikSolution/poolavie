package com.pedagogiksolution.beans;

public class NonSessionBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2722819452610321938L;

    private int teamNumberToShow;

    public int getTeamNumberToShow() {
	return teamNumberToShow;
    }

    public void setTeamNumberToShow(int teamNumberToShow) {
	this.teamNumberToShow = teamNumberToShow;
    }

}
