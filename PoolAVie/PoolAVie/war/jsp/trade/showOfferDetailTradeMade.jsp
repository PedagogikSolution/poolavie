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
<title>Feuille d'échange</title>
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
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />



	<div class="w3-container w3-row">
		<br>
		<br>

		<div class="w3-half w3-container">

			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>${tradeOfferBean.teamThatTradeName} a reçu</h1>
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
				<c:set var="nombreDeRookie" value="${tradeOfferBean.rookieNomMakingOffer}" />
				<c:if test="${empty nombreDeRookie}">
					<tr>
						<td class="w3-large" colspan="2">Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeRookie}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeRookie)-1}">

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



			</table>


			<br>
			<br>

		</div>



		<div class="w3-half w3-container">
			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>${tradeOfferBean.teamTradeToName} a reçu</h1>
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



			</table>
			<br>
			<br>
		</div>
	<br>
			<br>
	</div>

	<br>

	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecTrade').classList.add('w3-khaki');
	</script>
</body>
</html>