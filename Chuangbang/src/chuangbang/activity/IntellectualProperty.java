package chuangbang.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import chuangbang.entity.ServiceIntellectual;
import chuangbang.entity.User;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class IntellectualProperty extends Activity implements OnItemSelectedListener, OnClickListener{
	private EditText etName,etPhone,etCompanyName,etRemarks;
	private String name,phone,companyName,remarks;
	private Integer handleType;

	private Spinner sp;
	private List<String> list=new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private Button btCommit;
	private User currentUser;
	private ImageButton ibBack;





	private void setView(){
		etName=(EditText)findViewById(R.id.et_intellectual_set_mine_nico);
		etPhone=(EditText)findViewById(R.id.et_intellectual_set_phone_number);
		etCompanyName=(EditText)findViewById(R.id.et_intellectual_set_company_name);
		etRemarks=(EditText)findViewById(R.id.et_intellectual_set_remarks);
		btCommit=(Button)findViewById(R.id.btn_intellectual_commit);
		ibBack=(ImageButton)findViewById(R.id.ib_intellectual_back);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intellectual_property);

		setView();
		list.add("商标注册");
		list.add("专利申请");
		list.add("版权登记");
		list.add("高新认证");

		sp=(Spinner)findViewById(R.id.sp_set_management_type);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sp.setAdapter(adapter);
		currentUser=BmobUser.getCurrentUser(IntellectualProperty.this,User.class);

		sp.setOnItemSelectedListener(this);
		btCommit.setOnClickListener(this);
		ibBack.setOnClickListener(this);
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		handleType=position+1;
		Log.i("spi",""+ handleType);

	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {


	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_intellectual_commit:
			commit();
			
			break;
		case R.id.ib_intellectual_back:
			finish();
			break;

		}

	}

	private void commit(){
		CharSequence html1=null;
		ServiceIntellectual intel=new ServiceIntellectual();
		name=etName.getText().toString();
		phone=etPhone.getText().toString();
		companyName=etCompanyName.getText().toString();
		remarks=etRemarks.getText().toString();
		Log.i("spi",name);
		intel.setContactName(name);
		intel.setContactPhone(phone);
		intel.setCompanyName(companyName);
		intel.setRemarks(remarks);
		intel.setHandleType(handleType);
		intel.setState(1);
		intel.setApplicant(currentUser);

		if(name.length()==0){
			html1 = Html.fromHtml("<font color='black'>名字不能为空</font>"); 
			etName.setError(html1);
			
			return ;
		}


		if(phone.length()==0){
			html1 = Html.fromHtml("<font color='black'>手机号不能为空</font>"); 
			etPhone.setError(html1);
			return ;
		}
		if(companyName.length()==0){
			html1 = Html.fromHtml("<font color='black'>公司名不能为空</font>"); 
			etCompanyName.setError(html1);
			return ;
		}
		intel.save(IntellectualProperty.this, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(IntellectualProperty.this, "提交成功，后台正在处理！", Toast.LENGTH_LONG).show();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(IntellectualProperty.this, arg1, Toast.LENGTH_LONG).show();
				Log.i("spi",arg1);
			}
		});

	}





}
