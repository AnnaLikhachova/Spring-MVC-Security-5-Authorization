<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title></title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet"
          href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
          crossorigin="anonymous"/>
    <script src="https://use.fontawesome.com/44af6d62a2.js"></script>
    <script src='https://www.google.com/recaptcha/api.js?l=en'></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/static/js/useCaptcha.js" />"></script>
    <link href="<c:url value='/static/css/thisapp.css' />"
          rel="stylesheet"/>
</head>
<body>
<div class="limiter top-image">
    <div class="container-login">
        <div class="wrap-login">
            <form:form method="POST" modelAttribute="userForm" class="login-form validate-form">
                <span class="login-form-title p-b p-t"> Registration </span>

                <spring:bind path="name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="wrap-input validate-input"
                             data-validate="Enter username">
                            <form:input class="input form-control" type="text" path="name"
                                        placeholder="Enter username"/>
                            <form:errors class="form-error" path="name"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="wrap-input validate-input"
                         data-validate="Enter email">
                        <form:input class="input form-control" type="text" path="email" placeholder="Enter email"/>
                        <form:errors class="form-error" path="email"/>
                    </div>
                </div>
                </spring:bind>

                <spring:bind path="password">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="wrap-input validate-input"
                             data-validate="Enter password">
                            <form:input class="input form-control" path="password" type="password"
                                        placeholder="Enter password"/>
                            <form:errors class="form-error" path="password"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="confirmPassword">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <div class="wrap-input validate-input"
                             data-validate="Repeat password">
                            <form:input class="input form-control" type="password" path="confirmPassword"
                                   placeholder="Repeat password"/> <span
                                class="focus-input" data-placeholder="&#xf023;"></span>
                            <form:errors class="form-error" path="confirmPassword"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="userProfiles">
                <div class="wrap-input validate-input select-form select-registration-form">
                    <form:select class="select-registration" type="hidden"
                                 path="userProfiles" items="${roles}" itemValue="id"
                                 multiple="true" itemLabel="type"/>
                    <form:errors class="form-error" path="userProfiles"/>
                </div>
                </spring:bind>

                <div class="registration-space"></div>

                <div class="g-recaptcha col-sm-5"
                     data-sitekey="${captchaSettings}" data-callback="onReCaptchaSuccess"
                     data-expired-callback="onReCaptchaExpired"></div>
                <span id="captchaError" class="alert alert-danger col-sm-4"
                      style="display: none"></span>

                <div class="container-login-form-btn">
                    <div class="login-form-btn">
                        <input type="submit"
                               class="login-form-btn" onclick="useCaptcha(event)" value="Register"/>
                    </div>
                </div>
            </form:form>
            <div class="text-center p-t-b">
                <div>
                    <a href="${contextPath}/login" class="link-form">Sign in</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>