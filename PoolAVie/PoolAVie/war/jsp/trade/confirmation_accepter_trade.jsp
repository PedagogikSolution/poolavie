<%@ page language="java" import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%
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
		<a href="/jsp/main/main.jsp"><button class="btn_menu">Classement</button></a>
		<a href="/draft?sortby=all"><button class="btn_menu">Draft</button></a>
		<a href="/trade"><button class="btn_menu">Trade</button></a> <a
			href="/equipes"><button class="btn_menu">Team</button></a> <a
			href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<button class="btn_menu">Admin</button>
	</div>

	<hr class="hr_header">

	<hr class="hr_header">

	<div class="main_container">
		<div class="main_content"></div>
		<div id="main_content_title_classement">Confirmation d'une offre de trade</div>
		<div id="main_content_table_classement">

			<h1>Vous voulez recevoir :</h1>

			<c:forEach var="nomJoueurA" items="${tradeOfferBean.nomMakingOffer}">
			<c:out value="${nomJoueurA}" />
			<br>
			</c:forEach>
			<br>
			<c:if test="${tradeOfferBean.cashMakingOffer>0 }">
			 ainsi que la somme de <c:out value="${tradeOfferBean.cashMakingOffer}"/> $
			</c:if>
			
			<br>
			<br>
			
			et les round de draft suivantes :
			<br>
			<br>
				
						
			<c:forEach var="pickA" items="${tradeOfferBean.roundPickMakingOffer}">
			
			<c:out value="${pickA}" />
			<br>
		<br>
			</c:forEach>
			
			provenant respectivement de
			<br>
			<br>
			<c:forEach var="pickB" items="${tradeOfferBean.fromPickMakingOffer}">			
		
			<c:out value="${pickB}" />
			<br>
			
			</c:forEach>
			

			<h2>et vous allez donner les joueurs suivants:</h2>
			
			<c:forEach var="nomJoueurB" items="${tradeOfferBean.nomReceivingOffer}">
			<c:out value="${nomJoueurB}" />
			<br>
			</c:forEach>
			<br>
			<c:if test="${tradeOfferBean.cashReceivingOffer>0 }">
			 ainsi que la somme de <c:out value="${tradeOfferBean.cashReceivingOffer}"/> $
			</c:if>
			<br>
			<br>
			
			et les round de draft suivantes :
			<br>
			<br>
			
			<c:forEach var="pickC" items="${tradeOfferBean.roundPickReceivingOffer}">
			<c:out value="${pickC}" />
			<br>
			</c:forEach>
			provenant respectivement de
			<br>
			<br>
			<c:forEach var="pickD" items="${tradeOfferBean.fromPickReceivingOffer}">
			<c:out value="${pickD}" />
			<br>
			</c:forEach>
			
		
			
			<hr>

			<form action="/accepter_offre_trade" method="post">
				<input type="hidden" value="oui" name="confirmation" />
				<input type="hidden" value="${tradeOfferBean.trade_id}" name="trade_id" />
				<input style="font-size: 18px" type="submit" value="Oui je le veux">
			</form>

			<form action="/accepter_offre_trade" method="post">
				<input type="hidden" value="non" name="confirmation" />
				<input style="font-size: 18px" type="submit" value="Es-tu fou toi criss">
			</form>





		</div>
		<!-- fin du main container -->
	</div>







</body>

</html>