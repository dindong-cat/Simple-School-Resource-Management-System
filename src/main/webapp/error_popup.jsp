<%@page import="Class.ErrorCode"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% ErrorCode error = (ErrorCode) request.getAttribute("error"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error <%= error.getCode() %></title>
    </head>
    <body>
        <h1><%= error.getDescription() %></h1>
        <button type="button" onclick="window.close()">OK</button>
    </body>
</html>
