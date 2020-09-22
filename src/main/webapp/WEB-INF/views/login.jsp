<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet"
          href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
          crossorigin="anonymous"/>
    <script src="https://use.fontawesome.com/44af6d62a2.js"></script>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
</head>
<body>
<div class="limiter top-image">
    <div class="container-login">
        <div class="alert alert-info"></div>
        <div class="wrap-login">
            <div class="login-form validate-form">
					</span> <span class="login-form-title p-b p-t"> Log in </span>
                <form:form method="POST" modelAttribute="user">
                    <h1 class="alert alert-danger">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION}</h1>
                    <spring:bind path="name">
                    <div class="login-fa m-5">
                        <i class="fas fa-user-circle"></i>
                        <div class="wrap-input form-group validate-input"
                             data-validate="Enter username">
                            <form:input class="input form-control" type="text" path="name"
                                   placeholder="Username"/> <span class="focus-input"></span>
                        </div>
                    </div>
                    </spring:bind>

                    <spring:bind path="password">
                    <div class="login-fa">
                        <i class="fas fa-lock"></i>
                        <div class="wrap-input validate-input form-group"
                             data-validate="Enter password">

                            <form:input class="input form-control" type="password" placeholder="Password"
                                   path="password"/> <span class="focus-input"></span>
                        </div>
                    </div>
                    </spring:bind>

                    <div class="container-login-form-btn">
                        <div class="login-form-btn">
                            <input type="submit" class="login-form-btn" value="Login"/>
                        </div>
                    </div>
                </form:form>

                <div class="text-center p-t-b">
                    <div>
                        <a href="${contextPath}/registration" class="link-form">Registration</a>
                    </div>
                    <div>
                        <a class="link-form" href="${contextPath}/forgetPassword"> Forgot Password? </a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
