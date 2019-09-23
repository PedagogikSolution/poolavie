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

<body onload="removeProgressBar()">

	<!-- Header avec titre et images de promo -->
	<header class="w3-container w3-indigo w3-large">
		<ul class="w3-navbar w3-indigo w3-left w3-xlarge">
			<li>
				<a href="#">Démo</a>
			</li>
			<li>
				<a href="#">Beta Test</a>
			</li>
		</ul>

		<ul class="w3-navbar w3-indigo w3-right w3-xlarge">
			<li>
				<a href="#">Français</a>
			</li>
			<li>
				<a href="#">Nous Joindre</a>
			</li>
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
				<br>
				<br>
				<br>
				<br>
				<br>
				<div id="marketing2" class="w3-row">
					<form class="w3-container w3-padding-left" action="/CreationDirecteurGeneral" method="post" name="creation" autocomplete="off">
						<div class="w3-half w3-container ">

							<div class="w3-card-24 w3-white" style="width: 55%; height: 55%; margin-left: auto; margin-right: auto">
								<div class="w3-indigo w3-center w3-padding">
									<h3>Créer vos identifiants</h3>
								</div>

								<br>
								<label class="w3-label w3-text-indigo w3-container w3-large">Courriel</label>
								<br>
								<input class="w3-padding-left w3-padding-right w3-input w3-text-indigo w3-container w3-validate w3-margin-left" type="email" name="email" style="width: 90%" autocomplete="off" required>
								<br>
								<label class="w3-label w3-container w3-text-indigo w3-large">Nom d'utilisateur</label>
								<br>
								<input class="w3-padding-left w3-padding-right w3-input w3-text-indigo w3-container w3-validate w3-margin-left" type="text" name="username" style="width: 90%" autocomplete="off" required>
								<br>
								<label class="w3-label w3-container w3-text-indigo w3-large">Mot de passe</label>
								<br>
								<input class="w3-padding-left w3-padding-right w3-input w3-text-indigo w3-container w3-validate w3-margin-left" type="password" name="password" style="width: 90%" autocomplete="off" required>
								<br>
								<br>
								<c:if test="${MessageErreurBeans.erreurFormulaireRegistration!=null }">
									${MessageErreurBeans.erreurFormulaireRegistration}
								</c:if>
								<br>


							</div>
							<br>
							<br>

						</div>
						<div class="w3-half w3-container">
							<div class="w3-card-24 w3-white" style="width: 60%; height: 60%; margin-left: auto; margin-right: auto">
								<div class="w3-indigo w3-center w3-padding">
									<h3>Créer votre équipe</h3>
								</div>

								<br>
								<label class="w3-label w3-text-indigo w3-large w3-container ">Votre nom d'équipe</label>
								<input class="w3-padding-left w3-padding-right w3-input w3-container w3-margin-left" type="text" name="nomDuTeam" style="width: 90%" autocomplete="off" required>
								<br>
								<input class="w3-btn w3-margin-left w3-khaki w3-xlarge w3-text-indigo" type="button" name="logoTeam" value="Choisir un logo" onclick="openTeamLogoPicker()">
								<input id="logoUrl" type="hidden" name="logoUrlTeam" value="https://storage.googleapis.com/poolavie-bucket/QUE.png">
								<br>
								<br>
								<img id="logoTeamChosen" class="w3-container" src="https://storage.googleapis.com/poolavie-bucket/QUE.png">
								<br>
								<br>
								<div style="width: 95%; height: 95%; margin-left: auto; margin-right: auto">
									<button onclick="progressbar()" class="w3-btn w3-khaki w3-xlarge w3-text-indigo">Passez à l'Étape suivante</button>
									<br>
									<br>
								</div>
							</div>


						</div>
					</form>
				</div>
				<br>
				<br>
				<br>
				<br>


			</div>





			<jsp:directive.include file="../utils/teamLogoPicker.jsp" />
		</c:otherwise>
	</c:choose>

	<footer id="footerAccueil" class="w3-container w3-indigo w3-large w3-center w3-bottom">
		<p>Solution Pedagogik inc. © 2016. Tous droits réservés</p>
	</footer>
	<div id="progressBar" class="w3-display-middle w3-half w3-center w3-hide">
		<h1 id="progressMessage1" class="w3-show">Préparation des équipes</h1>
		<h1 id="progressMessage2" class="w3-hide">Comptabilisation des points au classement</h1>
		<h1 id="progressMessage3" class="w3-hide">Passage de la zamboni</h1>
		<h1 id="progressMessage4" class="w3-hide">Aiguissage des patins</h1>
		<h1 id="progressMessage5" class="w3-hide">Enculage des mouches</h1>
		<br>
		<div class="w3-progress-container ">
			<div id="myBar" class="w3-progressbar w3-blue" style="width: 0%">
				<div id="demo" class="w3-container w3-text-white">0</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function removeProgressBar() {
			document.getElementById('progressBar').classList.remove('w3-show');
			document.getElementById('progressBar').classList.add('w3-hide');
		}
		function progressbar() {
			document.getElementById('marketing1').classList.add('w3-overlay');
			document.getElementById('marketing1').classList.remove('background-image');
			document.getElementById('marketing2').classList.add('w3-hide');
			document.getElementById('footerAccueil').classList.add('w3-hide');
			document.getElementById('progressBar').classList.remove('w3-hide');
			document.getElementById('progressBar').classList.add('w3-show');
			var elem = document.getElementById("myBar");
			var width = 0;
			var id = setInterval(frame, 100);
			function frame() {
				if (width >= 100) {
					clearInterval(id);
				} else {
					width++;
					elem.style.width = width + '%';
					document.getElementById("demo").innerHTML = width * 1 + '%';
					if (width == 20) {
						document.getElementById('progressMessage1').classList
								.remove('w3-show');
						document.getElementById('progressMessage1').classList
								.add('w3-hide');
						document.getElementById('progressMessage2').classList
								.remove('w3-hide');
						document.getElementById('progressMessage2').classList
								.add('w3-show');
					}
					if (width == 40) {
						document.getElementById('progressMessage2').classList
								.remove('w3-show');
						document.getElementById('progressMessage2').classList
								.add('w3-hide');
						document.getElementById('progressMessage3').classList
								.remove('w3-hide');
						document.getElementById('progressMessage3').classList
								.add('w3-show');
					}
					if (width == 60) {
						document.getElementById('progressMessage3').classList
								.remove('w3-show');
						document.getElementById('progressMessage3').classList
								.add('w3-hide');
						document.getElementById('progressMessage4').classList
								.remove('w3-hide');
						document.getElementById('progressMessage4').classList
								.add('w3-show');
					}
					if (width == 80) {
						document.getElementById('progressMessage4').classList
								.remove('w3-show');
						document.getElementById('progressMessage4').classList
								.add('w3-hide');
						document.getElementById('progressMessage5').classList
								.remove('w3-hide');
						document.getElementById('progressMessage5').classList
								.add('w3-show');
					}
				}
			}

		}
	</script>
</body>
</html>