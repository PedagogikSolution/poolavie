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
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
</head>

<body>

	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	
	<!-- Body de la page classement -->
	
	    
	    
		
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
	 
	 
	
	
	
	
	
	
	
	

</body>
</html>