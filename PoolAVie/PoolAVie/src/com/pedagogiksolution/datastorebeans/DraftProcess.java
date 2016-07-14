package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;


public class DraftProcess implements Serializable {

   
    
    /**
     * 
     */
    private static final long serialVersionUID = 4960685510731786182L;
    

    private int currentPick;
    private int currentPicker;
  
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
    
    

}
