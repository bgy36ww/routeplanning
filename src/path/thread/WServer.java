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
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.Route;
import path.communication.ComServer;
import path.container.Amap;
import path.container.MissionPOD;
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
        Executors.newFixedThreadPool(20);
    
    //protected int robotnumber=2;
    public boolean ready=false;
    public ArrayList<ROT> rlist=new ArrayList<>();
    
    static public int alive=0;
    static public int finishe=0;
    
    static public synchronized void tellive(){
    alive++;
    }
    static public synchronized void teldead(){
    alive--;
    }
    static public synchronized void finished(){
    finishe++;
    }
    
    
    public WServer(int port){
        this.serverPort = port;}

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    @Override
    public void run() {
        
        
        //ConCom concom=new ConCom();
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        //Initialzation
        this.threadPool.execute(
                new WOut());
        
        
        
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
            rlist.add(r);
            Amap.idlebotset.add(r);
            this.threadPool.execute(
                new WBot(r));
            
        }
        
        
        
        while (true){
            int ttr=rlist.size();
            int tt=0;
            
            
            while (finishe<alive){
                try {
                Thread.sleep(10);
                
                } catch (InterruptedException ex) {
                    Logger.getLogger(WServer.class.getName()).log(Level.SEVERE, null, ex);
                }
}
            
           
            System.out.println(ttr);
            
            System.out.println("algorithm reset");
            Route.refresh();
            System.out.println("reset finished");
            //for (ROT rr:rlist)rr.running=false;
            
             finishe=0;
            if (alive==0){break;}
        }
        
        
        
        this.threadPool.shutdown();
        System.out.println("Server Stopped.") ;
        //System.exit(0);
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
        r.idle=true;
        r.ini(coms,concom.checkStatus());
    return r;
    }

    
}
