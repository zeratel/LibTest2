package com.lhf.test.lhfandroidutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件操作类
 * 
 * @Description: 文件操作类
 * 
 * @FileName: FileUtil.java
 * 
 * @Package com.device.photo
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-5-10 下午01:37:49
 * 
 * @Version V1.0
 */
public class FileUtil {
	public FileUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * InputStream to byte
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public byte[] readInputStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}

		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();

		return data;
	}

	/**
	 * Byte to bitmap
	 * 
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public Bitmap getBitmapFromBytes(byte[] bytes, BitmapFactory.Options opts) {
		if (bytes != null) {
			if (opts != null) {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			} else {
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			}
		}

		return null;
	}

	public static boolean isDebug = LogUtil.DEBUG;

	public static void writeToSdCard(final String message) {
		if (isDebug) {

			new Thread() {
				@Override
				public void run() {
					File file = new File(
							Environment.getExternalStorageDirectory(),
							"cpblog.txt");
					if (file.exists()) {
						file.delete();
					}
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(file, true);
						fos.write(message.getBytes());
						fos.flush();
						fos.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();

		}

	}

	public static void writeHpCacheDataToSdCard(final String message) {

		new Thread() {
			@Override
			public void run() {
				File file = new File(Environment.getExternalStorageDirectory(),
						"hp_cache_data.txt");
				if (file.exists()) {
					file.delete();
				}
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(file, true);
					fos.write(message.getBytes());
					fos.flush();
					fos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

//	public static void readHpCacheDataFromSdCard(
//			final TransactionHandler handler) {
//		new Thread() {
//			@Override
//			public void run() {
//
//				try {
//
//					File file = new File(
//							Environment.getExternalStorageDirectory(),
//							"hp_cache_data.txt");
//					if (!file.exists() || file.isDirectory()) {
//						Message message = Message.obtain();
//						message.what = -1;
//						message.arg1 = 500;
//						message.arg2 = 1;
//						message.obj = "Error";
//						handler.sendMessage(message);
//					} else {
//						BufferedReader br = new BufferedReader(new FileReader(
//								file));
//						String temp = null;
//						StringBuffer sb = new StringBuffer();
//						temp = br.readLine();
//						while (temp != null) {
//							sb.append(temp + " ");
//							temp = br.readLine();
//						}
//
//						Message message = Message.obtain();
//						message.what = 1;
//						message.arg1 = 200;
//						message.arg2 = 0;
//						message.obj = sb.toString();
//						handler.sendMessage(message);
//					}
//
//				} catch (IOException e) {
//					e.printStackTrace();
//					Message message = Message.obtain();
//					message.what = -1;
//					message.arg1 = 500;
//					message.arg2 = 1;
//					message.obj = "Error";
//					handler.sendMessage(message);
//
//				}
//			}
//		}.start();
//
//	}

}
