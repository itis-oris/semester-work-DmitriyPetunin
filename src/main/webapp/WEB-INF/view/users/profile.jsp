<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="User Profile">
  <div class="container">
    <h2>Мой профиль</h2>
    <div class="form-group">
      <label class="control-label col-sm-3" for="name">Имя:</label>
      <div class="controls col-sm-9">
        <p id="name"><c:out value="${user.getName()}"/></p>
      </div>
    </div>

    <div class="form-group">
      <label class="control-label col-sm-3" for="email">Почта:</label>
      <div class="controls col-sm-9">
        <p id="email"><c:out value="${user.getEmail()}"/></p>
      </div>
    </div>


    <div class="form-group">
      <label class="control-label col-sm-3" for="role">Возраст:</label>
      <div class="controls col-sm-9">
        <p id="role"><c:out value="${user.getAge()}"/></p>
      </div>
    </div>

    <div class="form-group">
      <div class="controls col-sm-9 col-sm-offset-3">
        <a href="<c:url value='/editProfile'/>" class="btn btn-primary">Редактировать</a>
        <a href="<c:url value='/logout'/>" class="btn btn-danger">Выйти</a>
        <a href="<c:url value='/createtravel'/>" class="btn btn-primary">Создать путешествие</a>
      </div>
    </div>
  </div>
</t:mainLayout>