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
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="../../css/material_design.css" rel="stylesheet">
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_admin.jsp" />

	<!-- Body de la page draft_center -->

	<div class="w3-container w3-margin-top">

		<div class="w3-row w3-container">
			<div class="w3-container w3-half">
				<div class="w3-card-24 w3-blue">
					<i class="w3-container material-icons md-96">settings</i>
					
					<h2 class="w3-right w3-padding-right w3-jumbo">Choisir la date du draft</h2>
					


				</div>

			</div>
			<div class="w3-container w3-half"></div>
		</div>


	</div>







</body>
</html>