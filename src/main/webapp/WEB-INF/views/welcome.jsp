<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
	<link href="<c:url value='/static/css/thisapp.css' />"
		  rel="stylesheet"/>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
	<title >Welcome</title>
</head>
<body>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">home</a>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="${contextPath}/logout">Logout</a></li>
		</ul>
	</div>
</nav>
<div class="container">
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<form id="logoutForm" method="POST" action="${contextPath}/logout">
		</form>
		<h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
	</c:if>
</div>
</body>
</html>