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
		<c:if test="${requestScope.whichShow==6 }">
		<div class="w3-third w3-container">

			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>J'offre</h1>
				</caption>

				<!-- regulier offer -->
				<tr class="w3-indigo">

					<th>Nom</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.nomMakingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td>${tradeOfferBean.nomMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				


				<!-- argent offer -->
				<tr class="w3-indigo">

					<th>Argent</th>


				</tr>

				<c:if test="${empty tradeOfferBean.cashMakingOffer}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.cashMakingOffer}">

					<tr>
						<td>${tradeOfferBean.cashMakingOffer}$</td>
					</tr>

				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th>Round</th>
					<th>team</th>


				</tr>
				<c:set var="nombreDePick" value="${tradeOfferBean.roundPickMakingOffer}" />
				<c:if test="${empty nombreDePick}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDePick}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

						<tr>
							<td>${tradeOfferBean.roundPickMakingOffer[i]}</td>
							<td>${tradeOfferBean.fromPickMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>



			</table>




		</div>

		<div class="w3-third w3-container">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<span class="w3-xxxlarge">EN ÉCHANGE DE</span>

		</div>

		<div class="w3-third w3-container">
			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Je reçois</h1>
				</caption>

				<!-- regulier offer -->
				<tr class="w3-indigo">

					<th>Nom</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.nomReceivingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td>${tradeOfferBean.nomReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				

				<!-- argent offer -->
				<tr class="w3-indigo">

					<th>Argent</th>


				</tr>

				<c:if test="${empty tradeOfferBean.cashReceivingOffer}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.cashReceivingOffer}">

					<tr>
						<td>${tradeOfferBean.cashReceivingOffer}$</td>
					</tr>

				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th>Round</th>
					<th>team</th>


				</tr>
				<c:set var="nombreDePick" value="${tradeOfferBean.roundPickReceivingOffer}" />
				<c:if test="${empty nombreDePick}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDePick}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

						<tr>
							<td>${tradeOfferBean.roundPickReceivingOffer[i]}</td>
							<td>${tradeOfferBean.fromPickReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>



			</table>

		</div>
		</c:if>
				<br>
		<br>
		<c:if test="${requestScope.whichShow==7 }">
		<div class="w3-third w3-container">

			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>Je recois</h1>
				</caption>

				<!-- regulier offer -->
				<tr class="w3-indigo">

					<th>Nom</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.nomMakingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td>${tradeOfferBean.nomMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				


				<!-- argent offer -->
				<tr class="w3-indigo">

					<th>Argent</th>


				</tr>

				<c:if test="${empty tradeOfferBean.cashMakingOffer}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.cashMakingOffer}">

					<tr>
						<td>${tradeOfferBean.cashMakingOffer}$</td>
					</tr>

				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th>Round</th>
					<th>team</th>


				</tr>
				<c:set var="nombreDePick" value="${tradeOfferBean.roundPickMakingOffer}" />
				<c:if test="${empty nombreDePick}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDePick}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

						<tr>
							<td>${tradeOfferBean.roundPickMakingOffer[i]}</td>
							<td>${tradeOfferBean.fromPickMakingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>



			</table>




		</div>

		<div class="w3-third w3-container">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<span class="w3-xxxlarge">EN ÉCHANGE DE</span>

		</div>

		<div class="w3-third w3-container">
			<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
				<caption class="w3-blue w3-xlarge">
					<h1>J'offre</h1>
				</caption>

				<!-- regulier offer -->
				<tr class="w3-indigo">

					<th>Nom</th>


				</tr>
				<c:set var="nombreDeJoueur" value="${tradeOfferBean.nomReceivingOffer}" />
				<c:if test="${empty nombreDeJoueur}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDeJoueur}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDeJoueur)-1}">

						<tr>
							<td>${tradeOfferBean.nomReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>

				

				<!-- argent offer -->
				<tr class="w3-indigo">

					<th>Argent</th>


				</tr>

				<c:if test="${empty tradeOfferBean.cashReceivingOffer}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty tradeOfferBean.cashReceivingOffer}">

					<tr>
						<td>${tradeOfferBean.cashReceivingOffer}$</td>
					</tr>

				</c:if>

				<!-- recrue offer -->
				<tr class="w3-indigo">

					<th>Round</th>
					<th>team</th>


				</tr>
				<c:set var="nombreDePick" value="${tradeOfferBean.roundPickReceivingOffer}" />
				<c:if test="${empty nombreDePick}">
					<tr>
						<td>Aucun</td>
					<tr>
				</c:if>
				<c:if test="${not empty nombreDePick}">
					<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

						<tr>
							<td>${tradeOfferBean.roundPickReceivingOffer[i]}</td>
							<td>${tradeOfferBean.fromPickReceivingOffer[i]}</td>
						</tr>

					</c:forEach>
				</c:if>



			</table>

		</div>
		</c:if>
	</div>
	
	
	
	

	<div class="w3-container w3-row w3-center">
		<br>
		<br>

		Ainsi que le message suivant :
		<br>
		<textarea rows="8" cols="150">${tradeOfferBean.messageOffre}</textarea>
	</div>
	<hr>
	<div class="w3-container w3-row w3-center">
	<br>
	<c:if test="${requestScope.whichShow==6 }">
	<form class="w3-container w3-form" action="/Trade" method="post">
	<input type="hidden" name="trade_id" value="${tradeOfferBean.trade_id}">
		<input type="hidden" name="tradeTag" value="5">
		<input style="font-size: 18px" type="submit" value="Annuler cette offre">
	</form>
	<br>
	
	</c:if>
	
	<c:if test="${requestScope.whichShow==7 }">
	<form class="w3-container w3-form" action="/Trade" method="post">
	<input type="hidden" name="trade_id" value="${tradeOfferBean.trade_id}">
		<input type="hidden" name="tradeTag" value="5">
		<input style="font-size: 18px" type="submit" value="Refuser cette offre">
	</form>
	<br>
	<br>
	
	<form class="w3-container w3-form" action="/Trade" method="post">
	<input type="hidden" name="trade_id" value="${tradeOfferBean.trade_id}">
		<input type="hidden" name="tradeTag" value="4">
		<input style="font-size: 18px" type="submit" value="Accepter cette offre">
	</form>
	</c:if>
	
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
</body>
</html>