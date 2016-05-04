<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
</head>

<body>

	<!-- Header du menu principal-->
	<jsp:directive.include file="navbar_main.jsp" />
	<jsp:directive.include file="menu_secondaire.jsp" />
	
	<!-- Body de la page news -->
	
	<!-- contient une page d'article avec possibilité d'écrire un nouvelle article en pop-up,
	 image et nom du poster et éventuelleent script auto-news avec image du pool
	 + meilleur pointeur de hier, top 10 score et mini-classement a droite  -->
	 
	 bienvenue dans la page des news du pool nommé ${Pool.poolName}.
	 <br>
	 Elle contiendra :
	 <br>
	 une page d'article avec possibilité d'écrire un nouvelle article en pop-up,
	 image et nom du poster et éventuelleent script auto-news avec image du pool
	 + meilleur pointeur de hier, top 10 score et mini-classement a droite
	 
	 
	
	
	
	
	
	
	
	

</body>
</html>