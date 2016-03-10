package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;

import chuangbang.activity.R;

import chuangbang.app.ChuangApp;
import chuangbang.entity.Project;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProjectAdapter extends BaseAdapter{

	private List<Project> data;
	private LayoutInflater inflater;
	private ChuangApp app;
	private DisplayImageOptions options;
	private ImageLoader io;
	private Context con;


	public MyProjectAdapter(List<Project> data,Context context){
		if (data == null) {
			this.data = new ArrayList<Project>();
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

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_empty) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.avater) // 设置图片Urn为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build(); // 构建完成
		app=ChuangApp.getApp();//获取app对象
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_businees_status,null);
			holder.tvName=(TextView)convertView.findViewById(R.id.tv_my_pro_name);
			holder.tvDomain=(TextView)convertView.findViewById(R.id.tv_my_pro_domain);
			holder.tvDescription=(TextView)convertView.findViewById(R.id.tv_my_pro_description);
			holder.tvDate=(TextView)convertView.findViewById(R.id.tv_my_pro_date);
			holder.tvComment=(TextView)convertView.findViewById(R.id.tv_my_pro_comments);
			holder.roundImage=(ImageView)convertView.findViewById(R.id.iv_my_pro_avater);
			holder.tvFavorite=(TextView)convertView.findViewById(R.id.tv_my_pro_favorite);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}

		holder.tvName.setText(data.get(position).getName());
		holder.tvDate.setText(data.get(position).getCreatedAt());
		if(data.get(position).getDomain()!=null)
			holder.tvDomain.setText(data.get(position).getDomain());
		holder.tvDescription.setText(data.get(position).getDescription());
		if(data.get(position).getCommentCount()!=null)



			holder.tvComment.setText(data.get(position).getCommentCount()+"人评论");
		if(data.get(position).getFavoriteUserCount()!=null)
			holder.tvFavorite.setText(data.get(position).getFavoriteUserCount()+"人收藏");
		if(data.get(position).getLogo()!=null)
			app.getImageLoader().displayImage(data.get(position).getLogo().getFileUrl(con), holder.roundImage, options);

		return convertView;
	}
	private class ViewHolder{

		TextView tvName,tvDomain,tvDescription,tvDate,tvComment,tvFavorite;
		ImageView roundImage;
	}

}
