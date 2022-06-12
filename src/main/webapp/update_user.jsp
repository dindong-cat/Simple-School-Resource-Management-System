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
        <form action="userUpdate" method="post">
    <div class="container">
    <div class="brand-title">Information Update</div>
    <div class="inputs">
    <label>User Name</label>
    <input type="text" name = "account_name" value="<%= accountName %>" required readonly />
    <label>First Name</label>
    <input type="text" name = "updateFirstName" required />
    <label>Last Name</label>
    <input type="text" name = "updateLastName" required />
    <label>New Password</label>
    <input type="password" name="updatePassword" required />
    <label>Confirm New Password</label>
    <input type="password" required />
    <button type="submit" onclick="refreshParent()">Update</button>
    <button type="reset" onclick="window.close()">Cancel</button>
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
