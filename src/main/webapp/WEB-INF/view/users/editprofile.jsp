<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="<c:url value="/style/editprofile.css"/>">
</head>

<t:mainLayout title="Edit User Profile">
    <div class="edit-user-container">
        <h2>Редактироание профиля</h2>
        <form action="<c:url value='/edit-profile'/>" method="post">
            <div class="form-group">
                <label class="control-label" for="name">Имя:</label>
                <input id="name" name="name" class="form-control" type="text" value="${user.getName()}" required/>
            </div>

            <div class="form-group">
                <label class="control-label" for="email">Электронная почта:</label>
                <input id="email" name="email" class="form-control" type="text" value="${user.getEmail()}" required/>
            </div>

            <div class="form-group">
                <input id="oldPassword" name="oldPassword" class="form-control" type="password" placeholder="Введите старый пароль" />
                <input id="newPassword" name="newPassword" class="form-control" type="password" placeholder="Введите новый пароль" />
                <input id="newRepeatPassword" name="newRepeatPassword" class="form-control" type="password" placeholder="Подтвердите новый пароль" />
                <small class="form-text text-muted">Оставьте пустым, если не хотите изменять пароль.</small>
            </div>
            <c:if test="${error != null}">
                <div id="error" class="alert alert-danger">${error}</div>
            </c:if>

            <div class="text-center">
                <button type="submit" class="btn btn-success">Сохранить изменения</button>
                <a href="<c:url value='/profile'/>" class="btn btn-danger">Отмена</a>
            </div>
        </form>
    </div>
</t:mainLayout>
