package chuangbang.activity;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;

import chuangbang.entity.Status;
import chuangbang.entity.User;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class SendMineStatus extends Activity{
	private EditText etMyStatus;
	private GridView gvPhoto;
	private String avatarPath;
	private String text;
	private Status status;
	private int count=0;
	private User author;
	
	
	
	private void setView(){
//
//		etMyStatus=(EditText)findViewById(R.id.et_write_status);
//		gvPhoto=(GridView)findViewById(R.id.gv_photo);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_mine_status);
		setView();
		author=BmobUser.getCurrentUser(this,User.class);
		Log.i("Tag", "getCurrentUser"+author.toString());
		
		
	}
	
	
	public void doClick(View v){
		switch (v.getId()) {
	
//		case R.id.bt_send_status:
//			sendStatus();
//		
//			finish();
		}
	}
	
	/*
	 * 发表动态
	 */
	private void sendStatus(){
		status=new Status();
		text=etMyStatus.getText().toString();
		status.setText(text);
		//status.setCommentCount(count);
		//BmobRelation relation=new BmobRelation();
	//	relation.add(author);
	//	status.setCommentList(relation);
		//添加一对一关联
		status.setAuthor(author);
		status.save(this,new SaveListener() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(SendMineStatus.this, "发表动态成功",Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				
				
			}
		});
	}
}
