<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <title>Invalid Session Page</title>
</head>
<body>
<div class="container">
    <h1 class="alert alert-danger">Session expired</h1>
    <a class="btn btn-primary" href="${contextPath}//login">Login</a>
</div>
</body>
</html>

