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
@Table(name = "QUESTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q")
    , @NamedQuery(name = "Question.findByQuestionId", query = "SELECT q FROM Question q WHERE q.questionId = :questionId")
    , @NamedQuery(name = "Question.findByQuestionName", query = "SELECT q FROM Question q WHERE q.questionName = :questionName")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTION_ID")
    private Integer questionId;
    @Size(max = 255)
    @Column(name = "QUESTION_NAME")
    private String questionName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionQuestionId")
    private Collection<Choice> choiceCollection;
    @JoinColumn(name = "QUIZ_QUIZ_ID", referencedColumnName = "QUIZ_ID")
    @ManyToOne(optional = false)
    private Quiz quizQuizId;

    public Question() {
    }

    public Question(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    @XmlTransient
    public Collection<Choice> getChoiceCollection() {
        return choiceCollection;
    }

    public void setChoiceCollection(Collection<Choice> choiceCollection) {
        this.choiceCollection = choiceCollection;
    }

    public Quiz getQuizQuizId() {
        return quizQuizId;
    }

    public void setQuizQuizId(Quiz quizQuizId) {
        this.quizQuizId = quizQuizId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Question[ questionId=" + questionId + " ]";
    }
    
}
