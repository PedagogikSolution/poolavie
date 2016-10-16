<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>
	<div id="mainContainer">
		<!-- Header du menu principal-->
		<jsp:directive.include file="../main/navbar_main.jsp" />
		<jsp:directive.include file="../main/menu_secondaire.jsp" />
		<jsp:directive.include file="menu_draft.jsp" />

		<!-- PROCESS POUR DRAFT -->
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
		
		<!-- section Alerte -->
		<jsp:directive.include file="../utils/messageAlerte.jsp" />
	
		<div class="w3-container">
			<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==0&&Pool.cycleAnnuel==0}">
				<!-- Si first years and avant draft sans date set-->
				<div class="w3-container w3-section w3-red">
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>Inscription des équipes</h3>
					<p>Présentement, le nombre d'équipe inscrite n'est pas complète. Une fois tous les DG inscrit, votre administrateur pourra choisir une date de draft et ainsi l'ordre de draft sera déterminé
						au Hasard et vous permettra aussi de voir les joueurs disponibles pour le prochain draft aevc leur salaire, projection, points de l'année dernière, etc.</p>
				</div>

			</c:if>

			<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==0&&Pool.cycleAnnuel==1}">
				<!-- Si first years and avant draft sans date set-->
				<div class="w3-container w3-section w3-red">
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>Date du draft</h3>
					<p>Présentement, votre manager n'a pas encore déterminé de période de draft, une fois celle-ci choisis, vous trouverez ici les informations concernant votre prochain draft, tel que l'ordre de
						draft, le nombre de temps avant la période de draft, etc.</p>
				</div>

			</c:if>


			<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.cycleAnnuel==2}">
				<!-- Si first years and avant draft avec date set-->



				<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
					<caption class="w3-blue w3-xlarge">
						<h1>First 2 round Draft Order first year of the pool</h1>
					</caption>
					<tr class="w3-indigo">
						<th>Overall pick no</th>
						<th>Ronde</th>
						<th>Equipe</th>

					</tr>
					<c:set var="nombreDeTeam" value="${Pool.numberTeam}" />
					<c:forEach var="i" begin="0" end="${(nombreDeTeam*2)-1}">

						<tr>
							<td>${DraftRound.draft_pick_no[i]}</td>
							<td>${DraftRound.ronde[i]}</td>
							<td>${DraftRound.equipe[i]}</td>

						</tr>

					</c:forEach>
				</table>

			</c:if>


			<!-- **************************DRAFT TIME********************************************************************* -->

			<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.cycleAnnuel>2}">

				<!-- ***************SECTION TEAM -->
				<div class="w3-half">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Attaquant</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Pos</th>
							<th>Nom</th>
							<th>Équipe</th>
							<th>Points</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionAttaquant.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${i+1}</td>
									<td>${NonSessionAttaquant.nom[i]}</td>
									<td>${NonSessionAttaquant.teamOfPlayer[i]}</td>
									<td>${NonSessionAttaquant.pts[i]}</td>

								</tr>

							</c:forEach>
						</c:if>

				</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Defenseur</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Pos</th>
							<th>Nom</th>
							<th>Équipe</th>
							<th>Points</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDefenseur.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>

									<td>${i+1}</td>
									<td>${NonSessionDefenseur.nom[i]}</td>
									<td>${NonSessionDefenseur.teamOfPlayer[i]}</td>
									<td>${NonSessionDefenseur.pts[i]}</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Gardien</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Pos</th>
							<th>Nom</th>
							<th>Équipe</th>
							<th>Points</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionGardien.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${i+1}</td>
									<td>${NonSessionGardien.nom[i]}</td>
									<td>${NonSessionGardien.teamOfPlayer[i]}</td>
									<td>${NonSessionGardien.pts[i]}</td>
								</tr>

							</c:forEach>
						</c:if>

					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Recrue</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Pos</th>
							<th>Nom</th>
							<th>Équipe</th>
							<th>Points</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionRecrue.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>

									<td>${i+1}</td>
									<td>${NonSessionRecrue.nom[i]}</td>
									<td>${NonSessionRecrue.teamOfPlayer[i]}</td>
									<td>${NonSessionRecrue.pts[i]}</td>
								</tr>

							</c:forEach>
						</c:if>

					</table>

					<br>

				</div>
				<!-- ***************SECTION BUDGET -->
				<div class="w3-quarter">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>MON BUDGET</h1>
						</caption>
						<tr>
							<td>Masse salarial maximum :</td>
							<td>${NonSessionEquipe.max_salaire_begin}</td>

						</tr>
						<tr>
							<td>Total des salaires actuel :</td>
							<td>${NonSessionEquipe.total_salaire_now}</td>
						</tr>
						<tr>
							<td>Budget restant :</td>
							<td>${NonSessionEquipe.budget_restant}</td>
						</tr>
						<tr>
							<td>Argent Reçu :</td>
							<td>${NonSessionEquipe.argent_recu}</td>
						</tr>
						<tr>
							<td>Moyenne restante par joueurs :</td>
							<td>${NonSessionEquipe.moy_sal_restant_draft}</td>
						</tr>

					</table>
					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>MES STATS D'ÉQUIPE</h1>
						</caption>
						<tr>
							<td>Nombre d'attaquant :</td>
							<td>${NonSessionEquipe.nb_attaquant}</td>
						</tr>
						<tr>
							<td>Nombre de défenseur :</td>
							<td>${NonSessionEquipe.nb_defenseur}</td>
						</tr>
						<tr>
							<td>Nombre de Gardien :</td>
							<td>${NonSessionEquipe.nb_gardien}</td>
						</tr>
						<tr>
							<td>Nombre de Recrue :</td>
							<td>${NonSessionEquipe.nb_rookie}</td>
						</tr>
						<tr>
							<td>Nombre de contrat :</td>
							<td>${NonSessionEquipe.nb_contrat}</td>
						</tr>
						<tr>
							<td>Nombre de joueur dans l'équipe :</td>
							<td>${NonSessionEquipe.nb_equipe}</td>
						</tr>
						<tr>
							<td>Nombre de joueur manquant :</td>
							<td>${NonSessionEquipe.manquant_equipe}</td>
						</tr>
						<tr>
							<td>Attanquant manquant :</td>
							<td>${NonSessionEquipe.manquant_att}</td>
						</tr>
						<tr>
							<td>Defenseur manquant :</td>
							<td>${NonSessionEquipe.manquant_def}</td>
						</tr>
						<tr>
							<td>Gardien manquant :</td>
							<td>${NonSessionEquipe.manquant_gardien}</td>
						</tr>
						<tr>
							<td>Recrue manquante :</td>
							<td>${NonSessionEquipe.manquant_recrue}</td>
						</tr>

					</table>
				</div>

				<!-- ***************SECTION PICK -->
				<div class="w3-quarter">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Next 20 pick</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Overall pick no</th>
							<th>Ronde</th>
							<th>Equipe</th>
							<th>Joueur repêché</th>
							<th>Team pick no</th>

						</tr>

						<tr class="w3-red">
							<td>${DraftRound.draft_pick_no[currentPick-1]}</td>
							<td>${DraftRound.ronde[currentPick-1]}</td>
							<td>${DraftRound.equipe[currentPick-1]}</td>
							<td>${DraftRound.player_drafted[currentPick-1]}</td>
							<td>${DraftRound.team_count[currentPick-1]}</td>

						</tr>


						<c:forEach var="i" begin="${DraftBean.currentPick}" end="${(DraftBean.currentPick)+18}">

							<tr>
								<td>${DraftRound.draft_pick_no[i]}</td>
								<td>${DraftRound.ronde[i]}</td>
								<td>${DraftRound.equipe[i]}</td>
								<td>${DraftRound.player_drafted[i]}</td>
								<td>${DraftRound.team_count[i]}</td>

							</tr>

						</c:forEach>
					</table>



				</div>



			</c:if>


			<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==1&&Pool.cycleAnnuel==1}">
				<!-- Si multi years and avant draft -->
		Nous attendons la date de draft, rappel de l'ordre selon la table draft round.
		
	</c:if>

			<!-- fin du container sans menu et alerte -->
		</div>

		<!-- fin du container principal -->
	</div>
	<jsp:directive.include file="../utils/draftMessage.jsp" />



	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
	document.getElementById('menuSecDraft').classList.add('w3-khaki');
	document.getElementById('menuSecDraftCenter').classList.add('w3-text-shadow');
	</script>
</body>
</html>