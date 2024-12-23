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
<title>Feuille d'échange</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript" src="https://talkgadget.google.com/talkgadget/channel.js"></script>

</head>
<body onload="removeProgressBar()">
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />


	<div id="trade_offer_sheet_main" class="w3-container w3-row">
		<br>
		<br>

		<div class="w3-half w3-container">

			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>J'offre <br>(${requestScope.team1})</h1>
				</caption>

				<!-- regulier offer -->
				<tr class="w3-indigo">

					<th class="w3-large">Joueur du Club ferme</th>
					<th class="w3-large">Salaire</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.nomMakingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td class="w3-large">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td class="w3-large">${tradeOfferBean.nomMakingOffer[i]}</td>
							<td class="w3-large">${tradeOfferBean.salaireMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th class="w3-large">Joueur du Club école</th>
					<th class="w3-large">Salaire</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.rookieNomMakingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td class="w3-large">${tradeOfferBean.rookieNomMakingOffer[i]}</td>
							<td class="w3-large">${tradeOfferBean.salaireRookieMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>


				<!-- argent offer -->
				<tr class="w3-indigo">

					<th class="w3-large" colspan="2">Argent</th>


				</tr>

				<c:if test="${empty tradeOfferBean.cashMakingOffer}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.cashMakingOffer}">

					<tr>
						<td class="w3-large" colspan="2">${tradeOfferBean.cashMakingOffer}$</td>
					</tr>

				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th class="w3-large">Round</th>
					<th class="w3-large">Provenance</th>


				</tr>
				<c:set var="nombreDePick" value="${tradeOfferBean.roundPickMakingOffer}" />
				<c:if test="${empty nombreDePick}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDePick}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

						<tr>
							<td class="w3-large">${tradeOfferBean.roundPickMakingOffer[i]}</td>
							<td class="w3-large">${tradeOfferBean.fromPickMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				<!-- budget restant offer -->
				<tr class="w3-indigo">

					<th class="w3-large" colspan="2">Budget Restant</th>


				</tr>
				
				<c:if test="${empty tradeOfferBean.budgetMakingOffer}">
					<tr>
						<td class="w3-large" colspan="2">0 $</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.budgetMakingOffer}">

					<tr>
						<td class="w3-large" colspan="2">${tradeOfferBean.budgetMakingOffer}$</td>
					</tr>

				</c:if>


			</table>


			<br>
			<br>

		</div>



		<div class="w3-half w3-container">
			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Je reçois <br> (${requestScope.team2})</h1>
				</caption>

				<!-- regulier offer -->
				<tr class="w3-indigo">

					<th class="w3-large">Joueur du Club ferme</th>
					<th class="w3-large">Salaire</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.nomReceivingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td class="w3-large">${tradeOfferBean.nomReceivingOffer[i]}</td>
							<td class="w3-large">${tradeOfferBean.salaireReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th class="w3-large">Joueur du Club école</th>
					<th class="w3-large">Salaire</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.rookieNomReceivingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td class="w3-large">${tradeOfferBean.rookieNomReceivingOffer[i]}</td>
							<td class="w3-large">${tradeOfferBean.salaireRookieReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>


				<!-- argent offer -->
				<tr class="w3-indigo">

					<th class="w3-large" colspan="2">Argent</th>


				</tr>

				<c:if test="${empty tradeOfferBean.cashReceivingOffer}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.cashReceivingOffer}">

					<tr>
						<td class="w3-large" colspan="2">${tradeOfferBean.cashReceivingOffer}$</td>
					</tr>

				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th class="w3-large">Round</th>
					<th class="w3-large">Provenance</th>


				</tr>
				<c:set var="nombreDePick" value="${tradeOfferBean.roundPickReceivingOffer}" />
				<c:if test="${empty nombreDePick}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDePick}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

						<tr>
							<td class="w3-large">${tradeOfferBean.roundPickReceivingOffer[i]}</td>
							<td class="w3-large">${tradeOfferBean.fromPickReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>
				
				<!-- budget restant offer -->
				<tr class="w3-indigo">

					<th class="w3-large" colspan="2">Budget Restant</th>


				</tr>

				<c:if test="${empty tradeOfferBean.budgetReceivingOffer}">
					<tr>
						<td class="w3-large" colspan="2">0 $</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.budgetReceivingOffer}">

					<tr>
						<td class="w3-large" colspan="2">${tradeOfferBean.budgetReceivingOffer}$</td>
					</tr>

				</c:if>

			</table>
			<br>
			<br>
		</div>

	</div>
	<hr id="hr1">
	<br>
	<div id="trade_offer_sheet_messageDg" class="w3-container w3-row w3-center">
		<h2>Message à inclure dans l'offre d'échange</h2>
		<br>
		<br>
		<div class="w3-padding-xlarge w3-topbar w3-leftbar w3-bottombar w3-rightbar w3-border-red w3-pale-red w3-round-xlarge w3-large">${tradeOfferBean.messageOffre}</div>
	</div>
	<br>
	<hr id="hr2">
	<div id="trade_offer_sheet_confirmation" class="w3-container w3-row w3-center">
		<br>
		<br>



		<form onsubmit="confirmationButtonA.disabled = true; return true;" action="/Trade" method="post">
			<input type="hidden" value="oui" name="confirmation" />
			<input type="hidden" name="tradeTag" value="3">
			<button name="confirmationButtonA" onclick="progressbar(3)" class="w3-btn w3-blue w3-xxlarge w3-round">Confirmez cette offre</button>
		</form>
		<br>
		<br>
		<form action="/Trade" method="post">
			<input type="hidden" value="non" name="confirmation" />
			<input type="hidden" name="tradeTag" value="3">
			<button class="w3-btn w3-blue w3-xxlarge w3-round">Fuck je me suis trompé</button>
		</form>
		<br>
		<br>
	</div>

	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecTrade').classList.add('w3-khaki');
	</script>

	<div id="progressBar" class="w3-display-middle w3-half w3-center w3-hide">
		<h1 id="progressMessage1" class="w3-show">Vérification des joueurs impliqués</h1>
		<h1 id="progressMessage2" class="w3-hide">Vérification de l'argent impliqué</h1>
		<h1 id="progressMessage3" class="w3-hide">Vérification des choix aux repêchages</h1>
		<h1 id="progressMessage4" class="w3-hide">Validation de la transaction versus les règles du pool</h1>
		<h1 id="progressMessage5" class="w3-hide">Envoie des courriels et persistence de l'offre pour visualisation</h1>
		<br>
		<div class="w3-progress-container ">
			<div id="myBar" class="w3-progressbar w3-blue" style="width: 0%">
				<div id="demo" class="w3-container w3-text-white">0</div>
			</div>
		</div>
	</div>

	<jsp:directive.include file="../utils/progressBar.jsp" />
</body>
</html>