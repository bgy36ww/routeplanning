package path.communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import path.algorithm.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.*;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.*;
import path.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class ComServer {
	private ServerSocket listener;
	private Socket socket;
	private DataInputStream input;
	private LogWriter logwriter;
	private int id;
	public byte[] state;
	public ComServer(Socket s) {
	socket = s;
        try{
	socket.setSoTimeout(1000);
	logwriter=new LogWriter();
	input=new DataInputStream(socket.getInputStream());
        }catch(Exception e){e.printStackTrace();}

	}
	public byte[] write(byte[] data,int t) throws InterruptedException {
	boolean retry=true;
	//System.out.println("Hello");
        byte[] ret=null;
        while (retry){
        
        retry=false;
        try{
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        out.write(data);
        
        
            ret=get();	
            TimeUnit.MILLISECONDS.sleep(t);
	}
	
        catch(IOException ee){
            retry=true;
        }
	
        }
        return ret;
        }
        

	public void close() {
	try{
	//listener.close();
	socket.close();
	logwriter.close();}
	catch(Exception e){e.printStackTrace();}	
	}

	public byte[] get() throws IOException{
	ArrayList<Byte> list=new ArrayList<Byte>();
	int n=0;
	byte bt=input.readByte();
	list.add(bt);
        
        //debug
	StringBuilder sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
        
        //log recording
//	logwriter.write(sb.toString()+" ");
	//System.out.println(sb.toString()+" ");
	
        //error checking
        while ((int)(bt&0xff)-252!=0){
        
	bt=input.readByte();
	list.add(bt);
	sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
//	logwriter.write(sb.toString()+" ");
	//System.out.println(sb.toString()+" ");
	}
        
        
	if (((int)(bt&0xff)-252)==0){
	bt=input.readByte();
	list.add(bt);
	n=(int)bt;
	sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
//	logwriter.write(sb.toString()+" ");
	//System.out.println(n-2);
	}
        
	for (int i=0;i<n-2;i++){
	bt=input.readByte();
	list.add(bt);	
	sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
//	logwriter.write(sb.toString()+" ");
	//System.out.println(sb.toString()+" ");
	}
        
	state= toArray(list);
	return state;
	}	
        
	private byte[] toArray(ArrayList<Byte> inarray) {
	byte[] ret = new byte[inarray.size()];
    	Iterator<Byte> iterator = inarray.iterator();
    	for (int i = 0; i < ret.length; i++)
    	{
       		ret[i] = iterator.next().byteValue();
    	}
    	return ret;
  	}
}
