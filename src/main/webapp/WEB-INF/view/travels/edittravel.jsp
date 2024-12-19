<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="<c:url value="/style/edittravel.css"/>">
</head>
<t:mainLayout title="Edit Travel">
    <div class="edit-travel-container">
        <h2>Редактирование Путешествия</h2>
        <form action="<c:url value='/travel/edit?id=${travel.getId()}'/>" method="post">
            <div class="form-group">
                <label class="control-label" for="name">Имя:</label>
                <input id="name" name="name" class="form-control" type="text" value="${travel.getName()}" required/>
            </div>

            <div class="form-group">
                <label class="control-label" for="description">Описание:</label>
                <textarea id="description" name="description" rows="4" cols="50">${travel.getDescription()}</textarea>
            </div>
            <div class="form-group">
                <label class="control-label" for="duration">Длительность:</label>
                <input id="duration" name="duration" type="text" value="${travel.getDuration()}" required>
            </div>
            <label class="control-label">Посещённые пункты:</label>
            <div id="locationsContainer">
                <c:forEach items="${locations}" var="location">
                    <div class="input-container">
                        <input id="location" name="location" class="form-control" type="text" value="${location.getName()}" required>
                        <i class="bi bi-trash3-fill remove-icon" onclick="removeInput(this)"></i>
                        <div class="clearfix"></div>
                    </div>
                </c:forEach>
            </div>
            <div id="responseMessages"></div>
            <input type="button" id="getCountryByLocation" value="Get country">
            <button type="button" id="addLocation">Добавить пункт</button>

            <c:if test="${error != null}">
                <div id="error" class="alert alert-danger">${error}</div>
            </c:if>

            <div class="text-center">
                <a href="<c:url value='/delete-travel?id=${travel.getId()}'/>" class="btn btn-danger">Удалить путешествие</a>
                <button type="submit" class="btn btn-success">Сохранить изменения</button>
                <a href="<c:url value='/travel/list'/>" class="btn btn-danger">Отмена</a>
            </div>
            <div id ='hidden-container'></div>
        </form>
    </div>
</t:mainLayout>

<script src="<c:url value="/js/edittravel.js"/>"></script>
