<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>

<% String username = (String)request.getAttribute("username");%>

<% if(username.equals("root")){ %>

<h3>Create Account</h3>
<form method='post' action='create'>
    <input type='username' placeholder='username' name='username'/>
    </br>
    <input type='password' placeholder='password' name='password1'/>
    </br>
    <input type='password' placeholder='repeat password' name='password2'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<hr>
<h3>Delete Account</h3>
<form method='post' action='remove'>
    <input type='username' placeholder='username' name='username'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<h3>Get Account</h3>
<form method='post' action='get'>
    <input type='username' placeholder='username' name='username'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<% } %>

<form method='POST' action='/SS-TP1/logout'>
    <input type='submit' value='Logout'>
</form>

</body>
</html>
