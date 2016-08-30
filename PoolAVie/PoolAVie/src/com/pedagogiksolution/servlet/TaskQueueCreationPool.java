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

        
    	
    	TaskQueueModel mModel = new TaskQueueModel(req);
    	
    	
    	mModel.createDatastoreEquipe();
	
    }
    
   

}
