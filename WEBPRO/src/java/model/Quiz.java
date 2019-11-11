/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ohmsu
 */
@Entity
@Table(name = "QUIZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quiz.findAll", query = "SELECT q FROM Quiz q")
    , @NamedQuery(name = "Quiz.findByOuizId", query = "SELECT q FROM Quiz q WHERE q.ouizId = :ouizId")
    , @NamedQuery(name = "Quiz.findByQuizName", query = "SELECT q FROM Quiz q WHERE q.quizName = :quizName")})
public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OUIZ_ID")
    private Integer ouizId;
    @Size(max = 255)
    @Column(name = "QUIZ_NAME")
    private String quizName;
    @JoinColumn(name = "STUDENT_STUDENT_ID", referencedColumnName = "STUDENT_ID")
    @ManyToOne(optional = false)
    private Student studentStudentId;
    @JoinColumn(name = "TEACHER_TEACHER_ID", referencedColumnName = "TEACHER_ID")
    @ManyToOne(optional = false)
    private Teacher teacherTeacherId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizOuizId")
    private Collection<Question> questionCollection;

    public Quiz() {
    }

    public Quiz(Integer ouizId) {
        this.ouizId = ouizId;
    }

    public Integer getOuizId() {
        return ouizId;
    }

    public void setOuizId(Integer ouizId) {
        this.ouizId = ouizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Student getStudentStudentId() {
        return studentStudentId;
    }

    public void setStudentStudentId(Student studentStudentId) {
        this.studentStudentId = studentStudentId;
    }

    public Teacher getTeacherTeacherId() {
        return teacherTeacherId;
    }

    public void setTeacherTeacherId(Teacher teacherTeacherId) {
        this.teacherTeacherId = teacherTeacherId;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ouizId != null ? ouizId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quiz)) {
            return false;
        }
        Quiz other = (Quiz) object;
        if ((this.ouizId == null && other.ouizId != null) || (this.ouizId != null && !this.ouizId.equals(other.ouizId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Quiz[ ouizId=" + ouizId + " ]";
    }
    
}
