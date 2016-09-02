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
import path.container.DBot;
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
            System.out.println("Connection get from Graph");
            } catch (IOException ex) {
            Logger.getLogger(WOut.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(true){
                try{
                while (Amap.obot.isEmpty()){
                Thread.sleep(100);
                }
                Thread.sleep(100);
                //Thread.yield();
                //System.out.println("Yeah, I am printing");
                //for (int i=0)
                
                dis.write(-900);
                synchronized(Amap.idlebotset){
                for (ROT rr:Amap.idlebotset)
                {
                rr.getpos();
                rr.setDBot();
                }}
                
                synchronized(Amap.obot){
                    dis.write(Amap.obot.size());
                for (DBot r:Amap.obot){
                    
                    dis.write(-800);
                    dis.write(r.ID);
                    dis.write((Amap.ilength-1)*1000-r.rx);
                    
                    dis.write(r.ry);
                    dis.write((540-r.rd)%360);
                    dis.write(r.ostate+r.xx);
                    dis.write(r.podid);
                    r.xx=0;
                    dis.write(-799);
                }}
                
                dis.write(-899);
                Thread.sleep(100);
                } catch (IOException ex) {
                try {
                dis=new disout();
                } catch (IOException ex1) {
                    ex1.printStackTrace();
                }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                }
        
    }
    
}
