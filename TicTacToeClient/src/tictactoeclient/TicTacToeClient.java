package signinanduppage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Isaac Wahba
 */
public class TicTacToeClient extends Application
{

    private ServerSocket server = null;
    Socket s;

    String serverMessage = "";
    PrintStream toServer = null;
    DataInputStream fromServer;

    public void init()
    {
        try {
            this.s = new Socket("10.145.3.103", 20080);
            this.toServer = new PrintStream(s.getOutputStream());
            this.fromServer = new DataInputStream(s.getInputStream());
//            toServer.print("login");
//            toServer.print("unam");
//            toServer.print("pass");
//            String stat=fromServer.readLine();
            //           System.out.println(stat);
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true) {
                    try {
                        System.out.println("waiting for connection");
                        String value;
                        value = fromServer.readLine();
                        System.out.println("recived");
                        System.out.println(value);
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle("Login Here!");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");

        Button Signupbtn = new Button("Sign up");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(Signupbtn);
        HBox HSignupBtn = new HBox(10);
        grid.add(HSignupBtn, 1, 4);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        Scene scene = new Scene(grid, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @FXML

            @Override
            public void handle(ActionEvent e)
            {

                System.out.println("Login");
                String User = userTextField.getText();
                String Pass = pwBox.getText();
                System.out.println(userTextField.getText());
                System.out.println(pwBox.getText());
                LoadHomePage(primaryStage);
                toServer.println("login");
                toServer.println(User);
                toServer.println(Pass);
                primaryStage.show();
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
            }
        });

//        new Thread(() -> {
//            try {
//                while (true) {
////                    System.out.println("Client accepted: " + s);
//                  // serverMessage = fromServer.readUTF();
//                    String value = fromServer.readLine();
//                    System.out.println(value );
//                    System.out.println("message received");
//
////                   Platform.runLater(() -> {
////                        userTextField.requestFocus();
////                        // pwBox.requestFocus();
////                        //userTextField.appendText(serverMessage);
////
////                    });
//                }
//            } catch (IOException e) {
//                //.appendText(e.toString() + "\n");
//            }
//        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public void LoadHomePage(Stage primaryStage)
    {

        GridPane Grid = new GridPane();

        Grid.setAlignment(Pos.CENTER);
        Grid.setHgap(10);
        Grid.setVgap(10);
        Grid.setPadding(new Insets(25, 25, 25, 25));
        Button PlayCompBtn = new Button("Play with Computer");
        Text Scenetitle = new Text("Play Here!");
        Scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Grid.add(Scenetitle, 0, 0, 2, 1);
        Grid.add(PlayCompBtn, 1, 4);
        Scene RegisterationScene = new Scene(Grid, 500, 600);
        primaryStage.setScene(RegisterationScene);

    }

}