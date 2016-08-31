// : c14:FileChooserTest.java
// Demonstration of File dialog boxes.
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

import Controller.MissionCenter;
import path.*;
import path.io.*;
import path.graph.*;
import path.container.*;
import path.communication.*;
import path.converter.*;

import java.io.InputStream;


import java.net.URL;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import path.algorithm.Algorithm;
import path.thread.MissionDispatcher;
import path.thread.MissionGetter;
import path.thread.WBot;
import path.thread.WServer;

public class FileChooserTest extends JFrame {
  private static boolean rflag;
  private static boolean wflag;
  private WServer server;
  static public boolean starting=false;

  private JTextField filename = new JTextField(), dir = new JTextField();

  private JButton open = new JButton("start"), save = new JButton("close"), retrive = new JButton("Connect");

  public FileChooserTest() {
    JPanel p = new JPanel();
    open.addActionListener(new RrtrL());
    p.add(open);

    Container cp = getContentPane();
    cp.add(p, BorderLayout.SOUTH);
    dir.setEditable(false);
    filename.setEditable(false);
    p = new JPanel();
    p.setLayout(new GridLayout(4, 1));
    p.add(filename);
    p.add(dir);
    cp.add(p, BorderLayout.NORTH);
  }

  class RrtrL implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        
        dir.setText("Mission Dispatcher running");
        filename.setText("Waiting for Graphic Connection");
        System.out.println("Waiting for Graphic Connection");

