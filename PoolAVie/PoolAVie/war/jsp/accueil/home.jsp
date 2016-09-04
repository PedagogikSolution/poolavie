<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Pool à vie</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="w3-theme-blue.css">
<link rel="stylesheet" type="text/css" href="css/home.css" />
<script type="text/javascript" src="js/login.js"></script>
</head>

<body>

<div class="navbar">
<div id="logo" onclick="onLogoClick()">POOL À VIE</div>
<div id="slogan">le meilleur site de pool au monde</div>
<div id="btn_login"><button onclick="onLoginButtonClick()">Login</button></div>
  <div id="btn_sign_in"><button onclick="onSignInButtonClick()">Sign Up</button></div> 
</div>

<hr class="hr_header">

<div class="main_container">

<div id="pub_main_content_home">
<h1>Bienvenue sur le site des vrais poolers de hockey</h1>
<p>Pool à vie est un site de gestion des pools de hockey qui propose une plateforme
 vous permettant de créer et gérer votre pool de hockey sur plusieurs saison. Il permet l'extension du concept des pools de Keepers
 avec une gestion élaborer des salaires, des contrats, des club-écoles et des échanges ainsi que la mise en place d'un système
 informatisé de draft live et de l'administration des différentes phases du cycle de vie avec des fonctionnalités informatisées
  tel que les signatures de contrat, rachat de contrat, monter et descendre des recrues.</p>

</div>

<div id="login_home">
<form name="input" action="/login" method="post">
Nom d'utilisateur: <input type="text" name="user">
<br>
<br>
Mot de passe: <input type="text" name="password">
<br>
<br>
<input type="submit" value="Me connecter">
</form>
</div>

<div id="register_home">
<form name="input" action="/register" method="post">
Choisis un nom d'utilisateur: <input type="text" name="user_reg">
<br>
<br>
Choisis un mot de passe: <input type="text" name="password_reg">
<br>
<br>
Entre ton email pour recevoir des offres de trade: <input type="text" name="email_reg">
<br>
<br>
Sélectionne ton équipe : <br>
<input type="radio" name="team_reg" value="Detroit">Détroit <br>
<input type="radio" name="team_reg" value="Montreal">Montreal <br>
<input type="radio" name="team_reg" value="Chicago">Chicago <br>
<input type="radio" name="team_reg" value="New York">New York <br>
<input type="radio" name="team_reg" value="Philadelphie">Philadelphie <br>
<input type="radio" name="team_reg" value="Toronto">Toronto <br>
<input type="radio" name="team_reg" value="St-Louis">St-Louis <br>
<input type="radio" name="team_reg" value="Boston">Boston <br>
<input type="radio" name="team_reg" value="Pittsburgh">Pittsburgh <br>
<input type="radio" name="team_reg" value="Los Angeles">Los Angeles <br>
<input type="radio" name="team_reg" value="Richard">Richard <br>
<input type="radio" name="team_reg" value="Fred">Fred <br>

<br>
<input type="submit" value="S'enregistrer">
</form>
</div>

<div id="sidebar_main_content_home">
<img id="image_coupe" alt="Image de la coupe" src="images/coupe.png">
</div>


</div>

</body>

<%
   Object messageRecuFromServlet = request.getAttribute("login_message");
if(messageRecuFromServlet!=null){
	String loginMessage = messageRecuFromServlet.toString();
	int mIntLoginMessage = Integer.parseInt(loginMessage);

	switch(mIntLoginMessage){
	case 0 : request.removeAttribute("login_message");
	%> <script> window.onload = connexionFail(); </script> <%
	break;
	case 1 : request.removeAttribute("login_message");
	%> <script> window.onload = notRegister(); </script> <%
	break;
	case 2 : request.removeAttribute("login_message");
	%> <script> window.onload = wrongPassword(); </script> <%
	break;
	case 3 : request.removeAttribute("login_message");
	%> <script> window.onload = teamExist(); </script> <%
	break;
	case 4 : request.removeAttribute("login_message");
	%> <script> window.onload = erreurBD(); </script> <%
	break;
	} 
	
	
}
   
%>
</html>