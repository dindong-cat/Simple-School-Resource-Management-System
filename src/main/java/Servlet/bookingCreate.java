/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;


import Class.ErrorCode;
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

/**
 *
 * @author Home
 */
@WebServlet(name = "bookingCreate", urlPatterns = {"/bookingCreate"})
public class bookingCreate extends HttpServlet {

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
        
        // only possible to create normal user
        

        String bookingFrom = request.getParameter("createBookingFrom");
        int bookingDuration = Integer.parseInt(request.getParameter("createBookingDuration"));
        int bookingResource = Integer.parseInt(request.getParameter("createBookingResource"));
        int bookingAccountId = Integer.parseInt(request.getParameter("createAccountId"));
        
        SQL db = new SQL();
        
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery(String.format("SELECT * "
                    + "FROM booking "
                    + "WHERE resource_id = %d "
                    + "AND ("
                    + "(booking_from < \"%s\" AND booking_to > \"%s\") OR (booking_from < (\"%s\" + INTERVAL %d HOUR) AND booking_to > (\"%s\" + INTERVAL %d HOUR)) OR (booking_from > \"%s\" AND booking_to < (\"%s\" + INTERVAL %d HOUR))"
                    + ");"
                    , bookingResource, bookingFrom, bookingFrom, bookingFrom, bookingDuration, bookingFrom, bookingDuration, bookingFrom, bookingFrom, bookingDuration));

            
            // check if there is duplicate record in database before INSERT new data
            if (resultSet.next()) {
                ErrorCode error = ErrorCode.OverlappedBooking;
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("error_popup.jsp");  
                view.forward(request,response);
            } else {
                System.out.println(String.format("INSERT INTO booking (account_id, resource_id, booking_from, booking_to) VALUES (%d, %d, %s, (%s + INTERVAL %d HOUR));", bookingAccountId, bookingResource, bookingFrom, bookingFrom, bookingDuration));
                statement.executeUpdate(String.format("INSERT INTO booking (account_id, resource_id, booking_from, booking_to) VALUES (%d, %d, \"%s\", (\"%s\" + INTERVAL %d HOUR));", bookingAccountId, bookingResource, bookingFrom, bookingFrom, bookingDuration));
                RequestDispatcher view = request.getRequestDispatcher("complete.jsp");
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
