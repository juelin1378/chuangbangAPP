package chuangbang.activity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import chuangbang.entity.Comment;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommentActivity extends Activity implements OnClickListener {

	private EditText etCommnetText;
	private Button btnSendComent, btnBack;
	private User user;
	private String proId;
	private Integer commentCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("user");
		proId=intent.getStringExtra("id");
		commentCount=intent.getIntExtra("commentCount",0);
		Log.i("comment", commentCount+"");
		etCommnetText = (EditText) findViewById(R.id.et_activity_comment_text);
		btnSendComent = (Button) findViewById(R.id.btn_activity_comment_send);
		btnBack = (Button) findViewById(R.id.btn_activity_comment_back);
		btnSendComent.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_activity_comment_back:
			finish();
			break;

		case R.id.btn_activity_comment_send:
			sendComment();
			break;
		}
	}

	private void sendComment() {
		// 发评论
		String text = etCommnetText.getText().toString().trim();
		if (text != null && !text.isEmpty()) {
			final Comment comment = new Comment();
			comment.setAuthor(user);
			comment.setText(text);
			comment.save(this, new SaveListener() {

				@Override
				public void onSuccess() {
					Log.i("comment", "数据添加成功" + comment.toString());

					Project pro=new Project();
					pro.setObjectId(proId);
					BmobRelation relation=new BmobRelation();
					relation.add(comment);
					pro.setCommentList(relation);
					pro.setCommentCount(commentCount+1);
					pro.update(CommentActivity.this, new UpdateListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub


							Toast.makeText(CommentActivity.this, "内容已发送",
									Toast.LENGTH_SHORT).show();
							etCommnetText.getText().clear();
							finish();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}
					});




				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Log.i("comment", "添加失败：" + arg0 + ":" + arg1);
				}
			});
		} else {
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
		}
	}
}
