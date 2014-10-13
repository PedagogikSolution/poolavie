<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.sql.*"%>


<%

	ResultSet rs = (ResultSet)request.getAttribute("draft_all_player");

	Object messageRecuFromServlet = request.getAttribute("mTeam");
	
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
		mLogoId = "detroit.jpg";
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
		<button class="btn_menu">Trade</button>
		<a href="/equipes"><button class="btn_menu">Team</button></a>
		<button class="btn_menu">Signature</button>
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
	<div class="next_10_drafting_team">
	
	PICKING NOW : <a href="/detroit"><img alt="Detroit" src="images/detroit.jpg"></a>
	10 NEXT TO PICK --> <a href="/montreal"><img alt="Detroit" src="images/montreal.png"></a>
	<a href="/philadelphie"><img alt="Detroit" src="images/philadelphie.png"></a>
	<a href="/los_angeles"><img alt="Detroit" src="images/los_angeles.png"></a>
	<a href="/st_louis"><img alt="Detroit" src="images/st_louis.png"></a>
	<a href="/boston"><img alt="Detroit" src="images/boston.png"></a>
	<a href="/pittsburgh"><img alt="Detroit" src="images/pittsburgh.png"></a>
	<a href="/toronto"><img alt="Detroit" src="images/toronto.png"></a>
	<a href="/philadelphie"><img alt="Detroit" src="images/philadelphie.png"></a>
	<a href="/toronto"><img alt="Detroit" src="images/toronto.png"></a>
	<a href="/detroit"><img alt="Detroit" src="images/detroit.jpg"></a>

	
	
	</div>	
	<hr class="hr_header">
	
	<div class="main_container">
		<div class="main_content"></div>
		<div id="main_content_title_classement">ALL AVAILABLE PLAYERS</div>
		<div id="main_content_table_classement">
		<br>
		<table>
		<tr>
		<th>Nom</th>
		<th>Team</th>
		<th>Pos</th>
		<th>Pj</th>
		<th>Win</th>
		<th>OTL</th>
		<th><a href="/gardien?sortby=pts">Pts</a></th>
		<th>Rookie</th>
		<th><a href="/gardien?sortby=salaire">Salaire</a></th>
		<th><a href="/gardien?sortby=proj">Proj</a></th>
		<th>Draft Pick</th>
		</tr>
		
		<% while (rs.next()) { %>
		
		<tr>
		<td><%= rs.getString("nom")%></td>
		<td><%= rs.getString("team")%></td>
		<td><%= rs.getString("position")%></td>
		<td><%= rs.getString("pj")%></td>
		<td><%= rs.getString("but_victoire")%></td>
		<td><%= rs.getString("aide_overtime")%></td>
		<td><%= rs.getString("pts")%></td>
		<td><%= rs.getString("can_be_rookie")%></td>
		<td><%= rs.getString("salaire_draft")%></td>
		<td><%= rs.getString("projection")%></td>
		<td><button>PICK</button></td>
		
		
		
		
		</tr>
		<% } %>
		</table>			
		</div>
		
		
		<div class="main_sidebar">
			<h2>MON BUDGET</h2>

			<p>
				Masse salarial maximum : <br>
				<%=session.getAttribute("max_salaire")%>
			</p>
			<p>
				Total des salaires actuel : <br>
				<%=session.getAttribute("total_salaire")%>
			</p>
			<p>
				Budget restant : <br><%=session.getAttribute("budget_restant")%>
			</p>
			<p>
				Moyenne restante début du draft : <br><%=session.getAttribute("moy_restante")%>
			</p>
			<h2>MES STATS D'ÉQUIPE</h2>
			<p>
				Nombre d'attanquant : <br><%=session.getAttribute("nb_att")%>
			</p>
			<p>
				Nombre de défenseur : <br><%=session.getAttribute("nb_def")%>
			</p>
			<p>
				Nombre de Gardien : <br><%=session.getAttribute("nb_gar")%>
			</p>
			<p>
				Nombre de Recrue : <br><%=session.getAttribute("nb_rook")%>
			</p>
			<p>
				Nombre de contrat : <br><%=session.getAttribute("nb_contrat")%>
			</p>
			<p>
				Nombre de joueur dans l'équipe : <br><%=session.getAttribute("nb_equipe")%>
			</p>
			<p>
				Nombre de joueur manquant : <br><%=session.getAttribute("manq_equipe")%>
			</p>
			<p>
				Attanquant manquant : <br><%=session.getAttribute("manq_att")%>
			</p>
			<p>
				Defenseur manquant : <br><%=session.getAttribute("manq_def")%>
			</p>
			<p>
				Gardien manquant : <br><%=session.getAttribute("manq_gar")%>
			</p>
			<p>
				Recrue manquante : <br><%=session.getAttribute("manq_rook")%>
			</p>
	<!--  		<p>
				Bonus de 5 Millions : <br><%=session.getAttribute("bonus_5")%>
			</p>
			<p>
				Argent Reçu : <br><%=session.getAttribute("argent_recu")%>
			</p>
			<p>
				Bonus et pénalité : <br><%=session.getAttribute("bonus_penalite")%>
			</p> -->
		</div>
		



		<!-- fin du main container -->
	</div>
	






</body>
</html>