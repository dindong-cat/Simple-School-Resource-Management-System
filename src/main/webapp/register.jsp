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
    <div class="brand-title">Register</div>
    <div class="inputs">
    <label>User Name</label>
    <input type="text" name = "createUserName" required />
    <label>First Name</label>
    <input type="text" name = "createFirstName" required />
    <label>Last Name</label>
    <input type="text" name = "createLastName" required />
    <label>Password</label>
    <input type="password" name="createPassword" id="password" onchange="confirmPasswordMustSame();" required />
    <label>Confirm Password</label>
    <input type="password" id="confirmPassword" onchange="confirmPasswordMustSame();" required />
    <input type="hidden" name="createDirect" value="no" />
    <span id="warningMsg"></span>
    <button type="submit" id="submit" style="background: gray;" disabled>Create</button>
    <button type="reset">Reset</button>
    </div>
    <a href="index.jsp">Already have account?</a>
    </div>
    </form>
    <script src=".\Javascript\confirmPasswordMustSame.js"></script>
</body>
</html>
