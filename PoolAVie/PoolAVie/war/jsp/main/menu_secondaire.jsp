<!-- Contient la bar de navigation de l'interface principal -->

<header class="w3-container w3-light-blue">

	<ul class="w3-navbar w3-center">
		<li class="w3-xlarge"><a href="/Nouvelles">NEWS</a></li>

		<li class="w3-xlarge"><a href="/Classement">CLASSEMENT</a></li>

		<li class="w3-xlarge"><a href="/DraftCenter">DRAFT</a></li>

		<li class="w3-xlarge"><a href="/Trade">TRADE</a></li>

		<li class="w3-xlarge"><a href="/Signature">SIGNATURE</a></li>

		<li class="w3-xlarge"><a href="/Equipes">TEAM</a></li>

		<li class="w3-xlarge"><a href="/Reglements">RÈGLEMENT</a></li>

		<li class="w3-xlarge"><a href="/Archives">ARCHIVES</a></li>

		<c:if test="${Utilisateur.typeUtilisateur==1 }">
			<li class="w3-xlarge"><a href="/AdminPool">ADMIN</a></li>
		</c:if>
	</ul>



</header>