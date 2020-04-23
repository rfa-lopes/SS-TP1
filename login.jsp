<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
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

<h1>Login</h1>
<form method='POST' action='login'>
    <input type='username' placeholder='username' name='username'/>
    <input type='password' placeholder='password' name='password'/>
    <input type=submit value='Login'>

    <hr>

    <% switch(response.getStatus()) {
        case 200: //Ok
            break;
        case 401: //Unouthorized
    %><h3 style="color:red">Login fails</h3><%
        break;
    case 404: //Unouthorized
%><h3 style="color:red">Login fails</h3><%
        break;
    case 400: //bad request
%><h3 style="color:red">Bad request</h3><%
        break;
    default: //Server error
%><h3 style="color:red">Server error</h3><%
    }%>

</form>
</body>
</html>
