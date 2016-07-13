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
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_draft.jsp" />

	<!-- Body de la page draft_center -->

	<div class="w3-container">
	
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==0&&Pool.cycleAnnuel==1}">
	<!-- Si first years and avant draft sans date set-->
	<div class="w3-container w3-section w3-red">
			<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
			<h3>Date du draft</h3>
			<p>Présentement, votre manager n'a pas encore déterminé de période de draft, une fois celle-ci choisis,
			 vous trouverez ici les informations concernant votre prochain draft, tel que l'ordre de draft,
			  le nombre de temps avant la période de draft, etc.
			</p>
		</div>	
		
	</c:if>
	
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==0&&Pool.cycleAnnuel==2}">
	<!-- Si first years and avant draft avec date set-->	
		
		<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>First 2 round Draft Order first year of the pool </h1></caption>
			<tr class="w3-blue">
					<th>Overall pick no</th>
					<th>Joueur repêché</th>
					<th>Ronde</th>
					<th>Equipe</th>
					<th>Team pick no</th>
					<th>Original Team</th>	
					
			</tr>
			<c:set var="nombreDeTeam" value="${Pool.numberTeam}" />
			<c:forEach var="i" begin="0" end="${(nombreDeTeam*2)-1}">
				
					<tr>
						<td>${DraftRound.draft_pick_no[i]}</td>
						<td>${DraftRound.player_drafted[i]}</td>
						<td>${DraftRound.ronde[i]}</td>
						<td>${DraftRound.equipe[i]}</td>
						<td>${DraftRound.team_count[i]}</td>
						<td>${DraftRound.from_who[i]}</td>
												
					</tr>
				
			</c:forEach>
		</table>
		
	</c:if>
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.cycleAnnuel==3&&Pool.poolYear==0}">
		
		LE DRAFT EST EN COURS
		
	</c:if>
	
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==1&&Pool.cycleAnnuel==1}">
	<!-- Si multi years and avant draft -->
		Nous attendons la date de draft, rappel de l'ordre selon la table draft round.
		
	</c:if>
	
	


	<!-- fin du container principal -->
	</div>	

</body>
</html>