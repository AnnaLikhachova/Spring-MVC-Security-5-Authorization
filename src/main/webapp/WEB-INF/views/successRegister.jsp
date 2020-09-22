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
    <title >Successfull registration</title>
</head>
<body>
<div class="container">
<h1 class="alert alert-danger">You register successfully. Please check your mail to procced with registration.</h1>
<div class="navigation-panel-link"><a href="${contextPath}/login" class="link-form">Sign in</a> </div>
</div>
</body>
</html>
