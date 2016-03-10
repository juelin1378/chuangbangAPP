package chuangbang.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.testpic.ImageBucket;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;


import chuangbang.adapter.MyPartnerAdapter;
import chuangbang.entity.PartnerDemand;
import chuangbang.entity.User;
import chuangbang.util.Final;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MyPartmerActivity extends Activity implements OnClickListener,Final{
	private BaseAdapter adapter;
	private List<PartnerDemand> data;
	private ListView lv;
	private User currentUser;
	private Button loadmoreButton;
	private ImageButton ivBack;
	private View loadmoreItem;
	private LayoutInflater inflater;
	private BroadcastReceiver receiver;


	private void setView(){
		lv=(ListView)findViewById(R.id.lv_my_partner);
		inflater=LayoutInflater.from(this);

		loadmoreItem=inflater.inflate(R.layout.button_loadmore, null);
		loadmoreButton=(Button)loadmoreItem.findViewById(R.id.bt_loadmore);
		ivBack=(ImageButton)findViewById(R.id.ib_my_partner_back);
		// 将底部按钮添加进lstView
		lv.addFooterView(loadmoreItem);
		loadmoreButton.setText("发布新需求");
	}
	private void setOnClick(){
		loadmoreButton.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_partmer);
		setView();
		setOnClick();
		
		
		receiver=new InnerReceiver();
		
		//注册广播
		IntentFilter filter=new IntentFilter();
		filter.addAction(INTENT_ACTION_UPDATE_MY_PARTNER);
		registerReceiver(receiver, filter);

		data=new ArrayList<PartnerDemand>();
		adapter=new MyPartnerAdapter(data, this);
		lv.setAdapter(adapter);
		currentUser=BmobUser.getCurrentUser(this,User.class);
		query();
	}


	/**
	 * 查询我发布的合伙人需求
	 */
	private void query(){
		BmobQuery<PartnerDemand> query=new BmobQuery<PartnerDemand>();
		query.addWhereEqualTo("author",currentUser);
		query.findObjects(this, new FindListener<PartnerDemand>() {

			@Override
			public void onSuccess(List<PartnerDemand> arg0) {
				data.clear();
				data.addAll(arg0);
				adapter.notifyDataSetChanged();

			}

			@Override
			public void onError(int arg0, String arg1) {


			}
		});

	}
	
	class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			query();
			
		}
		
	}




	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_loadmore:
			Intent intent=new Intent(MyPartmerActivity.this,NewMyPartner.class);
			startActivity(intent);
			
			break;

		case R.id.ib_my_partner_back:
			finish();
			break;
		}

	}
}
