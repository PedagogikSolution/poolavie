function m8players(){
	document.getElementById('j8').classList.remove('w3-show');
	document.getElementById('j8').classList.add('w3-hide');
	document.getElementById('j9').classList.remove('w3-show');
	document.getElementById('j9').classList.add('w3-hide');
	document.getElementById('j10').classList.remove('w3-show');
	document.getElementById('j10').classList.add('w3-hide');
	document.getElementById('j11').classList.remove('w3-show');
	document.getElementById('j11').classList.add('w3-hide');
}

function m9players(){
	document.getElementById('j8').classList.remove('w3-hide');
	document.getElementById('j8').classList.add('w3-show');
	document.getElementById('j9').classList.remove('w3-show');
	document.getElementById('j9').classList.add('w3-hide');
	document.getElementById('j10').classList.remove('w3-show');
	document.getElementById('j10').classList.add('w3-hide');
	document.getElementById('j11').classList.remove('w3-show');
	document.getElementById('j11').classList.add('w3-hide');
	
}

function m10players(){	
	document.getElementById('j8').classList.remove('w3-hide');
	document.getElementById('j8').classList.add('w3-show');
	document.getElementById('j9').classList.remove('w3-hide');
	document.getElementById('j9').classList.add('w3-show');
	document.getElementById('j10').classList.remove('w3-show');
	document.getElementById('j10').classList.add('w3-hide');
	document.getElementById('j11').classList.remove('w3-show');
	document.getElementById('j11').classList.add('w3-hide');
}


function m11players(){
	document.getElementById('j8').classList.remove('w3-hide');
	document.getElementById('j8').classList.add('w3-show');
	document.getElementById('j9').classList.remove('w3-hide');
	document.getElementById('j9').classList.add('w3-show');
	document.getElementById('j10').classList.remove('w3-hide');
	document.getElementById('j10').classList.add('w3-show');
	document.getElementById('j11').classList.remove('w3-show');
	document.getElementById('j11').classList.add('w3-hide');
}

function m12players(){
	document.getElementById('j8').classList.remove('w3-hide');
	document.getElementById('j8').classList.add('w3-show');
	document.getElementById('j9').classList.remove('w3-hide');
	document.getElementById('j9').classList.add('w3-show');
	document.getElementById('j10').classList.remove('w3-hide');
	document.getElementById('j10').classList.add('w3-show');
	document.getElementById('j11').classList.remove('w3-hide');
	document.getElementById('j11').classList.add('w3-show');
}

function openTeamLogoPicker(){
	document.getElementById('teamLogoPicker').classList.remove('w3-hide');
	document.getElementById('teamLogoPicker').classList.add('w3-show');	
}

function closeLogoTeamPicker(){
	document.getElementById('teamLogoPicker').classList.add('w3-hide');
	document.getElementById('teamLogoPicker').classList.remove('w3-show');	
}

function logoTeamchosen(logoId) {
	var urlLogo;
	switch (logoId) {
	case 1 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/ANA.png";
		break;
	case 2 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/ARI.png";
	break;
	case 3 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/ATL.png";
	break;
	case 4 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/BOS.png";
	break;
	case 5 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/BUF.png";
	break;
	case 6 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/CAR.png";
	break;
	
	}
	document.getElementById("logoUrl").value = urlLogo;
	document.getElementById('teamLogoPicker').classList.add('w3-hide');
	document.getElementById('teamLogoPicker').classList.remove('w3-show');

	document.getElementById('logoTeamChosen').classList.remove('w3-hide');
	document.getElementById('logoTeamChosen').classList.add('w3-show');
	document.getElementById('logoTeamChosen').src=urlLogo;
}
