/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
  @FXML Label name1,name2,score1,score2,whoseTurnLabel;
  @FXML TextArea chatArea;
  @FXML Button L1;
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
    static Stage primaryStage;
    static GridPane grid;
    static Scene scene;
    static Stage stage;
    static TextArea chatAREA;
    static Label whoseTURNLABEL;
    static Button[][] BOARD=new Button[3][3];;

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
        if (index == 0) {
            updateGUI(boards[0][0]);

        } else if (index == 1) {
            updateGUI(boards[1][0]);

        } else if (index == 2) {
            updateGUI(boards[2][0]);

        } else if (index == 3) {
            updateGUI(boards[0][1]);

        } else if (index == 4) {
            updateGUI(boards[1][1]);

        } else if (index == 5) {
            updateGUI(boards[2][1]);

        } else if (index == 6) {
            updateGUI(boards[0][2]);

        } else if (index == 7) {
            updateGUI(boards[1][2]);

        } else if (index == 8) {
            updateGUI(boards[2][2]);

        }

    }

    public void recieveSocket(PrintWriter toServer, BufferedReader fromServer, String myMark) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatAREA=chatArea;
        whoseTURNLABEL=whoseTurnLabel;
        BOARD=boards;
        boards[0][0] = L1;
        boards[1][0] = L2;
        boards[2][0] = L3;
        boards[0][1] = C1;
        boards[1][1] = C2;
        boards[2][1] = C3;
        boards[0][2] = R1;
        boards[1][2] = R2;
        boards[2][2] = R3;
        name1.setText(user);
        score1.setText(String.valueOf(score));
        name2.setText(invitedUserName);
        score2.setText(String.valueOf(invitedUserScore));
                
    }
    
   
    public void setChat(String mssg) {
        System.out.println(mssg);
    }

    public void pause(ActionEvent actionEvent) {
        System.out.println("pause");
        toServer.println("pause");
    }
    private void sendMove(Button btnPressed, int index) {
        if (!(btnPressed.getText().equals("O") || btnPressed.getText().equals("X")) && myTurn) {
            {
                myTurn = false;
                btnPressed.setText(myMark);
                btnPressed.disableProperty();
                toServer.println(index);
            }
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
    
    String recieveData(User myData)
    {       
        // myList.add(usr);
        System.out.println(myData.userName);
        user = myData.userName;
        score = myData.score;

        //pass=password;
        return user;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 static String invitedUserName;
 static Integer invitedUserScore;
 
    void recieveInvitedUserData(User invitedUser)
    {
        invitedUserName=invitedUser.userName;
        invitedUserScore=invitedUser.score;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    void resumeMatch(String gridFromServer, String playerTurn){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                BOARD[j][i].setText(Character.toString(gridFromServer.charAt(i+(3*j))));
            }
        }
        
        if(playerTurn.equals(myMark)){
            myTurn=true;
        }
        else myTurn=false;
    
    }
    public void showResult(String result){
        whoseTURNLABEL.setText(result);
       
    }

    void chatAppend(String mssg)
    {
        System.out.println("appending chat");
        System.out.println(chatAREA);
        chatAREA.appendText(mssg);
    }
    public void sendChat(ActionEvent actionEvent) {
        
        chatText= chatArea.getText();
        chatText="chat "+chatText;
        toServer.println(chatText);
        System.out.println("send chat to server" + chatText);
        
        
        
       
    }
}
