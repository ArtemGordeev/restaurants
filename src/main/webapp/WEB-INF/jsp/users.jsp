<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>name</th>
            <th>email</th>
            <th>roles</th>
            <th>active</th>
            <th>registered</th>
            <th>votes</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" scope="page" type="com.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
                <td><%=user.isEnabled()%>
                </td>
                <td><fmt:formatDate value="${user.registered}" pattern="dd-MM-yyyy"/></td>
                <td><a href="users/${user.id}/votes">Show votes</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>