<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pool � vie</title>
<link rel="stylesheet" type="text/css" href="css/home.css" />
<script type="text/javascript" src="js/login.js"></script>
</head>
<body>

<div class="navbar">
<div id="logo" onclick="onLogoClick()">POOL � VIE</div>
<div id="slogan">le meilleur site de pool au monde</div>
<div id="btn_login"><button onclick="onLoginButtonClick()">Login</button></div>
<div id="btn_sign_in"><button onclick="onSignInButtonClick()">Sign In</button></div>
</div>

<hr id="hr_header">

<div class="main_container">

<div id="pub_main_content_home">
<h1>Bienvenue sur le site des vrais poolers de hockey</h1>
<p>Pool � vie est un site de gestion des pools de hockey qui propose une plateforme
 vous permettant de cr�er et g�rer votre pool de hockey sur plusieurs saison. Il �tant le concept des pools de Keepers
 avec une gestion �laborer des salaires, des contrats, des club-�coles et des �changes ainsi que la mise en place d'un syst�me
 informatis� de draft live.</p>
</div>

<div id="login_home">
<form name="input" action="/login" method="post">
Username: <input type="text" name="user">
<br>
Password: <input type="text" name="password">
<br>
<input type="submit" value="LOGIN">
</form>
</div>

<div id="sidebar_main_content_home">
<img id="image_coupe" alt="Image de la coupe" src="images/coupe.png">
</div>


</div>

</body>
</html>