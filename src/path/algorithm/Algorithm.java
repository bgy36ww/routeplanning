package path.algorithm;

import static java.lang.Math.abs;
import path.container.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.lang.NullPointerException;

public class Algorithm{

	private int[][][] dMap;
	private int[][][] fdMap;
	private int[][][] fpMap;
	private int[][][] fbMap;	
	public int[][][] ffMap;

        private Tile[][][] tile;
	private int[][][] traceMapx;
	private int[][][] traceMapy;
	private int[][][] mfdMap;
	private int[][][] mfbMap;
	private int[][][] aMap;
	private int iLength;
	private int jLength;
	private int[] order;
	private int[] value;
	private int startt;
	private int ld;
	private int dssx;
        private int dssy;
        
	private ROT bot;

        public int stay(ROT b,int t,int i,int j)throws NullPointerException{
               
                this.fdMap=Amap.fdMap;
		this.fpMap=Amap.fpMap;
		this.fbMap=Amap.fbMap;
		this.ffMap=Amap.ffMap;
                this.mfbMap=b.mfbMap;
                this.mfdMap=b.mfdMap;
                if (ffMap[t+1][i][j]==0){throw new NullPointerException("Well, someone took this place already");}
		bot=b;
                fbMap[t+1][i][j]=b.ID;
                setMark(t+1,i,j,true);
		bot.order[t]=1;
                b.dir[t+1]=b.td;
                b.CoX[t+1]=b.tx;
                b.CoY[t+1]=b.ty;
                
		return t+1;
	}

