package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import chuangbang.activity.R;
import chuangbang.app.ChuangApp;
import chuangbang.entity.Meeting;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMeetingAdapter extends BaseAdapter {

	private Context context;
	private List<Meeting> data;
	private ChuangApp app;

	public MyMeetingAdapter(Context context, List<Meeting> data) {
		super();
		if (data == null)
			this.data = new ArrayList<Meeting>();
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_empty) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.avater) // 设置图片Urn为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
				.bitmapConfig(Bitmap.Config.RGB_565).build(); // 构建完成
		app = ChuangApp.getApp();// 获取app对象

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_mine_meeting, null);
			holder.ivLogo = (ImageView) convertView
					.findViewById(R.id.iv_item_meeting_logo);
			holder.tvProName = (TextView) convertView
					.findViewById(R.id.tv_item_meeting_project_name);
			holder.tvMeetState = (TextView) convertView
					.findViewById(R.id.tv_item_meeting_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Meeting meet = data.get(position);
		if (meet != null) {
			if (meet.getProject().getName() != null)
				holder.tvProName.setText(meet.getProject().getName());
			if (meet.getState() != null)
				holder.tvMeetState.setText("约谈状态      " + meet.getState());
			if (meet.getProject().getLogo() != null)
				app.getImageLoader().displayImage(
						meet.getProject().getLogo().getFileUrl(context),
						holder.ivLogo, options);
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView ivLogo;
		TextView tvProName, tvMeetState;
	}
}
