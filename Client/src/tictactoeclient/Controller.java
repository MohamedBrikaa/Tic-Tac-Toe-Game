/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author sara
 */
public class Controller
{

    DataOutputStream dout;
    private Socket s;
    DataInputStream dis;
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    String serverMessage = "";

    public Controller(Socket s, String User, String Pass) throws IOException
    {

        fromServer = new DataInputStream(s.getInputStream());

  // Create an output stream to send data to the server
        //toServer = new DataOutputStream( s.getOutputStream() );
        dout = new DataOutputStream(s.getOutputStream());
        onSetupGameClick(s, User, Pass);

    }

    Controller()
    {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void onSetupGameClick(Socket s, String User, String Pass) throws IOException
    {

        dout.writeUTF("login");
             //ServerHandler(s);
        //serverMessage = fromServer.readUTF();
        dout.writeUTF(User);
        dout.writeUTF(Pass);
        dout.flush();

//        dis.close();
        dout.close();
        s.close();

    }

}
