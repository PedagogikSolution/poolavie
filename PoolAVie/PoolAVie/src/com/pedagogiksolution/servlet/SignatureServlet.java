package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;
import com.pedagogiksolution.model.SignatureModel;

public class SignatureServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;

	public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayersDao playersDao;
	private SalaireDao salaireDao;

	@Override
	public void init() throws ServletException {
		/* Recuperation d'une instance de notre nos DAO */
		this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
		this.salaireDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSalaireDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		LoginModel mModel2 = new LoginModel(req);
		mModel2.createSessionEquipeBean();
		mModel2.createSessionAttaquantBean();
		mModel2.createSessionDefenseurBean();
		mModel2.createSessionGardienBean();
		mModel2.createSessionRecrueBean();
		mModel2.createSessionDraftPickBean();
		mModel2.createSessionDraftRoundBean();
		mModel2.createSessionPoolBean();
		int fromId;
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBeanPool, req);
		}

		String from = req.getParameter("from");
		if (from != null) {
			fromId = Integer.parseInt(from);
		} else {
			fromId = 1;
		}
		if (cycleAnnuel == 4) {
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			Boolean checkIfSignatureIsPossible = mModelSignature.checkIfSignatureIsPossible(req);

			if (checkIfSignatureIsPossible) {

				mModelSignature.putPlayersThatCanBeSignInBean(req, salaireDao);

				fromId = 2;
			} else {
				fromId = 992;
			}

		}

		if (cycleAnnuel == 7) {
			fromId = 3;
			req.getSession().removeAttribute("beanConfirmationRachat");
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			mModelSignature.preparationRachatApresSaison(req);

		}

		if (cycleAnnuel == 8) {
			fromId = 4;
			req.getSession().removeAttribute("beanConfirmation");
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			mModelSignature.preparationRookieBackToClubEcole(req);

			req.setAttribute("cycleAnnuel", 8);

		}

		if (cycleAnnuel == 9) {
			fromId = 3;
			req.getSession().removeAttribute("beanConfirmationRachat");
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			mModelSignature.preparationRachatApresSaison(req);

		}

		if (cycleAnnuel == 10) {
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			Boolean checkIfSignatureIsPossible = mModelSignature.checkIfSignatureIsPossible(req);

			if (checkIfSignatureIsPossible) {

				mModelSignature.putPlayersThatCanBeSignInBean(req, salaireDao);

				fromId = 2;
			} else {
				fromId = 992;
			}

		}

		if (cycleAnnuel == 12) {
			fromId = 3;
			req.getSession().removeAttribute("beanConfirmationRachat");
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			mModelSignature.preparationRachatApresSaison(req);

		}

		if (cycleAnnuel == 13) {
			fromId = 4;
			req.getSession().removeAttribute("beanConfirmationRachat");
			SignatureModel mModelSignature = new SignatureModel(playersDao);

			mModelSignature.preparationClubEcole(req);
			req.setAttribute("cycleAnnuel", 13);

		}
