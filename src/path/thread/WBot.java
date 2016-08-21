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
    public WBot(ConCom cc, ROT b){
        concom=cc;
        bot=b;
        t=new Thread(this,b.toString());
        threadName=b.toString();
    }
    @Override
    public void run() {
        //bot.orders=concom.toOrder(bot);
        //byte[][] data=concom.manualorder();
        
        //bot.ini(out, concom.checkStatus());
        int wtime=0;
        
        int ind=1;
        //need to request a mission from mission center
        //instead of rolling through quests
        
        
        boolean startflag=false;
        while (!bot.task.isEmpty()) {
            try {
                //if (startflag)
                //Route.refresh();
                synchronized (this){
                if ((bot.task.element().desx==bot.locationX)&&(bot.task.element().desy==bot.locationY)&&(bot.task.element().order!=1)){
                bot.task.element().order=1;
                }
                    
                    
                
                mdesx=bot.desx;
                mdesy=bot.desy;
                mdesd=bot.desd;
                startflag=true;
                //System.out.printf("the destination is %d and %d %d\n", mdesx*1000,mdesy*1000,mdesd);
                bot.mission=ConCom.toSommand(ind,bot.ID ,1 ,bot.order[0], mdesx,mdesy ,bot.toAngle(mdesd));
                //bot.mission=ConCom.toSommand(ind,bot.ID ,1 ,bot.order[Amap.sstime], bot.CoX[Amap.sstime],bot.CoY[Amap.sstime] ,bot.toAngle(bot.dir[Amap.sstime]));
                ind++;
                bot.write(bot.mission, wtime);
                bot.locationX=mdesx;
                bot.locationY=mdesy;
                bot.direction=mdesd;
                bot.getpos();
                }
                //System.out.printf("Remain dis is %d\n", bot.remdis);
                while ((int)bot.remdis!=0){
                    Thread.sleep(100);
                    bot.getpos();
                    //System.out.printf("Remain dis is %d\n", bot.remdis);
                }
                
                
                
                synchronized (this){
                
                if ((bot.task.element().desx==bot.locationX)&&(bot.task.element().desy==bot.locationY)){
                    if(bot.task.element().order==1){
                    System.out.printf("\nYo the Task I just completed is to move to %d and %d\n", bot.desx,bot.desy);
                    System.out.println("Task completed");
                
                    bot.task.remove();}
                    else{
                        System.out.println("going to stall");
                
                    }
                    
                    
                
                }}
                
                bot.running=true;
                while((bot.running)&&(!bot.task.isEmpty())){Thread.sleep(10);}
                
                
                   System.out.printf("Task number is %d",bot.task.size());     
                //recalculate and get new tasks for it.
                
                //TimeUnit.SECONDS.sleep(1);
                
                
            }catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                Logger.getLogger(WBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        Amap.iMap[bot.locationX][bot.locationY]=0;
        //out.close();
        bot.close();
        
        System.out.println(threadName+" has ended");
            

    }
    
}