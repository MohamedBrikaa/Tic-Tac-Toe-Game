/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.xo;

import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Basant Mahrous
 */
public class GameServerXO extends Thread{
    
    ServerSocket serverSocket;
    static final int SocketServerPORT = 5098;

    @Override
    public void run() {
         try {
            serverSocket = new ServerSocket(SocketServerPORT);
            while (true) {
                Socket s = serverSocket.accept();
                new RequestHandler(s);
                 Thread acceptedThread = new Thread(new RequestHandler(s));
                    acceptedThread.setDaemon(true); //terminate the thread when program end
                    acceptedThread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(GameServerXO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GameServerXO()  {
       
       
    }
 public  void closeServer()
 {
        try {
            serverSocket.close();
            System.out.println("server is closed");
            //closeAllInternalSockets();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
  
 }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         //new GameServerXO();
    }

}
