package com.lhf.test.lhfandroidutils;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	private static final String TAG = "StringUtil";

	public static boolean isDigital(String intstr) {
		for (int i = 0; intstr != null && i < intstr.length(); i++) {
			if (intstr.charAt(i) < '0' || intstr.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

	public static float floatSub(float sub1_f,float sub2_f){
		int sub1 = yuan2fen(sub1_f);
		int sub2 = yuan2fen(sub2_f);
		
		int rlt = sub1 - sub2;
		String str = (Math.abs(rlt) / 100) + ".";
		str += String.format("%02d", Math.abs(rlt % 100));
		if(rlt < 0){
			return Float.parseFloat(str) * -1;
		}else{
			return Float.parseFloat(str);
		}
	}
	
//	public static int yuan2fen(float sub){
//		return (int)(sub * 100 + 0.0001);
//	}
	
	
	public static int yuan2fen(float value){
		int f = (int)(value * 1000);
		int t =  f % 10;
		int fen = 0;
		if(t > 5){
			fen  = f/10 + 1;
		}else{
			fen = f/10;
		}
		return fen;
	}

	public static boolean isPasswords(String pass){	
		for (int i = 0; pass!=null&&i < pass.length(); i++) {		
			char chName = pass.charAt(i);
			if ((chName >= 'a' && chName <= 'z')
					|| (chName >= '0' && chName <= '9') || chName == '_') {
				continue;

			}else
				return false;

		}			
		return true;
	}

	/**
	 * 将一个集合中的球号码连成字符串，以separator分隔
	 * 
	 * @param tmp0
	 * @param separator
	 * @return
	 */
	public static String concatNumbers(Vector tmp0, String separator) {
		if (null == tmp0)
			return null;
		StringBuffer buf = new StringBuffer();
		int size = tmp0.size();
		for (int i = 0; i < size; i++) {
			String obj = (String) tmp0.elementAt(i);
			buf.append(obj);
			if (i < size - 1) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	public static String concat(String obj1, int obj2, String obj3) {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(obj1);
		strbuf.append(obj2);
		strbuf.append(obj3);
		return strbuf.toString();
	}

	public static String concat(String obj1, String obj2, String obj3) {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(obj1);
		strbuf.append(obj2);
		strbuf.append(obj3);
		return strbuf.toString();
	}

	public static String concat(String obj1, String obj2, int obj3) {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(obj1);
		strbuf.append(obj2);
		strbuf.append(obj3);
		return strbuf.toString();
	}

	public static String concat(int obj1, String obj2, String obj3) {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(obj1);
		strbuf.append(obj2);
		strbuf.append(obj3);
		return strbuf.toString();
	}

	public static String concat(String obj1, String obj2, String obj3,
			String obj4) {

		StringBuffer strbuf = new StringBuffer();
		strbuf.append(obj1);
		strbuf.append(obj2);
		strbuf.append(obj3);
		strbuf.append(obj4);

		return strbuf.toString();
	}

	public static int parseIntEx(String intstr) {
		StringBuffer sbuf = new StringBuffer();
		boolean nzero = false;
		for (int i = 0; intstr != null && i < intstr.length(); i++) {
			if (intstr.charAt(i) == '0' && !nzero) {
				continue;
			} else {
				nzero = true;
				sbuf.append(intstr.charAt(i));
			}
		}
		if (sbuf.length() > 0) {
			return Integer.parseInt(sbuf.toString());
		} else
			return 0;
	}

	/**
	 * 以splitchar符号分割字符串txt
	 * 
	 * @param txt
	 * @param split
	 * @return string[]
	 */
	public static String[] SplitString(String txt, char splitchar) {
		Vector v = new Vector();
		while (txt != null && txt.length() > 0) {
			int index = txt.indexOf(splitchar);
			if (index >= 0) {
				String str = getSubString(txt, 0, index);
				v.addElement(str);
				txt = txt.substring(index + 1);
			} else {
				break;
			}
		}
		if (txt != null && txt.length() > 0) {
			v.addElement(txt);
		}
		if (v.size() > 0) {
			String[] str = new String[v.size()];
			v.copyInto(str);
			return str;
		}
		return null;
	}

	/**
	 * 以splitchar符号分割字符串txt
	 * 
	 * @param txt
	 * @param splitchar
	 * @return vector
	 */
	public static Vector SplitString(String txt, String splitchar) {
		Vector v = new Vector();
		while (txt != null && txt.length() > 0) {
			int index = txt.indexOf(splitchar);
			if (index >= 0) {
				String str = getSubString(txt, 0, index);
				v.addElement(str);
				txt = txt.substring(index + splitchar.length());
			} else {
				break;
			}
		}
		if (txt != null && txt.length() > 0) {
			v.addElement(txt);
		}
		return v;
	}

	/**
	 * 将字符串str中的oldStr用newStr替换
	 * 
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public static String replace(String str, String oldStr, String newStr) {
		int startIndex = 0;
		int index = str.indexOf(oldStr, startIndex);
		if (index != -1) {
			StringBuffer buf = new StringBuffer();
			while (index >= 0) {
				if (index > startIndex)
					buf.append(getSubString(str, startIndex, index));
				buf.append(newStr);
				startIndex = index + oldStr.length();
				index = str.indexOf(oldStr, startIndex);
			}
			if (startIndex <= str.length() - 1)
				buf.append(getSubString(str, startIndex, str.length()));
			return buf.toString();
		}
		return str;
	}


	/**将投注号码字符拆分成数字数组
	 * @param lotteryNum
	 * @param splitNum
	 * @return luo
	 */
	public static ArrayList<String> StringToNumList(String lotteryNum, int splitNum,String splitchar,String playWay) {
		ArrayList<String>  ve = new ArrayList<String>();

		StringBuffer numberBuffer = new StringBuffer();
		
		if(playWay != null && playWay.equals("大小单双")){
			Pattern reg = Pattern.compile("\\d+");//如果大小单双是数字显示，则如下处理
			Matcher matcher = reg.matcher(lotteryNum);
			while (matcher.find())
				numberBuffer.append(matcher.group());

			int size = numberBuffer.toString().length();
			for(int i=0;i<size;i=i+splitNum){
				String subStr = getSubString(numberBuffer.toString(), i, i+splitNum);
				if(subStr.equals("0"))
					subStr = "小";
				else if(subStr.equals("1"))
					subStr = "单";
				else if(subStr.equals("2"))
					subStr = "双";
				else if(subStr.equals("9"))
					subStr = "大";
				ve.add(subStr);
			}

			if(ve.size() == 0){//如果大小单双是中文显示，则如下处理
				ve.addAll(StringUtil.SplitString(lotteryNum, ","));
			}

			return ve;
		}

		if(splitchar != null){
			int plusIndex = lotteryNum.indexOf(splitchar);
			if(plusIndex != -1){
				ArrayList<String>  ve1 = new ArrayList<String>();

				String redLotteryNum = lotteryNum.substring(0, plusIndex);
				ArrayList<String> v = StringToNumList(redLotteryNum,splitNum, null,playWay);
				LogUtil.d(TAG, "is repeated?");
				ve1.addAll(v);
				String blueLotteryNum = lotteryNum.substring(plusIndex+1);
				ve1.addAll(StringToNumList(blueLotteryNum,splitNum, null,playWay));

				ve1.add(String.valueOf(v.size()));//获取绿球的位置
				return ve1;
			}
		}else {
			Pattern reg = Pattern.compile("\\d+");
			Matcher matcher = reg.matcher(lotteryNum);

			while (matcher.find())
				numberBuffer.append(matcher.group());

			int size = numberBuffer.toString().length();
			for(int i=0;i<size;i=i+splitNum){
				String subStr = getSubString(numberBuffer.toString(), i, i+splitNum);
				ve.add(subStr);
			}
		}

		return ve;
	}

	/**
	 * 取出str中索引startIndex到stopIndex-1的字符串
	 * 
	 * @param str
	 * @param startIndex
	 * @param stopIndex
	 * @return
	 */
	public static String getSubString(String str, int startIndex, int stopIndex) {
		if (str == null)
			return "";
		if (startIndex > str.length())
			return "";
		if (stopIndex > str.length())
			return str.substring(startIndex);
		return str.substring(startIndex, stopIndex);
	}

	/**
	 * 逐个取出str中的字符以separator分隔
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String getSplitString(String str, String separator) {
		StringBuffer strbuf = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				strbuf.append(getSubString(str, i, i + 1));
				if (i != str.length() - 1) {
					strbuf.append(separator);
				}
			}
		}
		return strbuf.toString();
	}

	public static long xDate2Seconds(String strTime, int area) {
		if (strTime == null || strTime.length() != 19)
			return 0;
		int year = Integer.parseInt(strTime.substring(0, 4));
		int month = Integer.parseInt(strTime.substring(5, 7));
		int date = Integer.parseInt(strTime.substring(8, 10));
		int hourOfDay = Integer.parseInt(strTime.substring(11, 13)) - area;
		int minute = Integer.parseInt(strTime.substring(14, 16));
		int second = Integer.parseInt(strTime.substring(17, 19));
		return xDate2Seconds(year, month, date, hourOfDay, minute, second);
	}

	public static long xDate2Seconds(int ayear, int amonth, int aday,
			int ahour, int aminute, int asecond) {
		int xMINUTE = 60;// 1分的秒数
		int xHOUR = (60 * xMINUTE);// 1小时的秒数
		int xDAY = 24 * xHOUR; // 1天的秒数;
		int xYEAR = (365 * xDAY);// 1年的秒数

		long[] month1 = { xDAY * (0), xDAY * (31), xDAY * (31 + 28),
				xDAY * (31 + 28 + 31), xDAY * (31 + 28 + 31 + 30),
				xDAY * (31 + 28 + 31 + 30 + 31),
				xDAY * (31 + 28 + 31 + 30 + 31 + 30),
				xDAY * (31 + 28 + 31 + 30 + 31 + 30 + 31),
				xDAY * (31 + 28 + 31 + 30 + 31 + 30 + 31 + 31),
				xDAY * (31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30),
				xDAY * (31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31),
				xDAY * (31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30) };
		long seconds;
		int year;

		year = ayear - 1970;
		seconds = xYEAR * year; // 前几年过去的秒数
		// 计算过了多少个闰年
		for (int i = 1970; i <= ayear; i++) {
			// 当前是闰年
			if (i == ayear) {
				if (amonth < 2)
					break; // 当前年份未过2月份
			}

			/*
			 * 闰年要满足的条件: 1.能被4整除且不能被100整除 2.能被400整除 这两个条件只要有一个满足就可以了
			 */
			boolean b1 = ((i % 100) != 0) && ((i % 4) == 0);
			if (((i % 400) == 0) || b1) {
				seconds += xDAY; // 闰年加1天秒数
			}
		}
		seconds += month1[amonth - 1]; // 加上今年本月过去的秒数
		seconds += xDAY * (aday - 1); // 加上本天过去的秒数
		seconds += xHOUR * ahour; // 加上本小时过去的秒数
		seconds += xMINUTE * aminute; // 加上本分钟过去的秒数
		seconds += asecond; // 加上当前秒数
		return seconds * 1000;
	}
}