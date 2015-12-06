package com.lhf.test.lhfandroidutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片特效处理
 * 
 * @author chunjiang.shieh
 * 
 */
public class ImageUtil {

	/**
	 * 放大缩小图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获得圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获得带倒影的图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	// // 论坛把图片异步上传到服务器
	public static void bbsImagesUpload(final Handler handler,
			final Bitmap bitmap, final String userName, final String type,
			final int orderNumber) {
		// new Thread(
		SingleThreadPool.getIntance().executeThread(new Runnable() {
			@Override
			public void run() {
				// String actionUrl =
				// "http:// :8083/uploadPhoto.jsp?username="
				// + userName + "&type=" + type;
				// String actionUrl = Constants.LHFURL
				// + "/uploadPhoto.jsp?username=" + userName + "&type="
				// + type;
				String actionUrl = Constants.LHFURL
						+ Constants.UPLOAD_BATCH_PHOTO_URL + "?username="
						+ userName + "&type=" + type;
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "****";
				try {
					URL url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					// con.setRequestProperty("Content-Length: ",
					// file.length()+300+"");
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con
							.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; "
							+ "name=\"file1\";filename=\""
							+ System.currentTimeMillis() + ".jpg" + "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					InputStream isBm = new ByteArrayInputStream(baos
							.toByteArray());
					/* 设置每次写入1024bytes */
					int bufferSize = 4096;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = isBm.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
						System.out.println();
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					isBm.close();
					ds.flush();
					ds.close();

