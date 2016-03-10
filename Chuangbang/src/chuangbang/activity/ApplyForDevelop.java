package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;

public class ApplyForDevelop extends Activity implements OnClickListener{
	private TableRow trAppDevelop,trWebDevelop;
	private Button btCommit;
	private ImageButton ibBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_for_develop);
		trAppDevelop=(TableRow)findViewById(R.id.tr_app_develop);
		trWebDevelop=(TableRow)findViewById(R.id.tr_web_develop);
		btCommit=(Button)findViewById(R.id.btrn_commit);
		ibBack=(ImageButton)findViewById(R.id.ib_develop_back);
		setListener();
		
	}
	/*
	 * 添加监听
	 */
	private void setListener(){
		trAppDevelop.setOnClickListener(this);
		trWebDevelop.setOnClickListener(this);
		btCommit.setOnClickListener(this);
		ibBack.setOnClickListener(this);
	}

	
	
	
	
	@Override
	public void onClick(View arg0) {
		Intent intent=null;
		switch (arg0.getId()) {
		case R.id.tr_app_develop:
			intent=new Intent(ApplyForDevelop.this,MicroMsgOrAppDevelop.class);
			startActivity(intent);
			break;
		case R.id.tr_web_develop:
			
			intent=new Intent(ApplyForDevelop.this,WebDevelop.class);
			startActivity(intent);
			break;
			
			/*
			 * 提交申请
			 */
		case R.id.btrn_commit:
			break;
		case R.id.ib_develop_back:
			finish();
			break;
		}
	}
}
