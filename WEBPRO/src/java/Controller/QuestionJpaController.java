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
import model.Choice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Question;

/**
 *
 * @author ohmsu
 */
public class QuestionJpaController implements Serializable {

    public QuestionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Question question) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (question.getChoiceCollection() == null) {
            question.setChoiceCollection(new ArrayList<Choice>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Quiz quizOuizId = question.getQuizOuizId();
            if (quizOuizId != null) {
                quizOuizId = em.getReference(quizOuizId.getClass(), quizOuizId.getOuizId());
                question.setQuizOuizId(quizOuizId);
            }
            Collection<Choice> attachedChoiceCollection = new ArrayList<Choice>();
            for (Choice choiceCollectionChoiceToAttach : question.getChoiceCollection()) {
                choiceCollectionChoiceToAttach = em.getReference(choiceCollectionChoiceToAttach.getClass(), choiceCollectionChoiceToAttach.getChoiceId());
                attachedChoiceCollection.add(choiceCollectionChoiceToAttach);
            }
            question.setChoiceCollection(attachedChoiceCollection);
            em.persist(question);
            if (quizOuizId != null) {
                quizOuizId.getQuestionCollection().add(question);
                quizOuizId = em.merge(quizOuizId);
            }
            for (Choice choiceCollectionChoice : question.getChoiceCollection()) {
                Question oldQuestionQuestionIdOfChoiceCollectionChoice = choiceCollectionChoice.getQuestionQuestionId();
                choiceCollectionChoice.setQuestionQuestionId(question);
                choiceCollectionChoice = em.merge(choiceCollectionChoice);
                if (oldQuestionQuestionIdOfChoiceCollectionChoice != null) {
                    oldQuestionQuestionIdOfChoiceCollectionChoice.getChoiceCollection().remove(choiceCollectionChoice);
                    oldQuestionQuestionIdOfChoiceCollectionChoice = em.merge(oldQuestionQuestionIdOfChoiceCollectionChoice);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findQuestion(question.getQuestionId()) != null) {
                throw new PreexistingEntityException("Question " + question + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Question question) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Question persistentQuestion = em.find(Question.class, question.getQuestionId());
            Quiz quizOuizIdOld = persistentQuestion.getQuizOuizId();
            Quiz quizOuizIdNew = question.getQuizOuizId();
            Collection<Choice> choiceCollectionOld = persistentQuestion.getChoiceCollection();
            Collection<Choice> choiceCollectionNew = question.getChoiceCollection();
            List<String> illegalOrphanMessages = null;
            for (Choice choiceCollectionOldChoice : choiceCollectionOld) {
                if (!choiceCollectionNew.contains(choiceCollectionOldChoice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Choice " + choiceCollectionOldChoice + " since its questionQuestionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (quizOuizIdNew != null) {
                quizOuizIdNew = em.getReference(quizOuizIdNew.getClass(), quizOuizIdNew.getOuizId());
                question.setQuizOuizId(quizOuizIdNew);
            }
            Collection<Choice> attachedChoiceCollectionNew = new ArrayList<Choice>();
            for (Choice choiceCollectionNewChoiceToAttach : choiceCollectionNew) {
                choiceCollectionNewChoiceToAttach = em.getReference(choiceCollectionNewChoiceToAttach.getClass(), choiceCollectionNewChoiceToAttach.getChoiceId());
                attachedChoiceCollectionNew.add(choiceCollectionNewChoiceToAttach);
            }
            choiceCollectionNew = attachedChoiceCollectionNew;
            question.setChoiceCollection(choiceCollectionNew);
            question = em.merge(question);
            if (quizOuizIdOld != null && !quizOuizIdOld.equals(quizOuizIdNew)) {
                quizOuizIdOld.getQuestionCollection().remove(question);
                quizOuizIdOld = em.merge(quizOuizIdOld);
            }
            if (quizOuizIdNew != null && !quizOuizIdNew.equals(quizOuizIdOld)) {
                quizOuizIdNew.getQuestionCollection().add(question);
                quizOuizIdNew = em.merge(quizOuizIdNew);
            }
            for (Choice choiceCollectionNewChoice : choiceCollectionNew) {
                if (!choiceCollectionOld.contains(choiceCollectionNewChoice)) {
                    Question oldQuestionQuestionIdOfChoiceCollectionNewChoice = choiceCollectionNewChoice.getQuestionQuestionId();
                    choiceCollectionNewChoice.setQuestionQuestionId(question);
                    choiceCollectionNewChoice = em.merge(choiceCollectionNewChoice);
                    if (oldQuestionQuestionIdOfChoiceCollectionNewChoice != null && !oldQuestionQuestionIdOfChoiceCollectionNewChoice.equals(question)) {
                        oldQuestionQuestionIdOfChoiceCollectionNewChoice.getChoiceCollection().remove(choiceCollectionNewChoice);
                        oldQuestionQuestionIdOfChoiceCollectionNewChoice = em.merge(oldQuestionQuestionIdOfChoiceCollectionNewChoice);
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
                Integer id = question.getQuestionId();
                if (findQuestion(id) == null) {
                    throw new NonexistentEntityException("The question with id " + id + " no longer exists.");
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
            Question question;
            try {
                question = em.getReference(Question.class, id);
                question.getQuestionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The question with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Choice> choiceCollectionOrphanCheck = question.getChoiceCollection();
            for (Choice choiceCollectionOrphanCheckChoice : choiceCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Question (" + question + ") cannot be destroyed since the Choice " + choiceCollectionOrphanCheckChoice + " in its choiceCollection field has a non-nullable questionQuestionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Quiz quizOuizId = question.getQuizOuizId();
            if (quizOuizId != null) {
                quizOuizId.getQuestionCollection().remove(question);
                quizOuizId = em.merge(quizOuizId);
            }
            em.remove(question);
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

    public List<Question> findQuestionEntities() {
        return findQuestionEntities(true, -1, -1);
    }

    public List<Question> findQuestionEntities(int maxResults, int firstResult) {
        return findQuestionEntities(false, maxResults, firstResult);
    }

    private List<Question> findQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Question.class));
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

    public Question findQuestion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Question.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Question> rt = cq.from(Question.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
