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
public class Question {
    private int id;
    private String title;
    private String categorie;
    private Date createDate;
    private String content;
    private User user; 
    private int likes;
private int dislikes;

    public Question(int id, String title, String categorie, Date createDate, String content, User user, int likes, int dislikes) {
        this.id = id;
        this.title = title;
        this.categorie = categorie;
        this.createDate = createDate;
        this.content = content;
        this.user = user;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Question(int id, int likes, int dislikes) {
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Question(int id, String title, String categorie, Date createDate,String content) {
        this.id = id;
        this.title = title;
        this.categorie = categorie;
        this.createDate = createDate;
        this.content = content;
      
    }
   
    
    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
     @Override
    public String toString() {
        return "Question{" + "id=" + id + ", title=" + title + ", content=" + content + ", createDate=" + createDate + '}';
    }
    
    
public void incrementLikes() {
    likes++;
}
public void decrementLikes() {
    likes--;
}
public void incrementDislikes() {
    dislikes++;
}
public void decrementDislikes() {
    dislikes--;
}
}
