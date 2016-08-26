/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import Controller.MissionCenter;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        mc.publishMission();
        }
    
    }
    
}
