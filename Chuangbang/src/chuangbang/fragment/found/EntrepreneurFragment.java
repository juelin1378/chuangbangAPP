package chuangbang.fragment.found;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import chuangbang.activity.PartnerActivity;
import chuangbang.activity.R;
import chuangbang.activity.SeeOneProject;
import chuangbang.adapter.SeeProAdapter;
import chuangbang.entity.PartnerDemand;
import chuangbang.entity.Project;
import chuangbang.entity.User;
import chuangbang.util.SharedPreferencesUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
/**
 * 合伙人
 * @author Administrator
 *
 */
public class EntrepreneurFragment extends Fragment implements OnClickListener, OnItemClickListener{
	private ListView lvSeeProject;
	private String url;
	private HttpUtils http;
	private List<PartnerDemand> data;
	private BaseAdapter adapter;
	private Handler handler;
	private int page = 1;
	private View loadmoreItem; // listView下方加载更多的按钮
	private Button loadmoreButton;
	protected Gson gson;
	protected SharedPreferencesUtils spfu;
	private int skip = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * 通过view绑定控件
		 */
		View view = inflater.inflate(R.layout.fragment_entrepreneur, null);
		lvSeeProject = (ListView) view.findViewById(R.id.lv_see_project);
		//url = "http://cloud.bmob.cn/56a234cda6607ec7/projectReadList";
		// 增加一个view视图，用来加载底部的加载按钮
		loadmoreItem = inflater.inflate(R.layout.button_loadmore, null);
		loadmoreButton = (Button) loadmoreItem.findViewById(R.id.bt_loadmore);
		// 将底部按钮添加进lstView
		lvSeeProject.addFooterView(loadmoreItem);
		//初始化数据
		data = new ArrayList<PartnerDemand>();
		adapter = new SeeProAdapter(data, getActivity());
		lvSeeProject.setAdapter(adapter);
		//添加监听器
		loadmoreButton.setOnClickListener(this);
		lvSeeProject.setOnItemClickListener(this);


		initView();

		return view;
	}

	/**
	 * 初始化原有的数据
	 */
	private void initView(){
		gson = new Gson();
		spfu = new SharedPreferencesUtils(getActivity(), "partner");
		List<String> userJsons = spfu.query("partner");
		if(userJsons.size()==0){
			loadUserData(skip);
			Log.i("DATA","开启网络");
		}else{
			skip = userJsons.size()+1;
			data.clear();
			Log.i("DATA","调用缓存");
			//调用缓存
			List<PartnerDemand> newList=new ArrayList<PartnerDemand>();
			for (String string : userJsons) {
				//Log.i("DATA", "数据："+string);
				PartnerDemand partner = gson.fromJson(string, PartnerDemand.class);
				newList.add(partner);

			}
			/*
			 * 对List进行排序
			 */
			Collections.sort(newList, new Comparator<PartnerDemand>() {

				@Override
				public int compare(PartnerDemand lhs, PartnerDemand rhs) {
					// TODO Auto-generated method stub
					return rhs.getCreatedAt().compareTo(lhs.getCreatedAt());
				}
			});
			data.addAll(newList);
			adapter.notifyDataSetChanged();


		}
	}
	/**
	 * 加载数据
	 * @param newSkip 
	 */
	private void loadUserData(int newSkip) {
		BmobQuery<PartnerDemand> query = new BmobQuery<PartnerDemand>();
		//查询playerName叫“比目”的数据
		//query.addWhereEqualTo("memberType", 2);
		//返回50条数据，如果不加上这条语句，默认返回10条数据
		query.setLimit(10);
		query.setSkip(newSkip);
		query.order("createdAt");
		query.findObjects(getActivity(), new FindListener<PartnerDemand>() {
			@Override
			public void onSuccess(List<PartnerDemand> partner) {
				Log.i("DATA",partner.toString());
				for (PartnerDemand user : partner) {


					String userJson = gson.toJson(user);
					spfu.save("partner", userJson);

				}
				data.addAll(partner);
				adapter.notifyDataSetChanged();
				skip+=10;
				loadmoreButton.setText("加载更多");
			}

			@Override
			public void onError(int code, String msg) {
				Log.i("DATA", "查询数据失败："+code+"|"+msg);
			}

		});
	}





	public void onClick(View arg0) {
		loadmoreButton.setText("正在加载");
		loadUserData(skip);
	}
	
		/**
		 * listView的短点击
		 */
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			PartnerDemand intentPar=new PartnerDemand();
		
			intentPar = data.get(arg2);
			
			Intent intent = new Intent(getActivity(),PartnerActivity.class);
			
			intent.putExtra("partner",intentPar );
			
			startActivity(intent);
	
		}

}
