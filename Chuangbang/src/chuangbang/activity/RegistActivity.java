package chuangbang.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import chuangbang.app.ChuangApp;
import chuangbang.fragment.EstpRegist;
import chuangbang.fragment.InvestorRegist;
import chuangbang.util.MsgHandler;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RegistActivity extends FragmentActivity implements OnPageChangeListener, OnCheckedChangeListener{
	
	private ViewPager viewPager;
	private Handler handler;
	private Message msg;
	private ChuangApp app;
	private RadioGroup rg;
	
	private FragmentPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_regist);
		//消息处理者
		handler= new MsgHandler(RegistActivity.this);
		app=(ChuangApp) getApplication();
		/*
		 * 控件绑定
		 */
		viewPager=(ViewPager)findViewById(R.id.vp_regist);
		rg=(RadioGroup)findViewById(R.id.rg_regist);
		adapter=new InnerPagerAdapter(getSupportFragmentManager());
		
		
		
		/*
		 * 添加监听
		 */
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		rg.setOnCheckedChangeListener(this);
	}

	class InnerPagerAdapter extends FragmentPagerAdapter{
		public InnerPagerAdapter(FragmentManager fm){
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment=null;
			switch (position) {
			/*
			 * 创业者
			 */
			case 0:
				fragment=new EstpRegist();
				break;	
				/*
				 * 进入投资人
				 */
			case 1 :
				fragment=new InvestorRegist();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}


	@Override
	public void onPageSelected(int arg0) {
		
		
	}


	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch (arg1) {
		case R.id.rb_estp:
			viewPager.setCurrentItem(0);
			break;

		case R.id.rb_investor:
			viewPager.setCurrentItem(1);
			break;
		}
		
	}



	//	private void doPost(){
	//		final String postUrl="http://cloud.bmob.cn/56a234cda6607ec7/userSignUp";
	//		final String userName=etUserName.getText().toString();
	//		final String password=etPassword.getText().toString();
	//
	//		RequestParams params=new RequestParams("utf-8");
	//
	//		params.addBodyParameter("username",userName );
	//		params.addBodyParameter("password",password );
	//		HttpUtils http=new HttpUtils();
	//		http.send(HttpRequest.HttpMethod.POST,postUrl, params, new RequestCallBack<String>() {
	//			@Override
	//			public void onFailure(HttpException arg0, String arg1) {
	//
	//				Log.i("regist", arg1);
	//			}
	//
	//			@Override
	//			public void onSuccess(ResponseInfo<String> info) {
	//				String result=info.result;
	//				
	//
	//								try {
	//									JSONObject json=new JSONObject(result);
	//									String objectId=json.getString("objectId");
	//									String sessionToken =json.getString("sessionToken");
	//									app.setObjectId(objectId);
	//									app.setSessionToken(sessionToken);
	//									
	//									Log.i("Tag", "objectId="+app.getObjectId());
	//								} catch (JSONException e) {
	//									// TODO Auto-generated catch block
	//									e.printStackTrace();
	//								}
	//				new Thread(new Runnable() {
	//
	//					@Override
	//					public void run() {
	//						try {
	//							// Looper.prepare();
	//							// 调用sdk注册方法
	//							EMChatManager.getInstance().createAccountOnServer(userName, password);
	//							msg=handler.obtainMessage();
	//							msg.arg1=5;
	//							handler.sendMessage(msg);
	//							// Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
	//							finish();
	//						}catch (final EaseMobException e) {
	//							//注册失败
	//							etUserName.setText("");
	//							etPassword.setText("");
	//							etComfirmPassword.setText("");
	//							etValidateCode.setText("");
	//							int errorCode=e.getErrorCode();
	//							if(errorCode==EMError.NONETWORK_ERROR){
	//								// Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
	//								msg=handler.obtainMessage();
	//								msg.arg1=4;
	//								handler.sendMessage(msg);
	//							}else if(errorCode==EMError.USER_ALREADY_EXISTS){
	//								
	//								msg=handler.obtainMessage();
	//								msg.arg1=1;
	//								handler.sendMessage(msg);
	//								Log.i("Tag", "用户已经存在");
	//							}else if(errorCode==EMError.UNAUTHORIZED){
	//								//Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
	//								msg=handler.obtainMessage();
	//								msg.arg1=6;
	//								handler.sendMessage(msg);
	//							}else{
	//								//Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
	//								msg=handler.obtainMessage();
	//								msg.arg1=3;
	//								handler.sendMessage(msg);
	//							}
	//
	//						}
	//					}
	//				}).start();
	//				
	//				
	//
	//
	//			}
	//
	//
	//
	//		});
	//	}

}