	public int liftPOD(ROT b,int t,int i,int j)throws NullPointerException{
                
                
                this.fdMap=Amap.fdMap;
		this.fpMap=Amap.fpMap;
		this.fbMap=Amap.fbMap;
		this.ffMap=Amap.ffMap;
                this.mfbMap=b.mfbMap;
                this.mfdMap=b.mfdMap;
                
                if (ffMap[t+1][i][j]==0){
                    System.out.println(ffMap[t+1][i][j]);
                    System.out.println(fbMap[t+1][i][j]);
                    System.out.println(Amap.iMap[i][j]);
                    throw new NullPointerException("Well, someone took this place already");}
		System.out.println(fpMap[t][i][j]);
                
                bot=b;
		bot.state=1;
                fbMap[t+1][i][j]=b.ID;
                setMark(t+1,i,j,false);
                mfbMap[t+1][i][j]=1;
		
                b.dir[t+1]=b.td;
                b.CoX[t+1]=b.tx;
                b.CoY[t+1]=b.ty;
                clearRest(t+1,i,j);
                bot.order[t]=2;
                System.out.printf("\nSetting %d to 2\n", t+1);
		return t+1;
	}
	public int dropPOD(ROT b,int t,int i,int j)throws NullPointerException{
                
                this.fdMap=Amap.fdMap;
		this.fpMap=Amap.fpMap;
		this.fbMap=Amap.fbMap;
		this.ffMap=Amap.ffMap;
                this.mfbMap=b.mfbMap;
                this.mfdMap=b.mfdMap;
                if (ffMap[t+1][i][j]==0){throw new NullPointerException("Well, someone took this place already");}
		bot=b;
		bot.state=0;
                fbMap[t+1][i][j]=b.ID;
                setMark(t+1,i,j,false);
                mfbMap[t+1][i][j]=1;
		
                b.dir[t+1]=b.td;
                b.CoX[t+1]=b.tx;
                b.CoY[t+1]=b.ty;
		placeRest(t+1,i,j);
                bot.order[t]=3;
		return t+1;
	}


//down
	private void clearRest(int t, int i, int j){
		bot.podhold=fpMap[t][i][j];
                System.out.println(fpMap[t][i][j]);
		if (bot.podhold==0)  {System.out.println("Error no POD is here");System.exit(0);}
		for (int tt=t;tt<bot.maxsize;tt++){
			fpMap[tt][i][j]=0;
		}
	}
//down	
	public void placeRest(int t,int i,int j){
		for (int tt=t;tt<Amap.currtime;tt++){
                        if (fpMap[tt][i][j]==0)
                        {fpMap[tt][i][j]=bot.podhold;}
                        else{System.out.println("Logical Error! Please Reconsider Plans!!");
                        JOptionPane.showMessageDialog(null, "Logical Error! Please Reconsider Plans!!");
                        }
		}
	}
	
//clear

//clear
	private int findTrace(int t,int i,int j){
		if ((checkFringe(t,i,j))&&(aMap[t][i][j]!=0)){
			return aMap[t][i][j];
		}
		return aMap.length*2;
	}
//down


//questioned
//might be gone
	private void setRestMark(int t,int i,int j){
		ffMap[t][i][j]=0;
	}

//need to combine this
	private int toNumber(int t,int i,int j){
		if (dMap[t][i][j]==1){
			return 1;
		}
		if (dMap[t][i][j]==2){
			return 2;
		}
		if (dMap[t][i][j]==4){
			return 3;
		}
		if (dMap[t][i][j]==8){
			return 4;
		}
		return dMap[t][i][j]+4;
	}
//need to fix this as well
	private char toOutput(int input){
                if (input==1){
                        return '^';
                }
                if (input==2){
                        return '>';
                }
                if (input==3){
                        return 'v';
                }
                if (input==4){
                        return '<';
                }
		return ((char)(input+48));
	}

//really important code here
	private int quickFix(int t,int i, int j){
		if (checkFringe(t+1,i-1,j))
		if (mfdMap[t+1][i-1][j]>0)
		return 1;
		if (checkFringe(t+1,i+1,j))
		if (mfdMap[t+1][i+1][j]>0) 
                return 3;
		if (checkFringe(t+1,i,j-1))
		if (mfdMap[t+1][i][j-1]>0) 
                return 4;
		if (checkFringe(t+1,i,j+1))
		if (mfdMap[t+1][i][j+1]>0) 
                return 2;
 		return mfdMap[t][i][j];
		
	}

//
	public void setRest(int t,int i,int j){
		
		
                        setRestMark(t,i,j);
                        fdMap[t][i][j]=3;
                        mfdMap[t][i][j]=3;
                        fbMap[t][i][j]=bot.ID;
                

	}




//way too redundent
//need to fix here
	public void recMap(int stt,int t,int i,int j){
		int b,b1;		
		int f=1;


		//setRest(t,i,j);

		while ((t>stt)&&(aMap[t][i][j]>0)){
			if (fdMap[t][i][j]>0){t--;continue;}
			b=aMap[t][i][j];
			setMark(t,i,j,true);
			fdMap[t][i][j]=toNumber(t,i,j);
			mfdMap[t][i][j]=fdMap[t][i][j];
			if (mfdMap[t][i][j]>4){
				fdMap[t][i][j]=quickFix(t,i,j);
				mfdMap[t][i][j]=fdMap[t][i][j];
			}
                        b1=findTrace(t-1,i,j);
                        if ((b-b1==1)){
				fdMap[t][i][j]=ld;
				mfdMap[t][i][j]=fdMap[t][i][j];
				ld=(ld+1)%4;
				f=1;
                                t--;
                                continue;
                        }

	
			b1=findTrace(t-1,i-1,j);
			if ((b-b1==2)&&((dMap[t-1][i-1][j]&4)>0)){
				if (f==1){f=0;fdMap[t][i][j]=3;mfdMap[t][i][j]=fdMap[t][i][j];}
				
				ld=3;
                                t--;
				i--;
                                continue;
                        }
                        b1=findTrace(t-1,i+1,j);
			if ((b-b1==2)&&((dMap[t-1][i+1][j]&1)>0)){
				if (f==1){f=0;fdMap[t][i][j]=1;mfdMap[t][i][j]=fdMap[t][i][j];}
				ld=1;
                                t--;
				i++;
                                continue;
                        }
			b1=findTrace(t-1,i,j-1);
                        if ((b-b1==2)&&((dMap[t-1][i][j-1]&2)>0)){
				if (f==1){f=0;fdMap[t][i][j]=2;mfdMap[t][i][j]=fdMap[t][i][j];}
				ld=2;
                                t--;
				j--;
                                continue;
                        }
			b1=findTrace(t-1,i,j+1);
			if ((b-b1==2)&&((dMap[t-1][i][j+1]&8)>0)){
				if (f==1){f=0;fdMap[t][i][j]=4;mfdMap[t][i][j]=fdMap[t][i][j];}
				ld=4;
                                t--;
				j++;
                                continue;
                        }
			if (mfdMap[t][i][j]>4){
        	                fdMap[t][i][j]=quickFix(t,i,j);
				mfdMap[t][i][j]=fdMap[t][i][j];

	                }
		setMark(t,i,j,true);
                fdMap[t][i][j]=dMap[t][i][j];
                mfdMap[t][i][j]=fdMap[t][i][j];
                if (mfdMap[t][i][j]>4){
                        fdMap[t][i][j]=quickFix(t,i,j);
                        mfdMap[t][i][j]=fdMap[t][i][j];
                }
			return;
		}	
		setMark(t,i,j,true);
		fdMap[t][i][j]=dMap[t][i][j];
		mfdMap[t][i][j]=fdMap[t][i][j];
	        if (mfdMap[t][i][j]>4){
                	fdMap[t][i][j]=quickFix(t,i,j);
			mfdMap[t][i][j]=fdMap[t][i][j];
             	}
	}



