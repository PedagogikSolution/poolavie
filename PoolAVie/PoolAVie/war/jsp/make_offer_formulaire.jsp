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
	// réception des tableaux de résultats des deux équipes impliquer dans l'offre d'échange
	ResultSet rs = (ResultSet) request.getAttribute("team_that_trade");
	ResultSet rs2 = (ResultSet) request.getAttribute("team_to_trade");
	ResultSet rs3 = (ResultSet) request.getAttribute("pick_team_that_trade");
	ResultSet rs4 = (ResultSet) request.getAttribute("pick_team_to_trade");
	ResultSet rs5 = (ResultSet) request.getAttribute("rookie_team_that_trade");
	ResultSet rs6 = (ResultSet) request.getAttribute("rookie_team_to_trade");
	
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
		<form action="/confirm_offer" method="post">
	
		<div id="trade_form_left">
		<h3>CE QUE JE TRADE</h3>
		
		<table>
		<tr>
		<th>Trade</th>
		<th>Joueur</th>
		<th>Team</th>
		<th>Points</th>
		<th>Salaire</th>
		<th>Rookie</th>
				
		</tr>
		
		<% while (rs.next())  {
			String club_ecole2;
			String contrat2;
			int player_id = rs.getInt("_id");
			String nom = rs.getString("nom");	
			String team = rs.getString("team");	
			int pts = rs.getInt("pts");
			int salaire_contrat=rs.getInt("salaire_contrat");
	
			int club_ecole=rs.getInt("club_ecole");
			if (club_ecole==1){
				club_ecole2="Oui";
			} else {
				club_ecole2="Non";
			}
		%>
		
		
		<tr>
		
		<td><input type="checkbox" name="player_trade_by" value="<%=player_id%>"></td>	
		<td><%=nom %></td>
		<td><%=team %></td>
		<td><%=pts %></td>
		<td><%=salaire_contrat %></td>
		<td><%=club_ecole2 %></td>	
		</tr>
			
		<% }	%>
				
		</table> 
		
		<table>
		<tr>
		<th>Trade</th>
		<th>Joueur</th>
		<th>Team</th>
		<th>Points</th>
		<th>Salaire</th>
		<th>Rookie</th>
				
		</tr>
		
		<% while (rs5.next())  {
			String club_ecole2;
			String contrat2;
			int player_id = rs5.getInt("_id");
			String nom = rs5.getString("nom");	
			String team = rs5.getString("team");	
			int pts = rs5.getInt("pts");
			int salaire_contrat=rs5.getInt("salaire_contrat");
	
			int club_ecole=rs5.getInt("club_ecole");
			if (club_ecole==1){
				club_ecole2="Oui";
			} else {
				club_ecole2="Non";
			}
		%>
		
		
		<tr>
		
		<td><input type="checkbox" name="player_trade_by" value="<%=player_id%>"></td>	
		<td><%=nom %></td>
		<td><%=team %></td>
		<td><%=pts %></td>
		<td><%=salaire_contrat %></td>
		<td><%=club_ecole2 %></td>	
		</tr>
			
		<% }	%>
				
		</table>
		
		<table>
		<tr>
		<th>Inclus</th>
		<th>Montant</th>
		
		</tr>
		
		<tr>
		<td><input type="radio" name="cash_trade_by" value="0" checked></td>
		<td>0</td>
		
		</tr>
		
		<tr>
		<td><input type="radio" name="cash_trade_by" value="500000"></td>
		<td>500 000</td>
		
		</tr>
		<tr>
		<td><input type="radio" name="cash_trade_by" value="1000000"></td>
		<td>1 000 000</td>
		
		</tr>
		<tr>
		<td><input type="radio" name="cash_trade_by" value="1500000"></td>
		<td>1 500 000</td>
		
		</tr>
		<tr>
		<td><input type="radio" name="cash_trade_by" value="2000000"></td>
		<td>2 000 000</td>
		
		</tr>
		</table>
		
		<table>
		<tr>
		<th>inclus</th>
		<th>pick_no</th>
		<th>team</th>
		
				
		</tr>
		
		<% while (rs3.next())  {
			int pick_id = rs3.getInt("_id");
			int team_id_1 = rs3.getInt("team_id");	
			int pick_no = rs3.getInt("pick_no");	
		%>
		
		
		<tr>
		
		<td><input type="checkbox" name="pick_trade_by" value="<%=pick_id%>"></td>	
		<td><%=pick_no %></td>
		<td><input type="hidden" name="team_id_1" value="<%=team_id_1%>"><%=team_id_1 %></td>
		</tr>
			
		<% }	%>
				
		</table>
		
		</div>
		
		
		
		
		<div id="trade_form_right">
		<h3>CE QUE JE RECOIS</h3>
		<table>
		<tr>
		<th>Joueur</th>
		<th>Team</th>
		<th>Points</th>
		<th>Salaire</th>
		<th>Rookie</th>
		<th>Trade</th>		
		</tr>
		
		<% while (rs2.next())  {
			String club_ecole2;
			String contrat2;
			int player_id = rs2.getInt("_id");
			String nom = rs2.getString("nom");	
			String team = rs2.getString("team");	
			int pts = rs2.getInt("pts");
			int salaire_contrat=rs2.getInt("salaire_contrat");

			
			
			int club_ecole=rs2.getInt("club_ecole");
			if (club_ecole==1){
				club_ecole2="Oui";
			} else {
				club_ecole2="Non";
			}
		%>
		
		<tr>
		<th><%=nom %></th>
		<th><%=team %></th>
		<th><%=pts %></th>
		<th><%=salaire_contrat %></th>
		<th><%=club_ecole2 %></th>
		<th><input type="checkbox" name="player_trade_for" value="<%=player_id%>"></th>		
		</tr>
			
		<% }	%>
				
		</table>
		
				<table>
		<tr>
		
		<th>Joueur</th>
		<th>Team</th>
		<th>Points</th>
		<th>Salaire</th>
		<th>Rookie</th>
		<th>Trade</th>
				
		</tr>
		
		<% while (rs6.next())  {
			String club_ecole2;
			String contrat2;
			int player_id = rs6.getInt("_id");
			String nom = rs6.getString("nom");	
			String team = rs6.getString("team");	
			int pts = rs6.getInt("pts");
			int salaire_contrat=rs6.getInt("salaire_contrat");
	
			int club_ecole=rs6.getInt("club_ecole");
			if (club_ecole==1){
				club_ecole2="Oui";
			} else {
				club_ecole2="Non";
			}
		%>
		
		
		<tr>
		
			
		<th><%=nom %></th>
		<th><%=team %></th>
		<th><%=pts %></th>
		<th><%=salaire_contrat %></th>
		<th><%=club_ecole2 %></th>
		<th><input type="checkbox" name="player_trade_by" value="<%=player_id%>"></th>	
		</tr>
			
		<% }	%>
				
		</table>
		
		<table>
		<tr>
		<th>Montant</th>
		<th>Inclus</th>
		
		
		</tr>
		
		<tr>
		
		<td>0</td>
		<td><input type="radio" name="cash_trade_for" value="0" checked></td>
		
		</tr>
		<tr>
		<td>500 000</td>
		<td><input type="radio" name="cash_trade_for" value="500000"></td>
		</tr>
		<tr>
		<td>1 000 000</td>
		<td><input type="radio" name="cash_trade_for" value="1000000"></td>
		</tr>
		<tr>
		<td>1 500 000</td>
		<td><input type="radio" name="cash_trade_for" value="1500000"></td>
		</tr>
		<tr>
		<td>2 000 000</td>
		<td><input type="radio" name="cash_trade_for" value="2000000"></td>
		</tr>
		</table>
		
		<table>
		<tr>
		
		<th>pick_no</th>
		<th>team</th>
		<th>inclus</th>
				
		</tr>
		
		<% while (rs4.next())  {
			int pick_id = rs4.getInt("_id");
			int team_id_2 = rs4.getInt("team_id");	
			int pick_no = rs4.getInt("pick_no");	
		%>
		
		
		<tr>
		
			
		<th><%=pick_no %></th>
		<th><input type="hidden" name="team_id_2" value="<%=team_id_2%>"><%=team_id_2 %></th>
		<th><input type="checkbox" name="pick_trade_for" value="<%=pick_id%>"></th>
		</tr>
			
		<% } %>
				
		</table>
		
		</div>
		
		
		
		<input type="submit" value="Faire cette offre">
		</form>
		<!-- fin du main container -->
	</div>



</body>

</html>