    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controller.QuizJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import model.Users;
import Controller.UsersJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.Quiz;

/**
 *
 * @author ohmsu
 */
public class LoginServlet extends HttpServlet {

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

        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        UsersJpaController ujc = new UsersJpaController(utx, emf);
        List<Users> users = ujc.findUsersEntities();
        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("message", "ใส่ไม่ครบ");
            getServletContext().getRequestDispatcher("/WEB-INF/view/Login.jsp").forward(request, response);

        } else {
            for (Users user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    QuizJpaController qjc = new QuizJpaController(utx, emf);
                    List<Quiz> quiz = qjc.findQuizEntities();
                    session.setAttribute("quiz", quiz);
                    getServletContext().getRequestDispatcher("/WEB-INF/view/Homepage.jsp").forward(request, response);
                    return;
                }

            }
        }
        request.setAttribute("message", "Invalid");
        getServletContext().getRequestDispatcher("/WEB-INF/view/Login.jsp").forward(request, response);

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
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/view/Login.jsp").forward(request, response);
        }
        QuizJpaController qjc = new QuizJpaController(utx, emf);
                    List<Quiz> quiz = qjc.findQuizEntities();
                    request.setAttribute("quiz", quiz);
        getServletContext().getRequestDispatcher("/WEB-INF/view/Homepage.jsp").forward(request, response);

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
