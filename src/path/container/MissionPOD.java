/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.container;

/**
 *
 * @author omelet
 */
public class MissionPOD {
    public int ordernumber;
    public int xposition;
    public int yposition;
    public String missionC;
    public MissionPOD(int o,int x,int y,String mi){
        ordernumber=o;
        xposition=x;
        yposition=y;
        missionC=mi;
    }
    
}
