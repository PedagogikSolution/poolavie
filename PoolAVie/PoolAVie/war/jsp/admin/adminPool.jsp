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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="../../css/material_design.css" rel="stylesheet">
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
	
	<div class="w3-container w3-margin-top">

		<div class="w3-row w3-container">
			<div class="w3-container w3-half">
				<a href="/AdminDraft">
					<button class="w3-card-24 w3-blue">
						<i class="w3-container material-icons md-96">settings</i>

						<h2 class="w3-right w3-padding-right w3-jumbo">Choisir la date du draft</h2>



					</button>
				</a>

			</div>
			<div class="w3-container w3-half"></div>
		</div>


	</div>



	<jsp:directive.include file="../utils/draftMessage.jsp" />


	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecAdmin').classList.add('w3-khaki');
	</script>
</body>
</html>