<script type="text/javascript">
		function removeProgressBar() {
			document.getElementById('progressBar').classList.remove('w3-show');
			document.getElementById('progressBar').classList.add('w3-hide');
		}
		function progressbar() {
			document.getElementById('marketing1').classList.add('w3-overlay');
			document.getElementById('marketing1').classList.remove('background-image');
			document.getElementById('marketing2').classList.add('w3-hide');
			document.getElementById('marketing3').classList.add('w3-hide');
			document.getElementById('footerAccueil').classList.add('w3-hide');
			document.getElementById('hrAccueil1').classList.add('w3-hide');
			document.getElementById('hrAccueil2').classList.add('w3-hide');
			document.getElementById('progressBar').classList.remove('w3-hide');
			document.getElementById('progressBar').classList.add('w3-show');
			var elem = document.getElementById("myBar");
			var width = 0;
			var id = setInterval(frame, 100);
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