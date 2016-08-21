package path.container;

public class rMap{
	private int [][][] dMap;
	private int [][][] bMap;
	private int [][][] pMap;
	private int [][] iMap;
	private int time;
	private int size;

	public int getSize(){
		return size;
	}

	public int getTime(){
		return time;
	}
	public int[][][] getDMap(){
		return dMap;	
	}
	public int[][][] getBMap(){
		return bMap;	
	}
	public int[][][] getPMap(){
		return pMap;	
	}
	public int[][] getIMap(){
		return iMap;	
	}
	public rMap(int[][][] d,int[][][] b,int[][][] p,int [][] i,int t){
		dMap=d;
		bMap=b;
		pMap=p;
		iMap=i;
		time=t;
		size=i.length*i[0].length;
	}
}
