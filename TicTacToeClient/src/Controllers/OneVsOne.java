/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Basant Mahrous
 */
public class OneVsOne implements Initializable {

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
    static Stage primaryStage;
    static GridPane grid;
    static Scene scene;
    static Stage stage;
    static int movesNumber;
    static PrintWriter toServer = null;
    static boolean myTurn;
    static String myMark;
    static String opponentMark;
    static Button boards[][] = new Button[3][3];
    static boolean gameState=true;
    int cells = 3;
    static String gameEnd;

    public void setL1() {
        Button btnPressed = boards[0][0];
        sendMove(btnPressed, 0);
        gameEnd = ValidateWhoWin(0, 0, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setL2() {
        Button btnPressed = boards[1][0];
        sendMove(btnPressed, 1);
        gameEnd = ValidateWhoWin(1, 0, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setL3() {
        Button btnPressed = boards[2][0];
        sendMove(btnPressed, 2);
        gameEnd = ValidateWhoWin(2, 0, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setR1() {
        Button btnPressed = boards[0][2];
        sendMove(btnPressed, 6);
        gameEnd = ValidateWhoWin(0, 2, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setR2() {
        Button btnPressed = boards[1][2];
        sendMove(btnPressed, 7);
        gameEnd = ValidateWhoWin(1, 2, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setR3() {
        Button btnPressed = boards[2][2];
        sendMove(btnPressed, 8);
        gameEnd = ValidateWhoWin(2, 2, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setC1() {
        Button btnPressed = boards[0][1];
        sendMove(btnPressed, 3);
        gameEnd = ValidateWhoWin(0, 1, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setC2() {
        Button btnPressed = boards[1][1];
        sendMove(btnPressed, 4);
        gameEnd = ValidateWhoWin(1, 1, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setC3() {
        Button btnPressed = boards[2][1];
        sendMove(btnPressed, 5);
        gameEnd = ValidateWhoWin(2, 1, myMark);
        System.out.println("game State="+gameEnd);
    }

    public void setMark(int index) {
        myTurn = true;
        int row = index % 3;
        int column = index / 3;
        updateGUI(boards[row][column]);
          if(gameState==false)
                {
                    System.out.println("game state="+gameEnd);
                }

    }
      public void setMessageEndMatch(int index) {
        myTurn = false;
        int row = index % 3;
        int column = index / 3;
        updateGUIWinner(boards[row][column]);
          if(gameState==false)
                {
                    System.out.println("game state="+gameEnd);
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
        boards[0][0] = L1;
        boards[1][0] = L2;
        boards[2][0] = L3;
        boards[0][1] = C1;
        boards[1][1] = C2;
        boards[2][1] = C3;
        boards[0][2] = R1;
        boards[1][2] = R2;
        boards[2][2] = R3;
    }

    private void sendMove(Button btnPressed, int index) {
        if (!(btnPressed.getText().equals("O") || btnPressed.getText().equals("X")) && myTurn && gameState) {
            {
                myTurn = false;
                if(gameState==true)
                {
                btnPressed.setText(myMark);
                movesNumber++;
                toServer.println(index);
                toServer.println(gameEnd);
                }
            }
        }
    }

    private void updateGUI(Button updatedBtn) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updatedBtn.setText(opponentMark);
                  if(gameState==false)
                {
                    System.out.println("game state="+gameEnd);
                    updatedBtn.setText(gameEnd);
                }
              
            }
        });
    }

     private void updateGUIWinner(Button updatedBtn) {
     Platform.runLater(new Runnable() {
     @Override
     public void run() {
     if(gameEnd!=null)
     {
      updatedBtn.setText("win");
         gameEnd = ValidateWhoWin(1, 1, opponentMark);
         System.out.println(gameEnd);
     }
               
     }
     });
     }
 
    public String ValidateWhoWin(int row, int column, String mark) {

        //check end conditions:
        //check col
        for (int i = 0; i < cells; i++) {
            if (boards[row][i].getText() != mark) {
                break;
            }
            if (i == cells - 1) {
                System.out.println("The winner is" + mark +""+ row);
                gameState=false;
                return "The winner is" + mark;
            }
        }

        //check row
        for (int i = 0; i < cells; i++) {
            if (boards[i][column].getText() != mark) {
                break;
            }
            if (i == cells - 1) {
                System.out.println("The winner is" + mark+""+ column);
                gameState=false;
                return "The winner is" + mark;
            }
        }

        //check diagonal
        if (row == column) {
            //we're on a diagonal
            for (int i = 0; i < cells; i++) {
                if (boards[i][i].getText() != mark) {
                    break;
                }
                if (i == cells - 1) {
                    System.out.println("The winner is" + mark +""+i);
                     gameState=false;
                     return "The winner is" + mark;
                }
               
            }
        }

        //check anti diagonal (thanks rampion)
        if (row + column == cells - 1) {
            for (int i = 0; i < cells; i++) {
                if (boards[i][(cells - 1) - i].getText() != mark) {
                    break;
                }
                if (i == cells - 1) {
                    System.out.println("The winner is" + mark +""+i);
                    gameState=false;
                    return "The winner is" + mark;
                }
            }

        }

        //check draw //(Math.pow(cells, 2) - 1)
        if (movesNumber == (Math.pow(cells, 2) - 1)) {
            System.out.println("There's NO WINNER!");
            gameState=false;
            return "There's NO WINNER!";
        }
        return null;
    }
}
