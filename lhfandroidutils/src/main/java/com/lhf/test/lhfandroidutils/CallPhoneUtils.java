package com.lhf.test.lhfandroidutils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class CallPhoneUtils {
	/**
	 * 打电话的，弹框提示
	 * @param context
	 * @param phoneNumber 手机号
	 */
	public static void callPhone(final Context context, final String phoneNumber) {

		DialogUtil.createDialog(context, 0, "", "是否拨打：" + phoneNumber,
				Constants.BODA, Constants.CANCLE,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// Uri uri = Uri.parse("tel:" + phoneNumber);
						// Intent intent = new Intent(Intent.ACTION_DIAL, uri);
						Intent intent = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + phoneNumber));
//						context.startActivity(intent);

						dialog.dismiss();
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
	}
}
