function onLoginButtonClick() {
	document.getElementById("pub_main_content_home").style.display="none";
	document.getElementById("register_home").style.display="none";
    document.getElementById("login_home").style.display="block";
	
}

function onLogoClick() {
	location.reload();
}

function onSignInButtonClick() {
	document.getElementById("pub_main_content_home").style.display="none";
	document.getElementById("login_home").style.display="none";
    document.getElementById("register_home").style.display="block";
	
}