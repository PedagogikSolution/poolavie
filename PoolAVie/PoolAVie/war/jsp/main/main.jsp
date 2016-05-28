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

	<!-- Header du menu principal-->
	<jsp:directive.include file="navbar_main.jsp" />
	<jsp:directive.include file="menu_secondaire.jsp" />

	<!-- Body de la page news -->

	<!-- contenu de gauche contient les 10 dernieres articles -->
	<div class="w3-twothird">

		<c:forEach var="i" begin="1" end="5">
		<div class="w3-row w3-margin w3-card-8">

			<div class="w3-third">
				<img src="http://payload41.cargocollective.com/1/6/222178/3122272/wbhp_logo.jpg" style="width: 100%; min-height: 200px">
			</div>
			<div class="w3-twothird w3-container">
				<h2>Bienvenue dans poolavie.ca</h2>
				<p>L'équipe de poolavie.ca vous souhaite la bienvenue dans votre nouveau pool. Nous espérons que vous apprécierez votre expérience.
				Si ce n'est pas encore fait, vous recevrez sous peu un courriel vous indiquant la date de votre premier repêchage une fois que le commissaire
				de votre pool l'aura déterminer. Il ne reste qu'à vous souhaitez la meilleure des chances!!! 
				</p>
				<p>Les créateurs de PoolAVie.ca</p>
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


	<a id="btnNewPost" onclick="newPost(${Utilisateur.teamId})" class="w3-btn-floating-large w3-theme-action w3-right w3-red" style="position:fixed;bottom:25px;left:25px;">+</a>







</body>
</html>