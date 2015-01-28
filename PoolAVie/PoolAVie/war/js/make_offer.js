function onNewOfferButtonClick() {
	document.getElementById("formulaire_make_offer").style.display="block";
	document.getElementById("main_content_table_classement").style.display="none";
	document.getElementById("main_content_title_classement").style.display="none";
	document.getElementById("main_content_title_classement2").style.display="block";
	
	
}



function onAnnulerNewOfferButtonClick() {
	document.getElementById("formulaire_make_offer").style.display="none";
	document.getElementById("main_content_table_classement").style.display="block";
	document.getElementById("main_content_title_classement").style.display="block";
	document.getElementById("main_content_title_classement2").style.display="none";
	
}