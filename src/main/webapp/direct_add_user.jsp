<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register an Account</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
<body>
    <form action="userCreate" method="post">
    <div class="container">
    <div class="brand-title">Add Account</div>
    <div class="inputs">
    <label>User Name</label>
    <input type="text" name = "createUserName" required />
    <label>First Name</label>
    <input type="text" name = "createFirstName" required />
    <label>Last Name</label>
    <input type="text" name = "createLastName" required />
    <label>Password</label>
    <input type="password" name="createPassword" required />
    <input type="hidden" name="createDirect" value="yes" />
    <button type="submit" onclick="refreshParent();">Add</button>
    <button type="reset" onclick="window.close();">Cancel</button>
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
