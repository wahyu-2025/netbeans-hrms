/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import model.LoginModel;

/**
 *
 * @author user
 */
public class Session {

    private static Session instance;
    private LoginModel currentUser;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUser(LoginModel user) {
        this.currentUser = user;
    }

    public LoginModel getUser() {
        return currentUser;
    }
}
