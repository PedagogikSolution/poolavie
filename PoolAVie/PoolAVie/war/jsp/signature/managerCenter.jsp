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
	<jsp:directive.include file="menu_signature.jsp" />

	<!-- Body de la page reglements -->

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<div class="w3-container">

		<br>
		<c:if test="${Pool.cycleAnnuel==1||Pool.cycleAnnuel==1||Pool.cycleAnnuel==2||Pool.cycleAnnuel==3||Pool.cycleAnnuel==5||Pool.cycleAnnuel==6 }">
			<div class="w3-content w3-margin-top" style="max-width: 90%">
				<div class="w3-container w3-red w3-center">
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>Il n'y a pas de gestion possible à ce stade du cycle annuel du pool</h3>
				</div>
			</div>
		</c:if>



	</div>
	
	<div class="w3-container w3-margin-top">

		<div class="w3-row w3-container">
			<div class="w3-container w3-half">
				
				
				

			</div>
			<div class="w3-container w3-half">
			<c:if test="${Pool.cycleAnnuel==7}">
					<p>C'est la période de rachat de contrat de fin d'année. Vous pouvez faire vos rachats en cliquant sur le lien ci-bas ou en cliquant sur l'onglet du menu correspondant à Rachat de contrat</p>
					<p>voir section regles 4.3 pour des détails sur cette étape du cycle de fin d'année</p>
					<form action="/Signature" method="get">
					<input type="submit" value="Racheter des contrats">
					
					</form>

				</c:if>
			
			</div>
		</div>


	</div>



	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecSign').classList.add('w3-khaki');
	</script>
</body>
</html>