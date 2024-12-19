<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="<c:url value="/style/detail.css"/> ">
</head>
<t:mainLayout title="Travel Detail">
    <div class="travel-page">
        <h1 class="travel-name">${travel.getName()}</h1>
        <c:if test="${travel.getAuthor() != null}">
            <span class="travel-author">Автор: ${travel.getAuthor().getName()},${travel.getAuthor().getAge()}</span>
        </c:if>

        <div class="travel-details">
            <div class="travel-info">
                <div class="travel-field">
                    <span class="field-name">Длительность:</span>
                    <span class="field-value">${travel.getDuration()}</span>
                </div>
                <div class="travel-field">
                    <span class="field-name">Описание:</span>
                    <span class="field-value">${travel.getDescription()}</span>
                </div>
                <c:forEach items="${locations}" var="location">
                    <h3 class="travel-name"><a href="<c:url value="/travel/list?locationId=${location.getId()}"/>">${location.getName()}</a></h3>
                    <span class="travel-info">${location.getName()}, ${location.getCountry()}</span>
                    <div class="clearfix"></div>
                </c:forEach>
            </div>

            <div class="travel-images">
                <h2>Изображения:</h2>
                <c:forEach var="image" items="${images}">
                    <div class="travel-image">
                        <img src="${image.getImage_url()}" width="250" height="250" alt="Travel Image" />
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</t:mainLayout>
