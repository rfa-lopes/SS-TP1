<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

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
    %><h3>Login fails</h3><%
        break;
    case 404: //Unouthorized
%><h3>Login fails</h3><%
        break;
    case 400: //bad request
%><h3>Bad request</h3><%
        break;
    default: //Server error
%><h3>Server error</h3><%
    }%>

</form>
</body>
</html>
