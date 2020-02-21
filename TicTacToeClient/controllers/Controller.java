package sample;

import java.io.BufferedReader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.nio.Buffer;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

import static sample.TicTacToeClient.playersList;

public class Controller implements Initializable {

    static Socket socket;
    static BufferedReader fromServer;
    static PrintWriter toServer;
    static Vector<User> playerList;
    static Stage primaryStage;
    static GridPane grid;
    static Scene scene;
    static Stage stage;
    static User invitedUser;
    @FXML
    Label label;
    @FXML
    Label myName;
    @FXML
    Label myScore;
    @FXML
    Button playWithComputer;
    private Callback tableViewRowFactory;

    @FXML

    public void playWithComputer(javafx.event.ActionEvent actionEvent) {

        FXMLLoader("playWithComp.fxml");
        System.out.println("play with computer");

    }

    public void inviteFriend(javafx.event.ActionEvent actionEvent) {
        System.out.println("Invite a friend");
        System.out.println(socket);
        toServer.println("invite");
        toServer.println(invitedUser.userName);
        System.out.println(invitedUser.userName);
        String mssg;
//        try {
        System.out.println("waiting for invitation replay");
//            mssg = fromServer.readLine();
//            System.out.println(mssg);
        System.out.println("invitation Response");
       
//            if (mssg.equals("invitationAccepted")) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        new Controller().FXMLLoader("OneVsOne.fxml");
//                        new OneVsOne().recieveSocket(toServer, fromServer, "X");
//                    }
//                });
//            } else {
//                System.out.println("client refused invitation");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
        // FXMLLoader("OneVsOne.fxml");
    }

    public void FXMLLoader(String fxmlName) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Let's play");
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root1, 556, 630));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static String user;
    static Integer score;

    //   static String pass;
    public String recieveData(User myData) {
        // myList.add(usr);
        System.out.println(myData.userName);
        user = myData.userName;
        score = myData.score;

        //pass=password;
        return user;
    }
    @FXML
    TableView tableView;

    public void recieveSocket(Socket socket, PrintWriter toServer, BufferedReader fromServer, Vector<User> playerList) {
        this.toServer = toServer;
        this.socket = socket;
        this.fromServer = fromServer;
        this.playerList = playerList;

        System.out.println(playerList.get(0).userName);
        System.out.println(playerList.get(1).userName);

    }

    public void back(MouseEvent mouseEvent) {

        LoadLoginPage(primaryStage, grid, scene);

    }

    public void LoadLoginPage(Stage primaryStage, GridPane grid, Scene scene) {
        this.grid = grid;
        this.primaryStage = primaryStage;
        this.scene = scene;

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void SetCurrentUserInfo(String name, Integer score) {
        myName.setText(name);
        myScore.setText(String.valueOf(score));
        ObservableList<User> data = tableView.getItems();

        for (int i = 0; i < playerList.size(); i++) {
            data.add(new User(playerList.get(i).userID, playerList.get(i).userName, playerList.get(i).email, playerList.get(i).state, playerList.get(i).score));
        }

    }

    public void setData() {
        invitedUser = (User) tableView.getSelectionModel().getSelectedItem();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void show(MouseEvent mouseEvent) {
        invitedUser = (User) tableView.getSelectionModel().getSelectedItem();
        // System.out.println(invitedUser.userName);

    }
}
