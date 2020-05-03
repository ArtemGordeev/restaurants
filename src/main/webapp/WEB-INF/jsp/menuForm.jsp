<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<section>
    <hr>
    <h2>
        ${param.action == 'create' ? 'Create menu' : 'Edit menu'}
    </h2>
    <jsp:useBean id="menu" type="com.model.Menu" scope="request"/>
    <form method="post">
        <input type="hidden" name="id" value="${menu.id}">
        <dl>
            <dt>Title</dt>
            <dd><input type="text" value="${menu.title}" size=40 name="title" required></dd>
            <dt>Date</dt>
            <dd><input type="date" value="${menu.date}" size=40 name="date" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
