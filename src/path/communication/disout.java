/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.communication;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wei
 */
public class disout {
        private final ServerSocket listener;
	static private Socket s;
        static private DataOutputStream out;
        public disout() throws IOException{
                listener=new ServerSocket(9080);
                s=listener.accept();
                out=new DataOutputStream(s.getOutputStream());
        }
        
        public void write(int in) throws IOException{
            try{
            out.writeInt(in);
            }catch(IOException e){
            s=listener.accept();
            out=new DataOutputStream(s.getOutputStream());
            }
        
        
        }
        
    
}
