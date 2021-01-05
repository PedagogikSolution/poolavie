<%@ page contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"
	prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
	prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf"%>
<title>Classement ${Pool.poolName}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link
	href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript"
	src="https://talkgadget.google.com/talkgadget/channel.js"></script>

</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker"
			value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />



	<table
		class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-xlarge"
		style="width: 80%">
		<caption class="w3-blue w3-xlarge">
			<h1>Classement du pool ${Pool.poolName}</h1>
		</caption>
		<tr class="w3-indigo">
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
			<th>Move</th>
		</tr>
		<c:set var="nombreDeTeam" value="${Classement.team_id}" />
		<c:forEach var="i" begin="0"
			end="${fn:length(nombreDeTeam)-1}">

			<tr>
				<td>${pageScope.i+1}</td>
				<td>${Classement.equipe[i]}</td>
				<td>${Classement.pj[i]}</td>
				<td>${Classement.but[i]}</td>
				<td>${Classement.passe[i]}</td>
				<td>${Classement.points[i]}</td>
				<td><fmt:formatNumber var="roundUp" type="number"
						minFractionDigits="2" maxFractionDigits="2"
						value="${Classement.moyenne[i]}" /> <c:out
						value="${roundUp}" /></td>
				<td>${Classement.hier[i]}</td>
				<td>${Classement.semaine[i]}</td>
				<td>${Classement.mois[i]}</td>
				<td>${Classement.difference[i]}</td>
				<td><c:if test="${Classement.mouvement[i]==2}">
						<i class="material-icons w3-xxlarge"
							style="color: green">arrow_upward</i>
					</c:if> <c:if test="${Classement.mouvement[i]==1}">
						<i class="material-icons w3-xxlarge"
							style="color: red">arrow_downward</i>
					</c:if> <c:if test="${Classement.mouvement[i]==0}">
						<i class="material-icons w3-xxlarge"
							style="color: orange">arrow_forward</i>
					</c:if></td>

			</tr>

		</c:forEach>

	</table>
	<br>
	<br>
	<h4 class="w3-center">Mise à jour le
		${Pool.derniereMAJ}</h4>

	<hr>
	<br>
	<input class="w3-input w3-border w3-padding w3-large"
		type="text"
		placeholder="Rechercher un nom dans la liste..."
		id="myInput" onkeyup="filtrerParNom()"
		style="width: 40%; margin-left: auto; margin-right: auto">
	<br>
	<div class="w3-responsive">
		<table id="playersPickBox"
			class="w3-table w3-hoverable w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large"
			style="width: 95%; margin-left: auto; margin-right: auto">
			<caption class="w3-blue w3-xlarge">
				<h1>Liste des joueurs appartenant à un équipe</h1>
				<h6>(cliquez sur une colonne pour la trier)</h6>
			</caption>
			<tr class="w3-indigo">
				<th onclick="sortTableNum(0)" style="cursor:pointer;">Pos</th>
				<th onclick="sortTableAlpha(1)" style="cursor:pointer;">Nom</th>
				<th onclick="sortTableNum(2)" style="cursor:pointer;">Pj</th>
				<th onclick="sortTableNum(3)" style="cursor:pointer;">Points</th>
				<th onclick="sortTableAlpha(4)" style="cursor:pointer;">Propriétaire</th>

			</tr>
			<c:set var="nombreDePick"
				value="${NonSessionPlayersBean.nom}" />
			<c:if test="${empty nombreDePick}">
				<tr>
					<td>Vous n'avez aucun joueur présentementt</td>
				<tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
				<c:forEach var="i" begin="0"
					end="${fn:length(nombreDePick)-1}">

					<tr id=i>


						<td>${i+1}</td>
						<td>${NonSessionPlayersBean.nom[i]}</td>
						<td>${NonSessionPlayersBean.pj2[i]}</td>
						<td>${NonSessionPlayersBean.pts2[i]}</td>
						<td>${NonSessionPlayersBean.teamOfPlayer[i]}</td>

					</tr>

				</c:forEach>
			</c:if>




		</table>

	</div>

	<br>
	<br>
	<br>

	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if
		test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecStand').classList.add('w3-khaki');
	</script>
	<script>
		function filtrerParNom() {
			var input, filter, table, tr, td, i;
			document.getElementById('playersPickBox').classList
					.remove('w3-striped');
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("playersPickBox");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1];
				if (td) {
					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
						if (filter == "") {
							document.getElementById('playersPickBox').classList
									.add('w3-striped');
						}
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>
	<script>
		function sortTableAlpha(n) {
			var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
			table = document.getElementById("playersPickBox");
			switching = true;
			// Set the sorting direction to ascending:
			dir = "asc";

			/* Make a loop that will continue until
			no switching has been done: */
			while (switching) {
				// Start by saying: no switching is done:
				switching = false;
				rows = table.rows;
				/* Loop through all table rows (except the
				first, which contains table headers): */
				for (i = 1; i < (rows.length - 1); i++) {
					// Start by saying there should be no switching:
					shouldSwitch = false;
					/* Get the two elements you want to compare,
					one from current row and one from the next: */
					x = rows[i].getElementsByTagName("TD")[n];
					y = rows[i + 1].getElementsByTagName("TD")[n];
					/* Check if the two rows should switch place,
					based on the direction, asc or desc: */
					if (dir == "asc") {
						if (x.innerHTML.toLowerCase() > y.innerHTML
								.toLowerCase()) {
							// If so, mark as a switch and break the loop:
							shouldSwitch = true;
							break;
						}
					} else if (dir == "desc") {
						if (x.innerHTML.toLowerCase() < y.innerHTML
								.toLowerCase()) {
							// If so, mark as a switch and break the loop:
							shouldSwitch = true;
							break;
						}
					}
				}
				if (shouldSwitch) {
					/* If a switch has been marked, make the switch
					and mark that a switch has been done: */
					rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
					switching = true;
					// Each time a switch is done, increase this count by 1:
					switchcount++;
				} else {
					/* If no switching has been done AND the direction is "asc",
					set the direction to "desc" and run the while loop again. */
					if (switchcount == 0 && dir == "asc") {
						dir = "desc";
						switching = true;
					}
				}

			}
		}
	</script>
	<script>
		function sortTableNum(n) {
			var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
			table = document.getElementById("playersPickBox");
			switching = true;
			// Set the sorting direction to ascending:
			dir = "asc";

			/* Make a loop that will continue until
			no switching has been done: */
			while (switching) {
				// Start by saying: no switching is done:
				switching = false;
				rows = table.rows;
				/* Loop through all table rows (except the
				first, which contains table headers): */
				for (i = 1; i < (rows.length - 1); i++) {
					// Start by saying there should be no switching:
					shouldSwitch = false;
					/* Get the two elements you want to compare,
					one from current row and one from the next: */
					x = rows[i].getElementsByTagName("TD")[n];
					y = rows[i + 1].getElementsByTagName("TD")[n];
					/* Check if the two rows should switch place,
					based on the direction, asc or desc: */
					if (dir == "asc") {
						if (Number(x.innerHTML) < Number(y.innerHTML)) {
							// If so, mark as a switch and break the loop:
							shouldSwitch = true;
							break;
						}
					} else if (dir == "desc") {
						if (Number(x.innerHTML) > Number(y.innerHTML)) {
							// If so, mark as a switch and break the loop:
							shouldSwitch = true;
							break;
						}
					}
				}
				if (shouldSwitch) {
					/* If a switch has been marked, make the switch
					and mark that a switch has been done: */
					rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
					switching = true;
					// Each time a switch is done, increase this count by 1:
					switchcount++;
				} else {
					/* If no switching has been done AND the direction is "asc",
					set the direction to "desc" and run the while loop again. */
					if (switchcount == 0 && dir == "asc") {
						dir = "desc";
						switching = true;
					}
				}

			}
		}
	</script>
	<script>
	window.onload = sortTableNum(3);
	
	</script>

</body>
</html>