	private boolean checkFringe(int t,int i,int j){
		return (t>=0)&&(t<aMap.length)&&(i>=0)&&(j>=0)&&(i<iLength)&&(j<jLength);
	}

//clear
	private boolean checkRule(int t,int i,int j){
		if (checkFringe(t,i,j))
		{
			//colision check
			if (ffMap[t][i][j]==0) return false;
			if ((bot.state==1)&&(fpMap[t][i][j]!=0)) return false;	
			if ((fpMap[t][i][j]==0)&&((fbMap[t][i][j]==0)||(fbMap[t][i][j]==bot.ID))) return true;			
			if ((bot.state==0)&&((fbMap[t][i][j]==0)||(fbMap[t][i][j]==bot.ID))) return true;
		}
		return false;

	}	
//Clear

	private boolean checkColision(int t,int i1,int j1,int i2,int j2){
		if (checkRule(t,i2,j2)||checkRule(t+1,i1,j1)){
			return true;
		}
		return false;
	}


//fixed
	private boolean checkMap(int t,int i1,int j1,int i2,int j2,int nextDir,int exr){
                int tmp=0;
                if ((i1!=i2)||(j1!=j2)){
				tmp=aMap[t][i1][j1]+distcost(abs(i2-i1+j2-j1));
			}
			else{
				tmp=aMap[t][i1][j1]+exr;	
                                if (checkColision(t,i1,j1,i2,j2)) {		
                                if (checkRule(t+1,i2,j2)){
                                aMap[t+1][i2][j2]=tmp;
                                //System.out.printf("Something is wrong here %d %d %d \n",t+1,i2,j2);
                                this.tile[t+1][i2][j2]=new Tile();
                                this.tile[t+1][i2][j2].i=i1;
                                this.tile[t+1][i2][j2].j=j1;
                                dMap[t+1][i2][j2]|=nextDir;
                                return true;
                                }}
			}
		//ownMap.printLength();		
		if (checkColision(t,i1,j1,i2,j2)) {		
		if (checkRule(t+1,i2,j2)){
			if(checkShort(t,tmp,i2,j2)){
                            
			aMap[t+1][i2][j2]=tmp;
                        //System.out.printf("Something is wrong here2 %d %d %d \n",t,i1,j1);
                        this.tile[t+1][i2][j2]=new Tile();
			this.tile[t+1][i2][j2].i=i1;
                        this.tile[t+1][i2][j2].j=j1;
			dMap[t+1][i2][j2]|=nextDir;
                        return true;
			}
		}
		}
                
                
                
		return false;
	}
//clear
        private int distcost(int dd){
            if (dd==0) return 0;
            //if (dd==1) return 4;
            //if (dd==2) return 4;
            //else return dd+2;
            return 4;
        }
        private int tCost(){
            return 1;
        }
        private int liftcost(){
            return 4;
        }
        
        
        private boolean turntoEnd(int t,int i, int j,int di,int dj,int dd){
            
            boolean ret=false;
            int count=0;
            int ddi=di;
            int ddj=dj;
            

            if ((t==3)&&(i==8)&&(j==7)){
               // System.out.printf("\nI am the wanted guy here my i and j are %d %d\n btw di and dj are %d %d \n",i+di,j+dj,di,dj);
                //System.out.println(checkRule(t+1,i+di,j+dj));
                }
            
            if (di+dj==0){return false;}
            while ((checkMap(t,i,j,i+di,j+dj,dd,0))&&(count<3)){
                //System.out.printf("\ntruning%d %d\n",i+di,j+dj);
                if ((i+di==dssx)&&(j+dj==dssy)){return true;}
                
                ret=true;
                count++;
                di=di+ddi;
                dj=dj+ddj;
                
                if ((t==3)&&(i==8)&&(j==7)){
                //System.out.printf("\nI am the wanted guy here my i and j are %d %d\n btw di and dj are %d %d \n",i+di,j+dj,di,dj);
                }
            }
            return ret;
        
        }
        
        
        //remove this 
	private boolean turnROT(int t,int i, int j){
            
                if ((t==6)&&(i==2)&&(j==13))
                {System.out.println("bad");
                System.out.printf("\n%d\n", dMap[t][i][j]);}
                boolean ret=false;
		if ((dMap[t][i][j]&1)>0)  {//System.out.println("trun 1");
                                if(turntoEnd(t,i,j,-1,0,1)){ret=true;}}
                if ((dMap[t][i][j]&2)>0)  {//System.out.println("trun 2");
                                if(turntoEnd(t,i,j,0,1,2)){ret=true;}}
                if ((dMap[t][i][j]&4)>0)  {//System.out.println("trun 3");
                                if(turntoEnd(t,i,j,1,0,4)){ret=true;}}
                if ((dMap[t][i][j]&8)>0)  {//System.out.println("trun 4");
                                if(turntoEnd(t,i,j,0,-1,8)){ret=true;}}
		
                //System.out.println("trun 5");
                if (checkMap(t,i,j,i,j,15,2)) {ret=true;             }//(dMap[t][i][j]&15)==15?0:tCost())
                //System.out.println();
                return ret;
	}

