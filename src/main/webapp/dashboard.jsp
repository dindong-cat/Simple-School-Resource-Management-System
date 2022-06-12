<%@page import="Class.Booking"%>
<%@page import="Class.Resource"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.*"%>
<%@page import="java.util.*"%>
<%@page import="Class.Account"%>
<%@page import="Class.SQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    Account user = (Account) session.getAttribute("user");
    List<Account> accountInfo = (List<Account>) session.getAttribute("accountInfo");
    List<Resource> resourceInfo = (List<Resource>) session.getAttribute("resourceInfo");
    List<Booking> upComingBookingInfo = (List<Booking>) session.getAttribute("upComingBooking");
    List<Booking> bookingInfo = (List<Booking>) session.getAttribute("bookingInfo");
    List<Booking> reportResult = (List<Booking>) session.getAttribute("reportResult");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0);

%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href=".\CSS\dashboard.css">
    <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
<body>
  <div class="sidebar">
    <div class="logo-details">
      <i class='bx bx-grid-alt'></i>
      <span class="logo_name">Welcome</span>
    </div>
      <ul class="nav-links">
        <li>
          <a href="#" class="active">
            <i class='bx bx-user' ></i>
            <span class="links_name">Overview</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class='bx bx-box' ></i>
            <span class="links_name">Layout Design</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class='bx bx-box' ></i>
            <span class="links_name">Layout Design</span>
          </a>
        </li>
        
        <li class="log_out">
          <a href="Logout">
            <i class='bx bx-log-out'></i>
            <span class="links_name" onclick="Logout">Logout</span>
          </a>
        </li>
      </ul>
  </div>
  <section class="home-section">
    <nav>
      <div class="sidebar-button">
        <i class='bx bx-menu sidebarBtn'></i>
        <span class="dashboard">Project Assignment - Online School Management System</span>
      </div>
      
      <div class="profile-details">
        <span class="admin_name">Java Technology</span>
        
      </div>
    </nav>

    <div class="home-content">
      <div class="overview-boxes">
        <div class="box">
          <div class="right-side">
            <div class="box-topic">Name</div>
            <% if (user.isIsAdmin()) { %>
            <div class="number"><%= String.format("%s (Admin)", user.getFirstName()) %></div>
            <% } else { %>
            <div class="number"><%= String.format("%s", user.getFirstName()) %></div>
            <% } %>
          </div>
        </div>
          
        <div class="box">
          <div class="right-side">
              <% if (user.isIsAdmin()) { %>
            <div class="box-topic">Total Users</div>
            <div class="number"><%= accountInfo.size() %></div>
            <% } else { %>
            <div class="box-topic">Role</div>
            <div class="number">Normal User</div>
            <% } %>
          </div>
        </div>
          
        <div class="box">
          <div class="right-side">
              <% if (user.isIsAdmin()) { %>
            <div class="box-topic">Total Resources</div>
            <div class="number"><%= resourceInfo.size() %></div>
            <% } else { %>
            <div class="box-topic">Available Resources</div>
            <% int count = 0; %>
            <% for (Resource i: resourceInfo) { %>
            <% if (i.isIsAvailable()) { %>
            <% count++; %>
            <% } %>
            <% } %>
            <div class="number"><%= count %> out of <%= resourceInfo.size() %></div>
            <!-- <div class="number"><%= resourceInfo.size() %></div> -->
            <% } %>
          </div>
        </div>
          
        <div class="box">
          <div class="right-side">
              <% if (user.isIsAdmin()) { %>
            <div class="box-topic">Total Bookings</div>
            <div class="number"><%= bookingInfo.size() %></div>
            <% } else { %>
            <div class="box-topic">All-Time Booking</div>
            <div class="number"><%= bookingInfo.size() %></div>
            <% } %>
          </div>
        </div>
          
      </div>

      <div class="left_box">
        <div class="recent_booking box">
          <% if (user.isIsAdmin()) { %>
          <div class="title">User Management</div>
          <% } else { %>
          <div class="title">Your Account</div>
          <% } %>
          
          <% if (user.isIsAdmin()) { %>
          <div class="button"><a href="#"
          onClick="MyWindow=window.open('direct_add_user.jsp','MyWindow','width=600,height=1000'); return false;">+ Add User</a></div>
          <% } %>
          
        
          <div class="booking_details">
        
              <% if (user.isIsAdmin()) { %>
            <ul class="details">
              <li class="topic">No</li>
              <li>1</li>
              <% for(int i = 2; i <= accountInfo.size(); i++) { %>
              <li><%= i %></li>
                <%  } %>
              </ul>
            <% } %>
              
            <ul class="details">
            <li class="topic">First Name</li>
            <li><%= user.getFirstName() %></li>
            <% for (Account i: accountInfo) { 
                if (!i.getLoginName().equals(user.getLoginName())) { %>
            <li><%= i.getFirstName() %></li>
            <% } %>
            <% } %>
            </ul>
            
            <ul class="details">
            <li class="topic">Last Name</li>
            <li><%= user.getLastName() %></li>
            <% for (Account i: accountInfo) { 
                if (!i.getLoginName().equals(user.getLoginName())) { %>
            <li><%= i.getLastName() %></li>
            <% } %>
            <% } %>
          </ul>
            
          <% if (user.isIsAdmin()) { %>
          <ul class="details">
              <li class="topic">Role</li>
              <li style="color: red;">Admin</li>
              <% for (Account i: accountInfo) { 
                if (!i.getLoginName().equals(user.getLoginName())) { %>
              <% if (i.isIsAdmin()) { %>
              <li style="color: red;">Admin</li>
              <% } else { %>
              <li style="color: green;">User</li>
              <% } %>
              <% } %>
              <% } %> 
          </ul>
          <% } %>
              
            <ul class="details">
            <li class="topic">Edit</li>
            <li><div class="small_button"><a href="#"
            onClick="MyWindow=window.open('update_user.jsp?account_name=<%= user.getLoginName() %>','MyWindow','width=600,height=1000'); return false;">Edit</a></div></li>
            <% for(int i = 2; i <= accountInfo.size(); i++) { %>
            <% if (accountInfo.get(i - 1).isIsAdmin()) { %>
            <li><div class="small_button"><a href="#">---</a></div></li>
            <% } else { %>
              <li><div class="small_button"><a href="#"
            onClick="MyWindow=window.open('update_user.jsp?account_name=<%= accountInfo.get(i - 1).getLoginName() %>','MyWindow','width=600,height=1000'); return false;">Edit</a></div></li>
            <%  } %>
            <%  } %>
            
            </ul>
            
            <% if (user.isIsAdmin()) { %>
            <ul class="details">
              <li class="topic">Delete</li>
              <li><div class="small_button"><a href="#">------</a></div></li>
              
                <% for(int i = 2; i <= accountInfo.size(); i++) { %>
                <% if (accountInfo.get(i - 1).isIsAdmin()) { %>
            <li><div class="small_button"><a href="#">------</a></div></li>
                <% } else { %>
              <li><div class="small_button"><a href="#"
            onClick="MyWindow=window.open('delete_user.jsp?account_name=<%= accountInfo.get(i - 1).getLoginName() %>','MyWindow','width=600,height=1000'); return false;">Delete</a></div></li>
                <%  } %>
                <%  } %>
          </ul>
          <% } %>
 
          </div>
        </div>
          
        <div class="right_box box">
            
            <% if (user.isIsAdmin()) { %>
            <div class="title">Resource Management</div>
            <% } else { %>
            <div class="title">Available Resource</div>
            <% } %>
            
            <% if (user.isIsAdmin()) { %>
            <div class="button"><a href="#"
            onClick="MyWindow=window.open('direct_add_resource.jsp','MyWindow','width=600,height=1000'); return false;">+ Add Resource</a></div>
            <% } %>
            
            <div class="booking_details">
              
            <ul class="details">
            <li class="topic">No</li>
            <% for (int i = 1; i <= resourceInfo.size(); i++) { %>
            <li><%= i %></li>
            <% } %>
            </ul>
            
            <ul class="details">
            <li class="topic">Resource</li>
            <% for (Resource i: resourceInfo) { %>
            <li><%= i.getResourceName() %></li>
            <% } %>
          </ul>
            
          
          <ul class="details">
              <li class="topic">Availability</li>
              <% for (Resource i: resourceInfo) { %>
              <% if (i.isIsAvailable()) { %>
            <li style="color: green;">Yes</li>
            <% } else { %>
            <li style="color: red;">No</li>
            <% } %>
            <% } %>
          </ul>
          
        
          <% if (user.isIsAdmin()) { %>
          <ul class="details">
              <li class="topic">Edit</li>
              <% for (int i = 0; i < resourceInfo.size(); i++) { %>
              <li><div class="small_button"><a href="#"
              onClick="MyWindow=window.open('update_resource.jsp?resource_name=<%= resourceInfo.get(i).getResourceName() %>','MyWindow','width=600,height=1000'); return false;">Edit</a></li>
              <% } %>
          </ul>
          <% } %>
           
          <% if (user.isIsAdmin()) { %>
          <ul class="details">
              <li class="topic">Delete</li>
              <% for (int i = 0; i < resourceInfo.size(); i++) { %>
              <li><div class="small_button"><a href="#"
              onClick="MyWindow=window.open('delete_resource.jsp?resource_name=<%= resourceInfo.get(i).getResourceName() %>','MyWindow','width=600,height=1000'); return false;">Delete</a></li>
              <% } %>
          </ul>
          <% } %>
          
        </div>
      </div>
    </div>
          
          <br>
          
          <!-- dashboard set 2 -->
          
          <div class="left_box">
        <div class="recent_booking box" style="width: 100%;">
            
            <% if (user.isIsAdmin()) { %>
            <div class="title">Booking Management (Upcoming)</div>
            <% } else { %>
            <div class="title">Your Upcoming Bookings</div>
            <% } %>
          
          <div class="button"><a href="#"
          onClick="MyWindow=window.open('direct_add_booking.jsp','MyWindow','width=600,height=1000'); return false;">+ Add Booking</a></div>
            
          <div class="booking_details">
            <ul class="details">
              <li class="topic">No</li>
              <% for(int i = 1; i <= upComingBookingInfo.size(); i++) { %>
              <li><%= i %></li>
                <%  } %>
              </ul>
            
            <% if (user.isIsAdmin()) { %>
            <ul class="details">
            <li class="topic">Name</li>
            <% for (Booking i: upComingBookingInfo) { %>
            <li><%= i.getAccountName() %></li>
            <% } %>
            </ul>
            <% } %>
            
            <ul class="details">
            <li class="topic">Resource</li>
            <% for (Booking i: upComingBookingInfo) { %>
            <li><%= i.getResourceName() %></li>
            <% } %>

          </ul>
            
            
          <ul class="details">
            <li class="topic">From</li>
            <% for (Booking i: upComingBookingInfo) { %>
            <li><%= dtf.format(i.getBookingFrom()) %></li>
            <% } %>
          </ul>
          
          <ul class="details">
            <li class="topic">To</li>
            <% for (Booking i: upComingBookingInfo) { %>
            <li><%= dtf.format(i.getBookingTo()) %></li>
            <% } %>
          </ul>
              
            <ul class="details">
            <li class="topic">Change Time</li>
            <% for(int i = 0; i < upComingBookingInfo.size(); i++) { %>
              <li><div class="small_button"><a href="#"
            onClick="MyWindow=window.open('update_booking.jsp?booking_id=<%= upComingBookingInfo.get(i).getBookingId() %>','MyWindow','width=600,height=1000'); return false;">Edit</a></div></li>
            <%  } %>
            
            </ul>
              
              <ul class="details">
              <li class="topic">Cancel</li>
              <% for(int i = 0; i < upComingBookingInfo.size(); i++) { %>
              <li><div class="small_button"><a href="#"
            onClick="MyWindow=window.open('delete_booking.jsp?booking_id=<%= upComingBookingInfo.get(i).getBookingId() %>','MyWindow','width=600,height=1000'); return false;">Cancel</a></div></li>
            <%  } %>
              
          </ul>
 
          </div>
        </div>
            
            
          </div>
            
    <br>
    <!-- dashboard set 3 -->
            
    <div class="left_box">
        <div class="recent_booking box" style="width: 100%;">
            <div class="title">Booking Record Report</div>
            <br>
            <form action="generateResourceReport" method="post">
                
                
                <label for="reportAccountId">User</label>
                <select name="reportAccountId" id="reportAccountId" required>
                    <option value="-1">All Users</option>
                    <option value="<%= user.getAccountId() %>">Only Me</option>
                    
                    <% if (user.isIsAdmin()) { %>
                    <% for (Account i: accountInfo) { %>
                    <% if (!i.getLoginName().equals(user.getLoginName())) { %>
                    <option value="<%= i.getAccountId() %>"><%= String.format("%s %s (%s)", i.getFirstName(), i.getLastName(), i.getLoginName()) %></option>
                    <% } %>
                    <% } %>
                    <% } %>
                    
                    
                </select>
                
                
                <label for="reportResourceId">Resource</label>
                <select name="reportResourceId" id="reportResourceId" required>
                    <option value="-1">All Resources</option>
                    <% for (Resource i: resourceInfo) { %>
                    <% if (i.isIsAvailable()) { %>
                    <option value="<%= i.getResourceId() %>"><%= i.getResourceName() %></option>
                    <% } %>
                    <% } %>
                </select>
                
                
                <label for="reportFrom">From</label>
                <input type="datetime-local" name="reportFrom" id="reportFrom" 
                       <% if (session.getAttribute("reportFrom") != null) { %> 
                       value="<%= session.getAttribute("reportFrom") %>"
                       <% } else { %>
                       value="<%= now %>"
                       <% } %>
                       required />
                
                <label for="reportTo">To</label>
                <input type="datetime-local" name="reportTo" id="reportTo"
                       <% if (session.getAttribute("reportTo") != null) { %> 
                       value="<%= session.getAttribute("reportTo") %>"
                       <% } else { %>
                       value="<%= now.plusDays(1) %>"
                       <% } %>
                       required />

                
            <div class="button"><a><button type="submit" style="background: none; color: inherit; border: none; padding: 0; font: inherit; cursor: pointer; outline: inherit;">Generate</button></a></div>
            </form>
                
        
        <div class="booking_details">
            
            
            <ul class="details">
              <li class="topic">No</li>
              <% for(int i = 1; i <= reportResult.size(); i++) { %>
              <li><%= i %></li>
                <%  } %>
              </ul>
            
            <ul class="details">
              <li class="topic">Name</li>
              
              <% if (user.isIsAdmin()) { %>
              <% for (Booking i: reportResult) { %>
              <li><%= i.getAccountName() %></li>
              <% } %>
              <% } else { %>
              <% for (Booking i: reportResult) { %>
              <% if (i.getAccountId() != user.getAccountId()) { %>
              <li style="color:gray;">Anonymous User</li>
              <% } else { %>
              <li style="color:green; font-weight: bold;">You</li>
              <% } %>
              <% } %>
              <% } %>
              
              </ul>
              
            <ul class="details">
              <li class="topic">Resource</li>
            <% for (Booking i: reportResult) { %>
              <li><%= i.getResourceName() %></li>
              <% } %>
              </ul>
            
            <ul class="details">
              <li class="topic">From</li>
            <% for (Booking i: reportResult) { %>
              <li><%= dtf.format(i.getBookingFrom()) %></li>
              <% } %>
              </ul>
              
              <ul class="details">
              <li class="topic">To</li>
            <% for (Booking i: reportResult) { %>
              <li><%= dtf.format(i.getBookingTo()) %></li>
              <% } %>
              </ul>
              
              
        </div>

        </div>

    </div>
          
          
  </section>

  <script>
      
      
   let sidebar = document.querySelector(".sidebar");
   let sidebarBtn = document.querySelector(".sidebarBtn");
   sidebarBtn.onclick = function() {
   sidebar.classList.toggle("active");
   if(sidebar.classList.contains("active")){
   sidebarBtn.classList.replace("bx-menu" ,"bx-menu-alt-right");
   }else
   sidebarBtn.classList.replace("bx-menu-alt-right", "bx-menu");
   };
 </script>

</body>
</html>