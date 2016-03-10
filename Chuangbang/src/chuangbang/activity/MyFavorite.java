package chuangbang.activity;

import java.util.ArrayList;
import java.util.List;

import chuangbang.adapter.BusineeStatusAdapter;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MyFavorite extends Activity{
	private User current;
	private List<Project> data;
	private BaseAdapter adapter;
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favorite);
		current=BmobUser.getCurrentUser(MyFavorite.this,User.class);
		lv=(ListView)findViewById(R.id.lv_activity_favorite);
		
		
		data=new ArrayList<Project>();
		adapter=new BusineeStatusAdapter(data, MyFavorite.this);
		lv.setAdapter(adapter);
		queryMyFavorite();
	}
	
	
	private void queryMyFavorite(){
		BmobQuery<Project> query=new BmobQuery<Project>();
		User newUser=new User();
		
		query.include("owner");
		query.addWhereRelatedTo("favoriteProjectList", new BmobPointer(current));
		Log.i("favo", "查询");
		query.findObjects(this, new FindListener<Project>() {
			
			@Override
			public void onSuccess(List<Project> arg0) {
				Log.i("favo", "个数"+arg0.toString());
				data.addAll(arg0);
				adapter.notifyDataSetChanged();
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.i("favo", "错误"+arg0+arg1);
				
			}
		});
	}
}
