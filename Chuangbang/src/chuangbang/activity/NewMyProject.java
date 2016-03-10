package chuangbang.activity;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import chuangbang.util.Final;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Advanceable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class NewMyProject extends Activity implements OnClickListener,Final, OnItemSelectedListener{
	private TableRow trProLogo;
	private ImageView logoImage;

	private Handler handler;
	private EditText etProName,etProState,etProDomain,etProDescription,etPainPointer,etSolution,etCompetiors,etAdvantage,
	etBusinessModel,etFinancingAmount,etTransferShare,etTermNumCount;
	private String result,proName,proState,proDomain,proDescString,painPointer,solution,competiors,adavantage,busenessModel;
	private Integer financingAmount,financingStater,termNumCount,transferShare;

	private BroadcastReceiver receiver;
	private Bitmap image;
	private Button btBack,btSave;
	private File logoPath;//logo本地地址
	private String logoName;//logo文件名
	private String logoUrl;//logo的在网络上的url
	private User user;
	private BmobFile bmobFile;
	private List<String> list=new ArrayList<String>();
	private Spinner sp;
	private ArrayAdapter<String> adapter;




	private void setView(){
		etProName=(EditText)findViewById(R.id.et_new_pro_name);
		etProState=(EditText)findViewById(R.id.et_new_pro_statu);
		etProDomain=(EditText)findViewById(R.id.et_new_pro_domain);
		etProDescription=(EditText)findViewById(R.id.et_new_pro_description);
		etTermNumCount=(EditText)findViewById(R.id.et_new_pro_term_count);
		etPainPointer=(EditText)findViewById(R.id.et_new_pro_pain_point);
		etSolution=(EditText)findViewById(R.id.et_new_pro_solution);
		etCompetiors=(EditText)findViewById(R.id.et_new_pro_competitors);
		etAdvantage=(EditText)findViewById(R.id.et_new_pro_advantage);
		etBusinessModel=(EditText)findViewById(R.id.et_new_pro_business_model);
		etFinancingAmount=(EditText)findViewById(R.id.et_new_pro_financing_amount);
		etTransferShare=(EditText)findViewById(R.id.et_new_pro_transferShare);
		
		
		
		
		
		trProLogo=(TableRow)findViewById(R.id.tr_new_pro_logo);
		btBack=(Button)findViewById(R.id.bt_new_pro_back);
		btSave=(Button)findViewById(R.id.bt_new_pro_save);
		logoImage=(ImageView)findViewById(R.id.iv_new_pro_logo);

		sp=(Spinner)findViewById(R.id.sp_my_new_pro_financing_state);
		list.add("种子轮");
		list.add("天使轮");
		list.add("pre-A轮");
		list.add("A轮");
		list.add("B轮");





	}
	private void setOnClick(){

		trProLogo.setOnClickListener(this);
		btSave.setOnClickListener(this);
		btBack.setOnClickListener(this);
		sp.setOnItemSelectedListener(this);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_project);
		setView();
		setOnClick();

		user=BmobUser.getCurrentUser(this,User.class);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		/*
		 * 注册广播接受者
		 */
		receiver=new InnerReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(INTENT_ACTION_LOGO);

		registerReceiver(receiver, filter);


	}

	/**
	 * 按钮监听
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.tr_new_pro_logo:
			Intent intent=new Intent(NewMyProject.this,ImageFromNewPro.class);

			startActivity(intent);
			break;


			/*
			 * 发布项目按钮
			 */
		case R.id.bt_new_pro_save:
			if(image==null){
				updatePro();
			}else{
				uploadLogo();
				String path=Environment.getExternalStorageDirectory().getPath()+"/chuangbang/"+logoName;
				Toast.makeText(NewMyProject.this, path, Toast.LENGTH_LONG).show();

			}
			break;
		case R.id.bt_new_pro_back:
			finish();
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
			Log.i("Pro","新建项目受到的广播"+action);
			if(INTENT_ACTION_LOGO.equals(action)){
				image=intent.getParcelableExtra(INTENT_EXTRA_LOGO);
				Log.i("IMAGES",image.toString());
				logoImage.setImageBitmap(image);
				saveBitmap(image);

			}

		}
	}

	private void saveBitmap(Bitmap bitmap){
		logoName=System.currentTimeMillis()+".jpg";
		logoPath=new File(Environment.getExternalStorageDirectory(),"chuangbang/"+logoName);
		FileOutputStream out=null;
		try {
			File destDir = new File(Environment.getExternalStorageDirectory(),"chuangbang");
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			out=new FileOutputStream(logoPath);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

			out.close();
			out.flush();
		}catch (IOException e) {				
			e.printStackTrace();
		}

	}

	/**
	 * 上传logo到bmob服务器
	 */
	private void uploadLogo(){
		//String path=logoPath.getAbsolutePath();
		String path=Environment.getExternalStorageDirectory().getPath()+"/chuangbang/"+logoName;
		Log.i("Pro", "logo的本地路径"+path);
		//Log.i("Avater", "头像在本地的地址"+path);
		BTPFileResponse response = BmobProFile.getInstance(NewMyProject.this).upload(path, new UploadListener() {

			@Override
			public void onError(int arg0, String arg1) {


			}

			@Override
			public void onSuccess(String arg0, String arg1, BmobFile arg2) {
				Toast.makeText(NewMyProject.this, "上传图片成功", Toast.LENGTH_LONG).show();
				bmobFile=arg2;

				updatePro();
			}

			@Override
			public void onProgress(int arg0) {


			}
		});


	}
	/**
	 * 发布项目到BMOB
	 */
	private void updatePro(){
		//
		CharSequence html1=null;
		Project pro=new Project();
		proName=etProName.getText().toString();
		proState=etProState.getText().toString();
		proDescString=etProDescription.getText().toString();
		proDomain=etProDomain.getText().toString();
		
		
		
		
		
		if(proName.length()==0){
			html1 = Html.fromHtml("<font color='black'>名字不能为空</font>");
			etProName.setError(html1);
			Toast.makeText(NewMyProject.this, "名字不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		if(proState.length()==0){
			html1 = Html.fromHtml("<font color='black'>项目状态不能为空</font>");
			etProState.setError(html1);
			Toast.makeText(NewMyProject.this, "项目状态不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(proDescString.length()==0){
			html1 = Html.fromHtml("<font color='black'>项目简介不能为空</font>");
			etProDescription.setError(html1);
			Toast.makeText(NewMyProject.this, "项目简介不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		if(proDomain.length()==0){
			html1 = Html.fromHtml("<font color='black'>经营范围不能为空</font>");
			etProDomain.setError(html1);
			Toast.makeText(NewMyProject.this, "经营范围不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		if(etTermNumCount.getText().toString().length()==0){
			html1 = Html.fromHtml("<font color='black'>团队人数不能为空</font>");
			etTermNumCount.setError(html1);
			Toast.makeText(NewMyProject.this, "团队人数不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		termNumCount=Integer.parseInt(etTermNumCount.getText().toString());
		painPointer=etPainPointer.getText().toString();
		solution=etSolution.getText().toString();
		competiors=etCompetiors.getText().toString();
		adavantage=etAdvantage.getText().toString();
		busenessModel=etBusinessModel.getText().toString();
		
		pro.setName(proName);//项目名
		pro.setDescription(proDescString);
		pro.setDomain(proDomain);
		pro.setState(proState);
		pro.setProTermNumCount(termNumCount);
		pro.setPainPointer(painPointer);
		pro.setSolution(solution);
		pro.setCompetitors(competiors);
		pro.setAdvantage(adavantage);
		pro.setFinancingState(financingStater);
		pro.setFinancingAmount(financingAmount);
		pro.setTransferShare(transferShare);
		pro.setBusinessModel(busenessModel);
		if(etFinancingAmount.getText().toString().length()!=0)
			financingAmount=Integer.parseInt(etFinancingAmount.getText().toString());
		if(etTransferShare.getText().toString().length()!=0)
			transferShare=Integer.parseInt(etTransferShare.getText().toString());
			
		
		pro.setCommentCount(0);//评论数至0
		pro.setFavoriteUserCount(0);//收藏数至0
		
		
		if(bmobFile!=null){

			Log.i("Pro", "bmobFile判空"+bmobFile.toString());
			pro.setLogo(bmobFile);
		}
		pro.setOwner(user);

		pro.save(this, new SaveListener() {

			@Override
			public void onSuccess() {

				Intent intent=new Intent(INTENT_ACTION_UPDATE_MY_PROJECT);
				sendBroadcast(intent);
				if(image!=null){

					logoPath.delete();
				}
				Toast.makeText(NewMyProject.this, "项目发布成功",Toast.LENGTH_LONG).show();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("Pro",arg1);

			}
		});
	}









	private void dialogShow(String title,final int point){

		LayoutInflater inflater=LayoutInflater.from(this);
		LinearLayout ll=(LinearLayout)inflater.inflate(R.layout.view_dialog, null);
		TextView tv=(TextView)ll.findViewById(R.id.tv_dialog_title);
		tv.setText(title);
		final Dialog dialog=new AlertDialog.Builder(NewMyProject.this).create();
		dialog.show();
		dialog.getWindow().setContentView(ll);


		final EditText et=(EditText)ll.findViewById(R.id.et_edit);

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
	protected void onDestroy() {

		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if(position==0){
			financingStater=1;
		}else if(position==1){
			financingStater=2;
		}else if(position==2){
			financingStater=3;
		}else if(position==3){
			financingStater=11;
		}else if(position==4){
			financingStater=12;
		}
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}



}
