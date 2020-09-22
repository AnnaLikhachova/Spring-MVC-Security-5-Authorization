<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
    <title >home</title>
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
<h2>Error message</h2>
<div class="navigation-panel-link"><a href="${contextPath}/login" class="link-form">Go to login page</a> </div>
</div>
</body>
</html>
