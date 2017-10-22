package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 读写工具类
 * @author Xiloer
 *
 */
public class IOUtil {
	/**
	 * 根据给定的输入流读取字符串
	 * @param in
	 * @return
	 */
	public static String readString(InputStream in){
		try {
			int ch1 = in.read();
			int ch2 = in.read();
			if ((ch1 | ch2) < 0)
			    throw new EOFException();
			int len = (ch1 << 8) +ch2 ;
			byte[] data = new byte[len];
			in.read(data);
			return new String(data,"utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	/**
	 * 根据给定的输入流读取Long值
	 * @param in
	 * @return
	 */
	public static long readLong(InputStream in){
		try {
			DataInputStream dis = new DataInputStream(in);
			return dis.readLong();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	/**
	 * 写出一个short值
	 * 
	 * @param s
	 * @param out
	 */
	public synchronized static void writeShort(short s, OutputStream out) {
		try {
			DataOutputStream dos = new DataOutputStream(out);
			dos.writeShort(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写字符串
	 * @param str
	 * @param out
	 */
	public synchronized static void writeString(String str, OutputStream out) {
		try {
			byte[] data = str.getBytes("utf-8");
			writeShort((short) data.length, out);
			out.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写int值
	 * @param str
	 * @param out
	 */
	public static void writeLong(long i, OutputStream out) {
		try {
			DataOutputStream dos = new DataOutputStream(out);
			dos.writeLong(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



