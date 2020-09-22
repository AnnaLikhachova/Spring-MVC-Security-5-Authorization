<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/static/js/changePassword.js" />"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pwstrength-bootstrap/3.0.9/pwstrength-bootstrap.js"
            integrity="sha512-JvhEtOH1USy6bbKby0NSzqzcE1WfrRHSced1JWdIneN8KjIUOoCJHPIjnJTgQKtNcZ5s7Rs+Cwf8LjD8Zlj6qg=="
            crossorigin="anonymous"></script>
    <title>Reset password</title>
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
    <div class="row">
        <div id="error-info" class="alert alert-danger" style="display:none"></div>
        <h1>change password </h1>
        <form>
            <br/>
            <label class="col-sm-2">Old</label>
            <span class="col-sm-5"><input class="form-control" id="oldpass" name="oldPassword" type="password"
                                          value=""/></span>
            <span class="col-sm-5"></span>
            <br/><br/>
            <label class="col-sm-2">New</label>
            <span class="col-sm-5"><input class="form-control" id="password" name="newPassword" type="password"
                                          value=""/></span>
            <div class="col-sm-12"></div>
            <br/><br/>
            <label class="col-sm-2">Confirm</label>
            <div class="col-sm-5"><input class="form-control" id="matchPassword" name="matchingPassword" type="password"
                                         value=""/></div>
            <div id="error-dialog-error-label" class="alert alert-danger col-sm-12" style="display:none">Error</div>
            <div class="col-sm-12">
                <br/><br/>
                <button class="btn btn-primary" type="submit" onclick="savePassword()">Change password
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>

