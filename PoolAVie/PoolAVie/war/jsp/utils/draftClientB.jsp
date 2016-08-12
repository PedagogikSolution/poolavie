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
			alert("Vous êtes maintenant connecté au serveur de draft");
			
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
			alert(teamThatDraft + " a repêcher avec le "+pickNumber+"ième choix overall : "+playerDrafted+" ,"+position+", de "+teamOfPlayer+" au salaire de "+salaire+"$");
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
			alert("Vous avez choisis une rookie");
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