	private boolean checkShort(int t,int tmp,int i2,int j2){
		if (aMap[t+1][i2][j2]!=0)
		return ((tmp<aMap[t+1][i2][j2]));
		return true;
	}
        
	/* class constructor
	Create every robot with it's starting map*/

        
        
        public void printFDMap(int t){
		System.out.println(t);		
                for (int i=0;i<Amap.ilength;++i){
                        for (int j=0;j<Amap.jlength;++j){
                                System.out.print(dMap[t][i][j]);
                                System.out.print("  ");
                        }
                        System.out.println();
                }
                System.out.println();
        }
        public void printFDMap2(int t){
		System.out.println(t);		
                for (int i=0;i<iLength;++i){
                        for (int j=0;j<jLength;++j){
                                System.out.print(ffMap[t][i][j]);
                                System.out.print("  ");
                        }
                        System.out.println();
                }
                System.out.println();
        }
//clear
	public synchronized int planRoute(ROT b,int stx, int sty, int std, int dsx, int dsy, int stt) throws NullPointerException{
		this.bot=b;
		this.traceMapx=Amap.cMap();
		this.traceMapy=Amap.cMap();
		this.dMap=Amap.cMap();
		this.aMap=Amap.cMap();
		this.iLength=Amap.ilength;
		this.jLength=Amap.jlength;
                this.tile=Amap.Ctile();
		
		this.mfdMap=bot.mfdMap;
		this.mfbMap=bot.mfbMap;
		this.fdMap=Amap.fdMap;
		this.fpMap=Amap.fpMap;
		this.fbMap=Amap.fbMap;
		this.ffMap=Amap.ffMap;
                
                dssx=dsx;
                dssy=dsy;
		
		startt=stt;
		fdMap[stt][stx][sty]=std;
		fbMap[stt][stx][sty]=bot.ID;
		//bot.coor[stt].first=stx; bot.coor[stt].second=sty;

	// stx, sty: starting position  std:starting direction dsx,dsy: ending position  stt: starting time
	// Method to calculate shortest path from starting position to ending position from starting time

	
	int t=stt;
        //System.out.printf("The starting position is %d %d %d", stt,stx,sty);
        
	aMap[stt][stx][sty]=1;
	dMap[stt][stx][sty]=todir(std);
        tile[stt][stx][sty]=new Tile();
        tile[stt][stx][sty].i=bot.tx;
        tile[stt][stx][sty].j=bot.ty;
        tile[stt][stx][sty].nd=bot.td;
        setMark(stt,stx,sty,true);
        bot.dir[stt]=std;
        bot.CoX[stt]=stx;
        bot.CoY[stt]=sty;
        
       boolean bflag=false;
	
	while (((ffMap[t+3][dsx][dsy]==0)||(ffMap[t+2][dsx][dsy]==0)||(ffMap[t+1][dsx][dsy]==0))||(aMap[t][dsx][dsy]==0)&&(t+1<aMap.length)){//||(t<Amap.overtime)
            
		for (int i=0;i<iLength;i++){
			for (int j=0;j<jLength;j++){
				if (aMap[t][i][j]!=0){
                                    turnROT(t,i,j);
                                        //System.out.printf("I make a turn in here %d %d %d", t,i,j);
					//if (!turnROT(t,i,j)){if (t==stt){System.out.println("This is bad");dMap[stt][stx][sty]=15; turnROT(t,i,j);bflag=true;}}
				} 
			}
		}
                //printFDMap(t  +1);
		t++;
	}
        //System.out.printf("Current t is %d\n",t);
        Amap.overtime=Amap.overtime<t?t:Amap.overtime;
       
        
        
        
        if (false) 
        for (int i=stt;i<t+1;i++){
                //System.out.println(bot.dir[i]);
		//printAMap(i);
		printFDMap2(i);
		//ownMap.printMap(i);
	}
        //System.out.printf("The current time is %d\n",bot.ID);
        
         recMap2(stt,t,dsx,dsy);
        
         
         
        fdMap[t][dsx][dsy]=bot.dir[t];
        
        
        if (bflag){
        bot.dir[stt+1]=detdir(bot.CoX[stt],bot.CoY[stt],bot.CoX[stt+1],bot.CoY[stt+1]);
        }
        
        bot.td=bot.dir[t];
	bot.tx=dsx;
	bot.ty=dsy;
	bot.time=t;
        return t;
        


        //this.order=bot.order;
	//this.value=bot.value;
	//System.out.println(t);
	//bot.order[0]=1;
	//bot.value[0]=1;

	//int ttt=t;
	//int iii=dsx;
	//int jjj=dsy;	
	//System.out.println();
	//while (ttt>=stt){
	//System.out.println(iii);
	//System.out.println(jjj);
	//iii=traceMapx[ttt][iii][jjj];
	//jjj=traceMapy[ttt][iii][jjj];	
	//ttt--;
	//}
	//System.out.println();



	//recMap(stt,t,dsx,dsy);
	
	
	


	
	
	
        
        
        //return t;
	}

        
        
        
        
