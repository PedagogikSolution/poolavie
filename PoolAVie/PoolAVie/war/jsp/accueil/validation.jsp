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
</head>

<body onload="removeProgressBar()">

	<!-- Header avec titre et images de promo -->
	<header class="w3-container w3-indigo w3-large w3-center">
		<ul class="w3-navbar w3-indigo  w3-xlarge">
			<li class="w3-left">
				<a href="#">Démo</a>
			</li>
			<li class="w3-left">
				<a href="#">Beta Test</a>
			</li>
			<li class="w3-navitem w3-twothird w3-indigo w3-xlarge w3-padding-0 w3-hide-small w3-hide-medium">
				<a href="/jsp/accueil/home.jsp">Pool à vie</a>
			</li>
			<li class="w3-rigth">
				<a href="#">Français</a>
			</li>
			<li class="w3-rigth">
				<a href="#">Nous Joindre</a>
			</li>
		</ul>


	</header>
	<!-- SEction centrale -->
	<c:if test="${not empty mauvaiseDate}">
		<h1>${mauvaisDate.erreurFormulaireRegistration}</h1>
	</c:if>
	<c:if test="${empty mauvaiseDate}">
		<div id="marketing1" class="w3-container">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<div id="marketing2" class="w3-card-24 w3-white" style="width: 40%; height: 40%; margin-left: auto; margin-right: auto">
				<div class="w3-indigo w3-center w3-padding">
					<h3>Validez votre compte</h3>
				</div>
				<form class="w3-padding-left" action="/validation" method="post">
					<input type="hidden" name="formulaire" value="1">
					<br>
					<label class="w3-label w3-text-indigo w3-xlarge">Code de Validation</label>
					<input class="w3-input w3-container w3-text-blue w3-validate w3-xlarge" type="text" name="validation">
					<br>
					<button onclick="progressbar()" class="w3-btn  w3-khaki w3-xlarge w3-text-indigo">Valider mon code</button>
					<br>
					<br>
					<p class="w3-white">
						<a href="/recuperation">Vous n'avez pas reçu votre code</a>
					</p>
					<br>
					<c:if test="${MessageErreurBeans.erreurCodeValidationType1!=null }">
						<p>${MessageErreurBeans.erreurCodeValidationType1}</p>

					</c:if>
					<c:if test="${MessageErreurBeans.erreurCodeValidationType2!=null }">
						${MessageErreurBeans.erreurCodeValidationType2}
						
					</c:if>

				</form>
				<br>
			</div>
			
			<br>
			<br>
		</div>
	</c:if>
	<footer id="footerAccueil" class="w3-container w3-indigo w3-large w3-center w3-bottom">
		<p>Solution Pedagogik inc. © 2016. Tous droits réservés</p>
	</footer>
	<div id="progressBar" class="w3-display-middle w3-half w3-center w3-hide">
	<h1 id="progressMessage1" class="w3-show">Validation du code</h1>
	<h1 id="progressMessage2" class="w3-hide">Passage de la zamboni</h1>
	<h1 id="progressMessage3" class="w3-hide">Aiguissage des patins</h1>
	<h1 id="progressMessage4" class="w3-hide">Nettoyage de la chmabre des joueurs</h1>
	<h1 id="progressMessage5" class="w3-hide">Polissage de la coupe Stanley</h1>
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