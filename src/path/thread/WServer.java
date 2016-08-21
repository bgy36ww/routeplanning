/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.Route;
import path.communication.ComServer;
import path.container.Amap;
import path.container.ROT;
import path.converter.ConCom;
import path.io.BotCommander;
import path.io.InputOrder;

/**
 *
 * @author wei
 */
public class WServer implements Runnable{
    protected int          serverPort   = 9000;
    protected static int ind=0;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ExecutorService threadPool =
        Executors.newFixedThreadPool(11);
    protected int robotnumber=2;
    public boolean ready=false;
    public ArrayList<ROT> rlist=new ArrayList<>();
    
    public WServer(int port){
        this.serverPort = port;}

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    @Override
    public void run() {
        
        robotnumber=Amap.bot.length;
        ConCom concom=new ConCom();
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        WOut wout=new WOut();
        System.out.println("Connection get");
                    System.out.flush();
        openServerSocket();
        while(! isStopped()){
            
            
            //connecting robot
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    break;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            
            
            
            System.out.printf("Got the %d machine", ind);
            //create a new robot
            //wait until all robot is connected
            ROT r=connectBOT(clientSocket,ind+1);
            Amap.bot[ind]=r;
            r.task=InputOrder.readTask(ind);
            System.out.printf("The size of this task is %d\n",r.task.size());
            rlist.add(r);
            if (ind+1<robotnumber){ind++;continue;}
            
            
            
            
            try{
                
                
                
            //refresh robot starting position
            for (ROT rr:rlist){
            try {
                rr.getpos();
                rr.locationX=rr.rx/1000;
                rr.locationY=rr.ry/1000;
                rr.direction=(rr.rd+45)/90*90;
                rr.direction=rr.toD(rr.direction);
                System.out.printf("This robot is %d and its starting location is %d %d \n", rr.ID,rr.locationX,rr.locationY);
            } catch (Exception ex) {
            }
            }
            
            
            
            
            
            
            
            Amap.sstime=0;
            
            
            //should start planning here
            
            ready=true;
            //System.exit(0);
            
            
            Route.refresh();
            
            
            for (ROT rr:rlist){
            this.threadPool.execute(
                new WBot(concom,rr));}
            
            //wout.run();
            this.threadPool.execute(wout);
            }
            finally{stop();}
        }
        
        while (true){
            int ttr=rlist.size();
            int tt=0;
            
            
            while (tt<ttr){
                ttr=0;
                for (ROT rr:rlist){
                if (!rr.task.isEmpty())ttr++;
                }
                try {
                    tt=0;
                    Thread.sleep(10);
                    for (ROT rr:rlist){
                        if ((rr.running)&&(!rr.task.isEmpty())) tt++;
                    }   } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(ttr);
            synchronized (this){
            System.out.println("algorithm reset");
            for (ROT rr:rlist)rr.running=false;
            
            
            Route.refresh();
            if (ttr==0){break;}
            }
            
            //break;
        }
        
        
        
        this.threadPool.shutdown();
        System.out.println("Server Stopped.") ;
        System.exit(0);
    }
    
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    
    
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 9000", e);
        }
    }
    
    private ROT connectBOT(Socket cs,int id){
        ConCom concom=new ConCom();
        ROT r=null;//=new ROT(0,0,ind++);
        ComServer coms=new ComServer(cs);
        InputOrder io=Route.getOrder();
        r=new ROT(id);
        r.ini(coms,concom.checkStatus());
    return r;
    }

    
}
