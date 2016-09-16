<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.sql.*"%>


<%
    String mEquipe ="";
	Boolean myTurn = false;
	String mDraftPickNow = "";
	String mDraftPickImage = "";
	myTurn = (Boolean) request.getAttribute("myTurn");
	int draft_pick_now=99;
	int draft_pick_no=99;
	
	ResultSet rs = (ResultSet) request.getAttribute("draft_all_player");
	ResultSet rs7 = (ResultSet) request.getAttribute("next_10");

	String draft_pick_now2 = (String) request.getAttribute(
			"draft_pick_now").toString();
	if(draft_pick_now2!=null){
	draft_pick_now = Integer.parseInt(draft_pick_now2);}
	
	String draft_pick_no2 = (String) request.getAttribute(
			"draft_pick_no").toString();
	if(draft_pick_no2!=null){
	draft_pick_no = Integer.parseInt(draft_pick_no2);
	}
	if(draft_pick_now!=99){
	switch (draft_pick_now) {
		case 0 :
			mDraftPickNow = "/los_angeles";
			mDraftPickImage = "los_angeles.png";
			break;
		case 1 :
			mDraftPickNow = "/detroit";
			mDraftPickImage = "detroit.png";
			break;
		case 2 :
			mDraftPickNow = "/montreal";
			mDraftPickImage = "montreal.png";
			break;
		case 3 :
			mDraftPickNow = "/chicago";
			mDraftPickImage = "chicago.png";
			break;
		case 4 :
			mDraftPickNow = "/new_york";
			mDraftPickImage = "new_york.png";
			break;
		case 5 :
			mDraftPickNow = "/philadelphie";
			mDraftPickImage = "philadelphie.png";
			break;
		case 6 :
			mDraftPickNow = "/toronto";
			mDraftPickImage = "toronto.png";
			break;
		case 7 :
			mDraftPickNow = "/st_louis";
			mDraftPickImage = "st_louis.png";
			break;
		case 8 :
			mDraftPickNow = "/boston";
			mDraftPickImage = "boston.png";
			break;
		case 9 :
			mDraftPickNow = "/pittsburgh";
			mDraftPickImage = "pittsburgh.png";
			break;

	}
	}
	
	int teamId=99;
	String teamId2 = session.getAttribute("mTeamId").toString();
	if(teamId2!=null){
	teamId = Integer.parseInt(teamId2);}
	String mLogoId = "";
	String mFirstTeamName = "";
if(teamId!=99){
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
		<a href="/add_new_player"><button class="btn_menu_team">ADD</button></a>
	</div>
	<hr class="hr_header">

<div class="next_10_drafting_team">

		PICKING NOW : <a href="<%=mDraftPickNow%>"> <img alt="Detroit"
			src="images/<%=mDraftPickImage%>"></a> NEXT 10 TO PICK :

			<%
     while (rs7.next()) {
   		mEquipe = rs7.getString("equipe");
 %>


		<a href="/<%=mEquipe%>"><img alt="Detroit"
			src="images/<%=mEquipe%>.png"></a>


		<%
		    }
							rs7.close();
		%>


	</div>

	<hr class="hr_header">

	<div class="main_container">
		<div class="main_content">
			<div id="main_content_title_classement">ALL AVAILABLE PLAYERS</div>
			<div id="main_content_table_classement">
				<br>
				<%
				    if (myTurn == true) {
				%>

				<table>
					<tr>
						<th>Nom</th>
						<th>Team</th>
						<th>Pos</th>
						<th>Pj</th>
						<th>But</th>
						<th>Passe</th>
						<th><a href="/defenseur?sortby=pts">Pts</a></th>
						<th>Rookie</th>
						<th><a href="/defenseur?sortby=salaire">Salaire</a></th>
						<th><a href="/defenseur?sortby=proj">Proj</a></th>
						
					</tr>

					<%
					    while (rs.next()) {
																
															String nom = rs.getString("nom");	
															String position = rs.getString("position");	
																String team = rs.getString("team");
																int can_rook=rs.getInt("can_be_rookie");
																int salaire_draft=rs.getInt("salaire_draft");
					%>


					<tr>

						<td><%=nom%></td>
						<td><%=team%></td>
						<td><%=position%></td>
						<td><%=rs.getInt("pj")%></td>
						<td><%=rs.getInt("but_victoire")%></td>
						<td><%=rs.getInt("aide_overtime")%></td>
						<td><%=rs.getInt("pts")%></td>
						<td><%=can_rook%></td>
						<td><%=salaire_draft%></td>
						<td><%=rs.getInt("projection")%></td>
						

					</tr>

					<%
					    }
															rs.close();
					%>
				</table>

				<%
				    } else {
				%>
				<table>
					<tr>
						<th>Nom</th>
						<th>Team</th>
						<th>Pos</th>
						<th>Pj</th>
						<th>But</th>
						<th>Passe</th>
						<th><a href="/defenseur?sortby=pts">Pts</a></th>
						<th>Rookie</th>
						<th><a href="/defenseur?sortby=salaire">Salaire</a></th>
						<th><a href="/defenseur?sortby=proj">Proj</a></th>
						<th>Draft Pick</th>
					</tr>

					<%
					    while (rs.next()) {
					%>

					<tr>
						<td><%=rs.getString("nom")%></td>
						<td><%=rs.getString("team")%></td>
						<td><%=rs.getString("position")%></td>
						<td><%=rs.getInt("pj")%></td>
						<td><%=rs.getInt("but_victoire")%></td>
						<td><%=rs.getInt("aide_overtime")%></td>
						<td><%=rs.getInt("pts")%></td>
						<td><%=rs.getInt("can_be_rookie")%></td>
						<td><%=rs.getInt("salaire_draft")%></td>
						<td><%=rs.getInt("projection")%></td>
						<td><button>NOT YOUR TURN</button></td>

					</tr>
					<%
					    }
														
														rs.close();
					%>
				</table>
				<%
				    }
				%>


			</div>
		</div>

		<div class="main_sidebar">
			<div class="section_budget_haut"><h3>MON BUDGET</h3>

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
				Moyenne restante par joueurs : <br><%=session.getAttribute("moy_restante")%>
			</p>
			</div>
			<br>
			<div class="section_budget_bas">
			<h3>MES STATS D'ÉQUIPE</h3>
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
			</div>
					<p>
				Bonus de 5 Millions : <br><%=session.getAttribute("bonus_5")%>
			</p>
			<p>
				Argent Reçu : <br><%=session.getAttribute("argent_recu")%>
			</p>
			<p>
				Bonus et pénalité : <br><%=session.getAttribute("bonus_penalite")%>
			</p> 
		</div>



		<!-- fin du main container -->
	</div>







</body>

</html>