        System.out.println("Connected");
        filename.setText("Connected");
        starting=true;
        //System.exit(0);
        
  }
  }

  class OpenL implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.print("YOOOO");
      DBConnection db=new DBConnection();
      
    }
  }

  class SaveL implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //JFileChooser c = new JFileChooser();
            
                
                
                mapgen();
		Algorithm al=new Algorithm();
                Route.init(al);
                ConCom concom=new ConCom();
                WServer server=new WServer(9000);
                server.run();
                
		//bt.read();	
		//Initiate Planning Process
		//store result in rMap class
		//rMap rmap=Route.getResult();
		
		//rMap rmap=new rMap(Amap.fdMap,Amap.fbMap,Amap.fpMap,Amap.iMap,Amap.time+1);
		System.out.println("get");
		
                
                //out=new ComServer();
                
                
                
                
                
                ///*
                
                
                //*/
                
                //while (true){
                //TimeUnit.MILLISECONDS.sleep(100);
                //dis.write(-800);
                //dis.write(r.ID);
                //dis.write(r.locationX+12000);
                //System.out.print((3000-r.locationY));
                //dis.write((3000-r.locationY+6000));
                //dis.write(r.direction);
                //dis.write(r.rem);
                //dis.write(-799);
                //}
                
                
                //WBot mt1=new WBot(concom,Amap.bot[0],out);	
                //mt1.t.start();
		//System.out.println("Thread started");
		
                //mt1.t.join();
		//GraphDrawing graphd=new GraphDrawing(rmap);

		}catch(Exception ee){
                    ee.printStackTrace();
		}
		finally{
                    System.out.println("end");
                    System.out.flush();
		//out.close();
		}
            
            //int rVal = c.showSaveDialog(FileChooserTest.this);
            //if (rVal == JFileChooser.APPROVE_OPTION) {
            
            //filename.setText(c.getSelectedFile().getName());
            //dir.setText(c.getCurrentDirectory().toString());
            
            //toOutput(c.getCurrentDirectory().toString()+"/"+c.getSelectedFile().getName());
            
            //}
            //if (rVal == JFileChooser.CANCEL_OPTION) {
            // filename.setText("You pressed cancel");
            //dir.setText("");
            //}

      


    }
  }
  public static void mapgen(){
                Amap.obot=new LinkedList<>();
        
                Amap.ilength=20;
		Amap.jlength=10;
		Amap.fdMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];
		Amap.fpMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];
		Amap.fbMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];
		Amap.ffMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];

		Amap.tpMap=new int[Amap.ilength][Amap.jlength];
                
		Amap.iMap=new int[][] {
                { 0,0,0,1,1,1,0,0,0,0},
                { 0,0,0,1,1,1,0,0,0,0},
                { 0,0,0,1,1,1,0,0,0,0},
                { 0,0,0,1,1,1,0,0,0,0},
                { 1,1,1,1,1,1,1,1,1,1},
                { 1,1,1,1,1,1,1,1,1,1},
                { 1,1,1,1,1,1,1,1,1,1},
                { 1,1,1,1,1,1,1,1,1,1},
                { 1,0,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,1,1,1,1,1,1,1,1,1},
                { 1,0,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,2,2,1,2,2,1,2,2,1},
                { 1,1,1,1,1,1,1,1,1,1}
                };
                

		for (int i=0;i<Amap.ilength;i++)
			for (int j=0;j<Amap.jlength;j++){
				if (Amap.iMap[i][j]==2){
					Amap.tpMap[i][j]=1;
				}
			}

		for (int t=0;t<Amap.currtime;t++){
			for (int i=0;i<Amap.ilength;i++){
			Amap.ffMap[t][i]=Amap.iMap[i].clone();
			Amap.fpMap[t][i]=Amap.tpMap[i].clone();}
		}
		
  
  }
  
  public static void getback(String s){
		ComServer out=null;
		try{
                mapgen();
		Algorithm al=new Algorithm();
		InputOrder ior=new InputOrder(s);
                Route.init(al);


		//bt.read();	
		//Initiate Planning Process
		//store result in rMap class
		//rMap rmap=Route.getResult();
		
		//rMap rmap=new rMap(Amap.fdMap,Amap.fbMap,Amap.fpMap,Amap.iMap,Amap.time+1);
		
		
                ConCom concom=new ConCom();
                //out=new ComServer();
                
                WServer server=new WServer(9000);
                
                
                server.run();
                
                //while (server.rlist.get(0)==null);
                //ROT r=server.rlist.get(0);
                
                /*while (true){
                
                Thread.sleep(100);
                
                dis.write(-800);
                dis.write(r.ID);
                System.out.printf("\ncurrent x output to Jun is %d %d \n ",r.locationY,r.locationX);
                System.out.flush();
                dis.write(r.locationX);
                dis.write(r.locationY);
                dis.write(r.direction);
                dis.write(r.rem);
                dis.write(-799);
                }*/
                
                
                //WBot mt1=new WBot(concom,Amap.bot[0],out);	
                //mt1.t.start();
		//System.out.println("Thread started");
		
                //mt1.t.join();
		//GraphDrawing graphd=new GraphDrawing(rmap);

		}catch(Exception e){
                    e.printStackTrace();
		}
		finally{
                    System.out.println("end");
                    System.out.flush();
		//out.close();
		}

	}

  //static public void waiting(ROT b,ComServer out,ConCom concom) throws Exception{
    
    //out.write(concom.checkStatus());
    //b.status=out.state;
    //b.getStatus();
    //while (b.rem>8)
    //{out.write(concom.checkStatus());
    //b.status=out.state;
    //b.getStatus();}
  //}
  
  
  public static void toOutput(String s){
		BotCommander bt=new BotCommander(s);
		bt.write();
		bt.close();
	
	}




  public static void drawing(String filepath){
		String fp="./path/documents/connectionprotocal.txt";
		FileReader fr=new FileReader(fp);
		fr.readC();
		fp="./path/documents/addressprotocal.txt";
		fr.newFile(fp);
		fr.readF();

		
		
		//Initiate Planning Process
		//Route.ini(filepath);
		//store result in rMap class
		rMap rmap=Route.getResult();
		//There are five methods in rMap to obtain
		//getBMap()  to get BOT map    3D array  return type:int[][][]
		//getPMap()  to get POD map    3D array  return type:int[][][]
		//getDMap()  to get Direction map    3D array  return type:int[][][]
		//getIMap()  to get Initial map    2D array  return type:int[][]
		//getTime()  obtain longest time planned so far
		
		//debugging class PrintMap
		//print2d   input(int[][])  print 2d array on the screen
		//print3d   input(int[][][] int)  print 3d array with specfic time range
		
		//int[][][] m=rmap.getDMap();
		//PrintMap p=new PrintMap();
		//p.print3d(m,rmap.getTime());

		LogWriter log=new LogWriter();
		
		LogWriter.write("test");

		GraphDrawing graphd=new GraphDrawing(rmap);
		
		
		
		log.close();


  }

  public static void main(String[] args) {
        run(new FileChooserTest(), 250, 110);
        
        while (!FileChooserTest.starting){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(FileChooserTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        mapgen();
        Set<ROT> ir=new HashSet();
        Queue<ROT> rr=new LinkedList<ROT>();
        Queue<MissionPOD> mq=new LinkedList<>();
        

	Amap.get();
        Amap.idlebotset=ir;
        Amap.runningbotset=rr;
        Amap.missionholder=mq;
        
        System.out.println("Mission Dispatcher running");
        
        Algorithm al=new Algorithm();
        Route.init(al);
        ConCom concom=new ConCom();
        WServer server=new WServer(9000);
        server.run();
                

  }

  public static void run(JFrame frame, int width, int height) {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width, height);
    frame.setVisible(true);
  }
} ///:~


