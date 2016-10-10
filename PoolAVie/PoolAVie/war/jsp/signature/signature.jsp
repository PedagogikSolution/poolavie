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
<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_signature.jsp" />

	<!-- Body de la page reglements -->

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
		<c:if test="${Utilisateur.teamId==currentPicker&&Pool.cycleAnnuel==3}">
					<!-- Si first years and avant draft sans date set-->
					<a href="/DraftPlayers?seg=all&sort=pts"><button class="w3-container w3-section w3-red">
							<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
							<h2>
								C'EST VOTRE TOUR DE DRAFT !!!
								</h3>
								<p>Faites votre choix dans la section TOUS, ATTAQUANT, DEFENSEUR, GARDIEN OU RECRUE en cliquant sur le bouton pick, choisissez ensuite si celui-ci sera dans votre club école ou pas dans la
									boite de dialogue. Cliquez ici pour aller directement à la section TOUS de repêchage.</p>
						</button> </a>
				</c:if>
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