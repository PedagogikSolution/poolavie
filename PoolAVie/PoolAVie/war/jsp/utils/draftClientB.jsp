<script type="text/javascript">
   
   
   
	onOpened = function() {
		var xhttp = new XMLHttpRequest();
	
		  xhttp.open("POST", "/ConnectDraft", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send("testIfOpen=1");
	};

	onMessage = function(m) {
		messageReceived = JSON.parse(m.data);
		testIfOpen = messageReceived.testIfOpen;
		draftPickMade = messageReceived.draftPickMade;
		// connexion avec les serveurs établie
		if(testIfOpen==1){
			document.getElementById('connexionAlert').classList.remove('w3-hide');
			document.getElementById('connexionAlert').classList.add('w3-show');
			document.getElementById('mainContainer').classList.add('w3-opacity');
			mainContainer
			
		}
		// regular player drafted
		else if(draftPickMade==1){
			teamThatDraft = messageReceived.teamThatDraft;
			round = messageReceived.round;
			pickNumber = messageReceived.pickNumber;
			fromWho = messageReceived.fromWho;
			playerDrafted = messageReceived.playerDrafted;
			teamOfPlayer = messageReceived.teamOfPlayer;
			salaire = messageReceived.salaire;
			position = messageReceived.position;
			
			document.getElementById('draftPickAlert').classList.remove('w3-hide');
			document.getElementById('draftPickAlert').classList.add('w3-show');
			document.getElementById('pickNumber').innerHTML=pickNumber;
			document.getElementById('teamThatDraft').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted').innerHTML=playerDrafted;
			document.getElementById('position').innerHTML=position;
			document.getElementById('teamOfPlayer').innerHTML=teamOfPlayer;
			document.getElementById('salaire').innerHTML=salaire;
			
		}
		// recrue drafted
		else if(draftPickMade==2){
			teamThatDraft = messageReceived.teamThatDraft;
			round = messageReceived.round;
			pickNumber = messageReceived.pickNumber;
			fromWho = messageReceived.fromWho;
			playerDrafted = messageReceived.playerDrafted;
			teamOfPlayer = messageReceived.teamOfPlayer;
			salaire = messageReceived.salaire;
			position = messageReceived.position;
			document.getElementById('draftPickAlertRookie').classList.remove('w3-hide');
			document.getElementById('draftPickAlertRookie').classList.add('w3-show');
			document.getElementById('pickNumber').innerHTML=pickNumber;
			document.getElementById('teamThatDraft').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted').innerHTML=playerDrafted;
			document.getElementById('position').innerHTML=position;
			document.getElementById('teamOfPlayer').innerHTML=teamOfPlayer;
			document.getElementById('salaire').innerHTML=salaire;
		} else if(draftPickMade==3){
			teamThatDraft = messageReceived.teamThatDraft;
			round = messageReceived.round;
			pickNumber = messageReceived.pickNumber;
			fromWho = messageReceived.fromWho;
			playerDrafted = messageReceived.playerDrafted;
			teamOfPlayer = messageReceived.teamOfPlayer;
			salaire = messageReceived.salaire;
			position = messageReceived.position;
			document.getElementById('draftPickAlertFinish').classList.remove('w3-hide');
			document.getElementById('draftPickAlertFinish').classList.add('w3-show');
			document.getElementById('pickNumber').innerHTML=pickNumber;
			document.getElementById('teamThatDraft').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted').innerHTML=playerDrafted;
			document.getElementById('position').innerHTML=position;
			document.getElementById('teamOfPlayer').innerHTML=teamOfPlayer;
			document.getElementById('salaire').innerHTML=salaire;
			
		} else if(draftPickMade==4){
			teamThatDraft = messageReceived.teamThatDraft;
			round = messageReceived.round;
			pickNumber = messageReceived.pickNumber;
			fromWho = messageReceived.fromWho;
			playerDrafted = messageReceived.playerDrafted;
			teamOfPlayer = messageReceived.teamOfPlayer;
			salaire = messageReceived.salaire;
			position = messageReceived.position;
			document.getElementById('draftPickAlertRookieFinish').classList.remove('w3-hide');
			document.getElementById('draftPickAlertRookieFinish').classList.add('w3-show');
			document.getElementById('pickNumber').innerHTML=pickNumber;
			document.getElementById('teamThatDraft').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted').innerHTML=playerDrafted;
			document.getElementById('position').innerHTML=position;
			document.getElementById('teamOfPlayer').innerHTML=teamOfPlayer;
			document.getElementById('salaire').innerHTML=salaire;
			
		} else {
			
			
		}
		
		
	};
	
	onError = function() {
		var xhttp = new XMLHttpRequest();
		
		  xhttp.open("POST", "/ConnectDraft", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send("error=1");
	};
		
	onClose = function() {
		var xhttp = new XMLHttpRequest();
		
		  xhttp.open("POST", "/ConnectDraft", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send("close=1");
	};
	openChannel = function() {

		var channel = new goog.appengine.Channel('${DraftOnline.token}');
		var handler = {
			'onopen' : onOpened,
			'onmessage' : onMessage,
			'onerror' : onError,
			'onclose' : onClose
		};
		var socket = channel.open(handler);

		socket.onopen = onOpened;
		socket.onmessage = onMessage;
		$(window).on('beforeunload', function() {
			clearTimeout(socket.pollingTimer_);
		});
	}
	initialize = function() {
		openChannel();

	}
	setTimeout(initialize, 100);
</script>