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
	
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel<=3}">
			<br>
			<br>
			<br>
	CETTE SECTION CONTIENT L'ENSEMBLE DES PROCESSUS DE GESTION DES ÉQUIPES TEL QUE RACHAT, MONTER/DESCENDRE ROOKIE, SIGNATURE ET EVENTUELLEMENT EXPANSION
	<br>
			<br>
	LA PREMIÈRE ÉTAPE AURA LIEU IMMÉDIATEMENT APRÈS LE DRAFT ET VOUS PERMETTRA DE SIGNER UN MAXIMUM DE 12 JOUEURS REPÊCHÉS POUR 2 À 5 ANNÉE. VOIR REGLÈMENT SECTION CONTRAT
	</c:if>

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