package chuangbang.activity;




import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;

import chuangbang.util.MsgHandler;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	private TextView tvRegist;
	private EditText etUserName,etPassword;
	private Handler handler;
	private Message msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		handler=new MsgHandler(LoginActivity.this);
		
		
		//绑定控件
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		etUserName=(EditText)findViewById(R.id.et_username);
		etPassword=(EditText)findViewById(R.id.et_password);
		
		tvRegist.setOnClickListener(this);
		
		
	}


	public void doClick(View v){
		//Intent intent=null;
		final String username;
		final String password;

		username = etUserName.getText().toString();
		password = etPassword.getText().toString();
		switch (v.getId()) {
		case R.id.bt_login:
			/*
			 * bmob用户登录
			 */
			
			// 数据格式验证
			if (username == null || username.length() != 11) {
				Toast.makeText(LoginActivity.this, "错误!请输入正确的手机号!",
						Toast.LENGTH_SHORT).show();
				etUserName.setError("手机号不能为空");
				return;
			}

			if (password == null || password.length() < 6) {
				Toast.makeText(LoginActivity.this, "错误!密码的长度不得少于6位!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// 封装数据
			BmobUser bmobUser = new BmobUser();
			bmobUser.setUsername(username);
			bmobUser.setPassword(password);
			bmobUser.login(getApplicationContext(), new SaveListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(getApplicationContext(), "数据服务器登录成功",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
					//登录环信聊天服务器
//					EMChatManager.getInstance().login(username,password,new EMCallBack() {
//						@Override
//						public void onSuccess() {
//							runOnUiThread(new Runnable() {
//								public void run() {
//									EMGroupManager.getInstance().loadAllGroups();
//									EMChatManager.getInstance().loadAllConversations();
//									
//									Log.d("main", "聊天服务器登录成功！");		
//								}
//							});
//							
////							 ���ò�����ʾWhat's New
//							SharedPreferences sp = LoginActivity.this.getSharedPreferences("config", Context.MODE_PRIVATE);
//							Editor editor = sp.edit();
//							editor.putBoolean("isFirstRun", false);
//							editor.commit();
//							msg=handler.obtainMessage();
//							msg.arg1=2;
//							handler.sendMessage(msg);
//			

//							
//							
//						}
//					 
//						@Override
//						public void onProgress(int progress, String status) {
//					 
//						}
//					 
//						@Override
//						public void onError(int code, String message) {
////							Log.i("Tag", "登录失败");
//							
//						}
//					});

				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "数据服务器登录失败",
							Toast.LENGTH_SHORT).show();

				}
			});

			break;
		}
	}


	/*
	 * 跳转注册界面
	 */
	@Override
	public void onClick(View arg0) {
		Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
		startActivity(intent);
		
	}
	

}
