<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Travel List">
    <div class="text-info">Travels in Db: ${travelCount}</div>
    <div class="travels-list">
        <c:forEach items="${travels}" var="travel">
            <div class="travel-card">
                <div class="travel-header">
                    <h3 class="travel-name"><a href="<c:url value="/travel/detail?id=${travel.getId()}"/>">${travel.getName()}</a></h3>
                    <span class="field-name">Author:</span>
                    <span class="travel-info">${travel.getAuthor().getName()},${travel.getAuthor().getAge()}</span>
                    <div class="clearfix"></div>
                </div>
<%--                <c:forEach items="${locations}" var="location">--%>
<%--                    <span class="travel-info">${location.getName()},${location.getCountry()}</span>--%>
<%--                    <div class="clearfix"></div>--%>
<%--                </c:forEach>--%>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>