package path.converter;

import path.container.*;
import java.nio.*;
import path.algorithm.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
public class ConCom{
	private static int index=0;

	static private byte[] toArray(ArrayList<Byte> inarray) {
	byte[] ret = new byte[inarray.size()];
    	Iterator<Byte> iterator = inarray.iterator();
    	for (int i = 0; i < ret.length; i++)
    	{
       		ret[i] = iterator.next().byteValue();
    	}
    	return ret;
  	}


	static public byte[] toSommand(int ind,int id,int mcom,int acom,int x,int y,int dir){
		ArrayList<Byte> list=new ArrayList<Byte>();
		list.add((byte)0xFC);
		list.add((byte)29);
		list.add((byte) ((id & 0xff00) >>> 8));
		list.add((byte) ((id & 0x00ff)));
		list.add((byte)0);
		list.add((byte)0);
		list.add((byte)mcom);
		list.add((byte) ((ind & 0xff00) >>> 8));
		list.add((byte) ((ind & 0x00ff)));
		list.add((byte)1);
		list.add((byte)acom);
		list.add((byte) ((ind & 0xff00) >>> 8));
		list.add((byte) ((ind & 0x00ff)));
		list.add((byte) (((x*1000) & 0xff000000) >>> 24));
		list.add((byte) (((x*1000) & 0x00ff0000) >>> 16));
		list.add((byte) (((x*1000) & 0x0000ff00) >>> 8));
		list.add((byte) (((x*1000) & 0x000000ff)));
		list.add((byte) (((y*1000) & 0xff000000) >>> 24));
		list.add((byte) (((y*1000) & 0x00ff0000) >>> 16));
		list.add((byte) (((y*1000) & 0x0000ff00) >>> 8));
		list.add((byte) (((y*1000) & 0x000000ff)));
		list.add((byte) (((dir) & 0x0000ff00) >>> 8));
		list.add((byte) (((dir) & 0x000000ff)));
		//list.add((byte) (((dir) & 0x0000ff00) >>> 8));
		//list.add((byte) (((dir) & 0x000000ff)));
		list.add((byte)0);
                list.add((byte)0);
                list.add((byte)0);
		byte[] fid=new byte[4];
		CRC16 crc=new CRC16();
		fid=crc.process(toArray(list));
		list.add((byte)fid[0]);
		list.add((byte)fid[1]);
		list.add((byte)0x5A);
		return toArray(list);
	}

	static public byte[][] toCommand(ROT r){
		byte[][] ret=new byte[r.count][];
		for (int i=0;i<ret.length;i++){
			ret[i]=toSommand(i+1,r.ID,1,r.orders[i].order,r.orders[i].first,r.orders[i].second,r.orders[i].dir);
		}
		return ret;
	}
        public byte[][] manualorder(){
                byte[][] ret=new byte[7][];
                int ii=0;
                
                
                
                

ret[ii]=toSommand(ii+1,12,01,1,1,0,0);ii++;

ret[ii]=toSommand(ii+1,12,01,1,2,0,0);ii++;

ret[ii]=toSommand(ii+1,12,01,1,3,0,0);ii++;

ret[ii]=toSommand(ii+1,12,01,1,3,0,180);ii++;

ret[ii]=toSommand(ii+1,12,01,1,2,0,180);ii++;

ret[ii]=toSommand(ii+1,12,01,1,1,0,180);ii++;

ret[ii]=toSommand(ii+1,12,01,1,0,0,180);ii++;

                
                
                
                
                
                return ret;
        }

	public Dual[] toOrder(ROT r){
		int t=0;
		int oc=0;
		Dual[] ret=new Dual[r.time];
		
		int x=0;
		int y=0;
		int dir=0;
		while (t<r.time-1){
			if (r.order[t]==1){
			ret[oc]=getOrder(t,r);
			x=ret[oc].first;
			y=ret[oc].second;
			dir=ret[oc].dir;			
			}
			if (r.order[t]==4){while((r.order[t]==4)&&(t<r.time-1)){t++;}
							ret[oc]=new Dual();
							ret[oc].first=x;
							ret[oc].second=y;
							ret[oc].time=t;
							ret[oc].dir=dir;
							ret[oc].order=4;
						}
			if (r.order[t]==5){while((r.order[t]==5)&&(t<r.time-1)){t++;}
							ret[oc]=new Dual();
							ret[oc].first=x;
							ret[oc].second=y;
							ret[oc].time=t;
							ret[oc].dir=dir;
							ret[oc].order=5;
						}
			t=ret[oc].time;
			oc++;
		}
		r.count=oc;
		return ret;
	}


	public Dual getOrder(int t,ROT r){
		Dual ret=new Dual();
		int tt=t;
		while ((tt<r.time)&&(r.coor[tt].first==r.coor[t].first)&&(r.coor[tt].second==r.coor[t].second)){
			tt++;	
		}
		Dual incc=detAngle(r.coor[t].first,r.coor[t].second,r.coor[tt].first,r.coor[tt].second);
		while ((r.order[tt]==1)&&(tt+1<r.time)&&((r.coor[tt+1].first!=r.coor[tt].first)||(r.coor[tt+1].second!=r.coor[tt].second))){
			tt++;
		}
		ret.dir=incc.dir;
		ret.time=tt;
		ret.first=r.coor[tt+1].first;
		ret.second=r.coor[tt+1].second;
		ret.order=1;
		return ret;
	}
	public Dual detAngle(int i1,int j1,int i2,int j2){
		Dual ret=new Dual();
		ret.dir=144;
		if ((j2-j1)>0){ret.dir=90;ret.first=0;ret.second=1;}
		if ((i2-i1)>0){ret.dir=0;ret.first=1;ret.second=0;}
		if ((j1-j2)>0){ret.dir=270;ret.first=0;ret.second=-1;}
		if ((i1-i2)>0){ret.dir=180;ret.first=-1;ret.second=0;}
		return ret;
	}
	public byte[] checkStatus(){
		byte[] fid=new byte[4];
		byte[] second=new byte[]{(byte)0xFC,0x0C,0x00,0x01,0x00,0x00,0x02,0x00,0x00,0x00,0x00,(byte)0x5A};
		byte[] cdata=Arrays.copyOfRange(second,0,8);
		CRC16 crc=new CRC16();
		fid=crc.process(cdata);
		second[9]=fid[0];
		second[10]=fid[1];
		return second;
	}



}
