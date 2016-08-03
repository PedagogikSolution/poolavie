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
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_team.jsp" />

	<!-- Body de la page reglements -->

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
	<div class="w3-threequarter">
	CONTIENT LA PAGE DES ÉQUIPES ET LEUR BUDGET
	<br>
	<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Attaquant</h1></caption>
			<tr class="w3-blue">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>
					
			</tr>
			<c:set var="nombreDePick" value="${NonSessionAttaquant.team_id}" />
			<c:if test="${empty nombreDePick}">
			<tr><td>Vous n'avez aucun joueur présentementt</td><tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)}">
				
					<tr>
					
						
						<td>${i+1}</td>
						<td>${NonSessionAttaquant.nom[i]}</td>
						<td>${NonSessionAttaquant.equipe[i]}</td>
						<td>${NonSessionAttaquant.pj[i]}</td>
						<td>${NonSessionAttaquant.but_victoire[i]}</td>
						<td>${NonSessionAttaquant.passe_overtime[i]}</td>
						<td>${NonSessionAttaquant.pts[i]}</td>
						<td>${NonSessionAttaquant.years_1[i]}</td>
						<td>${NonSessionAttaquant.years_2[i]}</td>
						<td>${NonSessionAttaquant.years_3[i]}</td>
						<td>${NonSessionAttaquant.years_4[i]}</td>
						<td>${NonSessionAttaquant.years_5[i]}</td>
												
					</tr>
				
			</c:forEach>
			</c:if>
		
		
		
		
		</table>
	
	
	
	
	<br>
	<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Defenseur</h1></caption>
			<tr class="w3-blue">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>
					
			</tr>
			<c:set var="nombreDePick" value="${NonSessionDefenseur.team_id}" />
			<c:if test="${empty nombreDePick}">
			<tr><td>Vous n'avez aucun joueur présentementt</td><tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)}">
				
					<tr>
					
						
						<td>${i+1}</td>
						<td>${NonSessionDefenseur.nom[i]}</td>
						<td>${NonSessionDefenseur.equipe[i]}</td>
						<td>${NonSessionDefenseur.pj[i]}</td>
						<td>${NonSessionDefenseur.but_victoire[i]}</td>
						<td>${NonSessionDefenseur.passe_overtime[i]}</td>
						<td>${NonSessionDefenseur.pts[i]}</td>
						<td>${NonSessionDefenseur.years_1[i]}</td>
						<td>${NonSessionDefenseur.years_2[i]}</td>
						<td>${NonSessionDefenseur.years_3[i]}</td>
						<td>${NonSessionDefenseur.years_4[i]}</td>
						<td>${NonSessionDefenseur.years_5[i]}</td>
												
					</tr>
				
			</c:forEach>
			</c:if>
		
		
		
		
		</table>
	
	
	
	
	<br>
	<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Gardien</h1></caption>
			<tr class="w3-blue">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>
					
			</tr>
			<c:set var="nombreDePick" value="${NonSessionGardien.team_id}" />
			<c:if test="${empty nombreDePick}">
			<tr><td>Vous n'avez aucun joueur présentementt</td><tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)}">
				
					<tr>
					
						
						<td>${i+1}</td>
						<td>${NonSessionGardien.nom[i]}</td>
						<td>${NonSessionGardien.equipe[i]}</td>
						<td>${NonSessionGardien.pj[i]}</td>
						<td>${NonSessionGardien.but_victoire[i]}</td>
						<td>${NonSessionGardien.passe_overtime[i]}</td>
						<td>${NonSessionGardien.pts[i]}</td>
						<td>${NonSessionGardien.years_1[i]}</td>
						<td>${NonSessionGardien.years_2[i]}</td>
						<td>${NonSessionGardien.years_3[i]}</td>
						<td>${NonSessionGardien.years_4[i]}</td>
						<td>${NonSessionGardien.years_5[i]}</td>
												
					</tr>
				
			</c:forEach>
			</c:if>
		
		
		
		
		</table>
	
	
	
	
	<br>
	<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Recrue</h1></caption>
			<tr class="w3-blue">
					<th>Pos</th>
					<th>Nom</th>
					<th>Équipe</th>
					<th>Pj</th>
					<th>But</th>
					<th>Passe</th>
					<th>Points</th>
					<th>2015-16</th>
					<th>2016-17</th>
					<th>2017-18</th>
					<th>2018-19</th>
					<th>2019-20</th>
					
			</tr>
			<c:set var="nombreDePick" value="${NonSessionRecrue.team_id}" />
			<c:if test="${empty nombreDePick}">
			<tr><td>Vous n'avez aucun joueur présentementt</td><tr>
			</c:if>
			<c:if test="${not empty nombreDePick}">
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)}">
				
					<tr>
					
						
						<td>${i+1}</td>
						<td>${NonSessionRecrue.nom[i]}</td>
						<td>${NonSessionRecrue.equipe[i]}</td>
						<td>${NonSessionRecrue.pj[i]}</td>
						<td>${NonSessionRecrue.but_victoire[i]}</td>
						<td>${NonSessionRecrue.passe_overtime[i]}</td>
						<td>${NonSessionRecrue.pts[i]}</td>
						<td>${NonSessionRecrue.years_1[i]}</td>
						<td>${NonSessionRecrue.years_2[i]}</td>
						<td>${NonSessionRecrue.years_3[i]}</td>
						<td>${NonSessionRecrue.years_4[i]}</td>
						<td>${NonSessionRecrue.years_5[i]}</td>
												
					</tr>
				
			</c:forEach>
			</c:if>
		
		
		
		
		</table>
	
	<br>
	<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width:80%">
		<caption class="w3-blue w3-xlarge"><h1>Pick</h1></caption>
			<tr class="w3-blue">
					<th>Round</th>
					<th>From</th>
					
			</tr>
			<c:set var="nombreDePick" value="${NonSessionDraftPick.pick_no}" />
			
			<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">
				
					<tr>
					
						<td>${NonSessionDraftPick.pick_no[i]}</td>
						<td>${NonSessionDraftPick.teamNameOriginalPick[i]}</td>
						
												
					</tr>
				
			</c:forEach>
		
		
		
		
		
		</table>
		
		
	
	</div>
	
	<div class=w3-quarter>
			<div class="section_budget_haut"><h3>MON BUDGET</h3>

			<p>
				Masse salarial maximum : <br>
				${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Total des salaires actuel : <br>
				${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Budget restant : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Argent Reçu : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Moyenne restante par joueurs : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			</div>
			<br>
			<div class="section_budget_bas">
			<h3>MES STATS D'ÉQUIPE</h3>
			<p>
				Nombre d'attanquant : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Nombre de défenseur : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Nombre de Gardien : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Nombre de Recrue : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Nombre de contrat : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Nombre de joueur dans l'équipe : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Nombre de joueur manquant : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Attanquant manquant : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Defenseur manquant : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Gardien manquant : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Recrue manquante : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			</div>
			<!--  		<p>
				Bonus de 5 Millions : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Argent Reçu : <br>${NonSessionEquipe.max_salaire_begin}
			</p>
			<p>
				Bonus et pénalité : <br>${NonSessionEquipe.max_salaire_begin}
			</p> -->
		</div>

	
	
	</div>

<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
<jsp:directive.include file="../utils/draftClientB.jsp" />
</c:if>
</body>
</html>