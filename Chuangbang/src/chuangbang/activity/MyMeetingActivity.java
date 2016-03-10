package chuangbang.activity;

import java.util.ArrayList;
import java.util.List;

import chuangbang.adapter.MyMeetingAdapter;
import chuangbang.entity.Meeting;
import chuangbang.entity.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
/**
 * 我的约谈
 * @author Administrator
 *
 */
public class MyMeetingActivity extends Activity implements OnItemClickListener{
	
	private ListView lvMeeting;
	private List<Meeting> meets;
	private MyMeetingAdapter adapter;
	private User currentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_meeting);
		currentUser = BmobUser.getCurrentUser(this, User.class);
		Log.i("meeting", "当前用户："+currentUser.getUsername());
		
		lvMeeting = (ListView) findViewById(R.id.lv_mine_meeting);
		meets = new ArrayList<Meeting>();
		adapter = new MyMeetingAdapter(this, meets);
		lvMeeting.setAdapter(adapter);
		
		lvMeeting.setOnItemClickListener(this);
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		queryMeeting(currentUser);
	}
	

	private void queryMeeting(BmobUser currentUser) {
		BmobQuery<Meeting> meet1 = new BmobQuery<Meeting>();
		meet1.addWhereEqualTo("applyUser",currentUser);
		BmobQuery<Meeting> meet2 = new BmobQuery<Meeting>();
		meet2.addWhereEqualTo("inviteUser",currentUser);
		List<BmobQuery<Meeting>> and = new ArrayList<BmobQuery<Meeting>>();
		and.add(meet1);
		and.add(meet2);
		
		BmobQuery<Meeting> meeting = new BmobQuery<Meeting>();
		meeting.include("project,applyUser,inviteUser");
		meeting.or(and);
		meeting.setLimit(50);
		meeting.findObjects(this, new FindListener<Meeting>() {
			
			@Override
			public void onSuccess(List<Meeting> list) {
				// TODO Auto-generated method stub
				Log.i("meeting", "我的约谈："+list.toString());
				meets.clear();
				meets.addAll(list);
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("meeting", "查询失败"+arg0);
			}
		});
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View convertView, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MeetingDetailActivity.class);
		intent.putExtra("meeting", meets.get(position));
		startActivity(intent);
	}
}

