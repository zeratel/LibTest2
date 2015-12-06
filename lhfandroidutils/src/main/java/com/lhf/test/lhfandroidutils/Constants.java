package com.lhf.test.lhfandroidutils;

public class Constants {

	/** android */
	public static final String ANDROID = "android";


	public static final String UPLOAD_BATCH_PHOTO_URL = "/uploadBatchPhoto.jsp";
	public static final int UPLOAD_SUCCESS = 1001;// 上传图片成功
	public static final int UPLOAD_FAILD = 1002;// 上传图片失败
	public static final double appVersion = 1.0;
	// 默认文件缓存
	public static final String DEFAULT_FILE_DIR = "com.LHFao.faith";

	public static final String LHF_TEST_URL = "http:// :8083";//
	// 测试地址
	public static String LHFURL = LHF_TEST_URL;// 当前网络访问地址

	public final static String BACK_EXIT_TIPS = "再按一次返回键退出应用";

	public static String BODA = "确定";

	public static String CANCLE = "取消";

	public static String CHANDLE_AK_ID_CPB = "12345"; // 本站
	public static String CHANDLE_AK_ID = CHANDLE_AK_ID_CPB;
	private static Constants constants = new Constants();

	private Constants() {
	}

	public static Constants getIntance() {
		return constants;
	}

}
