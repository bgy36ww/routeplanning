/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.communication.disout;
import path.container.Amap;
import path.container.ROT;

/**
 *
 * @author wei
 */
public class WOut implements Runnable{

    private disout dis;
    public WOut(){
    
        try {
            dis=new disout();
        } catch (IOException ex) {
            Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        try {
            
            while(true){
                Thread.sleep(100);
                //Thread.yield();
                dis.write(-900);
                for (ROT r:Amap.bot){
                    
                    dis.write(-800);
                    dis.write(r.ID);
                    dis.write((Amap.ilength-1)*1000-r.rx);
                  
                    dis.write(r.ry);
                    dis.write((r.rd+90)%360);
                    dis.write(r.rem);
                    dis.write(-799);
                }
                
                dis.write(-899);
                Thread.sleep(100);
            }
        } catch (IOException ex) {
            Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
