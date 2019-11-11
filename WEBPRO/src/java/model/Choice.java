/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ohmsu
 */
@Entity
@Table(name = "CHOICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Choice.findAll", query = "SELECT c FROM Choice c")
    , @NamedQuery(name = "Choice.findByChoiceId", query = "SELECT c FROM Choice c WHERE c.choiceId = :choiceId")
    , @NamedQuery(name = "Choice.findByChoiceName", query = "SELECT c FROM Choice c WHERE c.choiceName = :choiceName")
    , @NamedQuery(name = "Choice.findByCorrect", query = "SELECT c FROM Choice c WHERE c.correct = :correct")})
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHOICE_ID")
    private Integer choiceId;
    @Size(max = 255)
    @Column(name = "CHOICE_NAME")
    private String choiceName;
    @Column(name = "CORRECT")
    private Character correct;
    @JoinColumn(name = "QUESTION_QUESTION_ID", referencedColumnName = "QUESTION_ID")
    @ManyToOne(optional = false)
    private Question questionQuestionId;

    public Choice() {
    }

    public Choice(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceName() {
        return choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public Character getCorrect() {
        return correct;
    }

    public void setCorrect(Character correct) {
        this.correct = correct;
    }

    public Question getQuestionQuestionId() {
        return questionQuestionId;
    }

    public void setQuestionQuestionId(Question questionQuestionId) {
        this.questionQuestionId = questionQuestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choiceId != null ? choiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Choice)) {
            return false;
        }
        Choice other = (Choice) object;
        if ((this.choiceId == null && other.choiceId != null) || (this.choiceId != null && !this.choiceId.equals(other.choiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Choice[ choiceId=" + choiceId + " ]";
    }
    //555555
    
}
