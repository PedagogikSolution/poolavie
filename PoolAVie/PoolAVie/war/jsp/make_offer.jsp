<%@ page language="java" import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>

<%
	String teamId2 = session.getAttribute("mTeamId").toString();
	int teamId = Integer.parseInt(teamId2);
	String mLogoId = null;
	String mFirstTeamName = null;

	switch (teamId) {
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
	Boolean erreur = false;
	erreur = (Boolean) request.getAttribute("erreur");
	
	ResultSet rs = (ResultSet) request.getAttribute("my_offer");	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript" src="js/make_offer.js"></script>
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
		<a href="/trade"><button class="btn_menu">Trade</button></a> <a
			href="/equipes"><button class="btn_menu">Team</button></a>
		<a href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<button class="btn_menu">Admin</button>
	</div>
	<div class="trade_menu">
	<c:if test="${sessionScope.periode_echange_ouverte==1}">
		<a href="/trade"><button class="btn_menu_trade">RECEIVED</button></a>
		<a href="/make_offer"><button class="btn_menu_trade">MAKE ONE</button></a>
		</c:if>
		<a href="/my_trade"><button class="btn_menu_trade">MY TRADE</button></a>
		<a href="/all_trade"><button class="btn_menu_trade">ALL	TRADE</button></a>
	</div>
	<hr class="hr_header">


	<div class="main_container">
		<div class="main_content">
			<div id="main_content_title_classement">
			LA LISTE DE MES OFFRES
			
			
			</div>
			
			<div id="main_content_table_classement">
			<table>
			<tr>
			<th>offre #</th>
			<th>équipe</th>
			<th>détail</th>
			<th>annuler</th>
			</tr>
			
			<% while (rs.next())  {
			String team_name=null;
			int trade_id = rs.getInt("_id");
			String team_that_recevide_offer = rs.getString("team_2");
			int team_that_recevide_offer2 = Integer.parseInt(team_that_recevide_offer);
			switch (team_that_recevide_offer2) {
			case 0: team_name="Los Angeles";
				break;
			case 1: team_name="Detroit";
			break;
			case 2: team_name="Montréal";
			break;
			case 3: team_name="Chicago";
			break;
			case 4: team_name="New York";
			break;
			case 5: team_name="Philadelphie";
			break;
			case 6: team_name="Toronto";
			break;
			case 7: team_name="St-Louis";
			break;
			case 8: team_name="Boston";
			break;
			case 9: team_name="Pittsburgh";
			break;
			}
				
			%>
			<tr>
			<td><%=trade_id %></td>
			<td><%=team_name %></td>
			<td><a href="/detail_offre_trade?trade_id=<%=trade_id %>"><button>voir détail</button></a></td>
			<td><a href="/cancel_offre_trade?trade_id=<%=trade_id %>"><button>annuler cette transaction</button></a></td>
			</tr>
			
			<% }	%>
			
			</table>
			
			<br>
			<% if (erreur==true){  %>	
			<p style="color:red">(VOUS AVEZ TENTÉ DE FAIRE UN ÉCHANGE AVEC VOUS-MÊME!!!)</p>	
			<% } %>
			
			<br>
			<br>
			
			<button onclick="onNewOfferButtonClick()">Faire une nouvelle offre</button>
			</div>
			<div id="main_content_title_classement2">AVEC QUI VEUX-TU ÉCHANGER?</div>
			<div id="formulaire_make_offer">
			<br>
			<form action="/make_offer" method="post">
			<input type="radio" name="team_to_trade" value="0">Los Angeles
			<br>
			<input type="radio" name="team_to_trade" value="1">Detroit
			<br>
			<input type="radio" name="team_to_trade" value="2">Montreal
			<br>
			<input type="radio" name="team_to_trade" value="3">Chicago
			<br>
			<input type="radio" name="team_to_trade" value="4">New York
			<br>
			<input type="radio" name="team_to_trade" value="5">Philadelphie
			<br>
			<input type="radio" name="team_to_trade" value="6">Toronto
			<br>
			<input type="radio" name="team_to_trade" value="7">St-Louis
			<br>
			<input type="radio" name="team_to_trade" value="8">Boston
			<br>
			<input type="radio" name="team_to_trade" value="9">Pittsburgh
			<br>
			<br>
			<input type="submit" value="Faire une offre à cette équipe">
			<br>
			</form>

			<br>
			<button onclick="onAnnulerNewOfferButtonClick()">Annuler</button>
			</div>
			

		</div>


		<!-- fin du main container -->
	</div>



</body>

</html>