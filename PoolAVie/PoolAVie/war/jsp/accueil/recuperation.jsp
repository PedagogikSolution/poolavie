<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Pool à vie</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
</head>

<body class="w3-light-grey">

	<!-- Header avec titre et images de promo -->
	<header class="w3-container w3-blue">

		<ul class="w3-navbar w3-third">
			<li class="w3-left w3-xlarge"><a href="/">Retour à l'Accueil</a></li>
		</ul>

		<h1 class="w3-third w3-center w3-margin-0">RÉCUPÉRATION DES IDENTIFIANTS</h1>

		<ul class="w3-navbar w3-third">
			<li class="w3-right  w3-xlarge"><a href="/login">Se connecter</a></li>
			<li class="w3-right  w3-xlarge"><a href="/register">Créer un compte</a></li>
		</ul>

	</header>

	<br>
	<br>
	<br>
	<br>
	<div class="w3-container ">
		<div class="w3-container w3-blue">
			<h2>Récupérez vos identifiants</h2>
		</div>

		<form class="w3-container w3-card-24 w3-white" action="/recuperation" method="post">

			<p>
				<label>Courriel</label> <input class="w3-input w3-validate" type="text" name="email">
			</p>

			
			<button class="w3-btn w3-blue w3-xlarge">Envoyer un courriel de récupération</button>
	
			<br>
			<br>

			<c:if test="${MessageErreurBeans.erreurFormulaireRecuperation!=null }">
				<p>${MessageErreurBeans.erreurFormulaireRecuperation}</p>
			</c:if>

		</form>




	</div>
</body>
</html>