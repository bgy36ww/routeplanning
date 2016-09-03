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
import path.communication.DBConnection;
import path.communication.disout;
import path.container.Amap;
import path.container.CommitedMission;
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
    private DBConnection db;
    private int lo=999;
    private int lx=999;
    private int ly=999;
    private int ld=999;
    //public boolean running=false;
    public WBot(ROT b,DBConnection dbc){
        db=dbc;
        
        synchronized (b){
            concom=new ConCom();
            bot=b;
            t=new Thread(this,b.toString());
            threadName=b.toString();
            bot.calculated=false;
            bot.operating=true;
            bot.idle=true;
        try {
            bot.resetall();
            bot.getpos();
            bot.locationX=(bot.rx+400)/1000;
            bot.locationY=(bot.ry+400)/1000;
            bot.direction=(bot.rd+20)/90*90;
            bot.direction=bot.toD(bot.direction);
            bot.commited=new CommitedMission(bot.locationX,bot.locationY,bot.direction);
            
            
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        }
    }
    @Override
    public void run() {
        //bot.orders=concom.toOrder(bot);
        //byte[][] data=concom.manualorder();
        int indd;
        //bot.ini(out, concom.checkStatus());
        int wtime=0;
        //int ind=2;
        
        System.out.printf("\nBot %d is running\n", bot.ID);
        bot.xx=0;
        while (bot.operating)
        {
        //need to request a mission from mission center
        //instead of rolling through quests

            while (bot.idle){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            Amap.iMap[bot.locationX][bot.locationY]=1;
            while (bot.operatingstages%7!=0){
                System.out.println("Bot not idle anymore");
                synchronized(Amap.get()){
                    bot.toMission();
                    WServer.tellive();
                }
                System.out.println("Mission Set");
            
                while (bot.task.isEmpty()){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(WBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                System.out.println("Mission Start");
                while(!bot.task.isEmpty()){
                    try{
                        bot.toMission();
                        bot.ready=true;
                        
                        synchronized(Route.get()){
                            Route.refresh(bot);
                        }
                        
                        
                        //while (!bot.calculated){
                        //    Thread.sleep(100);
                        //}
                        
                        
                        
                        
                        bot.ready=false;
                        System.out.println("Mission Calculated");
                    
                        synchronized (bot){
                            bot.calculated=false;
                            mdesx=bot.desx;
                            mdesy=bot.desy;
                            mdesd=bot.desd;
                
                
                            System.out.printf("yeeeee the destination for %d is %d and %d %d\n",bot.ID, mdesx,mdesy,mdesd);
                            System.out.printf("Also my tasks is %d %d\n",bot.task.element().desx,bot.task.element().desy);
                        }
                        boolean bf=false;
                        //if ((lo!=bot.dorder)||(lx!=mdesx)||(ly!=mdesy)||(ld!=mdesd)){
                        indd=bot.ind+1;
                        bot.mission=ConCom.toSommand(bot.ind++,bot.ID ,1 ,bot.dorder, mdesx,mdesy ,bot.toAngle(mdesd));
                        bot.commited=new CommitedMission(mdesx,mdesy,mdesd);
                        //bf=true;
                        //}
                        lo=bot.dorder;
                        lx=mdesx;
                        ly=mdesy;
                        ld=mdesd;
                        //if (bf){
                        
                        synchronized (bot){
                            bot.write(bot.mission, wtime);
                            System.out.println("I am sending my tasks");
                            bot.getpos();
                        //}
                        if (indd!=bot.ind){
                        bot.mission=ConCom.toSommand(++bot.ind,bot.ID ,1 ,bot.dorder, mdesx,mdesy ,bot.toAngle(mdesd));
                        bot.write(bot.mission, wtime);
                            System.out.println("I am sending my tasks again");
                            bot.getpos();
                        }
                        }
                        int safty=0;
                        //while ((int)bot.rem!=0){
                        while (safty<9){
                        safty++;
                            bot.setDBot();
                            Thread.sleep(100);
                            bot.getpos();
                        }
                        
                        //wont run here
                        synchronized (bot){
                        bot.locationX=(bot.rx+400)/1000;
                        bot.locationY=(bot.ry+400)/1000;
                        bot.direction=(bot.rd+5)/90*90;
                        bot.direction=bot.toD(bot.direction);
                        }
                        System.out.printf("\nthe turns left for %d is %d\n",bot.ID,bot.turns);
                        System.out.printf("\ntasks left for %d is %d\n",bot.ID,bot.task.size());
                        System.out.printf("\nI amd %d ing\n",bot.task.element().order); 
                        System.out.printf("\nThe tasks is going to %d %d \n ",bot.task.element().desx,bot.task.element().desy); 
                        //System.exit(0);
                        
                        if ((bot.dorder==2)){
                            while (!bot.task.isEmpty()){
                            while (bot.rem!=0){Thread.sleep(100);}
                            bot.task.remove();}
                            
                            System.out.println("Entered next phase");
                            
                            System.exit(0);
                            
                        }
                        
                        if ((bot.dorder==3)){
                            while (!bot.task.isEmpty()){
                            while (bot.rem!=0){Thread.sleep(100);}
                            bot.task.remove();}
                        }
                        
                        if ((!bot.task.isEmpty())&&(bot.turns<=1)&&(bot.task.element().desx==bot.locationX)&&(bot.task.element().desy==bot.locationY)&&(bot.task.element().order==0)&&(bot.operatingstages!=6)){
                            bot.task.remove();
                        }
                    
                        if (bot.operatingstages==6){
                        if ((bot.locationX==bot.missionpod.xposition)&&(bot.locationY==bot.missionpod.yposition)){

                            //&&(bot.rstate==0)
                            if((bot.ostate==0)){
                                while (bot.rem!=0){Thread.sleep(100);}
                            bot.task.remove();
                            break;
                            }
                            
                        }
                        }
                        
                        WServer.finished();
                        //while (WServer.finishe!=0){Thread.sleep(10);
                        //}
                    }catch( IOException | InterruptedException e){
                        e.printStackTrace();
                    }
                }
                
                System.out.println("All tasks done");
                
                WServer.teldead();
                proceed();
                
            }
                
                synchronized(Amap.get()){
                Amap.iMap[bot.locationX][bot.locationY]=0;
                Amap.runningbotset.remove(bot);}
                synchronized(Amap.idlebotset){
                Amap.idlebotset.add(bot);
                
                bot.idle=true;
                WServer.finished();
                db.reportback(bot.missionpod.missionC);
                }
        
        }
        
        bot.close();
        System.out.printf("I am bot %d and I just ended my tasks",bot.ID);
        System.out.println(threadName+" has ended");
       // System.exit(0);
    }
    
    public void proceed(){
                System.out.println("Entering next phase");
                System.out.println(bot.operatingstages);
                System.exit(0);
        
        
                if (bot.operatingstages==1){
                    bot.ostate=1;
                    bot.xx=1;
                    bot.opod=Amap.tpMap[mdesx][mdesy];
                    Amap.tpMap[mdesx][mdesy]=0;
                    bot.setDBot();
                    System.out.println("I am changing the state");
                    bot.operatingstages++;
                }
        
        
        
                
                if (bot.operatingstages==3){
                    if ((bot==Amap.outqueue.bqueue.peek())&&(Amap.picking==0)&&(bot.locationX==Amap.outqueue.location[0].x)&&(bot.locationY==Amap.outqueue.location[0].y)){
                        Amap.picking=1;
                        
                        //System.exit(0);
                    }
                }
                if (bot.operatingstages<3){
                    bot.operatingstages++;
                }
                
                if (bot.operatingstages==6){
                        
                    
                        if ((bot.locationX==bot.missionpod.xposition)&&(bot.locationY==bot.missionpod.yposition)){
                            bot.ostate=0;
                            bot.xx=3;
                            Amap.tpMap[mdesx][mdesy]=bot.opod;
                            bot.opod=0;
                            bot.setDBot();
                            
                            System.out.println("I am changing the state down");
                            bot.operatingstages++;
                            
                            
                        }
                }
                if ((bot.operatingstages==4)&&(bot.locationX==0)&&(bot.locationY==4)){
                    bot.operatingstages++;}
                if ((bot.operatingstages==5)&&(bot.locationX==0)&&(bot.locationY==3)){
                    bot.operatingstages++;}
    
    
    }
}