<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<br>
	<div class="w3-card-24 w3-white" style="width: 50%; height: 50%; margin-left: auto; margin-right: auto">
		<div class="w3-indigo w3-center w3-padding">
			<h3>Réinitialisez le mot de passe du compte que vous avez oubliez</h3>
			<h4>(Et noter votre nom d'utilisateur en même temps)</h4>
		</div>

		
			<table class="w3-table w3-content w3-bordered w3-margin-top w3-large" style="width: 90%">

				<tr class="w3-indigo">
					<th>Nom d'utilisateur</th>
					<th>Nouveau Mot de passe</th>
					<th>Action</th>

				</tr>
				<c:set var="nombreDeCompte" value="${Recuperation.username}" />
				<c:forEach var="i" begin="0" end="${fn:length(nombreDeCompte)-1}">
				
					<tr>
					
						<td>${Recuperation.username[i]}</td>
						<td>
							<input class="w3-input w3-container w3-text-blue  w3-validate" form="ChangePasswordForm${i}" type="password" name="password">
						</td>
						<td>
							<form id="ChangePasswordForm${i}" class="w3-padding-left" action="/recuperation" method="post">
							<input type="hidden" name="username" value="${Recuperation.username[i]}">
							
							<button class="w3-btn w3-khaki w3-large w3-text-indigo">Changer</button>
							</form>
						</td>
					
					</tr>
				
				</c:forEach>
				
			</table>
			<br>
			<br>
		
	</div>


</body>
</html>