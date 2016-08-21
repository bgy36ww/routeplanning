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

/**
 *
 * @author wei
 */

public class DBConnection {
 public Connection conn = null;
 public void getconnect() {
     
     
     
     
     try {
            
         

            //mysql database connectivity
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn =DriverManager.getConnection("jdbc:mysql://120.25.86.124:3306/GEEKPRO","liftian","LIFTIANSINC007");
            
            //Class.forName(driverName).newInstance();
            //conn = DriverManager.getConnection (url, dbuserName, dbpassword);
            System.out.println ("Database connection established");
            System.out.println("DONE");
        
        
        
        
        
        
        
        PreparedStatement pr=conn.prepareStatement("Call GEEKPRO.P_GetNextPod();");
        ResultSet rs = null;
        rs=pr.executeQuery();
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
        String st="Call GEEKPRO.P_ConfirmNextPod("+Integer.toString(OrderID)+",'"+PODID+"');";
        
        System.out.println(st);
        
        pr=conn.prepareStatement(st);
        pr.executeUpdate();
        st="Call GEEKPRO.P_PodArrivePickingArea("+Integer.toString(OrderID)+",'"+PODID+"');";
        System.out.println(st);
        TimeUnit.MILLISECONDS.sleep(100);
        int action=0;
        while (action!=1){
        pr=conn.prepareStatement(st);
        rs=pr.executeQuery();
        while (rs.next()) {
            action=rs.getInt("PODAction"); }
        System.out.println(action);
        TimeUnit.MILLISECONDS.sleep(100);
        }
        
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
