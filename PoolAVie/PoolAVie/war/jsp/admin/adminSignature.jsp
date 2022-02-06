<%@ page contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
	prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf"%>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link
	href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link href="../../css/material_design.css" rel="stylesheet">
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker"
			value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_admin.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<div class="w3-container w3-margin-top">

		<div class="w3-row w3-container">
			<div class="w3-container w3-half">

				<h2>ACTIVATION SÉANCE DE WAIVERS</h2>
				
				<form class="w3-container w3-card-24 w3-white"
							action="/AdminSignature" method="post">

							<input type="hidden" name="numeroFormulaire"
								value="1">


							<p>
								<label>Date d'ouverture de la période de choix des waivers</label> <input
									class="w3-input w3-validate" type="date"
									name="dateOpenWaivers" min="Date.now()" required>
							</p>

							<p>
								<label>Date de fermeture de la période de choix des waivers</label> <input
									class="w3-input w3-validate" type="date"
									name="dateCloseWaivers" min="Date.now()" required>
							</p>


							<button class="w3-btn w3-blue w3-xlarge">Confirmer</button>

							<br>

						</form>



			</div>
			<div class="w3-container w3-half">

				<h2>ACTIVATION SÉANCE DE ROOKIE PROMO</h2>
				
				
				<form class="w3-container w3-card-24 w3-white"
							action="/AdminSignature" method="post">

							<input type="hidden" name="numeroFormulaire"
								value="2">


							<p>
								<label>Date d'ouverture de la période de choix des waivers</label> <input
									class="w3-input w3-validate" type="date"
									name="dateOpenRookies" min="Date.now()" required>
							</p>

							<p>
								<label>Date de fermeture de la période de choix des waivers</label> <input
									class="w3-input w3-validate" type="date"
									name="dateCloseRookies" min="Date.now()" required>
							</p>


							<button class="w3-btn w3-blue w3-xlarge">Confirmer</button>

							<br>

						</form>

			</div>
		</div>


	</div>



	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if
		test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecAdmin').classList.add('w3-khaki');
	</script>
</body>
</html>