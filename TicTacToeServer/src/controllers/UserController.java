package controllers;

import models.UserModel;

public class UserController {

    UserModel usermodel;

    public UserController() {
        usermodel = new UserModel();
    }

    public boolean signIn(String userName, String Password) {
        return usermodel.checkVaildation(userName, Password);
    }

    public boolean signUP(User user) {
        return usermodel.addNewUser(user);
    }

    public User getUserInfo(String userName) {
        return usermodel.getUserInfo(userName);
    }
}
