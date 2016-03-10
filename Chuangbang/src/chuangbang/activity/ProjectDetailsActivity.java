package chuangbang.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.google.gson.Gson;

import chuangbang.adapter.ProjectCommentAdapter;
import chuangbang.entity.Comment;
import chuangbang.entity.Meeting;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import chuangbang.util.SharedPreferencesUtils;
import chuangbang.view.MyListView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��Ŀ����
 * 
 * @author chuangbang
 */
public class ProjectDetailsActivity extends Activity implements OnClickListener {

	private Button bntCollectionOrEdit, bntComment, bntInterview;
	private ImageView ivProLogo;
	private Integer favoriteCount, commentCount;
	private TextView tvProTitle;// 项目标题
	private TextView tvProUserNick;// 项目所有者昵称
	private TextView tvProUserPosition;// 项目所有I者职位
	private TextView tvProState;// 项目状态
	private TextView tvProDomain;// 项目领域标签
	private TextView tvProDescription;// 项目描述
	private TextView tvProPainPointer;// 行业痛点
	private TextView tvProSolution;// 解决方案
	private TextView tvProCompetitors;// 竞争产品
	private TextView tvProAdvantage;// 竞争优势
	private TextView tvProBusinessModel;// 商业模式
	private TextView tvProFinancingState;// 融资阶段
	private TextView tvProFinancingAmount;// 融资金额
	private TextView tvProSransferShare;// 出让股份
	private User currentUser;
	private User proOwner;
	private String proId;// 所看项目的objectId
	private Project currentPro;
	// private Handler handler;
	private MyListView mlvComment;
	private List<Comment> comments;
	private ProjectCommentAdapter adapter;

