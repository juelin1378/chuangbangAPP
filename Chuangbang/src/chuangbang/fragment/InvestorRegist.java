package chuangbang.fragment;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import chuangbang.activity.MenuDialog;
import chuangbang.activity.NewMyProject;
import chuangbang.activity.R;
import chuangbang.entity.InvestorInfo;
import chuangbang.entity.User;


import chuangbang.util.Final;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 如果没有选择名片，
 * 		先注册用户，再更新用户类型
 * 如果有选择名片，
 * 		先上传名片，
 * 		注册用户
 * 		更新用户类型和名片文件
 */
public class InvestorRegist extends Fragment implements OnClickListener,Final{
	private EditText etUserName;    //用户手机号
	private EditText etPassword;
	private EditText etComfirmPassword;//再次确认密码
	private EditText etValidateCode;  //验证码
	private EditText etEmail;    //邮箱
	private EditText etCompany;   //公司
	private EditText etPosition;  //职位
	private EditText etIntroduction;//投资人简介
	private EditText etInvestmentDomain;//投资领域
	private String phoneNumber,password,comfirmPassword,code,email,company,position,cardUrl,investmentDomain,introduction;
	private ImageView ivCard;
	private BroadcastReceiver receiver;
	private Bitmap image;
	private File cardFile;
	private Button btRegist,btGetCode;
	private  BmobFile bmobFile;
	private User currentUser;
	private Bitmap sysBit;
	private File systemFile;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragmen_investor_regist, null);
		
		

		/*
		 * 绑定控件
		 */
		etUserName=(EditText)view.findViewById(R.id.et_investor_regist_name);
		etPassword=(EditText)view.findViewById(R.id.et_investor_regist_password);
		etComfirmPassword=(EditText)view.findViewById(R.id.et_investor_regist_password_again);
		etValidateCode=(EditText)view.findViewById(R.id.et_investor_Code);
		etCompany=(EditText)view.findViewById(R.id.et_investor_company);
		etIntroduction=(EditText)view.findViewById(R.id.et_investor_introduction);
		etInvestmentDomain=(EditText)view.findViewById(R.id.et_investor_investmentDomain);
		etPosition=(EditText)view.findViewById(R.id.et_investor_position);
		ivCard=(ImageView)view.findViewById(R.id.iv_investor_card);
		btRegist=(Button)view.findViewById(R.id.bt_investor_regist_login);
		btGetCode=(Button)view.findViewById(R.id.bt_investor_Getcode_Id);
		receiver=new InnerReceiver();


		/*
		 * 添加监听
		 */
		ivCard.setOnClickListener(this);
		btRegist.setOnClickListener(this);
		btGetCode.setOnClickListener(this);


		/*
		 * 注册广播接受者
		 */
		receiver=new InnerReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(INTENT_ACTION_CARD);

		getActivity().registerReceiver(receiver, filter);

		//avaterIsExists();



		return view;
	}

	/**
	 * 
	 */
	private void saveSystemBitmap(){
		systemFile=new File(Environment.getExternalStorageDirectory(),"chuangbang/"+"system.jpg");
		FileOutputStream out=null;
		try {
			File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			sysBit=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_launcher);
			out=new FileOutputStream(systemFile);
			sysBit.compress(Bitmap.CompressFormat.PNG, 100, out);
			//handler.obtainMessage(210,"image").sendToTarget();
			out.close();
			out.flush();
		}catch (IOException e) {				
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View view) {
		Intent intent=null;
		switch (view.getId()) {
		/*
		 * 选择名片
		 */
		case R.id.iv_investor_card:
			intent=new Intent(getActivity(),MenuDialog.class);
			intent.putExtra("card", "card");
			startActivity(intent);
			break;

			/*
			 * 注册
			 */
		case R.id.bt_investor_regist_login:
			if(image==null){
				bmobSignUp();

			}else{
				uploadCard();
			}


			break;
			/*
			 * 获取验证码
			 */
		case R.id.bt_investor_Getcode_Id:
			getCode();
			
			break;


		}

	}

	/*
	 * 广播接受
	 */
	private class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action=intent.getAction();
			if(INTENT_ACTION_CARD.equals(action)){
				image=intent.getParcelableExtra(INTENT_EXTRA_CARD);
				Log.i("IMAGES",image.toString());
				saveBitmap(image);
				ivCard.setImageBitmap(image);
				//uploadCard();//上传名片到bmob
			}
		}
	}

	/**
	 * 保存进入investor表
	 */

	private void saveInvestorInfo(){
		currentUser=BmobUser.getCurrentUser(getActivity(),User.class);
		InvestorInfo info=new InvestorInfo();
		introduction=etIntroduction.getText().toString();
		investmentDomain=etInvestmentDomain.getText().toString();
		info.setIntroduction(introduction);
		info.setInvestmentDomain(investmentDomain);
		info.setVerifiedState(1);
	
		if(bmobFile!=null)
			info.setBusinessCardUrl(bmobFile);		
		Log.i("inre", currentUser.getObjectId());
		info.setOwner(currentUser);
		info.save(getActivity(), new SaveListener() {

			@Override
			public void onSuccess() {

				Toast.makeText(getActivity(), "注册成功，后台正在认证身份！", Toast.LENGTH_LONG).show();
				getActivity().finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getActivity(), "注册表失败", Toast.LENGTH_LONG).show();
				cardFile.delete();

			}
		});
	}


	/**
	 * 更新信息进入user表
	 */
	private void updateInvestor(){
		Toast.makeText(getActivity(), "正在注册......", Toast.LENGTH_LONG).show();
		//	BmobFile ava=new BmobFile();
		phoneNumber=etUserName.getText().toString();
		User newUser=new User();
		newUser.setNickName(phoneNumber);
		newUser.setMemberType(5);
		//Bitmap bit=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.avater);
		//ava.setUrl("http://img1.shenchuang.com/2016/0224/1456278142749.jpg");
		//bmobFile=systemFile;
		//newUser.setAvatar(ava);
		BmobUser user=BmobUser.getCurrentUser(getActivity());
		newUser.update(getActivity(), user.getObjectId(),new UpdateListener() {

			@Override
			public void onSuccess() {
				saveInvestorInfo();
			}

			@Override
			public void onFailure(int arg0, String arg1) {

			}
		});
	}
	/*
	 * 获取验证码
	 */
	private void getCode(){
		Toast.makeText(getActivity(), "正获取验证码......", Toast.LENGTH_LONG).show();
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
					Log.i("inre", "短信id：" + smsId);// 用于查询本次短信发送详情
				}
				else{
					
					ex.printStackTrace();
				}
			}}); 
	}

	/*
	 * 执行bmob注册
	 */
	private void bmobSignUp(){
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

		BmobUser user=new BmobUser();
		user.setUsername("TEL"+phoneNumber);
		user.setPassword(password);
		user.setMobilePhoneNumber(phoneNumber);
		user.setMobilePhoneNumberVerified(true);

		user.signUp(getActivity(), new SaveListener() {

			@Override
			public void onSuccess() {

				//getActivity().finish();
				//Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT).show();
				updateInvestor();


			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
				//cardFile.delete();

			}
		});

	}

	private void saveBitmap(Bitmap bitmap){
		cardFile=new File(Environment.getExternalStorageDirectory(),"chuangbang/card.jpg");
		FileOutputStream out=null;
		try {
			File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			out=new FileOutputStream(cardFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

			out.close();
			out.flush();
		}catch (IOException e) {				
			e.printStackTrace();
		}

	}

	/**
	 * 上传名片到bmob服务器
	 */
	private void uploadCard(){
		String path=cardFile.getAbsolutePath();
		Log.i("Pro", "logo的本地路径"+path);
		//String path=Environment.getExternalStorageDirectory().getPath()+"/chuangbang/"+logoName;
		//Log.i("Avater", "头像在本地的地址"+path);
		BTPFileResponse response = BmobProFile.getInstance(getActivity()).upload(path, new UploadListener() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0, String arg1, BmobFile arg2) {
				bmobFile=arg2;
				bmobSignUp();

			}

			@Override
			public void onProgress(int arg0) {
				// TODO Auto-generated method stub

			}
		});


	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}




}
