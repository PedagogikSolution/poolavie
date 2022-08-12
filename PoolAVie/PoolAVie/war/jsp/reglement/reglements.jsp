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
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script type="text/javascript" src="https://talkgadget.google.com/talkgadget/channel.js"></script>

</head>
<body>
	<c:if test="${Pool.cycleAnnuel==3 }">
		<c:set var="currentPick" value="${DraftBean.currentPick}" />
		<c:set var="currentPicker" value="${DraftBean.currentPicker}" />
	</c:if>
	<!-- Header du menu principal-->
	<jsp:directive.include file="../main/navbar_main.jsp" />
	<jsp:directive.include file="../main/menu_secondaire.jsp" />
	<jsp:directive.include file="menu_reglements.jsp" />
	
	<!-- section Alerte -->
	<jsp:directive.include file="../utils/messageAlerte.jsp" />
	
	<!-- Body de la page reglements -->

	<div class="w3-container">
	
	<h1>EN CONSTRUCTION</h1>
	

<p>﻿RÈGLEMENTS DU POOL À VIE (PAV)</p>

<p>Le cycle annuel</p>
<ol><li>Création d’un nouveau pool et du compte administrateur (équipe 1)</li>
<li>Création des équipes et comptes utilisateurs associés</li>
<li>Choix d’une date pour le draft</li>
<li>Tirage au sort de l’ordre de draft pour la première année</li>
<li>Draft de la première année avec ordre inversé durant les tours pairs</li>
<li>Signature après-draft</li>
<li>Saison régulière avec options trade jusqu’à date limite, à date ponctuel, avec monter rookie, avec waiver.</li>
<li>Rachat de contrat fin de saison avec argent année en cour</li>
<li>Rétrocession d’un joueur dans club école avec argent année en cour</li>
<li>Changement de saison → contrat avance d’une année, joueur X et JA retournent au draft</li>
<li>Rachat de début de nouvelle saison avec argent de nouvelle saison</li>
<li>Signature des joueurs A ou B et retour au draft des joueurs non-signé</li>
<li>Trade estival</li>
<li>Rachat final</li>
<li>Signature rookie ou libération rookie</li>
<li>Retour au point 3</li></ol>


<p>1. CRÉATION DE VOTRE ÉQUIPE</p>

<p>1.0.0  Lors du repêchage annuel, chaques directeurs généraux devront créer une équipe de 30 joueurs.</p>
<p>1.1.0  Chaques équipes devront être composées d'un <strong><u>minimum</strong></u> de:</p>
<p>- 8 attaquants</p>
<p>- 5 défenseurs</p>
<p>- 2 gardiens</p>
<p>- 7 substituts (qui peuvent être sélectionnés parmis les 3 catégories précédemment mentionnées).</p>
<p>- 8 recrues ou club école (Voir section 8. Club École).</p>

<p>1.2.0   52 000 000$ est le budget alloué pour la création de votre équipe de 22 joueurs qui forment votre grand club. Les joueurs recrues ne comptent pas dans la masse salarial. Les salaires sont basés sur un algorithme du nombre de point fait dans la ou les saisons précédentes et d’une charte des salaires par position (voir section 3. Les salaires)</p>



<p>2. LE REPÊCHAGE</p>

<p>2.1. Lors du premier repêchage, le rang de sélection est octroyé au hasard et se déroule en mode inverse à la 2ieme ronde et ainsi de suite.</p>

<p>2.2. Lors du repêchage des années subséquentes ( à partir de la 2ième année jusqu’à la mort du pool), les rangs de sélections seront déterminés selon les résultats du classement général de la dernière saison.</p>
<p>Donc à l'inverse du classement final →  Cela signifie que la dernière position repêchera premier à chaque ronde et le champion repêchera dernier à chaque ronde (sauf si des échanges de draft pick ont eu lieu durant l’année, auquel cas, évidemment, les positions sont adaptés en conséquence.</p>

