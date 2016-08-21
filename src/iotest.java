import path.io.*;
import path.container.*;
//import path.export.*;

public class iotest{
public static void main(String[] args){
	String fp="./path/documents/connectionprotocal.txt";
	FileReader fr=new FileReader(fp);
	fr.readC();
	fp="./path/documents/addressprotocal.txt";
	fr=new FileReader(fp);	
	fr.readF();
}
}
