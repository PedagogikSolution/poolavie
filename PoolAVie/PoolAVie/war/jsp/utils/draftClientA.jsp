<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
		if(testIfOpen==1){
			alert("Vous �tes maintenant connect� au serveur de draft");
		} else {
			
		}
		
		
		
	};

	openChannel = function() {

		var channel = new goog.appengine.Channel('${DraftOnline.token}');
		var handler = {
			'onopen' : onOpened,
			'onmessage' : onMessage,
			'onerror' : function() {
			},
			'onclose' : function() {
			}
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
</script>