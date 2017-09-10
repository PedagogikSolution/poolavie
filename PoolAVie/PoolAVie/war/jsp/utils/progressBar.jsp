<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script type="text/javascript">
		function removeProgressBar() {
			document.getElementById('progressBar').classList.remove('w3-show');
			document.getElementById('progressBar').classList.add('w3-hide');
		}
		function progressbar(from) {
			
			
			switch(from){
			case 1:
				document.getElementById('marketing1').classList.add('w3-overlay');
				document.getElementById('marketing1').classList.remove('background-image');
				document.getElementById('marketing2').classList.add('w3-hide');
				document.getElementById('marketing3').classList.add('w3-hide');
				document.getElementById('footerAccueil').classList.add('w3-hide');
				document.getElementById('hrAccueil1').classList.add('w3-hide');
				document.getElementById('hrAccueil2').classList.add('w3-hide');
				break;
			case 2:
				document.getElementById('showOfferDetailMainDiv').classList.add('w3-hide');
				document.getElementById('showOfferDetailMessageDG').classList.add('w3-hide');
				document.getElementById('showOfferDetailAnnulerOffre').classList.add('w3-hide');
				document.getElementById('showOfferDetailAccepterRefuserOffre').classList.add('w3-hide');
				document.getElementById('hr3').classList.add('w3-hide');
				document.getElementById('hr4').classList.add('w3-hide');
				
				
				
				
				break;
			case 3:
				document.getElementById('trade_offer_sheet_main').classList.add('w3-hide');
				document.getElementById('trade_offer_sheet_messageDg').classList.add('w3-hide');
				document.getElementById('trade_offer_sheet_confirmation').classList.add('w3-hide');
				document.getElementById('hr1').classList.add('w3-hide');
				document.getElementById('hr2').classList.add('w3-hide');
				break;
				
			}
			
			document.getElementById('progressBar').classList.remove('w3-hide');
			document.getElementById('progressBar').classList.add('w3-show');
			var elem = document.getElementById("myBar");
			var width = 0;
			var id = setInterval(frame, 200);
			function frame() {
				if (width >= 100) {
					clearInterval(id);
				} else {
					width++;
					elem.style.width = width + '%';
					document.getElementById("demo").innerHTML = width * 1 + '%';
					if(width==20){
						document.getElementById('progressMessage1').classList.remove('w3-show');
						document.getElementById('progressMessage1').classList.add('w3-hide');
						document.getElementById('progressMessage2').classList.remove('w3-hide');
						document.getElementById('progressMessage2').classList.add('w3-show');
					}
					if(width==40){
						document.getElementById('progressMessage2').classList.remove('w3-show');
						document.getElementById('progressMessage2').classList.add('w3-hide');
						document.getElementById('progressMessage3').classList.remove('w3-hide');
						document.getElementById('progressMessage3').classList.add('w3-show');
					}
					if(width==60){
						document.getElementById('progressMessage3').classList.remove('w3-show');
						document.getElementById('progressMessage3').classList.add('w3-hide');
						document.getElementById('progressMessage4').classList.remove('w3-hide');
						document.getElementById('progressMessage4').classList.add('w3-show');
					}
					if(width==80){
						document.getElementById('progressMessage4').classList.remove('w3-show');
						document.getElementById('progressMessage4').classList.add('w3-hide');
						document.getElementById('progressMessage5').classList.remove('w3-hide');
						document.getElementById('progressMessage5').classList.add('w3-show');
					}
				}
			}

	}
</script>