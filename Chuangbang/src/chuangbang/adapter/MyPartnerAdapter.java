package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;

import chuangbang.activity.R;

import chuangbang.entity.PartnerDemand;
import chuangbang.entity.Project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPartnerAdapter extends BaseAdapter{
	private List<PartnerDemand> data;
	private LayoutInflater inflater;
	private Context con;
	
	
	
	
	public MyPartnerAdapter(List<PartnerDemand> data,Context context){
		if (data == null) {
			this.data = new ArrayList<PartnerDemand>();
		} else {
			this.data = data;
		}
		inflater = LayoutInflater.from(context);
		con=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_my_partner,null);
			holder.tvName=(TextView) convertView.findViewById(R.id.tv_my_partner_name);
			holder.tvDate=(TextView) convertView.findViewById(R.id.tv_my_partner_date);
			holder.tvText=(TextView) convertView.findViewById(R.id.tv_my_partner_text);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.tvName.setText(data.get(position).getTitle());
		holder.tvDate.setText(data.get(position).getCreatedAt());
		holder.tvText.setText(data.get(position).getText());
		return convertView;
		
	
	}
	private class ViewHolder{
		TextView tvName,tvDate,tvText;
	}

}
