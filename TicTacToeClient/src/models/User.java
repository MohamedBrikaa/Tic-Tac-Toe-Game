/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
/**
 *
 * @author Mohamed-Suliman
 */
public class User {
    public int userID;
    public String userName;
    public String email;
    public String state;
    public String password;
    public String pic;
    public int score;
    
    public User(){
    
    }
    public User(int userId,String userName,String email,String password,int score ,String pic,String state){
        this.userID=userId;
        this.userName=userName;
        this.email=email;
        this.state=state;
        this.password=password;
        this.score=score;
        this.pic=pic;
    }
    
}
