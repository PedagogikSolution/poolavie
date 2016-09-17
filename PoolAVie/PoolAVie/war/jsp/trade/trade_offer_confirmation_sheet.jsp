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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<jsp:directive.include file="/jsp/utils/firebase.jsp" />
<script src="/js/nouvelles.js"></script>
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


				<h3>SECTION OFFRE REÇUE</h3>
				<p>${requestScope.messageTrade}</p>

			</div>


		</c:if>


		<c:if test="${requestScope.tradeOpen==1}">

			<!-- Section  -->

			<form method="Post" action="/Trade">



				<div class="w3-half">

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Attaquant</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionAttaquantPickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>


									<td>${NonSessionAttaquantPickMaking._id[i]}</td>
									<td>${NonSessionAttaquantPickMaking.nom[i]}</td>
									<td>${NonSessionAttaquantPickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionAttaquantPickMaking.pj[i]}</td>
									<td>${NonSessionAttaquantPickMaking.but_victoire[i]}</td>
									<td>${NonSessionAttaquantPickMaking.aide_overtime[i]}</td>
									<td>${NonSessionAttaquantPickMaking.pts[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_1[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_2[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_3[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_4[i]}</td>
									<td>${NonSessionAttaquantPickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionAttaquantPickMaking._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Defenseur</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>


						</tr>
						<c:set var="nombreDePick" value="${NonSessionDefenseurPickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>


									<td>${NonSessionDefenseurPickMaking._id[i]}</td>
									<td>${NonSessionDefenseurPickMaking.nom[i]}</td>
									<td>${NonSessionDefenseurPickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionDefenseurPickMaking.pj[i]}</td>
									<td>${NonSessionDefenseurPickMaking.but_victoire[i]}</td>
									<td>${NonSessionDefenseurPickMaking.aide_overtime[i]}</td>
									<td>${NonSessionDefenseurPickMaking.pts[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_1[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_2[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_3[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_4[i]}</td>
									<td>${NonSessionDefenseurPickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionDefenseurPickMaking._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Gardien</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>


						</tr>
						<c:set var="nombreDePick" value="${NonSessionGardienPickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>


									<td>${NonSessionGardienPickMaking._id[i]}</td>
									<td>${NonSessionGardienPickMaking.nom[i]}</td>
									<td>${NonSessionGardienPickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionGardienPickMaking.pj[i]}</td>
									<td>${NonSessionGardienPickMaking.but_victoire[i]}</td>
									<td>${NonSessionGardienPickMaking.aide_overtime[i]}</td>
									<td>${NonSessionGardienPickMaking.pts[i]}</td>
									<td>${NonSessionGardienPickMaking.years_1[i]}</td>
									<td>${NonSessionGardienPickMaking.years_2[i]}</td>
									<td>${NonSessionGardienPickMaking.years_3[i]}</td>
									<td>${NonSessionGardienPickMaking.years_4[i]}</td>
									<td>${NonSessionGardienPickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionGardienPickMaking._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Recrue</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>


						</tr>
						<c:set var="nombreDePick" value="${NonSessionRecruePickMaking.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>

									<td>${NonSessionRecruePickMaking._id[i]}</td>
									<td>${NonSessionRecruePickMaking.nom[i]}</td>
									<td>${NonSessionRecruePickMaking.teamOfPlayer[i]}</td>
									<td>${NonSessionRecruePickMaking.pj[i]}</td>
									<td>${NonSessionRecruePickMaking.but_victoire[i]}</td>
									<td>${NonSessionRecruePickMaking.aide_overtime[i]}</td>
									<td>${NonSessionRecruePickMaking.pts[i]}</td>
									<td>${NonSessionRecruePickMaking.years_1[i]}</td>
									<td>${NonSessionRecruePickMaking.years_2[i]}</td>
									<td>${NonSessionRecruePickMaking.years_3[i]}</td>
									<td>${NonSessionRecruePickMaking.years_4[i]}</td>
									<td>${NonSessionRecruePickMaking.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_my_team" value="${NonSessionRecruePickMaking._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>

					<br>

					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Argent Offer</h1>
						</caption>
						<tr class="w3-blue">
							<th>Montant</th>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="0" checked> 0</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="500000" > 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="1000000" >1 000 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="1500000" >1 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashMakingOffer" value="2000000" >2 000 000</td>
						</tr>
					</table>

					<br>

					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Pick</h1>
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
								<td><input type="checkbox" name="picks_id_my_team" value="${NonSessionDraftPickMaking.team_id[i]}">Inclus</td>


							</tr>

						</c:forEach>





					</table>

				</div>

				<!-- Liste des offres fait -->

				<div class="w3-half">

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Attaquant</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionAttaquantPickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>


									<td>${NonSessionAttaquantPickReciving._id[i]}</td>
									<td>${NonSessionAttaquantPickReciving.nom[i]}</td>
									<td>${NonSessionAttaquantPickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionAttaquantPickReciving.pj[i]}</td>
									<td>${NonSessionAttaquantPickReciving.but_victoire[i]}</td>
									<td>${NonSessionAttaquantPickReciving.aide_overtime[i]}</td>
									<td>${NonSessionAttaquantPickReciving.pts[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_1[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_2[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_3[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_4[i]}</td>
									<td>${NonSessionAttaquantPickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionAttaquantPickReciving._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Defenseur</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>


						</tr>
						<c:set var="nombreDePick" value="${NonSessionDefenseurPickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>


									<td>${NonSessionDefenseurPickReciving._id[i]}</td>
									<td>${NonSessionDefenseurPickReciving.nom[i]}</td>
									<td>${NonSessionDefenseurPickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionDefenseurPickReciving.pj[i]}</td>
									<td>${NonSessionDefenseurPickReciving.but_victoire[i]}</td>
									<td>${NonSessionDefenseurPickReciving.aide_overtime[i]}</td>
									<td>${NonSessionDefenseurPickReciving.pts[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_1[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_2[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_3[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_4[i]}</td>
									<td>${NonSessionDefenseurPickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionDefenseurPickReciving._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Gardien</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>


						</tr>
						<c:set var="nombreDePick" value="${NonSessionGardienPickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>


									<td>${NonSessionGardienPickReciving._id[i]}</td>
									<td>${NonSessionGardienPickReciving.nom[i]}</td>
									<td>${NonSessionGardienPickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionGardienPickReciving.pj[i]}</td>
									<td>${NonSessionGardienPickReciving.but_victoire[i]}</td>
									<td>${NonSessionGardienPickReciving.aide_overtime[i]}</td>
									<td>${NonSessionGardienPickReciving.pts[i]}</td>
									<td>${NonSessionGardienPickReciving.years_1[i]}</td>
									<td>${NonSessionGardienPickReciving.years_2[i]}</td>
									<td>${NonSessionGardienPickReciving.years_3[i]}</td>
									<td>${NonSessionGardienPickReciving.years_4[i]}</td>
									<td>${NonSessionGardienPickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionGardienPickReciving._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>




					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Recrue</h1>
						</caption>
						<tr class="w3-blue">
							<th>Id</th>
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
							<th>Choisir</th>


						</tr>
						<c:set var="nombreDePick" value="${NonSessionRecruePickReciving.nom}" />
						<c:if test="${empty nombreDePick}">
							<tr>
								<td>Vous n'avez aucun joueur présentementt</td>
							<tr>
						</c:if>
						<c:if test="${not empty nombreDePick}">
							<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

								<tr>

									<td>${NonSessionRecruePickReciving._id[i]}</td>
									<td>${NonSessionRecruePickReciving.nom[i]}</td>
									<td>${NonSessionRecruePickReciving.teamOfPlayer[i]}</td>
									<td>${NonSessionRecruePickReciving.pj[i]}</td>
									<td>${NonSessionRecruePickReciving.but_victoire[i]}</td>
									<td>${NonSessionRecruePickReciving.aide_overtime[i]}</td>
									<td>${NonSessionRecruePickReciving.pts[i]}</td>
									<td>${NonSessionRecruePickReciving.years_1[i]}</td>
									<td>${NonSessionRecruePickReciving.years_2[i]}</td>
									<td>${NonSessionRecruePickReciving.years_3[i]}</td>
									<td>${NonSessionRecruePickReciving.years_4[i]}</td>
									<td>${NonSessionRecruePickReciving.years_5[i]}</td>
									<td><input type="checkbox" name="players_id_trade_with_team" value="${NonSessionRecruePickReciving._id[i]}">Inclus</td>

								</tr>

							</c:forEach>
						</c:if>




					</table>

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Argent Demandé</h1>
						</caption>
						<tr class="w3-blue">
							<th>Montant</th>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="0" checked> 0</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="500000" > 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="1000000" >1 000 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="1500000" >1 500 000</td>
						</tr>
						<tr>
							<td><input type="radio" name="cashReceivingOffer" value="2000000" >2 000 000</td>
						</tr>
					</table>

					<br>
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h1>Pick</h1>
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
								<td><input type="checkbox" name="picks_id_trade_with_team" value="${NonSessionDraftPickReciving.team_id[i]}">Inclus</td>


							</tr>

						</c:forEach>





					</table>

				</div>

				<!-- Faire une offre -->


			<input type="hidden" name="tradeTag" value="3">
			<input type="submit" value="Évaluez cette échange" />
			</form>


		</c:if>



	</div>


	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
</body>
</html>