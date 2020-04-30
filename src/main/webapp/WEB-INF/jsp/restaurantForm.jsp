<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<section>
    <hr>
    <h2>
        ${param.action == 'create' ? 'Create restaurant' : 'Edit restaurant'}
    </h2>
    <jsp:useBean id="restaurant" type="com.model.Restaurant" scope="request"/>
    <form method="post">
        <input type="hidden" name="id" value="${restaurant.id}">
        <%--<input type="hidden" name="votes" value="${restaurant.votes}">--%>
        <dl>
            <dt>Title</dt>
            <dd><input type="text" value="${restaurant.title}" size=40 name="title" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
