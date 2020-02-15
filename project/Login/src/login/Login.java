/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author sara
 */
public class Login extends Application
{Socket s;

            DataOutputStream dout;

    public Login() throws IOException
    {
        this.s = new Socket("localhost",9001);
        this.dout = new DataOutputStream(s.getOutputStream());
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        Button btn = new Button();
        btn.setText("Login");
        TextField textField=new TextField();
        textField.setTranslateY(-50);
        TextField pass=new TextField();

        pass.setTranslateY(-70);
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Login");
                String User=textField.getText();
                String Pass=pass.getText();
                System.out.println(textField.getText());
                System.out.println(pass.getText());
                try {
                    
                    Controller cont=new Controller(s,User,Pass);
                    
                } catch (IOException ex) {
                   // Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
              
            }
        });
       
        
        StackPane root = new StackPane();
        
        
        root.getChildren().addAll(btn,textField,pass);
        
        Scene scene = new Scene(root, 300, 250);
       
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    { 
        launch(args);
    }
    
}
