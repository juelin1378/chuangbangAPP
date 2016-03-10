package chuangbang.activity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import chuangbang.entity.ServiceLegalConsulting;
import chuangbang.entity.User;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class LegalAdvice extends Activity implements OnClickListener{
	private ImageButton ibBack;
	private Button btCommit;
	private EditText etNico,etPhoneNumber,etCompanyName,etScore,etRemarks;
	private String nico,phoneNumber,companyName,companyNature,scope,remarks;
	
	private User currentUser;
	
	
	private void setView(){
		etNico=(EditText)findViewById(R.id.et_legal_set_mine_nico);
		etPhoneNumber=(EditText)findViewById(R.id.et_legal_set_phone_number);
		etCompanyName=(EditText)findViewById(R.id.et_legal_set_company_name);
	
		etScore=(EditText)findViewById(R.id.et_legal_set_business_scope);
		etRemarks=(EditText)findViewById(R.id.et_legal_remarks);
		
		ibBack=(ImageButton)findViewById(R.id.ib_legal_back);
		btCommit=(Button)findViewById(R.id.btn_legal_commit);
	}
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legal_advice);
		currentUser=BmobUser.getCurrentUser(LegalAdvice.this,User.class);
		setView();
		
		
		btCommit.setOnClickListener(this);
		ibBack.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_legal_commit:
			commit();
			break;

		case R.id.ib_legal_back:
			finish();
			break;
		}
		
	}
	
	private void commit(){
		CharSequence html1=null;
		ServiceLegalConsulting legal =new ServiceLegalConsulting();
		nico=etNico.getText().toString();
		phoneNumber=etPhoneNumber.getText().toString();
		companyName=etCompanyName.getText().toString();
		
		scope=etScore.getText().toString();
		remarks=etRemarks.getText().toString();
		if(nico.length()==0){
			html1 = Html.fromHtml("<font color='black'>名字不能为空</font>");
			etNico.setError(html1);
			return ;
		}
		if(phoneNumber.length()==0){
			html1 = Html.fromHtml("<font color='black'>手机号不能为空</font>");
			etPhoneNumber.setError(html1);
			return ;
		}
		if(companyName.length()==0){
			html1 = Html.fromHtml("<font color='black'>公司名不能为空</font>");
			etCompanyName.setError(html1);
			return ;
		}
	
		if(scope.length()==0){
			html1 = Html.fromHtml("<font color='black'>资讯类型不能为空</font>");
			etScore.setError(html1);
			return ;
		}
		
		legal.setContactName(nico);
		legal.setContactPhone(phoneNumber);
		legal.setCompanyName(companyName);
		legal.setConsultingType(scope);
		legal.setApplicant(currentUser);
		legal.setState(1);
		legal.save(LegalAdvice.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(LegalAdvice.this, "申请成功，后台正在处理", Toast.LENGTH_LONG).show();
				finish();
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(LegalAdvice.this,arg1, Toast.LENGTH_LONG).show();
				
			}
		});
		
	}
	
	
	

}
