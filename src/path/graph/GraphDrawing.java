package path.graph;

import path.container.*;
import path.*;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Dimension;



public class GraphDrawing {
	public GraphDrawing(rMap m){
	JFrame window = new JFrame();
	
	window.setTitle("Title");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.pack();
	window.setSize(1200,600);
	window.setVisible(true);
		
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	int w = window.getSize().width;
	int h = window.getSize().height;

	int x = (dim.width - w) / 2;
	int y = (dim.height - h) / 2;

	window.setLocationRelativeTo(null);
	
	drawingComponent dC = new drawingComponent();
	dC.ini(m);
	Graphics g=window.getGraphics();
	try{
		dC.sPaint(g);
	} catch (InterruptedException e){
		e.printStackTrace();		
	}
	window.add(dC);
	}
}
