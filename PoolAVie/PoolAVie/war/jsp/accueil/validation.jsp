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
	<div class="w3-container w3-blue w3-center">

		<h1>REGISTER</h1>

	</div>
	<br>
	<br>
	<br>
	<br>
	<c:if test="${MessageErreurBeans.erreurCodeValidationType2!=null }">
		<p><h2 class="w3-container">${MessageErreurBeans.erreurCodeValidationType2}</h2></p>

	</c:if>

	<div class="w3-container">
		<div class="w3-container w3-blue">
			<h2>ENTREZ VOTRE CODE DE VALIDATION RECU PAR COURRIEL</h2>
		</div>

		<form class="w3-container w3-card-24 w3-white" action="/validation" method="post">
			<input type="hidden" name="formulaire" value="1">
			<p>
				<label>Code de Validation</label> <input class="w3-input w3-validate" type="text" name="validation">
			</p>


			<button class="w3-btn w3-blue">Valider</button>
			<br> <br>
			<c:if test="${MessageErreurBeans.erreurCodeValidationType1!=null }">
				<p>${MessageErreurBeans.erreurCodeValidationType1}</p>

			</c:if>


		</form>
		<br> <br> <br>

		
			<div class="w3-container w3-blue">
			<h2>Entrez le courriel associé à votre compte pour recevoir un nouveau Code</h2>
		</div>
			<form class="w3-container w3-card-24 w3-white" action="/validation" method="post">
				<input type="hidden" name="formulaire" value="2">
				<p>
					<label>Courriel</label> <input class="w3-input w3-validate" type="text" name="email">
				</p>
				<button class="w3-btn w3-blue">Envoyer le courriel</button>
<br>
<br>


			</form>




	</div>

</body>
</html>