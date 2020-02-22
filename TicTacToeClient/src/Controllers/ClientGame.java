package Controllers;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGame {
    static Socket socket;
    static DataInputStream fromServer;
    static PrintStream toServer;
    String msg;
    String mark;
    static int playerTurn =0;
    public ClientGame(Socket socket, DataInputStream fromServer, PrintStream toServer,String mark) {
        //if the current player plays with O begin with 1
        System.out.println("constructor of the "+mark+" has created");
        if(mark.equals("O")){
            playerTurn=1;
        }
        System.out.println(msg + " " + playerTurn);
        this.mark = mark;
    }

    public void setButton(String index){
        //set button in GUI
//        new OneVsOne().setC1();
//        try {
//            new OneVsOne().transferDataServerandClient("0");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
