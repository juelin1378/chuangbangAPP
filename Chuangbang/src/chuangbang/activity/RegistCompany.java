package chuangbang.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;
import chuangbang.entity.ServiceRegCompany;
import chuangbang.entity.User;
import chuangbang.util.Final;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RegistCompany extends Activity implements Final, OnClickListener{
	private TableRow trBirthDay;
	private EditText etFoundName,etPhoneNumber,etCompany,etCompanyNature,etCompanyBusiness,etNumberOfShareholders;
	private TextView tvBirthDay;
	private String foundName,birthday,phoneNumber,comPanyName,companyNature,companyBusiness;
	private Integer numberOfShare;//股东人数
	private Button btnCommit;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DatePickerDialog dateDialog;
	private OnDateSetListener callback;
	private int year,monthOfYear,dayOfMonth;
	private Handler handler;
	private User currentUser;
	private final int BIRTHDAY=127;
	private ImageButton ibBack;




	private void setView(){
		etFoundName=(EditText)findViewById(R.id.et_set_mine_nico);
		etPhoneNumber=(EditText)findViewById(R.id.et_set_phone_number);
		tvBirthDay=(TextView)findViewById(R.id.tv_set_year_of_birth);
		etCompany=(EditText)findViewById(R.id.et_set_company_name);
		etCompanyNature=(EditText)findViewById(R.id.et_set_nature_of_company);
		etCompanyBusiness=(EditText)findViewById(R.id.et_set_business_scope);//经营范围
		etNumberOfShareholders=(EditText)findViewById(R.id.et_number_of_shareholders);//股东人数		
		trBirthDay=(TableRow)findViewById(R.id.tr_set_year_of_birth);
		btnCommit=(Button)findViewById(R.id.btn_commit);
		ibBack=(ImageButton)findViewById(R.id.ib_company_back);
	}
	private void setOnClick(){
		trBirthDay.setOnClickListener(this);
		btnCommit.setOnClickListener(this);
		ibBack.setOnClickListener(this);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_company);
		setView();
		setOnClick();
		getCurrentDate();
		currentUser=BmobUser.getCurrentUser(RegistCompany.this,User.class);
		handler=new InnerHandler();

	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tr_set_year_of_birth:
			setDatePickDialog("请选择出生日期",BIRTHDAY);
			break;
		case R.id.btn_commit:
			commit();
			break;
		case R.id.ib_company_back:
			finish();
			break;

		}

	}

	class InnerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case BIRTHDAY:
				birthday=msg.obj.toString();
				tvBirthDay.setText(birthday);
				break;


			}

		}
	}
	private void commit(){
		CharSequence html1=null;
		foundName=etFoundName.getText().toString();
		phoneNumber=etPhoneNumber.getText().toString();
		comPanyName=etCompany.getText().toString();
		companyNature=etCompanyNature.getText().toString();
		companyBusiness=etCompanyBusiness.getText().toString();
	//	numberOfShare=Integer.parseInt(etNumberOfShareholders.getText().toString());
		if(foundName.length()==0){
			html1 = Html.fromHtml("<font color='black'>名字不能为空</font>");
			etFoundName.setError(html1);
			return ;
		}
		if(phoneNumber.length()==0){
			html1 = Html.fromHtml("<font color='black'>手机号不能为空</font>");
			etPhoneNumber.setError(html1);
			return ;
		}
		if(comPanyName.length()==0){
			html1 = Html.fromHtml("<font color='black'>公司名字不能为空</font>");
			etCompany.setError(html1);
			return ;
		}
		if(companyNature.length()==0){
			html1 = Html.fromHtml("<font color='black'>公司性质不能为空</font>");
			etCompanyNature.setError(html1);
			return ;
		}
		if(companyBusiness.length()==0){
			html1 = Html.fromHtml("<font color='black'>经营范围不能为空</font>");
			etCompanyBusiness.setError(html1);
			return ;
		}
		if(birthday==null){
		//	html1 = Html.fromHtml("<font color='black'>出生日期不能为空</font>");
			Toast.makeText(RegistCompany.this, "出生日期不能为空",Toast.LENGTH_LONG).show();
			return ;
			
		}

		BmobDate bmobBirth=null;
		try {
			Date birthDate=sdf.parse(birthday);
			bmobBirth=new BmobDate(birthDate);
			ServiceRegCompany com=new ServiceRegCompany();
			com.setFounderName(foundName);
			com.setContactPhone(phoneNumber);
			com.setCompanyNamePreferred(comPanyName);
			com.setCompanyNature(companyNature);
			com.setCompanyBusinessScope(companyBusiness);
			if(etNumberOfShareholders.getText().toString().length()==0){
				numberOfShare=0;
			}else{
				numberOfShare=Integer.parseInt(etNumberOfShareholders.getText().toString());
			}
			com.setCompanyShareholdersCount(numberOfShare);
			com.setApplicant(currentUser);
			com.setFounderBirth(bmobBirth);
			com.setState(1);
			com.save(RegistCompany.this, new SaveListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(RegistCompany.this, "申请成功，后台正在处理", Toast.LENGTH_LONG).show();
					finish();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(RegistCompany.this, arg1, Toast.LENGTH_LONG).show();

				}
			});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}










	private void setDatePickDialog(String title,final int point){
		dateDialog=new DatePickerDialog(RegistCompany.this, callback, year, monthOfYear, dayOfMonth);
		dateDialog.setTitle(title);
		dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				Log.i("time", "日期"+dateDialog.getDatePicker().getYear());
				Calendar ca=Calendar.getInstance();
				ca.set(Calendar.YEAR,dateDialog.getDatePicker().getYear());
				ca.set(Calendar.MONTH,dateDialog.getDatePicker().getMonth());
				ca.set(Calendar.DAY_OF_MONTH, dateDialog.getDatePicker().getDayOfMonth());
				String da=sdf.format(ca.getTime());
				handler.obtainMessage(point,da).sendToTarget();
				//	handler.obtainMessage(1221, ca.getTime()).sendToTarget();
			}
		});
		dateDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dateDialog.cancel();

			}
		});

		callback=new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {


			}
		};
		dateDialog.show();
	}


	/*
	 * 获取当前系统的的年月日
	 */
	public void getCurrentDate(){
		Calendar c=Calendar.getInstance();


		year=c.get(Calendar.YEAR);
		monthOfYear=c.get(Calendar.MONTH);
		dayOfMonth=c.get(Calendar.DAY_OF_MONTH);


	}



}
