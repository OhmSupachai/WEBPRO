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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.User;

/**
 *
 * @author ohmsu
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Student student = user.getStudent();
            if (student != null) {
                student = em.getReference(student.getClass(), student.getStudentId());
                user.setStudent(student);
            }
            Teacher teacher = user.getTeacher();
            if (teacher != null) {
                teacher = em.getReference(teacher.getClass(), teacher.getTeacherId());
                user.setTeacher(teacher);
            }
            em.persist(user);
            if (student != null) {
                User oldUserUserIdOfStudent = student.getUserUserId();
                if (oldUserUserIdOfStudent != null) {
                    oldUserUserIdOfStudent.setStudent(null);
                    oldUserUserIdOfStudent = em.merge(oldUserUserIdOfStudent);
                }
                student.setUserUserId(user);
                student = em.merge(student);
            }
            if (teacher != null) {
                User oldUserUserIdOfTeacher = teacher.getUserUserId();
                if (oldUserUserIdOfTeacher != null) {
                    oldUserUserIdOfTeacher.setTeacher(null);
                    oldUserUserIdOfTeacher = em.merge(oldUserUserIdOfTeacher);
                }
                teacher.setUserUserId(user);
                teacher = em.merge(teacher);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUser(user.getUserId()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getUserId());
            Student studentOld = persistentUser.getStudent();
            Student studentNew = user.getStudent();
            Teacher teacherOld = persistentUser.getTeacher();
            Teacher teacherNew = user.getTeacher();
            List<String> illegalOrphanMessages = null;
            if (studentOld != null && !studentOld.equals(studentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Student " + studentOld + " since its userUserId field is not nullable.");
            }
            if (teacherOld != null && !teacherOld.equals(teacherNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Teacher " + teacherOld + " since its userUserId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (studentNew != null) {
                studentNew = em.getReference(studentNew.getClass(), studentNew.getStudentId());
                user.setStudent(studentNew);
            }
            if (teacherNew != null) {
                teacherNew = em.getReference(teacherNew.getClass(), teacherNew.getTeacherId());
                user.setTeacher(teacherNew);
            }
            user = em.merge(user);
            if (studentNew != null && !studentNew.equals(studentOld)) {
                User oldUserUserIdOfStudent = studentNew.getUserUserId();
                if (oldUserUserIdOfStudent != null) {
                    oldUserUserIdOfStudent.setStudent(null);
                    oldUserUserIdOfStudent = em.merge(oldUserUserIdOfStudent);
                }
                studentNew.setUserUserId(user);
                studentNew = em.merge(studentNew);
            }
            if (teacherNew != null && !teacherNew.equals(teacherOld)) {
                User oldUserUserIdOfTeacher = teacherNew.getUserUserId();
                if (oldUserUserIdOfTeacher != null) {
                    oldUserUserIdOfTeacher.setTeacher(null);
                    oldUserUserIdOfTeacher = em.merge(oldUserUserIdOfTeacher);
                }
                teacherNew.setUserUserId(user);
                teacherNew = em.merge(teacherNew);
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
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Student studentOrphanCheck = user.getStudent();
            if (studentOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Student " + studentOrphanCheck + " in its student field has a non-nullable userUserId field.");
            }
            Teacher teacherOrphanCheck = user.getTeacher();
            if (teacherOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Teacher " + teacherOrphanCheck + " in its teacher field has a non-nullable userUserId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
