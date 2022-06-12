<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register an Account</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
<body>
    <form action="resourceCreate" method="post">
    <div class="container">
    <div class="brand-title">Add Resource</div>
    <div class="inputs">
    <label>Resource Name</label>
    <input type="text" name = "createResourceName" required />
    <label>Availiable?</label>
    <input type="range" style="width: 30%;" min="0" max="1" value="1" step="1" name="createAvailable" id="createAvailable">
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
