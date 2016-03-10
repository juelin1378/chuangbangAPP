package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import chuangbang.activity.R;
import chuangbang.app.ChuangApp;
import chuangbang.entity.Comment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 评论列表的适配器
 * 
 * @author Administrator
 * 
 */
public class ProjectCommentAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> data;
	private DisplayImageOptions options;
	private ChuangApp app;

	public ProjectCommentAdapter(Context context, List<Comment> data) {
		if (data == null){
			this.data = new ArrayList<Comment>();
		}else{
			this.data = data;
		}
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_empty) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.avater) // 设置图片Urn为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		.considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
		.displayer(new RoundedBitmapDisplayer(360)) // 设置成圆角图片
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build(); // 构建完成
		app=ChuangApp.getApp();//获取app对象
		ViewHolder holder;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_project_comment, null);
			holder = new ViewHolder();
			holder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_item_coment_user_logo);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_item_coment_username);
			holder.tvUserDomain = (TextView) convertView.findViewById(R.id.tv_item_coment_domain);
			holder.tvComment = (TextView) convertView.findViewById(R.id.tv_item_comment_content);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Comment comment = data.get(position);
		holder.tvUsername.setText(comment.getAuthor().getUsername());
		holder.tvUserDomain.setText(comment.getAuthor().getDescription());
		holder.tvComment.setText(comment.getText());
		if(data.get(position).getAuthor().getAvatar()!=null)
			app.getImageLoader().displayImage(data.get(position).getAuthor().getAvatar().getFileUrl(context), holder.ivLogo, options);
		return convertView;
	}

	class ViewHolder{
		private ImageView ivLogo;
		private TextView tvUsername,tvUserDomain,tvComment;
	}
}
