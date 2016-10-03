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
<title>Feuille d'échange</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
</head>

<body>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- Body de la page tarde center -->

	<div class="w3-container">
		<!-- Si all team register et pool est commencer -->
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token==null}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>C'est l'heure du Draft</h3>
				<p>Votre draft est prêt à commencer. Cliquez ici pour vous connecter au serveur de draft</p>
				<p>
					<a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu
				</p>

			</div>

		</c:if>
		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&messageErreur.erreurConnectionDraft!=null}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>OUPS!</h3>
				<p>${messageErreur.erreurConnectionDraft}</p>
				<p>
					<a href="/DraftCenter"> Cliquez ici pour y aller directement</a> ou aller dans la section Draft du menu
				</p>

			</div>

		</c:if>

		<!-- Si l'attribut message est pas vide, affiche message trade not open at this time -->
		<c:if test="${requestScope.messageTrade!=null}">

			<div class="w3-container">


				<h3>Mauvais moment</h3>
				<p>${requestScope.messageTrade}</p>

			</div>


		</c:if>
		<c:if test="${messageErreur.erreurTrade!=null}">

			<div class="w3-container w3-section w3-red">

				<span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
				<h3>Oups! Échange impossible</h3>
				<p>${messageErreur.erreurTrade}</p>
			</div>

		</c:if>


		<c:if test="${requestScope.tradeOpen==1}">

			<!-- Section  -->

			<form method="Post" action="/Trade">



				<div class="w3-half">

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Attaquant</h2>
						</caption>

						<c:set var="nombreDePick" value="${NonSessionAttaquantPickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentement</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<tr class="w3-blue">
								<th>Nom</th>
								<th>Équipe</th>
								<th>Pj</th>
								<th>Points</th>
								<th>2015-16</th>
								<th>2016-17</th>
								<th>2017-18</th>
								<th>2018-19</th>
								<th>2019-20</th>
								<th>Choisir</th>

							</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${NonSessionAttaquantPickMaking.nom[i]}</td>
									<td>${NonSessionAttaquantPickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionAttaquantPickMaking.pj[i]}</td>
									<td>${NonSessionAttaquantPickMaking.pts[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_1[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_2[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_3[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_4[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionAttaquantPickMaking._id[i]}">Inclure</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Defenseur</h2>
						</caption>

						<c:set var="nombreDePick" value="${NonSessionDefenseurPickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<tr class="w3-blue">
								<th>Nom</th>
								<th>Équipe</th>
								<th>Pj</th>
								<th>Points</th>
								<th>2015-16</th>
								<th>2016-17</th>
								<th>2017-18</th>
								<th>2018-19</th>
								<th>2019-20</th>
								<th>Choisir</th>
							</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">
								<tr>
									<td>${NonSessionDefenseurPickMaking.nom[i]}</td>
									<td>${NonSessionDefenseurPickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionDefenseurPickMaking.pj[i]}</td>
									<td>${NonSessionDefenseurPickMaking.pts[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_1[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_2[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_3[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_4[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionDefenseurPickMaking._id[i]}">Inclure</td>

								</tr>

							</c:forEach>
						</c:if>

					</table>

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Gardien</h2>
						</caption>
						
						<c:set var="nombreDePick" value="${NonSessionGardienPickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
						<tr class="w3-blue">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Pj</th>
							<th>Points</th>
							<th>2015-16</th>
							<th>2016-17</th>
							<th>2017-18</th>
							<th>2018-19</th>
							<th>2019-20</th>
							<th>Choisir</th>


						</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${NonSessionGardienPickMaking.nom[i]}</td>
									<td>${NonSessionGardienPickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionGardienPickMaking.pj[i]}</td>
									<td>${NonSessionGardienPickMaking.pts[i]}</td>
									<td>${NonSessionGardienPickMaking.years_1[i]}</td>
									<td>${NonSessionGardienPickMaking.years_2[i]}</td>
									<td>${NonSessionGardienPickMaking.years_3[i]}</td>
									<td>${NonSessionGardienPickMaking.years_4[i]}</td>
									<td>${NonSessionGardienPickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionGardienPickMaking._id[i]}">Inclure</td>
								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Recrue</h2>
						</caption>					
						<c:set var="nombreDePick" value="${NonSessionRecruePickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
						<tr class="w3-blue">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Pj</th>
							<th>Points</th>
							<th>2015-16</th>
							<th>2016-17</th>
							<th>2017-18</th>
							<th>2018-19</th>
							<th>2019-20</th>
							<th>Choisir</th>
						</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">
								<tr>
									<td>${NonSessionRecruePickMaking.nom[i]}</td>
									<td>${NonSessionRecruePickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionRecruePickMaking.pj[i]}</td>
									<td>${NonSessionRecruePickMaking.pts[i]}</td>
									<td>${NonSessionRecruePickMaking.years_1[i]}</td>
									<td>${NonSessionRecruePickMaking.years_2[i]}</td>
									<td>${NonSessionRecruePickMaking.years_3[i]}</td>
									<td>${NonSessionRecruePickMaking.years_4[i]}</td>
									<td>${NonSessionRecruePickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionRecruePickMaking._id[i]}">Inclure</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>

					<br>

					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Argent Offer</h2>
						</caption>
						<tr class="w3-blue">
							<th>Montant</th>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="0" checked> 0</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="500000"> 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="1000000">1 000 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="1500000">1 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="2000000">2 000 000</td>
						</tr>
					</table>

					<br>

					<c:if test="${Pool.cycleAnnuel==3}">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick this year</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickMakingThisYear.pick_no}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickMakingThisYear.ronde[i]}</td>
								<td>${NonSessionDraftPickMakingThisYear.teamNameOriginalPick[i]}</td>
								<td><input type="checkbox" name="picks_id_my_team_this_year" value="${NonSessionDraftPickMakingThisYear.pick_no[i]}">Inclure</td>


							</tr>

						</c:forEach>

					</table>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick next year</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickMaking.pick_no}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickMaking.pick_no[i]}</td>
								<td>${NonSessionDraftPickMaking.teamNameOriginalPick[i]}</td>
								<td><input type="checkbox" name="picks_id_my_team" value="${NonSessionDraftPickMaking.pick_no[i]}">Inclure</td>


							</tr>

						</c:forEach>

					</table>
					
					
					</c:if>
					
					
					<c:if test="${Pool.cycleAnnuel>3}">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick next year</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickMaking.pick_no}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickMaking.pick_no[i]}</td>
								<td>${NonSessionDraftPickMaking.teamNameOriginalPick[i]}</td>
								<td><input type="checkbox" name="picks_id_my_team" value="${NonSessionDraftPickMaking.pick_no[i]}">Inclure</td>


							</tr>

						</c:forEach>

					</table>
					
					</c:if>

				</div>

				<!-- Liste des offres fait -->

				<div class="w3-half">

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Attaquant</h2>
						</caption>
						
						<c:set var="nombreDePick" value="${NonSessionAttaquantPickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
						<tr class="w3-blue">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Pj</th>
							<th>Points</th>
							<th>2015-16</th>
							<th>2016-17</th>
							<th>2017-18</th>
							<th>2018-19</th>
							<th>2019-20</th>
							<th>Choisir</th>

						</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${NonSessionAttaquantPickReciving.nom[i]}</td>
									<td>${NonSessionAttaquantPickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionAttaquantPickReciving.pj[i]}</td>
									<td>${NonSessionAttaquantPickReciving.pts[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_1[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_2[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_3[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_4[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionAttaquantPickReciving._id[i]}">Inclure</td>

								</tr>

							</c:forEach>
						</c:if>

					</table>

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Defenseur</h2>
						</caption>
						
						<c:set var="nombreDePick" value="${NonSessionDefenseurPickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
						<tr class="w3-blue">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Pj</th>
							<th>Points</th>
							<th>2015-16</th>
							<th>2016-17</th>
							<th>2017-18</th>
							<th>2018-19</th>
							<th>2019-20</th>
							<th>Choisir</th>


						</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${NonSessionDefenseurPickReciving.nom[i]}</td>
									<td>${NonSessionDefenseurPickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionDefenseurPickReciving.pj[i]}</td>
									<td>${NonSessionDefenseurPickReciving.pts[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_1[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_2[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_3[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_4[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionDefenseurPickReciving._id[i]}">Inclure</td>

								</tr>

							</c:forEach>
						</c:if>

					</table>

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Gardien</h2>
						</caption>

						<c:set var="nombreDePick" value="${NonSessionGardienPickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
												<tr class="w3-blue">
					
							<th>Nom</th>
							<th>Équipe</th>
							<th>Pj</th>
							<th>Points</th>
							<th>2015-16</th>
							<th>2016-17</th>
							<th>2017-18</th>
							<th>2018-19</th>
							<th>2019-20</th>
							<th>Choisir</th>


						</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${NonSessionGardienPickReciving.nom[i]}</td>
									<td>${NonSessionGardienPickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionGardienPickReciving.pj[i]}</td>
									<td>${NonSessionGardienPickReciving.pts[i]}</td>
									<td>${NonSessionGardienPickReciving.years_1[i]}</td>
									<td>${NonSessionGardienPickReciving.years_2[i]}</td>
									<td>${NonSessionGardienPickReciving.years_3[i]}</td>
									<td>${NonSessionGardienPickReciving.years_4[i]}</td>
									<td>${NonSessionGardienPickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionGardienPickReciving._id[i]}">Inclure</td>

								</tr>

							</c:forEach>
						</c:if>

					</table>
					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Recrue</h2>
						</caption>
						
						<c:set var="nombreDePick" value="${NonSessionRecruePickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
						<tr class="w3-blue">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Pj</th>
							<th>Points</th>
							<th>2015-16</th>
							<th>2016-17</th>
							<th>2017-18</th>
							<th>2018-19</th>
							<th>2019-20</th>
							<th>Choisir</th>


						</tr>
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>
									<td>${NonSessionRecruePickReciving.nom[i]}</td>
									<td>${NonSessionRecruePickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionRecruePickReciving.pj[i]}</td>
									<td>${NonSessionRecruePickReciving.pts[i]}</td>
									<td>${NonSessionRecruePickReciving.years_1[i]}</td>
									<td>${NonSessionRecruePickReciving.years_2[i]}</td>
									<td>${NonSessionRecruePickReciving.years_3[i]}</td>
									<td>${NonSessionRecruePickReciving.years_4[i]}</td>
									<td>${NonSessionRecruePickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionRecruePickReciving._id[i]}">Inclure</td>

								</tr>

							</c:forEach>
						</c:if>

					</table>

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Argent Demandé</h2>
						</caption>
						<tr class="w3-blue">
							<th>Montant</th>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="0" checked> 0</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="500000"> 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="1000000">1 000 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="1500000">1 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="2000000">2 000 000</td>
						</tr>
					</table>

					<br>
					<c:if test="${Pool.cycleAnnuel==3}">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick this year</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickRecivingThisYear.pick_no}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickRecivingThisYear.ronde[i]}</td>
								<td>${NonSessionDraftPickRecivingThisYear.teamNameOriginalPick[i]}</td>
								<td><input type="checkbox" name="picks_id_trade_with_team_this_year" value="${NonSessionDraftPickRecivingThisYear.pick_no[i]}">Inclure</td>


							</tr>

						</c:forEach>

					</table>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick next year</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickReciving.pick_no}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickReciving.pick_no[i]}</td>
								<td>${NonSessionDraftPickReciving.teamNameOriginalPick[i]}</td>
								<td><input type="checkbox" name="picks_id_trade_with_team" value="${NonSessionDraftPickReciving.pick_no[i]}">Inclure</td>


							</tr>

						</c:forEach>

					</table>
					
					
					</c:if>
					
					
					<c:if test="${Pool.cycleAnnuel>3}">
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick next year</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickReciving.pick_no}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickReciving.pick_no[i]}</td>
								<td>${NonSessionDraftPickReciving.teamNameOriginalPick[i]}</td>
								<td><input type="checkbox" name="picks_id_trade_with_team" value="${NonSessionDraftPickReciving.pick_no[i]}">Inclure</td>


							</tr>

						</c:forEach>

					</table>
					
					</c:if>
				</div>

				<!-- Faire une offre -->
				<br> <br>
				<div class="w3-container w3-center">
					<input type="hidden" name="tradeWith" value="${RequestScope.TradeWith}"> <input type="hidden" name="tradeTag" value="2">
					<button class="w3-btn w3-blue">Évaluez cette échange</button>
				</div>
			</form>


		</c:if>




		<jsp:directive.include file="../utils/draftMessage.jsp" />

		<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
			<jsp:directive.include file="../utils/draftClientB.jsp" />
		</c:if>
		<script>
	document.getElementById('menuSecTrade').classList.add('w3-khaki');
	</script>
</body>
</html>