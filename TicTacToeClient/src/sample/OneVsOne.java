package sample;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static sample.Controller.invitedUser;
import static sample.Controller.user;

/**
 *
 * @author Basant Mahrous
 */
public class OneVsOne implements Initializable {

    static PrintWriter toServer = null;

    static boolean myTurn;
    static String myMark;
    static String opponentMark;
    static Button boards[][] = new Button[3][3];
    static String chatText;
    @FXML
    Label name1, name2, score1, score2, whoseTurnLabel;
    @FXML
    TextArea chatArea;
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
    @FXML
    TextField chatTextField;
    static Stage primaryStage;
    static GridPane grid;
    static Scene scene;
    static Stage stage;
    static TextArea chatAREA;
    static Label whoseTURNLABEL;
    static boolean gameover = false;

    public void setL1() {
        Button btnPressed = boards[0][0];
        sendMove(btnPressed, 0);

    }

    public void setL2() {
        Button btnPressed = boards[1][0];
        sendMove(btnPressed, 1);

    }

    public void setL3() {
        Button btnPressed = boards[2][0];
        sendMove(btnPressed, 2);

    }

    public void setR1() {
        Button btnPressed = boards[0][2];
        sendMove(btnPressed, 6);

    }

    public void setR2() {
        Button btnPressed = boards[1][2];
        sendMove(btnPressed, 7);
    }

    public void setR3() {
        Button btnPressed = boards[2][2];
        sendMove(btnPressed, 8);
    }

    public void setC1() {
        Button btnPressed = boards[0][1];
        sendMove(btnPressed, 3);

    }

    public void setC2() {
        Button btnPressed = boards[1][1];
        sendMove(btnPressed, 4);
    }

    public void setC3() {
        Button btnPressed = boards[2][1];
        sendMove(btnPressed, 5);

    }

    public void setMark(int index) {
        myTurn = true;

        int i = index % 3;
        int j = index / 3;

        updateGUI(boards[i][j]);

         setTurnLabel();
    }

    public void closeGame() {
        gameover = true;
        System.out.println("game is paused from close");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                whoseTURNLABEL.setText("Game is paused");

            }
        });
    }

    public void recieveSocket(PrintWriter toServer, BufferedReader fromServer, String myMark) {
        System.out.println("recieve socket " + myMark);
        this.toServer = toServer;
        this.myMark = myMark;
        if (myMark.equals("X")) {
            myTurn = true;
            this.opponentMark = "O";
        } else {
            myTurn = false;
            this.opponentMark = "X";
        }

    }

    public void solve() {
        System.out.println("from solve " + boards[0][0]);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatAREA = chatArea;
        whoseTURNLABEL = whoseTurnLabel;
        gameover = false;
        boards[0][0] = L1;
        boards[1][0] = L2;
        boards[2][0] = L3;
        boards[0][1] = C1;
        boards[1][1] = C2;
        boards[2][1] = C3;
        boards[0][2] = R1;
        boards[1][2] = R2;
        boards[2][2] = R3;
        name1.setText(new Controller().user);
        score1.setText(String.valueOf(new Controller().score));
        name2.setText(invitedUserName);
        score2.setText(String.valueOf(invitedUserScore));
        setTurnLabel();

    }

    public void setChat(String mssg) {
        System.out.println(mssg);
    }

    public void pause(ActionEvent actionEvent) {
        System.out.println("pause from pause");
        gameover = true;
        toServer.println("pause");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                whoseTURNLABEL.setText("Game is paused");

            }
        });

    }

    private void sendMove(Button btnPressed, int index) {

        if (!(btnPressed.getText().equals("O") || btnPressed.getText().equals("X")) && myTurn && !gameover) {
            {
                myTurn = false;
                btnPressed.setText(myMark);
                btnPressed.disableProperty();
                toServer.println(index);

            }
            setTurnLabel();
        }
    }

    private void updateGUI(Button updatedBtn) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updatedBtn.setText(opponentMark);
                updatedBtn.disableProperty();

            }
        });
    }
    static String user;
    static Integer score;

    void setTurnLabel() {
        String text = "Your Turn";
        if (myTurn == false) {
            text = "Not your turn";
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (myTurn == true) {
                    whoseTURNLABEL.setText("Your Turn");
                } else {
                    whoseTURNLABEL.setText("Not your Turn");
                }

            }
        });

    }

    String recieveData(User myData) {
        // myList.add(usr);
        System.out.println(myData.userName);
        user = myData.userName;
        score = myData.score;

        //pass=password;
        return user;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    static String invitedUserName;
    static String  invitedUserScore;

    void recieveInvitedUserData(User invitedUser) {
        invitedUserName = invitedUser.userName;
        invitedUserScore = String.valueOf(invitedUser.score); 
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    void recieveInvitedUserData(String invitedUser، String invitedScore) {
        invitedUserName = invitedUser;
       invitedUserScore=invitedScore;
//
//
//
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }
//    

    void resumeMatch(String gridFromServer, String playerTurn) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                setBtn(boards[j][i], Character.toString(gridFromServer.charAt(i + (3 * j))));
            }
        }

        if (playerTurn.equals(myMark)) {
            myTurn = true;
        } else {
            myTurn = false;
        }
        setTurnLabel();

    }

    public void setBtn(Button btn, String mark) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if ("-".equals(mark)) {
                    btn.setText("");
                } else {
                    btn.setText(mark);
                }
            }
        });
    }

    public void showResult(String result) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                whoseTURNLABEL.setText(result);

            }
        });

        gameover = true;
    }

    void chatAppend(String mssg) {
        mssg = mssg.substring(4);
        mssg += '\n';
        System.out.println("appending chat");
        System.out.println(chatAREA);
        chatAREA.appendText(mssg);
    }

    public void sendChat(ActionEvent actionEvent) {

        chatText = chatTextField.getText();
        chatText = "chat " + chatText;
        toServer.println(chatText);
        chatTextField.setText("");
        System.out.println("send chat to server" + chatText);

    }

}
