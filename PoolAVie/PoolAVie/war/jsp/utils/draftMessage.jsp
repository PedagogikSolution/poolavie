<div id="connexionAlert" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Vous �tes connectez au serveur de Draft</h2>
	</div>
	<form class="w3-container w3-form w3-white w3-xlarge" method="get">

		<p>Vous pouvez maintenant effectuer vos choix de rep�chage et recevoir des alertes lorsqu'un autre �quipe fait un choix. Bon Draft!!!</p>




		<button class="w3-btn w3-khaki w3-xlarge">Hell Yeah!</button>

		<br> <br>

	</form>


</div>

<div id="draftPickAlert" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Nous avons un choix!</h2>
	</div>
	<form class="w3-container w3-form w3-white" method="get">

		<p class="w3-xxlarge">
			<span id="teamThatDraft"></span> a rep�cher avec le <span id="pickNumber"></span>i�me choix overall : <br> <span id="playerDrafted"></span> ,<span id="position"></span>, de <span
				id="teamOfPlayer"></span> <br> au salaire de <span id="salaire"></span>$ <br>
		</p>




		<button class="w3-btn w3-khaki w3-xlarge">C'est not�</button>

		<br> <br>

	</form>


</div>

<div id="draftPickAlertRookie" class="w3-card-24 w3-hide w3-display-middle" style="width: auto">

	<div class="w3-container w3-indigo">
		<h2>Nous avons un choix!</h2>
	</div>
	<form class="w3-container w3-form w3-white" method="get">

		<p class="w3-xlarge">
			<span id="teamThatDraft2"></span> a rep�cher avec le <span id="pickNumber2"></span>i�me choix overall : <br> <span id="playerDrafted2"></span> ,<span id="position2"></span>, de <span
				id="teamOfPlayer2"></span> <br> au salaire de <span id="salaire2"></span>$ <br> <br> Il place celui-ci dans son club �cole.
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
			<span id="teamThatDraft3"></span> a rep�cher avec le <span id="pickNumber3"></span>i�me choix overall : <br> <span id="playerDrafted3"></span> ,<span id="position3"></span>, de <span
				id="teamOfPlayer3"></span> <br> au salaire de <span id="salaire3"></span>$ <br> <br> Il place celui-ci dans son club �cole.
		</p>

		<p>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA SECTION SIGNATURE POUR DONNER DES CONTRATS � VOS JOUEURS REP�CH�S</p>


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
			<span id="teamThatDraft4"></span> a rep�cher avec le <span id="pickNumber4"></span>i�me choix overall : <br> <span id="playerDrafted4"></span> ,<span id="position4"></span>, de <span
				id="teamOfPlayer4"></span> <br> au salaire de <span id="salaire4"></span>$ <br> <br> Il place celui-ci dans son club �cole.
		</p>

		<p>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA SECTION SIGNATURE POUR DONNER DES CONTRATS � VOS JOUEURS REP�CH�S</p>


		<button class="w3-btn w3-khaki w3-xlarge">Malade!!!</button>

		<br> <br>

	</form>


</div>


<c:if test="${sessionScope.DraftFinish==1&&Pool.cycleAnnuel==3}">

	<div id="connexionAlert" class="w3-card-24 w3-display-middle" style="width: auto">

		<div class="w3-container w3-section w3-red">

			<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
			<h3>Votre draft est termin�</h3>
			<p>Vous pouvez maintenant rester connecter pour suivre la fin du draft ou vous d�connecter et revenir signer vos contrats de fin de draft aussit�t que celui-ci est termin� dans la section
				signature.</p>

		</div>


	</div>
</c:if>
