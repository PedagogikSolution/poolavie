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
	<header class="w3-container w3-indigo w3-large">
		<ul class="w3-navbar w3-indigo w3-left w3-xlarge">
			<li>
				<a href="#">Démo</a>
			</li>
			<li>
				<a href="/Beta">Beta Test</a>
			</li>
		</ul>

		<ul class="w3-navbar w3-indigo w3-right w3-xlarge">
			<li>
				<a href="#">Français</a>
			</li>
			<li>
				<a href="mailto:info@poolavie.ca" target="_blank">Nous Joindre</a>
			</li>
		</ul>


	</header>

	<div id="marketing1" class="w3-container">

		<div class="w3-row">
			<br>
			<h1 class="w3-center w3-xxxlarge">WWW.POOLAVIE.CA</h1>
			<br>
			<div class="w3-half">

				<div class="w3-card-24 w3-white" style="width: 40%; height: 40%; margin-left: auto; margin-right: auto">
					<div class="w3-indigo w3-center w3-padding">
						<h3>Connectez-vous</h3>
					</div>
					<form class="w3-padding-left" action="/login" method="post">

						<c:if test="${MessageErreurBeans.erreurNotLogIn!=null }">
							<p class="w3-text-red ">${MessageErreurBeans.erreurNotLogIn}</p>
						</c:if>
						<br>
						<label class="w3-label w3-text-indigo w3-large">Nom d'utilisateur</label>
						<input class="w3-input w3-container w3-text-blue w3-validate" type="text" name="username">
						<br>
						<label class="w3-label w3-text-indigo w3-large">Mot de passe</label>
						<input class="w3-input w3-container w3-text-blue  w3-validate" type="password" name="password">
						<br>
						<button onclick="progressbar()" class="w3-btn w3-khaki w3-large w3-text-indigo">Se connecter</button>
						<br>
						<p class="w3-white">
							<a href="/recuperation">Vous avez oubliez vos identifiants?</a>
						</p>
						<c:if test="${MessageErreurBeans.erreurFormulaireLogin!=null }">
							<p class="w3-text-red ">${MessageErreurBeans.erreurFormulaireLogin}</p>
						</c:if>
						<br>
					</form>
				</div>
				<br>
				<br>
			</div>

			<div class="w3-half">

				<div class="w3-card-24 w3-white" style="width: 40%; height: 40%; margin-left: auto; margin-right: auto">
					<div class="w3-indigo w3-center w3-padding">
						<h3>Créer un nouveau pool</h3>
					</div>
					<form class="w3-padding-left" action="/register" method="post" autocomplete="off">
						<br>
						<label class="w3-label w3-text-indigo w3-large">Courriel</label>
						<br>
						<input  class="w3-input  w3-text-indigo  w3-validate" type="email" name="email" autocomplete="off">
						<br>
						<label class="w3-label w3-text-indigo w3-large">Nom d'utilisateur</label>
						<br>
						<input id="usernameNewPool" class="w3-input  w3-text-indigo  w3-validate" type="text" name="username" autocomplete="off">
						<br>
						<label class="w3-label w3-text-indigo w3-large">Mot de passe</label>
						<br>
						<input id="passwordNewPool" class="w3-input  w3-text-indigo  w3-validate" type="password" name="password" autocomplete="off">
						<br>
						<button onclick="progressbar()" class="w3-btn w3-khaki w3-large w3-text-indigo w3">Poursuivre à l'étape suivante</button>
						<br>
						<c:if test="${MessageErreurBeans.erreurFormulaireRegistration!=null }">
							<p class="w3-text-red">${MessageErreurBeans.erreurFormulaireRegistration}</p>
						</c:if>
						<br>
					</form>

				</div>
				<br>
				<br>
			</div>
			<br>
			<br>
		</div>
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
	</div>
	<hr id="hrAccueil1" class="w3-indigo">
	<div id="marketing2" class="w3-container">
		<div class="w3-row" style="height: 800px">
			<br>
			<br>
			<br>
			<br>
			<div class="w3-half">
				<img src="https://storage.googleapis.com/poolavie-bucket/hockeyman.png" alt="background marketing 1" style="width: 80%, max-width:500px">
			</div>
			<div class="w3-half w3-xlarge">
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Plateforme de Repêchage Interactive avec projection sur Écran de télévision intelligente ou via chromecast et
					Apple Tv</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Gestion des échanges entièrement informatisées et automatisées avec offre via site internet ou courriel, et ce,
					même live durant le draft</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Une expérience de Directeur général avec des possibilités de contrats, de club école, de masse salarial, de
					rachats et plus</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Gérer vos équipes de vos ordinateurs, tablettes ou cellulaires, n'importe où dans le monde, pour toutes les
					facettes de votre pool</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Participer à un pool d'une année ou un pool de type keeper sur plusieurs années, avec plusieurs paramètres et des
					archives de vos saisons antérieures</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Fini les feuilles papiers et les heures à entrer les choix ou à modifier les équipes lors d'échange. Du repêchage
					à l'entre-saison, soyez branchez!</div>
			</div>

		</div>
	</div>
	<hr id="hrAccueil2" class="w3-indigo">
	<div id="marketing3" class="w3-container">
		<div class="w3-row" style="height: 800px">
			<br>
			<br>
			<br>
			<br>

			<div class="w3-half w3-xlarge">
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Plateforme de Repêchage Interactive avec projection sur Écran de télévision intelligente</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Gestion des échanges entièrement informatisées et automatisées avec offre via site internet ou courriel</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Une expérience de Directeur général avec des possibilités de contrats, de club école, de masse salarial et plus</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Gérer vos équipes de vos ordinateurs, tablettes ou cellulaires, n'importe où dans le monde</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Participer à un pool d'une année ou un pool de type keeper sur plusieurs années</div>
				<br>
				<div class="w3-leftbar w3-border-indigo w3-container " style="height: 69px">Fini les feuilles papiers et les heures à entrer les choix ou à modifier les équipes lors d'échange</div>
			</div>
			<div class="w3-half">
				<img src="https://storage.googleapis.com/poolavie-bucket/hockeyman.png" alt="background marketing 1" style="width: 80%, max-width:500px">
			</div>

		</div>
	</div>

	<footer id="footerAccueil" class="w3-container w3-indigo w3-large w3-center">
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
	
	<jsp:directive.include file="../utils/progressBar.jsp" />

</body>
</html>