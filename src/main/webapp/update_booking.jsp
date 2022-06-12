<%@page import="java.time.LocalDateTime"%>
<%@page import="Class.Booking"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0); %>
<% int bookingId = Integer.parseInt(request.getParameter("booking_id")); %>
<% session.setAttribute("bookingId", bookingId); %>
<% List<Booking> bookingInfo = (List<Booking>) session.getAttribute("upComingBooking"); %>
<% Booking record = new Booking(); %>
<% for (Booking i: bookingInfo) { %>
<% if (i.getBookingId() == bookingId) { %>
<% record.setBookingId(bookingId); %>
<% record.setAccountId(i.getAccountId()); %>
<% record.setAccountName(i.getAccountName()); %>
<% record.setResourceId(i.getResourceId()); %>
<% record.setResourceName(i.getResourceName()); %>
<% } %>
<% } %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Information for Booking</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
    <body>
        <form action="bookingUpdate" method="post">
    <div class="container">
    <div class="brand-title">Booking Update</div>
    <div class="inputs">
    <label>User Name</label>
    <input type="hidden" name="updateAccountId" value="<%= bookingId %>" />
    <input type="text" name = "updateAccountName" value="<%= record.getAccountName() %>" required readonly />
    <label>Resource</label>
    <input type="text" name = "updateResourceName" value=<%= record.getResourceName() %> required readonly />
    
       <label for="updateBookingDuration">Duration</label>
        <select name="updateBookingDuration" id="updateBookingDuration">
        <option value="1">1 Hour</option>
        <option value="2">2 Hours</option>
        <option value="3">3 Hours</option>
        </select>
    
    <label for="updateBookingFrom">From</label>
    <input type="datetime-local" name="updateBookingFrom" id="updateBookingFrom" value="<%= now %>" min="<%= now %>" required />
    
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