<p>2.3 Le draft est entièrement fait en ligne.</p>
<p>2.3.1 Le programme offrira ainsi les joueurs encore disponibles (donc pas sous contrat dans une équipe régulière ou dans un club école).</p>
<p>2.3.2 Le directeur général (DG) pourra placer le joueur repêché dans son club régulier ou dans son club école si celui-ci à moins de 25 ans en date du 15 septembre de l’année courante. Sinon le joueur va automatiquement dans le club régulier et son salaire est déduit du budget restant</p>
<p>2.3.3 Le programme empêchera les erreurs de draft allant à l’encontre des règles établies en envoyant un message d’erreur et en refusant un choix au repêchage du DG si :</p>
<p>2.3.3.1 Maximum 22 joueurs dans club régulier</p>
<p>2.3.3.2 Maximum 8 joueurs dans club école</p>
<p>2.3.3.3 Pas assez d’argent dans le budget restant pour effectuer le choix</p>
<p>2.3.3.4 Projection de manque d’argent dans le budget restant avec le nombre de choix à faire restant et le budget restant après ce choix (moyenne salaire restant pour draft doit être supérieur à 1M)</p>
<p>2.3.3.5 Nombre de joueur minimum par position (8 attaquants ,5 défenseurs, 2 gardiens)</p>

<p>3. LES ÉCHELLES SALARIALES</p>

<p>Les salaires attribués aux joueurs seront décidés selon leurs statistiques de la dernière saison.</p>
<p>Exception: les joueurs ayant joués moins de 50 matches se verront attribués un salaire </p>
<p>selon la moyenne de pts par match des 3 dernières années multiplié par 80 et selon la grille salariale suivante.</p>

<p>*Tous les joueurs qui n'ont pas été inscrit sur la liste des joueurs à repêcher, le salaire sera de 1 000 000$.</p>

<p>** Pour les recrues, le salaire est de 1 000 000 M sauf pour les choix de première ronde qui ont la charte suivante :</p>

<p>choix 1 à 5 → 3 000 000$</p>
<p>choix 6 à 10 → 2 000 000$</p>
<p>choix 11 à 300 → 1 000 000$</p>

<p>3.0.0 ÉCHELLES SALARIALES/POSITIONS:</p>

<p>3.0.1                               3.0.2                              3.0.3</p>
<p>Attaquants:                         Défenseurs:                        Gardiens:</p>

<p>110 pts et +     7 000 000$          86 pts et +     7 000 000$          105 pts et +     7 000 000$</p>
<p>100-109 pts     6 500 000$         80-85  pts     6 500 000$          100-104 pts     6 500 000$ </p>
<p> 95-99  pts     6 000 000$          74-79  pts     6 000 000$           95-99  pts     6 000 000$</p>
<p> 90-94  pts     5 500 000$          68-73  pts     5 500 000$           90-94  pts     5 500 000$</p>
<p> 85-89  pts     5 000 000$          62-67  pts     5 000 000$           85-89  pts     5 000 000$</p>
<p> 80-84  pts     4 500 000$          56-61  pts     4 500 000$           80-84  pts     4 500 000$</p>
<p> 75-79  pts     4 000 000$          50-55  pts     4 000 000$           75-79  pts     4 000 000$</p>
<p> 70-74  pts     3 500 000$          45-49  pts     3 500 000$           70-74  pts     3 500 000$</p>
<p> 65-69  pts     3 000 000$          40-44  pts     3 000 000$           65-69  pts     3 000 000$</p>
<p> 60-64  pts     2 500 000$          35-39  pts     2 500 000$           60-64  pts     2 500 000$</p>
<p> 55-59  pts     2 000 000$          30-34  pts     2 000 000$           55-59  pts     2 000 000$</p>
<p> 45-54  pts     1 500 000$          25-29  pts     1 500 000$           50-54  pts     1 500 000$</p>
<p>  0-44  pts     1 000 000$           0-24  pts     1 000 000$            0-49  pts     1 000 000$</p>


