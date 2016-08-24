package path;
import java.util.ArrayList;
import java.util.Arrays;
import path.io.*;
import path.container.*;
import path.algorithm.*;
import path.converter.ConCom;

public class Route{
        static private Algorithm al;
        static private InputOrder ior;
    
	static private rMap rmap;
        static public void init(Algorithm a,InputOrder io){
            al=a;
            //ior=io;
        }
        static public InputOrder getOrder(){
            return ior;
        }
	static public void ini(){

	}
        private Route instance=null;
        private Route(){}
        public synchronized Route get (){
        if (instance==null)
        instance=new Route();
            return instance;
        }
        
        static public synchronized void rearrange(ROT[] r){
        
            Arrays.sort(r);
        
        }
        
        
        
        
        static public synchronized void refresh(){
            
           // System.out.println(Amap.fpMap[4][18][4]);

            boolean retry=true;
            while (retry){
                
                //rearrange(Amap.bot);
                
                for (ROT rr:Amap.bot){
                    Amap.fbMap[0][rr.locationX][rr.locationY]=rr.ID;
                    //Amap.fbMap[1][rr.locationX][rr.locationY]=rr.ID;
                }
                
                
                retry=false;
            Amap.reset();
            al.ffMap=Amap.ffMap;
            for (int t=0;t<10;t++)
            al.printFDMap2(t);
            
            System.out.printf("\nWe have %d robots here\n",Amap.bot.length);
            System.out.flush();
            
            for (int ind=0;ind<Amap.bot.length;ind++){
                ROT rr=Amap.bot[ind];
                System.out.printf("The starting location for %d %d %d the starting direction is %d it has %d tasks\n",rr.ID,rr.locationX,rr.locationY,rr.direction,rr.task.size());
                
                rr.reset();
                rr.tx=rr.locationX;
                rr.ty=rr.locationY;
                rr.td=rr.direction;
                rr.turns=99;
                int btime=0;
                try{
                for (Task ts:rr.task){
                    System.out.printf("The destination location x is %d the destination location y is %d the ID is %d\n", ts.desx, ts.desy, rr.ID);
                    btime=planTask(ts,rr, rr.tx, rr.ty, rr.td, ts.desx, ts.desy, btime);
                    rr.turns=rr.turns>btime?btime:rr.turns;
                    //break;
                }}
                catch(NullPointerException e){
                    ROT kt=rr;
                    retry=true; 
                    System.out.println("OH BOY!!!!!!");
                    System.out.printf("The %d robot is down", rr.ID);
                    for (int i=ind;i>0;i--){
                    Amap.bot[i]=Amap.bot[i-1];
                    }
                    Amap.bot[0]=kt;
                    break;
                }
                System.out.println(btime);
                
                for (int ttt=btime+1;ttt<Amap.maxsize;ttt++)
                al.setRest(ttt, rr.tx, rr.ty);
                
                Amap.time=btime>Amap.time?btime:Amap.time;
                rr.desx=rr.CoX[1];
                rr.desy=rr.CoY[1];
                rr.desd=rr.dir[1];
                rr.dorder=rr.order[0];
                if (rr.dorder==0) rr.dorder=1;
                
                if ( btime==0){
                rr.desx=rr.CoX[0];
                rr.desy=rr.CoY[0];
                rr.desd=rr.dir[0];
                rr.dorder=0;
                //ind++;
                }
                
            }
                //rr.mission=ConCom.toSommand(time, time, btime, btime, time, time, time)
            }
            System.out.println("let's see the different");
            //System.exit(0);
            
            
        }
        static public synchronized int planTask(Task order,ROT r,int x,int y,int d,int desx,int desy,int time){
        switch(order.order){
            case 0: time=al.planRoute(r, x, y, d, desx, desy,time);
                    
                    break;
            case 1: if ((x!=desx)||(y!=desy))
                    {time=al.planRoute(r, x, y, d, desx, desy,time);}
                    time=al.liftPOD(r, time, desx, desy);
                    
                    break;
            case 2: if ((x!=desx)||(y!=desy))
                    {time=al.planRoute(r, x, y, d, desx, desy,time);}
                    time=al.dropPOD(r, time, desx, desy);
                    
                    break;
            case 3: if ((x!=desx)||(y!=desy))
                    {time=al.planRoute(r, x, y, d, desx, desy,time);}
                    time=al.stay(r, time, desx, desy);
                    
                    System.out.println("I am waiting");
                    break;
            case 4: time=al.stay(r, time, x, y);
                    break;
            default:System.out.println("Something is wrong!!!");
                    break;
        
        }
        
        return time;
        }
        
        static public void printMap(){
                for (int t=0;t<Amap.time;t++){
                            al.printFDMap(t);
                        }
        
        }
        
        
        
	static public rMap getResult(){
		return rmap;
	}


}
