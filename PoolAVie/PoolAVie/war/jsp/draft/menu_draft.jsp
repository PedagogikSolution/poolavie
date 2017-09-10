<!-- Contient la bar de navigation de l'interface principal -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<header class="w3-container w3-khaki">

		<ul class="w3-navbar w3-center">
			<li id="menuSecDraftCenter" class="w3-xlarge w3-text-indigo"><a href="/DraftCenter">Draft center</a></li>
		
			<li id="menuSecDraftResult" class="w3-xlarge w3-text-indigo"><a href="/DraftResult">Résultats</a></li>
		
			<li id="menuSecDraftTous" class="w3-xlarge w3-text-indigo"><a href="/DraftPlayers?seg=all&sort=pts&from=menu">Tous</a></li>
		
			<li id="menuSecDraftAttaquant" class="w3-xlarge w3-text-indigo"><a href="/DraftPlayers?seg=foward&sort=pts&from=menu">Attaquant</a></li>
		
			<li id="menuSecDraftDefenseur" class="w3-xlarge w3-text-indigo"><a href="/DraftPlayers?seg=defenseur&sort=pts&from=menu">Defenseur</a></li>
		
			<li id="menuSecDraftGardien" class="w3-xlarge w3-text-indigo"><a href="/DraftPlayers?seg=goaler&sort=pts&from=menu">Gardien</a></li>
		
			<li id="menuSecDraftRecrue" class="w3-xlarge w3-text-indigo"><a href="/DraftPlayers?seg=rookie&sort=pts&from=menu">Recrue</a></li>
		</ul>

		

</header>