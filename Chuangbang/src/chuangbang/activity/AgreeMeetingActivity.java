package chuangbang.activity;

import cn.bmob.v3.listener.UpdateListener;
import chuangbang.entity.Meeting;
import chuangbang.util.CommonUtils;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 同意约谈
 * 
 * @author Administrator
 * 
 */
public class AgreeMeetingActivity extends Activity implements OnClickListener {

	private EditText etContacts, etPhone, etLocation, etTime;
	private Button btnSave;
	private Meeting meet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agree_meeting);
		setupView();

		Intent intent = getIntent();
		meet = (Meeting) intent.getSerializableExtra("meeting");
		Log.i("agreemeeting", "约谈详情传来的数据：" + meet.toString());

	}

	private void updateMeeting(String objectId) {

		// TODO Auto-generated method stub
		String contacts = etContacts.getText().toString();
		String phone = etPhone.getText().toString();
		String location = etLocation.getText().toString();
		String time = etTime.getText().toString();
		// TODO 判空
		if(contacts==null){
			etContacts.setError("联系人不能为空！！");
			return;
		}
		if(phone==null){
			etPhone.setError("联系电话不能为空！！");
			return;
		}
		if(etLocation==null){
			etLocation.setError("地点不能为空！！");
			return;
		}
		
		Meeting meet = new Meeting();
		meet.setState(3);//将状态改为3
		meet.setContactName(contacts);
		meet.setContactPhone(phone);
		meet.setMeetingPosition(location);
		meet.setMeetingTime(CommonUtils.getDate(time));
		meet.update(this, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("agreemeeting", "数据更新成功！");
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("agreemeeting", "数据更新失败！" + arg0);
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		etContacts = (EditText) findViewById(R.id.et_agree_meeting_contacts);
		etPhone = (EditText) findViewById(R.id.et_argee_meeting_phone);
		etLocation = (EditText) findViewById(R.id.et_argee_meeting_location);
		etTime = (EditText) findViewById(R.id.et_agree_meeting_time);
		btnSave = (Button) findViewById(R.id.btn_agree_meeting_save);
		etTime.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_agree_meeting_save:
			updateMeeting(meet.getObjectId());
			break;

		case R.id.et_agree_meeting_time:
			DatePickerDialog datePicker = new DatePickerDialog(
					AgreeMeetingActivity.this, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							etTime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
						}
					}, 2016, 1, 1);
			datePicker.show();
			break;

		case R.id.btn_agree_meeting_back:
			finish();
			break;
		}
	}
}
