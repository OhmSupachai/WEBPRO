/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Choice;
import model.Question;

/**
 *
 * @author ohmsu
 */
public class ChoiceJpaController implements Serializable {

    public ChoiceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Choice choice) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Question questionQuestionId = choice.getQuestionQuestionId();
            if (questionQuestionId != null) {
                questionQuestionId = em.getReference(questionQuestionId.getClass(), questionQuestionId.getQuestionId());
                choice.setQuestionQuestionId(questionQuestionId);
            }
            em.persist(choice);
            if (questionQuestionId != null) {
                questionQuestionId.getChoiceCollection().add(choice);
                questionQuestionId = em.merge(questionQuestionId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findChoice(choice.getChoiceId()) != null) {
                throw new PreexistingEntityException("Choice " + choice + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Choice choice) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choice persistentChoice = em.find(Choice.class, choice.getChoiceId());
            Question questionQuestionIdOld = persistentChoice.getQuestionQuestionId();
            Question questionQuestionIdNew = choice.getQuestionQuestionId();
            if (questionQuestionIdNew != null) {
                questionQuestionIdNew = em.getReference(questionQuestionIdNew.getClass(), questionQuestionIdNew.getQuestionId());
                choice.setQuestionQuestionId(questionQuestionIdNew);
            }
            choice = em.merge(choice);
            if (questionQuestionIdOld != null && !questionQuestionIdOld.equals(questionQuestionIdNew)) {
                questionQuestionIdOld.getChoiceCollection().remove(choice);
                questionQuestionIdOld = em.merge(questionQuestionIdOld);
            }
            if (questionQuestionIdNew != null && !questionQuestionIdNew.equals(questionQuestionIdOld)) {
                questionQuestionIdNew.getChoiceCollection().add(choice);
                questionQuestionIdNew = em.merge(questionQuestionIdNew);
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
                Integer id = choice.getChoiceId();
                if (findChoice(id) == null) {
                    throw new NonexistentEntityException("The choice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choice choice;
            try {
                choice = em.getReference(Choice.class, id);
                choice.getChoiceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The choice with id " + id + " no longer exists.", enfe);
            }
            Question questionQuestionId = choice.getQuestionQuestionId();
            if (questionQuestionId != null) {
                questionQuestionId.getChoiceCollection().remove(choice);
                questionQuestionId = em.merge(questionQuestionId);
            }
            em.remove(choice);
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

    public List<Choice> findChoiceEntities() {
        return findChoiceEntities(true, -1, -1);
    }

    public List<Choice> findChoiceEntities(int maxResults, int firstResult) {
        return findChoiceEntities(false, maxResults, firstResult);
    }

    private List<Choice> findChoiceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Choice.class));
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

    public Choice findChoice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Choice.class, id);
        } finally {
            em.close();
        }
    }

    public int getChoiceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Choice> rt = cq.from(Choice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
