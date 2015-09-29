<%@ page language="java" import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored ="false" %>

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
	
	
	
	ResultSet rs = (ResultSet) request.getAttribute("my_offer");	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript" src="js/make_offer.js"></script>
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
		<a href="/trade"><button class="btn_menu">Trade</button></a> <a
			href="/equipes"><button class="btn_menu">Team</button></a>
		<a href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<button class="btn_menu">Admin</button>
	</div>
	<div class="trade_menu">
		<c:if test="${periode_echange_ouverte==1}">
		<a href="/trade_received"><button class="btn_menu_trade">RECEIVED</button></a>
		<a href="/make_offer"><button class="btn_menu_trade">MAKE ONE</button></a>
		</c:if>
		<a href="/my_trade"><button class="btn_menu_trade">MY TRADE</button></a>
		<a href="/all_trade"><button class="btn_menu_trade">ALL	TRADE</button></a>
	</div>
	<hr class="hr_header">


	<div class="main_container">
		<div class="main_content">
			<div id="main_content_title_classement">
			
			SECTION DES OFFRES QUE J'AI RECU
			
			
			</div>
			
			<div id="main_content_table_classement">
			
			<c:if test="${messageErreur.codeErreurOffreTrade==11}">
					<h1 style="color: red">
						<strong>Un ou des joueurs ou un ou des pick ne sont plus disponible dans l'une des équipes</strong>
					</h1>
				</c:if>
			<c:if test="${beanTrade.isThereAOfferForMe==0}">
			Tu n'as aucune offre pour le moment. Trouve toi des amis 
			</c:if>
			
			<c:if test="${beanTrade.isThereAOfferForMe==1}">
			
			<br>
			<c:set var="longueur" value="${beanAffichageOfferMade.tradeOfferId}" />
				<table>
					<tr>
						<th>offre #</th>
						<th>équipe</th>
						<th>détail</th>						
						<th>rejeter</th>
						<th>accepter</th>
					</tr>
					<c:if test="${(fn:length(longueur))>0}">
					<c:forEach var="i" begin="0" end="${fn:length(longueur)-1}">
						<tr>
							<td>${beanAffichageOfferMade.tradeOfferId[i]}</td>
							<td>${beanAffichageOfferMade.tradeOfferNameTeamTradeWith[i]}</td>
							<td>
								<form action="/trade_received" method="post">
									<input type="hidden" value="${beanAffichageOfferMade.tradeOfferId[i]}" name="trade_id" />
									<input type="hidden" value="showOffer" name="etape" />
									<input style="font-size: 18px" type="submit" value="Show Detail">
								</form>
							</td>
							
							<td>
								<form action="/trade_received" method="post">
									<input type="hidden" value="${beanAffichageOfferMade.tradeOfferId[i]}" name="trade_id" />
									<input type="hidden" value="cancelOffer" name="etape" />
									<input  style="font-size: 18px"type="submit" value="Annuler cette offre">
								</form>
							</td>
							<td>
								<form action="/trade_received" method="post">
									<input type="hidden" value="${beanAffichageOfferMade.tradeOfferId[i]}" name="trade_id" />
									<input type="hidden" value="acceptOffer" name="etape" />
									<input  style="font-size: 18px" type="submit" value="Accepter cette offre">
								</form>
							</td>

						</tr>

					</c:forEach>
					</c:if>
				</table>


				<br> <br>
			</c:if>
		</div>	
			
			

		</div>


		<!-- fin du main container -->
	</div>



</body>

</html>