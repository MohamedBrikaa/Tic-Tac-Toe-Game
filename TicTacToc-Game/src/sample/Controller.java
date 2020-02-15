package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class Controller {

    @FXML

    public void playWithComputer(javafx.event.ActionEvent actionEvent) {

        FXMLLoader("playWithComp.fxml");


        System.out.println("play with computer");

    }

    public void inviteFriend(javafx.event.ActionEvent actionEvent) {
        System.out.println("Invite a friend");
        //FXMLLoader();
    }

    public void FXMLLoader(String fxmlName) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Let's play");

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root1, 556, 630));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
