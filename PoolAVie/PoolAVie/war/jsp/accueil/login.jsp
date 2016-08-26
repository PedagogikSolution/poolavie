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

<body class="w3-light-grey">

	<!-- Header avec titre et images de promo -->
	<header class="w3-container w3-blue">

		<ul class="w3-navbar w3-third">
			<li class="w3-left w3-xlarge"><a href="/">Retour à l'Accueil</a></li>
		</ul>

		<h1 class="w3-third w3-center w3-margin-0">SE CONNECTER</h1>

		<ul class="w3-navbar w3-third">

			<li class="w3-right  w3-xlarge"><a href="/register">Créer un compte</a></li>
		</ul>

	</header>

	<br>
	<br>
	<br>
	<br>
	<div class="w3-container ">
		<div class="w3-container w3-blue">
			<h2>Se connecter</h2>
		</div>

		<form class="w3-container w3-card-24 w3-white" action="/login" method="post">
			
			
				<c:if test="${MessageErreurBeans.erreurNotLogIn!=null }">
					<p class="w3-container w3-red w3-large">${MessageErreurBeans.erreurNotLogIn}</p>
				</c:if>
			
			
			<p>
				<label>Nom d'utilisateur</label> <input class="w3-input w3-validate" type="text" name="username">
			</p>

			<p>
				<label>Mot de Passe</label> <input class="w3-input w3-validate" type="password" name="password">
			</p>


			<button class="w3-btn w3-blue w3-xlarge">Se connecter</button>
			<p>
				<a href="/recuperation">Vous avez oubliez vos identifiants?</a>
			</p>
			<br>

			<c:if test="${MessageErreurBeans.erreurFormulaireLogin!=null }">
				<p>${MessageErreurBeans.erreurFormulaireLogin}</p>
			</c:if>

		</form>




	</div>
</body>
</html>