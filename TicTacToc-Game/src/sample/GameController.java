package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class GameController  {


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

    public void setL1() {
L1.setText("X");



    }
    public void setL2() {
        L2.setText("X");


    }
    public void setL3() {
        L3.setText("X");


    }
    public void setC1(javafx.event.ActionEvent actionEvent) {
        C1.setText("X");

    }
    public void setC2(javafx.event.ActionEvent actionEvent) {
        C2.setText("X");

    }
    public void setC3(javafx.event.ActionEvent actionEvent) {
        C3.setText("X");

    }
    public void setR1(javafx.event.ActionEvent actionEvent) {
        R1.setText("X");

    }    public void setR2(javafx.event.ActionEvent actionEvent) {
        R2.setText("X");

    }    public void setR3(javafx.event.ActionEvent actionEvent) {
        R3.setText("X");

    }


}
