/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.*;
/**
 *
 * @author Mohamed-Suliman
 */
public class GameMatch extends Thread{
    User player1;
    User player2;

    public void initGame(String user1Name, String user2Name) {
        player1 = UserModel.playerInfo(user1Name);
        player2 = UserModel.playerInfo(user2Name);
    }

    public void stopGame() {

    }

    public void resumeGame() {

    }
}