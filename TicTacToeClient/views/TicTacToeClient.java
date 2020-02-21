package sample;

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

public class TicTacToeClient extends Application {

    private ServerSocket server = null;
    Socket s;
    String serverMessage = "";
    PrintWriter toServer = null;
    BufferedReader fromServer;
    String mssg;
    static Vector<User> playersList = new Vector<>();
    User currentUser;
    boolean loggedIn = false;
    boolean isServerOn = false;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

    Controller controller = loader.getController();

    public void init() {

        try {
            this.s = new Socket("127.0.0.1", 20080);
            this.toServer = new PrintWriter(s.getOutputStream(), true);
            this.fromServer = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        Button Signupbtn = new Button("Sign up");
        HBox HsignUpBtn = new HBox(10);
        HsignUpBtn.setAlignment(Pos.BOTTOM_RIGHT);
        HsignUpBtn.getChildren().add(Signupbtn);
        grid.add(HsignUpBtn, 1, 9);

        Label SignUp_name = new Label("User Name:");
        grid.add(SignUp_name, 0, 6);

        TextField SignUp_nameTextField = new TextField();
        grid.add(SignUp_nameTextField, 1, 6);

        Label SignUp_enterPw = new Label("Enter Password:");
        grid.add(SignUp_enterPw, 0, 7);

        PasswordField SignUp_enterPwBox = new PasswordField();
        grid.add(SignUp_enterPwBox, 1, 7);

        Label Email = new Label("Email:");
        grid.add(Email, 0, 8);

        TextField EmailTextField = new TextField();
        grid.add(EmailTextField, 1, 8);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 10);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String user = userTextField.getText();
                String Password = pwBox.getText();

                User usr = new User();
                toServer.println("login");
                toServer.println(user);
                toServer.println(Password);
                try {
                    mssg = fromServer.readLine();
                    if (mssg.equals("loginDone")) {
                        handleLoginOpeartionWithServer();
                        primaryStage.setScene(new Scene(root, 556, 630));
                        Controller controller = loader.getController();
                        controller.SetCurrentUserInfo(currentUser.userName, currentUser.score);
                        ClientListner listner = new ClientListner();
                        listner.start();
                        primaryStage.show();
                    } else if (mssg.equals("loginFailed")) {
                        System.out.println("login Failed Please try again");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        final Text signUpActiontarget = new Text();
        grid.add(signUpActiontarget, 1, 11);

        Signupbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                String name = SignUp_nameTextField.getText();
                String Pass = SignUp_enterPwBox.getText();
                String Email = EmailTextField.getText();
                System.out.println(name);
                System.out.println(Pass);
                System.out.println(Email);
                toServer.println("signup");
                toServer.println(name);
                toServer.println(Pass);
                toServer.println(Email);

                try {
                    mssg = fromServer.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (mssg.equals("signupDone")) {

                    System.out.println("Sign up Done");
                    signUpActiontarget.setFill(Color.FIREBRICK);
                    signUpActiontarget.setText("Signed up successfully, Please Login");

                } else if (mssg.equals("signupFailed")) {

                    System.out.println("Sign up Failed Please try again");
                    signUpActiontarget.setFill(Color.FIREBRICK);
                    signUpActiontarget.setText("Sign up Failed Please try again");

                }

            }
        });

        Scene scene = new Scene(grid, 556, 630);
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
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Invitation");
                                alert.setContentText("invitation from " + invitingPlayerUserName + " player");
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
