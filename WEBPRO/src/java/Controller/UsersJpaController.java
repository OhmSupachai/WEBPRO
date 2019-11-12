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
import model.Quiz;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Users;

/**
 *
 * @author ohmsu
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (users.getQuizCollection() == null) {
            users.setQuizCollection(new ArrayList<Quiz>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Quiz> attachedQuizCollection = new ArrayList<Quiz>();
            for (Quiz quizCollectionQuizToAttach : users.getQuizCollection()) {
                quizCollectionQuizToAttach = em.getReference(quizCollectionQuizToAttach.getClass(), quizCollectionQuizToAttach.getQuizId());
                attachedQuizCollection.add(quizCollectionQuizToAttach);
            }
            users.setQuizCollection(attachedQuizCollection);
            em.persist(users);
            for (Quiz quizCollectionQuiz : users.getQuizCollection()) {
                Users oldUsersUserIdOfQuizCollectionQuiz = quizCollectionQuiz.getUsersUserId();
                quizCollectionQuiz.setUsersUserId(users);
                quizCollectionQuiz = em.merge(quizCollectionQuiz);
                if (oldUsersUserIdOfQuizCollectionQuiz != null) {
                    oldUsersUserIdOfQuizCollectionQuiz.getQuizCollection().remove(quizCollectionQuiz);
                    oldUsersUserIdOfQuizCollectionQuiz = em.merge(oldUsersUserIdOfQuizCollectionQuiz);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsers(users.getUserId()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Users persistentUsers = em.find(Users.class, users.getUserId());
            Collection<Quiz> quizCollectionOld = persistentUsers.getQuizCollection();
            Collection<Quiz> quizCollectionNew = users.getQuizCollection();
            List<String> illegalOrphanMessages = null;
            for (Quiz quizCollectionOldQuiz : quizCollectionOld) {
                if (!quizCollectionNew.contains(quizCollectionOldQuiz)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Quiz " + quizCollectionOldQuiz + " since its usersUserId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Quiz> attachedQuizCollectionNew = new ArrayList<Quiz>();
            for (Quiz quizCollectionNewQuizToAttach : quizCollectionNew) {
                quizCollectionNewQuizToAttach = em.getReference(quizCollectionNewQuizToAttach.getClass(), quizCollectionNewQuizToAttach.getQuizId());
                attachedQuizCollectionNew.add(quizCollectionNewQuizToAttach);
            }
            quizCollectionNew = attachedQuizCollectionNew;
            users.setQuizCollection(quizCollectionNew);
            users = em.merge(users);
            for (Quiz quizCollectionNewQuiz : quizCollectionNew) {
                if (!quizCollectionOld.contains(quizCollectionNewQuiz)) {
                    Users oldUsersUserIdOfQuizCollectionNewQuiz = quizCollectionNewQuiz.getUsersUserId();
                    quizCollectionNewQuiz.setUsersUserId(users);
                    quizCollectionNewQuiz = em.merge(quizCollectionNewQuiz);
                    if (oldUsersUserIdOfQuizCollectionNewQuiz != null && !oldUsersUserIdOfQuizCollectionNewQuiz.equals(users)) {
                        oldUsersUserIdOfQuizCollectionNewQuiz.getQuizCollection().remove(quizCollectionNewQuiz);
                        oldUsersUserIdOfQuizCollectionNewQuiz = em.merge(oldUsersUserIdOfQuizCollectionNewQuiz);
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
                Integer id = users.getUserId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Quiz> quizCollectionOrphanCheck = users.getQuizCollection();
            for (Quiz quizCollectionOrphanCheckQuiz : quizCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Quiz " + quizCollectionOrphanCheckQuiz + " in its quizCollection field has a non-nullable usersUserId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
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

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
