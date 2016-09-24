<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Pool à vie</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<link rel="stylesheet" href="/css/acceuil.css">
<script src="/js/creationpool.js"></script>
</head>

<body>

	<!-- Header avec titre et images de promo -->
	<header class="w3-container w3-indigo w3-large">
		<ul class="w3-navbar w3-indigo w3-left w3-xlarge">
			<li><a href="#">Démo</a></li>
			<li><a href="#">Beta Test</a></li>
		</ul>

		<ul class="w3-navbar w3-indigo w3-right w3-xlarge">
			<li><a href="#">Français</a></li>
			<li><a href="#">Nous Joindre</a></li>
		</ul>


	</header>

	<c:choose>
		<c:when test="${MessageErreurBeans.erreurCreateNewTeam!=null }">
			<!-- message d'erreur avec solution possible du type envoyer courriel a commissaire 
			ou simplement reclicker sur lien dans message ou en copiant celui-ci au complet -->
	
		${MessageErreurBeans.erreurCreateNewTeam}
	
		</c:when>
		<c:otherwise>

			<!-- Section centrale -->
			<div id="marketing1" class="w3-container">
				<br> <br> <br> <br> <br>
				<div class="w3-row">
					<form class="w3-container w3-padding-left" action="/CreationDirecteurGeneral" method="post" name="creation">
						<div class="w3-half w3-container ">

							<div class="w3-card-24 w3-white" style="width: 55%; height: 55%; margin-left: auto; margin-right: auto">
								<div class="w3-indigo w3-center w3-padding">
									<h3>Créer vos identifiants</h3>
								</div>

								<br> <label class="w3-label w3-text-indigo w3-container w3-large">Courriel</label> <br> <input class="w3-padding-left w3-padding-right w3-input w3-text-indigo w3-container w3-validate" type="email" name="email"> <br> <label
									class="w3-label w3-container w3-text-indigo w3-large">Nom d'utilisateur</label> <br> <input class="w3-padding-left w3-padding-right w3-input w3-text-indigo w3-container w3-validate" type="text" name="username"> <br> <label
									class="w3-label w3-container w3-text-indigo w3-large">Mot de passe</label> <br> <input class="w3-padding-left w3-padding-right w3-input w3-text-indigo w3-container w3-validate" type="password" name="password"> <br> <br>
								<c:if test="${MessageErreurBeans.erreurFormulaireRegistration!=null }">
									${MessageErreurBeans.erreurFormulaireRegistration}
								</c:if>
								<br>


							</div>
							<br> <br>
							<div style="width: 55%; height: 55%; margin-left: auto; margin-right: auto">
								<button class="w3-btn w3-khaki w3-xlarge w3-text-indigo w3-center">Passez à l'Étape suivante</button>
							</div>
						</div>
						<div class="w3-half w3-container">
							<div class="w3-card-24 w3-white" style="width: 55%; height: 55%; margin-left: auto; margin-right: auto">
								<div class="w3-indigo w3-center w3-padding">
									<h3>Créer votre équipe</h3>
								</div>

								<br> <label class="w3-label w3-text-indigo w3-large w3-container">Votre nom d'équipe</label>
								<input class="w3-padding-left w3-padding-right w3-input w3-container" type="text" name="nomDuTeam">
								<br>
								<input class="w3-btn w3-margin-left w3-khaki w3-xlarge w3-text-indigo" type="button" name="logoTeam" value="Choisir un logo" onclick="openTeamLogoPicker()">
								<input id="logoUrl" type="hidden" name="logoUrlTeam" value="default"> <br> <br> <img id="logoTeamChosen" class="w3-container" src="https://storage.googleapis.com/poolavie-bucket/QUE.png">
								<br>
								<br>
							</div>

							<br> <br>
						</div>
					</form>
				</div>
				<br> <br> <br> <br> 


			</div>





			<jsp:directive.include file="../utils/teamLogoPicker.jsp" />
		</c:otherwise>
	</c:choose>

	<footer class="w3-container w3-indigo w3-large w3-center w3-bottom">
		<p>Solution Pedagogik inc. © 2016. Tous droits réservés</p>
	</footer>


</body>
</html>