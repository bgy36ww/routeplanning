import java.sql.Timestamp;

import java.util.Date;

import java.io.*;

import java.text.SimpleDateFormat;



public class LogWriter{

	static private File dir;

	static private BufferedWriter output;

	public LogWriter(){

		dir=new File("./LOG");

		dir.mkdir();

		String fileName = new SimpleDateFormat("'Logfile'yyyyMMddhhmmss'.txt'").format(new Date());

		fileName="./LOG/"+fileName;

		dir=new File(fileName);

		try{

		dir.createNewFile();

		output=new BufferedWriter(new FileWriter(dir));

		}

		catch(IOException e){

		e.printStackTrace();		

		}

	

	}
	static public void forcewrite() throws Exception{
		output.close();
		output=new BufferedWriter(new FileWriter(dir));
	}

	static public void write(String s){

		try{

		String timeStamp = new SimpleDateFormat("yyyy'-'MM'-'dd'  'hh':'mm':'ss'.'SSS'\r\n'").format(new Date());

		output.write(timeStamp);

		s=s+"\r\n";

		output.write(s);

		output.write("\r\n");}

		catch ( IOException e ) {

           	e.printStackTrace();

        	}

	}

	static public void close(){

		try{

		if ( output != null ) {

            	output.close();

          	}	

		}

		catch ( IOException e ) {

           	e.printStackTrace();

        	}

	}





}
