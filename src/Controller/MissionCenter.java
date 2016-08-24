/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Queue;
import java.util.Set;
import path.container.MissionPOD;
import path.container.ROT;

/**
 *
 * @author omelet
 */
public class MissionCenter {
    public Set<ROT> idlebotset;
    public Set<ROT> runningbotset;
    public Queue<MissionPOD> missionholder;
    public MissionCenter(Set<ROT> is,Set<ROT> rs,Queue<MissionPOD> mh){
        idlebotset=is;
        runningbotset=rs;
        missionholder=mh;
    }
    
    public synchronized boolean publishMission(){
        if ((missionholder.isEmpty())||(idlebotset.isEmpty())){
        return true;
        }
        while ((!missionholder.isEmpty())&&(!idlebotset.isEmpty()))
        {
               MissionPOD fp;
               fp=missionholder.remove();
               ROT br=null;
               int brdis=9999;
               for (ROT b:idlebotset){
                   br=b.getdisdiff(fp.xposition, fp.yposition)<brdis?b:br;
                   brdis=br==null?brdis:br.getdisdiff(fp.xposition, fp.yposition);
               }
               System.out.printf("Dispatched %d %d POD to %d %d Bot\n", fp.xposition,fp.yposition,br.locationX,br.locationY);
               
               idlebotset.remove(br);
               runningbotset.add(br);
               br.missionpod=fp;
               br.operatingstages=1;
               br.idle=false;
        }
        return false;
    }
}
