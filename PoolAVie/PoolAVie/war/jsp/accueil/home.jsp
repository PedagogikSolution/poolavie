<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Pool à vie</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
</head>

<body>

	<!-- Header avec titre et images de promo -->
	<header class="w3-container w3-indigo">
		<div class="w3-row">
			<div class="w3-col" style="width: 10%">
				<p class="w3-left w3-padding-left">Démo</p>
			</div>
			<div class="w3-col" style="width: 40%">
				<p class="w3-left">Beta Test</p>
			</div>
			<div class="w3-col" style="width: 40%">
				<p class="w3-right">Français</p>
			</div>
			<div class="w3-col" style="width: 10%">
				<p class="w3-right w3-padding-right">Nous Joindre</p>
			</div>
		</div>
	</header>

	<div class="w3-container w3-blue">
		
		<div class="w3-row" style="height: 800px">
		<br> <br> <br> <br>
			<div class="w3-half" >

				<div class="w3-card-24 w3-white" style="width:500px">
				<div class="w3-indigo w3-center w3-padding">
					<h1>Connectez-vous</h1>
				</div>
				<form class="w3-padding-left" action="/login" method="post">
					
					<c:if test="${MessageErreurBeans.erreurNotLogIn!=null }">
						<p class="w3-text-red w3-large">${MessageErreurBeans.erreurNotLogIn}</p>
					</c:if>
					<br>
					<label class="w3-label w3-text-indigo w3-xlarge">Nom d'utilisateur</label> <input class="w3-input w3-container w3-text-blue w3-xlarge" type="text">
					<br>
					<label class="w3-label w3-text-indigo w3-xlarge">Mot de passe</label> <input class="w3-input w3-container w3-text-blue w3-xlarge" type="password">
					<br>
					<button class="w3-btn  w3-khaki w3-xlarge w3-text-indigo">Se connecter</button>
					<br>
					<p class="w3-white">
					<a href="/recuperation">Vous avez oubliez vos identifiants?</a>
					</p>
					<br>
					<c:if test="${MessageErreurBeans.erreurFormulaireLogin!=null }">
						<p class="w3-text-red w3-large">${MessageErreurBeans.erreurFormulaireLogin}</p>
					</c:if>
					<br>
				</form>
				</div>
				
			</div>
			<div class="w3-half">
			
				<div class="w3-card-24 w3-white" style="width:500px">
				<div class="w3-indigo w3-center w3-padding">
					<h1>Créer un nouveau pool</h1>
				</div>
				<form class="w3-padding-left" action="/register" method="post">
				<br>
				<label class="w3-label w3-text-indigo w3-xlarge">Courriel</label>
				<br>
				<input class="w3-input w3-text-indigo w3-xlarge" type="email">
				<br>
				<label class="w3-label w3-text-indigo w3-xlarge">Nom d'utilisateur</label>
				<br>
				<input class="w3-input w3-text-indigo w3-xlarge" type="text">
				<br>
				<label class="w3-label w3-text-indigo w3-xlarge">Mot de passe</label>
				<br>
				<input class="w3-input w3-text-indigo w3-xlarge" type="password">
				<br>
				<button class="w3-btn w3-khaki w3-xlarge w3-text-indigo">Poursuivre à l'étape suivante</button>
				<br>
				<c:if test="${MessageErreurBeans.erreurFormulaireRegistration!=null }">
					<p class="w3-red">${MessageErreurBeans.erreurFormulaireRegistration}</p>
				</c:if>
				<br>
				</form>
				
				</div>
			</div>
		</div>

	</div>
	<div class="w3-container">

		<div class="w3-half">
			<img src="https://storage.googleapis.com/poolavie-bucket/hockeyman.png" alt="background marketing 1" style="width: 100%, max-width:600px">
		</div>
		<div class="w3-half">
			<ul>
				<li>Marketing line 1</li>
				<li>Marketing line 2</li>
				<li>Marketing line 3</li>
				<li>Marketing line 4</li>

			</ul>
		</div>

	</div>
	<div class="w3-container w3-khaki" style="height: 620px">
		<div class="w3-half">
			<img src="https://storage.googleapis.com/poolavie-bucket/hockeyman.png" alt="background marketing 1" style="width: 100%, max-width:600px">
		</div>
		<div class="w3-half">
			<ul>
				<li>Marketing line 1</li>
				<li>Marketing line 2</li>
				<li>Marketing line 3</li>
				<li>Marketing line 4</li>

			</ul>
		</div>

	</div>


</body>
</html>