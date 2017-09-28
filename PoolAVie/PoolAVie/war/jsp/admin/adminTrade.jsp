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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="../../css/material_design.css" rel="stylesheet">
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
	<jsp:directive.include file="menu_admin.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<!-- section Content -->
	<div class="w3-container">

		<c:if test="${Pool.poolType==1 }">
			<!-- sous-section changement de date fin de trade -->
			<div class="w3-row"></div>
		</c:if>

		<c:if test="${Pool.poolType==2 }">
			<!-- sous-section set numberOfTradePeriod -->
			<div class="w3-row"></div>

			<!-- sous-section set date for each period -->
			<div class="w3-row"></div>
		</c:if>

		<c:if test="${Pool.poolType==1 && Pool.cycleAnnuel>=3 }">
			<!-- sous-section open/close trade -->

			<br>
			<div class="w3-row">

				<h2 class="w3-container">Ouvrez ou fermez la possibilité de faire des échanges</h2>
				<label class="switch w3-container">
					<input id="toggleOpenTrade" type="checkbox" name="openTrade">
					<span class="slider round"></span>
				</label>




			</div>
		</c:if>

	</div>

	<!-- section DraftMessage -->
	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>

	<!-- section SCRIPT POUR MENU CSS -->
	<script>
		document.getElementById('menuSecAdmin').classList.add('w3-khaki');
	</script>
	<!-- section SCRIPT POUR AJAX OPEN TRADE -->
	<script>
		$(document).ready(function() {

			$("#toggleOpenTrade").change(function() {
				var state = $("#toggleOpenTrade").is(':checked') ? 1 : 0;

				if (state == 1) {
					$.post("/AdminTrade", {
						openTrade : "1"

					});
				} else {
					$.post("/AdminTrade", {
						openTrade : "0"

					});
				}
			});
		});
	</script>

</body>
</html>