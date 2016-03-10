package chuangbang.fragment;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import chuangbang.activity.R;
import chuangbang.entity.User;

import android.R.integer;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EstpRegist extends Fragment implements OnClickListener{
	private EditText etUserName,etPassword,etComfirmPassword,etValidateCode;
	private String phoneNumber,password,comfirmPassword,code;
	private Button btGetCode,btRegist;
	//private Handler handler;
	//private Message msg;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_estp_regist, null);

		/*
		 * 绑定控件
		 */

		etUserName=(EditText)view.findViewById(R.id.et_name);
		etPassword=(EditText)view.findViewById(R.id.et_password);
		etComfirmPassword=(EditText)view.findViewById(R.id.et_password_again);//确认密码
		etValidateCode=(EditText)view.findViewById(R.id.et_ValidateCode);
		btGetCode=(Button)view.findViewById(R.id.bt_Getcode_Id);
		btRegist=(Button)view.findViewById(R.id.bt_regist);

		//handler= new MsgHandler(getActivity());


		/*
		 * 添加监听
		 */
		btGetCode.setOnClickListener(this);
		btRegist.setOnClickListener(this);

		return view;

	}




	/**
	 * 按钮点击监听
	 */

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		/*
		 * 获取验证码
		 */
		case R.id.bt_Getcode_Id:
			phoneNumber=etUserName.getText().toString();
			if (phoneNumber == null || phoneNumber.length() != 11) {
				Toast.makeText(getActivity(), "错误!请输入正确的手机号!", Toast.LENGTH_SHORT).show();
				return ;
			}
			/**
			 * 请求验证码
			 */
			BmobSMS.requestSMSCode(getActivity(), phoneNumber, "注册模板",new RequestSMSCodeListener() {

				@Override
				public void done(Integer smsId, BmobException ex) {
					if (ex == null) {// 验证码发送成功

						Log.i("", "短信id：" + smsId);// 用于查询本次短信发送详情
					}
				}});
			break;


			/*
			 * 注册
			 */
		case R.id.bt_regist:
			password=etPassword.getText().toString();
			comfirmPassword=etComfirmPassword.getText().toString();
			code = etValidateCode.getText().toString();
			/**
			 * 判断手机号11位验证
			 * 
			 */
			if (phoneNumber == null || phoneNumber.length() != 11) {
				Toast.makeText(getActivity(), "错误，请输入正确号码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password == null || password.length() < 6) {
				Toast.makeText(getActivity(), "错误!密码的长度不得少于6位!", Toast.LENGTH_SHORT)
				.show();		
				return;
			}
			if (TextUtils.isEmpty(code)) {
				Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!(password.equals(comfirmPassword))) {
				Toast.makeText(getActivity(), "两次密码不一样", Toast.LENGTH_SHORT).show();
				return;
			}
			BmobSMS.verifySmsCode(getActivity(), phoneNumber, code, new VerifySMSCodeListener() {

				@Override
				public void done(BmobException arg0) {
					if(arg0==null){
						//短信验证通过
						//开始bmob的注册
						bmobSignUp();
					}else{
						Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
					}

				}
			});
			//开始bmob的注册
			bmobSignUp();
			break;
		}

	}



	/*
	 * bmob注册
	 */
	private void bmobSignUp(){
		BmobUser user=new BmobUser();
		user.setUsername("TEL"+phoneNumber);
		user.setPassword(password);
		user.setMobilePhoneNumber(phoneNumber);
		
		user.setMobilePhoneNumberVerified(true);	
		user.signUp(getActivity(), new SaveListener() {

			@Override
			public void onSuccess() {
				User newUser=new User();
				newUser.setMemberType(2);
				BmobUser cUser=BmobUser.getCurrentUser(getActivity());
				newUser.update(getActivity(),cUser.getObjectId(),new UpdateListener() {

					@Override
					public void onSuccess() {				
						Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT).show();
						getActivity().finish();
						
					}

					@Override
					public void onFailure(int arg0, String arg1) {


					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {


			}
		});
	}


	/*
	 * 环信注册
	 */
	//	private void chatSign(){
	//		new Thread(new Runnable() {
	//
	//					@Override
	//					public void run() {
	//						try {
	//							// Looper.prepare();
	//							// 调用sdk注册方法
	//							EMChatManager.getInstance().createAccountOnServer(phoneNumber, password);
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
	//	}
}
