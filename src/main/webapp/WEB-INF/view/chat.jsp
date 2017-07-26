<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%--Шаблон чата взят отсюда: https://bootsnipp.com/snippets/vrzGb--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>History Message page</title>

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
            <a class="navbar-brand" href="">${messageForm.themeName} / ${messageForm.topicName}</a>
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

            <%--ПОИСК СООБЩЕНИЙ
             <form class="navbar-form navbar-right" role="search">
                 <div class="form-group">
                     <input type="text" class="form-control" placeholder="Search message..">
                 </div>
                 <button type="submit" class="btn btn-default">Search</button>
             </form>--%>
        </div>
    </div>

</nav>
<%--НАВИГАЦИОННЫЙ БАР, ШТОРКА - КОНЕЦ--%>
<br>
<br>
<br>

<h2 class="form-heading" align="center">History message</h2>

<br>
<br>
<%--ФОРМА ОТОБРАЖЕНИЯ и ВВОДА СООБЩЕНИЙ --%>
<div class="col-sm-9 message_section">
    <div class="row">
        <div class="chat_area">
            <ul class="list-unstyled">
                <%--items мы указываем параметр из метода GET в контроллере, присваиваем ему имя
                   var="" по которому мы можем обращаться к полям нашего акласса
                   Соответственно будут выведены поля класса каждого инстанса пока они не закончатся--%>
                <c:forEach items="${allInstanceMessages}" var="allInstanceMessages">
                    <c:if test="${allInstanceMessages.topicId == }"
                    <li class="left clearfix">
                        <span class="chat-img1 pull-left"></span>
                        <div class="chat-body1 clearfix">
                            <p><b>${allInstanceMessages.username}</b>
                                <br>${allInstanceMessages.message}</p>
                            <div class="chat_time pull-right">${allInstanceMessages.date}</div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div><!--chat_area-->
        <%--Форма для отображения сообщений КОНЕЦ--%>

        <%--Ввод сообщений в textatea и отправка формы на сервер
        P.S. Форма отправляет данные на ту-же страницу на которой находимся,
        ModelAttribyte должен быть прописан и в GET и в POST, что бы знать, куда отправлять и откуда брать--%>
        <form:form action="/chat" method="POST" modelAttribute="messageForm">
            <div class="message_write">
                <form:textarea resize="none" path="message" class="form-control"
                               placeholder="Your message.."/><%--все параметры прописываются внутри формы--%>
                <div class="clearfix"></div>
                <div class="chat_bottom">
                    <button class="pull-right btn btn-primary" type="submit">Send</button>
                    <a href="/" class="pull-left btn btn-primary">Refresh</a>
                </div>
            </div>
        </form:form>
        <%--Ввод и отправка сообщений из формы КОНЕЦ--%>
    </div>
</div>
<%--Форма отображения и ввода сообщений КОНЕЦ--%>
</div>
</div>
</div>

<br>
<br>
<br>

<footer>
    <span style='padding-left:10px;'> &copy; Aymaletdinov R.</span>
</footer>

<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>