					/* 取得Response内容 */
					if (con.getResponseCode() == 200) {
						int leng = con.getContentLength();
						InputStream is = con.getInputStream();
						int ch;
						StringBuffer b = new StringBuffer();
						while ((ch = is.read()) != -1) {
							b.append((char) ch);
						}
						Message msg = new Message();
						msg.what = Constants.UPLOAD_SUCCESS;
						msg.arg1 = orderNumber;

						msg.obj = b.toString();
						handler.sendMessage(msg);
					} else {
						// 失败
						Message msg = new Message();
						msg.what = Constants.UPLOAD_FAILD;
						msg.arg1 = orderNumber;
						handler.sendMessage(msg);

					}
					/* 将Response显示于Dialog */
					// showDialog("上传成功" + b.toString().trim(), context);
					/* 关闭DataOutputStream */

				} catch (Exception e) {
					e.printStackTrace();
					// showDialog("上传失败", context);
					Message msg = new Message();
					msg.what = Constants.UPLOAD_FAILD;
					msg.arg1 = orderNumber;
					handler.sendMessage(msg);
				}
			}
		});
		// ).start();

	}

	// //setuppic图片异步上传到服务器
	public static void bbsImagesUploadSetUpPic(final Handler handler,
			final Bitmap bitmap, final String type, final Integer id,
			final Integer pos, final int orderNumber) {
		// new Thread(
		SingleThreadPool.getIntance().executeThread(new Runnable() {
			@Override
			public void run() {
				// String actionUrl =
				// "http:// :8083/uploadPhoto.jsp?type="
				// + type + "&id=" + id + "&pos=" + pos;
				// String actionUrl = Constants.LHFURL +
				// "/uploadPhoto.jsp?type="
				// + type + "&id=" + id + "&pos=" + pos;
				String actionUrl = Constants.LHFURL
						+ Constants.UPLOAD_BATCH_PHOTO_URL + "?type=" + type
						+ "&id=" + id + "&pos=" + pos;
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "****";
				try {
					URL url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					// con.setRequestProperty("Content-Length: ",
					// file.length()+300+"");
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con
							.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; "
							+ "name=\"file1\";filename=\""
							+ System.currentTimeMillis() + ".jpg" + "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					InputStream isBm = new ByteArrayInputStream(baos
							.toByteArray());
					/* 设置每次写入1024bytes */
					int bufferSize = 4096;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = isBm.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
						System.out.println();
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					isBm.close();
					ds.flush();
					ds.close();

					/* 取得Response内容 */
					if (con.getResponseCode() == 200) {
						int leng = con.getContentLength();
						InputStream is = con.getInputStream();
						int ch;
						StringBuffer b = new StringBuffer();
						while ((ch = is.read()) != -1) {
							b.append((char) ch);
						}
						Message msg = new Message();
						msg.what = Constants.UPLOAD_SUCCESS;
						msg.arg1 = orderNumber;

						msg.obj = b.toString();
						handler.sendMessage(msg);
					} else {
						// 失败
						Message msg = new Message();
						msg.what = Constants.UPLOAD_FAILD;
						msg.arg1 = orderNumber;
						handler.sendMessage(msg);

					}
					/* 将Response显示于Dialog */
					// showDialog("上传成功" + b.toString().trim(), context);
					/* 关闭DataOutputStream */

				} catch (Exception e) {
					e.printStackTrace();
					// showDialog("上传失败", context);
					Message msg = new Message();
					msg.what = Constants.UPLOAD_FAILD;
					msg.arg1 = orderNumber;
					handler.sendMessage(msg);
				}
			}
		});
		// ).start();

	}

	// //活动聚会结束时上传的图片墙异步上传到服务器
	public static void bbsImagesUploadPicWall(final Handler handler,
			final Bitmap bitmap, final String type, final Integer id,
			final int orderNumber) {
		// new Thread(
		SingleThreadPool.getIntance().executeThread(new Runnable() {
			@Override
			public void run() {
				// String actionUrl =
				// "http:// :8083/uploadPhoto.jsp?type="
				// + type + "&id=" + id;
				// String actionUrl = Constants.LHFURL +
				// "/uploadPhoto.jsp?type="
				// + type + "&id=" + id;
				String actionUrl = Constants.LHFURL
						+ Constants.UPLOAD_BATCH_PHOTO_URL + "?type=" + type
						+ "&id=" + id;
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "****";
				try {
					URL url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					// con.setRequestProperty("Content-Length: ",
					// file.length()+300+"");
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con
							.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					// ds.writeBytes("Content-Disposition: form-data; "
					// + "name=\"file1\";filename=\"" + "fileName_img"
					// + "\"" + end);
					ds.writeBytes("Content-Disposition: form-data; "
							+ "name=\"file1\";filename=\""
							+ System.currentTimeMillis() + ".jpg" + "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					InputStream isBm = new ByteArrayInputStream(baos
							.toByteArray());
					/* 设置每次写入1024bytes */
					int bufferSize = 4096;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = isBm.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
						System.out.println();
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					isBm.close();
					ds.flush();
					ds.close();

					/* 取得Response内容 */
					if (con.getResponseCode() == 200) {
						int leng = con.getContentLength();
						InputStream is = con.getInputStream();
						int ch;
						StringBuffer b = new StringBuffer();
						while ((ch = is.read()) != -1) {
							b.append((char) ch);
						}
						Message msg = new Message();
						msg.what = Constants.UPLOAD_SUCCESS;
						msg.arg1 = orderNumber;

						msg.obj = b.toString();
						handler.sendMessage(msg);
					} else {
						// 失败
						Message msg = new Message();
						msg.what = Constants.UPLOAD_FAILD;
						msg.arg1 = orderNumber;
						handler.sendMessage(msg);

					}
					/* 将Response显示于Dialog */
					// showDialog("上传成功" + b.toString().trim(), context);
					/* 关闭DataOutputStream */

				} catch (Exception e) {
					e.printStackTrace();
					// showDialog("上传失败", context);
					Message msg = new Message();
					msg.what = Constants.UPLOAD_FAILD;
					msg.arg1 = orderNumber;
					handler.sendMessage(msg);
				}
			}
		});
		// ).start();

	}

	public static void uploadPhoto(final Handler handler, final Bitmap bitmap,
			final String cookie) {
		final String fileName = "";
		// new Thread(
		SingleThreadPool.getIntance().executeThread(new Runnable() {
			@Override
			public void run() {
				String actionUrl = "/mobile/uploaUserPhoto.jsp?cookie="
						+ cookie;
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "****";
				try {
					URL url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					// con.setRequestProperty("Content-Length: ",
					// file.length()+300+"");
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con
							.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; "
							+ "name=\"file1\";filename=\"" + fileName + "\""
							+ end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					InputStream isBm = new ByteArrayInputStream(baos
							.toByteArray());
					/* 设置每次写入1024bytes */
					int bufferSize = 4096;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = isBm.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
						System.out.println();
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					isBm.close();
					ds.flush();
					ds.close();

					/* 取得Response内容 */
					if (con.getResponseCode() == 200) {
						int leng = con.getContentLength();
						InputStream is = con.getInputStream();
						int ch;
						StringBuffer b = new StringBuffer();
						while ((ch = is.read()) != -1) {
							b.append((char) ch);
						}
						Message msg = new Message();
						msg.what = Constants.UPLOAD_SUCCESS;
						msg.obj = b.toString();
						handler.sendMessage(msg);
					} else {
						// 失败
						Message msg = new Message();
						msg.what = Constants.UPLOAD_FAILD;
						handler.sendMessage(msg);

					}
					/* 将Response显示于Dialog */
					// showDialog("上传成功" + b.toString().trim(), context);
					/* 关闭DataOutputStream */

				} catch (Exception e) {
					e.printStackTrace();
					// showDialog("上传失败", context);
					Message msg = new Message();
					msg.what = Constants.UPLOAD_FAILD;
					handler.sendMessage(msg);
				}
			}
		});
		// ).start();

	}

	private static void showDialog(String mess, Context context) {
		new AlertDialog.Builder(context).setTitle("Message").setMessage(mess)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public static Bitmap convertBitmap() {
		String str = "http://www.70qao.com/upload/2012/5/25/1340586810422_s.jpg";
		try {
			URL url = new URL(str);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	//
	// public static String getFixImageUrl(String url, String width, String
	// height) {
	//
	// if (!TextUtils.isEmpty(url)) {
	// if (!url.startsWith("http://")) {
	// url = com.flsd.gift.util.Constants.UPLOADSERVER_HOST + url;
	// }
	// if (!url.startsWith(com.flsd.gift.util.Constants.UPLOADSERVER_HOST
	// + "/image.jsp")) {
	// url = com.flsd.gift.util.Constants.UPLOADSERVER_HOST
	// + "/image.jsp?imgUrl=" + url;
	// }
	//
	// return url + "&width=" + width + "&height=" + height;
	// }
	// return url;
	//
	// }

	// public static String getCompressImageUrl(String url) {
	// if (!TextUtils.isEmpty(url)) {
	// if (!url.startsWith("http://")) {
	// url = Constants.UPLOADSERVER_HOST + url;
	// }
	// if (!url.startsWith(com.flsd.gift.util.Constants.UPLOADSERVER_HOST
	// + "/imageCompress.jsp")) {
	// url = com.flsd.gift.util.Constants.UPLOADSERVER_HOST
	// + "/imageCompress.jsp?imgUrl=" + url;
	// }
	// return url;
	// }
	// return url;
	// }

	// public static String getHostImageUrl(String url) {
	//
	// if (!TextUtils.isEmpty(url)) {
	//
	// if(url.endsWith(";")){
	// url = url.replace(";", "");
	// }
	//
	// if (!url.startsWith("http://")) {
	// url = com.flsd.gift.util.Constants.UPLOADSERVER_HOST + url;
	// }
	// return url;
	// }
	// return url;
	// }

	// // 获取缓存图片的本地地址
	// public static String getLocalImagePath(String url) {
	//
	// return Environment.getExternalStorageDirectory().getAbsolutePath()
	// + File.separator + Constants.DEFAULT_FILE_CACHE
	// + File.separator
	// + MyMD5Util.MD5(ImageUtil.getHostImageUrl(url));
	//
	// }

	// 信息采集后上传图片
	public static void imagesUpload2(final Handler handler,
			final Bitmap bitmap, final String name) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// String actionUrl = "http:// :8083/"
		// + Constants.UPLOAD_BATCH_PHOTO_URL;
		// String end = "\r\n";
		// String twoHyphens = "--";
		// String boundary = "****";
		// try {
		// URL url = new URL(actionUrl);
		// HttpURLConnection con = (HttpURLConnection) url
		// .openConnection();
		// /* 允许Input、Output，不使用Cache */
		// con.setDoInput(true);
		// con.setDoOutput(true);
		// con.setUseCaches(false);
		// /* 设置传送的method=POST */
		// con.setRequestMethod("POST");
		// /* setRequestProperty */
		// con.setRequestProperty("Connection", "Keep-Alive");
		// con.setRequestProperty("Charset", "UTF-8");
		// con.setRequestProperty("Content-Type",
		// "multipart/form-data;boundary=" + boundary);
		// // con.setRequestProperty("Content-Length: ",
		// // file.length()+300+"");
		// /* 设置DataOutputStream */
		// DataOutputStream ds = new DataOutputStream(con
		// .getOutputStream());
		// ds.writeBytes(twoHyphens + boundary + end);
		// ds.writeBytes("Content-Disposition: form-data; "
		// + "name=\"file1\";filename=\"" + name
		// + "\"" + end);
		// ds.writeBytes(end);
		// /* 取得文件的FileInputStream */
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// InputStream isBm = new ByteArrayInputStream(baos
		// .toByteArray());
		// /* 设置每次写入1024bytes 注意 大尺寸图适配 */
		// int bufferSize = 4096;
		// byte[] buffer = new byte[bufferSize];
		// int length = -1;
		// /* 从文件读取数据至缓冲区 */
		// while ((length = isBm.read(buffer)) != -1) {
		// /* 将资料写入DataOutputStream中 */
		// ds.write(buffer, 0, length);
		// System.out.println();
		// }
		// ds.writeBytes(end);
		// ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
		// /* close streams */
		// isBm.close();
		// ds.flush();
		// ds.close();
		//
		// /* 取得Response内容 */
		// if (con.getResponseCode() == 200) {
		// int leng = con.getContentLength();
		// InputStream is = con.getInputStream();
		// int ch;
		// StringBuffer b = new StringBuffer();
		// while ((ch = is.read()) != -1) {
		// b.append((char) ch);
		// }
		// Message msg = new Message();
		// msg.what = Constants.UPLOAD_SUCCESS;
		//
		// msg.obj = b.toString();
		// handler.sendMessage(msg);
		// } else {
		// // 失败
		// Message msg = new Message();
		// msg.what = Constants.UPLOAD_FAILD;
		// handler.sendMessage(msg);
		//
		// }
		// /* 将Response显示于Dialog */
		// // showDialog("上传成功" + b.toString().trim(), context);
		// /* 关闭DataOutputStream */
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// // showDialog("上传失败", context);
		// Message msg = new Message();
		// msg.what = Constants.UPLOAD_FAILD;
		// handler.sendMessage(msg);
		// }
		// }
		// }).start();
		SingleThreadPool.getIntance().executeThread(new Runnable() {
			@Override
			public void run() {
				// String actionUrl = "http:// :8083/"
				// + Constants.UPLOAD_BATCH_PHOTO_URL;
				String actionUrl = Constants.LHFURL
						+ Constants.UPLOAD_BATCH_PHOTO_URL + "?type=1";
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "****";
				try {
					URL url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					// con.setRequestProperty("Content-Length: ",
					// file.length()+300+"");
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con
							.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; "
							+ "name=\"file1\";filename=\"" + name + "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					InputStream isBm = new ByteArrayInputStream(baos
							.toByteArray());
					/* 设置每次写入1024bytes 注意 大尺寸图适配 */
					int bufferSize = 4096;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = isBm.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
						System.out.println();
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					isBm.close();
					ds.flush();
					ds.close();

					/* 取得Response内容 */
					if (con.getResponseCode() == 200) {
						int leng = con.getContentLength();
						InputStream is = con.getInputStream();
						int ch;
						StringBuffer b = new StringBuffer();
						while ((ch = is.read()) != -1) {
							b.append((char) ch);
						}
						Message msg = new Message();
						msg.what = Constants.UPLOAD_SUCCESS;

						msg.obj = b.toString();
						handler.sendMessage(msg);
					} else {
						// 失败
						Message msg = new Message();
						msg.what = Constants.UPLOAD_FAILD;
						handler.sendMessage(msg);

					}
					/* 将Response显示于Dialog */
					// showDialog("上传成功" + b.toString().trim(), context);
					/* 关闭DataOutputStream */

				} catch (Exception e) {
					e.printStackTrace();
					// showDialog("上传失败", context);
					Message msg = new Message();
					msg.what = Constants.UPLOAD_FAILD;
					handler.sendMessage(msg);
				}
			}
		});

	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/*
	 * 把resId 变为Bitmap
	 */
	public static Bitmap res2bitmap(Context context, int resId) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		return bitmap;
	}
}
