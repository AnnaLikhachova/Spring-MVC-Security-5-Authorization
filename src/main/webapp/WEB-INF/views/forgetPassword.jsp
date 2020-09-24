<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/static/js/forgetPassword.js" />"></script>
<title>reset</title>
</head>
<body>
<div class="container">
    <h1>Reset password</h1>
    <br/>
    <form action="#">
        <div class="form-reset">
            <label class="form-reset-title">Enter your email</label>
            <span class="form-reset-row"><input class="form-reset-input" id="email" name="email" type="email" value="" required="required"/></span>
            <button class="btn btn-primary" type="submit">reset</button>
        </div>
    </form>
    <br/>
    <a class="btn btn-default" href="${contextPath}/registration">registration</a>
    <br/><br/>
    <a class="btn btn-default" href="${contextPath}/login">login</a>
</div>
</body>
</html>