<p>4. BUDGET</p>

<p>À chaque saison, lors du repêchage d'octobre, votre équipe disposera de 52 000 000$ (plus ou moins les bonus ou pénalités imposés</p>
<p>à votre équipe selon le classement final de la saison précédente et l'argent que vous pourrez avoir accumulé lors des transactions et moins les joueurs sous contrat). Exemple, une équipe termine 3ième au classement (bonus 1M), il a reçu 2M lors d'un échange et il a 10 joueurs sous contrat pour un total de contrat de 27M. Le DG disposera donc pour le draft de 52M +1M +2M - 27M = 28M. Comme il a 10 joueurs dans son club régulier et qu’il doit en avoir 22 à la fin du draft, il disposera donc de 28M pour repecher 12 joueurs, soit une moyenne de 2,33M par joueur.</p>


<p>5. BONUS / PÉNALITÉS</p>

<p>Selon la performance de votre club durant la saison, ils vous sera octroyer une bourse, une pénalité ou le status quo au début de chaque </p>
<p>nouvelle saison.</p>

<p>Voici la grille pour une ligue de 10 clubs (avec 11 ou 12 club, on agrandi les équipe sans bonus/malus et avec 8 clubs, on retire les club sans bonus/malus)</p>

<p>1er     =     3 000 000$</p>
<p>2ième   =     2 000 000$</p>
<p>3ième   =     1 000 000$</p>
<p>4ième   =       500 000$</p>
<p>5ième   =     ---------</p>
<p>6ième   =     ---------</p>
<p>7ième   =      -500 000$</p>
<p>8ième   =    -1 000 000$</p>
<p>9ième   =    -2 000 000$</p>
<p>10ième  =    -3 000 000$</p>


<p>6. LES CONTRATS ET OPTIONS</p>

<p>6.0.0   Les contrats offrent la possibilité à un directeur général de garder jusqu'à 11 joueurs dans son grand club et 8 joueurs dans son club école dans</p>
<p>        son équipe pour une durée allant de 1 à 5 ans maximum.</p>
<p>6.1.0   Le DG a la possibilité d'offrir un contrat à un joueur qu'une seule fois (Voir règle 6.0.0), ensuite le joueur doit retourner au draft ou avoir été échangé avant le terme. Il est possible de ré-avoir un joueur après qu’il soit passé par une autre équipe et ceci remet à zéro les règles, donc il peut à nouveau signer un contrat. Exemple, Équipe A possède un joueur nommé X auquel il a fait signer un contrat de 4 ans. Après 2 ans, il l’échange à l’équipe B. Ce dernier à encore 2 ans au contrat et il peut après les 2 premières années prolonger le contrat du Joueur X de 1,2 ou 3 ans. Celui-ci signe le joueur pour 2 autres années. Durant la première année de prolongation, il ré-échange celui-ci à l’équipe A. Celui-ci devra donc honorer le contrat de 2 ans restant et ensuite pourrait le prolonger de 1 à 3 ans. </p>
<p>6.2.0   La valeur annuelle du contrat accordé au joueur demeure la même pour la durée totale du contrat (peu importe la variation de production du joueur).</p>
<p>6.3.0   Un maximum de 11 joueurs peuvent être mis sous contrat pour une même équipe.</p>
<p>6.4.0   Durant la saison, une équipe peut avoir plus de 11 joueurs sous contrats mais elle devra se départir du surplus de joueurs sous contrat avant la tenue du repêchage soit par transaction ou rachat de contrat.</p>

<p>6.5.0   3 options de contrats sont à la disposition des directeurs généraux.</p>


