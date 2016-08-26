package path.container;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import path.communication.*;

public class ROT implements Comparable<ROT> {
	static public int maxsize=1000;
	public int locationX;
	public int locationY;
        
        public boolean calculated;
        public boolean operating;
       
        public DBot dbot;
        
        public int turns=99;
        
        public int rx;
        public int ry;
        public int rd;
        
        public boolean ready;
        
        public int operatingstages;
        public MissionPOD missionpod;
        
        public int tx;
        public int ty;
        public int td;
        
        
	private byte[] check;
        public byte[] mission;

	public int[] CoX;
	public int[] CoY;
	public int[] dir;
        
        public int cx;
        public int cy;
        public int cd;
        public boolean running=false;
        
        public boolean idle=true;
        
        public int xx=0;
        public int dorder=0;
        public int ostate=0;
        public int opod=0;
	public int time;
	public int state;
	public int pLevel;
	public int speed;
	public int direction;
	public int ID;
	public int podhold;
	public int ld;
	public int[] order;
	public int[] value;
	public Dual[] coor;
	public Dual[] orders;
	public int count;
	public byte[] status;
	public int rem;
        public int remdis;
        private ComServer coms;
        
        public int desx;
        public int desy;
        public int desd;
        
        
        public int taskhold;
        public Queue<Task> task;

        public int missionstatus;

	public int[][][] mfdMap;
	public int[][][] mfbMap;  


        public int toAngle(int d){
       // if (d==1) return 180;
        //if (d==2) return 90;
        //if (d==3) return 0;
        //if (d==4) return 270;
        if (d==1) return 180;
        if (d==2) return 90;
        if (d==3) return 0;
        if (d==4) return 270;
        return 0;
        }
        
        
        public int toD(int d){
        if (d==270) return 4;
        if (d==0) return 3;
        if (d==90) return 2;
        if (d==180) return 1;
        return 0;
        }
        
        
	public boolean getStatus(byte[] s){
		status=s;
                //System.out.print("The length is:");
                //System.out.println(s.length);
                if (status.length==0x2B){

               
                
                    
		rx= (status[11]<<24)|
       		(status[12]<<16)&0x00ff0000|
       		(status[13]<< 8)&0x0000ff00|
       		(status[14]<< 0)&0x000000ff;
                
                

                //locationX=rx/1000;

		ry= (status[15]<<24)|
       		(status[16]<<16)&0x00ff0000|
       		(status[17]<< 8)&0x0000ff00|
       		(status[18]<< 0)&0x000000ff;

                
                //locationY=ry/1000;

                rd=(status[19]<< 8)&0x0000ff00|
       		(status[20]<< 0)&0x000000ff;
                
                
                

		rem=(status[25]<< 0)&0x000000ff;
                
                remdis=(status[26]<< 8)&0x0000ff00|
       		(status[27]<< 0)&0x000000ff;

		//System.out.print("The current location is:");
		//System.out.print(rx);
		//System.out.print(" ");
		//System.out.print(ry);
		//System.out.print("direction is:");
		//System.out.print(rd);
		//System.out.print("Remaining tasks are:");
		//System.out.print(rem);
		//System.out.println();
                return true;
                }else{if (s.length==13){
                missionstatus=(status[9]<< 0)&0x000000ff;
                if (missionstatus!=0){
                System.out.print("fatal error in communication");
                System.out.print(missionstatus);
                System.out.println();
                System.out.flush();
                //return false;
                //System.exit(0);
                }
                return true;
                }else{System.out.println("not a command");return false;}}
                //return false;
	}


	public void setStatus(int s){
		state=s;
	}	


        public ROT(int x,int y){
        locationX=x;
        locationY=y;
        }
        
        
	/* class constructor
	Create every robot with it's starting map*/
        public void setTask(int t,Queue<Task> ts){taskhold=t;task=ts;}
        
        public void setDBot(){
            synchronized(dbot){
            dbot.ID=ID;
            dbot.ostate=ostate;
            dbot.rd=rd;
            dbot.rx=rx;
            dbot.ry=ry;
            dbot.xx=xx;}
        }
        
	public ROT(int id){
                dbot=new DBot();
                task=new LinkedList<>();
		ID=id;
		state=0;
                ostate=0;
		mfdMap=Amap.cMap();
		mfbMap=Amap.cMap();

		//order=new int[maxsize];
		order=new int[maxsize];
		value=new int[maxsize];

		value=new int[maxsize];		
		CoX=new int[maxsize];
		CoY=new int[maxsize];
		coor=new Dual[maxsize];
		for(int i=0;i<maxsize;i++){
			coor[i]=new Dual();
		}
		dir=new int[maxsize];
	}
        public void reset(){
                state=ostate;
                podhold=opod;
                
                mfdMap=Amap.cMap();
		mfbMap=Amap.cMap();
                
                order=new int[maxsize];
		value=new int[maxsize];

		value=new int[maxsize];		
		CoX=new int[maxsize];
		CoY=new int[maxsize];
		coor=new Dual[maxsize];
		for(int i=0;i<maxsize;i++){
			coor[i]=new Dual();
		}
		dir=new int[maxsize];
        }
	/*initialize all static member should only done once*/
	public boolean ini(ComServer com,byte[] c){
            coms=com;
            check=c;
            return !((com==null)||(c==null));
	}
        public void close(){
        coms.close();
        }
        public void write(byte[] bt,int t) throws IOException, InterruptedException{
            byte[] ret;
            do{
            do
            {
                
                ret=coms.write(check,t);
            }while(!getStatus(ret));
            }while(rem>10);
            
            
            do
            {
                
                ret=coms.write(bt,t);
            //System.out.println("TOOO");
            }while(!getStatus(ret));
        }
        public void getpos() throws IOException, InterruptedException{
            byte[] ret;
            do
            {ret=coms.write(check,0);
            }while(!getStatus(ret));
            //Amap.fbMap[0][desx][desy]=ID;
        }

    @Override
    public int compareTo(ROT o) {
        int compareunit=o.getdis();
        return this.getdis()-compareunit;
    }

    public int getdis(){
        return this.task.size();
        //return (this.locationX-this.task.element().desx)^2+(this.locationY-this.task.element().desy)^2;
    }

    public int getdisdiff(int i,int j){
    
        return (this.locationX-i)*(this.locationX-i)+(this.locationY-j)*(this.locationY-j);
    }
    
    public void toMission(){
        Task tt;
        task=new LinkedList<>();
        //if (this.operatingstages==1){
        tt=new Task(missionpod.xposition,missionpod.yposition,1);
        task.add(tt);//}
        //if (this.operatingstages==2){
        Amap.outqueue.bqueue.add(this);
        //this.operatingstages=3;
        //}
        //if (this.operatingstages==3){
        int ii=0;
        for (int i=0;i<Amap.outqueue.bqueue.size();i++)
        {
        if (((LinkedList)(Amap.outqueue.bqueue)).get(i)==this){
            ii=i;
        }}
        tt=new Task(Amap.outqueue.location[ii].x,Amap.outqueue.location[ii].y,3);
        task.add(tt);
        //}
        //if (this.operatingstages==4){
        tt=new Task(missionpod.xposition,missionpod.yposition,2);
        task.add(tt);
        //}
    }

}
