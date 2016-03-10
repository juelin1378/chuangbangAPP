package chuangbang.activity;

import chuangbang.entity.Project;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SeeOneProject extends Activity{
	private TextView tvProName,tvProContName,tvProState,tvProLocation,tvProLink,tvProDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_one_project);
		
		tvProContName=(TextView)findViewById(R.id.tv_see_one_project_procontname);
		tvProName=(TextView)findViewById(R.id.tv_see_one_project_proname);
		tvProState=(TextView)findViewById(R.id.tv_see_one_project_prostate);
		tvProLocation=(TextView)findViewById(R.id.tv_see_one_project_location);
		tvProLink=(TextView)findViewById(R.id.tv_see_one_project_link);
		tvProDescription=(TextView)findViewById(R.id.tv_see_one_project_description);
	
		Project pro=getIntent().getParcelableExtra("see_pro_project");
		System.out.println(pro.toString());
		String proName=pro.getName();

		String proDescription=pro.getDescription();
//		
//		System.out.println(proContName);
//		System.out.println(proName);
//		System.out.println(proState);
//		System.out.println(proLink);
//	
//		System.out.println(pro);

		tvProDescription.setText(proDescription);
		
		
	}
	
	public void doClick(View v){
		switch (v.getId()) {
		case R.id.bt_back:
			finish();
			break;

		}
	}
}
