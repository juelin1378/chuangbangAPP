package chuangbang.activity;

import java.io.File;

import chuangbang.app.ChuangApp;
import chuangbang.util.Exit;
import chuangbang.util.SharedPreferencesUtils;
import cn.bmob.v3.Bmob;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/*
 * 12:15
 */
public class MainActivity extends FragmentActivity {
	private ChuangApp app;
	private Exit exit=new Exit();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
		if (!destDir.exists()) {
			destDir.mkdirs();
		}



	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {    
			pressAgainExit();    

		}    
		return super.onKeyDown(keyCode, event);
	}


	private void pressAgainExit() {    
		if (exit.isExit()) {    
			finish();    
		} else {    
			//Toast.makeText(getApplicationContext(), "再按一次退出程序",    Toast.LENGTH_SHORT).show();    
			exit.doExitInOneSecond();    
		}    
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SharedPreferencesUtils sh=new SharedPreferencesUtils(this,"users");
		sh.clear();
	}







}
