package com.pedagogiksolution.beans;

import java.util.List;

public class GestionCompte implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5162130408219621695L;
    
    private List<String> username;

    public List<String> getUsername() {
	return username;
    }

    public void setUsername(List<String> username) {
	this.username = username;
    }

}
