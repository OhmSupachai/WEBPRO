/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controller.UsersJpaController;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Users;

/**
 *
 * @author ohmsu
 */
public class EditpassServlet extends HttpServlet {
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String confirmnewpassword = request.getParameter("confirmnewpassword");
        String name = request.getParameter("home");
        UsersJpaController ujc = new UsersJpaController(utx, emf);
        List<Users> users = ujc.findUsersEntities();
        if (name.equals("Return to Home")) {
            getServletContext().getRequestDispatcher("/WEB-INF/view/Homepage.jsp").forward(request, response);
        }
        else{
        if (oldpassword.isEmpty()||newpassword.isEmpty()||confirmnewpassword.isEmpty()) {
             getServletContext().getRequestDispatcher("/WEB-INF/view/ViewAccount.jsp").forward(request, response);
        }
       
        if (user.getPassword().equals(oldpassword)) {
        for (Users u : users) {
            if (u.getPassword().endsWith(user.getPassword())) {
                if (newpassword.equals(confirmnewpassword)) {
                    user.setPassword(newpassword);
                    try {
                        ujc.edit(user);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(EditpassServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(EditpassServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(EditpassServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    session.setAttribute("user", user);
                    getServletContext().getRequestDispatcher("/WEB-INF/view/Homepage.jsp").forward(request, response);
                    return;
                }
            }
        }
        
        }
        
        }
       getServletContext().getRequestDispatcher("/WEB-INF/view/ViewAccount.jsp").forward(request, response);
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
        
        getServletContext().getRequestDispatcher("/WEB-INF/view/ViewAccount.jsp").forward(request, response);
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
