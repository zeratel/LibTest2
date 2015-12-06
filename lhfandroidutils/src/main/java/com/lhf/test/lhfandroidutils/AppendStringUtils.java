package com.lhf.test.lhfandroidutils;

import java.util.ArrayList;

public class AppendStringUtils {
	/***
	 * 由Integer型集合，拼接获取拼接成的String
	 * @param list
	 * @param spilt 
	 * @return
	 */
	public static String getTokens(ArrayList<Integer> list,String spilt){
		if (list==null||list.size()==0) {
			return "";
		}
		StringBuilder builder=new StringBuilder();
		int size = list.size();
		for (int i = 0; i <size ; i++) {
			if (i!=size-1) {
				builder.append(list.get(i)+spilt);
			}else {
				builder.append(list.get(i)+"");
			}
		}
		return builder.toString();
	}
}
