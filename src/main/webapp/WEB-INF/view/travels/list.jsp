<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="<c:url value="/style/travelList.css"/>">
</head>
<t:mainLayout title="Travel List">
    <div class="text-prev">Всего путешествий : ${travels.size()}
        <c:if test="${location != null}">
            <div class="text-prev">  по локации :${location.getName()}</div>
        </c:if>
    </div>
    <div class="travels-list">
        <c:forEach items="${travels}" var="travel" varStatus="status">
            <div class="travel-card">
                <div class="travel-image">
                    <img src="${images.get(status.index)}" width="250" height="250" alt="${travel.getName()}"/>
                </div>
                <div class="travel-header">
                    <h3 class="travel-name">${travel.getName()}</h3>
                    <c:if test="${travel.getAuthor() != null}">
                        <span class="field-name">Author:</span>
                        <span class="travel-info">${travel.getAuthor().getName()},${travel.getAuthor().getAge()}</span>
                        <div class="clearfix"></div>
                    </c:if>
                </div>
                <a href="<c:url value='/travel/detail?id=${travel.getId()}'/>" class="btn bg-info">Details</a>
                <c:if test="${user != null}">
                    <с:if test="${travel.getAuthor().getId() == user.getId()}">
                        <div class="edit-icon">
                            <a href="<c:url value='/travel/edit?id=${travel.getId()}'/>" title="Edit Travel">
                                <i class="bi bi-pencil-square"></i>
                            </a>
                        </div>
                    </с:if>
                </c:if>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>