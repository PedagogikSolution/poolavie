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
<title>Nouvelles ${Pool.poolName}</title>
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
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<c:if test="${messageErreur.erreurTrade!=null}">

		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>${messageErreur.erreurTrade}</h3>
			</div>
		</div>
		<br>

	</c:if>



	<!-- Si l'attribut message est pas vide, affiche message trade not open at this time -->
	<c:if test="${requestScope.messageTrade!=null}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Il n'y a pas d'échange possible à cette période du pool</h3>
			</div>
		</div>

	</c:if>




	<c:if test="${requestScope.tradeOpen==1}">


		<!-- Liste des offres recu -->
		<div class="w3-row">
			<div class="w3-half">
				<br> <br> <br>
				<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
					<caption class="w3-blue w3-xlarge">
						<h2>Les offres que j'ai reçu</h2>
					</caption>
					<tr class="w3-indigo">
						<th>No</th>
						<th>Reçu de</th>
						<th>Date</th>
						<th>Détails</th>

					</tr>

					<c:set var="nombreDeTrade" value="${tradeOfferReceived.tradeOfferNameTeamTradeWith}" />
					<c:if test="${empty nombreDeTrade}">
						<tr>
							<td>Vous n'avez pas d'offre d'échange. Cliquez ci-bas pour en faire une</td>
						<tr>
					</c:if>
					<c:if test="${not empty nombreDeTrade}">
						<c:forEach var="i" begin="0" end="${fn:length(nombreDeTrade)-1}">

							<tr>


								<td>${tradeOfferReceived.tradeOfferId[i]}</td>
								<td>${tradeOfferReceived.tradeOfferNameTeamTradeWith[i]}</td>
								<td>${tradeOfferReceived.dateTradeOfferArray[i]}</td>
								<td>
									<form method="post" action="/Trade">
										<input type="hidden" name="tradeTag" value="7"> <input type="hidden" name="trade_id" value="${tradeOfferReceived.tradeOfferId[i]}"> <input type="submit" value="Voir">
									</form>
								</td>

							</tr>

						</c:forEach>
					</c:if>




				</table>


			</div>

			<!-- Liste des offres fait -->

			<div class="w3-half">
				<br> <br> <br>
				<table class="w3-table  w3-striped w3-bordered w3-card-8" style="width: 90%; margin-left: auto; margin-right: auto">
					<caption class="w3-blue w3-xlarge">
						<h2>Les offres que j'ai fait</h2>
					</caption>
					<tr class="w3-indigo">
						<th>No</th>
						<th>Fait à</th>
						<th>Date</th>
						<th>Détails</th>

					</tr>

					<c:set var="nombreDeTrade" value="${tradeOfferMade.tradeOfferId}" />
					<c:if test="${empty nombreDeTrade}">
						<tr>
							<td>Vous n'avez pas d'offre d'échange. Cliquez ci-bas pour en faire une</td>
						<tr>
					</c:if>
					<c:if test="${not empty nombreDeTrade}">
						<c:forEach var="i" begin="0" end="${fn:length(nombreDeTrade)-1}">

							<tr>


								<td>${tradeOfferMade.tradeOfferId[i]}</td>
								<td>${tradeOfferMade.tradeOfferNameTeamTradeWith[i]}</td>
								<td>${tradeOfferMade.dateTradeOfferArray[i]}</td>
								<td>
									<form method="post" action="/Trade">
										<input type="hidden" name="tradeTag" value="6"> <input type="hidden" name="trade_id" value="${tradeOfferMade.tradeOfferId[i]}"> <input type="submit" value="Voir">
									</form>
								</td>

							</tr>

						</c:forEach>
					</c:if>




				</table>

			</div>

		</div>

		<!-- Faire une offre -->

		<div class="w3-row w3-container w3-center">
			<br> <br> <br>
			<h2>Cliquez sur une équipe pour faire une offre à celle-ci</h2>
			<br> <br>
			<div class="w3-quarter">
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="1"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam1}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam1}
				</form>

				<br>
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="2"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam2}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam2}
				</form>
				<br>
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="3"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam3}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam3}
				</form>
			</div>
			<div class="w3-quarter">
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="4"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam4}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam4}
				</form>
				<br>
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="5"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam5}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam5}
				</form>
				<br>
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="6"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam6}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam6}
				</form>
			</div>
			<div class="w3-quarter">
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="7"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam7}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam7}
				</form>
				<br>
				<form class="w3-container" method="post" action="/Trade">
					<input type="hidden" name="tradeWith" value="8"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam8}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam8}
				</form>
				<br>
				<c:if test="${Pool.nomTeam9!=null}">
					<form class="w3-container" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="9"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam9}" alt="Submit" width="128px"
							height="128px" />
					</form>
					${Pool.nomTeam9}
				</c:if>
			</div>
			<div class="w3-quarter">
				<c:if test="${Pool.nomTeam10!=null}">
					<form class="w3-container" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="10"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam10}" alt="Submit" width="128px"
							height="128px" /> ${Pool.nomTeam10}
					</form>
				</c:if>
				<br>
				<c:if test="${Pool.nomTeam11!=null}">
					<form class="w3-container" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="11"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam11}" alt="Submit" width="128px"
							height="128px" /> ${Pool.nomTeam11}
					</form>
				</c:if>
				<br>
				<c:if test="${Pool.nomTeam12!=null}">
					<form class="w3-container" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="12"> <input type="hidden" name="tradeTag" value="1"> <input type="image" src="${Pool.logoTeam12}" alt="Submit" width="128px"
							height="128px" /> ${Pool.nomTeam12}
					</form>
				</c:if>
			</div>
			<br> <br> <br>
		</div>

	</c:if>

	<br>
	<br>
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