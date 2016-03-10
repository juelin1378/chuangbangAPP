package chuangbang.fragment.found;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import chuangbang.activity.R;
import chuangbang.adapter.InvestorAdapter;
import chuangbang.entity.InvestorInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InvestorFragment extends Fragment{
	private ListView lvInvestor;
	private List<InvestorInfo> investors;
	private InvestorAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_investor, null);
		lvInvestor=(ListView)view.findViewById(R.id.lv_investor);
		investors=new ArrayList<InvestorInfo>();
		adapter = new InvestorAdapter(getActivity(),investors);
		lvInvestor.setAdapter(adapter);
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		queryInvestor();
	}
	
	/**显示投资人表
	 * 
	 */
	private void queryInvestor(){
		BmobQuery<InvestorInfo> query=new BmobQuery<InvestorInfo>();
		query.include("owner");
		query.setLimit(50);
		query.findObjects(getActivity(), new FindListener<InvestorInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("investor", "获取数据失败："+arg0+":"+arg1);
			}

			@Override
			public void onSuccess(List<InvestorInfo> results) {
				Log.i("investor", "获取数据成功："+results.toString());
				investors.clear();
				investors.addAll(results);
				adapter.notifyDataSetChanged();
			}
		});
		
	}
}
