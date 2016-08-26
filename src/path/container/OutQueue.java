/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.container;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Waver
 */
public class OutQueue {
    public OutQueueLocation[] location;
    public Queue<ROT> bqueue;
    public OutQueue(){
        bqueue=new LinkedList<>();
        location=new OutQueueLocation[10];
        
        location[0]=new OutQueueLocation();
        location[0].x=0;
        location[0].y=7;
        
        location[1]=new OutQueueLocation();
        location[1].x=1;
        location[1].y=6;
        
        location[2]=new OutQueueLocation();
        location[2].x=2;
        location[2].y=6;
        
        location[3]=new OutQueueLocation();
        location[3].x=3;
        location[3].y=6;
        
        location[4]=new OutQueueLocation();
        location[4].x=4;
        location[4].y=6;
        
        location[5]=new OutQueueLocation();
        location[5].x=4;
        location[5].y=5;
        
        location[6]=new OutQueueLocation();
        location[6].x=4;
        location[6].y=4;
        
        location[7]=new OutQueueLocation();
        location[7].x=4;
        location[7].y=3;
        
        location[8]=new OutQueueLocation();
        location[8].x=4;
        location[8].y=2;
        
        location[9]=new OutQueueLocation();
        location[9].x=4;
        location[9].y=1;
    }
}
