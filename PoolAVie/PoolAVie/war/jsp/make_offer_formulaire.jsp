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
			href="/equipes"><button class="btn_menu">Team</button></a> <a
			href="/signature"><button class="btn_menu">Signature</button></a>
		<button class="btn_menu">Règlement</button>
		<button class="btn_menu">Admin</button>
	</div>
	<div class="trade_menu">
		<c:if test="${sessionScope.periode_echange_ouverte==1}">
			<a href="/trade"><button class="btn_menu_trade">RECEIVED</button></a>
			<a href="/make_offer"><button class="btn_menu_trade">MAKE
					ONE</button></a>
		</c:if>
		<a href="/my_trade"><button class="btn_menu_trade">MY
				TRADE</button></a> <a href="/all_trade"><button class="btn_menu_trade">ALL
				TRADE</button></a>
	</div>
	<hr class="hr_header">

	<div class="main_container">
		
	
		<form action="/make_offer" method="post">
		<input type="hidden" value="need_confirm" name="etape">
		<input type="hidden" value="${teamReceivingOffer.teamReceivingOfferId}" name="teamReceivingOfferId">
		<input type="hidden" value="${teamMakingOffer.teamMakingOfferId}" name="teamMakingOfferId">
		<c:set var="longueur" value="${teamMakingOffer.player_id}" />
		<h1><u>Ce que je trade</u></h1>
		<h2>Attaquant</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur)-1}">
				<c:if test="${teamMakingOffer.position[i]=='attaquant' && teamMakingOffer.club_ecole[i]==0 }">
					<tr>
						<td>${teamMakingOffer.nom[i]}</td>
						<td>${teamMakingOffer.team_name[i]}</td>
						<td>${teamMakingOffer.points[i]}</td>
						<td>${teamMakingOffer.years_1[i]}</td>
						<td>${teamMakingOffer.years_2[i]}</td>
						<td>${teamMakingOffer.years_3[i]}</td>
						<td>${teamMakingOffer.years_4[i]}</td>
						<td>${teamMakingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_my_team" value="${teamMakingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<h2>Défenseur</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur)-1}">
				<c:if test="${teamMakingOffer.position[i]=='defenseur' && teamMakingOffer.club_ecole[i]==0 }">
					<tr>
						<td>${teamMakingOffer.nom[i]}</td>
						<td>${teamMakingOffer.team_name[i]}</td>
						<td>${teamMakingOffer.points[i]}</td>
						<td>${teamMakingOffer.years_1[i]}</td>
						<td>${teamMakingOffer.years_2[i]}</td>
						<td>${teamMakingOffer.years_3[i]}</td>
						<td>${teamMakingOffer.years_4[i]}</td>
						<td>${teamMakingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_my_team" value="${teamMakingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<h2>Gardien</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur)-1}">
				<c:if test="${teamMakingOffer.position[i]=='gardien' && teamMakingOffer.club_ecole[i]==0 }">
					<tr>
						<td>${teamMakingOffer.nom[i]}</td>
						<td>${teamMakingOffer.team_name[i]}</td>
						<td>${teamMakingOffer.points[i]}</td>
						<td>${teamMakingOffer.years_1[i]}</td>
						<td>${teamMakingOffer.years_2[i]}</td>
						<td>${teamMakingOffer.years_3[i]}</td>
						<td>${teamMakingOffer.years_4[i]}</td>
						<td>${teamMakingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_my_team" value="${teamMakingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<h2>Recrue</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur)-1}">
				<c:if test="${teamMakingOffer.club_ecole[i]==1 }">
					<tr>
						<td>${teamMakingOffer.nom[i]}</td>
						<td>${teamMakingOffer.team_name[i]}</td>
						<td>${teamMakingOffer.points[i]}</td>
						<td>${teamMakingOffer.years_1[i]}</td>
						<td>${teamMakingOffer.years_2[i]}</td>
						<td>${teamMakingOffer.years_3[i]}</td>
						<td>${teamMakingOffer.years_4[i]}</td>
						<td>${teamMakingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_my_team" value="${teamMakingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<br>
		<table>
		<tr>
		<th>Montant à donner dans l'échange</th>
		<th>Inclure</th>		
		</tr>		
		<tr>
		<td>0</td>
		<td><input type="radio" name="cash_trade_by" value="0" checked></td>				
		</tr>		
		<tr>
		<td>500 000</td>
		<td><input type="radio" name="cash_trade_by" value="500000"></td>			
		</tr>
		<tr>
		<td>1 000 000</td>	
		<td><input type="radio" name="cash_trade_by" value="1000000"></td>			
		</tr>
		<tr>
		<td>1 500 000</td>
		<td><input type="radio" name="cash_trade_by" value="1500000"></td>				
		</tr>
		<tr>
		<td>2 000 000</td>
		<td><input type="radio" name="cash_trade_by" value="2000000"></td>				
		</tr>
		</table>
		<br>
		<h2>Mes draft pick que je donne</h2>
		<c:set var="longueur_draft_bean" value="${draft_pick_for_exchange.draft_id}" />
		<table>
			<tr>
				<th>Round</th>
				<th>Provient de</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur_draft_bean)-1}">
				<c:if test="${draft_pick_for_exchange.role_echange[i]==1 }">
					<tr>
						<td>${draft_pick_for_exchange.round[i]}</td>
						<td>${draft_pick_for_exchange.from[i]}</td>
						<td><input type="checkbox" name="draft_pick_my_team" value="${draft_pick_for_exchange.draft_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		
		<hr class="hr_header">
		<hr class="hr_header">
		
		<br>
		
		
		<c:set var="longueur2" value="${teamReceivingOffer.player_id}" />
		
		<h1><u>Ce que je veux recevoir</u></h1>
		<h2>Attaquant</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur2)-1}">
				<c:if test="${teamReceivingOffer.position[i]=='attaquant' && teamReceivingOffer.club_ecole[i]==0 }">
					<tr>
						<td>${teamReceivingOffer.nom[i]}</td>
						<td>${teamReceivingOffer.team_name[i]}</td>
						<td>${teamReceivingOffer.points[i]}</td>
						<td>${teamReceivingOffer.years_1[i]}</td>
						<td>${teamReceivingOffer.years_2[i]}</td>
						<td>${teamReceivingOffer.years_3[i]}</td>
						<td>${teamReceivingOffer.years_4[i]}</td>
						<td>${teamReceivingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_other_team" value="${teamReceivingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<h2>Défenseur</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur2)-1}">
				<c:if test="${teamReceivingOffer.position[i]=='defenseur' && teamReceivingOffer.club_ecole[i]==0 }">
					<tr>
						<td>${teamReceivingOffer.nom[i]}</td>
						<td>${teamReceivingOffer.team_name[i]}</td>
						<td>${teamReceivingOffer.points[i]}</td>
						<td>${teamReceivingOffer.years_1[i]}</td>
						<td>${teamReceivingOffer.years_2[i]}</td>
						<td>${teamReceivingOffer.years_3[i]}</td>
						<td>${teamReceivingOffer.years_4[i]}</td>
						<td>${teamReceivingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_other_team" value="${teamReceivingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<h2>Gardien</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur2)-1}">
				<c:if test="${teamReceivingOffer.position[i]=='gardien' && teamReceivingOffer.club_ecole[i]==0 }">
					<tr>
						<td>${teamReceivingOffer.nom[i]}</td>
						<td>${teamReceivingOffer.team_name[i]}</td>
						<td>${teamReceivingOffer.points[i]}</td>
						<td>${teamReceivingOffer.years_1[i]}</td>
						<td>${teamReceivingOffer.years_2[i]}</td>
						<td>${teamReceivingOffer.years_3[i]}</td>
						<td>${teamReceivingOffer.years_4[i]}</td>
						<td>${teamReceivingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_other_team" value="${teamReceivingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<h2>Recrue</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Équipe</th>
				<th>Points</th>
				<th>2015-16</th>
				<th>2016-17</th>
				<th>2017-18</th>
				<th>2018-19</th>
				<th>2019-20</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur2)-1}">
				<c:if test="${teamReceivingOffer.club_ecole[i]==1 }">
					<tr>
						<td>${teamReceivingOffer.nom[i]}</td>
						<td>${teamReceivingOffer.team_name[i]}</td>
						<td>${teamReceivingOffer.points[i]}</td>
						<td>${teamReceivingOffer.years_1[i]}</td>
						<td>${teamReceivingOffer.years_2[i]}</td>
						<td>${teamReceivingOffer.years_3[i]}</td>
						<td>${teamReceivingOffer.years_4[i]}</td>
						<td>${teamReceivingOffer.years_5[i]}</td>
						<td><input type="checkbox" name="players_id_other_team" value="${teamReceivingOffer.player_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<br>
		<br>
		<table>
		<tr>
		<th>Montant à donner dans l'échange</th>
		<th>Inclure</th>		
		</tr>		
		<tr>
		<td>0</td>
		<td><input type="radio" name="cash_trade_to" value="0" checked></td>				
		</tr>		
		<tr>
		<td>500 000</td>
		<td><input type="radio" name="cash_trade_to" value="500000"></td>			
		</tr>
		<tr>
		<td>1 000 000</td>	
		<td><input type="radio" name="cash_trade_to" value="1000000"></td>			
		</tr>
		<tr>
		<td>1 500 000</td>
		<td><input type="radio" name="cash_trade_to" value="1500000"></td>				
		</tr>
		<tr>
		<td>2 000 000</td>
		<td><input type="radio" name="cash_trade_to" value="2000000"></td>				
		</tr>
		</table>
		<br>
		<h2>Mes draft pick que je donne</h2>
		<c:set var="longueur_draft_bean" value="${draft_pick_for_exchange.draft_id}" />
		<table>
			<tr>
				<th>Round</th>
				<th>Provient de</th>
				<th>Inclure dans l'échange</th>
			</tr>
			<c:forEach var="i" begin="0" end="${fn:length(longueur_draft_bean)-1}">
				<c:if test="${draft_pick_for_exchange.role_echange[i]==2 }">
					<tr>
						<td>${draft_pick_for_exchange.round[i]}</td>
						<td>${draft_pick_for_exchange.from[i]}</td>
						<td><input type="checkbox" name="draft_pick_other_team" value="${draft_pick_for_exchange.draft_id[i]}">Inclus</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		
		<br>
		<hr class="hr_header">
		<br>
		<textarea name="message_vente" rows="8" cols="150"></textarea>
		<br>
		<h1><input  style="font-size: 28px" type="submit" value="FAIRE CETTE OFFRE"></h1>
		
		</form>
		
		

		<!-- fin du main container -->
	</div>



</body>

</html>