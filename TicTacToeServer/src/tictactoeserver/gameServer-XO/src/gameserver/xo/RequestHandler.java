/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.xo;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Basant Mahrous
 */
class RequestHandler extends Thread{
    DataInputStream dis;
    PrintStream ps;
    static Vector<RequestHandler> clientsVector = new Vector<RequestHandler>();

    public RequestHandler(Socket cs) {
        try {
            dis = new DataInputStream(cs.getInputStream());
            ps = new PrintStream(cs.getOutputStream());
            clientsVector.add(this);
             start();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void closeAllInternalSockets(String msg) {
        for (RequestHandler ch : clientsVector) {
            try {
                dis.close();
                ps.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        
    }
    

    
    
}
