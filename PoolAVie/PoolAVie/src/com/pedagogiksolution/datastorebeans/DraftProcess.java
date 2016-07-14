package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;


public class DraftProcess implements Serializable {

   
    
    /**
     * 
     */
    private static final long serialVersionUID = 4960685510731786182L;
    

    private Long currentPick;
    private Long currentPicker;
  
    public Long getCurrentPick() {
	return currentPick;
    }
    public void setCurrentPick(Long currentPick2) {
	this.currentPick = currentPick2;
    }
    public Long getCurrentPicker() {
	return currentPicker;
    }
    public void setCurrentPicker(Long currentPicker2) {
	this.currentPicker = currentPicker2;
    }
    
    

}
