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
	
	case 7 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/CBJ.png";
	break;
case 8 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/CHI.png";
break;
case 9 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/COL.png";
break;
case 10 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/DAL.png";
break;
case 11 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/DET.png";
break;
case 12 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/EDM.png";
break;

case 13 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/FLA.png";
break;
case 14 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/LAK.png";
break;
case 15 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/MIN.png";
break;
case 16 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/MTL.png";
break;
case 17 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/NJD.png";
break;
case 18 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/NSH.png";
break;

case 19 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/NYI.png";
break;
case 20 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/NYR.png";
break;
case 21 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/OTT.png";
break;
case 22 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/PHI.png";
break;
case 23 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/PIT.png";
break;
case 24 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/QUE.png";
break;

case 25 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/SJS.png";
break;
case 26 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/STL.png";
break;
case 27 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/TBL.png";
break;
case 28 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/TOR.png";
break;
case 29 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/VAN.png";
break;
case 30 :  urlLogo = "https://storage.googleapis.com/poolavie-bucket/WSH.png";
break;
	
	}
	document.getElementById("logoUrl").value = urlLogo;
	document.getElementById('teamLogoPicker').classList.add('w3-hide');
	document.getElementById('teamLogoPicker').classList.remove('w3-show');

	document.getElementById('logoTeamChosen').classList.remove('w3-hide');
	document.getElementById('logoTeamChosen').classList.add('w3-show');
	document.getElementById('logoTeamChosen').src=urlLogo;
}
