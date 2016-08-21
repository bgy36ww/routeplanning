package path.io;

import path.container.*;


import java.io.*;
import java.lang.*;
import java.util.*;
public class InputOrder{
	public int nb;//number of Robot
 	public POD[] p;
	public int np;//number of PODS to fetch
	public int desx;
	public int desy;
	public int roomsize;
	
        static protected Queue<Task>[] task;
        
	private int take(Scanner sc){
		while (!sc.hasNextInt()) sc.next();
		return sc.nextInt();
	}
        static public synchronized Queue<Task> readTask(int ind){
            return task[ind];
        
        }
        
        static public synchronized Queue<Task> finishTask(int ind){
            return task[ind];
        
        }
        
        
	public InputOrder(String s){
		File file=null;
		try{
			file=new File(s);
			
			Scanner sc=new Scanner(file);
			nb=take(sc);
			
			
			//POD not use here
			np=take(sc);

			p=new POD[np];
                        
                        Amap.bot=new ROT[nb];
			task=new Queue[nb];
                        
			for (int i=0;i<nb;i++){
				int nt=take(sc);
				task[i]=new LinkedList<>();
				for (int j=0;j<nt;j++){
					task[i].add(new Task(take(sc),take(sc),take(sc)));
				}
			}
			
			desx=take(sc);
			desy=take(sc);

						

			roomsize=100;
		}catch(Exception e){
		}
	}
}
