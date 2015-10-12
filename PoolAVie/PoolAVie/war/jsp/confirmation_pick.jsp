<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.sql.*"%>

<%
String draft_player_id,team_id,team,nom,salaire_draft,can_be_rookie2,draft_pick_no,position;
int can_be_rookie;
draft_player_id= request.getParameter("draft_player_id");
team_id= request.getParameter("team_id");
can_be_rookie2= request.getParameter("can_be_rookie");
can_be_rookie = Integer.parseInt(can_be_rookie2);
team= request.getParameter("team");
nom= request.getParameter("nom");
salaire_draft= request.getParameter("salaire");
draft_pick_no = request.getParameter("draft_pick_no");
position = request.getParameter("position");


Object messageRecuFromServlet = request.getAttribute("mTeam");

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
			<%
				if (messageRecuFromServlet != null) {
			%>
			<img alt="logo" src="images/<%=mLogoId%>" width="150px"
				height="150px">
			<%
				} else {
			%>
			<img alt="logo" src="../images/<%=mLogoId%>" width="150px"
				height="150px">
			<%
				}
			%>

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
		<div id="main_content_title_classement">CONFIRMATION PICK</div>
		<div id="main_content_table_classement">

		

		<%
			if (can_be_rookie==0) {
		%>

		<p>
			Vous avez choisis de repecher
			<%=nom%>
			de
			<%=team%>
			au salaire de
			<%=salaire_draft%></p>
		<br>
		<form action="/pick_confirmer" method="post" onsubmit="return checkForm(this);">
			<input type="hidden" name="is_rookie" value="0"> <input
				type="hidden" name="draft_player_id" value="<%=draft_player_id%>">
			<input type="hidden" name="team_id" value="<%=team_id%>">
			<input type="hidden" name="position" value="<%=position%>">
			<input type="hidden" name="nom" value="<%=nom%>"> 
			<input type="hidden" name="salaire" value="<%=salaire_draft%>"> 
			<input type="hidden" name="draft_pick_no" value="<%=draft_pick_no%>">  
			
			
			<input
				type="submit" name="myButton" value="JE CONFIRME MON CHOIX">
		</form>
		<%
			} else {
		%>

		<p>
			Vous avez choisis de repecher
			<%=nom%>
			de
			<%=team%>
			au salaire de
			<%=salaire_draft%></p>
		<br>
		<p>Je place ce joueur dans mon équipe école</p>
		<form action="/pick_confirmer" method="post" onsubmit="return checkForm(this);">
			<input type="hidden" name="is_rookie" value="1"> <input
				type="hidden" name="draft_player_id" value="<%=draft_player_id%>">
			<input type="hidden" name="team_id" value="<%=team_id%>">
			<input type="hidden" name="position" value="<%=position%>">
			<input type="hidden" name="nom" value="<%=nom%>"> 
			<input type="hidden" name="salaire" value="<%=salaire_draft%>"> 
			<input type="hidden" name="draft_pick_no" value="<%=draft_pick_no%>">  <input
				type="submit" value="DANS MES ROOKIES">
		</form>
		<p>Je place ce joueur dans mon équipe régulière</p>
		<form action="/pick_confirmer" method="post">
			<input type="hidden" name="is_rookie" value="0"> <input
				type="hidden" name="draft_player_id" value="<%=draft_player_id%>">
			<input type="hidden" name="team_id" value="<%=team_id%>">
			<input type="hidden" name="position" value="<%=position%>">
			<input type="hidden" name="nom" value="<%=nom%>"> 
			<input type="hidden" name="salaire" value="<%=salaire_draft%>"> 
			<input type="hidden" name="draft_pick_no" value="<%=draft_pick_no%>">  <input
				type="submit" name="myButton" value="DANS MON TEAM">
		</form>



		<%
			}
		%>
		</div>
		<div class="main_sidebar">
		
		
		
		
		</div>




		<!-- fin du main container -->
	</div>







</body>
<script type="text/javascript">

  function checkForm(form) // Submit button clicked
  {
    //
    // check form input values
    //

    form.myButton.disabled = true;
    form.myButton.value = "Please wait...stop clicking bastard";
    return true;
  }

  

</script>

</html>