	public int todir(int t){
		if (t==3) return (4);
		if (t==4) return (8);
		return t;

	}
/*useless printing function
	public void printPlan(int t){
	
		for (int i=0;i<t+1;i++){
		//ownMap.printMap(i);
		printFDMap(i);
		}
	}
*/
public void recMap2(int sst,int t, int dex,int dey) throws NullPointerException{
    int x=dex;
    int y=dey;
    
    
    int tx=dex;
    int ty=dey;
    int tt=t;
    
    
    
    while (t>sst){
        if (t-1<0) break;
        dex=x;
        dey=y;
        
        
            
        tx=tile[t][x][y].i;
        ty=tile[t][x][y].j;
        
        x=tx;
        y=ty;
        
        bot.CoX[t]=dex;
        bot.CoY[t]=dey;
        bot.dir[t]=tile[t][dex][dey].nd;
        
        
        
        tile[t-1][x][y].ni=dex;
        tile[t-1][x][y].nj=dey;
        tile[t-1][x][y].nd=detdir(x,y,dex,dey);
        t--; 
        
        
        
    }
    for (int ttt=sst;ttt<=tt;ttt++){
        
    if (bot.dir[ttt]==0){
        if (((bot.CoX[ttt+1]-bot.CoX[ttt]+bot.CoY[ttt+1]-bot.CoY[ttt])==0)&&(ttt>sst)){bot.dir[ttt]=bot.dir[ttt-1];}
        else {bot.dir[ttt]=bot.dir[ttt+1];}
        
    }
    //System.out.printf("\nWe are currently at %d  %d  %d  and the direction is %d\n", ttt,bot.CoX[ttt],bot.CoY[ttt],bot.dir[ttt]);
    
    setMark(ttt,bot.CoX[ttt],bot.CoY[ttt],true);
    if (ttt<tt){
    setAllMark(ttt+1,bot.CoX[ttt],bot.CoY[ttt],bot.CoX[ttt+1],bot.CoY[ttt+1]);
    }
    
    
    }
    
        if (bot.dir[tt]==0) {bot.dir[tt]=bot.dir[tt-1];
        setMark(tt,bot.CoX[tt],bot.CoY[tt],true);}
        //System.out.printf("\nWe are currently at %d and the direction is %d\n", tt,bot.dir[tt]);
}
        private void setAllMark(int t,int i,int j,int i2,int j2){
            int i3=i2;
            int j3=j2;
            
            setMark(t,i,j,false);
            setMark(t,i2,j2,false);
            while (j2>j){
            setMark(t,i2,j2,false);
            j2--;
            }
            while (i2>i){
            setMark(t,i2,j2,false);
            i2--;
            }
            while (j2<j){
            setMark(t,i2,j2,false);
            j2++;
            }
            while (i2<i){
            setMark(t,i2,j2,false);
            i2++;
            }
            
            
            
            
        }

	private void setMark(int t,int i,int j,boolean drawpod){
		ffMap[t][i][j]=0;
		fbMap[t][i][j]=bot.ID;
                fdMap[t][i][j]=bot.dir[t];
                
                if (drawpod){
		bot.order[t]=1;
		mfbMap[t][i][j]=1;
		if ((bot.state==1)&&(bot.podhold>0)){
			fpMap[t][i][j]=bot.podhold;
		}}


	}

public int todegree(int d){
if (d==1) return 90;
if (d==2) return 0;
if (d==3) return 270;
if (d==4) return 180;
return 999;
}


public int detdir(int x1,int y1,int x2,int y2){
    if ((y2-y1)>0) return 2; 
    if ((x2-x1)>0) return 3; 
    if ((y1-y2)>0) return 4;
    if ((x1-x2)>0) return 1; 
    return 0;
}
  

}

