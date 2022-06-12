<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String resourceName = request.getParameter("resource_name"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Information for User</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
    <body>
        <form action="resourceUpdate" method="post">
    <div class="container">
    <div class="brand-title">Information Update</div>
    <div class="inputs">
    <label>Resource Name</label>
    <input type="text" name = "updateResourceName" value="<%= resourceName %>" required readonly />
    <label>Availiable?</label>
    <input type="range" style="width: 30%;" min="0" max="1" value="1" step="1" name="updateAvailable" id="updateAvailable">
    
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
