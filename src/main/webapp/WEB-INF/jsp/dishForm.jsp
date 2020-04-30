<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<section>
    <hr>
    <h2>
        ${param.action == 'create' ? 'Create dish' : 'Edit dish'}
    </h2>
    <jsp:useBean id="dish" type="com.model.Dish" scope="request"/>
    <form method="post">
        <input type="hidden" name="id" value="${dish.id}">
        <dl>
            <dt>Description</dt>
            <dd><input type="text" value="${dish.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>Price</dt>
            <dd><input type="number" value="${dish.price}" name="price" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
