<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="<c:url value="/style/newtravel.css"/> ">
</head>

<t:mainLayout title="Create new Travel">
    <div class="newtravel-container">
        <form id ="locationForm" method="post" enctype="multipart/form-data">
            <div>
                <label for="tripName">Название путешествия:</label>
                <input type="text" id="tripName" name="tripName" required>
            </div>
            <div>
                <label for="description">Описание:</label>
                <textarea id="description" name="description" rows="4" cols="50"></textarea>
            </div>
            <div>
                <label for="duration">Длительность:</label>
                <input type="text" id="duration" name="duration" required>
            </div>
            <div>
                <label>Посещённые пункты:</label>
                <div id="locationsContainer"></div>
                <div id="responseMessages"></div>
                <input type="button" id="getCountryByLocation" value="Get country">
                <button type="button" id="addLocation">Добавить пункт</button>
            </div>
            <div>
                <label for="files">Выберите изображение:</label>
                <input type="file" id="files" name="files" accept="image/*" multiple>
            </div>
            <div id ='hidden-container'></div>
            <div>
                <input type="submit" value="Создать путешествие">
            </div>
        </form>
    </div>
</t:mainLayout>

<script src="<c:url value="/js/newtravel.js"/>"></script>
