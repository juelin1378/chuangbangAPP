package chuangbang.fragment;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import chuangbang.activity.MenuDialog;
import chuangbang.activity.MineResume;
import chuangbang.activity.MyFavorite;
import chuangbang.activity.MyMeetingActivity;
import chuangbang.activity.MyProject;
import chuangbang.activity.MyStatus;
import chuangbang.activity.R;
import chuangbang.activity.SettingMine;
import chuangbang.entity.User;
import chuangbang.util.Final;
import chuangbang.view.RoundRectImageView;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MineFragment extends Fragment implements OnClickListener,Final{


	private View llMyProject,llSetMine,llMyFavorite,llMineResume,llMyChat,llSetting;
	private RoundRectImageView roundImage;
	private String avatarPath;
	private byte[] mContent;
	private Bitmap image;
	private BroadcastReceiver receiver;
	private File avaterFile;
	private TextView tvMyName;
	private BmobFile bmobFile;
	private String avaterName;
	private User user;
	Handler handler;
	private Bitmap sysBit;
	//private Button bt;





	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view=inflater.inflate(R.layout.fragment_mine, null);
		/*
		 * 绑定控件
		 */
		roundImage=(RoundRectImageView)view.findViewById(R.id.iv_mine_avater);
		llMyProject=(View)view.findViewById(R.id.ll_mine_project);
		llSetMine=(View)view.findViewById(R.id.ll_set_mine);
		llMyFavorite=(View)view.findViewById(R.id.ll_mine_favorite);
		llMineResume=(View)view.findViewById(R.id.ll_mine_resume);
		llMyChat=(View)view.findViewById(R.id.ll_my_chat);
		tvMyName=(TextView)view.findViewById(R.id.tv_mine_name);
		llSetting=(View)view.findViewById(R.id.ll_setting);
		//bt=(Button)view.findViewById(R.id.bt_out_login);
		/*
		 * 添加监听
		 */
		llMyProject.setOnClickListener(this);
		llSetMine.setOnClickListener(this);
		llMyFavorite.setOnClickListener(this);
		llMineResume.setOnClickListener(this);
		llMyChat.setOnClickListener(this);
		llSetting.setOnClickListener(this);
		//bt.setOnClickListener(this);
		roundImage.setOnClickListener(this);
		handler=new InnerHandler();


		roundImage.setCircleImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.avater));
		/*
		 * 注册广播接受者
		 */
		receiver=new InnerReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(INTENT_ACTION_CARD);
		
		getActivity().registerReceiver(receiver, filter);
		user=BmobUser.getCurrentUser(getActivity(),User.class);
		
		
		
		avaterName=user.getUsername()+".jpg";
		if(user!=null){
			String myName=(String) BmobUser.getObjectByKey(getActivity(), "nickName");
			//Log.i("favo", myName);
			tvMyName.setText(myName);
		}
		
		Log.i("NAME", avaterName);
		avaterIsExists();
		return view;
	}



	@Override
	public void onClick(View view) {
		Intent intent=null;
		switch (view.getId()) {
		case R.id.ll_set_mine:
			intent=new Intent(getActivity(),SettingMine.class);
			startActivity(intent);
			break;
		case R.id.ll_mine_project:
			intent=new Intent(getActivity(),MyProject.class);
			startActivity(intent);
			break;
		case R.id.ll_mine_favorite:
			intent=new Intent(getActivity(),MyFavorite.class);
			startActivity(intent);

			break;
//		case R.id.ll_mine_resume:
//			intent=new Intent(getActivity(),MineResume.class);
//			startActivity(intent);
//			break;

			//点击头像
		case R.id.iv_mine_avater:
			intent=new Intent(getActivity(),MenuDialog.class);
			startActivity(intent);
			break;
		case R.id.ll_my_chat:
			intent=new Intent(getActivity(),MyMeetingActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_setting:
			BmobUser.logOut(getActivity());
			getActivity().finish();
			break;

		}
	}


	/*
	 * 广播接受
	 */
	private class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action=intent.getAction();
			Log.i("Pro", "我的头像受到的广播"+action);
			if(INTENT_ACTION_CARD.equals(action)){
				image=intent.getParcelableExtra(INTENT_EXTRA_CARD);
				
				saveBitmap(image);
				roundImage.setCircleImageBitmap(image);
				uploadAvater();//上传头像到bmob
			}
		}
	}
	
	private void saveSystemBitmap(){
		File systemFile=new File(Environment.getExternalStorageDirectory(),"chuangbang/"+"system.jpg");
		FileOutputStream out=null;
		try {
			File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			sysBit=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_launcher);
			out=new FileOutputStream(systemFile);
			sysBit.compress(Bitmap.CompressFormat.PNG, 100, out);
			//handler.obtainMessage(210,"image").sendToTarget();
			out.close();
			out.flush();
		}catch (IOException e) {				
			e.printStackTrace();
		}
	}

	/*
	 * 将剪切后的头像保存拿到本地
	 */
	private void saveBitmap(Bitmap bitmap){
		avaterFile=new File(Environment.getExternalStorageDirectory(),"chuangbang/"+avaterName);
		FileOutputStream out=null;
		try {
			File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			out=new FileOutputStream(avaterFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			handler.obtainMessage(210,"image").sendToTarget();
			out.close();
			out.flush();
		}catch (IOException e) {				
			e.printStackTrace();
		}

	}


	/**
	 * 上传头像到bmob服务器
	 */
	private void uploadAvater(){
		String path=avaterFile.getAbsolutePath();
		Log.i("Avater", "头像在本地的地址"+path);
		BTPFileResponse response = BmobProFile.getInstance(getActivity()).upload(path, new UploadListener() {
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(String arg0, String arg1, BmobFile arg2) {
				bmobFile=arg2;
				updateAvater();
			}
			
			@Override
			public void onProgress(int arg0) {
				
				
			}
		});
		
	}

	/**
	 * 将头像在服务器上的uri地址存入User表
	 */
	private void updateAvater(){
		User newUser=new User();
		newUser.setAvatar(bmobFile);
		BmobUser user=BmobUser.getCurrentUser(getActivity());
		newUser.update(getActivity(), user.getObjectId(),new UpdateListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(getActivity(), "头像上传成功", Toast.LENGTH_LONG).show();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getActivity(), "头像上传User表失败", Toast.LENGTH_LONG).show();
				avaterFile.delete();
			}
		});
	}


	/*
	 * 判断本地是否有头像文件
	 * 1，如果存在本地，则加载本地
	 * 2，不存在本地文件，加载网络User表的数据
	 * 		如果不为空，下载头像到本地，再加载头像
	 * 		如果为空，则加载默认的头像
	 */
	private void avaterIsExists()  
	{  
		try  
		{  
			File f=new File(Environment.getExternalStorageDirectory(),"chuangbang/"+avaterName);  
			if(f.exists())  
			{  
				Log.i("NAME","文件存在");
				roundImage.setCircleImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
			} 
			//加载网络User表
			else{
				Log.i("NAME","文件不存在");
				queryUserAvater();
				
			}

		}  
		catch (Exception e)  
		{  

		}  


	}  
	
	
	private void queryUserAvater(){


		BmobQuery<User> query=new BmobQuery<User>();
		query.addWhereEqualTo("objectId", user.getObjectId());
		query.findObjects(getActivity(),new FindListener<User>() {

			@Override
			public void onSuccess(List<User> arg0) {
				Log.i("NAME","getFileUrl"+arg0.get(0).getAvatar().getFileUrl(getActivity()));
				Log.i("NAME","getUrl"+arg0.get(0).getAvatar().getUrl());
				final String url=arg0.get(0).getAvatar().getUrl();
				if(url!=null){
					Log.i("NAME", "开启线程");
					new Thread(){
						public void run() {

							downloadPic(url);
							
						};
					}.start();

				}else{
					roundImage.setCircleImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
				}

			}

			@Override
			public void onError(int arg0, String arg1) {
				//Log.i("NAME",arg1);

			}
		});

	}

	/**
	 * 下载图片到本地
	 */
	private void downloadPic(String avaterPath){
		URL url=null;
		HttpURLConnection conn=null;
		InputStream in=null;
		//FileOutputStream out=null;
		Bitmap bit=null;

		try {
			Log.i("NAME","开始下载");
			url=new URL(avaterPath);

			conn=(HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			in=conn.getInputStream();
			bit=BitmapFactory.decodeStream(in);
			saveBitmap(bit);
			in.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class InnerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 210:
				//在线程中下载完图片后，在此加载头像
				roundImage.setCircleImageBitmap(BitmapFactory.decodeFile(avaterFile.getAbsolutePath()));
				break;

			}
			
		}
	}





}
