package chuangbang.activity;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.google.gson.Gson;

import chuangbang.entity.Meeting;
import chuangbang.entity.RefuseReasons;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class RefuseMeetingActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {

	private CheckBox cbRefuseOne, cbRefuseTwo, cbRefuseThree, cbRefuseFour,
			cbRefuseFive, cbRefuseSix, cbRefuseSeven, cbRefuseEight,
			cbRefuseOther;
	private EditText etRefuseOther;
	private Button btnSave;
	private RefuseReasons rr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refuse_meeting);
		setupView();
		
	}

	/**
	 * 初始化控件
	 */
	private void setupView() {
		// TODO Auto-generated method stub
		cbRefuseOne = (CheckBox) findViewById(R.id.cb_refuse_meet_01);
		cbRefuseTwo = (CheckBox) findViewById(R.id.cb_refuse_meet_02);
		cbRefuseThree = (CheckBox) findViewById(R.id.cb_refuse_meet_03);
		cbRefuseFour = (CheckBox) findViewById(R.id.cb_refuse_meet_04);
		cbRefuseFive = (CheckBox) findViewById(R.id.cb_refuse_meet_05);
		cbRefuseSix = (CheckBox) findViewById(R.id.cb_refuse_meet_06);
		cbRefuseSeven = (CheckBox) findViewById(R.id.cb_refuse_meet_07);
		cbRefuseEight = (CheckBox) findViewById(R.id.cb_refuse_meet_08);
		cbRefuseOther = (CheckBox) findViewById(R.id.cb_refuse_meet_09);
		etRefuseOther = (EditText) findViewById(R.id.et_refuse_meet_other);
		btnSave = (Button) findViewById(R.id.btn_refuse_meet_save);

		cbRefuseOne.setOnCheckedChangeListener(this);
		cbRefuseTwo.setOnCheckedChangeListener(this);
		cbRefuseThree.setOnCheckedChangeListener(this);
		cbRefuseFour.setOnCheckedChangeListener(this);
		cbRefuseFive.setOnCheckedChangeListener(this);
		cbRefuseSix.setOnCheckedChangeListener(this);
		cbRefuseSeven.setOnCheckedChangeListener(this);
		cbRefuseEight.setOnCheckedChangeListener(this);
		cbRefuseOther.setOnCheckedChangeListener(this);
		btnSave.setOnClickListener(this);
		
		rr = new RefuseReasons();
	}

	@Override
	public void onCheckedChanged(CompoundButton cb, boolean flg) {
		
		if (cb.equals(cbRefuseOne) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseOne.getText().toString());
			rr.setOne(cbRefuseOne.getText().toString());
		}
		if (cb.equals(cbRefuseTwo) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseTwo.getText().toString());
			rr.setTwo(cbRefuseTwo.getText().toString());
		}
		if (cb.equals(cbRefuseThree) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseThree.getText().toString());
			rr.setThree(cbRefuseThree.getText().toString());
		}
		if (cb.equals(cbRefuseFour) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseFour.getText().toString());
			rr.setFour(cbRefuseFour.getText().toString());
		}
		if (cb.equals(cbRefuseFive) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseFive.getText().toString());
			rr.setFive(cbRefuseFive.getText().toString());
		}
		if (cb.equals(cbRefuseSix) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseSix.getText().toString());
			rr.setSix(cbRefuseSix.getText().toString());
		}
		if (cb.equals(cbRefuseSeven) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseSeven.getText().toString());
			rr.setSeven(cbRefuseSeven.getText().toString());
		}
		if (cb.equals(cbRefuseEight) && flg) {
			Log.i("refuse", "你选择了：" + cbRefuseEight.getText().toString());
			rr.setEight(cbRefuseEight.getText().toString());
		}
		if (cb.equals(cbRefuseOther)) {
			if (flg) {
				etRefuseOther.setEnabled(true);
			} else {
				etRefuseOther.getText().clear();
				etRefuseOther.setEnabled(false);
			}

		}

	}

	private void save(RefuseReasons rr) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String objectId = intent.getStringExtra("meetId");
		
		String other = etRefuseOther.getText().toString();
		if (other != null)
			rr.setOther(other);
		
		Gson gson = new Gson();
		String gsonStr = gson.toJson(rr);
		Log.i("refuse", "拒绝理由："+gsonStr);
		
		Meeting meet = new Meeting();
		meet.setState(4);
		meet.setRefuseResource(gsonStr);
		meet.update(this, objectId, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i("refuse", "数据更新成功");
				finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("refuse", "数据更新失败:"+arg0);
				Toast.makeText(RefuseMeetingActivity.this, "数据提交失败，请检查您的网络", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		save(rr);
	}

}
