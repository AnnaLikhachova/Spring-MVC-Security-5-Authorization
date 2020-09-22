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
    <title >Registration confirmation</title>
</head>
<body>
<div class="container">
    <h1 class="alert alert-info">Registration confirmation</h1>
    <br/>
    <div class="form-group ${expired != null ? '"expired".equals(result)' : ''}"></div>
    <span>${message}</span>
    <br/>

    <div>
        <form action="/registrationConfirm" method="post">
            <input type="hidden" id="token" name=token value="${token}">
            <c:if test="${expired}">
                <br/>
                <button type="submit">Resend</button>
            </c:if>
        </form>
    </div>
    <a class="btn btn-primary" href="${contextPath}/login">Sign in</a>
</div>
</body>
</html>

