package controllers;

import com.mysql.jdbc.Buffer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import models.*;
import views.*;

public class ClientHandler extends Thread {

    BufferedReader dis;
    PrintWriter ps;
    Socket s;
    User user;
    static Vector<User> playersList = new Vector<>();
    static Vector<String> onlinePlayersUNames = new Vector<>();
    static Vector<ClientHandler> onlinePlayers = new Vector<>();
    GameMatch gameMatch = null;
    int gameIndex;
    static Vector<GameMatch> gameMatches = new Vector<>();
    int invitationIndex;
    static Vector<String> invitationStatus = new Vector<>();

    public ClientHandler(Socket s) {
        user = new User();
        try {
            this.s = s;
            dis = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
            ps = new PrintWriter(s.getOutputStream(), true);
            System.out.println("new socket for player connected and new handler initated");
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String mssg;
        int i = 0;
        while (true) {
            try {
                System.out.println("waiting for new message");
                mssg = dis.readLine();

                if (mssg.equals("signup")) {

                    System.out.println("request signup");
                    signUP();

                } else if (mssg.equals("login")) {

                    System.out.println("request login");
                    signIn();

                } else if (mssg.equals("invite")) {

                    String invitedPlayerUserName = dis.readLine();
                    invitePlayer(invitedPlayerUserName);

                } else if (mssg.equals("logout")) {

                    System.out.println("request logout");
                    sendUpdatetoAllClients("logout");
                    closePlayerConnection(this, "OFFLINE");

                }else if (isNumeric(mssg)||mssg.contains("chat")||mssg.equals("pause")) {                    //send movement
                    System.out.println("Play Movement");
                    gameMatches.get(gameIndex).sendNewMove(mssg);
                } else if (mssg.equals("accept") || mssg.equals("refused")) {
                    //client accept or refuse invitation from another thread
                    invitationStatus.set(invitationIndex, mssg);
                } else {
                    System.out.println("unknown operation");
                    System.out.println(mssg);
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void signIn() {
        String userName = "", password = "";
        try {
            userName = dis.readLine();
            password = dis.readLine();
            if (UserModel.validatePlayer(userName, password)) {

                UserModel.updatePlayerState(userName, "ONLINE");
                user = getUserInfo(userName);
                onlinePlayers.add(this);
                onlinePlayersUNames.add(userName);

                ps.println("loginDone");
                sendUserInfo(this.ps, user);
                //refresh players list and send the player list to the player
                refreshPlayersList();
                sendPlayersList();
                GameServerGUI.updatePlayersTable();
                sendUpdatetoAllClients("login");
            } else {
                ps.println("loginFailed");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendUpdatetoAllClients(String updateState) {
        for (ClientHandler player : onlinePlayers) {
            if (player.user.userID != user.userID) {
                player.ps.println("updateStatus");
                player.ps.println(this.user.userName);
                player.ps.println(updateState);
                player.ps.println(playersList.size() - 1);
                for (int i = 0; i < playersList.size(); i++) {
                    if (playersList.get(i).userID != player.user.userID) {
                        sendUserInfo(player.ps, playersList.get(i));
                    }
                }
            }
        }
    }

    public void sendPlayersList() {
        ps.println(playersList.size() - 1);
        for (int i = 0; i < playersList.size(); i++) {
            if (playersList.get(i).userID != user.userID) {
                System.out.println("send player " + playersList.get(i).userName);
                sendUserInfo(this.ps, playersList.get(i));
            }
        }
    }

    public void signUP() {
        try {
            user.userName = dis.readLine();
            user.password = dis.readLine();
            user.email = dis.readLine();
            user.score = 0;
            user.state = "OFFLINE";
            refreshPlayersList();
            //refresh data in server GUI
            //send updates to clients and server
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!(user.userName.isEmpty() && user.email.isEmpty() && user.password.isEmpty())) {
            if (UserModel.addPlayer(user)) {
                ps.println("signupDone");
                GameServerGUI.updatePlayersTable();
            } else {
                ps.println("signupFailed");
            }
        } else {
            ps.println("signupFailed");
        }

    }

    public void invitePlayer(String userName) {
        ClientHandler invitedClient = onlinePlayers.get(onlinePlayersUNames.indexOf(userName));
        invitedClient.ps.println("invitation");
        invitedClient.ps.println(this.user.userName);

        try {
            this.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        invitationStatus.add(new String("waiting"));
        this.invitationIndex = invitationStatus.size() - 1;
        invitedClient.invitationIndex = invitationStatus.size() - 1;
        while (invitationStatus.get(invitationIndex).equals("waiting"));
        String invitatonReply = invitationStatus.get(invitationIndex);
//        String invitatonReply = "accept";

        if (invitatonReply.equals("accept")) {
            //recive accept
            this.ps.println("invitationAccepted");
            try {
            this.sleep(500);
             } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
             }
            gameMatches.add(new GameMatch(this.user.userName, userName, this.s, invitedClient.s));
            this.gameIndex = gameMatches.size() - 1;
            invitedClient.gameIndex = gameMatches.size() - 1;

        } else {
            this.ps.println("invitationRefused");
        }

    }

    private User getUserInfo(String userName) {
        return UserModel.playerInfo(userName);
    }
    
    public void sendUserInfo(PrintWriter ps, User user) {
        ps.println(user.userID);
        ps.println(user.userName);
        ps.println(user.email);
        ps.println(user.state);
        ps.println(user.score);
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
            System.out.println(handler.user.userName);
            UserModel.updatePlayerState(handler.user.userName, state);
            handler.dis.close();
            handler.ps.close();
            GameServerGUI.updatePlayersTable();
            handler.stop();

            onlinePlayers.remove(handler);
            onlinePlayersUNames.remove(handler.user.userName);
            playersList.remove(handler);

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
