/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Student;
import model.Teacher;
import model.Question;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Quiz;

/**
 *
 * @author ohmsu
 */
public class QuizJpaController implements Serializable {

    public QuizJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Quiz quiz) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (quiz.getQuestionCollection() == null) {
            quiz.setQuestionCollection(new ArrayList<Question>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Student studentStudentId = quiz.getStudentStudentId();
            if (studentStudentId != null) {
                studentStudentId = em.getReference(studentStudentId.getClass(), studentStudentId.getStudentId());
                quiz.setStudentStudentId(studentStudentId);
            }
            Teacher teacherTeacherId = quiz.getTeacherTeacherId();
            if (teacherTeacherId != null) {
                teacherTeacherId = em.getReference(teacherTeacherId.getClass(), teacherTeacherId.getTeacherId());
                quiz.setTeacherTeacherId(teacherTeacherId);
            }
            Collection<Question> attachedQuestionCollection = new ArrayList<Question>();
            for (Question questionCollectionQuestionToAttach : quiz.getQuestionCollection()) {
                questionCollectionQuestionToAttach = em.getReference(questionCollectionQuestionToAttach.getClass(), questionCollectionQuestionToAttach.getQuestionId());
                attachedQuestionCollection.add(questionCollectionQuestionToAttach);
            }
            quiz.setQuestionCollection(attachedQuestionCollection);
            em.persist(quiz);
            if (studentStudentId != null) {
                studentStudentId.getQuizCollection().add(quiz);
                studentStudentId = em.merge(studentStudentId);
            }
            if (teacherTeacherId != null) {
                teacherTeacherId.getQuizCollection().add(quiz);
                teacherTeacherId = em.merge(teacherTeacherId);
            }
            for (Question questionCollectionQuestion : quiz.getQuestionCollection()) {
                Quiz oldQuizOuizIdOfQuestionCollectionQuestion = questionCollectionQuestion.getQuizOuizId();
                questionCollectionQuestion.setQuizOuizId(quiz);
                questionCollectionQuestion = em.merge(questionCollectionQuestion);
                if (oldQuizOuizIdOfQuestionCollectionQuestion != null) {
                    oldQuizOuizIdOfQuestionCollectionQuestion.getQuestionCollection().remove(questionCollectionQuestion);
                    oldQuizOuizIdOfQuestionCollectionQuestion = em.merge(oldQuizOuizIdOfQuestionCollectionQuestion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findQuiz(quiz.getOuizId()) != null) {
                throw new PreexistingEntityException("Quiz " + quiz + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Quiz quiz) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Quiz persistentQuiz = em.find(Quiz.class, quiz.getOuizId());
            Student studentStudentIdOld = persistentQuiz.getStudentStudentId();
            Student studentStudentIdNew = quiz.getStudentStudentId();
            Teacher teacherTeacherIdOld = persistentQuiz.getTeacherTeacherId();
            Teacher teacherTeacherIdNew = quiz.getTeacherTeacherId();
            Collection<Question> questionCollectionOld = persistentQuiz.getQuestionCollection();
            Collection<Question> questionCollectionNew = quiz.getQuestionCollection();
            List<String> illegalOrphanMessages = null;
            for (Question questionCollectionOldQuestion : questionCollectionOld) {
                if (!questionCollectionNew.contains(questionCollectionOldQuestion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Question " + questionCollectionOldQuestion + " since its quizOuizId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (studentStudentIdNew != null) {
                studentStudentIdNew = em.getReference(studentStudentIdNew.getClass(), studentStudentIdNew.getStudentId());
                quiz.setStudentStudentId(studentStudentIdNew);
            }
            if (teacherTeacherIdNew != null) {
                teacherTeacherIdNew = em.getReference(teacherTeacherIdNew.getClass(), teacherTeacherIdNew.getTeacherId());
                quiz.setTeacherTeacherId(teacherTeacherIdNew);
            }
            Collection<Question> attachedQuestionCollectionNew = new ArrayList<Question>();
            for (Question questionCollectionNewQuestionToAttach : questionCollectionNew) {
                questionCollectionNewQuestionToAttach = em.getReference(questionCollectionNewQuestionToAttach.getClass(), questionCollectionNewQuestionToAttach.getQuestionId());
                attachedQuestionCollectionNew.add(questionCollectionNewQuestionToAttach);
            }
            questionCollectionNew = attachedQuestionCollectionNew;
            quiz.setQuestionCollection(questionCollectionNew);
            quiz = em.merge(quiz);
            if (studentStudentIdOld != null && !studentStudentIdOld.equals(studentStudentIdNew)) {
                studentStudentIdOld.getQuizCollection().remove(quiz);
                studentStudentIdOld = em.merge(studentStudentIdOld);
            }
            if (studentStudentIdNew != null && !studentStudentIdNew.equals(studentStudentIdOld)) {
                studentStudentIdNew.getQuizCollection().add(quiz);
                studentStudentIdNew = em.merge(studentStudentIdNew);
            }
            if (teacherTeacherIdOld != null && !teacherTeacherIdOld.equals(teacherTeacherIdNew)) {
                teacherTeacherIdOld.getQuizCollection().remove(quiz);
                teacherTeacherIdOld = em.merge(teacherTeacherIdOld);
            }
            if (teacherTeacherIdNew != null && !teacherTeacherIdNew.equals(teacherTeacherIdOld)) {
                teacherTeacherIdNew.getQuizCollection().add(quiz);
                teacherTeacherIdNew = em.merge(teacherTeacherIdNew);
            }
            for (Question questionCollectionNewQuestion : questionCollectionNew) {
                if (!questionCollectionOld.contains(questionCollectionNewQuestion)) {
                    Quiz oldQuizOuizIdOfQuestionCollectionNewQuestion = questionCollectionNewQuestion.getQuizOuizId();
                    questionCollectionNewQuestion.setQuizOuizId(quiz);
                    questionCollectionNewQuestion = em.merge(questionCollectionNewQuestion);
                    if (oldQuizOuizIdOfQuestionCollectionNewQuestion != null && !oldQuizOuizIdOfQuestionCollectionNewQuestion.equals(quiz)) {
                        oldQuizOuizIdOfQuestionCollectionNewQuestion.getQuestionCollection().remove(questionCollectionNewQuestion);
                        oldQuizOuizIdOfQuestionCollectionNewQuestion = em.merge(oldQuizOuizIdOfQuestionCollectionNewQuestion);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = quiz.getOuizId();
                if (findQuiz(id) == null) {
                    throw new NonexistentEntityException("The quiz with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Quiz quiz;
            try {
                quiz = em.getReference(Quiz.class, id);
                quiz.getOuizId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The quiz with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Question> questionCollectionOrphanCheck = quiz.getQuestionCollection();
            for (Question questionCollectionOrphanCheckQuestion : questionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Quiz (" + quiz + ") cannot be destroyed since the Question " + questionCollectionOrphanCheckQuestion + " in its questionCollection field has a non-nullable quizOuizId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Student studentStudentId = quiz.getStudentStudentId();
            if (studentStudentId != null) {
                studentStudentId.getQuizCollection().remove(quiz);
                studentStudentId = em.merge(studentStudentId);
            }
            Teacher teacherTeacherId = quiz.getTeacherTeacherId();
            if (teacherTeacherId != null) {
                teacherTeacherId.getQuizCollection().remove(quiz);
                teacherTeacherId = em.merge(teacherTeacherId);
            }
            em.remove(quiz);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Quiz> findQuizEntities() {
        return findQuizEntities(true, -1, -1);
    }

    public List<Quiz> findQuizEntities(int maxResults, int firstResult) {
        return findQuizEntities(false, maxResults, firstResult);
    }

    private List<Quiz> findQuizEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Quiz.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Quiz findQuiz(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Quiz.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuizCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Quiz> rt = cq.from(Quiz.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
