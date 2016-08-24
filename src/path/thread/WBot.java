/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.Route;
import path.communication.ComServer;
import path.communication.disout;
import path.container.Amap;
import path.container.ROT;
import path.converter.ConCom;

/**
 *
 * @author wei
 */
public class WBot implements Runnable{
    public Thread t;
    public String threadName;
    private final ConCom concom;
    private final ROT bot;
    private int mdesx;
    private int mdesy;
    private int mdesd;
    //public boolean running=false;
    public WBot(ROT b){
        
        
            concom=new ConCom();
            bot=b;
            t=new Thread(this,b.toString());
            threadName=b.toString();
            bot.calculated=false;
            bot.operating=true;
            bot.idle=true;
        try {
            bot.getpos();
            bot.locationX=(bot.rx+500)/1000;
            bot.locationY=(bot.ry+500)/1000;
            bot.direction=(bot.rd+45)/90*90;
            bot.direction=bot.toD(bot.direction);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }
    @Override
    public void run() {
        //bot.orders=concom.toOrder(bot);
        //byte[][] data=concom.manualorder();
        
        //bot.ini(out, concom.checkStatus());
        int wtime=0;
        int ind=1;
        
        while (bot.operating)
        {
        //need to request a mission from mission center
        //instead of rolling through quests
            
            while (!bot.idle){}
            Amap.iMap[bot.locationX][bot.locationY]=1;
            bot.toMission();
            
            
            
                WServer.tellive();
                
                while(!bot.task.isEmpty()){
                try{
                synchronized (this){
                
                    
                    while (!bot.calculated){}
                
                    mdesx=bot.desx;
                    mdesy=bot.desy;
                    mdesd=bot.desd;
                
                
                    System.out.printf("the destination for %d is %d and %d %d\n",bot.ID, mdesx,mdesy,mdesd);
                    bot.mission=ConCom.toSommand(ind,bot.ID ,1 ,bot.dorder, mdesx,mdesy ,bot.toAngle(mdesd));
                    ind++;
                    bot.write(bot.mission, wtime);
                    bot.getpos();
                    }
                    while ((int)bot.remdis!=0){
                        Thread.sleep(100);
                        bot.getpos();
                    }
                    bot.xx=0;
                    if (bot.dorder==2){bot.ostate=1;
                    bot.xx=1;
                    bot.opod=Amap.tpMap[mdesx][mdesy];
                    Amap.tpMap[mdesx][mdesy]=0;
                    System.out.println("I am changing the state");
                    }
                    if (bot.dorder==3){
                        bot.xx=2;
                        bot.ostate=0;
                        Amap.tpMap[mdesx][mdesy]=bot.opod;
                        bot.opod=0;
                        System.out.println("I am changing the state");
                    }
                    bot.locationX=(bot.rx+100)/1000;
                    bot.locationY=(bot.ry+100)/1000;
                    bot.direction=(bot.rd+45)/90*90;
                    bot.direction=bot.toD(bot.direction);
                    synchronized (this){
                        System.out.printf("\nthe turns left for %d is %d\n",bot.ID,bot.turns);
                        System.out.printf("\ntasks left for %d is %d\n",bot.ID,bot.task.size());
                        System.out.printf("\nI amd %d ing\n",bot.task.element().order); 
                        System.out.printf("\nThe tasks is going to %d %d \n ",bot.task.element().desx,bot.task.element().desy); 
                        //System.exit(0);
                        if ((bot.turns<=1)&&(bot.task.element().desx==bot.locationX)&&(bot.task.element().desy==bot.locationY)){
                            bot.task.remove();
                        }
                    }
                    if (bot.task.isEmpty()){Amap.iMap[bot.locationX][bot.locationY]=0;}
                    WServer.finished();
                    while (WServer.finishe!=0){Thread.sleep(10);
                    }
        }catch( IOException | InterruptedException e){
            e.printStackTrace();
        }
        
        Amap.iMap[bot.locationX][bot.locationY]=0;
        WServer.teldead();
        Amap.runningbotset.remove(bot);
        Amap.idlebotset.add(bot);
        bot.idle=true;
        }
        }
        
        bot.close();
        System.out.printf("I am bot %d and I just ended my tasks",bot.ID);
        System.out.println(threadName+" has ended");
       // System.exit(0);
    }
}