<!-- Contient la bar de navigation de l'interface principal -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<!-- Header avec titre et images de promo -->
<header class="w3-center" >
	<ul class="w3-navbar w3-center w3-blue w3-xlarge">
		<li class="w3-navitem w3-left" style="width:4.99%"><img src="${Utilisateur.urlTeamLogo}" style="width:56px; height:56px;"></li>
		<li class="w3-padding-top w3-xlarge w3-left" style="width:19.99%;text-align: left;"><a href="#">${Utilisateur.teamName}</a></li>
		<li class="w3-padding-top w3-xxlarge w3-navitem" style="width:60.99%"><b class="w3-margin-top" style="padding-right:128px"><i>${Pool.poolName }</i></b></li>
		
		<li class="w3-padding-top w3-xlarge w3-right" style="width:6.99%"><a href="/deconnexion"><i class="material-icons w3-xxlarge">cancel</i></a></li>
		<li class="w3-padding-top w3-xlarge w3-right" style="width:6.99%"><a href="/MonCompte"><i id="menuNavBarUserAccount" class="material-icons w3-xxlarge">settings</i></a></li>
	</ul>

</header>