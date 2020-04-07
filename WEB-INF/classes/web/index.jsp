<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<hr>
<form method="post" action="${pageContext.request.contextPath}/login">
    <input type="text" name="username" value="username">
    <br>
    <input type="password" name="password" value="password">
    <br>
    <input type="submit" value="Login">
</form>
<br>
<br>
<br>
<br>

<% //String data = (String)request.getAttribute("appData"); %>
<p><%="Welcome" //data != null ? "Response: " + data : "Welcome!"%></p>

</body>
</html>
