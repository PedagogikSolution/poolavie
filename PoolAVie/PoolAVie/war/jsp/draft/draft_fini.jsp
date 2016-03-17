<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.sql.*"%>

<%
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
		<a href="/jsp/main/main.jsp"><button class="btn_menu">Classement</button></a>
		<a href="/draft?sortby=all"><button class="btn_menu">Draft</button></a>
		<a href="/trade"><button class="btn_menu">Trade</button></a>
		<a href="/equipes"><button class="btn_menu">Team</button></a>
		<a href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<button class="btn_menu">Admin</button>
	</div>
	<div class="team_menu">
		<a href="/draft?sortby=all"><button class="btn_menu_team">ALL</button></a>
		<a href="/attaquant?sortby=all"><button class="btn_menu_team">ATT</button></a>
		<a href="/defenseur?sortby=all"><button class="btn_menu_team">DEF</button></a>
		<a href="/gardien?sortby=all"><button class="btn_menu_team">GOAL</button></a>
		<a href="/recrue?sortby=all"><button class="btn_menu_team">ROOKIE</button></a>
		<a href="/draft_order"><button class="btn_menu_team">ORDER</button></a>
	</div>
	<hr class="hr_header">
	
	<hr class="hr_header">

	<div class="main_container">
		<div class="main_content"></div>
		<div id="main_content_title_classement">FÉLICITATION</div>
		<div id="main_content_table_classement">

		<p>Ton draft est fini. Tu dois rester connecté jusqu'a la fin. En attendant, va voir ton équipe</p>
		<p><a href="/equipes"><button class="btn_menu">Voir mon équipe</button></a></p>

		
		</div>
		<div class="main_sidebar">
		
		
		
		
		</div>




		<!-- fin du main container -->
	</div>







</body>

</html>