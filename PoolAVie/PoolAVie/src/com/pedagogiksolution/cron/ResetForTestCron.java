package com.pedagogiksolution.cron;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.utils.EMF;

public class ResetForTestCron extends HttpServlet {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1938855431213358344L;

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
        int  max_salaire_begin = 0, total_salaire_now = 0, budget_restant = 0, moy_sal_restant_draft = 0, nb_attaquant = 0;
        int nb_defenseur = 0, nb_gardien = 0, nb_rookie = 0, nb_contrat = 0, nb_equipe = 0, manquant_equipe = 0, manquant_att = 0, manquant_def = 0;
        int manquant_gardien = 0, manquant_recrue = 0, bonus_5m = 0, argent_recu = 0, bonus_penalite = 0;
	DraftProcess mBeanDraftProcess = new DraftProcess();

	Key clefDatastore = KeyFactory.createKey("DraftProcess", "1");

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {

	    em = emf.createEntityManager();
	    mBeanDraftProcess = em.find(DraftProcess.class, clefDatastore);
	    mBeanDraftProcess.setTeam1IsOpen(0);
	    mBeanDraftProcess.setTeam2IsOpen(0);
	    mBeanDraftProcess.setTeam3IsOpen(0);
	    mBeanDraftProcess.setTeam4IsOpen(0);
	    mBeanDraftProcess.setTeam5IsOpen(0);
	    mBeanDraftProcess.setTeam6IsOpen(0);
	    mBeanDraftProcess.setTeam7IsOpen(0);
	    mBeanDraftProcess.setTeam8IsOpen(0);
	    mBeanDraftProcess.setTeam9IsOpen(0);
	    mBeanDraftProcess.setTeam10IsOpen(0);
	    mBeanDraftProcess.setTeam11IsOpen(0);
	    mBeanDraftProcess.setTeam12IsOpen(0);
	    mBeanDraftProcess.setCurrentPick(1);
	    mBeanDraftProcess = em.merge(mBeanDraftProcess);
	} finally {
	    if (em != null)
		em.close();
	}
	
	// valeur de depart de la table Equipe
	max_salaire_begin = 52000000;
	total_salaire_now = 0;
	budget_restant = 52000000;
	moy_sal_restant_draft = 0;
	nb_attaquant = 0;
	nb_defenseur = 0;
	nb_gardien = 0;
	nb_rookie = 0;
	nb_contrat = 0;
	nb_equipe = 0;
	manquant_equipe = 23;
	manquant_att = 8;
	manquant_def = 5;
	manquant_gardien = 2;
	manquant_recrue = 8;
	bonus_5m = 0;
	argent_recu = 0;
	bonus_penalite = 0;
		for (int i = 1; i < (9); i++) {

		    // on crée le beans avec le processus JPA qui va créer le datastore en même temps
		    
		    try {
			em = emf.createEntityManager();

			// instanciation du bean Utilisateur
			Equipe mBean = new Equipe();

			String jspSessionName = "Equipe" + i;
			String datastoreId = 1 + "_" + i;

			mBean.setPoolTeamId(datastoreId);
			mBean.setPoolId(1);
			mBean.setTeamId(i);
			mBean.setMax_salaire_begin(max_salaire_begin);
			mBean.setTotal_salaire_now(total_salaire_now);
			mBean.setBudget_restant(budget_restant);
			mBean.setMoy_sal_restant_draft(moy_sal_restant_draft);
			mBean.setNb_attaquant(nb_attaquant);
			mBean.setNb_defenseur(nb_defenseur);
			mBean.setNb_gardien(nb_gardien);
			mBean.setNb_rookie(nb_rookie);
			mBean.setNb_contrat(nb_contrat);
			mBean.setNb_equipe(nb_equipe);
			mBean.setManquant_att(manquant_att);
			mBean.setManquant_def(manquant_def);
			mBean.setManquant_gardien(manquant_gardien);
			mBean.setManquant_recrue(manquant_recrue);
			mBean.setManquant_equipe(manquant_equipe);
			mBean.setArgent_recu(argent_recu);
			mBean.setBonus_5m(bonus_5m);
			mBean.setBonus_penalite(bonus_penalite);
			mBean.setClassement_last_year(0);
			mBean.setMeilleur_classement(0);
			mBean.setNum_annee(1);
			mBean.setNum_champion(0);

			// on place le bean dans un attribut de session
			req.getSession().setAttribute(jspSessionName, mBean);
			// on persiste dans le datastore via notre EntityManager
			em.persist(mBean);
			
			

		    } finally {

			// on ferme le manager pour libérer la mémoire
			if (em != null) {
			    em.close();

			}
		    }

		}
		
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	for(int i=106;i<1704;i++){
	Key datastoreKeyForPlayersTable = KeyFactory.createKey("Players_6",i);
	datastore.delete(datastoreKeyForPlayersTable);
	}
	
	req.getSession().invalidate();

    }
   
}
