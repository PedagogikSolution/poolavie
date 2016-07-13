package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;


public class DraftProcess implements Serializable {

   
    
    /**
     * 
     */
    private static final long serialVersionUID = 4960685510731786182L;
    
    private int pool_id;
    private int currentPick;
    
    public int getPool_id() {
	return pool_id;
    }
    public void setPool_id(int pool_id) {
	this.pool_id = pool_id;
    }
    public int getCurrentPick() {
	return currentPick;
    }
    public void setCurrentPick(int currentPick) {
	this.currentPick = currentPick;
    }
    

}
