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
<link rel="stylesheet" href="/css/trade_offer_sheet.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
	<jsp:directive.include file="menu_trade.jsp" />

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />

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

		<form method="Post" action="/Trade" name="evaluateTrade">


			<div class="w3-row">
			<div class="w3-half w3-center">
			<br>
				<h1>Mon équipe</h1>
				
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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>

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
								<td>
									<input type="checkbox" class="w3-check" name="players_id_my_team" value="${NonSessionAttaquantPickMaking._id[i]}">
								</td>

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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>
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
								<td>
									<input type="checkbox" class="w3-check" name="players_id_my_team" value="${NonSessionDefenseurPickMaking._id[i]}">
									
								</td>

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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>


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
								<td>
									<input type="checkbox" class="w3-check" name="players_id_my_team" value="${NonSessionGardienPickMaking._id[i]}">
									
								</td>
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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>
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
								<td>
									<input type="checkbox" class="w3-check" name="rookie_id_my_team" value="${NonSessionRecruePickMaking._id[i]}">
									
								</td>
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
						<td>
							<input class="w3-radio" type="radio" name="cashMakingOffer" value="0" checked>
							0
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashMakingOffer" value="500000">
							500 000
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashMakingOffer" value="1000000">
							1 000 000
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashMakingOffer" value="1500000">
							1 500 000
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashMakingOffer" value="2000000">
							2 000 000
						</td>
					</tr>
				</table>

				<br>

				


				
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Inclure</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickMaking.ronde}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickMaking.ronde[i]}</td>
								<td>${NonSessionDraftPickMaking.teamNameOriginalPick[i]}</td>
								<td>
									<input class="w3-check" type="checkbox" name="picks_id_my_team" value="${NonSessionDraftPickMaking.pick_no[i]}">
									
								</td>


							</tr>

						</c:forEach>

					</table>

			

			</div>

			<!-- Liste des offres fait -->
			
			<div class="w3-half w3-center">
			<br>
				<h1>Équipe avec qui j'échange</h1>
				
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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>

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
								<td>
									<input class="w3-check" type="checkbox" name="players_id_trade_with_team" value="${NonSessionAttaquantPickReciving._id[i]}">
									
								</td>

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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>


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
								<td>
									<input class="w3-check" type="checkbox" name="players_id_trade_with_team" value="${NonSessionDefenseurPickReciving._id[i]}">
									
								</td>

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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>


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
								<td>
									<input class="w3-check" type="checkbox" name="players_id_trade_with_team" value="${NonSessionGardienPickReciving._id[i]}">
									
								</td>

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
							<th>Pts</th>
							<th>2017</th>
							<th>2018</th>
							<th>2019</th>
							<th>2020</th>
							<th>2021</th>
							<th>Inclure</th>


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
								<td>
									<input class="w3-check" type="checkbox" name="rookie_id_trade_with_team" value="${NonSessionRecruePickReciving._id[i]}">
									
								</td>

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
						<td>
							<input class="w3-radio" type="radio" name="cashReceivingOffer" value="0" checked>
							0
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashReceivingOffer" value="500000">
							500 000
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashReceivingOffer" value="1000000">
							1 000 000
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashReceivingOffer" value="1500000">
							1 500 000
						</td>
					</tr>
					<tr>
						<td>
							<input class="w3-radio" type="radio" name="cashReceivingOffer" value="2000000">
							2 000 000
						</td>
					</tr>
				</table>

				<br>
				
					<table class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top" style="width: 80%">
						<caption class="w3-blue w3-xlarge">
							<h2>Draft pick</h2>
						</caption>
						<tr class="w3-blue">
							<th>Round</th>
							<th>From</th>
							<th>Inclure</th>

						</tr>
						<c:set var="nombreDePick" value="${NonSessionDraftPickReciving.ronde}" />

						<c:forEach var="i" begin="0" end="${fn:length(nombreDePick)-1}">

							<tr>
								<td>${NonSessionDraftPickReciving.ronde[i]}</td>
								<td>${NonSessionDraftPickReciving.teamNameOriginalPick[i]}</td>
								<td>
									<input class="w3-check" type="checkbox" name="picks_id_trade_with_team" value="${NonSessionDraftPickReciving.pick_no[i]}">
									
								</td>


							</tr>

						</c:forEach>

					</table>

			</div>
			</div>

			<!-- Faire une offre -->
			<br>
			<div class="w3-row w3-container w3-center">
			<h2>Message à inclure dans l'offre d'échange (max 720 char)</h2>
			<textarea maxlength="720" style="outline:none" class="w3-padding-xlarge w3-topbar w3-leftbar w3-bottombar w3-rightbar w3-border-red w3-pale-red w3-round-xlarge w3-large" name="message_vente" rows="8" cols="100" placeholder="Entrez ici un message pour la personne qui va recevoir votre offre"></textarea>
			</div>
			<br>
			<div class="w3-row w3-container w3-center">
				<br>
				<input type="hidden" name="tradeWith" value="${TradeWith}">
				<input type="hidden" name="tradeTag" value="2">
				<button class="w3-btn w3-blue w3-xxxlarge w3-round">Évaluez cette échange</button>
				<br>
			</div>
			<br>
		</form>
<br>

	</c:if>

<br>



	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecTrade').classList.add('w3-khaki');
	</script>
</body>
</html>