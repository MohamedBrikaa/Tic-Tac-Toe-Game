/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author sara
 */
public class Server extends Application
{
    private Object lblGame;
      String  str = null;
      String User=null;
      String password = null;
    @Override
    public void start(Stage primaryStage) throws IOException
    {
      ServerSocket ss=new ServerSocket(9001);
            Socket s=ss.accept();//establishes connection 

            DataInputStream dis=new DataInputStream(s.getInputStream());
System.out.println("Connection created");
        while(true){
         
            try {
                str = dis.readUTF();
                
                 if("login".equals(str))
            {System.out.println("here");
            User = dis.readUTF();
            password = dis.readUTF();
            System.out.println(User); 
            System.out.println(password);
            
           
            }
            } catch (IOException ex) {
               // Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

              
        
            
        ss.close();
    

        StackPane root = new StackPane();
   
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
       // primaryStage.show();
    }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
