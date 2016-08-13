<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>Classement ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>

	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	
	<!-- Body de la page classement -->
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
	    
		
		<h2 class="w3-center">Mise à jour le ${Pool.derniereMAJ}</h2>
		<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Classement du pool ${Pool.poolName}</h1></caption>
			<tr class="w3-blue">
					<th>Pos</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>B</th>
					<th>P</th>
					<th>Pts</th>	
					<th>Moy</th>
					<th>1</th>
					<th>7</th>
					<th>30</th>
					<th>Diff</th>
			</tr>
			<c:set var="nombreDeTeam" value="${Classement.team_id}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDeTeam)-1}">
				
					<tr>
						<td>${pageScope.i+1}</td>
						<td>${Classement.equipe[i]}</td>
						<td>${Classement.pj[i]}</td>
						<td>${Classement.but[i]}</td>
						<td>${Classement.passe[i]}</td>
						<td>${Classement.points[i]}</td>
						<td>${Classement.hier[i]}</td>
						<td>${Classement.semaine[i]}</td>
						<td>${Classement.mois[i]}</td>
						<td>${Classement.moyenne[i]}</td>
						<td>${Classement.difference[i]}</td>
						
					</tr>
				
			</c:forEach>
		</table>
	 
	 
	<jsp:directive.include file="../utils/draftMessage.jsp" />
	
<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
<jsp:directive.include file="../utils/draftClientB.jsp" />
</c:if>
</body>
</html>