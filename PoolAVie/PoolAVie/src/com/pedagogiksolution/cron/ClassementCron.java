package com.pedagogiksolution.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.cron.model.ClassementCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;

public class ClassementCron extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -1614017696917791335L;

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClassementDao classementDao;

    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.classementDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClassementDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// initilisation du métier
	ClassementCronModel mModel = new ClassementCronModel(classementDao);

	int numberOfPool = mModel.getNumberOfPool();

	if (numberOfPool == 0) {
	    //TODO  log de l'erreur
	} else {

	    for (int i = 1; i < (numberOfPool + 1); i++) {
		mModel.putDatabaseInDatastore(i);
	    }

	}

    }
}
