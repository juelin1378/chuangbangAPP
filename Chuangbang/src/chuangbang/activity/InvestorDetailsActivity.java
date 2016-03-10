package chuangbang.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import chuangbang.entity.InvestorInfo;
import chuangbang.entity.Meeting;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InvestorDetailsActivity extends Activity implements OnClickListener {
	
	private Button btnSend;
	private List<Project> pros;
	private InvestorInfo ii;
	private TextView tvName,tvCompanyName,tvPosition,tvDomain,tvInvestmentstage,tvDescription;
	private ImageView ivAvater;
	private User user;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investor_detail);
		initView();
		setInvestorView();
		queryProData();
		
	}
	
	private void setInvestorView(){
		tvName.setText(user.getNickName());
		tvCompanyName.setText(user.getWorkingCompany());
		tvPosition.setText(user.getWorkingPosition());
		tvDomain.setText(ii.getInvestmentDomain());
		tvInvestmentstage.setText(ii.getInvestmentStage());
		tvDescription.setText(user.getDescription());
		
	}

	private void initView() {
		btnSend = (Button) findViewById(R.id.btn_activity_investor_send);
		tvName=(TextView)findViewById(R.id.tv_activity_investor_name);
		tvCompanyName=(TextView)findViewById(R.id.tv_activity_investor_companyname);
		tvPosition=(TextView)findViewById(R.id.tv_activity_investor_position);
		tvDomain=(TextView)findViewById(R.id.tv_activity_investor_investmentdomain);
		tvDescription=(TextView)findViewById(R.id.tv_activity_investor_description);
		tvInvestmentstage=(TextView)findViewById(R.id.tv_activity_investor_investmentstage);
		ivAvater=(ImageView)findViewById(R.id.iv_activity_investor_avater);
		
		Intent intent = getIntent();
		ii = (InvestorInfo) intent.getSerializableExtra("investor");
		Log.i("investor", "ii:"+ii.getOwner().toString());
		user=ii.getOwner();
		pros = new ArrayList<Project>();
		handler = new InnerHandler();
		
		
		btnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_activity_investor_send:
			doSQLQuery();
			break;

		}
		
	}
	
	private class InnerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0x1233){
				sendMeeting();
			}
		}
	}
	
	/**
	 * 查询当前用户发起约谈的次数
	 */
	private void doSQLQuery(){
		BmobQuery<Meeting> query = new BmobQuery<Meeting>();
		List<BmobQuery<Meeting>> and = new ArrayList<BmobQuery<Meeting>>();
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf0.format(new Date());
		Log.i("time", "当前日期"+time);
		
		//大于00：00：00
		BmobQuery<Meeting> q1 = new BmobQuery<Meeting>();
		String start = time+" 00:00:00";  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date  = null;
		try {
			date = sdf.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		q1.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(date));
		and.add(q1);
		//小于23：59：59
		BmobQuery<Meeting> q2 = new BmobQuery<Meeting>();
		String end = time+" 23:59:59"; 
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date1  = null;
		try {
			date1 = sdf1.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		q2.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date1));
		and.add(q2);
		//申请人是当前用户
		BmobQuery<Meeting> q3 = new BmobQuery<Meeting>();
		
		User currentUser = BmobUser.getCurrentUser(this, User.class);
		q3.addWhereEqualTo("applyUser", currentUser);
		and.add(q3);
		//添加复合与查询
		query.and(and);
		query.findObjects(this, new FindListener<Meeting>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("send", "查询失败："+arg0);
			}

			@Override
			public void onSuccess(List<Meeting> mes) {
				// TODO Auto-generated method stub
				Log.i("send", "查询成功："+mes.size());
				if(mes.size()<3 && mes.size()>=0){
					handler.sendEmptyMessage(0x1233);
				}else{
					Toast.makeText(InvestorDetailsActivity.this, "今天你的约谈已经3次了，等明天在约谈", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private Project project;
	protected void sendMeeting() {	
		
		final User user = BmobUser.getCurrentUser(this, User.class);
		Log.i("abc", "pros:"+pros.size());
		String[] items = null;
		if(pros.size()==3){
			items = new String[]{pros.get(0).getName(),pros.get(1).getName(),pros.get(2).getName()};
		}else if(pros.size()==2){
			items = new String[]{pros.get(0).getName(),pros.get(1).getName()};
		}else if(pros.size()==1){
			items = new String[]{pros.get(0).getName()};
		}else if(pros.size()==0){
			Toast.makeText(InvestorDetailsActivity.this, "你没有项目可投，请先去创建项目吧", Toast.LENGTH_LONG).show();
			return;
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择你约谈的项目？");
		builder.setItems(items, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int which) {
				project = pros.get(which);
				final EditText et = new EditText(InvestorDetailsActivity.this);
				et.setLines(2);
				et.setHint("请输入留言内容");
				AlertDialog dialog = new AlertDialog.Builder(InvestorDetailsActivity.this).setTitle("请输入您的留言")
						.setIcon(android.R.drawable.ic_dialog_info).setView(et)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								String result = et.getText().toString();
								Log.i("edit", "输入框的内容：" + result);
								setMeeting(user, project.getOwner(), project,result);

							}
						}).setNegativeButton("取消", null).create();
				dialog.show();
				
			}
			
		});
		builder.create();
		builder.show();
		
	}
	

	/**
	 * 约谈
	 * @param user
	 * @param owner
	 * @param project
	 */
	protected void setMeeting(User user, User owner, Project project,String text) {
		Meeting meeting = new Meeting();
		meeting.setApplyUser(user);
		meeting.setInviteUser(owner);
		meeting.setProject(project);
		meeting.setApplyText(text);
		meeting.setState(1);
		meeting.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("meeting", "已发起约谈");
				Toast.makeText(InvestorDetailsActivity.this, "已发起约谈", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("meeting", "发起约谈失败："+arg0+":"+arg1);
				Toast.makeText(InvestorDetailsActivity.this, "约谈发送失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	/**
	 * 获取我的项目
	 */
	private void queryProData(){
		BmobUser user = BmobUser.getCurrentUser(this, User.class);
		BmobQuery<Project> project = new BmobQuery<Project>();
		project.addWhereEqualTo("owner", user);
		project.include("owner");
		project.setLimit(3);
		project.findObjects(this, new FindListener<Project>() {
			
			@Override
			public void onSuccess(List<Project> projects) {
				// TODO Auto-generated method stub
				Log.i("investor", "查询结果："+projects.toString());
				pros.clear();
				pros.addAll(projects);
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("investor", "查询失败："+arg0);
			}
		});
		
	}
	
}
