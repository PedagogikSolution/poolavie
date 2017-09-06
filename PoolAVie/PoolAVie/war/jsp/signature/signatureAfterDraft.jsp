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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
	<jsp:directive.include file="menu_signature.jsp" />

	<!-- Body de la page reglements -->

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<c:if test="${Pool.poolType==1&&(Pool.cycleAnnuel==1||Pool.cycleAnnuel==2||Pool.cycleAnnuel==3||Pool.cycleAnnuel==5||Pool.cycleAnnuel==6)}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Il n'y a pas de signature possible à ce stade du cycle annuel du pool</h3>
			</div>
		</div>
	</c:if>
	<c:if test="${Pool.poolType==2}">
		<br>
		<p>
			Il n'y a pas d'options de signature dans votre type de pool. Vous pouvez recréer un pool de type keeper (Pool à vie) pour essayer un pool plus complet.
			<br>
		<p>
	</c:if>
	<c:if test="${Pool.poolType==1&&Pool.cycleAnnuel==4&&requestScope.messageErreurs!=null}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>${requestScope.messageErreurs}</h3>
			</div>
		</div>
	</c:if>
	<c:if test="${Pool.poolType==1&&(Pool.cycleAnnuel==4||Pool.cycleAnnuel==10)&&requestScope.messageErreurs==null}">

		<div class="w3-responsive" style="width: 90%">
			<table id="playersPickBox" class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large" style="width: 95%">
				<caption class="w3-blue w3-xlarge">
					<h1>Joueurs pouvant être signé pour un contrat</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Nom</th>
					<th>Équipe</th>
					<th>Position</th>
					<th>Salaire</th>
					<th>${Pool.secondYear}</th>
					<th>${Pool.thirdYear}</th>
					<th>${Pool.fourthYear}</th>
					<th>${Pool.fifthYear}</th>
					<th>Signature</th>

				</tr>

				<c:set var="nombreDePlayers" value="${NonSessionPlayers.nom}" />
				<c:choose>

					<c:when test="${not empty nombreDePlayers}">
						<c:forEach var="i" begin="0" end="${fn:length(nombreDePlayers)-1}">

							<tr id=i>

								<td>${NonSessionPlayers.nom[i]}</td>
								<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
								<td>${NonSessionPlayers.position[i]}</td>
								<td>${NonSessionPlayers.salaire_draft[i]}</td>
								
								<td>
									<input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="2">
								</td>
								<td>
									<input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="3">
								</td>
								<td>
									<input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="4">
								</td>
								<td>
									<input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="5">
								</td>

								<td>
									<form id="signatureAbform${i}" action="/Signature" method="POST">
										<input type="hidden" name="signatureStep" value="1">
										<input type="hidden" name="draft_player_id" value="${NonSessionPlayers.players_id[i]}">
										<input type="hidden" name="nom" value="${NonSessionPlayers.nom[i]}">
										<input type="hidden" name="position" value="${NonSessionPlayers.position[i]}">
										<input type="hidden" name="team" value="${NonSessionPlayers.teamOfPlayer[i]}">
										<input type="hidden" name="salaire" value="${NonSessionPlayers.salaire_draft[i]}">
										<button class="w3-btn w3-round-xxlarge w3-blue">Signer ce joueur</button>
									</form>

								</td>

							</tr>

						</c:forEach>
					</c:when>
					<c:otherwise>

						<tr>
							<td class="w3-overflow">Vous n'avez plus aucun joueurs ne pouvant être signé
							<td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>

					</c:otherwise>

				</c:choose>


			</table>

		</div>


	</c:if>


	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecSign').classList.add('w3-khaki');
	</script>
</body>
</html>