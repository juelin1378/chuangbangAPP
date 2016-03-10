package chuangbang.app;



import java.io.File;

import com.easemob.chat.EMChat;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class ChuangApp extends Application{
	private static ImageLoader imageLoader;
	private static ChuangApp app;//app对象

	private String objectId;
	private String sessionToken;
	private boolean isInvestor;



	@Override
	public void onCreate() {

		super.onCreate();
		//初始化Application对象
		app=this;

		//环信sdk初始化
		EMChat.getInstance().init(this);
		//图片加载框架初始化，该框架为单例
		imageLoader=ImageLoader.getInstance();
		//初始化缓存机制
		initImageLoader(getApplicationContext());

	}

	/*
	 * 其他类调用此方法获取Application对象
	 */
	public static ChuangApp getApp(){
		return app;
	}

	/*、
	 * 其他类调用此方法获取图片框架对象
	 */
	public ImageLoader getImageLoader(){
		return imageLoader;
	}

	public static int getMCount(){
		Log.i("Count", "执行app");
		return 4;

	}

	public static void initImageLoader(Context context) {
		//缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "chuangbang/Cache"); 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽 
		.threadPoolSize(4) //线程池内加载的数量
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
		.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // /你可以通过自己的内存缓存实现
		.memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
		.diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		// 由原先的discCache -> diskCache
		.diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径  
		.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间  
		.writeDebugLogs() // Remove for release app
		.build();
		//全局初始化此配置  
		ImageLoader.getInstance().init(config);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public boolean isInvestor() {
		return isInvestor;
	}

	public void setInvestor(boolean isInvestor) {
		Log.i("Count", "执行赋值");
		this.isInvestor = isInvestor;
	}




}
