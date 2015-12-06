package com.lhf.test.lhfandroidutils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: AdnNameLengthFilter.java
 * @author: LHF
 * @date: 2015年7月27日 下午3:02:48
 * @description:
 * 
 */
public class InputFilterUtils implements InputFilter {

	private int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
	private String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

	public InputFilterUtils(int mAX_EN) {
		super();
		this.MAX_EN = mAX_EN;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		// if (destCount + sourceCount > MAX_EN) {
		// // Toast.makeText(MainActivity.this, getString(R.string.count),
		// // Toast.LENGTH_SHORT).show();
		// return "";
		//
		// } else {
		// return source;
		// }
		
		// 在这里判断还可以输入多少中英文
		// 已经输入的中英文
		int destCount = dest.toString().length()
				+ getChineseCount(dest.toString());
		// 还未输入的中英文数量
		int sourceCount = source.toString().length()
				+ getChineseCount(source.toString());
		// int sourceCount = getChineseCount(source.toString());

		// 最大数量减去已有的中英文数量再减去
		// int keep = MAX_EN - (dest.length() - (dend - dstart));
		int keep = MAX_EN - (destCount - (dend - dstart)); // -sourceCount

		try {
			
			// 针对一次输入大量中文
			if ((getChineseCount(source.toString()) > 0 || getChineseCount(dest
					.toString()) > 0) && destCount + sourceCount > MAX_EN) {
				if (getChineseCount(dest.toString()) > 0 && getChineseCount(source.toString()) > 0) {
					//后来输入大量中英文
					int temp = MAX_EN - (destCount - (dend - dstart));
					int number = 0;
					String tempString = source.toString();
					char[] a = tempString.toCharArray();
					for (int i = 0; i < temp; ++i) {
						if (a.length - 1 >= i && String.valueOf(a[i]).matches(regEx)) {
							++i;
						}
						++number;
					}
					
//				keep = MAX_EN - destCount - getChineseCount(source.toString());
					keep = number;
				}else{
					//一开始就输入大量中英文
//				keep = MAX_EN - getChineseCount(source.toString());
					keep = MAX_EN - 6;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (keep <= 0) {
			return "";
		} else if (keep >= end - start) {
			return null; // keep original
		} else {
			keep += start;
			if (Character.isHighSurrogate(source.charAt(keep - 1))) {
				--keep;
				if (keep == start) {
					return "";
				}
			}
			return source.subSequence(start, keep);
		}
	}

	private int getChineseCount(String str) {
		int count = 0;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}

}