<p>6.5.1   (J/A) :    Ces joueurs ont été repêchés mais le directeur général n'a pas jugé bon de leur accorder un contrat (ou il n'avait plus de place</p>
<p>                   disponible pour le signer ayant déjà le nombre maximum de joueurs sous contrats dans son équipe). (Voir règle 6.2.0).</p>
<p>                   Ces joueurs peuvent par contre obtenir l'option (B) s'il est échangé à une autre équipe en cours de saison (Voir règle 6.5.3).</p>


<p>6.5.2   Option (A): C'est-à-dire que le DG a la possibilité de signer un joueur ''sous contrat'' qu'il a reçu lors d'une transaction pour le nombre d'années restantes (ou moins) à l'entente de garder un joueur sous contrat pour une même équipe pour un maximum de 5 ans. (Voir règle 6.0.0).</p>
<p>L'année au cours de laquelle le joueur est acquis par le nouveau DG devient la première au service de sa nouvelle équipe. Le joueur doit compléter le contrat en vigueur et par la suite le directeur général aura la possibilité de signer le joueur pour le nombre d'années restantes à la règle de 5 ans maximum dans un même club. Le salaire du joueur pour son renouvellement de contrat est établi selon sa production offensive de sa dernière année sous contrat et l'échelle salariale déjà établie.(Voir règle 3.0.0).</p>
<p>        </p>

<p>Exemple 1:  </p>

<p>Toronto acquiert les services de Claude Giroux à qui il reste encore 2 années à son contrat à 5 000 000$/année.</p>
<p>À la fin de sa 2ème année, le DG de Toronto aura donc l'opportunité de signer Claude Giroux pour 3 autres années (ou moins selon les plans du DG) </p>
<p>au salaire équivalant à sa production offensive de la dernière année de son contrat selon l'échelle salariale préétablie.</p>
<p>Si le DG de Toronto juge trop élevée la valeur du joueur ou que celui-ci ne l'intéresse plus, à la fin de son contrat (obtenu d'un autre DG)</p>
<p>Claude Giroux deviendra tout simplement joueur autonome et retournera au repêchage.</p>


<p>Le directeur général n'a la possibilité de signer un joueur repêché ou reçu lors d'une transaction qu'à une occasion.</p>
<p>Il faut donc qu'il évalue bien la valeur, l'âge et le potentiel de chacun des joueurs qu'il désire mettre sous contrat.</p>
<p>Cela signifie donc qu'à la fin de chaques contrats que le directeur général aura octroyé à un joueur, il ne pourra le resigner une autre fois </p>
<p>(Voir règle 6.1.0) sauf s'il le repêche de nouveau (car une fois son contrat terminé le joueur devient alors joueur autonome et donc éligible au</p>
<p>prochain repêchage).</p>

<p>Exemple 2:</p>

<p>Détroit après le repêchage accorde un contrat à Sidney Crosby pour une valeur de 6 500 000$ pour 3 saisons craignant qu'il ne se blesse et être coincé avec son contrat.</p>
<p>Sidney Crosby connait des saisons de 110 pts (1ière saison) et 112 pts (2ième saison) mais connait une baisse de régime à cause d'un conflit avec son entraîneur. Au changement d'entraîneur, il connait un regain offensif mais termine tout de même avec une saison en-deça des attentes causé par son mauvais départ avec une récolte de 70 pts (3ième saison). Suite à cette baisse de production son salaire est maintenant évalué à 3 500 000$. Une véritable aubaine ! MAIS le DG ne peut lui accorder un nouvau contrat (Voir règle 6.1.0). Donc s'il ne l'a pas échangé avant la fin de la dernière période d'échange, celui-ci le perd tout simplement au repêchage où il deviendra joueur autonome.</p>


<p>6.5.3   Option (B):   C'est-à-dire un joueur acquis lors d'une transaction ayant le status (J/A) (Voir règle 6.4.1). Le DG a la possibilité de le signer à la valeur du joueur lors de son acquisition mais en y ajoutant 1 000 000$ (Voir règle 6.5.0) OU à la valeur de la saison offensive que le joueur a connu selon l'échelle salariale établie (Voir règle 3.0.0).en y ajoutant 1 000 000$ à son salaire.(Voir règle 6.5.0).</p>

