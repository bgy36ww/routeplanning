package path.container;
import java.io.*;
import java.util.*;
public class Wmap{
	public String getMap(int t,int i,int j){
	//get the map
		return holdmap.get(t).get(i).get(j);
	}
	public void printLength(){
		System.out.print("the iLength for Wmap is:");
		System.out.println(holdmap.get(0).size());
		System.out.print("the jLength for Wmap is:");
		System.out.println(holdmap.get(0).get(0).size());
		System.out.print("the tLength for Wmap is:");
                System.out.println(holdmap.size());

	}
	public void setMap(int t,int i,int j,String s){
	//set map to specific value
		holdmap.get(t).get(i).set(j,s);
	}
	private Vector<Vector<Vector<String>>> holdmap; 
	
	public Wmap(Vector<Vector<Vector<String>>> m){
	//class constructor 
		holdmap=m;
	}

	public boolean validate(int t,int i,int j,int s){
	//use to validate if a placement is valid
		if (holdmap.get(t).get(i).get(j).equals("0")) return false;
		if ((s==1)&&(holdmap.get(t).get(i).get(j).equals("2"))) return false;
		setMap(t,i,j,"0");
		return true;
	}	
	
	public void clearRest(int t,int i, int j){
	//use to clear all of the obstacles from this point of time
		for (int tt=t;tt<ROT.maxsize;tt++)
		setMap(tt,i,j,"1");
	}
	
        public void printMap(int t){
	//debug printing function
                for (int i=0;i<holdmap.get(t).size();++i){
                        for (int j=0;j<holdmap.get(t).get(i).size();++j){
                                System.out.print(holdmap.get(t).get(i).get(j));
                       		System.out.print("  ");
			 }
                        System.out.println();
                }
                System.out.println();
        }

	public int[][] ciMap(){
		//int tn=holdmap.size();
                int in=holdmap.get(0).size();
                int jn=holdmap.get(0).get(0).size();
		int [][] rt=new int[in][jn];
		//for (int t=0;t<tn;t++){
			for (int i=0;i<in;i++){
				for (int j=0;j<jn;j++){
					rt[i][j]=Integer.parseInt(holdmap.get(0).get(i).get(j));
				}
			}
		
		return rt;
	}	
	
	public int[][][] cMap(){
	//use to create empty map
		int t=holdmap.size();
		int i=holdmap.get(0).size();
		int j=holdmap.get(0).get(0).size();
		return new int[t][i][j];
	}
}
