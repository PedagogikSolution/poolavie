<!-- Contient la bar de navigation de l'interface principal -->

<header class="w3-container w3-indigo w3-center">
<c:if test="${Utilisateur.typeUtilisateur==1 }">
	<ul class="w3-navbar w3-center w3-mobile">
		<li id="menuSecNews" class="w3-xlarge"><a href="/Nouvelles">NEWS</a></li>

		<li id="menuSecStand" class="w3-xlarge"><a href="/Classement">CLASSEMENT</a></li>

		<li id="menuSecDraft" class="w3-xlarge"><a href="/DraftCenter">DRAFT</a></li>

		<li id="menuSecTrade" class="w3-xlarge"><a href="/Trade">TRADE</a></li>

		<li id="menuSecSign" class="w3-xlarge"><a href="/Signature?from=1">GESTION</a></li>

		<li id="menuSecTeam" class="w3-xlarge"><a href="/Equipes">TEAM</a></li>

		<li id="menuSecRule" class="w3-xlarge"><a href="/Reglements">RÈGLEMENTS</a></li>

		<li id="menuSecArchive" class="w3-xlarge"><a href="/Archives">ARCHIVES</a></li>

		<li id="menuSecAdmin" class="w3-xlarge"><a href="/AdminPool">ADMIN</a></li>
		
	</ul>
</c:if>
<c:if test="${Utilisateur.typeUtilisateur==2 }">
	<ul class="w3-navbar w3-center w3-mobile">
		<li id="menuSecNews" class="w3-xlarge"><a href="/Nouvelles">NEWS</a></li>

		<li id="menuSecStand" class="w3-xlarge"><a href="/Classement">CLASSEMENT</a></li>

		<li id="menuSecDraft" class="w3-xlarge"><a href="/DraftCenter">DRAFT</a></li>

		<li id="menuSecTrade" class="w3-xlarge"><a href="/Trade">TRADE</a></li>

		<li id="menuSecSign" class="w3-xlarge"><a href="/Signature?from=1">GESTION</a></li>

		<li id="menuSecTeam" class="w3-xlarge"><a href="/Equipes">TEAM</a></li>

		<li id="menuSecRule" class="w3-xlarge"><a href="/Reglements">RÈGLEMENTS</a></li>

		<li id="menuSecArchive" class="w3-xlarge"><a href="/Archives">ARCHIVES</a></li>

	</ul>
</c:if>


</header>