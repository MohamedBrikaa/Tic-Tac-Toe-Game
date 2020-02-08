/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.User;

/**
 *
 * @author Mohamed-Suliman
 */
public class UserModel {

    public boolean checkVaildation(String userName, String password) {
        return true;
    }

    public boolean addNewUser(User user) {
        return true;
    }

    public User getUserInfo(String userName) {
        return new User();
    }
}
