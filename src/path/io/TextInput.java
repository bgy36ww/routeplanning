package path.io;

import path.container.*;

import java.io.*;
import java.util.*;
public class TextInput{
	static public Mission[] in(String s){
		File file=null;
		Mission[] m=null;
		try{
			file=new File(s);
			Scanner sc=new Scanner(file);
			int n=sc.nextInt();
			m=new Mission[n];
			for (int i=0;i<n;i++){
				 m[i]=new Mission(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
			}
			return m;
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;

	} 



}
