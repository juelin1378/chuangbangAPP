package chuangbang.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import org.jivesoftware.smackx.muc.UserStatusListener;
import org.jivesoftware.smackx.packet.Nick;
import org.json.JSONObject;

import chuangbang.adapter.SetMineAdapter;
import chuangbang.app.ChuangApp;
import chuangbang.entity.User;
import chuangbang.util.Final;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.bmob.btp.e.a.in;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SettingMine extends Activity implements OnClickListener, Final {

	private EditText etName, etDescription, etCompany, etPosition, etEmail;
	private ImageView ivLogo;
	private Button btBack, btSave;
	private RadioGroup sex;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_mine);
		initView();

		query();

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					User user = (User) msg.obj;
					etName.setText(user.getNickName());
					etDescription.setText(user.getDescription());
					etPosition.setText(user.getWorkingPosition());
					etCompany.setText(user.getWorkingCompany());
					etEmail.setText(user.getEmail());
					//etPhone.setText(user.getMobilePhoneNumber()+"");
				
					if(user.getSex()!=null){

						if (user.getSex() == 1) {
							sex.check(R.id.rb_boy);
						} else {
							sex.check(R.id.rb_girl);
						}
					}
					break;

				
				}
			};
		};
	}

	private void initView() {
		etName = (EditText) findViewById(R.id.et_set_mine_name);
		etDescription = (EditText) findViewById(R.id.et_set_mine_description);
		etCompany = (EditText) findViewById(R.id.et_set_mine_company);
		etPosition = (EditText) findViewById(R.id.et_set_mine_position);
		etEmail = (EditText) findViewById(R.id.et_set_mine_email);
		//etLocation = (EditText) findViewById(R.id.et_set_mine_location);
		//etPhone = (EditText) findViewById(R.id.et_set_mine_phone);
		
		btBack = (Button) findViewById(R.id.btn_set_mine_back);
		btSave = (Button) findViewById(R.id.btn_set_mine_save);
		sex = (RadioGroup) findViewById(R.id.rg_sex);

		btBack.setOnClickListener(this);
		btSave.setOnClickListener(this);

		
	}

	private void query() {
		User currentUser = BmobUser.getCurrentUser(this, User.class);
		BmobQuery<User> query = new BmobQuery<User>();
		query.getObject(this, currentUser.getObjectId(),
				new GetListener<User>() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("mine", "个人信息查询失败" + arg0);
			}

			@Override
			public void onSuccess(User user) {
				// TODO Auto-generated method stub
				Log.i("mine", "个人信息" + user.toString());
				Message msg = new Message();
				msg.what = 1;
				msg.obj = user;
				handler.sendMessage(msg);

			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_set_mine_back:
			finish();
			break;

		case R.id.btn_set_mine_save:
			update();
			break;
		}
	}

	/**
	 * 提交个人信息
	 */
	private void update() {
		String name = etName.getText().toString();
		String company = etCompany.getText().toString();
		String description = etDescription.getText().toString();
		String position = etPosition.getText().toString();
		//String location = etLocation.getText().toString();
		String email = etEmail.getText().toString();
		
		Integer mySex = 1;
		if(sex.getCheckedRadioButtonId() != R.id.rb_boy){
			mySex = 2;
		}

		User user = BmobUser.getCurrentUser(this, User.class);

		User newUser = new User();
		newUser.setNickName(name);
		newUser.setWorkingCompany(company);
		newUser.setDescription(description);
		newUser.setWorkingPosition(position);
		newUser.setEmail(email);
		newUser.setSex(mySex);
		//newUser.setMobilePhoneNumber(phone);
		newUser.update(this, user.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("mine", "修改成功");
				Toast.makeText(SettingMine.this, "修改成功", Toast.LENGTH_SHORT).show();
				finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("mine", "修改失败："+arg0);
				Toast.makeText(SettingMine.this, arg1, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
