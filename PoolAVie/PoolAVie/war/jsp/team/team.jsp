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
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_team.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />
	
	
		<div class="w3-threequarter w3-margin-top">

			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Attaquant</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>

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
							<td>${NonSessionAttaquant.pj[i]}</td>
							<td>${NonSessionAttaquant.but_victoire[i]}</td>
							<td>${NonSessionAttaquant.aide_overtime[i]}</td>
							<td>${NonSessionAttaquant.pts[i]}</td>
							<td>${NonSessionAttaquant.years_1[i]}</td>
							<td>${NonSessionAttaquant.years_2[i]}</td>
							<td>${NonSessionAttaquant.years_3[i]}</td>
							<td>${NonSessionAttaquant.years_4[i]}</td>
							<td>${NonSessionAttaquant.years_5[i]}</td>

						</tr>

					</c:forEach>
				</c:if>




			</table>




			<br>
			<table class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Defenseur</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>

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
							<td>${NonSessionDefenseur.pj[i]}</td>
							<td>${NonSessionDefenseur.but_victoire[i]}</td>
							<td>${NonSessionDefenseur.aide_overtime[i]}</td>
							<td>${NonSessionDefenseur.pts[i]}</td>
							<td>${NonSessionDefenseur.years_1[i]}</td>
							<td>${NonSessionDefenseur.years_2[i]}</td>
							<td>${NonSessionDefenseur.years_3[i]}</td>
							<td>${NonSessionDefenseur.years_4[i]}</td>
							<td>${NonSessionDefenseur.years_5[i]}</td>

						</tr>

					</c:forEach>
				</c:if>




			</table>




			<br>
			<table class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Gardien</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>

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
							<td>${NonSessionGardien.pj[i]}</td>
							<td>${NonSessionGardien.but_victoire[i]}</td>
							<td>${NonSessionGardien.aide_overtime[i]}</td>
							<td>${NonSessionGardien.pts[i]}</td>
							<td>${NonSessionGardien.years_1[i]}</td>
							<td>${NonSessionGardien.years_2[i]}</td>
							<td>${NonSessionGardien.years_3[i]}</td>
							<td>${NonSessionGardien.years_4[i]}</td>
							<td>${NonSessionGardien.years_5[i]}</td>

						</tr>

					</c:forEach>
				</c:if>




			</table>




			<br>
			<table class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Recrue</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>

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
							<td>${NonSessionRecrue.pj[i]}</td>
							<td>${NonSessionRecrue.but_victoire[i]}</td>
							<td>${NonSessionRecrue.aide_overtime[i]}</td>
							<td>${NonSessionRecrue.pts[i]}</td>
							<td>${NonSessionRecrue.years_1[i]}</td>
							<td>${NonSessionRecrue.years_2[i]}</td>
							<td>${NonSessionRecrue.years_3[i]}</td>
							<td>${NonSessionRecrue.years_4[i]}</td>
							<td>${NonSessionRecrue.years_5[i]}</td>

						</tr>

					</c:forEach>
				</c:if>




			</table>

			<br>
			
			<c:if test="${Pool.cycleAnnuel>=2}">
			<div class="w3-card-8 w3-center" style="width: 90%; margin-left: auto; margin-right: auto">
				<div class="w3-blue w3-xxlarge w3-center">Pick</div>
				<div class="w3-third">
					<table class="w3-table  w3-striped w3-bordered w3-border-indigo">

						<tr class="w3-indigo">
							<th>Round</th>
							<th>From</th>

						</tr>

						<c:forEach var="i" begin="0" end="9">

							<tr>

								<td>${NonSessionDraftPick.pick_no[i]}</td>
								<td>${NonSessionDraftPick.teamNameOriginalPick[i]}</td>


							</tr>

						</c:forEach>





					</table>
				</div>
				<div class="w3-third">
					<table class="w3-table  w3-striped w3-bordered w3-border-indigo">

						<tr class="w3-indigo">
							<th>Round</th>
							<th>From</th>

						</tr>

						<c:forEach var="i" begin="10" end="19">

							<tr>

								<td>${NonSessionDraftPick.pick_no[i]}</td>
								<td>${NonSessionDraftPick.teamNameOriginalPick[i]}</td>


							</tr>

						</c:forEach>





					</table>
				</div>
				<div class="w3-third">
					<table class="w3-table  w3-striped w3-bordered w3-border-indigo">

						<tr class="w3-indigo">
							<th>Round</th>
							<th>From</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPick.pick_no}" />

						<c:forEach var="i" begin="20" end="${fn:length(nombreDePick)-1}">

							<tr>

								<td>${NonSessionDraftPick.pick_no[i]}</td>
								<td>${NonSessionDraftPick.teamNameOriginalPick[i]}</td>


							</tr>

						</c:forEach>





					</table>
				</div>
			</div>
			</c:if>

		</div>

		<div class="w3-quarter w3-margin-top">


			<div class="w3-row w3-bottombar w3-leftbar w3-rightbar w3-topbar w3-border-indigo">

				<div class="w3-half">
					<br>
					<br>
					<br>
					<br>
					<img src="${NonSessionBean.logoDuTeam}" style="width: 200px; height: 200px;">
				</div>
				<div class="w3-half">
					<div class="w3-xxlarge  w3-bottombar w3-border-indigo" style="width: 90%">${NonSessionBean.nomDuTeam}</div>
					<br>
					<div class="w3-xlarge ">Mon rang</div>
					<div class="w3-xxxlarge ">3ième</div>
					<div class="w3-xlarge ">Mes points totaux</div>
					<div class=" w3-xxxlarge">324</div>
					<div class="w3-xlarge ">Mes points hier</div>
					<div class=" w3-xxxlarge">17</div>
				</div>






			</div>

			<br>
			<br>




			<table class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top w3-border-indigo">
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
			<table class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top w3-border-indigo">
				<caption class="w3-blue w3-xlarge">
					<h1>MES STATS D'ÉQUIPE</h1>
				</caption>
				<tr>
					<td>Nombre d'attanquant :</td>
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


		<br>
		</div>

		<jsp:directive.include file="../utils/draftMessage.jsp" />

	</div>

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
	document.getElementById('menuSecTeam').classList.add('w3-khaki');
	</script>

</body>
</html>