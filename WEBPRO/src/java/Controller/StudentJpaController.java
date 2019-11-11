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
import model.User;
import model.Quiz;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Student;

/**
 *
 * @author ohmsu
 */
public class StudentJpaController implements Serializable {

    public StudentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Student student) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (student.getQuizCollection() == null) {
            student.setQuizCollection(new ArrayList<Quiz>());
        }
        List<String> illegalOrphanMessages = null;
        User userUserIdOrphanCheck = student.getUserUserId();
        if (userUserIdOrphanCheck != null) {
            Student oldStudentOfUserUserId = userUserIdOrphanCheck.getStudent();
            if (oldStudentOfUserUserId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The User " + userUserIdOrphanCheck + " already has an item of type Student whose userUserId column cannot be null. Please make another selection for the userUserId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User userUserId = student.getUserUserId();
            if (userUserId != null) {
                userUserId = em.getReference(userUserId.getClass(), userUserId.getUserId());
                student.setUserUserId(userUserId);
            }
            Collection<Quiz> attachedQuizCollection = new ArrayList<Quiz>();
            for (Quiz quizCollectionQuizToAttach : student.getQuizCollection()) {
                quizCollectionQuizToAttach = em.getReference(quizCollectionQuizToAttach.getClass(), quizCollectionQuizToAttach.getOuizId());
                attachedQuizCollection.add(quizCollectionQuizToAttach);
            }
            student.setQuizCollection(attachedQuizCollection);
            em.persist(student);
            if (userUserId != null) {
                userUserId.setStudent(student);
                userUserId = em.merge(userUserId);
            }
            for (Quiz quizCollectionQuiz : student.getQuizCollection()) {
                Student oldStudentStudentIdOfQuizCollectionQuiz = quizCollectionQuiz.getStudentStudentId();
                quizCollectionQuiz.setStudentStudentId(student);
                quizCollectionQuiz = em.merge(quizCollectionQuiz);
                if (oldStudentStudentIdOfQuizCollectionQuiz != null) {
                    oldStudentStudentIdOfQuizCollectionQuiz.getQuizCollection().remove(quizCollectionQuiz);
                    oldStudentStudentIdOfQuizCollectionQuiz = em.merge(oldStudentStudentIdOfQuizCollectionQuiz);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findStudent(student.getStudentId()) != null) {
                throw new PreexistingEntityException("Student " + student + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Student student) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Student persistentStudent = em.find(Student.class, student.getStudentId());
            User userUserIdOld = persistentStudent.getUserUserId();
            User userUserIdNew = student.getUserUserId();
            Collection<Quiz> quizCollectionOld = persistentStudent.getQuizCollection();
            Collection<Quiz> quizCollectionNew = student.getQuizCollection();
            List<String> illegalOrphanMessages = null;
            if (userUserIdNew != null && !userUserIdNew.equals(userUserIdOld)) {
                Student oldStudentOfUserUserId = userUserIdNew.getStudent();
                if (oldStudentOfUserUserId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The User " + userUserIdNew + " already has an item of type Student whose userUserId column cannot be null. Please make another selection for the userUserId field.");
                }
            }
            for (Quiz quizCollectionOldQuiz : quizCollectionOld) {
                if (!quizCollectionNew.contains(quizCollectionOldQuiz)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Quiz " + quizCollectionOldQuiz + " since its studentStudentId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userUserIdNew != null) {
                userUserIdNew = em.getReference(userUserIdNew.getClass(), userUserIdNew.getUserId());
                student.setUserUserId(userUserIdNew);
            }
            Collection<Quiz> attachedQuizCollectionNew = new ArrayList<Quiz>();
            for (Quiz quizCollectionNewQuizToAttach : quizCollectionNew) {
                quizCollectionNewQuizToAttach = em.getReference(quizCollectionNewQuizToAttach.getClass(), quizCollectionNewQuizToAttach.getOuizId());
                attachedQuizCollectionNew.add(quizCollectionNewQuizToAttach);
            }
            quizCollectionNew = attachedQuizCollectionNew;
            student.setQuizCollection(quizCollectionNew);
            student = em.merge(student);
            if (userUserIdOld != null && !userUserIdOld.equals(userUserIdNew)) {
                userUserIdOld.setStudent(null);
                userUserIdOld = em.merge(userUserIdOld);
            }
            if (userUserIdNew != null && !userUserIdNew.equals(userUserIdOld)) {
                userUserIdNew.setStudent(student);
                userUserIdNew = em.merge(userUserIdNew);
            }
            for (Quiz quizCollectionNewQuiz : quizCollectionNew) {
                if (!quizCollectionOld.contains(quizCollectionNewQuiz)) {
                    Student oldStudentStudentIdOfQuizCollectionNewQuiz = quizCollectionNewQuiz.getStudentStudentId();
                    quizCollectionNewQuiz.setStudentStudentId(student);
                    quizCollectionNewQuiz = em.merge(quizCollectionNewQuiz);
                    if (oldStudentStudentIdOfQuizCollectionNewQuiz != null && !oldStudentStudentIdOfQuizCollectionNewQuiz.equals(student)) {
                        oldStudentStudentIdOfQuizCollectionNewQuiz.getQuizCollection().remove(quizCollectionNewQuiz);
                        oldStudentStudentIdOfQuizCollectionNewQuiz = em.merge(oldStudentStudentIdOfQuizCollectionNewQuiz);
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
                Integer id = student.getStudentId();
                if (findStudent(id) == null) {
                    throw new NonexistentEntityException("The student with id " + id + " no longer exists.");
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
            Student student;
            try {
                student = em.getReference(Student.class, id);
                student.getStudentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The student with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Quiz> quizCollectionOrphanCheck = student.getQuizCollection();
            for (Quiz quizCollectionOrphanCheckQuiz : quizCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Student (" + student + ") cannot be destroyed since the Quiz " + quizCollectionOrphanCheckQuiz + " in its quizCollection field has a non-nullable studentStudentId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userUserId = student.getUserUserId();
            if (userUserId != null) {
                userUserId.setStudent(null);
                userUserId = em.merge(userUserId);
            }
            em.remove(student);
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

    public List<Student> findStudentEntities() {
        return findStudentEntities(true, -1, -1);
    }

    public List<Student> findStudentEntities(int maxResults, int firstResult) {
        return findStudentEntities(false, maxResults, firstResult);
    }

    private List<Student> findStudentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Student.class));
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

    public Student findStudent(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Student> rt = cq.from(Student.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
