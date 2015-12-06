package com.lhf.test.lhfandroidutils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	public static String Md51(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.reset();

			md5.update(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return "";
		}
		byte[] byteArray = md5.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));

		}
		return md5StrBuff.toString();

	}

	public static String Md5(String str) {
		// str = Md51("cpbao.com_友信宝"+Md51(str));

		str = Md51("cpbao.com_友信宝" + Md51(str));
		System.out.print("str" + str);
		return str;
	}
}
