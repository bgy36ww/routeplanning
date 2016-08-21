package path.graph;

import path.container.*;
//import path.export.*;
import path.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Rectangle;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class drawingComponent extends JComponent{
	JFrame window;
	private int buff=100;
	public void sPaint(Graphics g) throws InterruptedException {
			drawIniMap((Graphics2D)g);
			drawShellMap((Graphics2D)g,0);
			drawRobot((Graphics2D)g,0);
			TimeUnit.SECONDS.sleep(1);
			repaint();

			//Connector connector=new Connector();



		for (int t=0; t<mtime; t++){
			//connector.fakeTime=t;
			//connector.check();
			//connector.connect(connector.detOrder(t,Demo.r),Demo.r.value[t]);
			drawIniMap((Graphics2D)g);
			drawShellMap((Graphics2D)g,t);
			drawRobot((Graphics2D)g,t);
			TimeUnit.SECONDS.sleep(1);
			repaint();


		}

		

	}
	

	
	public int ini(rMap mm){
		iniMap = mm.getIMap();
		dirMap = mm.getDMap();
		robotMap = mm.getBMap();
		shellMap = mm.getPMap();
		mtime=mm.getTime();
		return 1;
	}
	
	private int mtime;
	private	int [][] iniMap;
	private	int [][][] dirMap;
	private	int [][][] robotMap;
	private	int [][][] shellMap;
		
	private void drawShellMap(Graphics2D g2d,int sTime){
		Graphics2D g2 = (Graphics2D) g2d;
		int r,l;
		r = shellMap[0].length;
		l = shellMap[0][0].length;
		for (int i = 0; i<r; i++)
			for (int i2 = 0; i2<l; i2++)
			{	

				if (shellMap[sTime][i][i2] != 0) 
				{
						Rectangle ib1 = new Rectangle(i2*50+5, i*50+5+buff, 35, 35);
						g2.draw(ib1);
						g2.setColor(Color.ORANGE);
						g2.fill(ib1);
				} 
			}
		}

		

	private void drawIniMap(Graphics2D g2d){
		Graphics2D g2 = (Graphics2D) g2d;
		int t,tt;
		t = iniMap.length;
		tt = iniMap[0].length;
		for (int i = 0; i<t; i++)
			for (int i2 = 0; i2<tt; i2++)
			{	

				Rectangle ib1 = new Rectangle(i2*50+5, i*50+5+buff, 45, 45);
				g2.draw(ib1);
				switch(iniMap[i][i2]){
				case 0:	
					g2.setColor(Color.pink);
					g2.fill(ib1);
					break;
				case 1:
					g2.setColor(Color.yellow);
					g2.fill(ib1);
					break;
				case 2:
					g2.setColor(Color.black);
					g2.fill(ib1);
					break;
				case 3:
					g2.setColor(Color.CYAN);
					g2.fill(ib1);
					break;
				case 4:
					g2.setColor(Color.blue);
					g2.fill(ib1);
					break;
				}
			}
	}
		//robot
		
	private void drawRobot(Graphics2D g2d,int sTime){	
		Graphics2D g2 = (Graphics2D) g2d;
		int r,rr;
		r = robotMap[0].length;
		rr = robotMap[0][0].length;
		for (int i = 0; i<r; i++)
			for (int i2 = 0; i2<rr; i2++)
				{
				if (robotMap[sTime][i][i2] > 0){
							switch (dirMap[sTime][i][i2]){
								case 5:
                                                                    int krn = i;
                                                                    int krn2 = i2;
                                                                    Rectangle krb3 = new Rectangle(krn2*50+15, krn*50+20+buff, 25, 20);
                                                                    g2.draw(krb3);
                                                                    
                                                                    
                                                                    g2.setColor(Color.BLUE);
                                                                    
                                                                    
                                                                    g2.fill(krb3);
                                                                    break;

								case 1:
								    int rn = i;
								    int rn2 = i2;
								    Rectangle rb1 = new Rectangle(rn2*50+10, rn*50+10+buff, 5, 30);
								    Rectangle rb2 = new Rectangle(rn2*50+40, rn*50+10+buff, 5, 30);
								    Rectangle rb3 = new Rectangle(rn2*50+15, rn*50+20+buff, 25, 20);
								    g2.draw(rb1);
								    g2.draw(rb2);
								    g2.draw(rb3);
								    g2.setColor(Color.GREEN);
								    g2.fill(rb1);
								    g2.fill(rb2);
								    g2.fill(rb3);
								    break;
								case 4:
									 int rrn = i;
									 int rrn2 = i2;
									 Rectangle rrb1 = new Rectangle(rrn2*50+10, rrn*50+10+buff, 30, 5);
									 Rectangle rrb2 = new Rectangle(rrn2*50+10, rrn*50+40+buff, 30, 5);
									 Rectangle rrb3 = new Rectangle(rrn2*50+20, rrn*50+15+buff, 20, 25);
									 g2.draw(rrb1);
									 g2.draw(rrb2);
									 g2.draw(rrb3);
									 g2.setColor(Color.GREEN);
									 g2.fill(rrb1);
									 g2.fill(rrb2);
									 g2.fill(rrb3);
									 break;
								case 3:
								    int rrrn = i;
								    int rrrn2 = i2;
								    Rectangle rrrb1 = new Rectangle(rrrn2*50+10, rrrn*50+10+buff, 5, 30);
								    Rectangle rrrb2 = new Rectangle(rrrn2*50+40, rrrn*50+10+buff, 5, 30);
								    Rectangle rrrb3 = new Rectangle(rrrn2*50+15, rrrn*50+10+buff, 25, 20);
								    g2.draw(rrrb1);
								    g2.draw(rrrb2);
								    g2.draw(rrrb3);
								    g2.setColor(Color.GREEN);
								    g2.fill(rrrb1);
								    g2.fill(rrrb2);
								    g2.fill(rrrb3);
								    break;
								case 2:
								    int rrrrn = i;
								    int rrrrn2 = i2;
								    Rectangle rrrrb1 = new Rectangle(rrrrn2*50+10, rrrrn*50+10+buff, 30, 5);
								    Rectangle rrrrb2 = new Rectangle(rrrrn2*50+10, rrrrn*50+40+buff, 30, 5);
								    Rectangle rrrrb3 = new Rectangle(rrrrn2*50+10, rrrrn*50+15+buff, 20, 25);
								    g2.draw(rrrrb1);
								    g2.draw(rrrrb2);
								    g2.draw(rrrrb3);
								    g2.setColor(Color.GREEN);
								    g2.fill(rrrrb1);
								    g2.fill(rrrrb2);
								    g2.fill(rrrrb3);
								    break;
							}
                                                        String robNumb = String.valueOf(robotMap[sTime][i][i2]);
                                                        g2.setColor(Color.black);
                                                        g2.drawString(robNumb,i2*50+20, i*50+50+buff);
	
		
					}
				}
		//public void paintComponent(Graphics g){
		//	Graphics2D g2 = (Graphics2D) g;
		//	int[][] aa = {{1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,0,0,0,0},
		//				  {1,2,2,2,2,2,4,2,2,2,2,2,1,1,1,1,0,0,0,0},
		//				  {1,2,2,2,2,2,4,2,2,2,2,2,1,1,1,1,0,0,0,0},
		//				  {1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,1},
		//				  {1,2,2,2,2,2,4,2,2,2,2,2,1,1,1,1,3,3,3,3},
		//				  {1,2,2,2,2,2,4,2,2,2,2,2,1,1,1,1,3,3,3,3},
		//				  {1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,0,0,0,0},
		//				  {1,2,2,2,2,2,4,2,2,2,2,2,1,1,1,1,0,0,0,0},
		//				  {1,2,2,2,2,2,4,2,2,2,2,2,1,1,1,1,0,0,0,0},
		//				  {1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,0,0,0,0},
		//				  };
			
			//map	
		//}
		
		
	}	

}

