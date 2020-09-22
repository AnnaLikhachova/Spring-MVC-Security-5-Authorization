<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <title >Unvalid link</title>
</head>
<body>
<div class="container">
<h1 class="alert alert-danger">${message}</h1>
<br/>
<a class="btn btn-default" href="${contextPath}/login">Sign in </a>
</div>
</body>
</html>