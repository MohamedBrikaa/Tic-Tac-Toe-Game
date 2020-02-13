package sample;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TicTacToeClient {


    public TicTacToeClient() {

        Socket socket = null;
        try {
            socket = new Socket("localhost", 9001);
//            socket.
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connected!");

        // get the output stream from the socket.
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create a DataInputStream so we can read data from it.
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        String message = null;
        try {
            message = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The message sent from the server was: " + message);

        System.out.println("Closing socket and terminating program.");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}