<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String bookingId = request.getParameter("booking_id"); %>) 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Information for User</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
    <body>
        <form action="bookingDelete" method="post">
    <div class="container">
    <div class="brand-title">Confirm Cancel this booking?</div>
    <div class="inputs">
    <input type="hidden" name = "booking_id" value="<%= bookingId %>" />
    <button type="submit" onclick="refreshParent()">Cancel</button>
    <button type="button" onclick="window.close()">Back</button>
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
