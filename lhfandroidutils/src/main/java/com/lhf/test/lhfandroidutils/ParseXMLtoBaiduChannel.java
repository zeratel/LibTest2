package com.lhf.test.lhfandroidutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ParseXMLtoBaiduChannel {
	static ParseXMLtoBaiduChannel pxbc;
	static Context mContext;
	public String baiduChannel = Constants.CHANDLE_AK_ID;
	
	private ParseXMLtoBaiduChannel(Context context){
		mContext = context;
		try {
			ApplicationInfo appInfo;
			appInfo = mContext.getPackageManager()
					.getApplicationInfo(mContext.getPackageName(),
							PackageManager.GET_META_DATA);
			//批量打包获取的渠道号类似channel137985
			String channel = appInfo.metaData.getString("baiduChannelssssss");
			if(channel.contains("CHANNEL")){
				baiduChannel=channel.replace("CHANNEL", "");
				if (baiduChannel.contains("${")) {
					baiduChannel=baiduChannel.replace("${", "");
					baiduChannel=baiduChannel.replace("}", "");
				}
			} else {
				baiduChannel = channel;
			}
		} catch (Exception e) {
			e.printStackTrace();
			baiduChannel = Constants.CHANDLE_AK_ID;
		}
	}
	
	public static ParseXMLtoBaiduChannel getInstance(Context context){
		if(pxbc == null){
			pxbc = new ParseXMLtoBaiduChannel(context);
		}
		return pxbc;
	}
	
}
