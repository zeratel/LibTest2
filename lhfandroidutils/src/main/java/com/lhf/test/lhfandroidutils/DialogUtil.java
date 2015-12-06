package com.lhf.test.lhfandroidutils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.view.View;
import android.widget.ListAdapter;

/**
 * 对话框工具类
 * 
 * @author houmiao.xiong
 * @date 2012-8-16
 */
public class DialogUtil {

	/**
	 * 
	 * @param context
	 * @param okResId
	 * @param cancelResId
	 * @param positiveClickListener
	 * @param negativeClickListener
	 */
	@SuppressLint("NewApi")
	public static void createDialog(Context context, int okResId, int cancelResId,String message,
			OnClickListener positiveClickListener,
			OnClickListener negativeClickListener) {
		
		Builder builder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			builder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new Builder(context);
		}

		builder.setMessage(message);
		builder.setPositiveButton(okResId, positiveClickListener);
		builder.setNegativeButton(cancelResId, negativeClickListener);

		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**创建对话框 （包含Message，SingleBtn）
	 *
	 * @param context
	 * @param view
	 * @return
	 */
	@SuppressLint("NewApi")
	public static AlertDialog createDialog(Context context,  View view) {
		
		Builder builder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			builder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new Builder(context);
		}
		
		AlertDialog dialog = builder
				.setView(view).create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		return dialog;
	}

	/**创建对话框 （包含Message，SingleBtn）
	 *
	 * @param context
	 * @param message
	 * @param resourseStr
	 * @param positiveClickListener
	 */
	@SuppressLint("NewApi")
	public static void createDialog(Context context, 
			String message, int resourseStr,OnClickListener positiveClickListener) {
		
		Builder builder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			builder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new Builder(context);
		}
		
		AlertDialog dialog = builder
				.setMessage(message).setPositiveButton(context.getString(resourseStr), positiveClickListener).create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	/**
	 * 创建对话框 （包含Title，Message，SingleBtn）
	 * 
	 * @param title
	 * @param message
	 */
	@SuppressLint("NewApi")
	public static void createDialog(Context context, String title,
			String message, String btnStr,OnClickListener positiveClickListener) {

		Builder builder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			builder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new Builder(context);
		}
//		AlertDialog.Builder builder = new Builder(context);

		AlertDialog dialog = builder
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle(title)
				.setMessage(message).setPositiveButton(btnStr, positiveClickListener).create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 创建对话框 （包含Title，Message，SingleBtn）
	 * 
	 * @param title
	 * @param message
	 */
	@SuppressLint("NewApi")
	public static void createDialog(Context context, String title,
			String message, String btnStr) {
		
		Builder builder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			builder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new Builder(context);
		}

//		AlertDialog.Builder builder = new Builder(context);

		AlertDialog dialog = builder
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle(title)
				.setMessage(message).setPositiveButton(btnStr,null).create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	/**
	 * 创建对话框(包含 TitleImg,Title,Message,PosiBtn,NegaBtn)
	 * 
	 * @param context
	 * @param iconid
	 * @param title
	 * @param message
	 * @param posiStr
	 * @param negaStr
	 * @param positiveClickListener
	 * @param negativeClickListener
	 */
	@SuppressLint("NewApi")
	public static AlertDialog createDialog(Context context, int iconid,
			String title, String message, String posiStr, String negaStr,
			OnClickListener positiveClickListener,
			OnClickListener negativeClickListener) {
		
		Builder dialogBuilder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			dialogBuilder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			dialogBuilder = new Builder(context);
		}

//		AlertDialog.Builder dialogBuilder = new Builder(context);
		dialogBuilder.setCancelable(false);
		AlertDialog dialog = dialogBuilder.setIcon(iconid).setTitle(title)
				.setMessage(message)
				.setPositiveButton(posiStr, positiveClickListener)
				.setNegativeButton(negaStr, negativeClickListener).create();

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

		return dialog;
	}

	/**
	 * 创建对话框（包含 TitleImg,Title,Message,posiBtn）
	 * 
	 * @param context
	 * @param iconId
	 * @param title
	 * @param message
	 * @param posiStr
	 * @param positiveClickListener
	 */
	@SuppressLint("NewApi")
	public static Dialog createDialog(Context context, int iconId,
			String title, String message, String posiStr,
			OnClickListener positiveClickListener) {
		
		Builder dialogBuilder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			dialogBuilder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			dialogBuilder = new Builder(context);
		}

//		AlertDialog.Builder dialogBuilder = new Builder(context);
		dialogBuilder.setCancelable(false);
		AlertDialog dialog = dialogBuilder.setIcon(iconId).setTitle(title)
				.setMessage(message)
				.setPositiveButton(posiStr, positiveClickListener).create();

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

		return dialog;
	}

	@SuppressLint("NewApi")
	public static void createDailog(Context context, int iconId, String title,
			String message, String posiStr, ListAdapter adapter,
			OnClickListener adapterListener,
			OnClickListener positiveClickListener) {
		
		Builder dialogBuilder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			dialogBuilder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			dialogBuilder = new Builder(context);
		}

		AlertDialog dialog = dialogBuilder.setIcon(iconId).setTitle(title)
				.setMessage(message).setAdapter(adapter, adapterListener)
				.setPositiveButton(posiStr, positiveClickListener).create();

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * @author shouli.luo
	 * @version 创建时间：2012-8-29 下午10:29:38 方法作用：创建自定义有两个button的提示框
	 * 
	 * @param context
	 * @param iconResId
	 * @param titleResId
	 * @param textEntryView
	 * @param okResId
	 * @param cancelResId
	 * @param positiveClickListener
	 * @param negativeClickListener
	 */
	@SuppressLint("NewApi")
	public static void showTextEntryDialog(Context context, int iconResId,
			int titleResId, View textEntryView, int okResId, int cancelResId,
			OnClickListener positiveClickListener,
			OnClickListener negativeClickListener) {
		
		Builder builder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			builder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			builder = new Builder(context);
		}

//		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setIcon(iconResId);
		builder.setTitle(titleResId);
		builder.setView(textEntryView);
		builder.setPositiveButton(okResId, positiveClickListener);
		builder.setNegativeButton(cancelResId, negativeClickListener);

		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 创建只带连接进度描述的进度框
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param cancelListener
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context,
			String title, String msg,
			DialogInterface.OnCancelListener cancelListener) {

		ProgressDialog dialog = ProgressDialog.show(context, title, msg);

		dialog.setCancelable(true);
		if (cancelListener != null) {
			dialog.setOnCancelListener(cancelListener);
		}
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	@SuppressLint("NewApi")
	public static AlertDialog createOnlyShowMessageDialog(Context context,
			String title, String message, String negativeButtonText, int ic_launcher) {
		
		Builder dialogBuilder = null;
		// 版本控制 VERSION_CODES.HONEYCOMB_MR2 = 13 3.2
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)  {
			dialogBuilder = new Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		} else {
			dialogBuilder = new Builder(context);
		}

//		AlertDialog.Builder dialogBuilder = new Builder(context);
		dialogBuilder.setCancelable(false);
		dialogBuilder.setIcon(ic_launcher).setTitle(title)
				.setMessage(message);
		dialogBuilder.setNegativeButton(negativeButtonText,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

					}
				});
		AlertDialog dialog = dialogBuilder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;

	}
	
}
