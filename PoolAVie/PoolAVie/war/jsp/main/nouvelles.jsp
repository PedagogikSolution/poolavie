<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf" %>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="/js/nouvelles.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript" src="https://talkgadget.google.com/talkgadget/channel.js"></script>

</head>

<body>

	<div id="all" class="w3-display-container">
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
		<!-- Header du menu principal-->
		<jsp:directive.include file="navbar_main.jsp" />
		<jsp:directive.include file="menu_secondaire.jsp" />
		
		<!-- section Alerte -->
		<jsp:directive.include file="../utils/messageAlerte.jsp" />

		<!-- Body de la page news -->
		<!-- -------------------- Zone d'alerte pour les Commissaires et les Directeurs généraux     --------------------------------------- -->

		<!-- Si manque des équipes -->
		<c:if test="${Pool.draftType==1&&Utilisateur.typeUtilisateur==1&&Pool.cycleAnnuel==0}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Inscription non terminé</h3>
				<p>L'ensemble des Directeurs généraux invités dans votre Pool ne sont pas encore inscrit. Aussitôt que le derniere d'entre eux sera officiellement inscris, vous pourrez choisir la date de
					votre draft et déterminer l'ordre de sélection</p>

			</div>

		</c:if>


		<!-- Si all team register but no date choose -->
		<c:if test="${Pool.draftType==1&&Utilisateur.typeUtilisateur==1&&Pool.cycleAnnuel==1}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Date du draft</h3>
				<p>Vous n'avez pas encore choisis la date de draft. Vous pouvez la choisir dans la section Admin. Lorsque la date sera choisi, les participants recevront un courriel et l'ordre de draft sera
					aléatoirement déterminé.</p>
				<p>
					<a href="/AdminDraft"> Cliquez ici pour aller directement pour vous y rendre immédiatement</a>
				</p>

			</div>

		</c:if>
		
		<c:if test="${openWaiver==1}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Période des waivers</h3>
				<p>Vous pouvez allez dans la section signature afin d'effectué votre priorité pour le draft waiver de saison</p>
				<p>
					<a href="/Signature"> Cliquez ici pour aller directement pour vous y rendre immédiatement</a>
				</p>

			</div>

		</c:if>
		



		<!-- contenu de gauche contient les 10 dernieres articles -->
		<div class="w3-twothird">
			<c:set var="nombreDeMessage" value="${Articles.titre}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDeMessage)-1}">
				<div class="w3-row w3-margin w3-card-8 w3-light-grey">

					<div class="w3-third w3-verticale w3-center w3-margin-top" style="max-width:300px">
						<img src="${Articles.writerLogo[i]}" style="width: 80%;  height:80%;">
					</div>
					<div class="w3-twothird w3-container">
						<h4>${Articles.dateCreation[i]}</h4>
						<h2>${Articles.titre[i]}</h2>
						<p>${Articles.body[i]}</p>
						<br><h5 class="w3-text-shadow">${Articles.writerName[i]}</h5>
					</div>

				</div>
			</c:forEach>
		</div>


		<!-- contenu de droite contient des minis tableaux de stats (mini-classement,top pointeur hiere, top scoreur)  -->
		<div class="w3-third w3-padding-left w3-padding-right">




			<!-- classement  -->
			<table class="w3-table w3-striped w3-bordered w3-card-8 w3-margin-top">
				<caption class="w3-blue w3-xlarge">Classement</caption>
				<tr class="w3-indigo">
					<th>Pos</th>
					<th>Équipe</th>
					<th>Pts</th>
				</tr>
				<c:set var="nombreDeTeam" value="${Classement.team_id}" />
				<c:forEach var="i" begin="0" end="${fn:length(nombreDeTeam)-1}">

					<tr>
						<td>${pageScope.i+1}</td>
						<td>${Classement.equipe[i]}</td>
						<td>${Classement.points[i]}</td>
					</tr>

				</c:forEach>
			</table>

			<!-- pointeur hier soir  -->
			<table class="w3-table w3-striped w3-bordered w3-card-8 w3-margin-top">
				<caption class="w3-blue w3-xlarge">Pointeurs hier</caption>
				<tr class="w3-indigo">
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pts</th>
				</tr>

			</table>

			<!-- top scoreur  -->
			<table class="w3-table w3-striped w3-bordered w3-card-8 w3-margin-top">
				<caption class="w3-blue w3-xlarge">Meilleurs pointeurs</caption>
				<tr class="w3-indigo">
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pts</th>
				</tr>

			</table>

		</div>


		<a id="btnNewPost" onclick="newPost()" class="w3-btn-floating-large w3-theme-action w3-right w3-red" style="position: fixed; bottom: 25px; left: 25px;">+</a>


		<div id="postForm" class="w3-card-24 w3-hide w3-display-middle" style="width:auto">

			<span onclick="closePostForm()" class="w3-padding w3-closebtn w3-text-khaki w3-hover-text-red">&times;</span>
			<div class="w3-container w3-indigo">
				<h2>Votre nouvelles/vos commentaires</h2>
			</div>

			<form class="w3-container w3-form w3-white" action="/Nouvelles" method="post" autocomplete="off">

				<p>
					<label class="w3-label w3-text-indigo w3-large">Titre</label><input id="titreNouvelle" class="w3-input w3-validate" type="text" name="titre">
				</p>

				<p>
					<label class="w3-label w3-text-indigo w3-large">Corps du Message</label><textarea id="corpsMessageNouvelle" rows="4" cols="100" class="w3-input w3-validate" name="body"></textarea>
				</p>


				<button class="w3-btn w3-khaki w3-xlarge">Envoyer mon post</button>

				<br> <br>

			</form>

		</div>




		


	</div>
	<jsp:directive.include file="../utils/draftMessage.jsp" />


		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
			<jsp:directive.include file="../utils/draftClientB.jsp" />
		</c:if>
	<script>
	document.getElementById('menuSecNews').classList.add('w3-khaki');
	</script>
</body>
</html>