package chuangbang.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

import chuangbang.adapter.MyFavoriteAdapter;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import chuangbang.util.Final;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyProject extends Activity implements OnItemClickListener,Final{
	private List<Project> data;
	private ListView lvProject;
	private User currentUser;
	private BaseAdapter adapter;
	private BroadcastReceiver receiver;
	private Button btSave;
	
	
	private void setView(){
		lvProject=(ListView)findViewById(R.id.lv_project);
		btSave=(Button)findViewById(R.id.bt_new_project);
	}
	
	private void setOnClick(){
		lvProject.setOnItemClickListener(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myproject);
		setView();
		setOnClick();
		currentUser=BmobUser.getCurrentUser(this,User.class);
		data=new ArrayList<Project>();
		adapter=new MyFavoriteAdapter(data, MyProject.this);
		lvProject.setAdapter(adapter);
		//注册广播接收者
		receiver=new InnerReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(INTENT_ACTION_UPDATE_MY_PROJECT);
		registerReceiver(receiver, filter);
		queryMyPro();
		
	}
	private void queryMyPro(){

		BmobQuery<Project> query=new BmobQuery<Project>();
		query.addWhereEqualTo("owner",new BmobPointer(currentUser)); 
		
		query.findObjects(this, new FindListener<Project>() {
			
			@Override
			public void onSuccess(List<Project> arg0) {
				Log.i("myPro", "个数"+arg0.toString());
				data.clear();
				data.addAll(arg0);
				adapter.notifyDataSetChanged();
				if(arg0.size()>2){
					btSave.setEnabled(false);
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.i("favo", "错误"+arg0+arg1);
				
			}
		});
	
	}
	private class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String action=arg1.getAction();
			if(INTENT_ACTION_UPDATE_MY_PROJECT.equals(action)){
				queryMyPro();
			}
			
		}
		
	}
	
	
	
	public void doClick(View v){
		Intent intent=null;
		switch (v.getId()) {
		case R.id.bt_new_project:
			intent=new Intent(MyProject.this,NewMyProject.class);
			startActivity(intent);
			break;

		case R.id.bt_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Project pro=data.get(position);
		Log.i("myPro", pro.toString());
		Intent intent=new Intent(MyProject.this,MyProDetailsActivity.class);
		intent.putExtra("project", pro);
		startActivity(intent);
		
	}
}
