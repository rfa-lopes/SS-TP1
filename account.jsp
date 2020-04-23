<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Account Info</title>
</head>
<body>

<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    th, td {
        padding: 5px;
        text-align: left;
    }
    h1 {
        font-family: Arial, Helvetica, sans-serif;
    }
</style>

<h1>Account information: <h4>${gettype}</h4></h1>

<table style="width:100%">
    <tr>
        <th>Username</th>
        <th>Password: B64[ Encryped[ password + salt ] ]</th>
        <th>Is login</th>
        <th>Is locked</th>
    </tr>
    <tr>
        <td>${getusername}</td>
        <td>${getpassword}</td>
        <td>${getisloggin}</td>
        <td>${getislocked}</td>
    </tr>
</table>

<form method='' action='/SS-TP1/home'>
    <input type='submit' value='Home'>
</form>

</form>
</body>
</html>
