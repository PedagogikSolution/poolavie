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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_draft.jsp" />

	<!-- Body de la page draft_center -->

	<div class="w3-container">
	<!-- Si all team register et pool est commencer -->
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token==null}">
			
				<div class="w3-container w3-section w3-red">
				
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>C'est l'heure du Draft </h3>
					<p>Votre draft est prêt à commencer. Cliquez ici pour vous connecter au serveur de draft</p><p><a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu</p>
						
				</div>
			
		</c:if>
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&messageErreur.erreurConnectionDraft!=null}">
			
				<div class="w3-container w3-section w3-red">
				
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>OUPS! </h3>
					<p>${messageErreur.erreurConnectionDraft}</p><p><a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu</p>
						
				</div>
			
		</c:if>
	
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
	
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear==0&&Pool.cycleAnnuel>=2}">
	<!-- Si first years and avant draft avec date set-->	
		
		<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Draft Order and Result</h1></caption>
			<tr class="w3-blue">
					<th>Overall pick no</th>
					<th>Equipe</th>
					<th>Ronde</th>
					<th>Joueur repêché</th>
					
					
					
			</tr>
			<c:set var="nombreDeTeam" value="${DraftRound.team_id}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDeTeam)-1}">
				
					<tr>
						<td>${DraftRound.draft_pick_no[i]}</td>
						<td>${DraftRound.equipe[i]}</td>
						<td>${DraftRound.ronde[i]}</td>
						<td>${DraftRound.player_drafted[i]}</td>
						
						
												
					</tr>
				
			</c:forEach>
		</table>
		
	</c:if>
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear>=1&&Pool.cycleAnnuel>=2}">
	<!-- Si first years and avant draft avec date set-->	
		
		<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Draft Order and Result</h1></caption>
			<tr class="w3-blue">
					<th>Overall pick no</th>
					<th>Joueur repêché</th>
					<th>Ronde</th>
					<th>Equipe</th>
					<th>Team pick no</th>
					<th>Original Team</th>	
					
			</tr>
			<c:set var="nombreDeTeam" value="${DraftRound.team_id}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDeTeam)-1}">
				
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
	
	
	<c:if test="${Pool.draftType==1&&Pool.poolType==1&&Pool.poolYear>=1&&Pool.cycleAnnuel==1}">
	<!-- Si multi years and avant draft -->
		Nous attendons la date de draft, rappel de l'ordre selon le classement de l'année derniere.
		
	</c:if>
	
	


	<!-- fin du container principal -->
	</div>	
	
<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
<jsp:directive.include file="../utils/draftClientB.jsp" />
</c:if>
</body>
</html>