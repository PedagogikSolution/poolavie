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

		<!-- Si l'attribut message est pas vide, affiche message trade not open at this time -->
		<c:if test="${requestScope.messageTrade!=null}">

			<div class="w3-container">


				<h3>SECTION OFFRE REÇUE</h3>
				<p>${requestScope.messageTrade}</p>

			</div>


		</c:if>


		<c:if test="${requestScope.tradeOpen==1}">

			<!-- Confirmation de l'offre  -->

			<form method="Post" action="/Trade">



			



			<input type="hidden" name="tradeTag" value="3">
			<input type="submit" value="Évaluez cette échange" />
			</form>


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