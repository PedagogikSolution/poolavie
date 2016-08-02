<script type="text/javascript">
   
   
   
	onOpened = function() {
		var xhttp = new XMLHttpRequest();
	
		  xhttp.open("POST", "/ConnectDraft", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send("testIfOpen=1");
	};

	onMessage = function(m) {
		newState = JSON.parse(m.data);
		testIfOpen = newState.testIfOpen;
		pickMadeMessage = newState.pickMadeMessage;
		if(testIfOpen==1){
			alert("Vous êtes maintenant connecté au serveur de draft");
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