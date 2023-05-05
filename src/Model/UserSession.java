/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hp
 */
public class UserSession {
    private static UserSession instance;
    private int userId;
        private User loggedInUser;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
     public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
     
     public void closeSession() {
        userId = 0;
        instance = null;
    }
     public User getLoggedInUser() {
    return loggedInUser;
}
     
      public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
