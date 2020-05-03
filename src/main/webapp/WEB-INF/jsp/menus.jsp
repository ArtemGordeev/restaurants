<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>menus</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<section>
    <hr/>
    <h2>Menus</h2>
    <hr/>
    <a href="menus/create?action=create">Add menu</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Title</th>
            <th>Date</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${menus}" var="menu">
            <jsp:useBean id="menu" type="com.model.Menu"/>
            <tr>
                <td>${menu.title}</td>
                <td>${menu.date}</td>
                <td><a href="menus/update/${menu.id}">Update</a></td>
                <td><a href="menus/delete/${menu.id}">Delete</a></td>
                <td><a href="menus/${menu.id}/dishes">Show dishes</a></td>
            </tr>
        </c:forEach>

    </table>
</section>
</body>
</html>