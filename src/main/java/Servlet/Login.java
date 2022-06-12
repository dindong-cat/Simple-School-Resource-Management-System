package Servlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Class.Account;
import Class.Booking;
import Class.Resource;
import Class.SQL;
import java.util.*;
import java.time.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Home
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        
        
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        // SQL login and password only can use getter method from a separated class in SQL.java
        SQL db = new SQL();
        
        // call database
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Account> allAccount = new ArrayList();
        List<Resource> allResource = new ArrayList();
        List<Booking> upComingBooking = new ArrayList();
        List<Booking> allBooking = new ArrayList();
        List<Resource> reportResult = new ArrayList();
 
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery(String.format("SELECT * FROM account WHERE (account_name = \"%s\" AND password = \"%s\")", userName, password));
            
            // can login
            if (resultSet.next()) {
                
                System.out.println("Login successful");

                Account login = new Account();
                login.setAccountId(resultSet.getInt("account_id"));
                login.setLoginName(resultSet.getString("account_name"));
                login.setFirstName(resultSet.getString("first_name"));
                login.setLastName(resultSet.getString("last_name"));
                login.setIsAdmin(resultSet.getInt("is_admin") == 1);
                
                // check if he or she is an admin
                // and calling same method with different parameter
                if (login.isIsAdmin()) {
                    
                    System.out.println("is admin");
                    allAccount = displayAllAccount(true, login.getAccountId());
                    allResource = displayAllResource();
                    upComingBooking = displayAllBooking(true, false, login.getAccountId());
                    allBooking = displayAllBooking(true, true, login.getAccountId());
                    
                } else {
                    
                    System.out.println("not admin");
                    allAccount = displayAllAccount(false, login.getAccountId());
                    allResource = displayAllResource();
                    upComingBooking = displayAllBooking(false, false, login.getAccountId());
                    allBooking = displayAllBooking(false, true, login.getAccountId());
                    
                }
                
                session.setAttribute("user", login);
                session.setAttribute("accountInfo", allAccount);
                session.setAttribute("resourceInfo", allResource);
                session.setAttribute("upComingBooking", upComingBooking);
                session.setAttribute("bookingInfo", allBooking);
                session.setAttribute("reportResult", reportResult);                
                
                RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");  
                view.forward(request,response);                
                
            }
            // cannot login then stay in index page
            else {
                System.out.println("Login failed");
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                view.forward(request,response);
            }
            
          
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(sqlConnection != null)
                    sqlConnection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }
    
    private List<Account> displayAllAccount(Boolean isAdmin, int accountId) {
        
        SQL db = new SQL();
        List<Account> allAccountInfo = new ArrayList<>();
        
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            if (isAdmin) {
                resultSet = statement.executeQuery("SELECT * FROM account;");
            } else {
                resultSet = statement.executeQuery(String.format("SELECT * FROM account WHERE account_id = %d;", accountId));
            }
            
            // put all information into generic arraylist
            while (resultSet.next()) {
                
                Account login = new Account();
                login.setAccountId(resultSet.getInt("account_id"));
                login.setLoginName(resultSet.getString("account_name"));
                login.setFirstName(resultSet.getString("first_name"));
                login.setLastName(resultSet.getString("last_name"));
                login.setIsAdmin(resultSet.getInt("is_admin") == 1);
                
                allAccountInfo.add(login);
                
            }
            
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(sqlConnection != null)
                    sqlConnection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return allAccountInfo;
    }
    
    
    
    private List<Resource> displayAllResource() {
        
        SQL db = new SQL();
        List<Resource> allResourceInfo = new ArrayList<>();
        
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM resource;");
            
            // put all information into generic arraylist
            while (resultSet.next()) {
                
                Resource resource = new Resource();
                resource.setResourceId(resultSet.getInt("resource_id"));
                resource.setResourceName(resultSet.getString("resource_name"));
                resource.setIsAvailable(resultSet.getInt("is_available") == 1);

                allResourceInfo.add(resource);

            }
            
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(sqlConnection != null)
                    sqlConnection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return allResourceInfo;
    }
    
    
    private List<Booking> displayAllBooking(Boolean isAdmin, Boolean displayPassedRecords, int accountId) {
        
        SQL db = new SQL();
        LocalDateTime currentTime = LocalDateTime.now().withNano(0);
        
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Booking> allResourceInfo = new ArrayList<>();
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            if (isAdmin) {
                resultSet = (displayPassedRecords) ? statement.executeQuery("SELECT * FROM booking;") : statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_from >= \"%s\";", currentTime));
            } else {
                resultSet = (displayPassedRecords) ? statement.executeQuery(String.format("SELECT * FROM booking WHERE account_id = %d;", accountId)) : statement.executeQuery(String.format("SELECT * FROM booking WHERE account_id = %d AND booking_from >= \"%s\";", accountId, currentTime));
            }
            
            // put all information into generic arraylist
            while (resultSet.next()) {
                
                Booking resourceInfo = new Booking();
                resourceInfo.setBookingId(resultSet.getInt("booking_id"));
                resourceInfo.setAccountId(resultSet.getInt("account_id"));
                resourceInfo.setResourceId(resultSet.getInt("resource_id"));
                resourceInfo.setBookingFrom(resultSet.getTimestamp("booking_from").toLocalDateTime());
                resourceInfo.setBookingTo(resultSet.getTimestamp("booking_to").toLocalDateTime());
               
                allResourceInfo.add(resourceInfo);
            }
            
            
            // use id as index, then seek for the names in other databases
            for (Booking i: allResourceInfo) {
                resultSet = statement.executeQuery(String.format("SELECT * FROM account WHERE account_id = %d;", i.getAccountId()));
                if (resultSet.next()) {
                    i.setAccountName(String.format("%s %s (%s)", resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("account_name")));
                }
                resultSet = statement.executeQuery(String.format("SELECT resource_name FROM resource WHERE resource_id = %d;", i.getResourceId()));
                if (resultSet.next()) {
                    i.setResourceName(resultSet.getString("resource_name"));
                }
            }
            
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(sqlConnection != null)
                    sqlConnection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return allResourceInfo;
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
