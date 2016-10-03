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
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- Body de la page tarde center -->

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

		<c:if test="${messageErreur.erreurTrade!=null}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>OUPS!</h3>
				<p>${messageErreur.erreurTrade}</p>


			</div>

		</c:if>

		<!-- Si l'attribut message est pas vide, affiche message trade not open at this time -->
		<c:if test="${requestScope.messageTrade!=null}">

			<div class="w3-container">


				<h3>SECTION OFFRE REÇUE</h3>
				<p>${requestScope.messageTrade}</p>

			</div>


		</c:if>


		<c:if test="${requestScope.tradeOpen==1}">


			<!-- Liste des offres recu -->

			<div class="w3-half">

				<c:if test="${tradeOfferReceived.team1==null}">
					<h2>Vous n'avez pas d'offre d'échange. Cliquez ci-bas pour en faire une.</h2>
				</c:if>

			</div>

			<!-- Liste des offres fait -->

			<div class="w3-half">

				<c:if test="${tradeOfferMade.team1==null}">
					<h2>Vous n'avez pas fait aucune offre d'échange encore. Cliquez ci-bas pour en faire une.</h2>
				</c:if>

			</div>
			<br>
			<!-- Faire une offre -->

			<div class="w3-center">
				<h2>Cliquez sur une équipe pour faire une offre à celle-ci</h2>
				<div class="w3-center">
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="1"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam1}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="2"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam2}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="3"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam3}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="4"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam4}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="5"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam5}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="6"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam6}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="7"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam7}" />
					</form>
					<form class="w3-left" method="post" action="/Trade">
						<input type="hidden" name="tradeWith" value="8"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam8}" />
					</form>
					<c:if test="${Pool.nomTeam9!=null}">
						<form class="w3-left" method="post" action="/Trade">
							<input type="hidden" name="tradeWith" value="9"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam9}" />
						</form>
					</c:if>
					<c:if test="${Pool.nomTeam10!=null}">
						<form class="w3-left" method="post" action="/Trade">
							<input type="hidden" name="tradeWith" value="10"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam10}" />
						</form>
					</c:if>
					<c:if test="${Pool.nomTeam11!=null}">
						<form class="w3-left" method="post" action="/Trade">
							<input type="hidden" name="tradeWith" value="11"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam11}" />
						</form>
					</c:if>
					<c:if test="${Pool.nomTeam12!=null}">
						<form class="w3-left" method="post" action="/Trade">
							<input type="hidden" name="tradeWith" value="12"> <input type="hidden" name="tradeTag" value="1"> <input type="submit" value="${Pool.nomTeam12}" />
						</form>
					</c:if>
				</div>
			</div>

		</c:if>



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