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
import model.Teacher;

/**
 *
 * @author ohmsu
 */
public class TeacherJpaController implements Serializable {

    public TeacherJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Teacher teacher) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (teacher.getQuizCollection() == null) {
            teacher.setQuizCollection(new ArrayList<Quiz>());
        }
        List<String> illegalOrphanMessages = null;
        User userUserIdOrphanCheck = teacher.getUserUserId();
        if (userUserIdOrphanCheck != null) {
            Teacher oldTeacherOfUserUserId = userUserIdOrphanCheck.getTeacher();
            if (oldTeacherOfUserUserId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The User " + userUserIdOrphanCheck + " already has an item of type Teacher whose userUserId column cannot be null. Please make another selection for the userUserId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User userUserId = teacher.getUserUserId();
            if (userUserId != null) {
                userUserId = em.getReference(userUserId.getClass(), userUserId.getUserId());
                teacher.setUserUserId(userUserId);
            }
            Collection<Quiz> attachedQuizCollection = new ArrayList<Quiz>();
            for (Quiz quizCollectionQuizToAttach : teacher.getQuizCollection()) {
                quizCollectionQuizToAttach = em.getReference(quizCollectionQuizToAttach.getClass(), quizCollectionQuizToAttach.getOuizId());
                attachedQuizCollection.add(quizCollectionQuizToAttach);
            }
            teacher.setQuizCollection(attachedQuizCollection);
            em.persist(teacher);
            if (userUserId != null) {
                userUserId.setTeacher(teacher);
                userUserId = em.merge(userUserId);
            }
            for (Quiz quizCollectionQuiz : teacher.getQuizCollection()) {
                Teacher oldTeacherTeacherIdOfQuizCollectionQuiz = quizCollectionQuiz.getTeacherTeacherId();
                quizCollectionQuiz.setTeacherTeacherId(teacher);
                quizCollectionQuiz = em.merge(quizCollectionQuiz);
                if (oldTeacherTeacherIdOfQuizCollectionQuiz != null) {
                    oldTeacherTeacherIdOfQuizCollectionQuiz.getQuizCollection().remove(quizCollectionQuiz);
                    oldTeacherTeacherIdOfQuizCollectionQuiz = em.merge(oldTeacherTeacherIdOfQuizCollectionQuiz);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTeacher(teacher.getTeacherId()) != null) {
                throw new PreexistingEntityException("Teacher " + teacher + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Teacher teacher) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Teacher persistentTeacher = em.find(Teacher.class, teacher.getTeacherId());
            User userUserIdOld = persistentTeacher.getUserUserId();
            User userUserIdNew = teacher.getUserUserId();
            Collection<Quiz> quizCollectionOld = persistentTeacher.getQuizCollection();
            Collection<Quiz> quizCollectionNew = teacher.getQuizCollection();
            List<String> illegalOrphanMessages = null;
            if (userUserIdNew != null && !userUserIdNew.equals(userUserIdOld)) {
                Teacher oldTeacherOfUserUserId = userUserIdNew.getTeacher();
                if (oldTeacherOfUserUserId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The User " + userUserIdNew + " already has an item of type Teacher whose userUserId column cannot be null. Please make another selection for the userUserId field.");
                }
            }
            for (Quiz quizCollectionOldQuiz : quizCollectionOld) {
                if (!quizCollectionNew.contains(quizCollectionOldQuiz)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Quiz " + quizCollectionOldQuiz + " since its teacherTeacherId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userUserIdNew != null) {
                userUserIdNew = em.getReference(userUserIdNew.getClass(), userUserIdNew.getUserId());
                teacher.setUserUserId(userUserIdNew);
            }
            Collection<Quiz> attachedQuizCollectionNew = new ArrayList<Quiz>();
            for (Quiz quizCollectionNewQuizToAttach : quizCollectionNew) {
                quizCollectionNewQuizToAttach = em.getReference(quizCollectionNewQuizToAttach.getClass(), quizCollectionNewQuizToAttach.getOuizId());
                attachedQuizCollectionNew.add(quizCollectionNewQuizToAttach);
            }
            quizCollectionNew = attachedQuizCollectionNew;
            teacher.setQuizCollection(quizCollectionNew);
            teacher = em.merge(teacher);
            if (userUserIdOld != null && !userUserIdOld.equals(userUserIdNew)) {
                userUserIdOld.setTeacher(null);
                userUserIdOld = em.merge(userUserIdOld);
            }
            if (userUserIdNew != null && !userUserIdNew.equals(userUserIdOld)) {
                userUserIdNew.setTeacher(teacher);
                userUserIdNew = em.merge(userUserIdNew);
            }
            for (Quiz quizCollectionNewQuiz : quizCollectionNew) {
                if (!quizCollectionOld.contains(quizCollectionNewQuiz)) {
                    Teacher oldTeacherTeacherIdOfQuizCollectionNewQuiz = quizCollectionNewQuiz.getTeacherTeacherId();
                    quizCollectionNewQuiz.setTeacherTeacherId(teacher);
                    quizCollectionNewQuiz = em.merge(quizCollectionNewQuiz);
                    if (oldTeacherTeacherIdOfQuizCollectionNewQuiz != null && !oldTeacherTeacherIdOfQuizCollectionNewQuiz.equals(teacher)) {
                        oldTeacherTeacherIdOfQuizCollectionNewQuiz.getQuizCollection().remove(quizCollectionNewQuiz);
                        oldTeacherTeacherIdOfQuizCollectionNewQuiz = em.merge(oldTeacherTeacherIdOfQuizCollectionNewQuiz);
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
                Integer id = teacher.getTeacherId();
                if (findTeacher(id) == null) {
                    throw new NonexistentEntityException("The teacher with id " + id + " no longer exists.");
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
            Teacher teacher;
            try {
                teacher = em.getReference(Teacher.class, id);
                teacher.getTeacherId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teacher with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Quiz> quizCollectionOrphanCheck = teacher.getQuizCollection();
            for (Quiz quizCollectionOrphanCheckQuiz : quizCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Teacher (" + teacher + ") cannot be destroyed since the Quiz " + quizCollectionOrphanCheckQuiz + " in its quizCollection field has a non-nullable teacherTeacherId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userUserId = teacher.getUserUserId();
            if (userUserId != null) {
                userUserId.setTeacher(null);
                userUserId = em.merge(userUserId);
            }
            em.remove(teacher);
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

    public List<Teacher> findTeacherEntities() {
        return findTeacherEntities(true, -1, -1);
    }

    public List<Teacher> findTeacherEntities(int maxResults, int firstResult) {
        return findTeacherEntities(false, maxResults, firstResult);
    }

    private List<Teacher> findTeacherEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teacher.class));
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

    public Teacher findTeacher(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teacher.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeacherCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teacher> rt = cq.from(Teacher.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
