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
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf" %>
<title>${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript" src="https://talkgadget.google.com/talkgadget/channel.js"></script>

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

	<c:if test="${Pool.poolType==1&&(Pool.cycleAnnuel==1||Pool.cycleAnnuel==2||Pool.cycleAnnuel==3||Pool.cycleAnnuel==4||Pool.cycleAnnuel==5||Pool.cycleAnnuel==6||Pool.cycleAnnuel==7||Pool.cycleAnnuel==9||Pool.cycleAnnuel==10||Pool.cycleAnnuel==11||Pool.cycleAnnuel==12)}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Il n'y a pas de gestion des club école possible à ce stade du cycle annuel du pool</h3>
			</div>
		</div>
	</c:if>
	<c:if test="${Pool.poolType==2}">
		<br>
		<p>
			Il n'y a pas d'options de club écolet dans votre type de pool. Vous pouvez recréer un pool de type keeper (Pool à vie) pour essayer un pool plus complet. <br>
		<p>
	</c:if>
	<c:if test="${requestScope.messageErreurs!=null}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>${requestScope.messageErreurs}</h3>
			</div>
		</div>
	</c:if>


	<!-- Retrocession -->
	<c:if test="${requestScope.cycleAnnuel==8 && sessionScope.beanConfirmation==null}">
		<div class="w3-responsive" style="width: 90%">
			<table id="playersPickBox" class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large" style="width: 95%">
				<caption class="w3-blue w3-xlarge">
					<h1>Joueurs encore rookie pouvant être renvoyer dans club-école pour 1M</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Nom</th>
					<th>Équipe</th>
					<th>Position</th>
					<th>${Pool.thisYear}</th>
					<th>${Pool.secondYear}</th>
					<th>${Pool.thirdYear}</th>
					<th>${Pool.fourthYear}</th>
					<th>${Pool.fifthYear}</th>
					<th>Retourner au club école</th>

				</tr>

				<c:set var="nombreDePlayers" value="${NonSessionPlayers.nom}" />
				<c:choose>

					<c:when test="${not empty nombreDePlayers}">
						<c:forEach var="i" begin="0" end="${fn:length(nombreDePlayers)-1}">

							<tr id=i>

								<td>${NonSessionPlayers.nom[i]}</td>
								<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
								<td>${NonSessionPlayers.position[i]}</td>
								<td>${NonSessionPlayers.years_1[i]}</td>
								<td>${NonSessionPlayers.years_2[i]}</td>
								<td>${NonSessionPlayers.years_3[i]}</td>
								<td>${NonSessionPlayers.years_4[i]}</td>
								<td>${NonSessionPlayers.years_5[i]}</td>


								<td>
									<form onsubmit="confirmationButtonA.disabled = true; return true;" action="/Signature" method="post">
										<input type="hidden" value="${NonSessionPlayers.players_id[i]}" name="player_id" />
										 <input type="hidden" value="4" name="signatureStep" />
										 <input name="confirmationButtonA" type="submit" value="Descendre ce joueur">
									</form>
								</td>

							</tr>

						</c:forEach>
					</c:when>
					<c:otherwise>

						<tr>
							<td class="w3-overflow">Vous n'avez plus aucun joueurs ne pouvant être descendus
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
			<br> <br>

		</div>

	</c:if>

	<c:if test="${sessionScope.beanConfirmation!=null}">
		<div id="pickConfirmBox" class="w3-card-24 w3-display-middle w3-center" style="width: auto">
			<div class="w3-container w3-indigo">
				<h2>Confirmation choix de repêchage</h2>
			</div>
			<p class="w3-xlarge">Vous voulez descendre ${beanConfirmation.nom} au coût total de 1 Millions avec votre argent de l'année venant de ce terminer?</p>

			<form onsubmit="confirmationButtonB.disabled = true; return true;" action="/Signature" method="post">
				<input type="hidden" name="players_id" value="${Players._id}"> <input type="hidden" name="signatureStep" value="5">
				<button name="confirmationButtonB" class="w3-btn w3-khaki w3-xlarge">Je confirme la rétrocession de ce joueur</button>



			</form>
			<br> <a href="/Signature"><button class="w3-btn w3-khaki w3-xlarge">Annuler</button></a> <br>
		</div>
	</c:if>

	<!-- MONTER ET DROPPER ROOKIE -->

	<c:if test="${requestScope.cycleAnnuel==13}">

		<div class="w3-responsive" style="width: 90%">
			<table id="playersPickBox" class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large" style="width: 95%">
				<caption class="w3-blue w3-xlarge">
					<h1>Gestion club école</h1>
				</caption>
				<tr class="w3-indigo">
					<th>Nom</th>
					<th>Équipe</th>
					<th>Position</th>
					<th>Salaire</th>

					<th>${Pool.thisYear}</th>
					<th>${Pool.secondYear}</th>
					<th>${Pool.thirdYear}</th>
					<th>${Pool.fourthYear}</th>
					<th>${Pool.fifthYear}</th>
					<th>Signature</th>
					<th>Drop</th>

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
								<td><input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="1" required></td>

									<c:choose>
									<c:when test="${NonSessionPlayers.years_2[i]!='X'}">
										<td><input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="2" required></td>
									</c:when>
									<c:otherwise>
									<td>X</td>
									</c:otherwise>
									</c:choose>
									<c:choose>
									<c:when test="${NonSessionPlayers.years_3[i]!='X'}">
										<td><input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="3" required></td>
									</c:when>
									<c:otherwise>
									<td>X</td>
									</c:otherwise>
									</c:choose>
									<c:choose>
									<c:when test="${NonSessionPlayers.years_4[i]!='X'}">
										<td><input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="4" required></td>
									</c:when>
									<c:otherwise>
									<td>X</td>
									</c:otherwise>
									</c:choose>
									<c:choose>
									<c:when test="${NonSessionPlayers.years_5[i]!='X'}">
										<td><input class="w3-radio" form="signatureAbform${i}" type="radio" name="nombreAnneeSignature" value="5" required></td>
									</c:when>
									<c:otherwise>
									<td>X</td>
									</c:otherwise>
									</c:choose>

									<td>
										<form onsubmit="confirmationButtonC.disabled = true; return true;" id="signatureAbform${i}" action="/Signature" method="POST">
											<input type="hidden" name="signatureStep" value="6"> <input type="hidden" name="draft_player_id" value="${NonSessionPlayers.players_id[i]}"> <input type="hidden" name="nom"
												value="${NonSessionPlayers.nom[i]}"> <input type="hidden" name="position" value="${NonSessionPlayers.position[i]}"> <input type="hidden" name="team"
												value="${NonSessionPlayers.teamOfPlayer[i]}"> <input type="hidden" name="salaire" value="${NonSessionPlayers.salaire_draft[i]}">
												<input type="hidden" name="years_1" value="${NonSessionPlayers.years_1[i]}">
												<input type="hidden" name="years_2" value="${NonSessionPlayers.years_2[i]}">
												<input type="hidden" name="years_3" value="${NonSessionPlayers.years_3[i]}">
												<input type="hidden" name="years_4" value="${NonSessionPlayers.years_4[i]}">
												<input type="hidden" name="years_5" value="${NonSessionPlayers.years_5[i]}">
												 
											<button name="confirmationButtonC" class="w3-btn w3-round-xxlarge w3-blue">Signer ce joueur</button>
										</form>

									</td>
									<td>
										<form onsubmit="confirmationButtonD.disabled = true; return true;" id="signatureAbform${i}" action="/Signature" method="POST">
											<input type="hidden" name="signatureStep" value="7"> <input type="hidden" name="draft_player_id" value="${NonSessionPlayers.players_id[i]}"> <input type="hidden" name="nom"
												value="${NonSessionPlayers.nom[i]}"> <input type="hidden" name="position" value="${NonSessionPlayers.position[i]}"> <input type="hidden" name="team"
												value="${NonSessionPlayers.teamOfPlayer[i]}"> <input type="hidden" name="salaire" value="${NonSessionPlayers.salaire_draft[i]}">
											<button name="confirmationButtonD" class="w3-btn w3-round-xxlarge w3-blue">Dropper ce joueur</button>
										</form>

									</td>
							</tr>

						</c:forEach>
					</c:when>
					<c:otherwise>

						<tr>
							<td class="w3-overflow">Vous n'avez plus aucun joueurs dans votre club école
							<td>
							<td></td>
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