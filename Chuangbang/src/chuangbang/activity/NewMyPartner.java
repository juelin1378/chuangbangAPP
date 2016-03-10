package chuangbang.activity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import chuangbang.entity.PartnerDemand;
import chuangbang.entity.User;
import chuangbang.util.Final;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewMyPartner extends Activity implements OnClickListener,Final{
	private EditText etTitle,etText,etName,etCellphone;
	private String title,text,name,cellPhone;
	private Button bt;
	private User currentUser;

	private void setView(){
		etTitle=(EditText)findViewById(R.id.et_new_my_partner_title);
		etText=(EditText)findViewById(R.id.et_new_my_partner_text);
		etName=(EditText)findViewById(R.id.et_new_my_partner_name);
		etCellphone=(EditText)findViewById(R.id.et_new_my_partner_cellphone);
		bt=(Button)findViewById(R.id.bt_new_partner_commit);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_my_partner);
		setView();
		currentUser=BmobUser.getCurrentUser(this,User.class);
		bt.setOnClickListener(this);
	}

	/**
	 * 发布我的合伙人需求
	 */
	private void save(){
		CharSequence html1=null;
		PartnerDemand par=new PartnerDemand();
		title=etTitle.getText().toString();
		text=etText.getText().toString();
		name=etName.getText().toString();
		cellPhone=etCellphone.getText().toString();
		if(title.length()==0){
			html1 = Html.fromHtml("<font color='black'>名字不能为空</font>");
			etTitle.setError(html1);
			return ;
		}
		if(text.length()==0){
			html1 = Html.fromHtml("<font color='black'>内容不能为空</font>");
			etText.setError(html1);
			return ;
		}
		if(name.length()==0){
			html1 = Html.fromHtml("<font color='black'>姓名不能为空</font>");
			etName.setError(html1);
			return ;
		}
		if(cellPhone.length()==0){
			html1 = Html.fromHtml("<font color='black'>联系方式不能为空</font>");
			etCellphone.setError(html1);
			return ;
		}
		par.setTitle(title);
		par.setText(text);
		par.setContactName(name);
		par.setContactPhone(cellPhone);
		par.setAuthor(currentUser);
		par.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				Intent in=new Intent(INTENT_ACTION_UPDATE_MY_PARTNER);
				sendBroadcast(in);
				Toast.makeText(NewMyPartner.this, "发布成功", Toast.LENGTH_LONG).show();
				finish();
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}




	@Override
	public void onClick(View arg0) {
		save();

	}



}
