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
    }
    @Override
    public void run() {
        
            try {
            dis=new disout();
            } catch (IOException ex) {
            Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(true){
                try{
                Thread.sleep(100);
                //Thread.yield();
                dis.write(-900);
                for (ROT r:Amap.obot){
                    
                    dis.write(-800);
                    dis.write(r.ID);
                    dis.write((Amap.ilength-1)*1000-r.rx);
                  
                    dis.write(r.ry);
                    dis.write((r.rd+90)%360);
                    dis.write(r.ostate+r.xx);
                    r.xx=0;
                    dis.write(-799);
                }
                
                dis.write(-899);
                Thread.sleep(100);
                } catch (IOException ex) {
                try {
                dis=new disout();
                } catch (IOException ex1) {
                    Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex1);
                }
                } catch (InterruptedException ex) {
                Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        
    }
    
}