//
		// on check si waiver ou rookie in season trade phase
	/*	if (cycleAnnuel == 5 || cycleAnnuel == 6) {
			AdminModel mAdminModel = new AdminModel();
		//	boolean checkIfWaiver = mAdminModel.checkIfWaivertDay(mBeanPool, req);
			if (checkIfWaiver) {
				req.setAttribute("openWaiver", 1);
				String segment = "all";
				String sort = "pts";

				DraftPlayersModel mModel = new DraftPlayersModel(req, segment, sort);

				mModel.showPlayersSortByParameter();
			}
			// boolean checkIfRookie = mAdminModel.checkIfRookieDay(mBeanPool, req);
			if (checkIfRookie) {

				req.getSession().removeAttribute("NonSessionPlayers");
				req.setAttribute("openRookie", 1);

				SignatureModel mModelSignature = new SignatureModel(playersDao);
				Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
				String mBeanEquipeSessionName = "Equipe" + mBeanUser.getTeamId();
				Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(mBeanEquipeSessionName);

				boolean checkIfAlreadyUpARookie = mModelSignature.checkIfAlreadyUpARookie(mBeanEquipe, req);

				if (checkIfAlreadyUpARookie) {

					req.setAttribute("alreadyUpARookie", 1);

				} else {

					boolean canSignRookie = mModelSignature.preparationClubEcoleForInYearUp(req);
					int canSign;
					if (canSignRookie) {
						canSign = 1;
					} else {
						canSign = 0;
					}

					req.setAttribute("canSign", canSign);

				}

			}

			fromId = 5;

		}
		*/

		switch (fromId) {
		case 1: // cycle 0 a 3 ou 5 et 6
			req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
			break;
		case 2: // siganture apres darft
			req.getRequestDispatcher("jsp/signature/signatureAfterDraft.jsp").forward(req, resp);
			break;
		case 3: // rachat de contrat fin de saison
			req.getRequestDispatcher("jsp/signature/rachatContrat.jsp").forward(req, resp);
			break;
		case 4:

			req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);
			break;
		case 5:

			req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
			break;
		case 992: // message deja 12 contrat
			req.setAttribute("messageErreurs", "Vous avez le nombre maximal de joueur sous contrat dans votre équipe");
			req.getRequestDispatcher("jsp/signature/signatureAfterDraft.jsp").forward(req, resp);
			break;

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SignatureModel mModel = new SignatureModel(playersDao);
		String signatureStep = req.getParameter("signatureStep");
		int signatureStepId = Integer.parseInt(signatureStep);

		switch (signatureStepId) {
		case 1:
			// on persiste dans bdd et datastore et session les changements

			mModel.signatureAfterDraft(req);
			resp.sendRedirect("/Signature?from=2");

			break;
		case 2:
			// on check si possible et si oui envoie message pour confirmation

			Boolean checkIfCashIsGood = mModel.checkIfCashAvailablePourRachat(req);
			if (checkIfCashIsGood) {
				req.getRequestDispatcher("jsp/signature/rachatContrat.jsp").forward(req, resp);
			} else {
				req.setAttribute("messageErreurs", "Vous n'avez pas le budget nécessaire pour racheter ce joueur");
				req.getRequestDispatcher("jsp/signature/rachatContrat.jsp").forward(req, resp);
			}

			break;
		case 3: // on effectue le rachat retirant le joueur des bdd et l'argent du datastore
				// equipe. On relance les datastores Attaquant, »def, Goal de la bdd

			mModel.rachatApresSaison(req);

			req.getSession().removeAttribute("beanConfirmationRachat");
			req.setAttribute("messageErreurs", "Votre rachat a été effectué avec succès");
			resp.sendRedirect("/Signature?from=3");

			break;
		case 4: // on check si possible et si oui envoie message de confirmation pour
				// retrocession dans club école
			Boolean checkIfCashIsGoodForRookieDrop = mModel.checkIfCashIsGoodForRookieDrop(req);
			if (checkIfCashIsGoodForRookieDrop) {
				req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);
			} else {
				req.getSession().removeAttribute("beanConfirmation");
				req.setAttribute("messageErreurs", "Vous n'avez pas le budget nécessaire pour descendre ce joueur");
				req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);
			}

			break;

		case 5: // on effectue la retrocession de la rookie, on retire le million.

			mModel.retrocessionRookieDansClubEcole(req);
			req.getSession().removeAttribute("beanConfirmation");
			req.setAttribute("messageErreurs", "Votre joueurs est maintenant dans votre club école");
			resp.sendRedirect("/Signature?from=4");

			break;

		case 6: // on effectue la signature et ajuste les table bdd et datastore en conséquence

			Boolean checkIfSignatureIsPoosible = mModel.checkIfSignatureIsPossible(req);

			if (checkIfSignatureIsPoosible) {

				Boolean checkIfCashIsGoodToGo = mModel.checkIfCashIsGoodToGo(req);

				if (checkIfCashIsGoodToGo) {
					mModel.signatureRookie(req);
					resp.sendRedirect("/Signature?from=4");
				} else {
					req.setAttribute("messageErreurs",
							"Vous n'avez pas le budget nécessaire pour monter ce joueur et drafter une équipe complète");
					req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);
				}

			} else {

				req.setAttribute("messageErreurs", "Vous avez déjà 11 joueurs sous contrat");
				req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);

			}

			break;

		case 7: // on drop la recrue en retirant datastore et bdd

			mModel.dropRookie(req);
			resp.sendRedirect("/Signature?from=4");

			break;

		case 8: // on persiste les choix de priorité des waivers

			String[] players_id = req.getParameterValues("players_id");

			break;

		case 9: // on monte la rookie

			

				boolean checkIfCashIsGoodToGo = mModel.checkIfCashIsGoodToUp(req);

				if (checkIfCashIsGoodToGo) {
					mModel.signatureRookieInSeasonSign(req);

					mModel.updateDatastoreEquipeAlreadySignARookie(req);

					resp.sendRedirect("/Signature?from=5");
				} else {
					req.setAttribute("messageErreurs",
							"Vous n'avez pas le budget nécessaire pour monter ce joueur");
					req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);
			
				}
			break;
			
		case 10: // on monte la rookie

			
			boolean checkIfCashIsGoodToGo2 = mModel.checkIfCashIsGoodToUp(req);

				if (checkIfCashIsGoodToGo2) {
					mModel.signatureRookieInSeasonJA(req);

					mModel.updateDatastoreEquipeAlreadySignARookie(req);

					resp.sendRedirect("/Signature?from=5");
				} else {
					req.setAttribute("messageErreurs",
							"Vous n'avez pas le budget nécessaire pour monter ce joueur");
					req.getRequestDispatcher("jsp/signature/rookieManager.jsp").forward(req, resp);
				}

			

			break;

		}
		

	}

}
