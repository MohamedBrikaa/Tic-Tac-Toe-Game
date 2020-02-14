package controllers;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

public class ClientHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    Socket s;
    User user;
    static Vector<User> playersList = new Vector<>();
    static Vector<String> onlinePlayersUNames = new Vector<>();
    static Vector<ClientHandler> onlinePlayers = new Vector<>();

    public ClientHandler(Socket s) {
        user = new User();
        try {
            this.s = s;
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            System.out.println("new socket for player connected and new handler initated");
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String mssg;
                System.out.println("waiting for new message");
//                System.out.println();
                mssg = dis.readLine();
                System.out.println(mssg);
                if (mssg.equals("signup")) {
                    System.out.println("request signup");
                    ps.println(signUP());
                } else if (mssg.equals("login")) {
                    System.out.println("request login");
                    signIn();
                } else if (mssg.equals("invite")) {
                    //send user invitation
                    String invitedPlayerUserName = dis.readLine();
                    invitePlayer(invitedPlayerUserName);
                } else if (mssg.equals("playalone")) {
                    //cretat a thread to handle the game with the AI
//                    new GameMatch(this.s,this.user.userName);
//                    closePlayerConnection(this, "PLAYING");
                } else if (mssg.equals("logout")) {
                    System.out.println("request logout");
                    closePlayerConnection(this, "OFFLINE");
                } else {
                    System.out.println("unknown operation");
                    System.out.println(mssg);
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void signIn() {
        String userName = "", password = "";
        try {
            userName = dis.readLine();
            password = dis.readLine();
            //1 check user validation
            if (UserModel.validatePlayer(userName, password)) {
                UserModel.updatePlayerState(userName, "ONLINE");
                user = getUserInfo(userName);
                onlinePlayers.add(this);
                onlinePlayersUNames.add(userName);
                ps.println("1");
                sendUserInfo(user);
                //refresh players list and send the player list to the player
                refreshPlayersList();
                for (int i = 0; i < playersList.size(); i++) {
                    if (playersList.get(i).userID != user.userID) {
                        sendUserInfo(playersList.get(i));
                    }
                }
            } else {
                ps.println("0");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean signUP() {
        try {
            user.userName = dis.readLine();
            user.password = dis.readLine();
            user.email = dis.readLine();
            user.score = 0;
            user.state = "OFFLINE";
            refreshPlayersList();
            //send updates to clients and server
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UserModel.addPlayer(user);
    }

    public void invitePlayer(String userName) {
        Socket invitedPlayerSocket;
        int invitedPlayerIndex = onlinePlayersUNames.indexOf(userName);
        onlinePlayers.get(invitedPlayerIndex).ps.println("invitation");
        onlinePlayers.get(invitedPlayerIndex).ps.println(user.userName);
        try {
            if (onlinePlayers.get(invitedPlayerIndex).dis.readLine().equals("accept")) {
                invitedPlayerSocket = onlinePlayers.get(invitedPlayerIndex).s;
//              new GameMatch(this.s,invitedPlayerSocket,this.user.userName,userName);
                closePlayerConnection(this, "PLAYING");
                closePlayerConnection(onlinePlayers.get(invitedPlayerIndex), "PLAYING");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private User getUserInfo(String userName) {
        return UserModel.playerInfo(userName);
    }
    
    public void sendUserInfo(User user) {
        ps.println(user.userID);
        ps.println(user.userName);
        ps.println(user.email);
        ps.println(user.state);
    }

    public void refreshPlayersList() {
        playersList = UserModel.returnAllPlayers();
    }

    public static void closeAllInternalSockets() {
        for (ClientHandler player : onlinePlayers) {
            player.ps.println("serveroff");
            closePlayerConnection(player, "OFFLINE");
            try {
                player.s.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void closePlayerConnection(ClientHandler handler, String state) {
        try {
            handler.dis.close();
            handler.ps.close();
            handler.stop();
            UserModel.updatePlayerState(handler.user.userName, state);
            onlinePlayers.remove(handler);
            onlinePlayersUNames.remove(handler.user.userName);
            playersList.remove(handler);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
