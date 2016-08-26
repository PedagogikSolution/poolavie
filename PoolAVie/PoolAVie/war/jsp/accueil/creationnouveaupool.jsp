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
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/creationpool.js"></script>
</head>

<body>

	<!-- Header avec titre et images de promo -->
	<div class="w3-container w3-blue w3-center">
		<h1>CRÉATION D'UN NOUVEAU POOL</h1>
	</div>


	<!-- Section centrale -->
	<div class="w3-container">
		<form class="w3-container" action="/CreationPool" method="post" name="creation">
			<div class="w3-third w3-container">
				<p>
					<input class="w3-input w3-validate" type="email" name="email1" placeholder="courriel joueur 1">
				</p>

				<p>
					<input class="w3-input w3-validate" type="email" name="email2" placeholder="courriel joueur 2">
				</p>

				<p>
					<input class="w3-input w3-validate" type="email" name="email3" placeholder="courriel joueur 3">
				</p>
				<p>
					<input class="w3-input w3-validate" type="email" name="email4" placeholder="courriel joueur 4">
				</p>
				<p>
					<input class="w3-input w3-validate" type="email" name="email5" placeholder="courriel joueur 5">
				</p>
				<p>
					<input class="w3-input w3-validate" type="email" name="email6" placeholder="courriel joueur 6">
				</p>
				<p>
					<input class="w3-input w3-validate" type="email" name="email7" placeholder="courriel joueur 7">
				</p>
				<p>
					<input id="j8" class="w3-input w3-validate w3-hide" type="email" name="email8" placeholder="courriel joueur 8">
				</p>
				<p>
					<input id="j9" class="w3-input w3-validate w3-hide" type="email" name="email9" placeholder="courriel joueur 9">
				</p>
				<p>
					<input id="j10" class="w3-input w3-validate w3-hide" type="email" name="email10" placeholder="courriel joueur 10">
				</p>
				<p>
					<input id="j11" class="w3-input w3-validate w3-hide" type="email" name="email11" placeholder="courriel joueur 11">
				</p>

				<br>

			</div>
			<div class="w3-third w3-container w3-center">
				<p>
					<label>Nom de l'équipe</label> <input class="w3-input" type="text" name="nomDuTeam" placeholder="Le nom de votre équipe">
				</p>
				<p>
					<label>Logo du team</label> <input class="w3-input" type="button" name="logoTeam" value="Upload a logo" disabled>
				</p>

				<button class="w3-margin-top w3-btn w3-blue">Register</button>

				<c:if test="${MessageErreurBeans.erreurFormulaireLogin!=null }">
					<p>${MessageErreurBeans.erreurFormulaireLogin}</p>
				</c:if>
			</div>
			<div class="w3-third w3-container">

				<p>Type de pool</p>
				<label>For life</label><input class="w3-input" type="radio" name="typeTrade" value="1" checked disabled> <label>One years</label><input class="w3-input w3-validate" type="radio"
					name="typeTrade" value="2" disabled> <br>

				<p>
					<label>Nom du pool</label> <input class="w3-input" type="text" name="nomDuPool" placeholder="Exemple : Challenge de Saint-Clinclin">
				</p>

				<p>
					<label>Image du Pool</label> <input class="w3-input" type="button" name="imagePool" value="Choisir un image" disabled>
				</p>

				<p>Nombre de Joueur</p>
				<label>8</label><input class="w3-input" type="radio" name="nombreEquipe" value="8" checked onclick="m8players()"> <label>9</label><input class="w3-input" type="radio" name="nombreEquipe"
					value="9" onclick="m9players()"> <label>10</label><input class="w3-input" type="radio" name="nombreEquipe" value="10" onclick="m10players()"> <label>11</label><input
					class="w3-input" type="radio" name="nombreEquipe" value="11" onclick="m11players()"> <label>12</label><input class="w3-input" type="radio" name="nombreEquipe" value="12"
					onclick="m12players()"> <br>

				<p>Option de trade</p>
				<label>All years</label><input class="w3-input" type="radio" name="typeTrade" value="1" checked> <label>Ponctual Period</label><input class="w3-input" type="radio" name="typeTrade"
					value="2"> <br>


				<p>Option de draft</p>
				<label>Live</label><input class="w3-input" type="radio" name="typeDraft" value="1" checked> <label>Manuel</label><input class="w3-input" type="radio" name="typeDraft" value="2">


			</div>
		</form>
	</div>



</body>
</html>