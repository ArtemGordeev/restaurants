<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
</head>
<body>
<hr>
<form method="post" action="restaurants">
    <b>Войти как:</b>
    <select name="userId">
        <option value="100000">User</option>
        <option value="100001">Admin</option>
    </select>
    <button type="submit">Select</button>
</form>
<a href="users">Show all users</a>
</body>
</html>
