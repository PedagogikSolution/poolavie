<%@ page language="java" import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored ="false" %>
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
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rachat de contrat fin de saison</title>
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
		<div id="main_content_title_classement">Rachat fin de saison</div>
		<div id="main_content_table_classement">
		
		<c:set var="longueur" value="${players_list.player_id}"/>
    
		<table>
		<tr>
		<th>Nom</th>
		<th>Équipe</th>
		<th>Position</th>
		<th>Year 1</th>
		<th>Year 2</th>
		<th>Year 3</th>
		<th>Year 4</th>
		<th>Year 5</th>
		<th>Rachat</th>
		</tr>
		<c:forEach var="i" begin="0" end="${fn:length(longueur)-1}">
		<tr>			
		<td>${players_list.nom[i]}</td>	
		<td>${players_list.team_name[i]}</td>		
		<td>${players_list.position[i]}</td>	
		<td>${players_list.years_1[i]}</td>		
		<td>${players_list.years_2[i]}</td>		
		<td>${players_list.years_3[i]}</td>		
		<td>${players_list.years_4[i]}</td>		
		<td>${players_list.years_5[i]}</td>
		<td><form action="/rachat" method="post">
		<input type="hidden" value="${players_list.player_id[i]}" name="player_id"/>
		<input type="hidden" value="verifRachat" name="rachat_type"/>
		<input type="submit" value="Racheté ce joueur">
		</form></td>
		</tr>
		</c:forEach>
		</table>
	
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
		
		</div>




		<!-- fin du main container -->
	</div>







</body>

</html>