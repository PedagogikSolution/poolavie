<%@ page language="java" import="java.sql.*"%>

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
	ResultSet rs3=null;
	ResultSet rs4=null;
	ResultSet rs = (ResultSet) request.getAttribute("player_team_1");
	ResultSet rs2 = (ResultSet) request.getAttribute("player_team_2");
	
	if(request.getAttribute("pick_team_1")!=null){
	rs3 = (ResultSet) request.getAttribute("pick_team_1");}
	if(request.getAttribute("pick_team_2")!=null){
	rs4 = (ResultSet) request.getAttribute("pick_team_2");}
	String cash_team_1 = (String) request.getAttribute("cash_team_1");
	String cash_team_2 = (String) request.getAttribute("cash_team_2");
	String team_2 = (String) request.getAttribute("team_2");
	
	if(cash_team_1==null){
		cash_team_1="0";
	}
	if(cash_team_2==null){
		cash_team_2="0";
	}
	
	String team_name=null;
	String team_name2=null;
	String team_name5=null;
	
	int team_3 = Integer.parseInt(team_2);
	switch (team_3) {
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
		<a href="/trade"><button class="btn_menu_trade">RECEIVED</button></a>
		<a href="/make_offer"><button class="btn_menu_trade">MAKE ONE</button></a>
		<a href="/my_trade"><button class="btn_menu_trade">MY TRADE</button></a>
		<a href="/all_trade"><button class="btn_menu_trade">ALL	TRADE</button></a>
	</div>
	<hr class="hr_header">


	<div class="main_container">
		<div class="main_content">
			<div id="main_content_title_classement">
			
			
			
			OFFRE REÇU DE <%=team_name %>
			<br>
			<br>
			</div>
			
			<div id="main_content_table_classement">
			<br>
			CE QUE JE RECOIS
			<br>
			<br>
			<table>
				<caption>JOUEURS</caption>
				<tr>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>Points</th>
					<th>2014-15</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
				</tr>

				<% while (rs.next())  {
	
			String nom = rs.getString("nom");
			String team = rs.getString("team");
			String pj = rs.getString("pj");
			String pts = rs.getString("pts");
			String years_1 = rs.getString("years_1");
			String years_2 = rs.getString("years_2");
			String years_3 = rs.getString("years_3");
			String years_4 = rs.getString("years_4");
			String years_5 = rs.getString("years_5");
			
			%>
				<tr>
					
					<td><%=nom%></td>
					<td><%=team%></td>
					<td><%=pj%></td>
					<td><%=pts%></td>
					<td><%=years_1%></td>
					<td><%=years_2%></td>
					<td><%=years_3%></td>
					<td><%=years_4%></td>
					<td><%=years_5%></td>
					
				</tr>
				<%
					}
				%>
			</table>
			<br>
			JE RECOIS AUSSI EN ARGENT <%=cash_team_1%> $
			<br>
			<br>
			
			<% if(request.getAttribute("pick_team_1")==null) {
			} else {
			%>
			<table>
				<caption>PICK</caption>
				<tr>
					<th>Round</th>
					<th>Team</th>
					
				</tr>

				<% while (rs3.next())  {
	
			String round = rs3.getString("pick_no");
			String team2 = rs3.getString("team_id");
			
			int team3 = Integer.parseInt(team2);
			switch (team3) {
			case 0: team_name2="Los Angeles";
				break;
			case 1: team_name2="Detroit";
			break;
			case 2: team_name2="Montréal";
			break;
			case 3: team_name2="Chicago";
			break;
			case 4: team_name2="New York";
			break;
			case 5: team_name2="Philadelphie";
			break;
			case 6: team_name2="Toronto";
			break;
			case 7: team_name2="St-Louis";
			break;
			case 8: team_name2="Boston";
			break;
			case 9: team_name2="Pittsburgh";
			break;
			}
			
			%>
				<tr>
					
					<td><%=round%></td>
					<td><%=team_name2%></td>
					
				</tr>
				<%
					}
				
			}
				%>
			</table>
	<br>
	<br>
				CE QUE JE DONNE
				<br>
				<br>
			<table>
				<caption>JOUEURS</caption>
				<tr>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>Points</th>
					<th>2014-15</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
				</tr>

				<% while (rs2.next())  {
	
			String nom = rs2.getString("nom");
			String team = rs2.getString("team");
			String pj = rs2.getString("pj");
			String pts = rs2.getString("pts");
			String years_1 = rs2.getString("years_1");
			String years_2 = rs2.getString("years_2");
			String years_3 = rs2.getString("years_3");
			String years_4 = rs2.getString("years_4");
			String years_5 = rs2.getString("years_5");
			
			%>
				<tr>
					
					<td><%=nom%></td>
					<td><%=team%></td>
					<td><%=pj%></td>
					<td><%=pts%></td>
					<td><%=years_1%></td>
					<td><%=years_2%></td>
					<td><%=years_3%></td>
					<td><%=years_4%></td>
					<td><%=years_5%></td>
					
				</tr>
				<%
					}
				%>
			</table>
			<br>
			JE DONNE AUSSI EN ARGENT <%=cash_team_2%> $
			
			<br>
			<br>
			<% if(request.getAttribute("pick_team_2")==null) {
			} else {
			%>
			<table>
				<caption>PICK</caption>
				<tr>
					<th>Round</th>
					<th>Team</th>
					
				</tr>

				<% while (rs4.next())  {
	
			String round = rs4.getString("pick_no");
			String team4 = rs4.getString("team_id");
			
			int team5 = Integer.parseInt(team4);
			switch (team5) {
			case 0: team_name5="Los Angeles";
				break;
			case 1: team_name5="Detroit";
			break;
			case 2: team_name5="Montréal";
			break;
			case 3: team_name5="Chicago";
			break;
			case 4: team_name5="New York";
			break;
			case 5: team_name5="Philadelphie";
			break;
			case 6: team_name5="Toronto";
			break;
			case 7: team_name5="St-Louis";
			break;
			case 8: team_name5="Boston";
			break;
			case 9: team_name5="Pittsburgh";
			break;
			}
			%>
				<tr>
					
					<td><%=round%></td>
					<td><%=team_name5%></td>
					
				</tr>
				<%
					}
			}
				%>
			</table>		
			
			</div>
			

		</div>


		<!-- fin du main container -->
	</div>



</body>

</html>