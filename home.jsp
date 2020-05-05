<%@ page contentType="text/html;charset=UTF-8" session="false"%>
<html>
<head>
    <title>Home</title>
</head>
<body>

<style>
    h1 {
        font-family: Arial, Helvetica, sans-serif;
    }

    h3 {
        font-family: "Lucida Console", Courier, monospace;
    }

</style>

<%String username = (String)request.getAttribute("username");%>

<h1>Welcome ${username} </h1>
<hr>
<% switch(response.getStatus()) {
    case 200: //Ok
        break;
    case 201: //Ok
%><h3 style="color:green">Done</h3><%
        break;
    case 400: //bad request
%><h3 style="color:red">Bad request</h3><%
        break;
    case 401: //Unouthorized
%><h3 style="color:red">Password incorrect</h3><%
        break;
    case 404: //Not found
%><h3 style="color:red">Not exist</h3><%
        break;
    case 422: //Duplicate
%><h3 style="color:#ff620c">Duplicate</h3><%
        break;
    default: //Server error
%><h3 style="color:red">Server error</h3><%
    }%>
<hr>

<% if(username.equals("root")){ %>

<h3>Create Account</h3>
<form method='post' action='create'>
    <input type='username' placeholder='username' name='createusername'/>
    </br>
    <input type='password' placeholder='password' name='createpassword1'/>
    </br>
    <input type='password' placeholder='repeat password' name='createpassword2'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<h3>Delete Account</h3>
<form method='post' action='remove'>
    <input type='username' placeholder='username' name='deleteusername'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<h3>Get Account</h3>
<form method='post' action='get'>
    <input type='username' placeholder='username' name='getformusername'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<h3>List Accounts</h3>
<form method='GET' action='/SS-TP1/list'>
    <input type='submit' value='Go'>
</form>

<hr>

<h3>List Loggers</h3>
<form method='GET' action='/SS-TP1/loggers'>
    <input type='submit' value='Go'>
</form>

<hr>

<h3>Lock/Unlock Account</h3>
<form method='post' action='lock'>
    <input type='username' placeholder='username' name='lockformusername'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<% } %>

<h3>Change password</h3>
<form method='post' action='/SS-TP1/change'>
    <input type='password' placeholder='actual password' name='changepassword1'/>
    </br>
    <input type='password' placeholder='new password' name='changepassword2'/>
    </br>
    <input type='password' placeholder='repeat password' name='changepassword3'/>
    </br>
    <input type=submit value='Confirm'>
</form>

<hr>

<form method='POST' action='/SS-TP1/logout'>
    <input type='submit' value='Logout'>
</form>

</body>
</html>
