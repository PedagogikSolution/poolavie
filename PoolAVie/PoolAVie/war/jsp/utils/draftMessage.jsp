<div id="connexionAlert" class="w3-hide w3-display-middle w3-card-24 w3-white">


	<div class="w3-orange">
		<span class="w3-padding-left w3-padding-right w3-xxlarge">Vous êtes connectez au serveur de Draft</span>
	</div>
	<div class="w3-white">
	<br>
	<form class="w3-container w3-center w3-white" method="get">
		<button class="w3-btn w3-orange w3-xlarge">Hell Yeah!</button>
	</form>
	<br>
	</div>

</div>


<div id="draftPickAlert" class="w3-hide w3-display-middle w3-card-24 w3-white">

	<div class="w3-orange">
		<span class="w3-padding-left w3-padding-right w3-xxlarge">We got a Pick!!!</span>
	</div>
	<div class="w3-white">
	<br>
	<span id="teamThatDraft"></span> a repêcher avec le <span
				id="pickNumber"></span>ième choix overall : <br> <span
				id="playerDrafted"></span> ,<span id="position"></span>, de <span
				id="teamOfPlayer"></span> <br> au salaire de <span id="salaire"></span>$
				<br>
	<form class="w3-container w3-center w3-white" method="get">
		<button class="w3-btn w3-orange w3-xlarge">C'est noté</button>
	</form>
	<br>
</div>



</div>

<div id="draftPickAlertRookie" class="w3-hide w3-display-middle w3-card-24 w3-white">

	<div class="w3-orange">
		<span class="w3-padding-left w3-padding-right w3-xxlarge">We got a Pick!!!</span>
	</div>
	<div class="w3-white">
	<br>
	<span id="teamThatDraft"></span> a repêcher avec le <span
				id="pickNumber"></span>ième choix overall : <br> <span
				id="playerDrafted"></span> ,<span id="position"></span>, de <span
				id="teamOfPlayer"></span> <br> au salaire de <span id="salaire"></span>$
				<br>
				Il place celui-ci dans son club école.
				<br>
	<form class="w3-container w3-center w3-white" method="get">
		<button class="w3-btn w3-orange w3-xlarge">Malade!!!</button>
	</form>
	<br>
</div>



</div>

<div id="draftPickAlertFinish" class="w3-hide w3-display-middle w3-card-24 w3-white">

	<div class="w3-orange">
		<span class="w3-padding-left w3-padding-right w3-xxlarge">We got a Pick!!!</span>
	</div>
	<div class="w3-white">
	<br>
	<span id="teamThatDraft"></span> a repêcher avec le <span
				id="pickNumber"></span>ième choix overall : <br> <span
				id="playerDrafted"></span> ,<span id="position"></span>, de <span
				id="teamOfPlayer"></span> <br> au salaire de <span id="salaire"></span>$
				<br>
				<p>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA
			SECTION SIGNATURE POUR DONNER DES CONTRATS À VOS JOUEURS REPÊCHÉS</p>
	<form class="w3-container w3-center w3-white" method="get">
		<button class="w3-btn w3-orange w3-xlarge">C'est noté</button>
	</form>
	<br>
	</div>



</div>

<div id="draftPickAlertRookieFinish" class="w3-hide w3-display-middle w3-card-24 w3-white">

	<div class="w3-orange">
		<span class="w3-padding-left w3-padding-right w3-xxlarge">We got a Pick!!!</span>
	</div>
	<div class="w3-white">
	<br>
	<span id="teamThatDraft"></span> a repêcher avec le <span
				id="pickNumber"></span>ième choix overall : <br> <span
				id="playerDrafted"></span> ,<span id="position"></span>, de <span
				id="teamOfPlayer"></span> <br> au salaire de <span id="salaire"></span>$
				<br>
				<p>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA
			SECTION SIGNATURE POUR DONNER DES CONTRATS À VOS JOUEURS REPÊCHÉS</p>
	<form class="w3-container w3-center w3-white" method="get">
		<button class="w3-btn w3-orange w3-xlarge">C'est noté</button>
	</form>
	<br>
	</div>



</div>




<c:if test="${sessionScope.DraftFinish==1}">
<div class="w3-orange">
		<span class="w3-padding-left w3-padding-right w3-xxlarge">Votre draft est terminé</span>
	</div>
	<div class="w3-white">
	<br>
	<form class="w3-container w3-center w3-white" method="get">
		<button class="w3-btn w3-orange w3-xlarge">Enfin!</button>
	</form>
	<br>
	</div>


</c:if>
