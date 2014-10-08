<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
Iterator<Object> itr;
List<Object> dataAttaquant, dataDefenseur, dataGardien, dataRecrue,dataDraftRound;
int i;
Object messageRecuFromServlet = request.getAttribute("mTeam");
dataAttaquant = (List<Object>) request
		.getAttribute("mTeamDataListAttaquant");
dataDefenseur = (List<Object>) request
		.getAttribute("mTeamDataListDefenseur");
dataGardien = (List<Object>) request
		.getAttribute("mTeamDataListGardien");
dataRecrue = (List<Object>) request
		.getAttribute("mTeamDataListRecrue");
dataDraftRound = (List<Object>) request
		.getAttribute("dataDraftRound");
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
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript" src="js/main.js"></script>
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
		<button class="btn_menu">Draft</button>
		<button class="btn_menu">Trade</button>
		<a href="/equipes"><button class="btn_menu">Team</button></a>
		<button class="btn_menu">Signature</button>
		<button class="btn_menu">Règlement</button>
		<button class="btn_menu">Admin</button>
	</div>
	<div class="team_menu">
	    <a href="/boston"><button class="btn_menu_team">BOS</button></a>
	    <a href="/chicago"><button class="btn_menu_team">CHI</button></a>
		<a href="/detroit"><button class="btn_menu_team">DET</button></a>
		<a href="/los_angeles"><button class="btn_menu_team">LAK</button></a>
		<a href="/montreal"><button class="btn_menu_team">MTL</button></a>
		<a href="/new_york"><button class="btn_menu_team">NYR</button></a>
		<a href="/philadelphie"><button class="btn_menu_team">PHI</button></a>
		<a href="/pittsburgh"><button class="btn_menu_team">PIT</button></a>
		<a href="/st_louis"><button class="btn_menu_team">STL</button></a>
		<a href="/toronto"><button class="btn_menu_team">TOR</button></a>
		
	</div>
	<hr class="hr_header">
	<div class="main_container">
		<div class="main_content"></div>
		<div id="main_content_title_classement">BOSTON</div>
		<div id="main_content_table_classement">
			<br>
			<table>
				<caption>Attaquants</caption>
				<tr>
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2014-15</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
				</tr>

				<%
					i = 1;
					for (itr = dataAttaquant.iterator(); itr.hasNext();) {
				%>
				<tr>
					<td><%=i%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>

					<%
						i++;
					%>
				</tr>
				<%
					}
				%>
			</table>

			<br>
			<table>
				<caption>Défenseurs</caption>
				<tr>
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2014-15</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
				</tr>

				<%
					i = 1;
					for (itr = dataDefenseur.iterator(); itr.hasNext();) {
				%>
				<tr>
					<td><%=i%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>

					<%
						i++;
					%>
				</tr>
				<%
					}
				%>
			</table>

			<br>
			<table>
				<caption>Gardiens</caption>
				<tr>
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>V</th>
					<th>Ot</th>
					<th>Bl</th>
					<th>Points</th>
					<th>2014-15</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
				</tr>

				<%
					i = 1;
					for (itr = dataGardien.iterator(); itr.hasNext();) {
				%>
				<tr>
					<td><%=i%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>

					<%
						i++;
					%>
				</tr>
				<%
					}
				%>
			</table>

			<br>
			<table>
				<caption>Recrues</caption>
				<tr>
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2014-15</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
				</tr>

				<%
					i = 1;
					for (itr = dataRecrue.iterator(); itr.hasNext();) {
				%>
				<tr>
					<td><%=i%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>

					<%
						i++;
					%>
				</tr>
				<%
					}
				%>
			</table>
			<br>
			<table>
				<caption>Draft Pick 2014-2015</caption>
				<tr>
					<th>Team pick no</th>
					<th>Round</th>
					<th>From</th>
					<th>Overall pick number</th>
					
				</tr>
				<%
					for (itr = dataDraftRound.iterator(); itr.hasNext();) {
				%>
				<tr>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
				</tr>
				<%
					}
				%>
			</table>
			<br>
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