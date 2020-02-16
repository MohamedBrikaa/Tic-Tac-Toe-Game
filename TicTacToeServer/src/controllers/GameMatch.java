
package controllers;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMatch extends Thread{
   
    String u1,u2;
    Socket s1,s2;
    DataInputStream[] dis;
    PrintStream[] ps;
    char[][] grid=new char[3][3];
    char[] XO=new char[]{'X','O'};
   
    public GameMatch(String u1,String u2,Socket s1,Socket s2){
        try {
            this.u1=u1;
            this.u2=u2;
            this.s1=s1;
            this.s2=s2;
            
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    grid[i][j]='-';
                }
            }
            
           
            
            //ba3dein han-assign kol wa7d fihom x aw o
            dis[0]=new DataInputStream(s1.getInputStream());
            ps[0]=new PrintStream(s1.getOutputStream());
            ps[0].println(XO[0]);
            
            dis[1]=new DataInputStream(s2.getInputStream());
            ps[1]=new PrintStream(s2.getOutputStream());
            ps[1].println(XO[1]);
            start();
            
        } catch (IOException ex) {
            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void playerTurn(int playerNumber){
        try {
            //ba3dein nbda2 l game ba2a kol wa7d 3aleh l dor ha2olo 3aleik l dor 
            ps[playerNumber].println("It is your turn");
           
            //ba3dein kol wa7d yb3at 7aga 
            String index=dis[playerNumber].readLine();
            int x=Integer.parseInt(index);
            int i=x%3;
            int j=x/3;
            
            //ha-update l grid w ab3atha lel etnein
            grid[i][j]=XO[playerNumber];
            ps[0].println(index);
            ps[1].println(index);
            
        } catch (IOException ex) {
            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void run(){
        int playerNumber=0;
        int cntr=0;
        while(cntr<9){
            playerTurn(playerNumber);
            playerNumber++;
            playerNumber%=2;  
            cntr++;
        }
        
    }
    
    
    public void stopGame(){
    
    }
    
    public void resumeGame(){
    
    }
    
    public static void main(String[] args){
    
    
    }
    
}
