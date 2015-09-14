<%@ page language="java" import="java.sql.*"%>

<%
ResultSet rs2 = (ResultSet) request.getAttribute("all_possible_signature");
String teamId2 = session.getAttribute("mTeamId").toString();
int teamId = Integer.parseInt(teamId2);
String mLogoId = null;
String mFirstTeamName = null;

switch (teamId) {
	case 0 :
mLogoId = "los_angeles.png";
mFirstTeamName = "Kings de";
break;
	case 1 :
mLogoId = "detroit.png";
mFirstTeamName = "Red Wings de";
break;
	case 2 :
mLogoId = "montreal.png";
mFirstTeamName = "Canadiens de";
break;
	case 3 :
mLogoId = "chicago.png";
mFirstTeamName = "Blackhawks de";
break;
	case 4 :
mLogoId = "new_york.png";
mFirstTeamName = "Rangers de";
break;
	case 5 :
mLogoId = "philadelphie.png";
mFirstTeamName = "Flyers de";
break;
	case 6 :
mLogoId = "toronto.png";
mFirstTeamName = "Maple Leafs de";
break;
	case 7 :
mLogoId = "st_louis.png";
mFirstTeamName = "Blues de";
break;
	case 8 :
mLogoId = "boston.png";
mFirstTeamName = "Bruins de";
break;
	case 9 :
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
			Bienvenue
			<%=session.getAttribute("mUsername")%>
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
		<button class="btn_menu">Admin</button>
	</div>
	
	<hr class="hr_header">
	
	<hr class="hr_header">

	<div class="main_container">
		<div class="main_content"></div>
		<div id="main_content_title_classement">MY SIGNATURE AFTER DRAFT</div>
		<div id="main_content_table_classement">
		
		AUCUNE ACTION POSSIBLE SUR VOTRE ÉQUIPE À CETTE DATE
		<br>
		Calendrier des actions 2015-2016 :
		<br>12 au 15 septembre : Période de rachat de contrat avec argent restant de l'année dernière avec moitié prix des année restante en comptant l'année 2014-2015.
		 De plus, durant cette période, vous pouvez descendre un joueur de votre club ferme dans votre club école pour le coût de 1 Millions.
		 Si le joueur descendu est un joueur B, il sera augmenter de 1 Millions dans son salaire conformément au règle des J/A obtenu.
		<br>16 septembre : fin de la saison 2014-2015. Reset des finances des équipes. Mise à jour des salaires des joueurs avec projections pour les moins de 50 et recrues.
		<br>16 septembre : Début de la saison 2015-2016.
		<br>17 au 21 septembre : Période de signature des joueurs en renouvellement de contrat ou recu via échange (A et B pour année).
		<br>22 au 27 septembre : Période d'échange d'été
		<br>29 au 30 septembre : rachat après échange 
		<br>1 octobre au 5 octobre : monter ou dropper les recrues
		<br>
		
	
		</div>
		<div class="main_sidebar">
		
		
		</div>




		<!-- fin du main container -->
	</div>







</body>

</html>