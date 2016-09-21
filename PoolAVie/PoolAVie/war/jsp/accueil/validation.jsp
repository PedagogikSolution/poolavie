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
	<!-- SEction centrale -->
	<c:if test="${not empty mauvaiseDate}">
	<h1>${mauvaisDate.erreurFormulaireRegistration}</h1>
	</c:if>
	<c:if test="${empty mauvaiseDate}">
	<div id="marketing1" class="w3-container">
	<br><br><br><br><br><br><br><br><br><br><br><br>
		<div class="w3-card-24 w3-white" style="width: 30%; height: 20%; margin-left: auto; margin-right: auto">
			<div class="w3-indigo w3-center w3-padding">
				<h3>Validez votre compte</h3>
			</div>
			<form class="w3-padding-left" action="/validation" method="post">
				<input type="hidden" name="formulaire" value="1">
				<br>
				<label class="w3-label w3-text-indigo w3-xlarge">Code de Validation</label>
				<input class="w3-input w3-container w3-text-blue w3-validate w3-xlarge" type="text" name="validation">
				<br>
				<button class="w3-btn  w3-khaki w3-xlarge w3-text-indigo">Valider mon code</button>
				<br><br>
				<p class="w3-white">
					<a href="/recuperation">Vous n'avez pas reçu votre code</a>
				</p>
				<br>
				<c:if test="${MessageErreurBeans.erreurCodeValidationType1!=null }">
					<p>${MessageErreurBeans.erreurCodeValidationType1}</p>

				</c:if>
				
			</form>
		</div>
		<div class="w3-card-24 w3-white w3-hide" style="width: 40%; height: 20%; margin-left: auto; margin-right: auto">
			<div class="w3-indigo w3-center w3-padding">
				<h3>Connectez-vous</h3>
			</div>
			<form class="w3-padding-left" action="/login" method="post">
				<input type="hidden" name="formulaire" value="2">
				<br>
				<label class="w3-label w3-text-indigo w3-large">Courriel</label>
				<input class="w3-input w3-container w3-text-blue w3-validate" type="email" name="email">
				<br>
				<button class="w3-btn  w3-khaki w3-large w3-text-indigo">M'envoyer un nouveau code</button>
				<br>
				<p class="w3-white">
					<a href="/recuperation">Vous n'avez pas reçu votre code</a>
				</p>
				<br>
				<c:if test="${MessageErreurBeans.erreurCodeValidationType1!=null }">
					<p>${MessageErreurBeans.erreurCodeValidationType1}</p>

				</c:if>
				<br>
			</form>
		</div>


		<!-- Section message -->
		<div>
			<c:if test="${MessageErreurBeans.erreurCodeValidationType2!=null }">
				<p>${MessageErreurBeans.erreurCodeValidationType2}</p>

			</c:if>
		</div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	</div>
	</c:if>
	<footer class="w3-container w3-indigo w3-large w3-center w3-bottom">
		<p>Solution Pedagogik inc. © 2016. Tous droits réservés</p>
	</footer>

	

</body>
</html>