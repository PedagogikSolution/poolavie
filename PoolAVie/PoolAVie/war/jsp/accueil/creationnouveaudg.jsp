<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Pool à vie</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="/js/creationpool.js"></script>
</head>

<body>

	<!-- Header avec titre et images de promo -->
	<div class="w3-container w3-blue w3-center">
		<h1>CRÉATION D'UNE NOUVELLE ÉQUIPE</h1>
	</div>
	<c:choose>
		<c:when test="${MessageErreurBeans.erreurCreateNewTeam!=null }">
			<!-- message d'erreur avec solution possible du type envoyer courriel a commissaire 
			ou simplement reclicker sur lien dans message ou en copiant celui-ci au complet -->
	
		${MessageErreurBeans.erreurCreateNewTeam}
	
		</c:when>
		<c:otherwise>
			<!-- On affiche le formulaire de creation d'une équipe et de son DG -->
			<!-- Section centrale -->
			<div class="w3-container">
				<form class="w3-container" action="/CreationDirecteurGeneral" method="post" name="creation">
					<div class="w3-half w3-container">
						<p>
							<label>Nom d'utilisateur</label> <input class="w3-input w3-validate" type="text" name="username">
						</p>

						<p>
							<label>Mot de Passe</label> <input class="w3-input w3-validate" type="password" name="password">
						</p>

						<p>
							<label>Courriel</label> <input class="w3-input w3-validate" type="email" name="email">
						</p>

						<br>
						<c:if test="${MessageErreurBeans.erreurFormulaireRegistration!=null }">
							<p>${MessageErreurBeans.erreurFormulaireRegistration}</p>
						</c:if>

					</div>
					<div class="w3-half w3-container w3-center">
						<p>
							<label>Nom de l'équipe</label> <input class="w3-input" type="text" name="nomDuTeam" placeholder="Le nom de votre équipe">
						</p>
						<p>
					<label>Logo du team</label> <input class="w3-input" type="button" name="logoTeam" value="Choisir un logo" onclick="openTeamLogoPicker()" >
					<input id="logoUrl" type="hidden" name="logoUrlTeam" value="default">
				</p>
				<br>
					<img id="logoTeamChosen" class="w3-hide" src="https://storage.googleapis.com/poolavie-bucket/QUE.png">
				<br>

						<button class="w3-margin-top w3-btn w3-blue">Register</button>


					</div>

				</form>
			</div>

		</c:otherwise>
	</c:choose>

<jsp:directive.include file="../utils/teamLogoPicker.jsp" />

</body>
</html>