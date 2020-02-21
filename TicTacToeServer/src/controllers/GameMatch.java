/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nourfayed
 */
public class GameMatch {

 Socket s1,s2;
 int player1Id,player2Id;
 int playerNumber = 0, cntr = 0;
 
 PrintStream[] ps= new PrintStream[2];
 String[][] grid=new String[3][3];
 String[] XO=new String[]{"X","O"};
 String[] users=new String[2];

 

     
  public GameMatch(String u1,String u2,Socket s1,Socket s2){
      try {
          users[0]=u1;
          users[1]=u2;
          
          this.s1=s1;
          this.s2=s2;
          
          player1Id=UserModel.playerId(u1);
          player2Id=UserModel.playerId(u2);
          
         //first check if there is a saved game???
          
          Match savedMatch=RecordMatch.getRecordedMatch(player1Id,player2Id);
          
          if(savedMatch== null){
              System.out.println("A new Match");
              for(int i=0;i<3;i++){
                  for(int j=0;j<3;j++){
                      grid[i][j]="-";
                  }
              }
          }
          else {
              System.out.println("A saved Match");
              for(int i=0;i<3;i++){
                  for(int j=0;j<3;j++){
                      grid[i][j]=savedMatch.grid[i][j];
                  }
              }
              RecordMatch.removeMatch(savedMatch.matchId);
          }
          
          System.out.println("this is s1 "+this.s1);
          System.out.println("this is s2 "+this.s2);
          
          //initialize ps and assign X or O to each player
         
          ps[0]=new PrintStream(this.s1.getOutputStream());
          ps[0].println(XO[0]);
          
       
          ps[1]=new PrintStream(this.s2.getOutputStream());
          ps[1].println(XO[1]);
         
          
      } catch (IOException ex) {
          Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
      }
      
  }    public void playerTurn(int playerNumber,String msg){
 
        
         
         
         if(msg.equals("pause")){
             System.out.println(msg);
             pauseGame(playerNumber);
         }
         /*
         else if(playerNumber==8){
             ps[0].println("tie");
             ps[1].println("tie");
         }*/
         else if(Integer.parseInt(msg)>=0 && Integer.parseInt(msg)<9){
             int x = Integer.parseInt(msg);
             int i = x % 3;
             int j = x / 3;
             grid[i][j] = XO[playerNumber];
             //go check if the game is still going 
             if(!checkGrid()){
                System.out.println("sending to "+users[(playerNumber+1)%2]+" and "+users[playerNumber]+" played in  "+msg);
                System.out.println("");
                ps[(playerNumber+1)%2].println(msg);
             }
             else {
                // ps[playerNumber].println("win");
                 //ps[(playerNumber+1)%2].println("lose");
                 UserModel.updatePlayerScore(users[playerNumber], 5);
                 
             }    
                
         }
         else {
            msg=users[playerNumber]+" : "+msg;
            ps[playerNumber].println(msg);
            ps[(playerNumber+1)%2].println(msg); 
         }
         
         
     
     
     
 }
 public void sendNewMove(String move) {

     if (cntr < 9) {
         System.out.println("move number " + cntr);
         
        // if(cntr==8)playerNumber=8;
         
         playerTurn(playerNumber, move);
         playerNumber++;
         playerNumber %= 2;
         cntr++;
     }
    

 }


 
 public void pauseGame(int playerNumber){
      
     //insert match to database
     boolean b=RecordMatch.addMatch(player1Id,player2Id,grid,playerNumber);
     
     //close everything 
    try {
         for(int i=0;i<2;i++){
                
                 ps[i].close();          
            }
         s1.close();
         s2.close();
         //send to both players 2enna shatabna?
         
     } catch (IOException ex) {
             Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
         }
    
    
 
 }
 
 public  boolean checkGrid(){
     for(int i=0;i<3;i++){
         if(grid[i][0]==grid[i][1] && grid[i][1]==grid[i][2] && grid[i][0]!="-"){
             //sb won
             return true;
         }
         else if(grid[0][i]==grid[1][i] && grid[1][i]==grid[2][i] && grid[0][i]!="-"){
             return true;
         }
         
     }
     if(grid[0][0]==grid[1][1] && grid[1][1]==grid[2][2] && grid[0][0]!="-")return true;
     else if(grid[0][2]==grid[1][1] && grid[1][1]== grid[2][0] && grid[1][1]!="-" )return true;
     return false;
 }

}
