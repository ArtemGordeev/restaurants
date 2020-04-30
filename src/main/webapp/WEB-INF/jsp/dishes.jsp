<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>dishes</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<section>
    <hr/>
    <h2>Dishes</h2>
    <hr/>
    <a href="dishes/create?action=create">Add dish</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Description</th>
            <th>Price</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${dishes}" var="dish">
            <jsp:useBean id="dish" type="com.model.Dish"/>
            <tr>
                <td>${dish.description}</td>
                <td>${dish.price}</td>
                <td><a href="dishes/update/${dish.id}">Update</a></td>
                <td><a href="dishes/delete/${dish.id}">Delete</a></td>
            </tr>
        </c:forEach>

    </table>
</section>
</body>
</html>