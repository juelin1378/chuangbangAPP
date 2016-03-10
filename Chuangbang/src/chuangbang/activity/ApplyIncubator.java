package chuangbang.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import chuangbang.entity.ServiceIncubator;
import chuangbang.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.DbUtils.DaoConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ApplyIncubator extends Activity implements OnClickListener{

	private TableRow trBirthDay;//出生日期
	private TableRow trGradualtedTime;//毕业时间
	private TableRow trSettledExpectTime;//期望入驻时间

	private EditText etSetMyNico;//用户昵称
	private EditText etSetPhoneNumber;//手机号
	private EditText etSetNowAddress;
	private EditText etSetGraduateSchool;//毕业院校
	private EditText etSetCompanyName;
	private EditText etSetTeamNumber;//团队人数
	private EditText etSetRemarks;//备注

	private String userNico,phoneNumber,nowAddress,graduateSchool,companyName,remarks;
	private String birthDay,gradualtedTime,expectTime;
	private Integer termNumberCount;




	private TextView tvFounderName,tvBirthDay,tvCellPhone,tvLocation,tvComany,tvSchool,tvGradualtendTime,tvSettledExpectTime,tvRemarks;
	private int year,monthOfYear,dayOfMonth,hourOfDay,minute;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	private String result;
	private Handler handler;
	private DatePickerDialog dateDialog;
	private OnDateSetListener callback;
	private TimePickerDialog timeDialog;
	private Button btSend;
	private Date birthDate,gradDate,excepteDate;
	private ImageButton ivBack;








	private final int BIRTHDAY=124;
	private final int GRADUATIME=125;//毕业时间
	private final int EXPECTIONTIME=126;//期望进入孵化器时间



	private User currentUser;



	private void setView(){

		trBirthDay=(TableRow)findViewById(R.id.tr_set_year_of_birth);

		trGradualtedTime=(TableRow)findViewById(R.id.tr_set_graduate_time);

		trSettledExpectTime=(TableRow)findViewById(R.id.tr_set_expected_time_to_be_settled);

		tvBirthDay=(TextView)findViewById(R.id.tv_set_year_of_birth);
	

		tvGradualtendTime=(TextView)findViewById(R.id.tv_set_graduate_time);
	



		etSetTeamNumber=(EditText)findViewById(R.id.et_set_team_number);
		etSetMyNico=(EditText) findViewById(R.id.et_set_mine_nico);
		etSetPhoneNumber=(EditText)findViewById(R.id.et_set_phone_number);
		etSetCompanyName=(EditText) findViewById(R.id.et_set_company_name);
		etSetGraduateSchool=(EditText) findViewById(R.id.et_set_graduate_school);
		etSetNowAddress=(EditText) findViewById(R.id.et_set_now_address);
		etSetRemarks=(EditText)findViewById(R.id.et_set_remark);
		tvSettledExpectTime=(TextView)findViewById(R.id.tv_set_expected_time_to_be_settled);
		btSend=(Button)findViewById(R.id.btn_commit);
		ivBack=(ImageButton)findViewById(R.id.ib_incubator_back);
	}
	private void setOnClick(){

		trBirthDay.setOnClickListener(this);
		trGradualtedTime.setOnClickListener(this);
		trSettledExpectTime.setOnClickListener(this);
		btSend.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	private void setDatePickDialog(String title,final int point){
		dateDialog=new DatePickerDialog(ApplyIncubator.this, callback, year, monthOfYear, dayOfMonth);
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_incubator);
		setView();
		setOnClick();
		getCurrentDate();
		currentUser=BmobUser.getCurrentUser(ApplyIncubator.this,User.class);
		//setDatePickDialog();

		handler=new InnerHandler();
	}

	/**
	 * 消息处理
	 * @author Administrator
	 *
	 */
	private class InnerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case BIRTHDAY:
				birthDay=msg.obj.toString();
				tvBirthDay.setText(birthDay);
				break;
			case GRADUATIME:
				gradualtedTime=msg.obj.toString();
				tvGradualtendTime.setText(gradualtedTime);
				break;
			case EXPECTIONTIME:
				expectTime=msg.obj.toString();
				tvSettledExpectTime.setText(expectTime);
				break;
			}
		}
	}



	/*
	 * 对话框
	 */
	private void dialogShow(String title,final int point){

		LayoutInflater inflater=LayoutInflater.from(this);
		LinearLayout ll=(LinearLayout)inflater.inflate(R.layout.view_dialog, null);
		TextView tv=(TextView)ll.findViewById(R.id.tv_dialog_title);
		tv.setText(title);
		final Dialog dialog=new AlertDialog.Builder(ApplyIncubator.this).create();
		dialog.show();
		dialog.getWindow().setContentView(ll);
		final EditText et=(EditText)ll.findViewById(R.id.et_edit);
		et.setInputType(InputType.TYPE_CLASS_TEXT);
		Button bt1=(Button)ll.findViewById(R.id.bt_left);//左边的按钮
		Button bt2=(Button)ll.findViewById(R.id.bt_right);

		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//确定按钮的返回值
				result=et.getText().toString();
				handler.obtainMessage(point, result).sendToTarget();
				dialog.dismiss();
			}
		});

		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

	}


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tr_set_year_of_birth:
			setDatePickDialog("请选择出生日期",BIRTHDAY);
			break;
		case R.id.tr_set_graduate_time:
			setDatePickDialog("请选择毕业时间", GRADUATIME);
			break;
		case R.id.tr_set_expected_time_to_be_settled:
			setDatePickDialog("请选择期望进驻时间", EXPECTIONTIME);
			break;
		case R.id.btn_commit:
			sendTo();
			break;
		case R.id.ib_incubator_back:
			finish();
			break;
		}

	}
	/*
	 * 获取当前系统的的年月日
	 */
	public void getCurrentDate(){
		Calendar c=Calendar.getInstance();


		year=c.get(Calendar.YEAR);
		monthOfYear=c.get(Calendar.MONTH);
		dayOfMonth=c.get(Calendar.DAY_OF_MONTH);
		hourOfDay=c.get(Calendar.HOUR_OF_DAY);
		minute=c.get(Calendar.MINUTE);


	}

	/*
	 * 发送
	 */
	private void sendTo(){
		CharSequence html1=null;
		ServiceIncubator cub=new ServiceIncubator();
		BmobDate daa=null;
		try {
			userNico=etSetMyNico.getText().toString();
			phoneNumber=etSetPhoneNumber.getText().toString();
			nowAddress=etSetNowAddress.getText().toString();
			graduateSchool=etSetGraduateSchool.getText().toString();
			companyName=etSetCompanyName.getText().toString();
			//termNumberCount=Integer.parseInt(etSetTeamNumber.getText().toString());
			remarks=etSetRemarks.getText().toString();
			
			if(userNico.length()==0){
				html1 = Html.fromHtml("<font color='black'>名字不能为空</font>");
				etSetMyNico.setError(html1);
				return ;
			}
			if(phoneNumber.length()==0){
				html1 = Html.fromHtml("<font color='black'>手机号不能为空</font>");
				etSetPhoneNumber.setError(html1);
				return ;
			}
			if(companyName.length()==0){
				html1 = Html.fromHtml("<font color='black'>公司名不能为空</font>");
				etSetCompanyName.setError(html1);
				return ;
				//Toast.makeText(ApplyIncubator.this, "公司名字不能为空", Toast.LENGTH_LONG).show();
			}
			if(nowAddress.length()==0){
				html1 = Html.fromHtml("<font color='black'>地址不能为空</font>");
				etSetNowAddress.setError(html1);
				return ;
			}
			if(etSetTeamNumber.getText().toString().length()==0){
				html1 = Html.fromHtml("<font color='black'>团队人数不能为空</font>");
				//Toast.makeText(ApplyIncubator.this, "团队人数不能为空", Toast.LENGTH_LONG).show();
				etSetTeamNumber.setError(html1);
				return ;
			}
			if(graduateSchool.length()==0){
				html1 = Html.fromHtml("<font color='black'>毕业院校不能为空</font>");
				etSetGraduateSchool.setError(html1);
				return ;
			}
			if(birthDay==null){
				Toast.makeText(ApplyIncubator.this, "出生日期不能为空", Toast.LENGTH_LONG).show();
				return ;
			}
			if(gradualtedTime==null){
				Toast.makeText(ApplyIncubator.this, "毕业日期不能为空", Toast.LENGTH_LONG).show();
				return ;
			}
			if(expectTime==null){
				Toast.makeText(ApplyIncubator.this, "期望日期不能为空", Toast.LENGTH_LONG).show();
				return ;
			}
			
			cub.setFounderName(userNico);
			cub.setContactPhone(phoneNumber);
			cub.setCompanyName(companyName);
			cub.setRemarks(remarks);
			cub.setApplicant(currentUser);
			birthDate=sdf.parse(birthDay);
			daa=new BmobDate(birthDate);
			cub.setFounderBirth(daa);
			gradDate=sdf.parse(gradualtedTime);
			daa=new BmobDate(gradDate);
			cub.setGradualtedTime(daa);
			excepteDate=sdf.parse(expectTime);
			daa=new BmobDate(excepteDate);
			cub.setSettledExpectTime(daa);
			cub.setCurrentLocation(nowAddress);
			cub.setGradualtedSchoolName(graduateSchool);
			termNumberCount=Integer.parseInt(etSetTeamNumber.getText().toString());
			cub.setTermNumberCount(termNumberCount);
			cub.setState(1);



			cub.save(ApplyIncubator.this, new SaveListener() {

				@Override
				public void onSuccess() {

					Toast.makeText(ApplyIncubator.this, "申请成功，后台人员正在处理", Toast.LENGTH_LONG).show();
					finish();

				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Log.i("favo",arg1);
				}
			});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
