/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.*;
import models.*;
import java.io.*;
import java.net.*;
import java.util.Vector;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Basant Mahrous
 */
public class GameServerGUI extends Application {

    private TableView table = new TableView();
    private Vector<User> vAllUsers = UserModel.returnAllPlayers();
    //vAllUsers.size();
    private Vector<Player> getALLUsersFromDB()
    {
        Vector<Player> allPlayer=new Vector<Player>();
     for(int index=0;index<vAllUsers.size();index++)
    {
      allPlayer.add(new Player(vAllUsers.get(index)));
    }
     return allPlayer;
    }
       private int i=0;
//     private final ObservableList<Player> data =
//        FXCollections.observableArrayList(
//       
//                //new Player(vAllUsers.get(i));
//           /* new Player("Isabella", "456", "isabella.johnson@example.com","5555","online"),
//            new Player("Ethan", "678", "ethan.williams@example.com","5555","online"),
//            new Player("Emma", "589", "emma.jones@example.com","5555","online"),
//            new Player("Michael", "915", "michael.brown@example.com","5555","online")*/
//        );
      private final ObservableList<Player> data =
        FXCollections.observableArrayList(getALLUsersFromDB());

    public class SwitchButtonC extends Label {

        private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);

        public SwitchButtonC() {
            Button switchBtn = new Button();
            switchBtn.setStyle(" -fx-border-radius: 15;");
            switchBtn.setStyle("-fx-background-color: blue;");
            switchBtn.setStyle("-fx-background-radius: 16.4, 15;");

            switchBtn.setPrefWidth(40);
            switchBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    switchedOn.set(!switchedOn.get());
                }
            });

            setGraphic(switchBtn);

            switchedOn.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov,
                        Boolean t, Boolean t1) {
                    if (t1) {
                        setText("ON");
                        setStyle("-fx-background-color: red;-fx-text-fill:white;");
                        setContentDisplay(ContentDisplay.RIGHT);

                    } else {
                        setText("OFF");
                        setStyle("-fx-background-color: gray;-fx-text-fill:black;");
                        setContentDisplay(ContentDisplay.LEFT);

                    }
                }
            });
            switchedOn.set(false);
        }

        public SimpleBooleanProperty switchOnProperty() {
            return switchedOn;
        }
    }
   
    @Override
    public void start(Stage primaryStage) {
        final Label label = new Label("UserName of Players");
        label.setFont(new Font("Arial", 20));
        table.setEditable(true);
        TableColumn userIdCol = new TableColumn("User ID");
        userIdCol.setMinWidth(25);
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("userID"));
        TableColumn userNameCol = new TableColumn("User Name");
        userNameCol.setMinWidth(25);
        userNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("userName"));
        TableColumn passCol = new TableColumn("Password");
        passCol.setMinWidth(20);
        passCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password"));
        TableColumn emailCol = new TableColumn("email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email"));
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(50);
        scoreCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("score"));
        TableColumn statusCol = new TableColumn("Status");
        scoreCol.setMinWidth(20);
        statusCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("state"));
        table.setItems(data);
        table.getColumns().addAll(userIdCol, userNameCol, passCol, emailCol, scoreCol, statusCol);
        // SwitchButtonC switchBtn = new SwitchButtonC();
        ToggleButton onBtn = new ToggleButton("ON");
        ToggleButton offBtn = new ToggleButton("OFF");
        ToggleGroup toggleGroup = new ToggleGroup();
        onBtn.setToggleGroup(toggleGroup);
        offBtn.setToggleGroup(toggleGroup);
        onBtn.setSelected(true);
        FlowPane fPane = new FlowPane(onBtn, offBtn);
        BorderPane root = new BorderPane(table);
        //root.getChildren().add(fPane);
        BorderPane.setAlignment(fPane, Pos.BOTTOM_CENTER);
        root.setBottom(fPane);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.setTitle("Game Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread socketServerThread = new Thread(new GameServerXO());
        socketServerThread.setDaemon(true); //terminate the thread when program end
        socketServerThread.start();
        offBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                socketServerThread.stop();
                System.out.println("close Server");
            }
        });
        onBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                socketServerThread.resume();
                System.out.println("resume thread server");
            }
        });
    }

    public static void main(String[] args) {
        System.out.println("run main");
        launch(args);
    }

    private class GameServerXO extends Thread {

        ServerSocket serverSocket;
        static final int SocketServerPORT = 20080;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                System.out.println("start accepting clients");
                while (true) {
                    System.out.println("waiting");
                    Socket s = serverSocket.accept();
                    System.out.println("new player connected");
                    new ClientHandler(s);
//                    Thread acceptedThread = new Thread(new ClientHandler(s));
//                    acceptedThread.setDaemon(true); //terminate the thread when program end
//                    acceptedThread.start();
                }
            } catch (IOException ex) {

            }
        }

        public GameServerXO() {

        }

        public void closeServer() {
            try {
                serverSocket.close();
                System.out.println("server is closed");
                ClientHandler.closeAllInternalSockets();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static class Player {

        private final SimpleStringProperty userID;
        private final SimpleStringProperty userName;
        private final SimpleStringProperty password;
        private final SimpleStringProperty email;
        private final SimpleStringProperty state;
        private final SimpleStringProperty score;

        private Player(User userobj) {
            this.userID= new SimpleStringProperty( Integer.toString(userobj.userID) );
            this.userName = new SimpleStringProperty(userobj.userName);
            this.state = new SimpleStringProperty(userobj.state);
            this.password = new SimpleStringProperty(userobj.password);
            this.email = new SimpleStringProperty(userobj.email);
            this.score = new SimpleStringProperty(Integer.toString(userobj.score));
        }

        private Player(String userID,String userName, String password, String email, String score, String state) {
            this.userID = new SimpleStringProperty(userID);
            this.userName = new SimpleStringProperty(userName);
            this.state = new SimpleStringProperty(state);
            this.password = new SimpleStringProperty(password);
            this.email = new SimpleStringProperty(email);
            this.score = new SimpleStringProperty(score);
        }
         public void setUserID(String userId) {
            userID.set(userId);
        }

        public String getUserID() {
            return userID.get();
        }


        public void setScore(String scores) {
            score.set(scores);
        }

        public String getScore() {
            return password.get();
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String emails) {
            email.set(emails);
        }

        public String getUserName() {
            return userName.get();
        }

        public void setUserName(String uName) {
            userName.set(uName);
        }

        public String getState() {
            return state.get();
        }

        public void setState(String states) {
            state.set(states);
        }

        public void setPassword(String passwords) {
            password.set(passwords);
        }

        public String getPassword() {
            return password.get();
        }

    }
}