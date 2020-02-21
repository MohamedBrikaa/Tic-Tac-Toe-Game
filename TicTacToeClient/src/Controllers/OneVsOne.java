package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.application.Platform;

public class OneVsOne implements Initializable {
    @FXML
    AnchorPane oneVsOneAnchor;
    @FXML AnchorPane turnAnchorPane;
    static Socket socket = null;
    static BufferedReader fromServer = null;
    static String position;
    static String name;
    private boolean myTurn;
    static String myMark;
    static String opponentMark;
    public String msg;
    private final static String win = "win";
    private final static String lose = "lose";
    int row, column;
    public final static String turn = "It is your turn";
    private final static String waitForOpponentPlayer = "waiting for second player";
    private final static String waitForOpponentPlayerToPlay = "waiting for second player to play";
    private final static boolean youWin = false;
    private final static boolean opponentWin = false;
    static Button boards[][] = new Button[3][3];
    static String chatText;
    static PrintWriter toServer = null;
    // public DataInputStream dis;
    // public PrintStream ps;
    @FXML

    Button L1;
    @FXML
    Button L2;
    @FXML
    Button L3;
    @FXML
    Button C1;
    @FXML
    Button C2;
    @FXML
    Button C3;
    @FXML
    Button R1;
    @FXML
    Button R2;
    @FXML
    Button R3;
    @FXML Label name1;

    @FXML Label name2;
    @FXML Label score1;
    @FXML Label score2;
    @FXML
    TextArea chatArea;


    public void setL1() {
        System.out.println(this.myMark + " pressed");
        Button btnPressed = boards[0][0];

    }

    public void setL2() {
        Button btnPressed = boards[1][0];

        }
;

    public void setL3() {

                chatArea.setText(myMark);
                toServer.println("2");

    }

    public void setR1() {


                chatArea.setText(myMark);


    }

    public void setR2() {
        Button btnPressed = boards[1][1];
                btnPressed.setText(myMark);
                chatArea.setText(myMark);

    }

    public void setR3() {
        Button btnPressed = boards[2][1];

                chatArea.setText(myMark);


    }

    public void setC1() {
        Button btnPressed = boards[2][0];

                chatArea.setText(myMark);


    }

    public void setC2() {
        Button btnPressed = boards[2][1];

                chatArea.setText(myMark);

    }

    public void setC3() {
                chatArea.setText(myMark);

    }


    public void recieveSocket(Socket socket, PrintWriter toServer, BufferedReader fromServer, String myMark, String name) {
        System.out.println(socket);
        this.toServer = toServer;
        this.socket = socket;
        this.fromServer = fromServer;
        this.myMark = myMark;
        this.name=name;
        if (myMark.equals("X")) {
            this.opponentMark = "O";
        } else {
            this.opponentMark = "X";
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boards[0][0] = L1;
        boards[1][0] = L2;
        boards[2][0] = L3;
        boards[0][1] = C1;
        boards[1][1] = C2;
        boards[2][1] = C3;
        boards[0][2] = R1;
        boards[1][2] = R2;
        boards[2][2] = R3;
        name1.setText(String.valueOf(new HomeController().user));
        score1.setText(String.valueOf(new HomeController().myScore));
        name2.setText(new HomeController().invitedUser.userName);
        score2.setText(String.valueOf(new HomeController().invitedUser.score));

    }


    public void sendChat(ActionEvent actionEvent) {
        chatText= chatArea.getText();
        System.out.println(chatText);
    }

    public void setInfo(String userName, int score) {
    }
}
