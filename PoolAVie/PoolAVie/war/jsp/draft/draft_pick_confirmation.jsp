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
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_draft.jsp" />

	<!-- PROCESS POUR DRAFT -->
	<c:set var="currentPick" value="${DraftBean.currentPick}" />
	<c:set var="currentPicker" value="${DraftBean.currentPicker}" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

		<c:if test="${messageErreur.erreurDraft==null&&confirmationPick.can_be_rookie==0}">

			<div id="pickConfirmBox" class="w3-card-24 w3-display-middle" style="width: auto">
				<div class="w3-container w3-indigo">
					<h2>Confirmation choix de repêchage</h2>
				</div>
				<form onsubmit="confirmationButton.disabled = true; return true;" class="w3-container w3-form w3-white" action="/DraftPlayers" method="post">

					<p class="w3-xxlarge">Voulez-vous confirmer le repêchage de ${confirmationPick.nom}, ${confirmationPick.position} de l'équipe de ${confirmationPick.teamOfPlayer} au salaire de
						${confirmationPick.salaire_draft}</p>


					<input type="hidden" name="draftStep" value="2" />
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}" />
					<input type="hidden" name="team_id" value="${Utilisateur.teamId}" />
					<input type="hidden" name="nom" value="${confirmationPick.nom}" />
					<input type="hidden" name="position" value="${confirmationPick.position}" />
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}" />
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}" />
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}" />
					<button class="w3-btn w3-khaki w3-xlarge">Oui je le veux</button>
					<br>
				</form>

				<form class="w3-container w3-card-24 w3-white" action="/DraftCenter" method="get">
					<button class="w3-btn w3-khaki w3-xlarge">Annuler</button>
					<br>
				</form>

			</div>

		</c:if>


		<c:if test="${messageErreur.erreurDraft==null&&confirmationPick.can_be_rookie==1}">
			<div id="pickConfirmBox" class="w3-card-24 w3-display-middle" style="width: auto">


				<div class="w3-container w3-indigo">
					<h2>Confirmation choix de repêchage</h2>
				</div>
				<p class="w3-xxlarge">Voulez-vous confirmer le repêchage de ${confirmationPick.nom}, ${confirmationPick.position} de l'équipe de ${confirmationPick.teamOfPlayer} au salaire de
					${confirmationPick.salaire_draft}</p>

				<form onsubmit="confirmationButtonA.disabled = true; return true;" class="w3-container w3-form w3-white" action="/DraftPlayers" method="post">
					<input type="hidden" name="draftStep" value="2">
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}">
					<input type="hidden" name="team_id" value="${Utilisateur.teamId}">
					<input type="hidden" name="nom" value="${confirmationPick.nom}">
					<input type="hidden" name="position" value="${confirmationPick.position}">
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}">
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}">
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}">
					<button name="confirmationButtonA" class="w3-btn w3-khaki w3-xlarge">Oui, dans mon club régulier</button>
				</form>
				<br>
				<form onsubmit="confirmationButtonB.disabled = true; return true;" class="w3-container w3-form w3-white" action="/DraftPlayers" method="post">
					<input type="hidden" name="draftStep" value="3">
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}">
					<input type="hidden" name="team_id" value="${Utilisateur.teamId}">
					<input type="hidden" name="nom" value="${confirmationPick.nom}">
					<input type="hidden" name="position" value="${confirmationPick.position}">
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}">
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}">
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}">
					<button name="confirmationButtonB" class="w3-btn w3-khaki w3-xlarge">Oui, dans mon club école</button>

				</form>
				<br>
				<form class="w3-container w3-form w3-white" action="/DraftCenter" method="get">

					<p>
						<input type="submit" value="Annuler">
					</p>

				</form>
				<br>


			</div>

		</c:if>
		<c:if test="${messageErreur.erreurDraft==null&&confirmationPick.can_be_rookie==2}">
			<div id="pickConfirmBox" class="w3-card-24 w3-display-middle" style="width: auto">


				<div class="w3-container w3-indigo">
					<h2>Confirmation choix de repêchage</h2>
				</div>
				<p class="w3-xxlarge">Voulez-vous confirmer le repêchage de ${confirmationPick.nom}, ${confirmationPick.position} de l'équipe de ${confirmationPick.teamOfPlayer} au salaire de
					${confirmationPick.salaire_draft}</p>
				<br>
				<form onsubmit="confirmationButtonB.disabled = true; return true;" class="w3-container w3-form w3-white" action="/DraftPlayers" method="post">
					<input type="hidden" name="draftStep" value="3">
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}">
					<input type="hidden" name="team_id" value="${Utilisateur.teamId}">
					<input type="hidden" name="nom" value="${confirmationPick.nom}">
					<input type="hidden" name="position" value="${confirmationPick.position}">
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}">
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}">
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}">
					<button name="confirmationButtonB" class="w3-btn w3-khaki w3-xlarge">Oui, dans mon club école</button>

				</form>
				<br>
				<form class="w3-container w3-form w3-white" action="/DraftCenter" method="get">

					<p>
						<input type="submit" value="Annuler">
					</p>

				</form>
				<br>


			</div>

		</c:if>
		<c:if test="${messageErreur.erreurDraft!=null}">

			<div id="pickConfirmBox" class="w3-card-24 w3-display-middle">


				<div class="w3-container w3-indigo">
					<h2>OUPS! Vous ne pouvez faire ce choix</h2>
				</div>

				<form class="w3-container w3-form w3-white" action="/DraftCenter" method="get">

					<p>Raison : ${messageErreur.erreurDraft}</p>
					<button class="w3-btn w3-khaki w3-xlarge">Revenir au draft center</button>
				</form>

			</div>

		</c:if>

		<jsp:directive.include file="../utils/draftMessage.jsp" />


		<!-- fin du container principal -->
	</div>

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecDraft').classList.add('w3-khaki');
	</script>
</body>
</html>