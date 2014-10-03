function onLoginButtonClick() {
	document.getElementById("pub_main_content_home").style.display="none";
	document.getElementById("register_home").style.display="none";
    document.getElementById("login_home").style.display="block";
	
}

function onLogoClick() {	
	window.location.assign("http://localhost:8888/");
}

function onSignInButtonClick() {
	document.getElementById("pub_main_content_home").style.display="none";
	document.getElementById("login_home").style.display="none";
    document.getElementById("register_home").style.display="block";
	
}

function connexionFail() {
	alert("Probleme de connexion!");
	onLogoClick();
}

function notRegister() {
	alert("Vous n'êtes pas enregistrer, veuillez créer votre compte!");
	onSignInButtonClick();
}

function wrongPassword() {
	alert("Mauvais mot de passe!");
	onLoginButtonClick();
}
function teamExist() {
	alert("Cette équipe existe déjà!");
	onLoginButtonClick();
	onSignInButtonClick();
}
function erreurBD() {
	alert("Voir Frank");
	onLoginButtonClick();
	onSignInButtonClick();
}