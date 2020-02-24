package sample;

import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.options.Option;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.controlsfx.control.Notifications;

public class TicTacToeClient extends Application {

    private ServerSocket server = null;
    Socket s;
    String serverMessage = "";
    PrintWriter toServer = null;
    BufferedReader fromServer;
    String mssg;
    int flag=0;
    static Vector<User> playersList = new Vector<>();
    User currentUser;
    boolean loggedIn = false;
    boolean isServerOn = false;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

    Controller controller = loader.getController();

    public void init() {

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = loader.load();
        primaryStage.setTitle("Login Here!");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFill(Color.WHITE);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
        grid.add(scenetitle, 0, 0, 2, 2);

        Label userName = new Label("User Name:");
     
        grid.add(userName, 0, 5);
userName.setTextFill(Color.WHITE);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 5);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 7);
pw.setTextFill(Color.WHITE);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 7);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 8);
        Button Signupbtn = new Button("Sign up");
        HBox HsignUpBtn = new HBox(10);
        HsignUpBtn.setAlignment(Pos.BOTTOM_RIGHT);
        HsignUpBtn.getChildren().add(Signupbtn);
        grid.add(HsignUpBtn, 1, 13);

        Label SignUp_name = new Label("User Name:");
        SignUp_name .setTextFill(Color.WHITE);
        grid.add(SignUp_name, 0, 10);

        TextField SignUp_nameTextField = new TextField();
        grid.add(SignUp_nameTextField, 1, 10);

        Label SignUp_enterPw = new Label("Enter Password:");
        SignUp_enterPw .setTextFill(Color.WHITE);
        grid.add(SignUp_enterPw, 0, 11);

        PasswordField SignUp_enterPwBox = new PasswordField();
    
        grid.add(SignUp_enterPwBox, 1, 11);

        Label Email = new Label("Email:");
        Email .setTextFill(Color.WHITE);
        grid.add(Email, 0, 12);

        TextField EmailTextField = new TextField();
        grid.add(EmailTextField, 1, 12);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 13);
        TextField IPTextField = new TextField();
        Button connectBtn = new Button("Connect");
        HBox HConnectBtn = new HBox(10);
        HConnectBtn.setAlignment(Pos.BOTTOM_RIGHT);
        HConnectBtn.getChildren().add(connectBtn);
        grid.add(HConnectBtn, 0, 29);
               HBox IP = new HBox(10);
        IP.setAlignment(Pos.BOTTOM_RIGHT);
        IP.getChildren().add(IPTextField);
        grid.add(IP, 1, 29);

        final Text signActiontarget = new Text();
        signActiontarget.setFill(Color.YELLOW);
        grid.add(signActiontarget,1, 2);
        final Text sigupNameInstruction = new Text();
        grid.add(sigupNameInstruction, 0, 14);
        sigupNameInstruction.setText("* Name: 2 fields, starts with CAPITAL LETTERS");
                final Text connectInstruction = new Text();
        grid.add(connectInstruction, 0, 16);
        connectInstruction.setText("*YOU HAVE TO CONNECT TO SERVER FIRST");
        final Text sigupPassInstruction = new Text();
        sigupPassInstruction.setFill(Color.YELLOW);
        sigupNameInstruction.setFill(Color.YELLOW);
        connectInstruction.setFill(Color.YELLOW);
        grid.add(sigupPassInstruction, 0, 15);
        sigupPassInstruction.setFont(Font.font("Tahoma", FontWeight.THIN, 12));
        sigupNameInstruction.setFont(Font.font("Tahoma", FontWeight.THIN, 12));
        sigupPassInstruction.setText("* Password: [4-8] Numbers & contains shape ");
  IPTextField.setText("127.0.0.1");

        connectBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               
                String IP = IPTextField.getText();
                flag=1;
                try {
                    s = new Socket(IP, 20080);
                    toServer = new PrintWriter(s.getOutputStream(), true);
                    fromServer = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
                    
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (IOException ex) {
                    signActiontarget.setText("Please Check Your Connection");
                    //Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String user = userTextField.getText();
                String Password = pwBox.getText();
try {
    if(flag==1)
    {  User usr = new User();
                toServer.println("login");
                toServer.println(user);
                toServer.println(Password);
                
                    mssg = fromServer.readLine();
                    if (mssg.equals("loginDone")) {
                        handleLoginOpeartionWithServer();
                        primaryStage.setScene(new Scene(root, 720, 700));
                        Controller controller = loader.getController();
                        controller.SetCurrentUserInfo(currentUser.userName, currentUser.score);
                        ClientListner listner = new ClientListner();
                        listner.start();
                        primaryStage.show();
                    } else if (mssg.equals("loginFailed")) {
                        signActiontarget.setText("login Failed Please try again");
                        System.out.println("login Failed Please try again");
                        
                    }
    }
    else{ signActiontarget.setText("Please Connect First then login");}
                } catch (IOException ex) {
                    signActiontarget.setText("Please Connect First then login");
                   // Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        final Text signUpActiontarget = new Text();
        grid.add(signUpActiontarget, 1, 15);

        Signupbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                String name = SignUp_nameTextField.getText();
                String Pass = SignUp_enterPwBox.getText();
                String Email = EmailTextField.getText();
                System.out.println(name);
                System.out.println(Pass);
                System.out.println(Email);
                String error = "";
                String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
                String patternName = "(" + regexName + "){2,3}";
                System.out.println("The name is: " + name);
                System.out.println("Is the above name valid? " + name.matches(patternName));
                boolean nameResult = name.matches(patternName);
                String regexEmail = "^(.+)@(.+)$";
                Pattern patternEmail = Pattern.compile(regexEmail);
                System.out.println("Is the above Email valid? " + Email.matches(String.valueOf(patternEmail)));
                boolean EmailResult = Email.matches(String.valueOf(patternEmail));
                String regexPass = "((?=.*\\d)(?=.*[@#$%!]).{5,40})";
                Pattern patternPass = Pattern.compile(regexPass);
                System.out.println("Is the above Pass valid? " + Pass.matches(String.valueOf(patternPass)));
                boolean passResult = Pass.matches(String.valueOf(patternPass));

                if (name.equals("") || Pass.equals("") || Email.equals("")) {
                    error = "Please Fill All Inputs\n";
                    signActiontarget.setText(error);
                }
                if (nameResult == false) {

                    error += "Please Enter Valid Name\n";
                    signActiontarget.setText(error);
                }
                if (EmailResult == false) {
                    error += "Please Enter Valid Email\n";
                    signActiontarget.setText(error);
                }
                if (passResult == false) {
                    error += "Please Enter Valid Password\n";
                    signActiontarget.setText(error);
                } else { signActiontarget.setText("");
                    toServer.println("signup");
                    toServer.println(name);
                    toServer.println(Pass);
                    toServer.println(Email);

                    try {
                        mssg = fromServer.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (mssg.equals("signupDone")) {

                                System.out.println("Sign up Done");
                                signActiontarget.setFill(Color.YELLOW);
                                signActiontarget.setText("Signed up successfully, Please Login");

                            } else if (mssg.equals("signupFailed")) {

                                System.out.println("Sign up Failed Please try again");
                                signActiontarget.setFill(Color.YELLOW);
                                signActiontarget.setText("Sign up Failed Please try again");

                            }

                        }
                    });

                }
            }
        });

        Scene scene = new Scene(grid, 720, 700);
        scene.getStylesheets().add("sample/controlStyle1.css");
        grid.setStyle("-fx-background-image: url('/sample/loginbg.jpg')");
        new Controller().LoadLoginPage(primaryStage, grid, scene);
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
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
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
            SendDataToHomePage(currentUser);
            int playersNum = Integer.valueOf(fromServer.readLine());
            for (int i = 0; i < playersNum; i++) {
                playersList.add(receiveUserInfo());
            }
            SendAllInfoToGamePage(s, toServer, fromServer, playersList);
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SendDataToHomePage(User newUser) {
        new Controller().recieveData(newUser);
    }

    private void SendAllInfoToGamePage(Socket socket, PrintWriter toServer, BufferedReader fromServer, Vector<User> playerList) {
        new Controller().recieveSocket(socket, toServer, fromServer, playerList);
    }

    private void SendDataToOneVsOnePage(User newUser) {
        new OneVsOne().recieveData(newUser);
    }

    private void recievePlayersData() {
        try {
            int playersNum = Integer.valueOf(fromServer.readLine());
            for (int i = 0; i < playersNum; i++) {
                playersList.add(receiveUserInfo());
                printUserData(playersList.get(i));
            }
            SendAllInfoToGamePage(s, toServer, fromServer, playersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    class ClientListner extends Thread {

        boolean isServerOn;

        public ClientListner() {
            System.out.println("Thread created");
            isServerOn = true;
        }

        public boolean isNumeric(String strNum) {
            if (strNum == null) {
                return false;
            }
            try {
                double d = Double.parseDouble(strNum);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
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
                        String invitingPlayerScore = fromServer.readLine();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Invitation");
                                alert.setContentText("invitation from " + invitingPlayerUserName + " player");
                                sendInvitedUserDatatoOneVsOnePage(invitingPlayerUserName,invitingPlayerScore );
                                ButtonType accept = new ButtonType("Accept");
                                ButtonType reject = new ButtonType("Reject");
                                alert.getButtonTypes().setAll(accept, reject);
//                                alert.show();
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == accept) {
                                    toServer.println("accept");
                                    System.out.println("invitation from " + invitingPlayerUserName + "player accepted");
                                    new Controller().FXMLLoader("OneVsOne.fxml");
                                    new OneVsOne().recieveSocket(toServer, fromServer, "O");
                                } else {
                                    toServer.println("refused");
                                }
                            }

                            private void sendInvitedUserDatatoOneVsOnePage(String invitingPlayerUserName,String invitingPlayerScore ) {
                                new OneVsOne().recieveInvitedUserData(invitingPlayerUserName,invitingPlayerScore);
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });

                    } else if (mssg.equals("invitationAccepted")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new Controller().FXMLLoader("OneVsOne.fxml");
                                new OneVsOne().recieveSocket(toServer, fromServer, "X");
                            }
                        });

                    } else if (mssg.equals("updateStatus")) {
                        System.out.println(mssg);
                        String userName = fromServer.readLine();
                        String updateState = fromServer.readLine(); //login or logout
                        System.out.println("the user " + userName + " " + updateState);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Notifications.create()
                                        .title("Hey!")
                                        .text("A notification !! User " + userName + " has " + updateState).darkStyle()
                                        .showInformation();
                            }
                        });
                        playersList.clear();
                        String playersNum = fromServer.readLine();
                        System.out.println("number of users in list is " + playersNum);
                        for (int i = 0; i < Integer.valueOf(playersNum); i++) {
                            playersList.add(receiveUserInfo());
                        }
                        System.out.println("the list is ");
                        for (User players : playersList) {
                            System.out.println(players.userName + " state is " + players.state);
                        }

                        SendAllInfoToGamePage(s, toServer, fromServer, playersList);
                        controller = loader.getController();
                        controller.SetCurrentUserInfo(currentUser.userName, currentUser.score);
                    } else if (mssg.equals("resume")) {
                        String savedGrid = fromServer.readLine();
                        String lastMark = fromServer.readLine();
                        new OneVsOne().resumeMatch(savedGrid, lastMark);
                    } else if (mssg.equals("win")) {
                        System.out.println("YOU WON YA BASHAAA");
                        new OneVsOne().showResult("YOU WON YA BASHAAA");
                    } else if (mssg.equals("lose")) {
                        System.out.println("You lost ");
                        new OneVsOne().showResult("You lost ");

                    } else if (mssg.contains("chat")) {
                        System.out.println("recieved f tictactoe");
                        new OneVsOne().chatAppend(mssg);//the function that append to chat}

                    } else if (isNumeric(mssg)) {
                        System.out.println("movement received");
                        new OneVsOne().setMark(Integer.valueOf(mssg));
                    } else {
                        System.out.println("unknown operation");
                        System.out.println(mssg);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
