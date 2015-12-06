package com.lhf.test.lhfandroidutils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastManager {
	private static Toast toast;

	public ToastManager() {
	}

	public static void showShortToast(Context context, String message) {
		toast = getToast(context);
		toast.setText(message);
		toast.setDuration(0);
		toast.show();
	}

	public static void showShortToast(Context context, int resId) {
		toast = getToast(context);
		toast.setText(resId);
		toast.setDuration(0);
		toast.show();
	}

	public static void showLongToast(Context context, String message) {
		toast = getToast(context);
		toast.setText(message);
		toast.setDuration(1);
		toast.show();
	}

	public static void showLongToast(Context context, int resId) {
		toast = getToast(context);
		toast.setText(resId);
		toast.setDuration(1);
		toast.show();
	}

	/*** 网络错误 */
	public static final int ERRORTYPE_NETWORK = 1;
	/*** 后端程序错误 */
	public static final int ERRORTYPE_BACKSERVER = 2;
	/*** json解析异常 */
	public static final int ERRORTYPE_PARSEJSON = 3;

	public static void showErrorToast(Context context, int failType) {
		toast = getToast(context);
		String errorMessage = "对不起，服务器忙！请稍后再试！";
		switch (failType) {
		case 1:
			errorMessage = "网络错误";
			break;
		case 2:
			errorMessage = "后端程序错误";
			break;
		case 3:
			errorMessage = "json解析异常";
			break;

		}
		toast.setText(errorMessage);
		toast.setDuration(0);
		toast.show();

	}

	private static Toast getToast(Context context) {
		if (toast == null) {
			synchronized (ToastManager.class) {
				if (toast == null) {
					toast = Toast.makeText(context, "", 0);
				}

			}
		}
		return toast;
	}
	// private static class MyToast extends Toast
	// { private static MyToast instance;
	// private TextView myToast_message_tv;
	// private MyToast(Context context) {
	// super(context);
	// initView(context);
	// }
	//
	// public static MyToast getInstance(Context context)
	// {
	// if(instance==null)
	// {
	// synchronized (MyToast.class) {
	// if(instance==null)
	// {
	// instance = new MyToast(context);
	// }
	//
	// }
	// }
	// return instance;
	// }
	// public void initView(Context context)
	// {
	// View view = View.inflate(context,R.layout.mytoast, null);
	// myToast_message_tv = (TextView)
	// view.findViewById(R.id.myToast_message_tv);
	//
	// this.setView(view);
	// }
	//
	// public void setText(String message)
	// {
	// if(myToast_message_tv!=null)
	// {
	// myToast_message_tv.setText(message);
	// }
	// }
	//
	// }
}