	/**
	 * 初始化控件
	 */
	private void initView() {

		bntCollectionOrEdit = (Button) findViewById(R.id.bnt_activity_project_collection_or_edit);
		bntComment = (Button) findViewById(R.id.bnt_activity_project_comment);
		bntInterview = (Button) findViewById(R.id.bnt_activity_project_meeting);

		mlvComment = (MyListView) findViewById(R.id.mlv_activity_project_comment);
		comments = new ArrayList<Comment>();
		adapter = new ProjectCommentAdapter(this, comments);
		mlvComment.setAdapter(adapter);

		ivProLogo = (ImageView) findViewById(R.id.iv_activity_project_logo);

		tvProTitle = (TextView) findViewById(R.id.tv_activity_project_title);
		tvProUserNick = (TextView) findViewById(R.id.tv_activity_project_name);
		tvProUserPosition = (TextView) findViewById(R.id.tv_activity_project_working_position);
		tvProState = (TextView) findViewById(R.id.tv_activity_project_statu);
		tvProDomain = (TextView) findViewById(R.id.tv_activity_project_domain);
		tvProDescription = (TextView) findViewById(R.id.tv_activity_project_description);
		tvProPainPointer = (TextView) findViewById(R.id.tv_activity_project_pain_pointer);
		tvProSolution = (TextView) findViewById(R.id.tv_activity_project_solution);
		tvProCompetitors = (TextView) findViewById(R.id.tv_activity_project_competitors);
		tvProAdvantage = (TextView) findViewById(R.id.tv_activity_project_advantage);
		tvProBusinessModel = (TextView) findViewById(R.id.tv_activity_project_businessModel);
		tvProFinancingState = (TextView) findViewById(R.id.tv_activity_project_financing);
		tvProFinancingAmount = (TextView) findViewById(R.id.tv_activity_project_money);
		tvProSransferShare = (TextView) findViewById(R.id.tv_activity_project_shares);

		bntCollectionOrEdit.setOnClickListener(this);
		bntComment.setOnClickListener(this);
		bntInterview.setOnClickListener(this);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_details);
		initView();
		// loadProject(proId);
		init();
		queryProjectComments(proId);

	}

	private void init() {

		Intent intent = getIntent();
		currentPro = (Project) intent.getSerializableExtra("project");
		Log.i("project", "当前的项目：" + currentPro.toString());
		proOwner = currentPro.getOwner();
		proId = currentPro.getObjectId();
		Log.i("project", "获取到的项目id" + proId);
		currentUser = BmobUser.getCurrentUser(ProjectDetailsActivity.this,
				User.class);

		String currentUserObject = BmobUser.getCurrentUser(
				ProjectDetailsActivity.this).getObjectId();
		Log.i("project", "当前用户Id" + currentUserObject);
		Log.i("project", "当前项目Id" + currentPro.getObjectId());
		if (currentUserObject != null
				&& currentUserObject.equals(currentPro.getObjectId())) {
			bntCollectionOrEdit.setText("编辑项目");
		} else {
			bntCollectionOrEdit.setText("收藏");
		}

		User user = currentPro.getOwner();
		tvProTitle.setText(currentPro.getName());
		tvProUserNick.setText(user.getNickName());
		tvProUserPosition.setText(user.getWorkingPosition());
		tvProState.setText(currentPro.getState());
		tvProDomain.setText(currentPro.getDomain());
		tvProDescription.setText(currentPro.getDescription());
		tvProPainPointer.setText(currentPro.getPainPointer());
		tvProSolution.setText(currentPro.getSolution());
		tvProCompetitors.setText(currentPro.getCompetitors());
		tvProAdvantage.setText(currentPro.getAdvantage());
		tvProBusinessModel.setText(currentPro.getBusinessModel());
		tvProFinancingAmount.setText(currentPro.getFinancingAmount()+"");
		// tvProFinancingState.setText(currentPro.getFinancingState());
		tvProSransferShare.setText(currentPro.getTransferShare()+"");

		favoriteCount = currentPro.getFavoriteUserCount();
		commentCount = currentPro.getCommentCount();
	}

	/**
	 * 查询当前用户发起约谈的次数
	 */
	private void doSQLQuery() {
		BmobQuery<Meeting> query = new BmobQuery<Meeting>();
		List<BmobQuery<Meeting>> and = new ArrayList<BmobQuery<Meeting>>();
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf0.format(new Date());
		Log.i("date", "当前日期："+time);

		// 大于00：00：00
		BmobQuery<Meeting> q1 = new BmobQuery<Meeting>();
		String start = time + " 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		q1.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
		and.add(q1);
		// 小于23：59：59
		BmobQuery<Meeting> q2 = new BmobQuery<Meeting>();
		String end = time + " 23:59:59";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		try {
			date1 = sdf1.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		q2.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date1));
		and.add(q2);
		// 申请人是当前用户
		Log.i("project", "申请用户："+currentUser.toString());
		BmobQuery<Meeting> q3 = new BmobQuery<Meeting>();
		q3.addWhereEqualTo("applyUser", currentUser);
		and.add(q3);
		// 添加复合与查询
		query.and(and);
		query.findObjects(this, new FindListener<Meeting>() {

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("send", "查询失败：" + arg0);
			}

			@Override
			public void onSuccess(List<Meeting> mes) {
				Log.i("send", "查询成功：" + mes.size());
				if (mes.size() < 3) {
					sendMeeting();
				} else {
					Toast.makeText(ProjectDetailsActivity.this,
							"今天你的约谈已经3次了，等明天在约谈", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * 发起约谈
	 */
	private void sendMeeting() {

		final EditText et = new EditText(this);
		et.setLines(2);
		et.setHint("请输入留言内容");

		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请输入您的留言")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						String result = et.getText().toString();
						Log.i("edit", "输入框的内容：" + result);
						if (!proOwner.getObjectId().equals(
								currentUser.getObjectId())) {
							setMeeting(proOwner, currentUser, currentPro,
									result);
						} else {
							Toast.makeText(ProjectDetailsActivity.this,
									"该项目是你自己的，不能发起约谈", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}).setNegativeButton("取消", null).create();
		dialog.show();

	}

	/**
	 * 将数据发送到服务器上
	 * 
	 * @param proOwner2
	 * @param currentUser2
	 * @param applyText
	 */
	protected void setMeeting(User proOwner, final User currentUser,
			Project currentPro, String applyText) {

		final Meeting meeting = new Meeting();
		meeting.setApplyUser(currentUser);
		meeting.setInviteUser(proOwner);
		meeting.setProject(currentPro);
		meeting.setApplyText(applyText);
		meeting.setState(1);
		meeting.save(ProjectDetailsActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				Log.i("meeting", "已发起约谈");
				User user = new User();
				user.setObjectId(currentUser.getObjectId());
				BmobRelation relation = new BmobRelation();
				relation.add(meeting);
				user.setMeetingList(relation);
				user.update(ProjectDetailsActivity.this, new UpdateListener() {

					@Override
					public void onSuccess() {
						Log.i("meeting", "关联User成功");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Log.i("meeting", "关联User失败" + arg0);
					}
				});

				Log.i("meeting", "已发起约谈");
				Toast.makeText(ProjectDetailsActivity.this, "已发起约谈",
						Toast.LENGTH_SHORT).show();

				Toast.makeText(ProjectDetailsActivity.this, "已发起约谈",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("meeting", "发起约谈失败：" + arg0 + ":" + arg1);
				Toast.makeText(ProjectDetailsActivity.this, "约谈发送失败",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.bnt_activity_project_collection_or_edit:
			String str = bntCollectionOrEdit.getText().toString();
			if (str.equals("收藏")) {
				setMyFavorite();
			}else{
				updatePro(proId);
			}
			break;
		case R.id.bnt_activity_project_comment:
			intent = new Intent(this, CommentActivity.class);
			intent.putExtra("user", currentUser);
			intent.putExtra("id", proId);
			intent.putExtra("commentCount", commentCount);
			startActivity(intent);
			break;
		case R.id.bnt_activity_project_meeting:
			doSQLQuery();
		}

	}
	/**
	 * 编辑项目
	 * @param proId2
	 */
	private void updatePro(String proId2) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 获取当前项目所有评论列表
	 * 
	 * @param proId
	 */
	private void queryProjectComments(String proId) {
		// 查询喜欢这个项目的所有用户，因此查询的是用户表
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		Project pro = new Project();
		pro.setObjectId(proId);
		// commentList是Project表中的字段，用来存储所有该项目的评论
		query.include("author");
		query.addWhereRelatedTo("commentList", new BmobPointer(pro));
		query.findObjects(this, new FindListener<Comment>() {

			@Override
			public void onSuccess(List<Comment> object) {
				// Log.i("project", "查询个数："+object.toString());
				commentCount = object.size();
				comments.clear();
				comments.addAll(object);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int code, String msg) {
				Log.i("project", "查询失败：" + code + "-" + msg);
			}
		});
	}

	/**
	 * 收藏
	 */
	private void setMyFavorite() {
		Project pro = new Project();
		// 添加要评论的项目的ObjectId
		pro.setObjectId(proId);
		BmobRelation relation = new BmobRelation();
		relation.add(currentUser);
		pro.setFavoriteUserList(relation);
		pro.setFavoriteUserCount(favoriteCount + 1);

		pro.update(ProjectDetailsActivity.this, new UpdateListener() {

			@Override
			public void onSuccess() {
				// "收藏成功1",Toast.LENGTH_LONG).show();
				User user = new User();
				Log.i("project", "当前用户Id" + currentUser.getObjectId());
				// currentUser.setObjectId(currentUser.getObjectId());
				BmobRelation relation = new BmobRelation();
				Log.i("project", currentPro.toString());
				relation.add(currentPro);
				user.setFavoriteProjectList(relation);
				user.update(ProjectDetailsActivity.this,
						currentUser.getObjectId(), new UpdateListener() {

							@Override
							public void onSuccess() {
								Toast.makeText(ProjectDetailsActivity.this,
										"收藏成功", Toast.LENGTH_LONG).show();

							}

							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(ProjectDetailsActivity.this,
										"收藏失败2" + arg1, Toast.LENGTH_LONG)
										.show();
								Log.i("project", arg1);

							}
						});

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(ProjectDetailsActivity.this, "收藏失败1" + arg1,
						Toast.LENGTH_LONG).show();

			}
		});
	}

	// /**
	// * 查询数据
	// *
	// * @param proId
	// */
	// private void loadProject(String proId) {
	//
	// BmobQuery<Project> query = new BmobQuery<Project>();
	// query.getObject(this, proId, new GetListener<Project>() {
	//
	// @Override
	// public void onFailure(int arg0, String arg1) {
	// Log.i("project", "查询失败：" + arg0 + ":" + arg1);
	// }
	//
	// @Override
	// public void onSuccess(Project pro) {
	// // Log.i("project",pro.toString());
	// //proOwner = pro.getOwner();
	// currentPro = pro;
	// handler.obtainMessage(2222).sendToTarget();
	//
	// // 获取当前用户object
	// String currentUserObject = BmobUser.getCurrentUser(
	// ProjectDetailsActivity.this).getObjectId();
	// // Log.i("project","当前用户Id"+currentUserObject);
	// Log.i("project", "当前项目Id" + pro.getObjectId());
	//
	// if (currentUserObject != null
	// && currentUserObject.equals(pro.getObjectId())) {
	// bntCollectionOrEdit.setText("编辑项目");
	// Log.i("project", "bianji");
	// } else {
	// bntCollectionOrEdit.setText("收藏");
	// Log.i("project", "souicang");
	// }
	// }
	// });
	// }

	// class InnerHandler extends Handler {
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 2222:
	// User user = currentPro.getOwner();
	// tvProTitle.setText(currentPro.getName());
	// tvProUserNick.setText(user.getNickName());
	// tvProUserPosition.setText(user.getWorkingPosition());
	// tvProState.setText(currentPro.getState());
	// tvProDomain.setText(currentPro.getDomain());
	// tvProDescription.setText(currentPro.getDescription());
	// tvProPainPointer.setText(currentPro.getPainPointer());
	// tvProSolution.setText(currentPro.getSolution());
	// tvProCompetitors.setText(currentPro.getCompetitors());
	// tvProAdvantage.setText(currentPro.getAdvantage());
	// tvProBusinessModel.setText(currentPro.getBusinessModel());
	// tvProFinancingAmount.setText(currentPro.getFinancingAmount());
	// // tvProFinancingState.setText(currentPro.getFinancingState());
	// tvProSransferShare.setText(currentPro.getTransferShare());
	//
	// favoriteCount = currentPro.getFavoriteUserCount();
	// commentCount = currentPro.getCommentCount();
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	// }

}
