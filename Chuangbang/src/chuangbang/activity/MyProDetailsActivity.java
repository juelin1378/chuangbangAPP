package chuangbang.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.UpdateListener;
import chuangbang.app.ChuangApp;
import chuangbang.entity.Project;
import chuangbang.util.Final;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

public class MyProDetailsActivity extends Activity implements OnClickListener,Final, TextWatcher, OnItemSelectedListener{
	private ImageView ivLogo;
	private EditText etProName,etProDescription,etProPainPoint,etProSulotion,etProCompetitors,etProAdvantage,etProBusinessModel,etProMoney,etProShare;
	private String proName,proDescription,proPainPoint,proSulotion,proCompetitors,proAdavantage,proBusinessModel;
	private Integer financingAmount,transferShare,finacingState;
	private Spinner sp;
	private Project project;
	private List<String> list=new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private Button btDeleted,btUpdate,btBack;
	private ChuangApp app;
	//我的项目object
	private String myProObject;
	private boolean isCheck;



	private void setView(){
		etProName=(EditText)findViewById(R.id.et_my_pro_detail_title);
		etProDescription=(EditText)findViewById(R.id.et_my_pro_detail_description);
		etProPainPoint=(EditText)findViewById(R.id.et_my_pro_detail_pain_pointer);
		etProSulotion=(EditText)findViewById(R.id.et_my_pro_details_solution);
		etProCompetitors=(EditText)findViewById(R.id.et_my_pro_details_competitors);
		etProAdvantage=(EditText)findViewById(R.id.et_my_pro_details_advantage);
		etProBusinessModel=(EditText)findViewById(R.id.et_my_pro_details_businessModel);
		etProMoney=(EditText)findViewById(R.id.et_my_pro_details_money);
		etProShare=(EditText)findViewById(R.id.et_my_pro_shares);
		sp=(Spinner)findViewById(R.id.sp_my_pro_details_financing_state);
		btDeleted=(Button)findViewById(R.id.bnt_activity_my_project_deleted);
		btUpdate=(Button)findViewById(R.id.bnt_activity_my_project_update);
		btBack=(Button)findViewById(R.id.bnt_activity_my_project_back);
		ivLogo=(ImageView)findViewById(R.id.iv_activity_project_logo);

		list.add("种子轮");
		list.add("天使轮");
		list.add("pre-A轮");
		list.add("A轮");
		list.add("B轮");



	}
	private void setOnClick(){
		btDeleted.setOnClickListener(this);
		btUpdate.setOnClickListener(this);
		btBack.setOnClickListener(this);
		etProName.addTextChangedListener(this);
		etProDescription.addTextChangedListener(this);
		etProPainPoint.addTextChangedListener(this);
		etProSulotion.addTextChangedListener(this);
		etProCompetitors.addTextChangedListener(this);
		etProAdvantage.addTextChangedListener(this);
		etProBusinessModel.addTextChangedListener(this);
		etProMoney.addTextChangedListener(this);
		etProShare.addTextChangedListener(this);
		sp.setOnItemSelectedListener(this);


	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pro_details);

		setView();
		setOnClick();

		Intent intent=getIntent();
		project=(Project) intent.getSerializableExtra("project");
		myProObject=project.getObjectId();
		Log.i("detail", project.toString());
		app=(ChuangApp) getApplication();
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		//把收到的project显示在UI上
		if(project.getLogo()!=null)
			app.getImageLoader().displayImage(project.getLogo().getUrl(), ivLogo);
		setPro();

