package chuangbang.activity;



import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import chuangbang.fragment.WhatNewFragmentOne;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

public class WelcomeActivity extends Activity{
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		Bmob.initialize(this, "f68a522a7ce3b89c222044431b372dae");
		// ʾWhat's New
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean isFirstRun = sp.getBoolean("isFirstRun", true);
		final Class<? extends Activity> cls;
		if (isFirstRun) {
			cls = WhatNewActivity.class;
		} else {
			BmobUser user=BmobUser.getCurrentUser(this);
			if(user!=null){
				cls=MainActivity.class;
			}else{

				cls = LoginActivity.class;
			}
		}

		// 
		long delayMillis =4000;

		// 
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this, cls);
				startActivity(intent);
				finish();
			}
		}, delayMillis);
	}
}
