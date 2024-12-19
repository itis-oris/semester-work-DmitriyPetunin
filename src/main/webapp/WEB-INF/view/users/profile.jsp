<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
  <link rel="stylesheet" href="<c:url value="/style/profile.css"/> ">
</head>

<t:mainLayout title="User Profile">
  <div class="container">
    <div class="profile-header">
      <img src="<c:url value='https://res.cloudinary.com/imagesoftravel/image/upload/v1734626358/t69wp16dvaqkxy6xvvoh.jpg'/>" alt="Profile Image" class="profile-image"/>
    </div>
    <h2>Мой профиль</h2>
    <div class="profile-content">
      <div class="user-info">
        <div class="form-group">
          <label class="control-label" for="name">Имя:</label>
          <p id="name"><c:out value="${user.getName()}"/></p>
        </div>

        <div class="form-group">
          <label class="control-label" for="email">
            <i class="bi bi-envelope"></i> Почта:
          </label>
          <p id="email"><c:out value="${user.getEmail()}"/></p>
        </div>

        <div class="form-group">
          <label class="control-label" for="date_of_birth">
            <i class="bi bi-calendar"></i> Дата рождения:
          </label>
          <p id="date_of_birth"><c:out value="${user.getDateOfBirth()}"/></p>
        </div>
      </div>

      <div class="action-buttons">
        <a href="<c:url value='/delete-profile'/>" class="btn btn-danger">Удалить аккаунт</a>
        <a href="<c:url value='/edit-profile'/>" class="btn btn-success">Редактировать</a>
        <a href="<c:url value='/logout'/>" class="btn btn-danger">Выйти</a>
        <a href="<c:url value='/create-travel'/>" class="btn btn-primary">Создать путешествие</a>
        <a href="<c:url value="/travel/list?userId=${user.getId()}"/>" class="btn btn-primary">Мои путешествия</a>
      </div>
    </div>
  </div>
</t:mainLayout>