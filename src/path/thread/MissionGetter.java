/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.container.MissionPOD;

/**
 *
 * @author Waver
 */
public class MissionGetter implements Runnable{
    Queue<MissionPOD> q;
    public MissionGetter(Queue<MissionPOD> mq){
    q=mq;
    }
    @Override
    public void run() {
        

        
        try {
            q.add(new MissionPOD(1,8,1));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,8,2));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,18,1));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,18,2));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,18,8));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,18,7));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,16,1));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,16,2));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,15,7));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,15,8));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,14,7));
            Thread.sleep(20000);
            q.add(new MissionPOD(1,14,8));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    
    }    
}
