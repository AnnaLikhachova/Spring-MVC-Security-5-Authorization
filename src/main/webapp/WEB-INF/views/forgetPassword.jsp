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
    <h1>Reset password</h1>
    <br/>
    <div class="row">
      <form action="#">
        <label class="col-sm-2">Enter your email</label>
        <span class="col-sm-5"><input class="form-control" id="email" name="email" type="email" value="" required="required" /></span>
        <button class="btn btn-primary" type="submit">reset</button>
      </form>
    </div>

<br/> 
<a class="btn btn-default" href="${contextPath}/registration">registration</a>
<br/><br/>
<a class="btn btn-default" href="${contextPath}/login">login</a>
</div>
</body>
</html>

