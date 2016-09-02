/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import Controller.MissionCenter;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.container.Amap;
import path.container.ROT;
import path.container.SortedArrayList;

/**
 *
 * @author omelet
 */
public class MissionDispatcher implements Runnable{
    private MissionCenter mc;
    public MissionDispatcher(MissionCenter m){
        mc=m;
    }
    
    @Override
    public void run() {
        while (true){
        try {
            Thread.sleep(1000);
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(mc.publishMission()){
        SortedArrayList<ROT> sqr=new SortedArrayList<ROT>();
        for(ROT rr:Amap.runningbotset){
            if (!Amap.outqueue.bqueue.contains(rr)&&(rr.operatingstages<=3)){
            sqr.insertSorted(rr);}
        }
        
        for(ROT rr:sqr){
        Amap.outqueue.bqueue.add(rr);
        }
        }
        }
    
    }
    
}
