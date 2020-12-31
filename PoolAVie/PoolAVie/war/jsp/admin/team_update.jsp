<%@ page contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
	prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf"%>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<link rel="stylesheet" href="/css/team.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript"
	src="https://talkgadget.google.com/talkgadget/channel.js"></script>

</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker"
			value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_admin.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />


	<div class="w3-threequarter w3-margin-top">

		<table class="w3-table  w3-striped w3-bordered w3-card-8"
			style="width: 95%; margin-left: auto; margin-right: auto">
			<caption class="w3-blue w3-xlarge">
				<h1>Attaquant</h1>
			</caption>
			<tr class="w3-indigo">
				<th>Pos</th>
				<th>Nom</th>
				<th>Équipe</th>

				<th>${Pool.thisYear}</th>
				<th>${Pool.secondYear}</th>
				<th>${Pool.thirdYear}</th>
				<th>${Pool.fourthYear}</th>
				<th>${Pool.fifthYear}</th>

			</tr>
			<c:set var="nombreDePick"
				value="${NonSessionAttaquant.nom}" />
			<c:if test="${empty nombreDePick}">
				<tr>
					<td>Vous n'avez aucun joueur présentementt</td>
				<tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
				<c:forEach var="i" begin="0"
					end="${fn:length(nombreDePick)-1}">

					<tr>


						<td>${i+1}</td>
						<td>${NonSessionAttaquant.nom[i]}</td>
						<td>${NonSessionAttaquant.teamOfPlayer[i]}</td>
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
		<table
			class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top"
			style="width: 95%; margin-left: auto; margin-right: auto">
			<caption class="w3-blue w3-xlarge">
				<h1>Defenseur</h1>
			</caption>
			<tr class="w3-indigo">
				<th>Pos</th>
				<th>Nom</th>
				<th>Équipe</th>

				<th>${Pool.thisYear}</th>
				<th>${Pool.secondYear}</th>
				<th>${Pool.thirdYear}</th>
				<th>${Pool.fourthYear}</th>
				<th>${Pool.fifthYear}</th>

			</tr>
			<c:set var="nombreDePick"
				value="${NonSessionDefenseur.nom}" />
			<c:if test="${empty nombreDePick}">
				<tr>
					<td>Vous n'avez aucun joueur présentementt</td>
				<tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
				<c:forEach var="i" begin="0"
					end="${fn:length(nombreDePick)-1}">

					<tr>


						<td>${i+1}</td>
						<td>${NonSessionDefenseur.nom[i]}</td>
						<td>${NonSessionDefenseur.teamOfPlayer[i]}</td>

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
		<table
			class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top"
			style="width: 95%; margin-left: auto; margin-right: auto">
			<caption class="w3-blue w3-xlarge">
				<h1>Gardien</h1>
			</caption>
			<tr class="w3-indigo">
				<th>Pos</th>
				<th>Nom</th>
				<th>Équipe</th>

				<th>${Pool.thisYear}</th>
				<th>${Pool.secondYear}</th>
				<th>${Pool.thirdYear}</th>
				<th>${Pool.fourthYear}</th>
				<th>${Pool.fifthYear}</th>

			</tr>
			<c:set var="nombreDePick"
				value="${NonSessionGardien.nom}" />
			<c:if test="${empty nombreDePick}">
				<tr>
					<td>Vous n'avez aucun joueur présentementt</td>
				<tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
				<c:forEach var="i" begin="0"
					end="${fn:length(nombreDePick)-1}">

					<tr>


						<td>${i+1}</td>
						<td>${NonSessionGardien.nom[i]}</td>
						<td>${NonSessionGardien.teamOfPlayer[i]}</td>

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
		<table
			class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top"
			style="width: 95%; margin-left: auto; margin-right: auto">
			<caption class="w3-blue w3-xlarge">
				<h1>Recrue</h1>
			</caption>
			<tr class="w3-indigo">
				<th>Pos</th>
				<th>Nom</th>
				<th>Position</th>
				<th>Équipe</th>

				<th>${Pool.thisYear}</th>
				<th>${Pool.secondYear}</th>
				<th>${Pool.thirdYear}</th>
				<th>${Pool.fourthYear}</th>
				<th>${Pool.fifthYear}</th>

			</tr>
			<c:set var="nombreDePick" value="${NonSessionRecrue.nom}" />
			<c:if test="${empty nombreDePick}">
				<tr>
					<td>Vous n'avez aucun joueur présentementt</td>
				<tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
				<c:forEach var="i" begin="0"
					end="${fn:length(nombreDePick)-1}">

					<tr>


						<td>${i+1}</td>
						<td>${NonSessionRecrue.nom[i]}</td>
						<td>${NonSessionRecrue.position[i]}</td>
						<td>${NonSessionRecrue.teamOfPlayer[i]}</td>

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
			<div class="w3-card-8 w3-center"
				style="width: 95%; margin-left: auto; margin-right: auto">
				<div class="w3-blue w3-xxlarge w3-center">Pick</div>
				<div class="w3-third">
					<table
						class="w3-table  w3-striped w3-bordered w3-border-indigo">

						<tr class="w3-indigo">
							<th>Round</th>
							<th>From</th>

						</tr>

						<c:forEach var="i" begin="0" end="10">

							<tr>

								<td>${NonSessionDraftPick.pick_no[i]}</td>
								<td>${NonSessionDraftPick.teamNameOriginalPick[i]}</td>


							</tr>

						</c:forEach>





					</table>
				</div>
				<div class="w3-third">
					<table
						class="w3-table  w3-striped w3-bordered w3-border-indigo">

						<tr class="w3-indigo">
							<th>Round</th>
							<th>From</th>

						</tr>

						<c:forEach var="i" begin="11" end="21">

							<tr>

								<td>${NonSessionDraftPick.pick_no[i]}</td>
								<td>${NonSessionDraftPick.teamNameOriginalPick[i]}</td>


							</tr>

						</c:forEach>





					</table>
				</div>
				<div class="w3-third">
					<table
						class="w3-table  w3-striped w3-bordered w3-border-indigo">

						<tr class="w3-indigo">
							<th>Round</th>
							<th>From</th>

						</tr>
						<c:set var="nombreDePick"
							value="${NonSessionDraftPick.pick_no}" />

						<c:forEach var="i" begin="21"
							end="${fn:length(nombreDePick)-1}">

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
		<div id="boite_team_little_stats"
			class="w3-row w3-bottombar w3-leftbar w3-rightbar w3-topbar w3-border-indigo"
			style="width: 95%">

			<div class="w3-half w3-hide-medium">
				<br> <br> <br> <br> <img
					src="${NonSessionBean.logoDuTeam}"
					style="width: 184px; height: 184px;">
			</div>
			<div class="w3-half w3-padding-left">
				<div class="w3-xxlarge  w3-bottombar w3-border-indigo"
					style="width: 90%">${NonSessionBean.nomDuTeam}</div>
				<br>
				<div class="w3-xlarge ">Mon rang</div>
				<div class="w3-xxlarge ">${NonSessionBean.positionDuTeam}er</div>
				<div class="w3-xlarge ">Mes points totaux</div>
				<div class=" w3-xxlarge">${NonSessionBean.pointTotalDuTeam}</div>
				<div class="w3-xlarge ">Mes points hier</div>
				<div class=" w3-xxlarge">${NonSessionBean.pointHierDuTeam}</div>
			</div>
		</div>


		<form action="/AdminTeam" method="post" style="width: 95%">
			<table
				class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top w3-border-indigo"
				style="width: 95%">

				<caption class="w3-blue w3-xlarge">
					<h1>MON BUDGET</h1>
				</caption>
				<tr style="width: 95%">
					<td>Masse salarial maximum :</td>
					<td><input type="number" name="max_salaire_begin"
						value="${NonSessionEquipe.max_salaire_begin}" size="9"
						style="width: 75%;"></td>

				</tr>
				<tr style="width: 95%">
					<td>Total des salaires actuel :</td>
					<td><input type="number" name="total_salaire_now"
						value="${NonSessionEquipe.total_salaire_now}" size="9"
						style="width: 75%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Budget restant :</td>
					<td><input type="number" name="budget_restant"
						value="${NonSessionEquipe.budget_restant}" size="9"
						style="width: 75%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Argent Reçu :</td>
					<td><input type="number" name="argent_recu"
						value="${NonSessionEquipe.argent_recu}" size="9"
						style="width: 75%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Moyenne restante par joueurs :</td>
					<td>${NonSessionEquipe.moy_sal_restant_draft}</td>
				</tr>
				<tr style="width: 95%">
					<td><input type="hidden" name="team"
						value="${team}"> <input type="hidden"
						name="teamUpdate" value="1"> <input
						type="submit" value="Modifier Mon Budget"></td>
				</tr>




			</table>

		</form>

		<br>

		<form action="/AdminTeam" method="post" style="width: 95%">
			<table
				class="w3-table  w3-striped w3-bordered w3-card-8 w3-margin-top w3-border-indigo"
				style="width: 95%">
				<caption class="w3-blue w3-xlarge">
					<h1>MES STATS D'ÉQUIPE</h1>
				</caption>
				<tr style="width: 95%">
					<td>Nombre d'attaquant :</td>
					<td><input type="number" name="nb_attaquant"
						value="${NonSessionEquipe.nb_attaquant}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Nombre de défenseur :</td>
					<td><input type="number" name="nb_defenseur"
						value="${NonSessionEquipe.nb_defenseur}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Nombre de Gardien :</td>
					<td><input type="number" name="nb_gardien"
						value="${NonSessionEquipe.nb_gardien}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Nombre de Recrue :</td>
					<td><input type="number" name="nb_rookie"
						value="${NonSessionEquipe.nb_rookie}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Nombre de contrat :</td>
					<td><input type="number" name="nb_contrat"
						value="${NonSessionEquipe.nb_contrat}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%"> 
					<td>Nombre de joueur dans l'équipe :</td>
					<td><input type="number" name="nb_equipe"
						value="${NonSessionEquipe.nb_equipe}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Nombre de joueur manquant :</td>
					<td><input type="number" name="manquant_equipe"
						value="${NonSessionEquipe.manquant_equipe}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Attanquant manquant :</td>
					<td><input type="number" name="manquant_att"
						value="${NonSessionEquipe.manquant_att}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Defenseur manquant :</td>
					<td><input type="number" name="manquant_def"
						value="${NonSessionEquipe.manquant_def}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Gardien manquant :</td>
					<td><input type="number" name="manquant_gardien"
						value="${NonSessionEquipe.manquant_gardien}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td>Recrue manquante :</td>
					<td><input type="number" name="manquant_recrue"
						value="${NonSessionEquipe.manquant_recrue}" size="3"
						style="width: 45%;"></td>
				</tr>
				<tr style="width: 95%">
					<td><input type="hidden" name="team"
						value="${team}"> <input type="hidden"
						name="teamUpdate" value="2"> <input
						type="submit" value="Modifier les stats équipes"></td>
				</tr>
			</table>
		</form>

		<br>
	</div>

	<jsp:directive.include file="../utils/draftMessage.jsp" />



	<c:if
		test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecAdmin').classList.add('w3-khaki');
	</script>

</body>
</html>