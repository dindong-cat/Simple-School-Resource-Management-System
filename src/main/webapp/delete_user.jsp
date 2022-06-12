<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String accountName = request.getParameter("account_name"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Information for User</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
    <body>
        <form action="userDelete" method="post">
    <div class="container">
    <div class="brand-title">Confirm Delete <%= accountName %>?</div>
    <div class="inputs">
    <input type="hidden" name = "account_name" value="<%= accountName %>" />
    <button type="submit" onclick="refreshParent()">Delete</button>
    <button type="button" onclick="window.close()">Cancel</button>
    </div>
    </div>
    </form>
    <script>
    window.onunload = refreshParent;
    function refreshParent() {
        window.opener.location.reload(true);
    }
</script>
    </body>
</html>
