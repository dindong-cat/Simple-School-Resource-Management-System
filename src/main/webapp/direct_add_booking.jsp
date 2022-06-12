<%@page import="Class.Account"%>
<%@page import="Class.Resource"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0); %>
<% List<Resource> resourceInfo = (List<Resource>) session.getAttribute("resourceInfo"); %>
<% Account myself = (Account) session.getAttribute("user"); %>
<% List<Account> accountInfo = (List<Account>) session.getAttribute("accountInfo"); %>
<!-- List<Resource> resourceNameList = (List<Resource>) session.getAttribute("resourceNameList"); -->

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Make a Booking</title>
        <link rel="stylesheet" href=".\CSS\register_page.css">
    </head>
<body>
    <form action="bookingCreate" method="post">
    <div class="container">
    <div class="brand-title">Add Booking</div>
    <div class="inputs">
        
        <label>User Name</label>
        <% if (myself.isIsAdmin()) { %>
        <select name="createAccountId" id ="createAccountId">
        <option value="<%= myself.getAccountId() %>"><%= myself.getLoginName() %></option>
        <% for (Account i: accountInfo) { %>
        <% if (i.getAccountId() != myself.getAccountId() && !i.isIsAdmin()) { %>
        <option value="<%= i.getAccountId() %>"><%= i.getLoginName() %></option>
        <% } %>
        <% } %>
        </select>
        <% } else { %>
        <input type="text" name="createAccountName" value="<%= myself.getLoginName() %>" readonly />
        <input type="hidden" name="createAccountId" value="<%= myself.getAccountId() %>" />
        <% } %>
        
    <label for="createBookingResource">Resource</label>
        <select name="createBookingResource" id="createBookingResource">
            <% for (Resource i: resourceInfo) { %>
            <% if (i.isIsAvailable())  { %>
            <option value="<%= i.getResourceId() %>"><%= i.getResourceName() %></option>
            <% } %>
            <% } %>
        </select>
    
    <label for="createBookingDuration">Duration</label>
        <select name="createBookingDuration" id="createBookingDuration">
        <option value="1">1 Hour</option>
        <option value="2">2 Hours</option>
        <option value="3">3 Hours</option>
        </select>
    
    <label for="createBookingFrom">From</label>
    <input type="datetime-local" name="createBookingFrom" id="createBookingFrom" value="<%= now %>" min="<%= now %>" required />
    
    
    
    
    
    <input type="hidden" name="createDirect" value="yes" />
    <button type="submit" onclick="refreshParent();">Book</button>
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
