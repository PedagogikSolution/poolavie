package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.model.TaskQueueModel;

public class TaskQueueCreationPool extends HttpServlet {
  
      

   
    /**
     * 
     */
    private static final long serialVersionUID = 5153659828527340877L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        
	
	String fromTag = req.getParameter("fromTag");
	int fromTagId = Integer.parseInt(fromTag);
	
	
    	
    	TaskQueueModel mModel = new TaskQueueModel(req);
    	
    	switch(fromTagId){
    	case 1:
    	mModel.createDatastoreEquipe();
    	    break;
    	case 2:
    	mModel.createDatastorePlayers();
    	    break;
    	case 3:
        	mModel.createDatastoreAttaquant();
        	    break;
    	case 4:
        	mModel.createDatastoreDefenseur();
        	    break;
    	case 5:
        	mModel.createDatastoreGardien();
        	    break;
    	case 6:
        	mModel.createDatastoreRecrue();
        	    break;
    	    
    	}
    	
	
    }
    
   

}
