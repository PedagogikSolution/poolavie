function newPost(){
	
	document.getElementById('all').classList.add('w3-overlay');
	document.getElementById('all').classList.add('w3-show');
	document.getElementById('postForm').classList.remove('w3-hide');
	document.getElementById('postForm').classList.add('w3-show');
	document.getElementById('btnNewPost').classList.add('w3-hide');
	

	
	
}

function closePostForm(){
	document.getElementById('postForm').classList.remove('w3-show');
	document.getElementById('postForm').classList.add('w3-hide');
	document.getElementById('all').classList.remove('w3-overlay');
	document.getElementById('btnNewPost').classList.remove('w3-hide');
	document.getElementById('titreNouvelle').value ="";
	document.getElementById('corpsMessageNouvelle').value ="";
	
	
}

