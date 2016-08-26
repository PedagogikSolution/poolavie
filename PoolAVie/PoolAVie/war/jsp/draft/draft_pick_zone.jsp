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
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_draft.jsp" />

	<!-- PROCESS POUR DRAFT -->
	<c:set var="currentPick" value="${DraftBean.currentPick}" />
	<c:set var="currentPicker" value="${DraftBean.currentPicker}" />

	<!-- Body de la page draft_center -->
	<c:set var="segment" value="${SegmentSort}" />
	<div class="w3-container">
<!-- Si all team register et pool est commencer -->
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token==null}">
			
				<div class="w3-container w3-section w3-red">
				
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>C'est l'heure du Draft </h3>
					<p>Votre draft est prêt à commencer. Cliquez ici pour vous connecter au serveur de draft</p><p><a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu</p>
						
				</div>
			
		</c:if>
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&messageErreur.erreurConnectionDraft!=null}">
			
				<div class="w3-container w3-section w3-red">
				
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>OUPS! </h3>
					<p>${messageErreur.erreurConnectionDraft}</p><p><a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu</p>
						
				</div>
			
		</c:if>

		<table id="playersPickBox" class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
			<caption class="w3-blue w3-xlarge">
				<h1>
					Joueurs disponible au Draft (
					<c:out value="${segment}" />
					)
				</h1>
			</caption>
			<tr class="w3-blue">
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=nom">Nom</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=teamOfPlayer">Équipe</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=position">Position</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=pj">Pj</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=but_victoire">But</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=aide_overtime">Aide</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=pts">Pts</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=salaire_draft">Salaire</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=can_be_rookie">Rookie</a></th>
				<th><a href="/DraftPlayers?seg=<c:out value="${segment}"/>&sort=projection">Proj</a></th>
				<c:if test="${Utilisateur.teamId==currentPicker}">
					<th>Draft Pick</th>
				</c:if>


			</tr>
			<c:set var="nombreDePlayers" value="${NonSessionPlayers.pj}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePlayers)-1}">

				<tr id=i>
					<td>${NonSessionPlayers.nom[i]}
					<!--  <script
							src="http://www.hockeydb.com/em/?text_col=%23000000&linktext_col=%230000ee&linktext_hover_col=%23770000&bg_col=%23f0ecdd&border_col=%23000000&title_bg_col=%23d6cda5&row_bg_col=%23ffffff&row_alt_bg_col=%23f5f2e9&header=1&pid=73288"
							type="text/javascript">
					</script>
					-->
					</td>
					<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
					<td>${NonSessionPlayers.position[i]}</td>
					<td>${NonSessionPlayers.pj[i]}</td>
					<td>${NonSessionPlayers.but_victoire[i]}</td>
					<td>${NonSessionPlayers.aide_overtime[i]}</td>
					<td>${NonSessionPlayers.pts[i]}</td>
					<td>${NonSessionPlayers.salaire_draft[i]}</td>
					<td><c:if test="${NonSessionPlayers.can_be_rookie[i]==0}">Non</c:if> <c:if test="${NonSessionPlayers.can_be_rookie[i]==1}">Oui</c:if></td>
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
	
		
		
		

<jsp:directive.include file="../utils/draftMessage.jsp" />

		<!-- fin du container principal -->
	</div>
	
<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
<jsp:directive.include file="../utils/draftClientB.jsp" />
</c:if>
</body>
</html>