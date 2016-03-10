package chuangbang.util;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MsgHandler extends Handler{
	private Activity activity;
	
	public MsgHandler(Activity activity){
		this.activity=new WeakReference<Activity>(activity).get();//使用弱引用
		
	}
	@Override
	public void handleMessage(Message msg) {
		switch (msg.arg1) {
		case 1:
			showInfo("用户已经存在");
			break;

		case 2:
			showInfo("聊天服务器登录成功");
			break;
		case 3:
			showInfo("注册失败");
			break;
		case 4:
			showInfo("网络异常，请检查网络");
			break;
		case 5:
			showInfo("注册成功");
			break;
		case 6:
			showInfo("注册失败，无权限");
			break;
		}
		
		
	}
	
	public void showInfo(String info){
		Toast.makeText(activity.getApplicationContext(), info, Toast.LENGTH_LONG).show();
	}
}
