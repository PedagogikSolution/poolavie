<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="/js/nouvelles.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>
	<!-- Header du menu principal -->
		<jsp:directive.include file="navbar_main.jsp" />
		<jsp:directive.include file="menu_secondaire.jsp" />
		<div id="all">
		<!-- Si manque des équipes -->
		<c:if test="${Pool.draftType==1&&Utilisateur.typeUtilisateur==1&&Pool.cycleAnnuel==0}">
			
				<div class="w3-container w3-section w3-red">
				
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>Inscription non terminé</h3>
					<p>L'ensemble des Directeurs généraux invités dans votre Pool ne sont pas encore inscrit.
					Aussitôt que le dernier d'entre eux sera officiellement inscris, vous pourrez choisir la date
					 de votre draft et déterminer l'ordre de sélection</p>
						
				</div>
			
		</c:if>
		<!-- Si all team register but no date choose -->
		<c:if test="${Pool.draftType==1&&Utilisateur.typeUtilisateur==1&&Pool.cycleAnnuel==1}">
			
				<div class="w3-container w3-section w3-red">
				
					<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
					<h3>Date du draft</h3>
					<p>Vous n'avez pas encore choisis la date de draft. Vous pouvez la choisir dans la section Admin. Lorsque la date sera choisi, les participants recevront un courriel et l'ordre de draft sera
						aléatoirement déterminé.</p><p><a href="/AdminDraft"> Cliquez ici pour aller directement pour vous y rendre immédiatement</a></p>
						
				</div>
			
		</c:if>
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
		
		<!-- contenu de gauche contient les 10 dernieres articles -->
		<div class="w3-twothird">
			<c:set var="nombreDeMessage" value="${Articles.titre}" />
			<c:forEach var="i" begin="0" end="${fn:length(nombreDeMessage)-1}">
				<div class="w3-row w3-margin w3-card-8">

					<div class="w3-third">
						<img src="${Articles.writerLogo[i]}" style="width: 100%; min-height: 200px">
					</div>
					<div class="w3-twothird w3-container">
						<h4>${Articles.dateCreation[i]}</h4>
						<h2>${Articles.titre[i]}</h2>
						<p>${Articles.body[i]}</p>
						<p>${Articles.writerName[i]}</p>
					</div>

				</div>
			</c:forEach>
		</div>
		


	</div>
</body>
</html>