/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import java.io.IOException;
import path.Route;
import path.algorithm.Algorithm;
import path.communication.disout;
import path.container.Amap;
import path.container.ROT;
import path.io.FileReader;
import path.io.InputOrder;

/**
 *
 * @author wei
 */
public class newunittest {

       public static void main(String[] args) throws IOException{
                FileReader fr=new FileReader("/home/wei/LiftianPathFinding/sf_sh/Xiaojun/PODMap");
		fr.scanM();
		Algorithm al=new Algorithm();
		InputOrder ior=new InputOrder("/home/wei/LiftianPathFinding/sf_sh/Xiaojun/input.txt");
                Route.init(al, ior);
                
                Amap.get();
                Amap.cMap();
                ROT r1=new ROT(1);
                ROT r2=new ROT(2);
                Amap.bot=new ROT[2];
                Amap.bot[0]=r1;
                Amap.bot[1]=r2;
                r1.reset();
                r1.locationX=0;
                r1.locationY=0;
                r2.locationX=0;
                r2.locationY=5;
                r1.task=InputOrder.readTask(0);
                r2.task=InputOrder.readTask(1);
                Route.refresh();
                r1.locationX=r1.desx;
                r1.locationY=r1.desy;
                r1.direction=r1.desd;
                r2.locationX=r2.desx;
                r2.locationY=r2.desy;
                r2.direction=r2.desd;
                Route.refresh();
                r1.locationX=r1.desx;
                r1.locationY=r1.desy;
                r1.direction=r1.desd;
                r2.locationX=r2.desx;
                r2.locationY=r2.desy;
                r2.direction=r2.desd;
                Route.refresh();
                r1.locationX=r1.desx;
                r1.locationY=r1.desy;
                r1.direction=r1.desd;
                r2.locationX=r2.desx;
                r2.locationY=r2.desy;
                r2.direction=r2.desd;
                Route.refresh();
                r1.locationX=r1.desx;
                r1.locationY=r1.desy;
                r1.direction=r1.desd;
                r2.locationX=r2.desx;
                r2.locationY=r2.desy;
                r2.direction=r2.desd;
                Route.refresh();
                r1.locationX=r1.desx;
                r1.locationY=r1.desy;
                r1.direction=r1.desd;
                r2.locationX=r2.desx;
                r2.locationY=r2.desy;
                r2.direction=r2.desd;
                Route.refresh();
                r1.task.remove();
                r2.task.remove();
                r1.locationX=r1.desx;
                r1.locationY=r1.desy;
                r1.direction=r1.desd;
                r2.locationX=r2.desx;
                r2.locationY=r2.desy;
                r2.direction=r2.desd;
                Route.refresh();
       
       }

    
}
