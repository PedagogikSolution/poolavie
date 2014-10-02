<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String mTeamName2 = request.getAttribute("mTeam").toString();
	String mTeamId2 = request.getAttribute("mTeamId").toString();

	session.setAttribute("mTeamId", mTeamId2);
	session.setAttribute("mTeamName", mTeamName2);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
</head>
<body>
	Bienvenue
	<%=session.getAttribute("mTeamName")%>
</body>
</html>