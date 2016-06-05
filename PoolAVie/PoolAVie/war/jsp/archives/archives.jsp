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
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="navbar_main.jsp" />
	<jsp:directive.include file="menu_secondaire.jsp" />
	<jsp:directive.include file="menu_archives.jsp" />

	<!-- Body de la page reglements -->

	<div class="w3-container">
	
	CONTIENT UN TABLEAU RECAPITULATIF DES MEILLEURS MOMENTS DU POOL ET DES LIENS POUR AFFICHERS LES CLASSEMENTS, LES DRAFTS, LES TRADES ET LES TEAMS ANTÉRIEURS
	
	
	</div>
	
	
	




</body>
</html>