<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div id="connexionAlert" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Vous êtes connectez au serveur de Draft</h2>
	</div>
	<form class="w3-container w3-form w3-white w3-xlarge" method="get">

		<p>Vous pouvez maintenant effectuer vos choix de repêchage et recevoir des alertes lorsqu'un autre équipe fait un choix. Bon Draft!!!</p>




		<button class="w3-btn w3-khaki w3-xlarge">Hell Yeah!</button>

		<br> <br>

	</form>


</div>

<div id="draftPickAlert" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Nous avons un choix!</h2>
	</div>
	<form class="w3-container w3-form w3-white" method="get">

		<p class="w3-xlarge">
			<span id="teamThatDraft"></span> a repêcher avec le <span id="pickNumber"></span>ième choix overall : <br> <span id="playerDrafted"></span> ,<span id="position"></span>, de <span
				id="teamOfPlayer"></span> <br> au salaire de <span id="salaire"></span>$ <br>
		</p>




		<button onclick="document.getElementById('draftPickAlert').classList.remove('w3-show');
			document.getElementById('draftPickAlert').classList.add('w3-hide');" class="w3-btn w3-khaki w3-xlarge">C'est noté</button>

		<br> <br>

	</form>


</div>

<div id="draftPickAlertRookie" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Nous avons un choix!</h2>
	</div>
	<form class="w3-container w3-form w3-white" method="get">

		<p class="w3-xlarge">
			<span id="teamThatDraft2"></span> a repêcher avec le <span id="pickNumber2"></span>ième choix overall : <br> <span id="playerDrafted2"></span> ,<span id="position2"></span>, de <span
				id="teamOfPlayer2"></span> <br> au salaire de <span id="salaire2"></span>$ <br> <br> Il place celui-ci dans son club école.
		</p>




		<button class="w3-btn w3-khaki w3-xlarge">Malade!!!</button>

		<br> <br>

	</form>


</div>

<div id="draftPickAlertFinish" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Nous avons un choix!</h2>
	</div>
	<form class="w3-container w3-form w3-white" method="get">

		<p class="w3-xlarge">
			<span id="teamThatDraft3"></span> a repêcher avec le <span id="pickNumber3"></span>ième choix overall : <br> <span id="playerDrafted3"></span> ,<span id="position3"></span>, de <span
				id="teamOfPlayer3"></span> <br> au salaire de <span id="salaire3"></span>$ <br> <br> Il place celui-ci dans son club école.
		</p>

		<p>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA SECTION SIGNATURE POUR DONNER DES CONTRATS À VOS JOUEURS REPÊCHÉS</p>


		<button class="w3-btn w3-khaki w3-xlarge">Malade!!!</button>

		<br> <br>

	</form>


</div>

<div id="draftPickAlertRookieFinish" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Nous avons un choix!</h2>
	</div>
	<form class="w3-container w3-form w3-white" method="get">

		<p class="w3-xlarge">
			<span id="teamThatDraft4"></span> a repécher avec le <span id="pickNumber4"></span>ième choix overall : <br> <span id="playerDrafted4"></span> ,<span id="position4"></span>, de <span
				id="teamOfPlayer4"></span> <br> au salaire de <span id="salaire4"></span>$ <br> <br> Il place celui-ci dans son club école.
		</p>

		<p>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA SECTION SIGNATURE POUR DONNER DES CONTRATS À VOS JOUEURS REPÊCHÉS</p>


		<button class="w3-btn w3-khaki w3-xlarge">Malade!!!</button>

		<br> <br>

	</form>


</div>



