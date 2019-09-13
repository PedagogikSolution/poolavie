<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf" %>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>
<style type="text/css">
td {
	white-space: nowrap;
	overflow: hidden;
}
</style>
<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_draft.jsp" />

	<c:set var="segment" value="${SegmentSort}" />
    
	<!-- PROCESS POUR DRAFT -->
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<br>
	<input class="w3-input w3-border w3-padding w3-large" type="text" placeholder="Rechercher un nom dans la liste..." id="myInput" onkeyup="filtrerParNom()"
		style="width: 40%; margin-left: auto; margin-right: auto">
	<br>
	<div class="w3-responsive">
		<table id="playersPickBox" class="w3-table w3-hoverable w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large" style="width: 95%">
			<caption class="w3-blue w3-xlarge">
				<h1>
					Joueurs disponible au Draft (
					<c:out value="${segment}" />
					)
				</h1>
			</caption>
			<tr class="w3-indigo">
				<th>Nom</th>
				<th>Équipe</th>
				<th>Position</th>
				<th>
				 <a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=pj">Pj</a>
				</th>
				<th>
					But
				</th>
				<th>
				 Aide
				</th>
				<th>
					<a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=pts">Pts</a>
				</th>
				<th>
					<a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=salaire_draft">Salaire</a>
				</th>
				<th>Recrue</th>
				<th>
					<a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=projection">Proj</a>
				</th>
				<c:if test="${Utilisateur.teamId==currentPicker}">
					<th>Draft Pick</th>
				</c:if>


			</tr>
			<c:set var="nombreDePlayers" value="${NonSessionPlayers.pj}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePlayers)-1}">

				<tr id=i>
					<td>${NonSessionPlayers.nom[i]}</td>
					<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
					<td>${NonSessionPlayers.position[i]}</td>
					<td>${NonSessionPlayers.pj[i]}</td>
					<td>${NonSessionPlayers.but_victoire[i]}</td>
					<td>${NonSessionPlayers.aide_overtime[i]}</td>
					<td>${NonSessionPlayers.pts[i]}</td>
					<td>${NonSessionPlayers.salaire_draft[i]}</td>
					<td>
						<c:if test="${NonSessionPlayers.can_be_rookie[i]==0}">Non</c:if>
						<c:if test="${NonSessionPlayers.can_be_rookie[i]==1}">Oui</c:if>
					</td>
					<td>${NonSessionPlayers.projection[i]}</td>
					<c:if test="${Utilisateur.teamId==currentPicker}">
						<td>
							<form action="/DraftPlayers" method="POST">
								<input type="hidden" name="draftStep" value="1">
								<input type="hidden" name="draft_player_id" value="${NonSessionPlayers.players_id[i]}">
								<input type="hidden" name="team_id" value="${Utilisateur.teamId}">
								<input type="hidden" name="nom" value="${NonSessionPlayers.nom[i]}">
								<input type="hidden" name="position" value="${NonSessionPlayers.position[i]}">
								<input type="hidden" name="team" value="${NonSessionPlayers.teamOfPlayer[i]}">
								<input type="hidden" name="can_be_rookie" value="${NonSessionPlayers.can_be_rookie[i]}">
								<input type="hidden" name="salaire" value="${NonSessionPlayers.salaire_draft[i]}">
								<input type="submit" value="PICK THIS PLAYERS">
							</form>

						</td>
					</c:if>

				</tr>

			</c:forEach>





		</table>

	</div>

	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<!-- fin du container principal -->


	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>


	<script>
		document.getElementById('menuSecDraft').classList.add('w3-khaki');
	</script>
	<c:if test="${requestScope.trierPar==1}">
		<script>
			document.getElementById('menuSecDraftTous').classList
					.add('w3-text-shadow');
		</script>
	</c:if>
	<c:if test="${requestScope.trierPar==2}">
		<script>
			document.getElementById('menuSecDraftAttaquant').classList
					.add('w3-text-shadow');
		</script>
	</c:if>
	<c:if test="${requestScope.trierPar==3}">
		<script>
			document.getElementById('menuSecDraftDefenseur').classList
					.add('w3-text-shadow');
		</script>
	</c:if>
	<c:if test="${requestScope.trierPar==4}">
		<script>
			document.getElementById('menuSecDraftGardien').classList
					.add('w3-text-shadow');
		</script>
	</c:if>
	<c:if test="${requestScope.trierPar==5}">
		<script>
			document.getElementById('menuSecDraftRecrue').classList
					.add('w3-text-shadow');
		</script>
	</c:if>
	

	<!-- SCRIPT POUR FILTRE PAR NOM -->
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
				td = tr[i].getElementsByTagName("td")[0];
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


</body>
</html>