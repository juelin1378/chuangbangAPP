package chuangbang.activity;

import java.io.File;

import chuangbang.util.Final;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class ImageFromNewPro extends Activity implements Final{
	private File tempFile;
	private static final int  PHOTO_REQUEST_GALLERY=1;
	private static final int  PHOTO_REQUEST_CAREMA=2;
	private static final int PHOTO_REQUEST_CUT=3;
	private static final String PHOTO_FILE_NAME = "chuangbang/carema.jpg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_from_new_pro);
		File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}
	
	
	
	
	
	public void doClick(View v){
		switch (v.getId()) {
		//从相机获取
		case R.id.btn_new_pro_take_photo:
			camera();
			break;
			//从相册获取
		case R.id.btn_new_pro_pick_photo:
			gallery();
			break;

		case R.id.btn_new_pro_cancel:
			finish();
			break;
		}

	}
	
	
	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY

		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/*
	 * 从相机获取
	 */
	public void camera() {
		// 激活相机
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_NAME);
			// 从文件中创建uri
			Uri uri = Uri.fromFile(tempFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		}
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
		startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		//super.onActivityResult(arg0, arg1, arg2);
		if(requestCode==PHOTO_REQUEST_GALLERY){
			//从相册返回的数据
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}
		}else if(requestCode==PHOTO_REQUEST_CAREMA){
			// 从相机返回的数据
			if (hasSdcard()) {
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(ImageFromNewPro.this, "未找到存储卡，无法存储照片！", 0).show();
			}


		}else if(requestCode==PHOTO_REQUEST_CUT){
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				/*
				 * 对剪切后的图片进行操作,将bitmap通过广播传送到MineFragment
				 */
				Intent intent=null;
				intent=new Intent(INTENT_ACTION_LOGO);
				intent.putExtra(INTENT_EXTRA_LOGO, bitmap);
				sendBroadcast(intent);
				finish();
				//iv.setCircleImageBitmap(bitmap);
			}
			try {
				// 将临时文件删除
				tempFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	
	}
	/*
	 * 剪切图片
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);

		intent.putExtra("outputFormat", "JPEG");// 图片格式
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	/*
	 * 检测sd卡是否有效
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}
}