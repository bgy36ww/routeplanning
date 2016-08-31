/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Queue;
import java.util.Set;
import path.container.Amap;
import path.container.MissionPOD;
import path.container.ROT;

/**
 *
 * @author omelet
 */
public class MissionCenter {
    public Set<ROT> idlebotset;
    public Queue<ROT> runningbotset;
    public Queue<MissionPOD> missionholder;
    public MissionCenter(Set<ROT> is,Queue<ROT> rs,Queue<MissionPOD> mh){
        idlebotset=is;
        runningbotset=rs;
        missionholder=mh;
    }
    
    public boolean publishMission(){
        synchronized(runningbotset){
        if ((missionholder.isEmpty())||(idlebotset.isEmpty())){
        return true;
        }
        while ((!missionholder.isEmpty())&&(!idlebotset.isEmpty()))
        {
               System.out.println("Dispatching mission");
               MissionPOD fp;
               fp=missionholder.remove();
               ROT br=null;
               int brdis=9999;
               for (ROT b:idlebotset){
                   br=b.getdisdiff(fp.xposition, fp.yposition)<brdis?b:br;
                   brdis=br==null?brdis:br.getdisdiff(fp.xposition, fp.yposition);
               }
               synchronized (br){
               System.out.printf("Dispatched %d %d POD to %d %d Bot\n", fp.xposition,fp.yposition,br.locationX,br.locationY);
               
               Amap.iMap[br.locationX][br.locationY]=1;
               br.missionpod=fp;
               br.operatingstages=1;
               
               
               //br.toMission();
               synchronized (idlebotset){
               idlebotset.remove(br);}
               
               synchronized (runningbotset){
               runningbotset.add(br);}
               Amap.outqueue.bqueue.add(br);
               }
        }
        return false;
        }
    }
}
