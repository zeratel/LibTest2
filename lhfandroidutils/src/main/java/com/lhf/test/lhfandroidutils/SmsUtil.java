package com.lhf.test.lhfandroidutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

public class SmsUtil {
	private Activity activity;
	private static SmsUtil sms;
	
	private SmsUtil( Activity activity){
		this.activity=activity;
	}
	public static SmsUtil getInstance(Activity activity){
		if(sms==null){
			sms=new SmsUtil(activity);
		}
		return sms;
	}
	
	   /**
	    * 读取短信
	    * @return
	    */
	    public String getSmsCenter()
	    {
	       String[] projection = new String[] {"service_center"};
	       try{
	    	//获取所有短信，按时间倒序
	        Cursor myCursor =activity.managedQuery(Uri.parse("content://sms/inbox"),
	          projection,
	          null, null , "date desc");
	        return doCursor(myCursor);
	       }
	       catch (SQLiteException ex)
	       {
	    	   ex.printStackTrace();
	       }
	       return null;
	    }
	    
	/**
	 * 处理游标，得到短信中心号
	 * 
	 * @param cur
	 *            游标
	 * @return 短信中心号
	 */
	private String doCursor(Cursor cur) {
		// 短信中心号
		String smscenter = null;
		if (cur.moveToFirst()) {
			int smscColumn = cur.getColumnIndex("service_center");
			smscenter = cur.getString(smscColumn);
		}
		return smscenter;
	}
	
	/**
	 * 调用系统界面，给指定的号码发送短信，并附带短信内容
	 * 
	 * @param context
	 * @param number
	 * @param body
	 */
	public static void sendSmsWithBody(Context context, String number, String body) {
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(Uri.parse("smsto:" + number));
		sendIntent.putExtra("sms_body", body);
		context.startActivity(sendIntent);
	}
}
