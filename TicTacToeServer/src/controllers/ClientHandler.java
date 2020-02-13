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
                mssg = dis.readLine();
                if (mssg.equals("signup")) {
                    ps.println(signUP());
                } else if (mssg.equals("login")) {
                    signIn();
                } else if (mssg.equals("invite")) {
                    //send user invitation
                    String invitedPlayerUserName = dis.readLine();
                    invitePlayer(invitedPlayerUserName);
                } else if (mssg.equals("playalone")) {
                    //cretat a thread to handle the game with the AI

                } else if (mssg.equals("logout")) {
                    this.dis.close();
                    this.ps.close();
                    this.s.close();
                    this.stop();
                    onlinePlayers.remove(this);
                    onlinePlayersUNames.remove(this.user.userName);
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
            if (UserModel.validatePlayer(userName, password)) {
                
                updateUserState(userName, "online");
                user = getUserInfo(userName);

                onlinePlayers.add(this);
                onlinePlayersUNames.add(userName);
                
                
                ps.println("true");
                sendUserInfo(user);
                //send the player list to the player
                refreshPlayersList();
                for (int i = 0; i < playersList.size(); i++) {
                    if (playersList.get(i).userID != user.userID) {
                        sendUserInfo(playersList.get(i));
                    }
                }
            } else {
                ps.println("false");
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
// new VsGameMatch(this.s,invitedPlayerSocket);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        //get socket for then invited player then get the replay if ok kill the 2 threads
        // and create anoter thread to handle the game between the 2 players
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

    public void updateUserState(String useName, String State) {
//    UserModel.updateState(State);
    }

    public void refreshPlayersList() {
//        playersList=UserModel.getPlayersdata();
    }
}
