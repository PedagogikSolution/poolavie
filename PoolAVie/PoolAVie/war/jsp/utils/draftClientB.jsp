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
		// connexion avec les serveurs �tablie
		if(testIfOpen==1){
			document.getElementById('connexionAlert').classList.remove('w3-hide');
			document.getElementById('connexionAlert').classList.add('w3-show');
			document.getElementById('mainContainer').classList.add('w3-opacity');
			
			
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
			document.getElementById('draftPickAlertRookie').classList.remove('w3-show');
			document.getElementById('draftPickAlertRookie').classList.add('w3-hide');
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
			document.getElementById('draftPickAlert').classList.remove('w3-show');
			document.getElementById('draftPickAlert').classList.add('w3-hide');
			document.getElementById('pickNumber2').innerHTML=pickNumber;
			document.getElementById('teamThatDraft2').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted2').innerHTML=playerDrafted;
			document.getElementById('position2').innerHTML=position;
			document.getElementById('teamOfPlayer2').innerHTML=teamOfPlayer;
			document.getElementById('salaire2').innerHTML=salaire;
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
			document.getElementById('draftPickAlertRookieFinish').classList.remove('w3-show');
			document.getElementById('draftPickAlertRookieFinish').classList.add('w3-hide');
			document.getElementById('pickNumber3').innerHTML=pickNumber;
			document.getElementById('teamThatDraft3').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted3').innerHTML=playerDrafted;
			document.getElementById('position3').innerHTML=position;
			document.getElementById('teamOfPlayer3').innerHTML=teamOfPlayer;
			document.getElementById('salaire3').innerHTML=salaire;
			
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
			document.getElementById('draftPickAlertFinish').classList.remove('w3-show');
			document.getElementById('draftPickAlertFinish').classList.add('w3-hide');
			document.getElementById('pickNumber4').innerHTML=pickNumber;
			document.getElementById('teamThatDraft4').innerHTML=teamThatDraft;
			document.getElementById('playerDrafted4').innerHTML=playerDrafted;
			document.getElementById('position4').innerHTML=position;
			document.getElementById('teamOfPlayer4').innerHTML=teamOfPlayer;
			document.getElementById('salaire4').innerHTML=salaire;
			
		} else {
			
			
		}
		
		
	};
	
	onError = function() {
		
	};
		
	onClose = function() {
		  var xhttp = new XMLHttpRequest();
		
		  xhttp.open("POST", "/ConnectDraft", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send("close=1");
		  openChannel();
		  
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
		socket.onclose = onClose;
		$(window).on('beforeunload', function() {
			clearTimeout(socket.pollingTimer_);
		});
	}
	initialize = function() {
		openChannel();

	}
	// setTimeout(initialize, 100);
</script>