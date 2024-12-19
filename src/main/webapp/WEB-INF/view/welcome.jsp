<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Welcome page">
    <style>
        body {
            height: 100vh; /* Высота страницы равная высоте экрана */
            margin: 0; /* Убираем отступы */
            background-image: url("https://res.cloudinary.com/imagesoftravel/image/upload/v1734610152/nimhhxlxkpxz5st0o1gm.jpg");
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }

        .welcome-text {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: white;
            font-size: 5em;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
        }
    </style>

    <div class="welcome-text">WELCOME AND LET'S GO TRAVEL</div>

</t:mainLayout>