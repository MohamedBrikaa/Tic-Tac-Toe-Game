/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed-Suliman
 */
public class GameMatch {

    //String u1, u2;
    Socket s1, s2;
    int playerNumber = 0, cntr = 0;

  
    PrintWriter[] ps = new PrintWriter[2];
    String[][] grid = new String[3][3];
    String[] XO = new String[]{"X", "O"};
    String[] users=new String[2];

    String opponentUserName, myUserName;
    PrintStream opponentPS;

    public GameMatch(String u1, String u2, Socket s1, Socket s2) {
        try {
           // this.u1 = u1;
            //this.u2 = u2;
            this.s1 = s1;
            this.s2 = s2;
            
            users[0]=u1;
            users[1]=u2;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[i][j] = "-";
                }
            }
            System.out.println("this is s1 " + this.s1);
            System.out.println("this is s2 " + this.s2);

            //ba3dein han-assign kol wa7d fihom x aw o
           // dis[0] = new DataInputStream(this.s1.getInputStream());
            ps[0] = new  PrintWriter(this.s1.getOutputStream(),true);
            ps[0].println(XO[0]);

            //dis[1] = new DataInputStream(this.s2.getInputStream());
            ps[1] = new PrintWriter(this.s2.getOutputStream(),true);
            ps[1].println(XO[1]);

        } catch (IOException ex) {
            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public GameMatch(PrintStream opponentPS, String opponentUserName, String myUserName) {
        this.opponentPS = opponentPS;
        this.opponentUserName = opponentUserName;
        this.myUserName = myUserName;
        
    }

    public GameMatch(){
    }
//    public void sendMoveToOpponent(String index) {
//        this.opponentPS.println(index);
//    }

    public void playerTurn(int playerNumber, String msg) {

        // System.out.println(index); 
        //index=dis[playerNumber].readLine();
            System.out.println(msg);
            if(msg.equals("pause")){
 
               // pauseGame(playerNumber);
            }
            else{
                int x = Integer.parseInt(msg);
                int i = x % 3;
                int j = x / 3;

                //ha-update l grid w ab3atha lel etnein
                grid[i][j] = XO[playerNumber];
               // ps[0].println(msg);
                System.out.println(users[(playerNumber+1)%2]+" and the move is "+msg);
                System.out.println("");
                ps[(playerNumber+1)%2].println(msg);

            }
//            ps[(playerNumber+1)%2].println("It is your turn");
//            ps[playerNumber].println("It is not your turn.");
    }

    public void sendNewMove(String move) {

        if (cntr < 9) {
            System.out.println("move number " + cntr);
            playerTurn(playerNumber, move);
            playerNumber++;
            playerNumber %= 2;
            cntr++;
        }

    }

}
