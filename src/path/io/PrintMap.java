package path.io;

import path.container.*;

import java.io.*;
public class PrintMap{
	public void print2d(int[][] m){
		for (int i=0;i<m.length;i++){
			for (int j=0;j<m[0].length;j++){
				System.out.print(m[i][j]);
				System.out.print("     ");
			}	
			System.out.println();
		}
	}
	public void print3d(int[][][] m,int time){
		for (int t=0;t<time;t++){
		System.out.println();
		System.out.print(t);
		System.out.println();
		for (int i=0;i<m[t].length;i++){
                        for (int j=0;j<m[t][i].length;j++){
                                System.out.print(m[t][i][j]);
                                System.out.print(" ");
                        }
                        System.out.println();
                }
		}

	}

}
