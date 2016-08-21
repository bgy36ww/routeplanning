package path.container;

import java.util.ArrayList;

public class Amap{
        static public int overtime=0;
	static public int maxsize=2500;
	static public int currtime=2500;
	static public int[][] iMap;
        static public int[][] tpMap;
	static public int[][][] fdMap;
	static public int[][][] fpMap;
	static public int[][][] fbMap;
	static public int[][][] ffMap;
	static public int nb;
	static public ROT[] bot;
	static public ArrayList<ROT> botlist;
	static public ArrayList<POD> podlist;
	static public int time;
	static public int ilength;
	static public int jlength;
        static public int sstime;
        private static Amap instance=null; 
	static public int[][][] cMap(){
		return new int[currtime][ilength][jlength];
	}
        static public Tile[][][] Ctile(){
                
                return new Tile[currtime][ilength][jlength];
        }
        private Amap(){botlist=new ArrayList<ROT>();podlist=new ArrayList<POD>();}
        public static synchronized Amap get(){
        if (instance==null){
            instance=new Amap();
            return instance;
        }else {
            return instance;
        }
        }
        
        static public void reset(){
        
            //iMap=new int[ilength][jlength];
            fdMap=new int[maxsize][ilength][jlength];
            fpMap=new int[maxsize][ilength][jlength];
            fbMap=new int[maxsize][ilength][jlength];
            ffMap=new int[maxsize][ilength][jlength];
            tpMap=new int[ilength][jlength];
        
            for (int t=0;t<currtime;t++){
			for (int i=0;i<ilength;i++){
			ffMap[t][i]=iMap[i].clone();
			fpMap[t][i]=tpMap[i].clone();}
		}
            
            
        }
}
