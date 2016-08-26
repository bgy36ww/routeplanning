/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.Route;
import path.container.Amap;
import path.container.MissionPOD;
import path.container.ROT;
import static path.thread.WServer.alive;
import static path.thread.WServer.finishe;

/**
 *
 * @author Waver
 */
public class TrafficControl implements Runnable{
    public Set<ROT> idlebotset;
    public Set<ROT> runningbotset;
    public Queue<MissionPOD> missionholder;
    
    public TrafficControl(){
        this.idlebotset=Amap.idlebotset;
        this.runningbotset=Amap.runningbotset;
        this.missionholder=Amap.missionholder;
    }
    
    @Override
    public void run() {
        System.out.println("Traffic Control started");
        while (true){
            try{
                
                
                Thread.sleep(100);

                while (Amap.runningbotset.isEmpty()){Thread.sleep(100);}
                int size=0;
                synchronized(runningbotset){
                    size=runningbotset.size();
                    for (ROT rr:this.runningbotset){
                        rr.idle=false;
                        //rr.ready=false;
                        while (!rr.ready){Thread.sleep(100);}
                    }
                    System.out.println("algorithm reset");
                    System.out.printf("the size before algorithm is %d\n",this.runningbotset.size());
                    Route.refresh();
                    System.out.printf("the size after algorithm is %d\n",this.runningbotset.size());
                    for (ROT rr:this.runningbotset){
                        System.out.printf("\nSetting %d robot free with %d takss\n", rr.ID,rr.task.size());
                        rr.calculated=true;
                        
                    }
                }
                    while (finishe<size){  
                        Thread.sleep(100);   
                    }}catch(Exception e){e.printStackTrace();}
            System.out.println("reset finished");
            finishe=0;
            
            if (alive==0){System.out.print("Sleeping");}
        }
    }
    
}
