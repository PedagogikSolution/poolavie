<div id="connexionAlert"
			class="w3-container w3-hide w3-display-middle">

			<span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
			<div class="w3-container w3-orange">
				<h2>Vous êtes connectez au serveur de Draft</h2>
			</div>
			

			<form class="w3-container w3-card-24 w3-white" method="get">


				<button class="w3-btn w3-orange w3-xlarge">Hell Yeah!</button>

				<br>

			</form>




		</div>


		<div id="draftPickAlert"
			class="w3-container w3-hide w3-display-middle">

			<span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
			<div class="w3-container w3-orange">
				<h2>We got a Pick!!!</h2>
			</div>
			<div>
				<p>
					<span id="teamThatDraft"></span> a repêcher avec le <span
						id="pickNumber"></span>ième choix overall : <br> <span
						id="playerDrafted"></span> ,<span id="position"></span>, de <span
						id="teamOfPlayer"></span> <br> au salaire de <span
						id="salaire"></span>$
				</p>

			</div>

			<form class="w3-container w3-card-24 w3-white" method="get">


				<button class="w3-btn w3-orange w3-xlarge">Ok</button>

				<br>

			</form>




		</div>
		
		<div id="draftPickAlertRookie"
			class="w3-container w3-hide w3-display-middle">

			<span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
			<div class="w3-container w3-orange">
				<h2>We got a Pick!!!</h2>
			</div>
			<div>
				<p>
					<span id="teamThatDraft"></span> a repêcher avec le <span
						id="pickNumber"></span>ième choix overall : <br> <span
						id="playerDrafted"></span> ,<span id="position"></span>, de <span
						id="teamOfPlayer"></span> <br> au salaire de <span
						id="salaire"></span>$ Il place celui-ci dans son club école.
				</p>

			</div>

			<form class="w3-container w3-card-24 w3-white" method="get">


				<button class="w3-btn w3-orange w3-xlarge">Ok</button>

				<br>

			</form>




		</div>
		
		<div id="draftPickAlertFinish"
			class="w3-container w3-hide w3-display-middle">

			<span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
			<div class="w3-container w3-orange">
				<h2>We got a Pick!!!</h2>
			</div>
			<div>
				<p>
					<span id="teamThatDraft"></span> a repêcher avec le <span
						id="pickNumber"></span>ième choix overall : <br> <span
						id="playerDrafted"></span> ,<span id="position"></span>, de <span
						id="teamOfPlayer"></span> <br> au salaire de <span
						id="salaire"></span>$. 
				</p>
				
				<h2>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA SECTION SIGNATURE POUR DONNER DES CONTRATS À VOS JOUEURS REPÊCHÉS</h2>

			</div>

			<form class="w3-container w3-card-24 w3-white" method="get">


				<button class="w3-btn w3-orange w3-xlarge">Ok</button>

				<br>

			</form>




		</div>
		
		<div id="draftPickAlertRookieFinish"
			class="w3-container w3-hide w3-display-middle">

			<span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
			<div class="w3-container w3-orange">
				<h2>We got a Pick!!!</h2>
			</div>
			<div>
				<p>
					<span id="teamThatDraft"></span> a repêcher avec le <span
						id="pickNumber"></span>ième choix overall : <br> <span
						id="playerDrafted"></span> ,<span id="position"></span>, de <span
						id="teamOfPlayer"></span> <br> au salaire de <span
						id="salaire"></span>$ Il place celui-ci dans son club école.
				</p>
				
				
				<h2>LE DRAFT EST FINI. VOUS POUVEZ MAINTENANT ALLER DANS LA SECTION SIGNATURE POUR DONNER DES CONTRATS À VOS JOUEURS REPÊCHÉS</h2>
				

			</div>

			<form class="w3-container w3-card-24 w3-white" method="get">


				<button class="w3-btn w3-orange w3-xlarge">Ok</button>

				<br>

			</form>




		</div>
		
		<c:if test="${sessionScope.DraftFinish==1}">
		<div id="connexionAlert"
			class="w3-container w3-hide w3-display-middle">

			<span onclick="closePostForm()" class="w3-closebtn w3-hover-text-red">&times;</span>
			<div class="w3-container w3-orange">
				<h2>Votre draft est terminé</h2>
			</div>
			

			<form class="w3-container w3-card-24 w3-white" method="get">


				<button class="w3-btn w3-orange w3-xlarge">Cool</button>

				<br>

			</form>




		</div>
		</c:if>
