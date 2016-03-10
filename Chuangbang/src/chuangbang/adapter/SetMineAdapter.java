package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import chuangbang.activity.R;
import chuangbang.app.ChuangApp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SetMineAdapter extends BaseAdapter{
	private List<String> title;
	private List<String> contant;
	
	private LayoutInflater inflater;
	
	public SetMineAdapter(Context context,List<String> title,List<String> contant){
		if(title==null || contant==null){
			title=new ArrayList<String>();
			contant=new ArrayList<String>();
		}else{
			this.title=title;
			this.contant=contant;
		
		}
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		
		return title.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return title.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_set_mine, null);
			viewHolder.tvTitle=(TextView)convertView.findViewById(R.id.tv_set_mine_title);
			viewHolder.tvContane=(TextView)convertView.findViewById(R.id.set_mine_contant);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvTitle.setText(title.get(position));
		//viewHolder.tvContane.setText(contant.get(position));
		
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tvTitle,tvContane;
	}

}
