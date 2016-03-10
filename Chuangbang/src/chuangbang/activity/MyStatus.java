package chuangbang.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.testpic.PublishedActivity;

import chuangbang.adapter.MyStatusAdapter;
import chuangbang.entity.Status;
import chuangbang.entity.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MyStatus extends Activity{
	private User user;
	private BaseAdapter adapter;
	private Status status;
	private List<Status> data;
	private ListView lvMineStatus;

	private String date;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_status);
		user=BmobUser.getCurrentUser(this, User.class);
		Log.i("status", user.toString());
	//	status=new Status();
		data=new ArrayList<Status>();
		adapter=new MyStatusAdapter(data,this);
		lvMineStatus=(ListView)findViewById(R.id.lv_mine_status);
		//lvMineStatus.setAdapter(adapter);
		BmobQuery<Status> query=new BmobQuery<Status>();
		query.addWhereEqualTo("author", user);
		query.order("createAt");
		query.include("author");
		query.findObjects(this, new FindListener<Status>() {
			
			@Override
			public void onSuccess(List<Status> arg0) {
			//user=arg0.get(1).getAuthor();
			
				
				
				data.addAll(arg0);
				
				//adapter.notifyDataSetChanged();
			//	Log.i("status", data.toString());
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.i("status", "错误"+arg1+"编号"+arg0);
				
			}
		});
		
		
	}
	

	public void doClick(View v){
		Intent intent=null;
		switch (v.getId()) {
	
		case R.id.bt_write_status:
			intent=new Intent(MyStatus.this,PublishedActivity.class);
			startActivity(intent);
			break;
			
		case R.id.ib_back:
			finish();
			break;
		}
	}
	
	private void getMineStatus(){
		
	}
}