<p>Exemple 3:</p>

<p>Philadelphie reçoit Artemi Panarin pour une valeur de 1 000 000$ lors d'une transaction. Le joueur a la mention (j/a)(Voir règle 6.4.1). Ce joueur</p>
<p>obtient donc la mention (B)(Voir règle 6.4.3). Il termine la présente campagne avec un total de 82 pts en 82 parties ce qui lui vaudra une </p>
<p>nouvelle évaluation salariale de 4 500 000$ (Voir règle 3.0.1). Le DG de Philadelphie aura donc l'opportunité de signer le joueur au courant de la</p>
<p>période estivale au salaire le plus bas soit: 2 000 000$ par saison (Voir règle 6.5.0). jusqu'à un maximum de 5 années (Voir règle 6.0.0). incluant</p>
<p>l'année où le joueur fût obtenue.</p>

<p>Exemple 4:</p>

<p>NY-Rangers reçoit Patrick Kane pour une valeur de 6 000 000$ lors d'une transaction. Le joueur a la mention (j/a)(Voir règle 6.4.1). Ce joueur obtient donc la mention (B)(Voir règle 6.4.3). Il termine la présente campagne avec un total de 62 pts en 76 parties ce qui lui vaudra une </p>
<p>nouvelle évaluation salariale de 2 500 000$ (Voir règle 3.0.1). Le DG de NY-Rangers aura donc l'opportunité de signer le joueur au courant de la période estivale au salaire le plus bas soit: 3 500 000$ par saison (Voir règle 6.5.0). jusqu'à un maximum de 5 années (Voir règle 6.0.0). incluant</p>
<p>l'année où le joueur fût obtenue.</p>


<p>6.6.0   Au moment de signer un joueur ayant l'option (B) un montant de 1 000 000$ est ajouté afin de maintenir un équilibre budgétaire réaliste dans une dynamique de pool qui se déroule sur une longue période indéterminée (pool à vie) vs les salaires des joueurs. De plus, cela permet d'améliorer le bassin de joueurs à repêcher année après année. </p>

<p>6.7.0 Rachat de contrats:Les DG's auront la possibilité de racheter le ou les contrats de joueurs  indésirables avant la période estivale.</p>
<p>      6.7.1  Le rachat de contrat peut s'effectué avec le budget restant de cette saison combiné (si désiré) avec l'argent reçu lors de transaction.</p>
<p>      6.7.2  Le rachat de contrat peut aussi s'effectué avec le budget de l'année à venir combiné (si désiré) avec l'argent reçu lors de transaction et les bonus obtenus reliés aux performances de votre équipe lors de la saison qui vient de se terminer.</p>
<p>      6.7.3  Dans les 2 cas, pour racheter un contrat vous devrez racheter la MOITIÉ de la ''valeur totale restante du contrat'' du joueur. Dépendamment si vous utilisez l'argent de la saison qui vient de se terminer ou non vous devrez conséquamment y inclure le salaire du joueur lors de cette année ou non.</p>

<p>Exemple 5:</p>

<p>Montréal veut racheter le contrat de son joueur Dion Phaneuf. La saison 2015-16 (saison 1/2) vient de se terminer et son contrat se termine en 2016-17 (saison 2/2) pour 5 000 000$ par année. Il reste 2 000 000$ à son budget d'équipe pour l'année 2015-16 et n'a pas reçu d'argent lors de transaction. Il ne peut donc pas racheter le contrat du joueur avec l'argent de l'année 2015-16 puisque la valeur total du contrat du joueur doit inclure l'année qui vient de se terminer. Le montant aurait été de 5 000 000$ pour racheter le contrat du joueur (5 000 000$ X 2 saisons divisé par 2) (Voir règle 6.7.3). Donc pour le rachat du contrat, le DG devra prendre l'argent à même le budget de la saison prochaine (2016-17) qui est de 52 000 000$ (puisqu'il a terminé</p>
<p>au milieu du classement). Cela lui coûtera donc la somme de 2 500 000$ pour racheter le contrat de son joueur et le DG débutera la saison 2016-17 avec un budget de 49 500 000$ mais moins le joueur indésirable.</p>

