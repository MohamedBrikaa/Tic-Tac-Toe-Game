package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.ResourceBundle;

public class GameController implements Initializable {
@FXML
    AnchorPane playWithCompAnchor;
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


    Button[][] boards;


    public void setL1() {
        playerPlay(boards[0][0]);
    }

    public void setL2() {
        playerPlay(boards[0][1]);

    }

    public void setL3() {
        playerPlay(boards[0][2]);

    }

    public void setC1() {
        playerPlay(boards[1][0]);
    }

    public void setC2() {
        playerPlay(boards[1][1]);


    }

    public void setC3() {
        playerPlay(boards[1][2]);

    }

    public void setR1() {
        playerPlay(boards[2][0]);
    }

    public void setR2() {
        playerPlay(boards[2][1]);
    }

    public void setR3() {
        playerPlay(boards[2][2]);

    }


    public void playerPlay(Button btnPressed) {
        if (!(btnPressed.getText().equals("O") || btnPressed.getText().equals("X"))) {
            {
                btnPressed.setText("X");

                aiPlay();
            }
        }
    }

    public void aiPlay() {
        boolean aiTurn = true;

        for (int i = 0; i < 3 && aiTurn; i++) {
            for (int j = 0; j < 3 && aiTurn; j++) {
                if (!(boards[i][j].getText().equals("O") || boards[i][j].getText().equals("X"))) {
                    boards[i][j].setText("O");
                    aiTurn = false;
                }
            }
        }

    }

    public void back(MouseEvent mouseEvent) throws IOException {
      playWithCompAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("sample.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boards = new Button[3][3];
        boards[0][0] = L1;
        boards[0][1] = L2;
        boards[0][2] = L3;
        boards[1][0] = C1;
        boards[1][1] = C2;
        boards[1][2] = C3;
        boards[2][0] = R1;
        boards[2][1] = R2;
        boards[2][2] = R3;
    }


    public void chooseEasyLevel(ActionEvent actionEvent) {
        System.out.println("You choose Easy");
    }

    public void chooseHardLevel(ActionEvent actionEvent) {
        System.out.println("You choose Hard");
    }
}
