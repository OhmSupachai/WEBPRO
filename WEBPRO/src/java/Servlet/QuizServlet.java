/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controller.ChoiceJpaController;
import Controller.QuestionJpaController;
import Controller.QuizJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import javafx.print.Collation;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Choice;
import model.Question;
import model.Question_;
import model.Quiz;
import model.Users;
import sun.text.resources.CollationData;

/**
 *
 * @author ohmsu
 */
public class QuizServlet extends HttpServlet {

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
        EntityManager em = emf.createEntityManager();

        ChoiceJpaController cjc = new ChoiceJpaController(utx, emf);
        List<Choice> c = cjc.findChoiceEntities();

        Quiz qq = em.find(Quiz.class, 1);
        Collection<Question> questionCollection = qq.getQuestionCollection();

        int[] aryc = new int[c.size()];
        int score = 0;
        for (int i = 1; i <= questionCollection.size(); i++) {
            String sc = "correct_" + i;
            if (sc == null) {
                getServletContext().getRequestDispatcher("/WEB-INF/view/Quiz.jsp").forward(request, response);
                return;
            } else {
                int idc = Integer.parseInt(request.getParameter(sc));

                aryc[i] = idc;
            }

        }
        if (aryc == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/view/Quiz.jsp").forward(request, response);
        } else {
            for (Choice cc : c) {
                for (int i : aryc) {
                    if (cc.getChoiceId() == i) {
                        System.out.println(cc.getChoiceName());

                        if (cc.getCorrect() == 'T') {
                            System.out.println(cc.getCorrect());
                            score++;
                        }

                    }
                }
            }
            System.out.println(score);

            request.setAttribute("score", score);
            getServletContext().getRequestDispatcher("/WEB-INF/view/Scores.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/view/Quiz.jsp").forward(request, response);

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
        int id = Integer.parseInt(request.getParameter("id"));
        EntityManager em = emf.createEntityManager();
        Quiz quiz = em.find(Quiz.class, id);
        Collection<Question> quizcollection = quiz.getQuestionCollection();
        request.setAttribute("q", quizcollection);
        getServletContext().getRequestDispatcher("/WEB-INF/view/Quiz.jsp").forward(request, response);

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
