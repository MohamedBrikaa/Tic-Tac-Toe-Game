
package serverchatting;

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
    DataInputStream[] dis= new DataInputStream[2];
    PrintStream[] ps= new PrintStream[2];
    String[][] grid=new String[3][3];
    String[] XO=new String[]{"X","O"};
   
    public GameMatch(String u1,String u2,Socket s1,Socket s2){
        try {
            this.u1=u1;
            this.u2=u2;
            this.s1=s1;
            this.s2=s2;
            
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    grid[i][j]="-";
                }
            }
            System.out.println("this is s1 "+this.s1);
            System.out.println("this is s2 "+this.s2);
            
            
            dis[0]=new DataInputStream(this.s1.getInputStream());
            ps[0]=new PrintStream(this.s1.getOutputStream());
            ps[0].println(XO[0]);
            
            dis[1]=new DataInputStream(this.s2.getInputStream());
            ps[1]=new PrintStream(this.s2.getOutputStream());
            ps[1].println(XO[1]);
            start();
            
        } catch (IOException ex) {
            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void playerTurn(int playerNumber){
        try {
            
            ps[playerNumber].println("It is your turn");
            ps[(playerNumber+1)%2].println("It is not your turn.");
           
           
            String index="-";
         
            System.out.println(index); 
            index=dis[playerNumber].readLine();
          
            System.out.println(index);
            
            int x=Integer.parseInt(index);
            int i=x%3;
            int j=x/3;
            
            
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
            System.out.println("iteration number "+cntr);
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
