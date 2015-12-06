package com.lhf.test.lhfandroidutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

public class UpLoadImageManager {
	ArrayList<Bitmap> bitmaps;
	public static int count = 0;
	private Context context;

	private UploadImageListener uploadImageListener;

	public UpLoadImageManager(Context context) {
		this.context = context;
	}

	private Handler upLoadHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.UPLOAD_SUCCESS:
				// 上传成功
				int index = msg.arg1;
				Object obj = msg.obj;
				String imageUrl = obj.toString();// {"fileName":"m/201309/16/1379328818466.jpeg","fileSize":"86196"}

				count++;
				if (uploadImageListener != null) {
					if (count == bitmaps.size()) {
						// 全部上传成功
						uploadImageListener.onSuccess(imageUrl, index, true);
					} else {
						uploadImageListener.onSuccess(imageUrl, index, false);
					}
				}
				break;
			case Constants.UPLOAD_FAILD:
				// 上传失败
				index = msg.arg1;
				// "张图片上传失败");
				if (uploadImageListener != null) {
					uploadImageListener.onError(index);
				}
				break;
			}
		}

	};

	// 创建AA时上传图片
	public void upLoadPhotosSetUpPic(ArrayList<Bitmap> bitmaps, String type,
			Integer id) {
		this.bitmaps = bitmaps;
		// 把用户裁剪的图片上传到服务器
		for (int i = 0; i < bitmaps.size(); i++) {
			uploadPhoto2serverSetUpPic(upLoadHandler, bitmaps.get(i), type, id,
					i + 1, i + 1);
		}
	}

	// 创建AA时上传图片
	private void uploadPhoto2serverSetUpPic(Handler upLoadHandler,
			Bitmap bitmap, String type, Integer id, Integer pos, int index) {

		ImageUtil.bbsImagesUploadSetUpPic(upLoadHandler, bitmap, type, id, pos,
				index);
	}

	// 活动聚会结束时上传图片
	public void upLoadPhotosPicWall(ArrayList<Bitmap> bitmaps, String type,
			Integer id) {
		this.bitmaps = bitmaps;
		// 把用户裁剪的图片上传到服务器
		for (int i = 0; i < bitmaps.size(); i++) {
			uploadPhoto2serverPicWall(upLoadHandler, bitmaps.get(i), type, id,
					i + 1);
		}
		LogUtil.i("a427", bitmaps.size() + "==bitmaps");
	}
	
	// 采集信息后上传图片  上传失败的由逻辑层控制 访问数据库然后再次上传失败的
		public void uploadPhoto2(ArrayList<Bitmap> bitmaps, ArrayList<String> numbers) {

			this.bitmaps = bitmaps;
			for (int i = 0; i < bitmaps.size(); i++) {
				ImageUtil.imagesUpload2(upLoadHandler, bitmaps.get(i), numbers.get(i));
				
				LogUtil.i("LHF", "images.size():imagesUpload2"+i);
			}
			
		}

	// 活动聚会结束时上传图片
	private void uploadPhoto2serverPicWall(Handler upLoadHandler,
			Bitmap bitmap, String type, Integer id, int index) {

		ImageUtil
				.bbsImagesUploadPicWall(upLoadHandler, bitmap, type, id, index);
	}

	public void upLoadPhotos(ArrayList<Bitmap> bitmaps, String username,
			String[] types) {
		this.bitmaps = bitmaps;
		// 把用户裁剪的图片上传到服务器
		for (int i = 0; i < bitmaps.size(); i++) {
			uploadPhoto2server(upLoadHandler, bitmaps.get(i), username,
					types[i], i + 1);
		}
	}

	private void uploadPhoto2server(Handler upLoadHandler, Bitmap bitmap,
			String username, String type, int index) {

		ImageUtil.bbsImagesUpload(upLoadHandler, bitmap, username, type, index);
	}

	public UploadImageListener getUploadImageListener() {
		return uploadImageListener;
	}

	public void setUploadImageListener(UploadImageListener uploadImageListener) {
		this.uploadImageListener = uploadImageListener;
	}

	public interface UploadImageListener {

		/**
		 * 
		 * @param imageUrl
		 *            图片地址
		 * @param index
		 *            图片在集合中的位置
		 * @param isAllUpLoaded
		 *            所有图片是否全部上传完成
		 */
		public void onSuccess(String imageUrl, int index, boolean isAllUpLoaded);

		/*
		 * index 代表上传失败图片在集合中的 位置
		 */
		public void onError(int index);

	}
}
