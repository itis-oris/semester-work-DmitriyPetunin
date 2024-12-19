<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="<c:url value="/style/register.css"/>">
</head>

<t:mainLayout title="Register page">
    <form id="loginForm" class="form-horizontal" action="<c:url value="/register"/>" method="POST">
        <h1>Регистрация</h1>
        <div class="form-group">
            <label class="control-label col-sm-3" for="name">Имя</label>
            <div class="controls col-sm-9">
                <input id="name" name="name" class="form-control" type="text" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="email">Почта</label>
            <div class="controls col-sm-9">
                <input id="email" name="email" class="form-control" type="text" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="password">Пароль</label>
            <div class="controls col-sm-9">
                <input id="password" name="password" class="form-control" type="password" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="password">Дата рождения</label>
            <div class="controls col-sm-9">
                <input id="date_of_birth" name="date_of_birth" class="form-control" type="date" value=""/>
            </div>
        </div>
        <button type="submit" class="btn btn-success">Зарегистрироваться</button>
    </form>
</t:mainLayout>
