<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	Iterator<Object> itr;
	List<Object> data, data2;
	int i;
	Object messageRecuFromServlet = request.getAttribute("mTeam");
	if (messageRecuFromServlet != null) {
		String mTeamName2 = request.getAttribute("mTeam").toString();
		String mTeamId2 = request.getAttribute("mTeamId").toString();
		String mUsername2 = request.getAttribute("mUsername")
				.toString();
		data2 = (List<Object>) request.getAttribute("classement");
		session.setAttribute("data", data2);
		session.setAttribute("mTeamId", mTeamId2);
		session.setAttribute("mTeamName", mTeamName2);
		session.setAttribute("mUsername", mUsername2);
	}

	data = (List<Object>) session.getAttribute("data");
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
<script>
	$(document).ready(checkForPickMade);

	function checkForPickMade() {
		// va vérifier si un pick a eu lieu toute les 10 secondes afin de permettre refresh de la page
		//ouverte chez le client et ainsi avoir la liste de repechage a jour sans le dernier joueurs choisis et ajuster l'ordre de draft (next to et 10 next pick to come)
		setInterval(launchAjaxCheckForChange, 10000);

	}
	function launchAjaxCheckForChange() {
		// requete a un script .jsp qui interroge une bdd et qui retourne 1 en output si un pick a été fait depuis le dernier reload
		$.ajaxSetup({ cache: false });
		$.get("check_for_pick", function(data) {
			if (data == 1) {
				
				alert("Un pick vient d'être fait. Votre page va se rafraichir pour enlever le joueur sélectionné et mettre à jour l'ordre de draft");
				location.reload();
				//window.location.replace("/draft");
			} else {
				
			}
		});

	}
</script>
</head>
<body>
	<div class="main_navbar">

	  	<div id="logo_main">
	
			<img alt="logo" src="../images/<%=mLogoId%>" width="150px"
				height="150px">
	

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
		<a href="/trade"><button class="btn_menu">Trade</button></a>
		<a href="/equipes"><button class="btn_menu">Team</button></a>
		<a href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<a href="/admin"><button class="btn_menu">Admin</button></a>
	</div>
	<hr class="hr_header">
	<div class="main_container">
		<div class="main_content"></div>
		<div id="main_content_title_classement">CLASSEMENT</div>
		<div id="main_content_table_classement">
		<br>
	 <p>Le classement a été mise à jour le <%=session.getAttribute("dateLastUpdate") %></p> 	
		<br>
			<table>
				<tr>
					<th>Pos</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>B</th>
					<th>P</th>
					<th>Pts</th>	
					<th>Moy</th>
					<th>1</th>
					<th>7</th>
					<th>30</th>
					<th>Diff</th>
				</tr>

				<%
					i = 1;
					for (itr = data.iterator(); itr.hasNext();) {
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
					
					<%
						i++;
					%>
				</tr>
				<%
					}
				%>
			</table>
		</div>
		<div class="main_sidebar"></div>

		<!-- fin du main container -->
	</div>






</body>
</html>