<p>Exemple 6:</p>

<p>Boston veut racheter le contrat de son joueur Milan Lucic. La saison 2015-16 (saison 1/4) vient de se terminer et son contrat se termine en 2018-19 (saison 4/4) pour 3 000 000$ par année. Il reste 5 000 000$ à son budget d'équipe pour l'année 2015-16 et il a reçu 2 000 000$ en argent lors de transactions durant la saison qui vient de se terminer. Il peut donc racheter le contrat du joueur avec l'argent de l'année 2015-16 et son argent reçu puisque la valeur total du contrat du joueur doit inclure l'année qui vient de se terminer. Le montant du contrat à racheter est de 6 000 000$ </p>
<p>(3 000 000$ X 4 saisons divisé par 2) (Voir règle 6.7.3). Donc pour le rachat du contrat, le DG devra prendre l'argent de son budget restant de l'année 2016-17 qui est de 5 000 000$ et y ajouter 1 000 000$ de l'argent qu'il a reçu pour racheter la totalité du contrat. Cela lui coûtera donc la somme de 6 000 000$ pour racheter le contrat de son joueur (ce qui est nettement plus avantageux que de payer les 3 années restantes avec le budget de la saison 2016-17 ce qui lui </p>
<p>aurait causé un trou de 4 500 000$ dans son budget versus un budget intacte utilisant la méthode décrite précédemment) et le DG débutera la saison 2016-17 avec un budget de 52 000 000$, plus 1 000 000$ en argent reçu ET moins le joueur indésirable.</p>



<p>7. TRANSACTIONS</p>

