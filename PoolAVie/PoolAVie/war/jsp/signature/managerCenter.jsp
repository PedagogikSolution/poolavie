<%@ page contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
	prefix="fn"%>
<c:if test="${Utilisateur.loginReussi != 1 }">
	<c:redirect url="/login?notLoggin=1"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="../utils/firebase_config.jspf"%>
<title>Nouvelles ${Pool.poolName}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/w3.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript"
	src="https://talkgadget.google.com/talkgadget/channel.js"></script>

</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker"
			value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_signature.jsp" />

	<!-- Body de la page reglements -->

	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />
	<c:set var="segment" value="${SegmentSort}" />
	<div class="w3-container">

		<br>
		<c:if
			test="${Pool.cycleAnnuel==1||Pool.cycleAnnuel==2||Pool.cycleAnnuel==3 }">
			<div class="w3-content w3-margin-top"
				style="max-width: 90%">
				<div class="w3-container w3-red w3-center">
					<span onclick="this.parentElement.style.display='none'"
						class="w3-closebtn">&times;</span>
					<h3>Il n'y a pas de gestion possible à ce stade du
						cycle annuel du pool</h3>
				</div>
			</div>
		</c:if>
		<c:if
			test="${(Pool.cycleAnnuel==5||Pool.cycleAnnuel==6) && openWaiver==1}">
			<div class="w3-content w3-margin-top"
				style="max-width: 90%">
				<div class="w3-container w3-center">

					<h2>Ordre de priorité des waivers</h2>


					<input class="w3-input w3-border w3-padding w3-large"
						type="text"
						placeholder="Rechercher un nom dans la liste..."
						id="myInput" onkeyup="filtrerParNom()"
						style="width: 40%; margin-left: auto; margin-right: auto">
					<br>
					<form action="/Signature" method="POST">

						<input type="hidden" value="8" name="signatureStep" />
						<div class="w3-responsive">
							<table id="playersPickBox"
								class="w3-table w3-hoverable w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large"
								style="width: 95%">
								<caption class="w3-blue w3-xlarge">
									<h1>
										Joueurs disponible au Draft (
										<c:out value="${segment}" />
										)
									</h1>
								</caption>
								<tr class="w3-indigo">
									<th>Nom</th>
									<th>Équipe</th>
									<th>Position</th>
									<th>Pj</th>
									<th>But</th>
									<th>Aide</th>
									<th>Pts</th>
									<th>Salaire</th>



									<th># Priorité</th>

									<th>Pick</th>



								</tr>
								<c:set var="nombreDePlayers"
									value="${NonSessionPlayers.pj}" />
								<c:forEach var="i" begin="0"
									end="${fn:length(nombreDePlayers)-1}">

									<tr id=i>
										<td>${NonSessionPlayers.nom[i]}</td>
										<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
										<td>${NonSessionPlayers.position[i]}</td>
										<td>${NonSessionPlayers.pj[i]}</td>
										<td>${NonSessionPlayers.but_victoire[i]}</td>
										<td>${NonSessionPlayers.aide_overtime[i]}</td>
										<td>${NonSessionPlayers.pts[i]}</td>
										<td>${NonSessionPlayers.salaire_draft[i]}</td>
										<td><input type="number" max=12 min=1
											name="positionPriorite"></td>
										<td><input type="checkbox" class="w3-check"
											name="players_id"
											value="${NonSessionPlayers.players_id[i]}">
										</td>
									</tr>

								</c:forEach>





							</table>
							<button class="w3-btn w3-green w3-xxxlarge w3-round">Envoyez
								mes choix</button>
						</div>


					</form>



				</div>
			</div>
		</c:if>
		<c:if
			test="${(Pool.cycleAnnuel==5||Pool.cycleAnnuel==6) && (openWaiver!=1&&openRookie!=1)}">
			<div class="w3-content w3-margin-top"
				style="max-width: 90%">
				<div class="w3-container w3-red w3-center">
					<span onclick="this.parentElement.style.display='none'"
						class="w3-closebtn">&times;</span>
					<h3>Il n'y a pas de gestion possible à ce stade du
						cycle annuel du pool</h3>
				</div>
			</div>
		</c:if>

		<c:if
			test="${(Pool.cycleAnnuel==5||Pool.cycleAnnuel==6) && openRookie==1 && alreadyUpARookie!=1}">

			<c:if test="${canSign==1}">

				<div class="w3-responsive" style="width: 90%">
					<table id="playersPickBox"
						class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large"
						style="width: 95%">
						<caption class="w3-blue w3-xlarge">
							<h1>Monter une recrue en cours d'année</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Position</th>
							<th>Salaire</th>

							<th>${Pool.thisYear}</th>
							<th>${Pool.secondYear}</th>
							<th>${Pool.thirdYear}</th>
							<th>${Pool.fourthYear}</th>
							<th>${Pool.fifthYear}</th>
							<th>Signature</th>

						</tr>

						<c:set var="nombreDePlayers"
							value="${NonSessionPlayers.nom}" />
						<c:choose>

							<c:when test="${not empty nombreDePlayers}">
								<c:forEach var="i" begin="0"
									end="${fn:length(nombreDePlayers)-1}">

									<tr id=i>

										<td>${NonSessionPlayers.nom[i]}</td>
										<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
										<td>${NonSessionPlayers.position[i]}</td>
										<td>${NonSessionPlayers.salaire_draft[i]}</td>
										<td><input class="w3-radio"
											form="signatureAbform${i}" type="radio"
											name="nombreAnneeSignature" value="1" required></td>

										<c:choose>
											<c:when
												test="${NonSessionPlayers.years_2[i]!='X'}">
												<td><input class="w3-radio"
													form="signatureAbform${i}" type="radio"
													name="nombreAnneeSignature" value="2" required></td>
											</c:when>
											<c:otherwise>
												<td>X</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when
												test="${NonSessionPlayers.years_3[i]!='X'}">
												<td><input class="w3-radio"
													form="signatureAbform${i}" type="radio"
													name="nombreAnneeSignature" value="3" required></td>
											</c:when>
											<c:otherwise>
												<td>X</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when
												test="${NonSessionPlayers.years_4[i]!='X'}">
												<td><input class="w3-radio"
													form="signatureAbform${i}" type="radio"
													name="nombreAnneeSignature" value="4" required></td>
											</c:when>
											<c:otherwise>
												<td>X</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when
												test="${NonSessionPlayers.years_5[i]!='X'}">
												<td><input class="w3-radio"
													form="signatureAbform${i}" type="radio"
													name="nombreAnneeSignature" value="5" required></td>
											</c:when>
											<c:otherwise>
												<td>X</td>
											</c:otherwise>
										</c:choose>

										<td>
											<form
												onsubmit="confirmationButtonC.disabled = true; return true;"
												id="signatureAbform${i}" action="/Signature"
												method="POST">
												<input type="hidden" name="signatureStep"
													value="9"> <input type="hidden"
													name="draft_player_id"
													value="${NonSessionPlayers.players_id[i]}">
												<input type="hidden" name="nom"
													value="${NonSessionPlayers.nom[i]}"> <input
													type="hidden" name="position"
													value="${NonSessionPlayers.position[i]}">
												<input type="hidden" name="team"
													value="${NonSessionPlayers.teamOfPlayer[i]}">
												<input type="hidden" name="salaire"
													value="${NonSessionPlayers.salaire_draft[i]}">
												<input type="hidden" name="years_1"
													value="${NonSessionPlayers.years_1[i]}">
												<input type="hidden" name="years_2"
													value="${NonSessionPlayers.years_2[i]}">
												<input type="hidden" name="years_3"
													value="${NonSessionPlayers.years_3[i]}">
												<input type="hidden" name="years_4"
													value="${NonSessionPlayers.years_4[i]}">
												<input type="hidden" name="years_5"
													value="${NonSessionPlayers.years_5[i]}">

												<button name="confirmationButtonC"
													class="w3-btn w3-round-xxlarge w3-blue">Monter
													ce joueur et le signer</button>
											</form>

										</td>

									</tr>

								</c:forEach>
							</c:when>
							<c:otherwise>

								<tr>
									<td class="w3-overflow">Vous n'avez plus aucun
										joueurs dans votre club école
									<td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>

							</c:otherwise>

						</c:choose>


					</table>

				</div>
			</c:if>

			<c:if test="${canSign==0}">
				<div class="w3-responsive" style="width: 90%">
					<table id="playersPickBox"
						class="w3-table w3-content w3-striped w3-bordered w3-card-8 w3-margin-top w3-large"
						style="width: 95%">
						<caption class="w3-blue w3-xlarge">
							<h1>Monter une recrue en cours d'année</h1>
						</caption>
						<tr class="w3-indigo">
							<th>Nom</th>
							<th>Équipe</th>
							<th>Position</th>
							<th>Salaire</th>

							<th>Signature</th>

						</tr>

						<c:set var="nombreDePlayers"
							value="${NonSessionPlayers.nom}" />
						<c:choose>

							<c:when test="${not empty nombreDePlayers}">
								<c:forEach var="i" begin="0"
									end="${fn:length(nombreDePlayers)-1}">

									<tr id=i>

										<td>${NonSessionPlayers.nom[i]}</td>
										<td>${NonSessionPlayers.teamOfPlayer[i]}</td>
										<td>${NonSessionPlayers.position[i]}</td>
										<td>${NonSessionPlayers.salaire_draft[i]}</td>


										<td>
											<form
												onsubmit="confirmationButtonC.disabled = true; return true;"
												id="signatureAbform${i}" action="/Signature"
												method="POST">
												<input type="hidden" name="signatureStep"
													value="10"> <input type="hidden"
													name="draft_player_id"
													value="${NonSessionPlayers.players_id[i]}">
												<input type="hidden" name="nom"
													value="${NonSessionPlayers.nom[i]}"> <input
													type="hidden" name="position"
													value="${NonSessionPlayers.position[i]}">
												<input type="hidden" name="team"
													value="${NonSessionPlayers.teamOfPlayer[i]}">
												<input type="hidden" name="salaire"
													value="${NonSessionPlayers.salaire_draft[i]}">
												
												<button name="confirmationButtonC"
													class="w3-btn w3-round-xxlarge w3-blue">Monter
													ce joueur en tant que JA</button>
											</form>

										</td>

									</tr>

								</c:forEach>
							</c:when>
							<c:otherwise>

								<tr>
									<td class="w3-overflow">Vous n'avez plus aucun
										joueurs dans votre club école
									<td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>

							</c:otherwise>

						</c:choose>


					</table>

				</div>
			</c:if>

		</c:if>


		<c:if
			test="${(Pool.cycleAnnuel==5||Pool.cycleAnnuel==6) && openRookie==1 && alreadyUpARookie==1}">

			<div class="w3-content w3-margin-top"
				style="max-width: 90%">
				<div class="w3-container w3-red w3-center">
					<span onclick="this.parentElement.style.display='none'"
						class="w3-closebtn">&times;</span>
					<h3>Vous avez déjà monté le nombre maximal de
						rookie pour une année en cours</h3>
				</div>
			</div>

		</c:if>



	</div>

	<div class="w3-container w3-margin-top">

		<div class="w3-row w3-container">
			<div class="w3-container w3-half"></div>
			<div class="w3-container w3-half">
				<c:if test="${Pool.cycleAnnuel==7}">
					<p>C'est la période de rachat de contrat de fin
						d'année. Vous pouvez faire vos rachats en cliquant sur
						le lien ci-bas ou en cliquant sur l'onglet du menu
						correspondant à Rachat de contrat</p>
					<p>voir section regles 4.3 pour des détails sur
						cette étape du cycle de fin d'année</p>
					<form action="/Signature" method="get">
						<input type="submit" value="Racheter des contrats">

					</form>

				</c:if>

			</div>
		</div>


	</div>



	<jsp:directive.include file="../utils/draftMessage.jsp" />

	<c:if
		test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
		<jsp:directive.include file="../utils/draftClientB.jsp" />
	</c:if>
	<script>
		document.getElementById('menuSecSign').classList.add('w3-khaki');
	</script>
</body>
</html>