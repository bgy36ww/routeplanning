/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import path.container.Amap;

/**
 *
 * @author Waver
 */
public class Jobfinisher implements Runnable {

    @Override
    public void run() {
        while(true){
        try{
        while (Amap.picking==0){
            Thread.sleep(1000);
        }
        if (Amap.picking==1){
            Thread.sleep(10000);
        }
        Amap.picking=2;
        }catch(Exception e){
        e.printStackTrace();
        }}
        
    }
    
}
