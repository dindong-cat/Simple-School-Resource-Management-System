<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Assignment - Homepage</title>
        <link rel="stylesheet" href=".\CSS\login_page.css">
    </head>
    <body>
        <form action="Login" method="post">
  <div class="container">
  <div class="brand-title">Welcome</div>
  <div class="inputs">
    <label>User Name</label>
    <input type="text" name = "userName" required />
    <label>Password</label>
    <input type="password" name="password" required />
    <button type="submit">Login</button>
    <button type="reset">Reset</button>
  </div>
  <a href="register.jsp">Register Account</a>
</div>
        </form>
    </body>
</html>