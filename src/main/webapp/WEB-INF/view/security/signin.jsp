<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <link rel="stylesheet" href="<c:url value="/style/signin.css"/>">
</head>

<t:mainLayout title="Sign In page">
    <div class="signing-container">
        <form id="loginForm" class="form-horizontal" action="<c:url value="/signin"/>" method="POST">
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">Email</label>
                <div class="controls col-sm-9">
                    <input id="email" name="email" class="form-control" type="text" value=""/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="password">Password</label>
                <div class="controls col-sm-9">
                    <input id="password" name="password" class="form-control" type="password" value=""/>
                </div>
            </div>
            <c:if test="${error != null}">
                <div id="error" class="alert alert-danger">${error}</div>
            </c:if>
            <button type="submit" class="btn btn-success">Sign in</button>
        </form>
    </div>
</t:mainLayout>
