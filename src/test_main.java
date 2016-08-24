import Controller.MissionCenter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import path.*;
import path.io.*;
import path.graph.*;
import path.container.*;
import path.thread.MissionDispatcher;
public class test_main{
	
	public static void main(String[] args){
            Set<ROT> ir=new HashSet();
            ir.add(new ROT(0,0));
            ir.add(new ROT(2,4));
            ir.add(new ROT(7,8));
            ir.add(new ROT(5,5));
            Set<ROT> rr=new HashSet();
            Queue<MissionPOD> mq=new LinkedList<>();
            mq.add(new MissionPOD(1,4,5));
            mq.add(new MissionPOD(1,8,0));
            mq.add(new MissionPOD(1,9,9));
            mq.add(new MissionPOD(1,9,9));
            mq.add(new MissionPOD(1,9,9));
            
            MissionCenter mc=new MissionCenter(ir,rr,mq);
            
            MissionDispatcher md=new MissionDispatcher(mc);
            
            md.run();
            
            System.out.print(rr.isEmpty());
            
		

	}

}
