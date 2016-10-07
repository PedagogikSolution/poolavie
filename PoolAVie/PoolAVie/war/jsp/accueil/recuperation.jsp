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
	<header class="w3-container w3-indigo w3-large w3-center">
		<ul class="w3-navbar w3-indigo  w3-xlarge">
			<li class="w3-left"><a href="#">Démo</a></li>
			<li class="w3-left"><a href="#">Beta Test</a></li>	
			<li class="w3-navitem w3-twothird w3-indigo w3-xlarge w3-padding-0 w3-hide-small w3-hide-medium"><a href="/jsp/accueil/home.jsp">Pool à vie</a></li>	
			<li class="w3-rigth"><a href="#">Français</a></li>
			<li class="w3-rigth"><a href="#">Nous Joindre</a></li>
		</ul>

		
	</header>
	<br>
	<div class="w3-card-24 w3-white" style="width: 40%; height: 40%; margin-left: auto; margin-right: auto">
		<div class="w3-indigo w3-center w3-padding">
			<h3>Récupérez vos identifiants</h3>
		</div>
		<form class="w3-padding-left" action="/recuperation" method="post">

			<c:if test="${MessageErreurBeans.erreurNotLogIn!=null }">
				<p class="w3-text-red ">${MessageErreurBeans.erreurNotLogIn}</p>
			</c:if>
			<br> <label class="w3-label w3-text-indigo w3-large">Courriel</label>
			<input class="w3-input  w3-container w3-text-blue w3-validate" type="email" name="email"> <br>
			<button class="w3-btn  w3-khaki w3-large w3-text-indigo">Envoyer un courriel de récupération</button>
			<br>
				<c:if test="${MessageErreurBeans.erreurFormulaireRecuperation!=null }">
				<p>${MessageErreurBeans.erreurFormulaireRecuperation}</p>
			</c:if>
			<br>
		</form>
	</div>
	
	
</body>
</html>