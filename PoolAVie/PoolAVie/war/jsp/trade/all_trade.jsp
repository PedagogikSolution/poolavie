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
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

			<!-- Liste des offres recu -->
		<div class="w3-row">
				<br>
				<br>
				<br>
				<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
					<caption class="w3-blue w3-xlarge">
						<h2>Les trades de notre pool</h2>
					</caption>
					<tr class="w3-indigo">
						<th>No</th>
						<th>Équipe 1</th>
						<th>Équipe 2</th>
						<th>Date de l'échange</th>
						<th>Détails</th>

					</tr>

					<c:set var="nombreDeTrade" value="${tradeOfferMade.tradeOfferNameTeamTradeWith}" />
					<c:if test="${empty nombreDeTrade}">
						<tr>
							<td>Il n'y a pas eu de trade encore dans votre pool cette année. Allez au trade center pour faire des offres</td>
						<tr>
					</c:if>
					<c:if test="${not empty nombreDeTrade}">
						<c:forEach var="i" begin="0" end="${fn:length(nombreDeTrade)-1}">

							<tr>


								<td>${tradeOfferMade.tradeOfferId[i]}</td>
								<td>${tradeOfferMade.tradeOfferNameTeamTradeWith[i]}</td>
								<td>${tradeOfferMade.tradeOfferNameTeamTradeWith2[i]}</td>
								<td>${tradeOfferMade.dateTradeOfferArray[i]}</td>
								<td>
									<form method="post" action="/Trade">
									<input type="hidden" name="tradeTag" value="9">
									<input type="hidden" name="trade_id" value="${tradeOfferMade.tradeOfferId[i]}">
									<input type="submit" value="Voir">
									</form>
								</td>

							</tr>

						</c:forEach>
					</c:if>




				</table>
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