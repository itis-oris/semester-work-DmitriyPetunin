<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Travel Detail">
    <div class="travel-page">
        <h1 class="travel-name">${travel.getName()}</h1>
        <div class="travel-field">
            <span class="field-name">Duration:</span>
            <span class="field-value">${travel.getDuration()}</span>
        </div>
    </div>
</t:mainLayout>
