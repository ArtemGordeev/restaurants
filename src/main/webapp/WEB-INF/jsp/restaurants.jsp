<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Restaurants</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<section>
    <hr/>
    <h2>Restaurants</h2>
    <hr/>
    <a href="restaurants/create?action=create">Add restaurant</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Title</th>
            <th>Votes</th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${restaurants}" var="restaurant">
            <jsp:useBean id="restaurant" type="com.to.RestaurantTo"/>
            <tr>
                <td>${restaurant.title}</td>
                <td>${restaurant.votes}</td>
                <td><a href="restaurants/update/${restaurant.id}">Update</a></td>
                <td><a href="restaurants/delete/${restaurant.id}">Delete</a></td>
                <td><a href="restaurants/vote/${restaurant.id}">Vote</a></td>
                <td><a href="restaurants/${restaurant.id}/menus">Show menus</a></td>
            </tr>
        </c:forEach>

    </table>
</section>
</body>
</html>