<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.sql.*"%>


<%
	Iterator<Object> itr;
	List<Object> dataAttaquant, dataDefenseur, dataGardien, dataRecrue, dataDraftRound;

	dataDraftRound = (List<Object>) request
			.getAttribute("dataDraftOrder");
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<!--  <script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script>
	$(document).ready(checkForPickMade);

	function checkForPickMade() {
		// va v�rifier si un pick a eu lieu toute les 10 secondes afin de permettre refresh de la page
		//ouverte chez le client et ainsi avoir la liste de repechage a jour sans le dernier joueurs choisis et ajuster l'ordre de draft (next to et 10 next pick to come)
		setInterval(launchAjaxCheckForChange, 10000);

	}
	function launchAjaxCheckForChange() {
		// requete a un script .jsp qui interroge une bdd et qui retourne 1 en output si un pick a �t� fait depuis le dernier reload
		$.ajaxSetup({ cache: false });
		$.get("check_for_pick", function(data) {
			if (data == 1) {
				
				alert("Un pick vient d'�tre fait. Votre page va se rafraichir pour enlever le joueur s�lectionn� et mettre � jour l'ordre de draft");
				location.reload();
				//window.location.replace("/draft");
			} else {
				
			}
		});

	}
</script>-->
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
			<br> dans ta section de directeur g�n�ral <br> des
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
		<button class="btn_menu">R�glement</button>
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
		<div id="main_content_title_classement">ALL AVAILABLE PLAYERS</div>
		<div id="main_content_table_classement">
			<br>
			<table>

				<tr>
					<th>Pick number</th>
					<th>Round number</th>
					<th>Team to pick</th>
					<th>Receveid from</th>
					<th>Joueurs Drafter</th>
				</tr>

				<%
					for (itr = dataDraftRound.iterator(); itr.hasNext();) {
				%>
				<tr>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
					<td><%=itr.next()%></td>
				</tr>
				<%
					}
				%>
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
				Moyenne restante d�but du draft : <br><%=session.getAttribute("moy_restante")%>
			</p>
			<h2>MES STATS D'�QUIPE</h2>
			<p>
				Nombre d'attanquant : <br><%=session.getAttribute("nb_att")%>
			</p>
			<p>
				Nombre de d�fenseur : <br><%=session.getAttribute("nb_def")%>
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
				Nombre de joueur dans l'�quipe : <br><%=session.getAttribute("nb_equipe")%>
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
				Argent Re�u : <br><%=session.getAttribute("argent_recu")%>
			</p>
			<p>
				Bonus et p�nalit� : <br><%=session.getAttribute("bonus_penalite")%>
			</p> -->
		</div>




		<!-- fin du main container -->
	</div>







</body>
</html>