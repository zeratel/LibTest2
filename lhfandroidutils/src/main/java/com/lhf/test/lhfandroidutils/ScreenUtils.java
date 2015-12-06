package com.lhf.test.lhfandroidutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获得屏幕相关的辅助类
 * 
 * @author zhy
 * 
 */
public class ScreenUtils {
	private ScreenUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 取得 View 的截图
	 * 
	 * @throws IOException
	 */
	public static String saveImage(Context context, View view, String fileName)
			throws IOException {
		String imagepath = "";
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				&& Environment.getExternalStorageDirectory().exists()) {
			imagepath = Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ File.separator
					+ Constants.DEFAULT_FILE_DIR;
			File dir = new File(imagepath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			imagepath = imagepath + File.separator + fileName;
		} else {
			imagepath = context.getApplicationContext().getFilesDir()
					.getAbsolutePath()
					+ fileName;
		}
		imagepath += ".jpg";
		File file = new File(imagepath);
		if (true) {
			file.delete();
			file.createNewFile();

			Bitmap pic = getBitmapFromView(view);
			FileOutputStream fos = new FileOutputStream(file);
			pic.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		}
		ToastManager.showShortToast(context, imagepath);
		return imagepath;
	}

	/**
	 * 取得 View 的截图
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		Bitmap bitmap = null;
		int width = view.getWidth();
		int height = view.getHeight();
		if (width != 0 && height != 0) {
			bitmap = Bitmap
					.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.eraseColor(0xffffffff);
			Canvas canvas = new Canvas(bitmap);
			view.draw(canvas);
		}
		return bitmap;
	}
}
