package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MineResume extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_resume);
	}
	
	
	
	
	/*
	 * 跳转按钮
	 */
	public void doClick(View v){
		Intent intent=null;
		switch (v.getId()) {
		case R.id.bt_resume_add_education:
			intent=new Intent(MineResume.this,EducationalBackground.class);
			startActivity(intent);
			break;
			//工作经历
		case R.id.bt_resume_add_work:
			intent=new Intent(MineResume.this,WorkExperience.class);
			startActivity(intent);
			break;
			//个人优势
		case R.id.bt_resume_add_goodnes:		
			intent=new Intent(MineResume.this,MyGoodnes.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
