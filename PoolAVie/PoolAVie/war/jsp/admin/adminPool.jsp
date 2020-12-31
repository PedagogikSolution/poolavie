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
			
				
				
				<c:if test="${Pool.cycleAnnuel==4}">
					<p>Signature apres draft</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="9">
					<input type="submit" value="FIN DE SIGNATURE DÉBUT D'ANNÉE">					
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
				
					<p>Signature AB</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="5">
					<input type="submit" value="FIN DE SIGNATURE">
					
					</form>

				</c:if>
				
				<c:if test="${Pool.cycleAnnuel==11}">
					<p>Trade été</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="6">
					<input type="submit" value="FIN DE TRADE ÉTÉ">
					
					</form>

				</c:if>
				<c:if test="${Pool.cycleAnnuel==12}">
					<p>Rachat apres Trade</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="7">
					<input type="submit" value="FIN DE RACHAT APRES TRADE ÉTÉ">
					
					</form>

				</c:if>
				<c:if test="${Pool.cycleAnnuel==13}">
					<p>Gestion club école</p>
					<form action="/AdminPool" method="post">
					<input type="hidden" name="adminButton" value="8">
					<input type="submit" value="FIN DE GESTION CLUB ECOLE, PREPARATION AU DRAFT">
					
					</form>

				</c:if>

			</div>
			
			
			
			<!--  Section modification des équipes stats and rosters -->
			<div class="w3-container w3-half">
			
			
			
			<div class="w3-row w3-container w3-center">
			
			<h2>Cliquez sur une équipe pour modifier celle-ci</h2>
			<br> <br>
			<div class="w3-quarter">
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="1"> <input type="image" src="${Pool.logoTeam1}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam1}
				</form>

				<br>
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="2"> <input type="image" src="${Pool.logoTeam2}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam2}
				</form>
				<br>
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="3"> <input type="image" src="${Pool.logoTeam3}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam3}
				</form>
			</div>
			<div class="w3-quarter">
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="4"> <input type="image" src="${Pool.logoTeam4}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam4}
				</form>
				<br>
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="5"> <input type="image" src="${Pool.logoTeam5}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam5}
				</form>
				<br>
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="6"> <input type="image" src="${Pool.logoTeam6}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam6}
				</form>
			</div>
			<div class="w3-quarter">
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="7"> <input type="image" src="${Pool.logoTeam7}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam7}
				</form>
				<br>
				<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="8"> <input type="image" src="${Pool.logoTeam8}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam8}
				</form>
				<br>
				<c:if test="${Pool.nomTeam9!=null}">
					<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="9"> <input type="image" src="${Pool.logoTeam9}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam9}
				</form>
				</c:if>
			</div>
			<div class="w3-quarter">
				<c:if test="${Pool.nomTeam10!=null}">
					<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="10"> <input type="image" src="${Pool.logoTeam10}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam10}
				</form>
				</c:if>
				<br>
				<c:if test="${Pool.nomTeam11!=null}">
					<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="11"> <input type="image" src="${Pool.logoTeam11}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam11}
				</form>
				</c:if>
				<br>
				<c:if test="${Pool.nomTeam12!=null}">
					<form class="w3-container" method="get" action="/AdminTeam">
					<input type="hidden" name="team" value="12"> <input type="image" src="${Pool.logoTeam12}" alt="Submit" width="128px"
						height="128px" /> ${Pool.nomTeam12}
				</form>
				</c:if>
			</div>
			<br> <br> <br>
		</div>
			
			
			</div>
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