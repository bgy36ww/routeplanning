package path.io;

import path.container.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.net.URL;
@SuppressWarnings("unchecked")
public class FileReader {
	private Scanner sc;
	private File file;
	public FileReader(String s){
		file=new File(s);
		try{
		sc=new Scanner(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public FileReader(){
	}	
	public void newFile(InputStream s){
		
		try{
		sc=new Scanner(s);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	

	public void newFile(String s){
		//file.close();		
		sc.close();		
		file=new File(s);
		try{
		sc=new Scanner(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void scanM(){
		Amap.ilength=sc.nextInt();
		Amap.jlength=sc.nextInt();
		Amap.iMap=new int[Amap.ilength][Amap.jlength];
		Amap.fdMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];
		Amap.fpMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];
		Amap.fbMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];
		Amap.ffMap=new int[Amap.maxsize][Amap.ilength][Amap.jlength];

		Amap.tpMap=new int[Amap.ilength][Amap.jlength];
		
		

		for (int i=0;i<Amap.ilength;i++)
			for (int j=0;j<Amap.jlength;j++){
				rmCo();
				Amap.iMap[i][j]=sc.nextInt();
				if (Amap.iMap[i][j]==2){
					Amap.tpMap[i][j]=1;
				}
			}

		for (int t=0;t<Amap.currtime;t++){
			for (int i=0;i<Amap.ilength;i++){
			Amap.ffMap[t][i]=Amap.iMap[i].clone();
			Amap.fpMap[t][i]=Amap.tpMap[i].clone();}
		}
		
		sc.close();		

		
	}


	public Command readC(){
		Command retfile=null;	
		try{
			readf(sc);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retfile;
	}
	public Command readf(Scanner sc){
		Command comm=new Command();
		rmCo();
		int n=sc.nextInt();
		comm.majorCommand=readC(sc,n);
		rmCo();
		n=sc.nextInt();
		comm.actionCommand=readC(sc,n);
		rmCo();
		n=sc.nextInt();
		comm.returnCommand=readC(sc,n);
		rmCo();
		n=sc.nextInt();
		comm.Errors=readC(sc,n);

		return comm;
	}
	public void readF(){
		Format retfile=null;	
		try{
			readf2(sc);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//return (T) retfile;
	}
	public void readf2(Scanner sc){	
		while (sc.hasNextLine()){
		//System.out.println(sc.next());
		if (sc.hasNext("Start")){sc.next();		
		Format.startCode=sc.nextInt(16);}
		//System.out.println(sc.next());
		if (sc.hasNext("End")){sc.next();	
		Format.endCode=sc.nextInt(16);}
		sc.nextLine();
		}
	}
	public void rmCo(){
		while (!sc.hasNextInt()) sc.next();
	}
	public int[] readC(Scanner sc,int n){
		int[] rec=new int[n];
		for (int i=0;i<n;i++){
			rmCo();
			int temp=sc.nextInt();
			//System.out.println(temp);
			rmCo();
			rec[temp]=sc.nextInt();
		}
		return rec;
	}
}
