<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<script src="/js/nouvelles.js"></script>
</head>

<body>
	<div id="all">
	<!-- Header du menu principal-->
	<jsp:directive.include file="navbar_main.jsp" />
	<jsp:directive.include file="menu_secondaire.jsp" />

	<!-- Body de la page news -->

	<!-- Zone d'alerte pour les Commissaires et les Directeurs généraux -->
	
	<!-- Si commish first connexion ever -->
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==0&&Utilisateur.typeUtilisateur==1&&Pool.cycleAnnuel==1}">
		<div class="w3-container w3-section w3-red">
			<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
			<h3>Date du draft</h3>
			<p>Vous n'avez pas encore choisis la date de draft. Vous pouvez la choisir dans la section Admin.
			Lorsque la date sera choisi, les participants recevront un courriel et l'ordre de draft sera aléatoirement déterminé.
			La section Draft sera alors disponible afin de voir la liste des joueurs par position et les salaires.</p>
		</div>
	</c:if>
	

	<!-- contenu de gauche contient les 10 dernieres articles -->
	<div class="w3-twothird">
		<c:set var="nombreDeMessage" value="${Articles.titre}" />
		<c:forEach var="i" begin="0" end="${fn:length(nombreDeMessage)-1}">
			<div class="w3-row w3-margin w3-card-8">

				<div class="w3-third">
					<img src="${Articles.writerLogo[i]}" style="width: 100%; min-height: 200px">
				</div>
				<div class="w3-twothird w3-container">
					<h4>${Articles.dateCreation[i]}</h4>
					<h2>${Articles.titre[i]}</h2>
					<p>${Articles.body[i]}</p>
					<p>${Articles.writerName[i]}</p>
				</div>

			</div>
		</c:forEach>
	</div>

	<!-- contenu de droite contient des minis tableaux de stats (mini-classement,top pointeur hiere, top scoreur)  -->
	<div class="w3-third w3-padding-left w3-padding-right">

		<!-- pointeur hier soir  -->
		<table class="w3-table w3-striped w3-bordered w3-card-8 w3-margin-top">
			<caption class="w3-blue w3-xlarge">Pointeurs hier</caption>
			<tr class="w3-blue">
				<th>Pos</th>
				<th>Équipe</th>
				<th>Pts</th>
			</tr>
			<c:set var="nombreDeTeam" value="${Classement.team_id}" />
			<c:forEach var="i" begin="0" end="${(fn:length(nombreDeTeam))-1}">

				<tr>
					<td>${pageScope.i+1}</td>
					<td>${Classement.equipe[i]}</td>
					<td>${Classement.points[i]}</td>
				</tr>

			</c:forEach>
		</table>


		<!-- classement  -->
		<table class="w3-table w3-striped w3-bordered w3-card-8 w3-margin-top">
			<caption class="w3-blue w3-xlarge">Classement</caption>
			<tr class="w3-blue">
				<th>Pos</th>
				<th>Équipe</th>
				<th>Pts</th>
			</tr>
			<c:set var="nombreDeTeam" value="${Classement.team_id}" />
			<c:forEach var="i" begin="0" end="${(fn:length(nombreDeTeam))-1}">

				<tr>
					<td>${pageScope.i+1}</td>
					<td>${Classement.equipe[i]}</td>
					<td>${Classement.points[i]}</td>
				</tr>

			</c:forEach>
		</table>

		<!-- top scoreur  -->
		<table class="w3-table w3-striped w3-bordered w3-card-8 w3-margin-top">
			<caption class="w3-blue w3-xlarge">Meilleurs pointeurs</caption>
			<tr class="w3-blue">
				<th>Pos</th>
				<th>Équipe</th>
				<th>Pts</th>
			</tr>
			<c:set var="nombreDeTeam" value="${Classement.team_id}" />
			<c:forEach var="i" begin="0" end="${(fn:length(nombreDeTeam))-1}">

				<tr>
					<td>${pageScope.i+1}</td>
					<td>${Classement.equipe[i]}</td>
					<td>${Classement.points[i]}</td>
				</tr>

			</c:forEach>
		</table>






	</div>


	<a id="btnNewPost" onclick="newPost(${Utilisateur.teamId})" class="w3-btn-floating-large w3-theme-action w3-right w3-red" style="position: fixed; bottom: 25px; left: 25px;">+</a>
	
	
	<div  id="postForm" class="w3-container w3-hide w3-display-middle" >
	
	   <span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
		<div class="w3-container w3-orange">
			<h2>Votre nouvelles/vos commentaires</h2>
		</div>

		<form class="w3-container w3-card-24 w3-white" action="/nouvelles" method="post">			
							
			<p>
				<label>Titre</label><input class="w3-input w3-validate" type="text" name="titre">
			</p>

			<p>
				<label>Corps du Message</label><input class="w3-input w3-validate" type="text" name="body">
			</p>


			<button class="w3-btn w3-orange w3-xlarge">Envoyer mon post</button>
			
			<br>
			<br>
			
		</form>




	</div>


	</div>
	
	




</body>
</html>