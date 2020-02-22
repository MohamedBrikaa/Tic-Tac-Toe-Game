package Controllers;

import Models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sun.plugin.javascript.navig.Anchor;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import static javafx.fxml.FXMLLoader.*;



public class HomeController  implements Initializable  {
    static Socket socket;
   static BufferedReader fromServer;
   static PrintWriter toServer;
    static Vector<User> playerList;
    static Stage primaryStage;
    static GridPane grid;
    static  Scene scene;
    static Stage stage;
   static User invitedUser;

    @FXML Label label;
@FXML Label myName;
@FXML Label myScore;
@FXML
 static   AnchorPane homeAnchor;
@FXML Button playWithComputer;
    private Callback tableViewRowFactory;

    @FXML


    public void playWithComputer(javafx.event.ActionEvent actionEvent) throws IOException {
homeAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("playWithComp.fxml")));
        System.out.println("play with computer");

    }

    public void inviteFriend(javafx.event.ActionEvent actionEvent) {
        System.out.println("Invite a friend");
      //  this.toServer.println("Invite");
        //FXMLLoader();
        System.out.println(socket);
       toServer.println("invite");
        toServer.println(invitedUser.userName);
    System.out.println(invitedUser.userName);

       // FXMLLoader("OneVsOne.fxml");
    }

    public void Loader(String fxmlName) throws IOException {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root1 = (Parent) fxmlLoader.load();
           stage = new Stage();
            stage.setTitle("Let's play");
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root1, 556, 630));
            stage.show();



    }



static String user;
    static Integer score;

 //   static String pass;
    public String recieveData(User myData)  {
       // myList.add(usr);
        System.out.println(myData.userName);
        user=myData.userName;
        score=myData.score;

        //pass=password;
        return user;
    }
@FXML TableView tableView;


    public void recieveSocket(Socket socket, PrintWriter toServer, BufferedReader fromServer, Vector<User> playerList) {
        this.toServer=toServer;
        this.socket=socket;
        this.fromServer=fromServer;
this.playerList=playerList;

        System.out.println(playerList.get(0).userName);
        System.out.println(playerList.get(1).userName);

    }




    public void back(MouseEvent mouseEvent) throws IOException {




    }

    public void LoadLoginPage(Stage primaryStage, GridPane grid ,Scene scene) {
        this.grid=grid;
        this.primaryStage=primaryStage;
        this.scene=scene;

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void SetCurrentUserInfo(String name, Integer score)
    {
        myName.setText(name);
        myScore.setText(String.valueOf(score));
        ObservableList<User> data = tableView.getItems();


           for (int i=0 ;i<playerList.size();i++) {
               data.add(new User(playerList.get(i).userID, playerList.get(i).userName, playerList.get(i).email, playerList.get(i).state, playerList.get(i).score));
           }


    }
    public void setData()
    {
        invitedUser= (User) tableView.getSelectionModel().getSelectedItem();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

    }

    public void show(MouseEvent mouseEvent) {
        invitedUser= (User) tableView.getSelectionModel().getSelectedItem();
       // System.out.println(invitedUser.userName);

    }
}

