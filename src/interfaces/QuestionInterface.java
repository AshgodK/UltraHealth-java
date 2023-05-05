package Interfaces;


import Model.Question;
import java.util.List;
import javafx.collections.ObservableList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author hp
 */
public interface QuestionInterface {
        public void addQuestion(Question question);   
    public List<Question> searchByTitle(String query);
     public void updateQuestion(Question p);
     public void deleteQuestion(int id);
      public int nbrComments(int id);
       public ObservableList<Question> displayPosts();

    
}
