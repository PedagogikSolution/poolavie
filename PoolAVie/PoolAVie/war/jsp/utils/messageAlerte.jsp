<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token==null}">
	<div class="w3-container w3-section w3-red">
		<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
		<h4>C'est l'heure du Draft</h4>
		<p>
			Votre draft est pr�t � commencer.
			<a href="/DraftCenter"> Cliquez ici pour vous connecter</a>
			ou rendez-vous dans la section Draft
		</p>
	</div>
</c:if>
<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&messageErreur.erreurConnectionDraft!=null}">
	<div class="w3-container w3-section w3-red">

		<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
		<h4>OUPS!</h4>
		<p>${messageErreur.erreurConnectionDraft}</p>
		<p>
			<a href="/DraftCenter"> Cliquez ici pour vous connecter</a>
			ou rendez-vous dans la section Draft
		</p>
	</div>
</c:if>
<c:if test="${Utilisateur.teamId==currentPicker&&Pool.cycleAnnuel==3}">
	<div class="w3-container w3-section w3-red">
		<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
		<h3>C'EST VOTRE TOUR DE DRAFT !!!</h3>
	</div>
</c:if>

<c:if test="${sessionScope.DraftFinish==1&&Pool.cycleAnnuel==3}">



	<div class="w3-container w3-section w3-red">

		<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
		<h3>Votre draft est termin�</h3>
		<p>Vous pouvez maintenant rester connecter pour suivre la fin du draft ou vous d�connecter et revenir signer vos contrats de fin de draft aussit�t que celui-ci est termin� dans la section
			signature.</p>

	</div>



</c:if>