/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package path.thread;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.container.ROT;

/**
 *
 * @author omelet
 */
public class BOTfinder implements Runnable{
    
    private ROT bot;
    
    public BOTfinder(ROT r){
        bot=r;
    }
    @Override
    public void run() {
      
        try {
            Thread.sleep(300);
            synchronized(bot){
            bot.getpos();
            }
            } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
