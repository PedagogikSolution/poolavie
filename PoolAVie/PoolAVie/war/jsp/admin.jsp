<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String teamId = session.getAttribute("mTeamId").toString();
	int teamId2 = Integer.parseInt(teamId);

	String mLogoId = null;
	String mFirstTeamName = null;

	switch (teamId2) {
	case 0:
		mLogoId = "los_angeles.png";
		mFirstTeamName = "Kings de";
		break;
	case 1:
		mLogoId = "detroit.png";
		mFirstTeamName = "Red Wings de";
		break;
	case 2:
		mLogoId = "montreal.png";
		mFirstTeamName = "Canadiens de";
		break;
	case 3:
		mLogoId = "chicago.png";
		mFirstTeamName = "Blackhawks de";
		break;
	case 4:
		mLogoId = "new_york.png";
		mFirstTeamName = "Rangers de";
		break;
	case 5:
		mLogoId = "philadelphie.png";
		mFirstTeamName = "Flyers de";
		break;
	case 6:
		mLogoId = "toronto.png";
		mFirstTeamName = "Maple Leafs de";
		break;
	case 7:
		mLogoId = "st_louis.png";
		mFirstTeamName = "Blues de";
		break;
	case 8:
		mLogoId = "boston.png";
		mFirstTeamName = "Bruins de";
		break;
	case 9:
		mLogoId = "pittsburgh.png";
		mFirstTeamName = "Penguins de";
		break;

	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />

</head>
<body>
	<div class="main_navbar">

	  	<div id="logo_main">
	
			<img alt="logo" src="../images/<%=mLogoId%>" width="150px"
				height="150px">
	

		</div>
		<div id="welcome_main">
			Bienvenue <%=session.getAttribute("mUsername")%>
			<br> dans ta section de directeur général <br> des
			<%=mFirstTeamName%>
			<%=session.getAttribute("mTeamName")%>
		</div>

		<div id="btn_sign_out">
			<a href="/deconnexion"><button>Sign out</button></a>
		</div>
		<!--  fin de la main navbar -->
	</div>
	<hr class="hr_header">
	<div class="main_menu">
		<a href="/jsp/main.jsp"><button class="btn_menu">Classement</button></a>
		<a href="/draft?sortby=all"><button class="btn_menu">Draft</button></a>
		<a href="/trade"><button class="btn_menu">Trade</button></a>
		<a href="/equipes"><button class="btn_menu">Team</button></a>
		<a href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<a href="/admin"><button class="btn_menu">Admin</button></a>
	</div>
	<hr class="hr_header">
	<div class="main_container">
	<br>
	<br>
	<form action="/admin" method="post">	
	<input type="hidden" value="1" name="admin"/>
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 5.1 : Archivages des tables Classement,Draft Round et Trade Made"/>	
	</form>
	<br>
	<br>
	<form action="/admin" method="post">
	<input type="hidden" value="2" name="admin"/>	
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 5.2 : Vidage des tables Classement,Draft Round et Trade Made"/>	
	</form>
	<form action="/admin" method="post">	
	<input type="hidden" value="3" name="admin"/>
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 5.3 : Archivages de la table players where equipe is not null"/>	
	</form>
	<br>
	<br>
	<form action="/admin" method="post">
	<input type="hidden" value="4" name="admin"/>	
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 5.4 : Vidage de l de la table players pour les sans équipes"/>	
	</form>
	<form action="/admin" method="post">
	<input type="hidden" value="5" name="admin"/>	
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 5.5 : Update du champs take_proj"/>	
	</form>
	<form action="/admin" method="post">	
	<input type="hidden" value="6" name="admin"/>
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 6.5 : MAJ table equipe (budget et effectif) pour changement d'année"/>	
	</form>
	<br>
	<br>
	<form action="/admin" method="post">
	<input type="hidden" value="7" name="admin"/>	
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 8.1 : Drop des joueurs non signé"/>	
	</form>
	<form action="/admin" method="post">	
	<input type="hidden" value="8" name="admin"/>
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 9 : Ouvrir la période des échanges"/>	
	</form>
	<br>
	<br>
	<form action="/admin" method="post">
	<input type="hidden" value="9" name="admin"/>	
	<input type="submit" class="btn_menu" style="width:100%" value="Étape 13 : Préparation finale du draft"/>	
	</form>
	
		

		<!-- fin du main container -->
	</div>






</body>
</html>