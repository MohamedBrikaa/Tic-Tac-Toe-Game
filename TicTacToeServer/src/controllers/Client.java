/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;


/**
 *
 * @author Basant Mahrous
 */
public class Client extends Thread {

    private int position;
    private boolean myTurn;
    private String mark;
    private DataInputStream dis;
    private PrintStream ps;
    private  String msg;
    private static final String ipAddress="192.168.122.1";
    private int socketPort=5005;
    private final static String win = "win";
    private final static String lose = "lose";
    int row, column;
    private final static String turn = "It is your turn";
    private final static String waitForOpponentPlayer = "waiting for second player";
    private final static String waitForOpponentPlayerToPlay = "waiting for second player to play";
    private final static boolean youWin = false;
    private final static boolean opponentWin = false;
    private String board[][] = {};
    private Socket socket;

    public Client() {
        try {
            socket = new Socket(ipAddress,socketPort);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            while (msg != "X" || msg != "O") {

                try {
                    msg = dis.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        mark = msg;
    }

    public void calculatePosition(int position) {
        row = Integer.parseInt(msg) % 3;
        column = Integer.parseInt(msg) / 3;
        if(board[row][column]=="")
        {
           board[row][column] = mark;
        }
    }

    public void getPosition(int position) {
        // if()
        ps.println(String.valueOf(position));
    }

    public boolean listenToGetMyTurn() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (msg == turn) {

                    try {
                        msg = dis.readLine();

                        Runnable updater = new Runnable() {
                            @Override
                            public void run() {

                            }
                        };
                        Platform.runLater(updater);
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        myTurn = true;
        return myTurn;
    }

    private String playMyTurn(int position) {
        return Integer.toString(position);
    }

    @Override
    public void run() {
        while (true) {
            try {

                msg = dis.readLine();
                if (msg == turn) {
                    ps.println(String.valueOf(position));
                    msg = dis.readLine();
                }
                if (Integer.parseInt(msg) >= 0 && Integer.parseInt(msg) < 9) {
                    row = Integer.parseInt(msg) % 3;
                    column = Integer.parseInt(msg) / 3;
                    board[row][column] = mark;
                }

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
public void main (String []args)

{
 new Client();
}
}
