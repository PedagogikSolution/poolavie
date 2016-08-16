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
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
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

	<!-- Body de la page draft_center -->
	<c:set var="segment" value="${SegmentSort}" />
	<div class="w3-container">
		<!-- Si all team register et pool est commencer -->
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token==null}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>C'est l'heure du Draft</h3>
				<p>Votre draft est prêt à commencer. Cliquez ici pour vous connecter au serveur de draft</p>
				<p>
					<a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu
				</p>

			</div>

		</c:if>
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&messageErreur.erreurConnectionDraft!=null}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>OUPS!</h3>
				<p>${messageErreur.erreurConnectionDraft}</p>
				<p>
					<a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu
				</p>

			</div>

		</c:if>

		<c:if test="${messageErreur.erreurDraft==null&&confirmationPick.can_be_rookie==0}">
			<div id="pickConfirmBox" class="w3-container w3-display-middle">


				<div class="w3-container w3-orange">
					<h2>Confirmation choix de repêchage</h2>
				</div>
				<div>Voulez-vous confirmer le repêchage de ${confirmationPick.nom}, ${confirmationPick.position} de l'équipe de ${confirmationPick.teamOfPlayer} au salaire de
					${confirmationPick.salaire_draft}</div>

				<form onsubmit="confirmationButton.disabled = true; return true;" class="w3-container w3-card-24 w3-white" action="/DraftPlayers" method="post">
					<input type="hidden" name="draftStep" value="2">
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}">
					<input type="hidden" name="team_id"	value="${Utilisateur.teamId}">
					<input type="hidden" name="nom" value="${confirmationPick.nom}">
					<input type="hidden" name="position" value="${confirmationPick.position}">
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}">
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}">
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}">
					<p>
						<input name="confirmationButton" type="submit" value="Oui je le veux">
					</p>

				</form>
				<form class="w3-container w3-card-24 w3-white" action="/DraftCenter" method="get">

					<p>
						<input type="submit" value="Annuler">
					</p>

				</form>



			</div>

		</c:if>
		
		
		<c:if test="${messageErreur.erreurDraft==null&&confirmationPick.can_be_rookie==1}">
			<div id="pickConfirmBox" class="w3-container w3-display-middle">


				<div class="w3-container w3-orange">
					<h2>Confirmation choix de repêchage</h2>
				</div>
				<div>Voulez-vous confirmer le repêchage de ${confirmationPick.nom}, ${confirmationPick.position} de l'équipe de ${confirmationPick.teamOfPlayer} au salaire de
					${confirmationPick.salaire_draft}</div>

				<form onsubmit="confirmationButtonA.disabled = true; return true;" class="w3-container w3-card-24 w3-white" action="/DraftPlayers" method="post">
					<input type="hidden" name="draftStep" value="2">
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}">
					<input type="hidden" name="team_id"	value="${Utilisateur.teamId}">
					<input type="hidden" name="nom" value="${confirmationPick.nom}">
					<input type="hidden" name="position" value="${confirmationPick.position}">
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}">
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}">
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}">
					<p>
						<input name="confirmationButtonA" type="submit" value="Oui, dans mon club régulier">
					</p>

				</form>
				<form onsubmit="confirmationButtonB.disabled = true; return true;" action="/DraftPlayers" method="post">
					<input type="hidden" name="draftStep" value="3">
					<input type="hidden" name="draft_player_id" value="${confirmationPick.players_id}">
					<input type="hidden" name="team_id"	value="${Utilisateur.teamId}">
					<input type="hidden" name="nom" value="${confirmationPick.nom}">
					<input type="hidden" name="position" value="${confirmationPick.position}">
					<input type="hidden" name="team" value="${confirmationPick.teamOfPlayer}">
					<input type="hidden" name="can_be_rookie" value="${confirmationPick.can_be_rookie}">
					<input type="hidden" name="salaire" value="${confirmationPick.salaire_draft}">
					<p>
						<input name="confirmationButtonB" type="submit" value="Oui, dans mon club école">
					</p>

				</form>
				<form class="w3-container w3-card-24 w3-white" action="/DraftCenter" method="get">

					<p>
						<input type="submit" value="Annuler">
					</p>

				</form>



			</div>

		</c:if>
		<c:if test="${messageErreur.erreurDraft!=null}">

			<div id="pickConfirmBox" class="w3-container w3-display-middle">


				<div class="w3-container w3-orange">
					<h2>OUPS! Vous ne pouvez faire ce choix</h2>
				</div>

				<form class="w3-container w3-card-24 w3-white" action="/DraftCenter" method="get">

					<p>Raison : ${messageErreur.erreurDraft}</p>
					<p>
						<input type="submit" value="Revenir au draft center">
					</p>

				</form>



			</div>

		</c:if>








<jsp:directive.include file="../utils/draftMessage.jsp" />


		<!-- fin du container principal -->
	</div>

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
</body>
</html>