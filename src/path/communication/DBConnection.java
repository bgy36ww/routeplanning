/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.sql.SQLException;

//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.container.MissionPOD;
import path.container.ROT;

/**
 *
 * @author wei
 */

public class DBConnection {
 public Connection conn = null;
 public PreparedStatement pr=null;
 public ResultSet rs=null;
 public PreparedStatement pr2=null;
 public ResultSet rs2=null;
 public DBConnection(){
            try{
            //mysql database connectivity
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn =DriverManager.getConnection("jdbc:mysql://120.25.86.124:3306/GEEKPRO","liftian","LIFTIANSINC007");
            
            //Class.forName(driverName).newInstance();
            //conn = DriverManager.getConnection (url, dbuserName, dbpassword);
            System.out.println ("Database connection established");
            System.out.println("DONE");
            }catch(Exception e){e.printStackTrace();}
 
 }
 public MissionPOD getPOD() {
     MissionPOD mp=null;
     try {
        pr=conn.prepareStatement("Call GEEKPRO.P_GetNextPod();");
        rs = null;
        rs=pr.executeQuery();
        while (rs.next()) {  
            if (rs.getString("PODlocID")!=null){break;}
            //return;
            synchronized(this){
            rs=pr.executeQuery();}
            Thread.sleep(100);
        }
        System.out.println(rs.getString("PODlocID"));
        synchronized(this){
        rs.beforeFirst();
        
        int OrderID=0;
        String PODID="";
        String[] tp=null; 
        int PODIDx=0;
        int PODIDy=0;
        while (rs.next()) {
            OrderID=rs.getInt("p_OrderID"); 
            PODID =rs.getString("PODlocID");
            tp=PODID.split("#");
            PODIDx=Integer.parseInt(tp[0]);
            PODIDy=Integer.parseInt(tp[1]);
            System.out.printf("The OrderID is %s + The PIDID is %s \n", OrderID, PODID);
        }
        mp=new MissionPOD(OrderID,PODIDx,PODIDy,PODID);
        String st="Call GEEKPRO.P_ConfirmNextPod("+Integer.toString(OrderID)+",'"+PODID+"');";
        System.out.println(st);
        pr=conn.prepareStatement(st);
        pr.executeUpdate();}
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    
     return mp;
 }
 public void finish(ROT bot){
        int OrderID=bot.missionpod.ordernumber;
        String PODID=bot.missionpod.missionC;
        
        try{
        String st="Call GEEKPRO.P_PodArrivePickingArea("+Integer.toString(OrderID)+",'"+PODID+"');";
        System.out.println(st);
        TimeUnit.MILLISECONDS.sleep(100);
        int action=0;
        while (action!=1){
        pr2=conn.prepareStatement(st);
        rs2=pr2.executeQuery();
        while (rs2.next()) {
            action=rs2.getInt("PODAction"); }
        System.out.printf("\nIt is picking %d\n", action);
        System.out.println(action);
        TimeUnit.MILLISECONDS.sleep(100);
        }
        }
        catch(Exception e){e.printStackTrace();}
 }
public void reportback(String PODID){
     try {
         String st="SELECT GEEKPRO.F_PodReturned('"+PODID+"');";
         pr2=conn.prepareStatement(st);
         rs2=pr2.executeQuery();
     } catch (SQLException ex) {
        ex.printStackTrace();
     }
}
}

