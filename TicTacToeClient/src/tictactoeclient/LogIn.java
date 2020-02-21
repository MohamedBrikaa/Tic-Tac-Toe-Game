package tictactoeclient;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.stage.StageStyle;
import models.User;

/**
 *
 * @author Isaac Wahba
 */
public class LogIn extends Application {

    private ServerSocket server = null;
    Socket s;
    String serverMessage = "";
    PrintStream toServer = null;
    DataInputStream fromServer;
    String mssg;
    static Vector<User> playersList = new Vector<>();
    User currentUser;
    boolean loggedIn = false;
//    boolean isServerOn = false;

    public void init() {
        try {
            this.s = new Socket("127.0.0.1", 20080);
//            isServerOn = true;
            this.toServer = new PrintStream(s.getOutputStream());
            this.fromServer = new DataInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while (isServerOn) {
//                    try {
//                        System.out.println("waiting for message");
//                        mssg = fromServer.readLine();
//                        System.out.println(mssg);
//
//                        if (mssg.equals("serveroff")) {
//
//                            isServerOn = false;
//                            closeConnectionToServer();
//
//                        } else if (mssg.equals("invitation")) {
//
//                            String invitingPlayerUserName = fromServer.readLine();
//                            System.out.println("invitation from " + invitingPlayerUserName + "player");
//                            toServer.println("accept");
//
//                        } else if (mssg.equals("updateStatus")) {
//
//                        }
//                    } catch (IOException ex) {
//                        Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }).start();
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Login Here!");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");

        Button Signupbtn = new Button("Sign up");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(Signupbtn);
        HBox HSignupBtn = new HBox(10);
        grid.add(HSignupBtn, 1, 4);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        Scene scene = new Scene(grid, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        //login btn action
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @FXML

            @Override
            public void handle(ActionEvent e) {
                System.out.println("login");
                String User = userTextField.getText();
                String Pass = pwBox.getText();

                System.out.println(userTextField.getText());
                System.out.println(pwBox.getText());
                toServer.println("login");
                toServer.println(User);
                toServer.println(Pass);

                try {
                    mssg = fromServer.readLine();
                    if (mssg.equals("loginDone")) {

                        handleLoginOpeartionWithServer();
                        LoadHomePage(primaryStage);
                        primaryStage.show();
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Sign in button pressed");
                        ClientListner listner = new ClientListner();
                        listner.start();
//                        listner.s
                    } else if (mssg.equals("loginFailed")) {

                        System.out.println("login Failed Please try again");

                    }
                } catch (IOException ex) {
                    Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void LoadHomePage(Stage primaryStage) {

        GridPane Grid = new GridPane();
        Grid.setAlignment(Pos.CENTER);
        Grid.setHgap(10);
        Grid.setVgap(10);
        Grid.setPadding(new Insets(25, 25, 25, 25));
        Button PlayCompBtn = new Button("Play with Computer");
        Text Scenetitle = new Text("Play Here!");
        Scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Grid.add(Scenetitle, 0, 0, 2, 1);
        Grid.add(PlayCompBtn, 1, 4);
        Scene RegisterationScene = new Scene(Grid, 500, 600);
        primaryStage.setScene(RegisterationScene);

    }

    private User receiveUserInfo() {
        User user = new User();
        try {
            user.userID = Integer.valueOf(fromServer.readLine());
            user.userName = fromServer.readLine();
            user.email = fromServer.readLine();
            user.state = fromServer.readLine();
            user.score = Integer.valueOf(fromServer.readLine());
        } catch (IOException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    private void printUserData(User u) {
        System.out.println(u.userID);
        System.out.println(u.userName);
        System.out.println(u.email);
        System.out.println(u.state);
        System.out.println(u.score);
    }

    private void handleLoginOpeartionWithServer() {
        try {
            loggedIn = true;
            currentUser = receiveUserInfo();
            printUserData(currentUser);
            int playersNum = Integer.valueOf(fromServer.readLine());
            for (int i = 0; i < playersNum; i++) {
                playersList.add(receiveUserInfo());
                printUserData(playersList.get(i));
            }
        } catch (IOException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        toServer.println("logout");
        closeConnectionToServer();
    }

    private void closeConnectionToServer() {
        try {
            fromServer.close();
            toServer.close();
            s.close();
            super.stop();
        } catch (IOException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ClientListner extends Thread {

        boolean isServerOn;

        public ClientListner() {
            System.out.println("thread created");
            isServerOn = true;
        }

        @Override
        public void run() {
            while (isServerOn) {
                try {
                    System.out.println("thread waiting for a message");
                    mssg = fromServer.readLine();
                    System.out.println(mssg);
                    if (mssg.equals("serveroff")) {
                        isServerOn = false;
                        closeConnectionToServer();
                    } else if (mssg.equals("invitation")) {
                        String invitingPlayerUserName = fromServer.readLine();
                        System.out.println("invitation from " + invitingPlayerUserName + "player");
                        toServer.println("accept");
                    } else if (mssg.equals("updateStatus")) {
                    } else {
                        System.out.println("unknown");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
