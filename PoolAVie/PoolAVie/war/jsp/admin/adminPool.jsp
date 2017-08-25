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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="../../css/material_design.css" rel="stylesheet">
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_admin.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

	<c:if test="${Pool.cycleAnnuel==0}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>En attente de l'inscription des autres participants</h3>
			</div>
		</div>

	</c:if>

	<c:if test="${Pool.cycleAnnuel==1}">
		<div class="w3-content w3-margin-top" style="max-width: 90%">
			<div class="w3-container w3-red w3-center">
				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>En attente du choix d'une date pour le draft. Vous recevrez un courriel lorsque votre administrateur aura fixé celle-ci.</h3>
			</div>
		</div>

	</c:if>

	<div class="w3-container w3-margin-top">

		<div class="w3-row w3-container">
			<div class="w3-container w3-half">
			
				<c:if test="${Pool.cycleAnnuel==3}">
					<p>Période de trade</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="8">
					<input type="submit" value="FIN DE RACHAT DÉBUT D'ANNÉE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==4}">
					<p>Signature apres draft</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="9">
					<input type="submit" value="FIN DE RACHAT DÉBUT D'ANNÉE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==5||Pool.cycleAnnuel==6}">
					<p>Pour mettre fin à la saison et entamer le cycle de fin d'année et d'été, appuyez sur le bouton ci-dessous</p>
					<p>Vous ne pourrez revenir en arrière, ceci enverra un message courriel pour féliciter les gagnants et postera sur la page news une nouvelle de fin de saison.</p>
					<p>Une fois effectuez, le pool ouvrira la période de rachat des contrats avec l'argent de l'année en cours et vous permettra de passez à la suite du processus de fin d'année</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="1">
					<input type="submit" value="FIN DE LA SAISON">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==7}">
					<p>C'est la période des rachats de contrat. Pour y mettre fin et entamer le cycle de retrocession d'une rookie dans le club école. Cliquez ci-bas.</p>
					<p>Ceci mettra fin à la période de rachat de l'année en cours</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="2">
					<input type="submit" value="FIN DE RACHAT FIN D'ANNÉE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==8}">
					<p>Une fois la période de rétrocession terminé, vous pouvez mettre fin à l'année en cours pour entamer la prochaine année.</p>
					<p>*****AJOUTER LES 217 RECRUES DU DRAFT, LES JOUEURS DE LA KHL CONNU COMME REVENANT SI PAS DEJA DANS LISTE, RETIRER LES MORTS ET RETRAITÉS******</p>
					<p>*****METTRE A JOUR LA TABLE UPDATE STAT AVEC ITÉRATION LA PLUS RÉCENTE DES JOUEURS POUR UPDATE DES TEAMS DES JOUEURS</p>
					
					<p>Ceci persistera les classements, equipes et statistiques dans les archives et fera un reset des equipes monétairement et les joueurs X ou JA seront retourner au draft.</p>
					<p>Il activera la 2ieme période de rachat avec l'argent de la nouvelle année</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="3">
					<input type="submit" value="FIN DE RÉTROCESSION CLUB ÉCOLE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==9}">
				<p>*****METTRE A JOUR LA TABLE PLAYERS_X AVEC SALAIRE DES ROOKIES</p>
					<p>Rachat de début d'année</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="4">
					<input type="submit" value="FIN DE RACHAT DÉBUT D'ANNÉE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==10}">
					<p>Trade été</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="5">
					<input type="submit" value="FIN DE RACHAT DÉBUT D'ANNÉE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==11}">
					<p>Rachat apres trade été</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="6">
					<input type="submit" value="FIN DE RACHAT DÉBUT D'ANNÉE">
					
					</form>

				</c:if>
				<c:if test="${Pool.cycleAnnuel==12}">
					<p>Gestion club école</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="7">
					<input type="submit" value="FIN DE RACHAT DÉBUT D'ANNÉE">
					
					</form>

				</c:if>

			</div>
			<div class="w3-container w3-half"></div>
		</div>


	</div>



	<jsp:directive.include file="../utils/draftMessage.jsp" />


	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecAdmin').classList.add('w3-khaki');
	</script>
</body>
</html>