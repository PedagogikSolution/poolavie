<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
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

	<!-- Section centrale -->
	<div id="marketing1" class="w3-container">
		<br>
		<br>
		<br>
		<div id="marketing2" class="w3-row-padding">
			<form class="w3-container" action="/CreationPool" method="post" name="creation">
				<div class="w3-third w3-container ">

					<div class="w3-card-24 w3-white" style="width: 80%; height: 80%; margin-left: auto; margin-right: auto">
						<div class="w3-indigo w3-center w3-padding">
							<h3>Étape 1 : Créer votre équipe</h3>
						</div>

						<br>
						<label class="w3-label w3-text-indigo w3-large w3-container">Votre nom d'équipe</label>
						<input class="w3-input w3-container w3-validate w3-xlarge w3-margin-left" type="text" name="nomDuTeam" maxlength="20" style="width:90%" autocomplete="off">
						<br>
						<input class="w3-btn  w3-margin-left w3-khaki w3-xlarge w3-text-indigo" type="button" name="logoTeam" value="Choisir un logo" onclick="openTeamLogoPicker()">
						<input id="logoUrl" type="hidden" name="logoUrlTeam" value="https://storage.googleapis.com/poolavie-bucket/QUE.png">
						<br>
						<br>
						<img id="logoTeamChosen" class="w3-container" src="https://storage.googleapis.com/poolavie-bucket/QUE.png">
						<br>
						<br>
					</div>
					<br>
					<br>

				</div>
				<div class="w3-third w3-container">

					<div class="w3-card-24 w3-white" style="width: 80%; height: 80%; margin-left: auto; margin-right: auto">
						<div class="w3-indigo w3-center w3-padding">
							<h3>Étape 2 : Invitez des particpants</h3>
						</div>
						<br>
						<label class="w3-label w3-text-indigo w3-large w3-container">Nombre de Joueur</label>

						<div class="w3-container">
							<input class="w3-radio" type="radio" name="nombreEquipe" value="8" checked onclick="m8players() ">
							<label class="w3-validate">8</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="nombreEquipe" value="9" onclick="m9players()">
							<label class="w3-validate">9</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="nombreEquipe" value="10" onclick="m10players()">
							<label class="w3-validate">10</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="nombreEquipe" value="11" onclick="m11players()">
							<label class="w3-validate">11</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="nombreEquipe" value="12" onclick="m12players()">
							<label class="w3-validate">12</label>
						</div>

						<br>
						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email1" placeholder="courriel joueur 1" style="width:90%" autocomplete="off">
						</p>

						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email2" placeholder="courriel joueur 2" style="width:90%" autocomplete="off">
						</p>

						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email3" placeholder="courriel joueur 3" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email4" placeholder="courriel joueur 4" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email5" placeholder="courriel joueur 5" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email6" placeholder="courriel joueur 6" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input class="w3-input w3-validate w3-margin-left" type="email" name="email7" placeholder="courriel joueur 7" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input id="j8" class="w3-input w3-validate w3-margin-left w3-hide" type="email" name="email8" placeholder="courriel joueur 8" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input id="j9" class="w3-input w3-validate w3-margin-left w3-hide" type="email" name="email9" placeholder="courriel joueur 9" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input id="j10" class="w3-input w3-validate w3-margin-left w3-hide" type="email" name="email10" placeholder="courriel joueur 10" style="width:90%" autocomplete="off">
						</p>
						<p>
							<input id="j11" class="w3-input w3-validate w3-margin-left w3-hide" type="email" name="email11" placeholder="courriel joueur 11" style="width:90%" autocomplete="off">
						</p>
					</div>
					<br>
					<br>
				</div>
				<div class="w3-third w3-container">

					<div class="w3-card-24 w3-white" style="width: 80%; height: 80%; margin-left: auto; margin-right: auto">
						<div class="w3-indigo w3-center w3-padding">
							<h3>Étape 3 : Paramétrez votre pool</h3>
						</div>
						<br>
						<label class="w3-label w3-text-indigo w3-large w3-container">Nom du pool</label>
						<input class="w3-input w3-container w3-margin-left" type="text" name="nomDuPool" placeholder="Exemple : Challenge de Saint-Clinclin" maxlength="30" style="width:90%">
						<br>

						<label class="w3-label w3-text-indigo w3-large w3-container">Type de pool</label>

						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typePool" value="1" checked>
							<label class="w3-validate w3-margin-left">Sur plusieurs années</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typePool" value="2" disabled>
							<label class="w3-validate w3-margin-left">Une seule année</label>
						</div>

						<br>



						<label class="w3-label w3-text-indigo w3-large w3-container">Paramètre des échanges</label>

						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typeTrade" value="1" checked>
							<label class="w3-validate w3-margin-left">Ouverte jusqu'à la date limite</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typeTrade" value="2" >
							<label class="w3-validate w3-margin-left">Uniquement lors de période d'échange</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typeTrade" value="3" disabled>
							<label class="w3-validate w3-margin-left">Jamais</label>
						</div>

						<br>

						<label class="w3-label w3-text-indigo w3-large w3-container">Option de repêchage</label>

						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typeDraft" value="1" checked>
							<label class="w3-validate w3-margin-left">Live draft</label>
						</div>
						<div class="w3-container">
							<input class="w3-radio" type="radio" name="typeDraft" value="2" disabled>
							<label class="w3-validate w3-margin-left">Manuel</label>
						</div>

						<br>
						<div style="width: 80%; height: 80%; margin-left: auto; margin-right: auto">
							<button onclick="progressbar()" class="w3-btn w3-khaki w3-xlarge w3-text-indigo w3-center">Créer mon pool</button>
						</div>
						<br>
						<br>
					</div>

				</div>




			</form>
		</div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>


	</div>





	<jsp:directive.include file="../utils/teamLogoPicker.jsp" />



	<footer id="footerAccueil" class="w3-container w3-indigo w3-large w3-center w3-bottom">
		<p>Solution Pedagogik inc. © 2016. Tous droits réservés</p>
	</footer>
	<div id="progressBar" class="w3-display-middle w3-half w3-center w3-hide">
	<h1 id="progressMessage1" class="w3-show">Création des équipes</h1>
	<h1 id="progressMessage2" class="w3-hide">Création des bases de données</h1>
	<h1 id="progressMessage3" class="w3-hide">Création des paramètres</h1>
	<h1 id="progressMessage4" class="w3-hide">Envoie des courriels</h1>
	<h1 id="progressMessage5" class="w3-hide">Passage de la zamboni. Votre nouveaux pool devrait être prêt d'ici peu</h1>
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
					if(width==20){
						document.getElementById('progressMessage1').classList.remove('w3-show');
						document.getElementById('progressMessage1').classList.add('w3-hide');
						document.getElementById('progressMessage2').classList.remove('w3-hide');
						document.getElementById('progressMessage2').classList.add('w3-show');
					}
					if(width==40){
						document.getElementById('progressMessage2').classList.remove('w3-show');
						document.getElementById('progressMessage2').classList.add('w3-hide');
						document.getElementById('progressMessage3').classList.remove('w3-hide');
						document.getElementById('progressMessage3').classList.add('w3-show');
					}
					if(width==60){
						document.getElementById('progressMessage3').classList.remove('w3-show');
						document.getElementById('progressMessage3').classList.add('w3-hide');
						document.getElementById('progressMessage4').classList.remove('w3-hide');
						document.getElementById('progressMessage4').classList.add('w3-show');
					}
					if(width==80){
						document.getElementById('progressMessage4').classList.remove('w3-show');
						document.getElementById('progressMessage4').classList.add('w3-hide');
						document.getElementById('progressMessage5').classList.remove('w3-hide');
						document.getElementById('progressMessage5').classList.add('w3-show');
					}
				}
			}

	}
</script>

</body>
</html>