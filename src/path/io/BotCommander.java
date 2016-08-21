package path.io;

import java.sql.Timestamp;

import java.util.Date;

import java.io.*;
import java.lang.*;
import java.util.*;

import path.container.*;

public class BotCommander{
	private int take(Scanner sc){
	while (!sc.hasNextInt()) sc.next();
	return sc.nextInt();
	}

	static private File dir;
	static private BufferedWriter output;
	public BotCommander(String s){
		dir=new File(s);
	}
	public void close(){try{output.close();}catch(IOException e){e.printStackTrace();}}
	public Amap read(){
		try{
		Scanner sc=new Scanner(dir);
		Amap amap=Amap.get();
		amap.ilength=take(sc);
		amap.jlength=take(sc);
		amap.time=take(sc);
		amap.iMap=readMap(sc);
		amap.nb=take(sc);
		amap.bot=new ROT[amap.nb];
		for (int n=0;n<amap.nb;n++){
			amap.bot[n]=new ROT(take(sc));
			System.out.println(amap.bot[n].ID);
			amap.bot[n].coor=readTime(sc,amap.bot[n]);	
			amap.bot[n].mfdMap=readbMap(sc);
		}
		amap.fdMap=readbMap(sc);
		amap.fpMap=readbMap(sc);
		amap.fbMap=readbMap(sc);
				
		System.out.println(take(sc));		
		
		sc.close();
		return amap;

		}catch(IOException e){e.printStackTrace();}
		return null;
	}
	public Dual[] readTime(Scanner sc, ROT bot)throws IOException{
		int time=take(sc);
		bot.time=time;
		Dual[] ret=new Dual[time];
		
		for (int t=0;t<time;t++){
			ret[t]=new Dual();
			ret[t].first=take(sc);
			ret[t].second=take(sc);
		}
		return ret;
	}
	public void writeTime(Dual[] coor,int[] order, int[] dir,int time) throws IOException{
		String s="  "+Integer.toString(time)+"\r\n";
		output.write(s);
		for (int t=0;t<time;t++){
			s=" "+Integer.toString(coor[t].first)+" "+Integer.toString(coor[t].second)+" ";
			output.write(s);
			s=Integer.toString(order[t])+" ";
			output.write(s);
			s=Integer.toString(dir[t])+" ";
			output.write(s);
			output.write("\r\n");
		}
		
	}
	

	public int[][] readMap(Scanner sc){

		int [][] ret=new int [Amap.ilength][Amap.jlength];
		for (int i=0;i<Amap.ilength;i++){
		for (int j=0;j<Amap.jlength;j++){
			ret[i][j]=take(sc);		
		}
		}
		return ret;
	}
	public int[][][] readbMap(Scanner sc){
		int time=take(sc);
		int[][][] ret=new int [time+2][][];
		for (int t=0;t<time;t++){
			take(sc);
			ret[t]=readMap(sc);		
		}
		ret[time]=new int[Amap.ilength][Amap.jlength];
		return ret;
	}


	public void write(){
		try{
		dir.createNewFile();
		output=new BufferedWriter(new FileWriter(dir));
		}
		catch(IOException e){
		e.printStackTrace();		
		}

		String s=Integer.toString(Amap.ilength)+" "+Integer.toString(Amap.jlength)+" "+Integer.toString(Amap.time)+"\r\n";
		try{
		output.write(s);
		writeMap(Amap.iMap);
		s=Integer.toString(Amap.bot.length)+"\r\n";
		output.write(s);
		for (int nb=0;nb<Amap.bot.length;nb++){
		s=Integer.toString(Amap.bot[nb].ID)+"\r\n";
		output.write(s);
		
		writeTime(Amap.bot[nb].coor,Amap.bot[nb].order,Amap.bot[nb].dir,Amap.bot[nb].time);
		writebMap(Amap.bot[nb].mfdMap,Amap.bot[nb].time);
		}
		writebMap(Amap.fdMap,Amap.time);
		writebMap(Amap.fpMap,Amap.time);
		writebMap(Amap.fbMap,Amap.time);
		
		output.write(" 400 ");
		
		}catch(IOException e){e.printStackTrace();}
	}
	public void writeMap(int[][] map){
		try{
		for (int i=0;i<map.length;i++){
		for (int j=0;j<map[i].length;j++){
		String s=Integer.toString(map[i][j])+" ";
		output.write(s);
		}
		output.write("\r\n");		
		}
		}catch(IOException e){e.printStackTrace();}
			
	}
	public void writebMap(int[][][] map,int time){
		try{
		String s=Integer.toString(time)+"\r\n";
		output.write(s);
		for (int t=0;t<time;t++){
		s=Integer.toString(t)+"\r\n";
		output.write(s);
		writeMap(map[t]);		
		}
		}catch(IOException e){e.printStackTrace();}
	}
}