		//首先把更新按钮设为不可用
		btUpdate.setEnabled(false);





	}
	/**
	 * 把传过来的项目详细信息显出出来
	 */
	private void setPro(){

		etProName.setText(project.getName());
		etProDescription.setText(project.getDescription());
		etProPainPoint.setText(project.getPainPointer());
		etProSulotion.setText(project.getSolution());
		etProCompetitors.setText(project.getCompetitors());
		etProAdvantage.setText(project.getAdvantage());
		etProBusinessModel.setText(project.getBusinessModel());
		if(project.getFinancingState()!=null)
			sp.setSelection(project.getFinancingState(), true);
		if(project.getFinancingAmount()!=null)
			etProMoney.setText(project.getFinancingAmount().toString());

		if(project.getTransferShare()!=null)
			etProShare.setText(project.getTransferShare().toString());







	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		//删除按钮
		case R.id.bnt_activity_my_project_deleted:
			new AlertDialog.Builder(MyProDetailsActivity.this).setTitle("警告！").setMessage("你确定删除吗？").setIcon(android.R.drawable.ic_dialog_info).
			setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//执行删除操作
					deleted();

				}
			}).setNegativeButton("取消",null).show();

			break;

			//更新项目按钮
		case R.id.bnt_activity_my_project_update:
			update();
			break;
			//返回按钮
		case R.id.bnt_activity_my_project_back:
			if(isCheck){
				new AlertDialog.Builder(MyProDetailsActivity.this).setTitle("警告！").setMessage("您要放弃已经做的修改吗？").setIcon(android.R.drawable.ic_dialog_info).
				setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();

					}
				}).setNegativeButton("取消",null).show();
			}
			break;

		}

	}
	/**
	 * 删除我的项目
	 */
	private void deleted(){
		Project pro=new Project();
		pro.setObjectId(myProObject);
		pro.delete(this,new DeleteListener() {

			@Override
			public void onSuccess() {
				Intent intent=new Intent(INTENT_ACTION_UPDATE_MY_PROJECT);
				sendBroadcast(intent);
				Toast.makeText(MyProDetailsActivity.this, "删除成功", Toast.LENGTH_LONG).show();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(MyProDetailsActivity.this, arg1, Toast.LENGTH_LONG).show();

			}
		});
	}
	/**
	 * 更新操作
	 */
	private void update(){
		Project pro=new Project();
		CharSequence html1=null;
		proName=etProName.getText().toString();
		proDescription=etProDescription.getText().toString();
		proPainPoint=etProPainPoint.getText().toString();
		proSulotion=etProSulotion.getText().toString();
		proCompetitors=etProCompetitors.getText().toString();
		proAdavantage=etProAdvantage.getText().toString();
		proBusinessModel=etProBusinessModel.getText().toString();



		if(proName.length()==0){
			html1 = Html.fromHtml("<font color='black'>名字不能为空</font>");
			etProName.setError(html1);
			return ;
		}
		if(proDescription.length()==0){
			html1 = Html.fromHtml("<font color='black'>项目描述不能为空</font>");
			etProDescription.setError(html1);
			return ;
		}

		//先给Integer型判空后才能进行转换赋值
		if(etProMoney.getText().toString().length()!=0){
			financingAmount=Integer.parseInt(etProMoney.getText().toString());
			pro.setFinancingAmount(financingAmount);
		}
		if(etProShare.getText().toString().length()!=0){
			transferShare=Integer.parseInt(etProShare.getText().toString());
			pro.setTransferShare(transferShare);
		}
		pro.setObjectId(myProObject);
		pro.setName(proName);
		pro.setDescription(proDescription);
		pro.setPainPointer(proPainPoint);
		pro.setSolution(proSulotion);
		pro.setCompetitors(proCompetitors);
		pro.setAdvantage(proAdavantage);
		pro.setBusinessModel(proBusinessModel);

		pro.update(this,new UpdateListener() {

			@Override
			public void onSuccess() {
				Intent intent=new Intent(INTENT_ACTION_UPDATE_MY_PROJECT);
				sendBroadcast(intent);
				Toast.makeText(MyProDetailsActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("detail",arg1);

			}
		});

	}

	/**
	 * 监听输入框是否有值变化
	 * @param arg0
	 */
	@Override
	public void afterTextChanged(Editable arg0) {
		btUpdate.setEnabled(true);
		//设为true，退出时询问是否更新
		isCheck=true;

	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


	}

	/**
	 * 下拉框的监听
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		if(position==0)
			finacingState=1;
		if(position==1)
			finacingState=2;
		if(position==2)
			finacingState=3;
		if(position==3)
			finacingState=11;
		if(position==4)
			finacingState=12;

	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {


	}






}
