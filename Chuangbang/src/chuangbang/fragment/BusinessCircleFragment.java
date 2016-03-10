package chuangbang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import chuangbang.activity.ProjectDetailsActivity;
import chuangbang.activity.R;
import chuangbang.adapter.BusineeStatusAdapter;
import chuangbang.entity.Project;
import chuangbang.util.SharedPreferencesUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BusinessCircleFragment extends Fragment implements OnClickListener,OnItemClickListener{
	private ListView lvBusiness;
	private BaseAdapter adapter; 
	private List<Project> data;
	private View loadmoreItem;
	private Button loadmoreButton;
	private int skip;
	private SharedPreferencesUtils spuProject;
	private Gson gson;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_business_circle, null);
	
		lvBusiness=(ListView)view.findViewById(R.id.lv_business);
		loadmoreItem=inflater.inflate(R.layout.button_loadmore, null);
		loadmoreButton=(Button)loadmoreItem.findViewById(R.id.bt_loadmore);
		lvBusiness.addFooterView(loadmoreItem);
		
		
		data=new ArrayList<Project>();
		adapter=new BusineeStatusAdapter(data, getActivity());
		lvBusiness.setAdapter(adapter);
		
		init();
		
		loadmoreButton.setOnClickListener(this);
		lvBusiness.setOnItemClickListener(this);
		
		return view;
	}
	
	
	/**
	 * 初始化数据
	 */
	private void init(){
		gson = new Gson();
		spuProject = new SharedPreferencesUtils(getActivity(), "users");
		List<String> projects = spuProject.query("project");
		if(projects.size()==0){
			doBmobQuery(skip);
		}else{
			skip = projects.size()+1;
			data.clear();
			Project pro;
			for (String str : projects) {
				pro = gson.fromJson(str, Project.class);
				data.add(pro);
			}
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 加载数据
	 */
	private void doBmobQuery(int newSkip){
		
		BmobQuery<Project> query=new BmobQuery<Project>();
		query.setLimit(20);
		query.setSkip(newSkip);
		query.order("createdAt");
		query.findObjects(getActivity(), new FindListener<Project>() {

			@Override
			public void onSuccess(List<Project> projects) {
				Log.i("project", "项目的个数："+projects.get(1).getName());
				Log.i("project", "项目的个数："+projects.get(1).getCommentCount());
				if(projects.get(1).getCommentCount()==null)
					Log.i("project","dd");
				Log.i("project", "项目的个数："+projects.get(1).getFavoriteUserCount());
				
				//将数据保存到本地
				String proJson;
				for (Project project : projects) {
					proJson = gson.toJson(project);
					spuProject.save("project", proJson);
				}
				data.addAll(projects);
				adapter.notifyDataSetChanged();
				loadmoreButton.setText("加载更多");
				skip+=2;
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("project", "项目数据加载失败->"+arg0+":"+arg1);
				
			}

		});

	}


	@Override
	public void onClick(View arg0) {
		loadmoreButton.setText("点击加载");
		doBmobQuery(skip);
		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Log.i("project", "跳转下一个界面");
		Intent intent = new Intent(getActivity(), ProjectDetailsActivity.class);
		intent.putExtra("project", data.get(position));
		getActivity().startActivity(intent);
	}

	

}
