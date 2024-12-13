<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="http://cloudinary.com/jsp/taglib" %>
<t:mainLayout title="Travel Detail">
    <div class="travel-page">
        <h1 class="travel-name">${travel.getName()}</h1>
        <span class="travel-author">By: ${travel.getAuthor().getName()},${travel.getAuthor().getAge()}</span>

        <div class="travel-field">
            <span class="field-name">Duration:</span>
            <span class="field-value">${travel.getDuration()}</span>
        </div>
        <div class="travel-field">
            <span class="field-name">Description:</span>
            <span class="field-value">${travel.getDescription()}</span>
        </div>
        <c:forEach items="${locations}" var="location">
            <span class="travel-info">${location.getName()},${location.getCountry()}</span>
            <div class="clearfix"></div>
        </c:forEach>

        <div class="travel-images">
            <h2>Images:</h2>
            <c:forEach var="image" items="${images}">
                <div class="travel-image">
                    <img src="${image.getImage_url()}" width="150" height="150" alt="Travel Image" />
                </div>
            </c:forEach>
        </div>
    </div>
</t:mainLayout>