<p>7.0.0    Les DG's ont l'opportunité de transiger des joueurs, recrues (du club école), argent (argent virtuel du PAV), et choix au repêchage.</p>
<p>7.1.0    Il y a 3 périodes d'une durée de 2 heures qui sont allouées pour transiger durant le cours de la saison déterminée selon les disponibilités des DG's.</p>
<p>7.2.0    Il est aussi permis de transiger durant la rencontre estivale (lorsqu'elle a lieu) et lors du repêchage.</p>
<p>7.2.1    Lors de la période estivale et le repêchage, seul les joueurs sous contrat, les recrues (club école), les choix au repêchage et l'argent peuvent</p>
<p>         changer de propriétaires.</p>
<p>7.3.0    Les DG's doivent respecter les contrats consentis aux joueurs reçus lors de transaction. </p>
<p>         Ces joueurs sont soumis aux règles suivantes: 6.5.1 - 6.5.2 - 6.5.3</p>
<p>7.3.1    Les DG's doivent respecter les contrats consentis aux joueurs reçus lors de transaction. </p>
<p>         Ces équipes sont soumis aux règles suivantes: 6.0.0 - 6.1.0 - 6.2.0 - 6.3.0 et 6.4.0</p>
<p>7.4.0    Un DG ne peut donner plus de 2 000 000$ en argent lors d'une même transaction.</p>
<p>7.4.1    Un DG ne peut échanger de l'argent contre de l'argent.</p>
<p>7.4.2    Un DG ne peut échanger de l'argent contre une ronde (cas d'exception: lors du repêchage cette manoeuvre est permise).</p>
<p>7.4.3    Un DG ne peut échanger de l'argent contre un joueur n'ayant aucune valeur au partenaire de transaction (le DG n'a pas l'intention de garder le </p>
<p>         joueur reçu ou n'apporte aucun avantage au classement).</p>
<p>7.4.4    Seulement les choix au repêchage de la saison à venir peuvent être échangés (On ne peut échanger les rondes de saisons 2-3-4... saisons à venir). (cas d'exception: lors du repêchage seulement les rondes du présent repêchage peuvent être transigées).</p>
<p>7.5.0    La saison durant laquelle un joueur est acquis, est comptabilisée au nombre d'année au service de cette même équipe (Voir règle 6.0.0).</p>
<p>7.6.0    Une transaction peut être refusée si la majorité des DG's jugent qu'il y a tentative de collusion ou tentative de contourner un règlement (VETO).</p>
<p> </p>

<p>8. CLUB ÉCOLE</p>

<p>8.0.0    Votre club école est composé de 8 joueurs MAXIMUM au ''début de la saison'' (soit au moment du repêchage).</p>
<p>8.1.0    Les 8 joueurs composants votre club école peuvent être la combinaison voulue d'attaquant(s), défenseurs (s) et gardien (s).</p>
<p>8.2.0    En cours de saison un club école peut contenir plus ou moins de 8 joueurs selon les échanges effectuées par les DG's.</p>
<p>8.3.0    Les salaires des 8 joueurs composant votre club école ne sont pas comptabilisés dans le budget de 52 000 000$.</p>
<p>8.4.0    Les contrats consentis à vos joueurs formant votre club école ne sont pas comptabilisés sur votre total de 11 contrats maximum.</p>
<p>8.5.0    Pour qu'un joueur puisse être illégible à faire partie de votre club école, il doit être agé de MOINS de 23 ans en date du 15 septembre de l'année en</p>
<p>         cours lors du repêchage.</p>
<p>8.6.0    Avant chaque début de saison, les DG's auront l'opportunité de décider du sort des joueurs formant leur club école.</p>
<p>         </p>
<p>         8.6.1  Le DG peut garder son joueur dans son club école.</p>
<p>         8.6.2  Le DG peut faire graduer son joueur dans son grand club (voir règle 8.7.0)</p>
<p>         8.6.3  Le DG peut terminer l'association avec le joueur qui devient joueur autonome (j/a) et retourne au repêchage où il devient disponible pour toutes les équipes (incluant l'équipe qui l'a libérée).</p>

<p>8.7.0  Un joueur nouvellement gradué se verra accordé un contrat dans le grand club (jusqu'à un maximum de 5 années: déductible du nombre d'année déjà en propriété de la dite équipe)(Voir règle 6.0.0) au salaire auquel il a été repêché (Voir règle 6.2.0).</p>

<p>Exemple 5:  Un joueur ayant passé 2 saisons dans le club école d'une même équipe aura l'opportunité de graduer dans le grand club pour une durée de 3 ans (pour un total de 5 années au sein de la même organisation). (voir règle 6.0.0)</p>

<p>8.8.0    Si un joueur est agé de plus de 25 ans, le DG doit décider du sort du joueur avant la tenue du repêchage à une étape pré déterminée par votre administrateur au cours du cycle de votre saison (Voir règle 8.6.2 et règle 8.6.3).</p>
<p>         </p>
<p>8.9.0    Un DG a la possibilité de descendre un joueur du grand club dans son club école. Par contre ce joueur doit répondre à la règle 8.5.0.</p>
<p>         </p>
<p>         8.9.1  Le DG devra aussi payer une taxe de 1 000 000$ afin d'effectuer cette manoeuvre.</p>
<p>         8.9.2  La date de l'opération sera pré déterminée par votre administrateur pour le cycle de votre saison.</p>
	
	

	
	
	</div>
	
	<jsp:directive.include file="../utils/draftMessage.jsp" />
	
<c:if test="${Pool.draftType==1&&Pool.cycleAnnuel==3&&DraftOnline.token!=null}">
<jsp:directive.include file="../utils/draftClientB.jsp" />
</c:if>
<script>
	document.getElementById('menuSecRule').classList.add('w3-khaki');
	</script>
</body>
</html>