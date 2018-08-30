package com.uns.baseapp.util;

import java.security.MessageDigest;


public class MD5 {

	public static String getMD5ofStr(String arg0) {
		try {
			String digestHexStr = "";
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(arg0.getBytes());
			for (int i = 0; i < 16; i++) {
				digestHexStr += byteHEX(md5[i]);
			}
			return digestHexStr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String byteHEX(byte byte0) {
		char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char ac1[] = new char[2];
		ac1[0] = ac[byte0 >>> 4 & 0xf];
		ac1[1] = ac[byte0 & 0xf];
		String s = new String(ac1);
		return s;
	}
}