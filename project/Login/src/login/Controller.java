/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author sara
 */
public class Controller
{DataOutputStream dout;

    public Controller(Socket s,String User, String Pass) throws IOException
            {
            
            
        dout = new DataOutputStream(s.getOutputStream());
       onSetupGameClick(s, User,Pass);
            }
      
            

    Controller()
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private void onSetupGameClick(Socket s,String User,String Pass) {
        try{    
            

            dout.writeUTF("login");
             dout.writeUTF(User);
             dout.writeUTF(Pass);
            dout.flush();

            dout.close();
            s.close();

            }catch(Exception e){
                System.out.println(e);
                }
    }
}
