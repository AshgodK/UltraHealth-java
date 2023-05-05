/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author hp
 */
public class Response {
    private int id;
    private Question question;
    private String text;
    private Date date;
    int questionId;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    

    public Response() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Response(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Response(Question question, String text, Date date) {
        this.question = question;
        this.text = text;
        this.date = date;
    }

    public Response(int id, Question question, String text, Date date) {
        this.id = id;
        this.question = question;
        this.text = text;
        this.date = date;
    }

    
    
    
    @Override
    public String toString() {
        return "Response{" + "id=" + id + ", question=" + question + ", text=" + text + ", date=" + date + '}';
    }

   
    
}
