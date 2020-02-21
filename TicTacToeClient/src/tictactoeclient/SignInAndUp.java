package tictactoeclient;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Isaac Wahba
 */
public class SignInAndUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to Tic Tak Toy!");
        
//Log In Grid: 
        GridPane loginGrid = new GridPane();
        
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));

        
        Text logInScenetitle = new Text("Sign in Here!");
        logInScenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginGrid.add(logInScenetitle, 0, 0, 2, 1);

        
        Label userName = new Label("User Name:");
        loginGrid.add(userName, 0, 1);
        
        TextField userTextField = new TextField();
        loginGrid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        loginGrid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        loginGrid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        loginGrid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        loginGrid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
    @Override
    public void handle(ActionEvent e) {
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText("Sign in button pressed");
    }
});
   
     // Registeration Grid
        
        GridPane registerationGrid = new GridPane();
        
         registerationGrid.setAlignment(Pos.CENTER);
        registerationGrid.setHgap(10);
        registerationGrid.setVgap(10);
        registerationGrid.setPadding(new Insets(25, 25, 25, 25));
       
        Text RegisterationScenetitle = new Text("Sign Up Here!");
        RegisterationScenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        registerationGrid.add(RegisterationScenetitle, 0, 0, 2, 1);
        
        
        
        Label name = new Label("User Name:");
        registerationGrid.add(name, 0, 1);
        
        TextField nameTextField = new TextField();
        registerationGrid.add(nameTextField, 1, 1);

        Label enterPw = new Label("Enter Password:");
        registerationGrid.add(enterPw, 0, 2);

        PasswordField enterPwBox = new PasswordField();
        registerationGrid.add(enterPwBox, 1, 2);

        Label confirmPw = new Label("Confirm Password:");
        registerationGrid.add(confirmPw, 0, 3);

        PasswordField confirmPwBox = new PasswordField();
        registerationGrid.add(confirmPwBox, 1, 3);
        
        
        Button signUpBtn = new Button("Sign Up");
        HBox hbsignUpBtn = new HBox(10);
        hbsignUpBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbsignUpBtn.getChildren().add(signUpBtn);
        registerationGrid.add(hbsignUpBtn, 1, 4);
        
        final Text signUpActiontarget = new Text();
        loginGrid.add(signUpActiontarget, 1, 6);
        
        signUpBtn.setOnAction(new EventHandler<ActionEvent>() {
 
    @Override
    public void handle(ActionEvent e) {
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText("Sign Up button pressed");
    }
});
       Scene RegisterationScene = new Scene(registerationGrid, 300, 275);
       primaryStage.setScene(RegisterationScene);
             
        // Switcher :
        
        Button switcherBtn = new Button("Sign Up");
        HBox switcherHbBtn = new HBox(10);
        switcherHbBtn.setAlignment(Pos.BOTTOM_LEFT);
        switcherHbBtn.getChildren().add(switcherBtn);
        loginGrid.add(switcherHbBtn, 1, 4);
        
        final Text switchActiontarget = new Text();
        loginGrid.add(switchActiontarget, 1, 6);
        
        switcherBtn.setOnAction(new EventHandler<ActionEvent>() {
 
    @Override
    public void handle(ActionEvent e) {
        switchActiontarget.setFill(Color.FIREBRICK);
        switchActiontarget.setText("Switch button pressed");
       primaryStage.setScene(RegisterationScene);
       primaryStage.show();
       
    }
});
        
        
        //Scenes 
        
        Scene sighInScene = new Scene(loginGrid, 300, 275);
       // Scene RegisterationScene = new Scene(registerationGrid, 300, 275);
        primaryStage.setScene(sighInScene);
       // primaryStage.setScene(RegisterationScene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */


}