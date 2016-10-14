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
<title>${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<link rel="stylesheet" href="/css/acceuil.css">
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script src="/js/creationpool.js"></script>
</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<!-- section Principal -->
	<div class="w3-container">
		<br>
		<!-- changer identifiant et courriel -->
		<c:if test="${requestScope.messageConfirmationChangementUsername!=null}">
			<div class="w3-container w3-section w3-red" style="width: 80%; margin-left: auto; margin-right: auto">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h4>${requestScope.messageConfirmationChangementUsername}</h4>
			</div>
		</c:if>
		<c:if test="${requestScope.messageConfirmationChangementPassword!=null}">
			<div class="w3-container w3-section w3-red" style="width: 80%; margin-left: auto; margin-right: auto">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h4>${requestScope.messageConfirmationChangementPassword}</h4>
			</div>
		</c:if>
		<c:if test="${requestScope.messageConfirmationChangementCourriel!=null}">
			<div class="w3-container w3-section w3-red" style="width: 80%; margin-left: auto; margin-right: auto">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h4>${requestScope.messageConfirmationChangementCourriel}</h4>
			</div>
		</c:if>
		<c:if test="${requestScope.messageConfirmationChangementNomTeam!=null}">
			<div class="w3-container w3-section w3-red" style="width: 80%; margin-left: auto; margin-right: auto">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h4>${requestScope.messageConfirmationChangementNomTeam}</h4>
			</div>
		</c:if>
		<c:if test="${requestScope.messageConfirmationChangementCourriel!=null}">
			<div class="w3-container w3-section w3-red" style="width: 80%; margin-left: auto; margin-right: auto">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h4>${requestScope.messageConfirmationChangementCourriel}</h4>
			</div>
		</c:if>
		<!-- changer identifiant et courriel -->
		<br>
		<div class="w3-card-24 w3-white" style="width: 80%; margin-left: auto; margin-right: auto">
			<div class="w3-indigo w3-center w3-padding">
				<h3>Changer votre nom d'utilisateur, votre mot de passe ou votre courriel (vous pouvez changer un ou plusieurs à la fois)</h3>
			</div>
			<form class="w3-padding-left" action="/MonCompte" method="post">

				<br>
				<label class="w3-label w3-text-indigo w3-large">Nouveau nom d'utilisateur</label>
				<input class="w3-input w3-container w3-text-blue w3-validate" type="text" name="username" style="width: 90%">
				<br>
				<label class="w3-label w3-text-indigo w3-large">Nouveau mot de passe</label>
				<input class="w3-input w3-container w3-text-blue w3-validate" type="password" name="password" style="width: 90%">
				<br>
				<label class="w3-label w3-text-indigo w3-large">Nouveau courriel (si vous ne recevez pas de courriel de confirmation d'ici une heure, essayez de nouveau)</label>
				<input class="w3-input w3-container w3-text-blue w3-validate" type="email" name="email" style="width: 90%">
				<br>
				<button class="w3-btn w3-khaki w3-large w3-text-indigo" disabled>Effectuer le ou les changement</button>
				<br>
				<c:if test="${requestScope.messageErreurChangementCourriel!=null}">
					<p class="w3-text-red ">${requestScope.messageErreurChangementCourriel!=null}</p>
				</c:if>
				<br>
			</form>
		</div>

		<!-- changer nom équipe ou Logo -->
		<br>
		<br>
		<br>
		<div class="w3-card-24 w3-white" style="width: 80%; margin-left: auto; margin-right: auto">
			<div class="w3-indigo w3-center w3-padding">
				<h3>Changer votre nom d'équipe ou votre logo</h3>
			</div>
			<form class="w3-padding-left" action="/MonCompte" method="post">

				<br>
				<label class="w3-label w3-text-indigo w3-large">Nouveau nom d'équipe</label>
				<input class="w3-input w3-container w3-text-blue w3-validate" type="text" name="teamName" style="width: 90%">
				<br>
				<input class="w3-btn w3-margin-left w3-khaki w3-xlarge w3-text-indigo" type="button" name="logoTeam" value="Choisir un nouveau logo" onclick="openTeamLogoPicker()">
				<input id="logoUrl" type="hidden" name="logoUrlTeam" value="default">
				<br>
				<br>
				<img id="logoTeamChosen" class="w3-container" src="${Utilisateur.urlTeamLogo}">
				<br>
				<br>
				<button class="w3-btn w3-khaki w3-large w3-text-indigo" disabled>Effectuer le ou les changement</button>
				<br>

			</form>
			<br>
		</div>

		<br>
		<br>
		<br>
		<jsp:directive.include file="../utils/teamLogoPicker.jsp" />


	</div>



	<!-- section Javascript+ChannelMessage -->
	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuNavBarUserAccount').classList
				.add('w3-khaki');
	</script>
</body>
</html>