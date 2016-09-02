/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import Controller.MissionCenter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.Route;
import path.communication.ComServer;
import path.communication.DBConnection;
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
        this.threadPool.execute(
                new TrafficControl());
        DBConnection dbc=new DBConnection();
        this.threadPool.execute(new Jobfinisher(dbc));
        dbc=new DBConnection();
        MissionCenter mc=new MissionCenter(Amap.idlebotset,Amap.runningbotset,Amap.missionholder);    
        MissionDispatcher md=new MissionDispatcher(mc);
        this.threadPool.execute(md);
        MissionGetter mg=new MissionGetter(Amap.missionholder,dbc);
        this.threadPool.execute(mg);
        
        
        this.threadPool.execute(new WDis());
        
        
        System.out.println("Connection get");
                    System.out.flush();
        openServerSocket();
        
        Amap.botset=new HashSet<>();
        
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
            System.out.printf("Got the %d machine", ind+1);
            
            boolean jumpf=false;

            for (ROT rr:Amap.botset){
            if (clientSocket.getInetAddress().equals(rr.getIP()))
            {
                ConCom concom=new ConCom();
                ComServer coms=new ComServer(clientSocket);
                rr.ini(coms,concom.checkStatus());
                jumpf=true;
                System.out.println("Got the old machine");
                //System.exit(0);
            }
            }
            if (jumpf){continue;}
            
            //create a new robot
            //wait until all robot is connected
            ROT r=connectBOT(clientSocket,ind+1);
            rlist.add(r);
            try {
                r.getpos();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            r.setDBot();
            synchronized(Amap.obot){
            Amap.obot.add(r.dbot);}
            this.threadPool.execute(
                new WBot(r,new DBConnection()));
            synchronized( Amap.idlebotset){
            Amap.idlebotset.add(r);}
            Amap.botset.add(r);
            r.idle=true;
            ind++;
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
        //InputOrder io=Route.getOrder();
        r=new ROT(id);
        r.idle=true;
        r.ini(coms,concom.checkStatus());
    return r;
    }

    
}
