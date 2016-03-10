package chuangbang.activity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import chuangbang.entity.Meeting;
import chuangbang.entity.User;
import android.app.Activity;
import android.app.SearchManager.OnCancelListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * 约谈详情
 * @author Administrator
 *
 */
public class MeetingDetailActivity extends Activity implements android.view.View.OnClickListener{
	
	private TextView tvCreateDate,tvApplyName,tvInviteName,tvState,tvApplyContent;
	private Button btnAgree, btnRefuse;
	private Meeting meet;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting_detail);
		Intent intent = getIntent();
		meet = (Meeting) intent.getSerializableExtra("meeting");
		Log.i("meeting", "约谈："+meet.toString());
		initView(meet);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateView(meet.getObjectId());
	}

	private void updateView(String objectId) {
		// TODO Auto-generated method stub
		BmobQuery<Meeting> meet = new BmobQuery<Meeting>();
		meet.include("applyUser,inviteUser");
		meet.getObject(this, objectId, new GetListener<Meeting>() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("meeting", "meetingDetail->数据更新失败");
			}
			
			@Override
			public void onSuccess(Meeting me) {
				// TODO Auto-generated method stub
				tvCreateDate.setText(me.getCreatedAt());
				tvApplyName.setText(me.getApplyUser().getNickName());
				tvInviteName.setText(me.getInviteUser().getNickName());
				tvApplyContent.setText(me.getApplyText());
				tvState.setText(me.getState()+"");
			}
		});
	}

	private void initView(Meeting meet) {
		String inviteUserId = meet.getInviteUser().getObjectId();
		

		tvCreateDate = (TextView) findViewById(R.id.tv_meeting_detail_date);
		tvApplyName = (TextView) findViewById(R.id.tv_meeting_detail_applyname);
		tvInviteName = (TextView) findViewById(R.id.tv_meeting_detail_invitename);
		tvState = (TextView) findViewById(R.id.tv_meeting_detail_state);
		tvApplyContent = (TextView) findViewById(R.id.tv_meeting_detail_applytext);
		
		
		btnAgree = (Button) findViewById(R.id.btn_meeting_detail_agree);
		btnRefuse = (Button) findViewById(R.id.btn_meeting_detail_refuse);
		btnAgree.setOnClickListener(this);
		btnRefuse.setOnClickListener(this);
//		Log.i("meeting", "时间："+meet.getCreateAt());
//		Log.i("meeting", "申请人姓名："+meet.getApplyUser().getNickName());
//		Log.i("meeting", "受邀人姓名："+meet.getInviteUser().getNickName());
//		Log.i("meeting", "状态："+meet.getState());
//		Log.i("meeting", "留言："+meet.getApplyText());
		
		tvCreateDate.setText(meet.getCreatedAt());
		tvApplyName.setText(meet.getApplyUser().getNickName());
		tvInviteName.setText(meet.getInviteUser().getNickName());
		tvApplyContent.setText(meet.getApplyText());
		tvState.setText(meet.getState()+"");
		
		if (inviteUserId!=null && inviteUserId.equals(BmobUser.getCurrentUser(this, User.class).getObjectId())) {
			if( meet.getState()==1){
				tvState.setText("2");
				update(meet,2);
			}
		} else {
			tvState.setText(meet.getState() + "");
			btnAgree.setVisibility(View.GONE);
			btnRefuse.setVisibility(View.GONE);
		}
		
	}
	
	/**
	 * 更新约谈信息
	 * @param meet
	 */
	private void update(Meeting meet,Integer number) {
		Meeting meeting = new Meeting();
		meeting.setState(number);
		meeting.update(this, meet.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("meeting", "约谈信息更新成功");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("meeting", "约谈信息更新失败："+arg0);
			}
		});
	}
		

	
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (view.getId()) {
		case R.id.btn_meeting_detail_agree:
			intent = new Intent(this, AgreeMeetingActivity.class);
			intent.putExtra("meeting", meet);
			startActivity(intent);
			break;

		case R.id.btn_meeting_detail_refuse:
			intent = new Intent(this, RefuseMeetingActivity.class);
			intent.putExtra("meetId", meet.getObjectId());
			startActivity(intent);
			break;

		}
	}
}
