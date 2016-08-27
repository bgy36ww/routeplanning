/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import path.communication.DBConnection;
import path.container.Amap;

/**
 *
 * @author Waver
 */
public class Jobfinisher implements Runnable {
    DBConnection dbc=null;
    public Jobfinisher(DBConnection db){
        dbc=db;
    }
    
    @Override
    public void run() {
        Amap.picking=0;
        while(true){
        try{
        while (Amap.picking==0){
            Thread.sleep(1000);
            
        }
        
        if (Amap.picking==1){
        
            synchronized (dbc){
            dbc.finish(Amap.outqueue.bqueue.element());
            System.out.println("Arrived");
            //System.exit(0);
            }
        
        }
        Amap.picking=0;
                        
        synchronized (Amap.outqueue.bqueue){
            Amap.outqueue.bqueue.remove().operatingstages++;}
                        
        }catch(Exception e){
        e.printStackTrace();
        }}
        
    }
    
}
