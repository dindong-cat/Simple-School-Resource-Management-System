/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Class.Booking;
import Class.ErrorCode;
import Class.Resource;
import Class.SQL;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "generateResourceReport", urlPatterns = {"/generateResourceReport"})
public class generateResourceReport extends HttpServlet {

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
        
        HttpSession session =request.getSession();
        
        String reportFrom = request.getParameter("reportFrom");
        String reportTo = request.getParameter("reportTo");
        int reportResourceId = Integer.parseInt(request.getParameter("reportResourceId"));
        int reportAccountId = Integer.parseInt(request.getParameter("reportAccountId"));
        
        session.setAttribute("reportFrom", reportFrom);
        session.setAttribute("reportTo", reportTo);
        session.setAttribute("reportResourceId", reportResourceId);
        session.setAttribute("reportAccountId", reportAccountId);
        
        SQL db = new SQL();
        
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "";
        
        List<Booking> reportResult = new ArrayList();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            
            /* condition 1: all parameter no set */
            if (reportFrom.equals("-1") && reportTo.equals("-1") && reportResourceId == -1) {
                sqlQuery = "SELECT * FROM booking";
                // resultSet = statement.executeQuery("SELECT * FROM booking;");
            } 
            /* condition 2: Only set report start date */
            else if (!reportFrom.equals("-1") && reportTo.equals("-1") && reportResourceId == -1) {
                sqlQuery = String.format("SELECT * FROM booking WHERE booking_from >= \"%s\"", reportFrom);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_from >= \"%s\";", reportFrom));
            }
            /* condition 3: Only set report end date */
            else if (reportFrom.equals("-1") && !reportTo.equals("-1") && reportResourceId == -1) {
                sqlQuery = String.format("SELECT * FROM booking WHERE booking_to <= \"%s\"", reportTo);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_to <= \"%s\";", reportTo));
            }
            /* condition 4: Only set specific resource */
            else if (reportFrom.equals("-1") && reportTo.equals("-1") && reportResourceId != -1) {
                sqlQuery = String.format("SELECT * FROM booking WHERE resource_id = %d", reportResourceId);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE resource_id = %d;", reportResourceId));
            }
            /* condition 5: Set report start date and report end date */
            else if (!reportFrom.equals("-1") && !reportTo.equals("-1") && reportResourceId == -1) {
                sqlQuery = String.format("SELECT * FROM booking WHERE booking_from >= \"%s\" AND booking_to <= \"%s\"", reportFrom, reportTo);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_from >= \"%s\" AND booking_to <= \"%s\";", reportFrom, reportTo));
            }
            /* condition 6: Set report start date and specific resource */
            else if (!reportFrom.equals("-1") && reportTo.equals("-1") && reportResourceId != -1) {
                sqlQuery = String.format("SELECT * FROM booking WHERE booking_from >= \"%s\" AND resource_id = %d", reportFrom, reportResourceId);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_from >= \"%s\" AND resource_id = %d;", reportFrom, reportResourceId));
            }
            /* condition 7: Set report end date and specific resource*/
            else if (reportFrom.equals("-1") && !reportTo.equals("-1") && reportResourceId != -1) {
                sqlQuery = String.format("SELECT * FROM booking WHERE booking_to <= \"%s\" AND resource_id = %d", reportTo, reportResourceId);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_to <= \"%s\" AND resource_id = %d;", reportTo, reportResourceId));
            }
            /* condition 8: Set report start date, end date and specific resource*/
            else {
                sqlQuery = String.format("SELECT * FROM booking WHERE booking_from >= \"%s\" AND booking_to <= \"%s\" AND resource_id = %d", reportFrom, reportTo, reportResourceId);
                // resultSet = statement.executeQuery(String.format("SELECT * FROM booking WHERE booking_from >= \"%s\" AND booking_to <= \"%s\" AND resource_id = %d;", reportFrom, reportTo, reportResourceId));
            }
            
            // except if else, also demostrate concat to do the query
            // check if report need to have specific user's booking
            if (reportAccountId != -1) {
                String keyword = "WHERE";
                if (sqlQuery.contains(keyword)) {
                    sqlQuery += String.format(" AND account_id = %d;", reportAccountId);
                } else {
                    sqlQuery += String.format(" WHERE account_id = %d;", reportAccountId);
                }
            } else {
                sqlQuery += ";";
            }
            
            System.out.println(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);
            

            while (resultSet.next()) {
                
                Booking resourceInfo = new Booking();
                resourceInfo.setBookingId(resultSet.getInt("booking_id"));
                resourceInfo.setAccountId(resultSet.getInt("account_id"));
                resourceInfo.setResourceId(resultSet.getInt("resource_id"));
                resourceInfo.setBookingFrom(resultSet.getTimestamp("booking_from").toLocalDateTime());
                resourceInfo.setBookingTo(resultSet.getTimestamp("booking_to").toLocalDateTime());
               
                reportResult.add(resourceInfo);
            }
            
            
            // use id as index, then seek for the names in other databases
            for (Booking i: reportResult) {
                resultSet = statement.executeQuery(String.format("SELECT * FROM account WHERE account_id = %d;", i.getAccountId()));
                if (resultSet.next()) {
                    i.setAccountName(String.format("%s %s (%s)", resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("account_name")));
                }
                resultSet = statement.executeQuery(String.format("SELECT resource_name FROM resource WHERE resource_id = %d;", i.getResourceId()));
                if (resultSet.next()) {
                    i.setResourceName(resultSet.getString("resource_name"));
                }
            }
            
        session.setAttribute("reportResult", reportResult);
            
        RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");  
        view.forward(request,response);
            
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
