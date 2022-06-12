/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Class.ErrorCode;
import Class.SQL;
import java.io.IOException;
import java.io.PrintWriter;
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


@WebServlet(name = "userUpdate", urlPatterns = {"/userUpdate"})
public class userUpdate extends HttpServlet {

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
        
        String accoutName = request.getParameter("account_name");
        String firstName = request.getParameter("updateFirstName");
        String lastName = request.getParameter("updateLastName");
        String password = request.getParameter("updatePassword");
        
        
        SQL db = new SQL();
        
        Connection sqlConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(db.getSQLName(), db.getSQLAccount(), db.getSQLPassword());
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery(String.format("SELECT * FROM account WHERE account_name = \"%s\";", accoutName));
            
            Boolean isAdmin;
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("is_admin"));
                isAdmin = resultSet.getInt("is_admin") == 1;
            } else {
                isAdmin = false;
            }

            statement.executeUpdate(String.format("UPDATE account "
                    + "SET first_name = \"%s\", last_name = \"%s\", password = \"%s\" "
                    + "WHERE account_name = \"%s\"", firstName, lastName, password, accoutName));
            
            RequestDispatcher view = request.getRequestDispatcher("complete.jsp");
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
