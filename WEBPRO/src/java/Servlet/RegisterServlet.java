/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controller.UsersJpaController;
import Controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.Users;

/**
 *
 * @author ohmsu
 */
public class RegisterServlet extends HttpServlet {
    @PersistenceUnit(unitName = "WEBPROPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

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
            throws ServletException, IOException, RollbackFailureException, Exception {
        response.setContentType("text/html;charset=UTF-8");
       String username = request.getParameter("username");
       String password = request.getParameter("password");
       String confirmedpass = request.getParameter("conpass");
       String fullname = request.getParameter("fullname");
       String email = request.getParameter("email");
       String type = request.getParameter("type");
       
        UsersJpaController ujc = new UsersJpaController(utx, emf);
        List<Users> user = ujc.findUsersEntities();
        
        if (username.isEmpty()||password.isEmpty()||fullname.isEmpty()||email.isEmpty()||type.isEmpty()
                ||confirmedpass.isEmpty()||!password.equals(confirmedpass)) {
            request.setAttribute("message", "Not completed");
            getServletContext().getRequestDispatcher("/WEB-INF/view/Register.jsp").forward(request, response);
        }
        for (Users u : user) {
            if (!u.getUsername().equals(username)) {
                u.setUsername(username);
                u.setPassword(password);
                u.setUserFullname(fullname);
                u.setUserEmail(email);
                u.setUserType(type);
                
                ujc.create(u);
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
