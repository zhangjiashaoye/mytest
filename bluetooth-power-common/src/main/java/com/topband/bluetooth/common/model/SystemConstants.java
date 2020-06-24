package com.topband.bluetooth.common.model;

/**
 * 常量类.
 * 
 * @author wujiahuai
 *
 */
public class SystemConstants {
	
	private SystemConstants() {
		
	}



	/**
	 * 请求 header 中用于保存 token 的参数名.
	 */
	public static final String TOKEN_HEADER = "Authorization";


	/**
	 * 登录时签发的 tokenKey 在 redis 中的 key 的前缀.
	 */
	public static final String TOKEN_CACHE_PREFIX = "cloud:token:";

	/**
	 *判断强制登出redis的key值
	 */
	public static final String APP_LOGIN_TOKEN_PREFIX = "app:token:";

	/**
	 * 登出redis的key值
	 */
	public static final String KNOCT_OUT_PREFIX = "logout:token:";

	/**
	 * 删除的用户redis前缀
	 */
	public static final String DEL_FORCR_OUT_PREFIX = "del:token:";

	/**
	 * 自动刷新redis的token前缀
	 */
	public static final String REFRESH_TOKEN = "refresh:token:";

	/**
	 * 强制登出redis的value值
	 */
	public static final String FORCE_LOGOUT = "Y";


	/**
	 * 语言请求头
	 */
	public static final String LANGUAGE_HEADER = "Language";

	/**
	 * 默认语言
	 */
	public static final String LANGUAGE_DEFAULT = "en_US";

	/**
	 * 数据库转实体 字段是否转下划线驼峰且首字母小写
	 */
	public  final  static  boolean DB_FIELD_CAMEL_CASE=true;

	// 邮件验证码缓存
	public static final String KEY_HEAD_CODE_EMAIL = "code_email_";

	// 邮件验证码校验通过,缓存
	public static final String KEY_HEAD_CHECK_CODE_EMAIL = "checked_code_email_";

	/**
	 * web强制登出redis的key前缀
	 */
	public static final String WEB_LOGIN_TOKEN_PREFIX = "web:token:";

	// 短信验证码缓存
	public static final String KEY_HEAD_CODE_SMS = "code_sms_";

	// 短信验证码校验通过,缓存
	public static final String KEY_HEAD_CHECK_CODE_SMS = "checked_code_sms_";

	public static int MEMCACHED_CODE_TIME_EMAIL = 30 * 60;// 3分钟 验证码

	public static int MEMCACHED_CODE_TIME_SMS = 3 * 60;// 3分钟 验证码

	public static int STATUS_CODE_LOST = 14;// 验证码过期

	public static int STATUS_CODE_ERROR = 15;// 验证码错误

	public static int PAGE_NUMBER = 1;//

	public static int PAGE_SIZE = 1000;//

	public static  final String KEY_CODE = "code";

	public static final String KEY_TRY = "tryCount";

	/**
	 * // 验证码出错次数  不做返回的status！！！
	 */
	public static final int ERROR_COUNT_MAX_LIMIT = 5;

	public static final String FAMILY_DEFAULT_NAME = "我的家庭";

	/**
	 * 验证码前缀.
	 */
	public static final String VALIDATE_CODE_PREFIX = "webCode" + ":validateCode:";


	/**
	 * web强制登出redis的key前缀
	 */
	public static final String SQE_SPACE = "seq_space";

	public static class FilePostFix{
		public static final String ZIP_FILE =".zip";

		public static final String [] IMAGES ={"jpg", "jpeg", "JPG", "JPEG", "gif", "GIF", "bmp", "BMP", "png"};
		public static final String [] ZIP ={"ZIP","zip","rar","RAR"};
		public static final String [] VIDEO ={"mp4","MP4","mpg","mpe","mpa","m15","m1v", "mp2","rmvb"};
		public static final String [] APK ={"apk","exe"};
		public static final String [] OFFICE ={"xls","xlsx","docx","doc","ppt","pptx"};

	}
	public class FileType{
		public static final int FILE_IMG = 1;
		public static final int FILE_ZIP = 2;
		public static final int FILE_VEDIO= 3;
		public static final int FILE_APK = 4;
		public static final int FIVE_OFFICE = 5;
		public static final String FILE_IMG_DIR= "/img/";
		public static final String FILE_ZIP_DIR= "/zip/";
		public static final String FILE_VEDIO_DIR= "/video/";
		public static final String FILE_APK_DIR= "/apk/";
		public static final String FIVE_OFFICE_DIR= "/office/";
	}

	/**
	 * 场景里的 组名称 键
	 */
	public static final	String SCENE_KEY_GROUPNAME = "groupName";
	/**
	 * 场景里的 组id 键
	 */
	public static final	String SCENE_KEY_GROUPID = "groupId";

}
