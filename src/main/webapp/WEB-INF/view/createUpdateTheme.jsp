<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>
<body>

<%--НАВИГАЦИОННЫЙ БАР, ШТОРКА--%>
<nav class="navbar navbar-inverse navbar-fixed-top">

    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/forum/0">FORUM INTECH</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <%--Тут мы проверяем есть ли есть ли пользователь и обнуляем действующую активность. Т.е. logout--%>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <form id="logoutForm" method="POST" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <li><a onclick="document.forms['logoutForm'].submit()">Log out</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
<%--НАВИГАЦИОННЫЙ БАР, ШТОРКА - КОНЕЦ--%>
<br>
<br>
<br>
<br>
<%--ФОРМА ВВОДА НАЗВАНИЯ И ОПИСАНИЯ НОВОЙ ТЕМЫ--%>
<div class="container">
    <c:if test="${themeForm.themeName != null}">
        <h2 class="form-heading" align="center">Update theme</h2>
    </c:if>
    <c:if test="${themeForm.themeName == null}">
        <h2 class="form-heading" align="center">Create new theme</h2>
    </c:if>
    <br>
    <br>
    <form:form method="POST" modelAttribute="themeForm" class="form-signin">
        <div class="message_write">
            <form:input type="text" path="themeName" class="form-control" placeholder="The title of the topic"
                        autofocus="true"></form:input>
            <br>
            <form:textarea resize="none" path="description" class="form-control"
                           placeholder="Your description.."/>
            <div class="clearfix"></div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>
</div>
<%--ФОРМА ВВОДА НАЗВАНИЯ И ОПИСАНИЯ НОВОЙ ТЕМЫ  - КОНЕЦ--%>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<br>
<br>
<br>
<footer>
    <span style='padding-left:10px;'> &copy; Aymaletdinov R.</span>
</footer>

</body>
</html>