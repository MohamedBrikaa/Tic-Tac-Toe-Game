package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tec-Tac-Toe Game");
        primaryStage.setScene(new Scene(root, 556, 630));
       // new TicTacToeClient();
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);

            // need host and port, we want to connect to the ServerSocket at port 7777

        }


    }

