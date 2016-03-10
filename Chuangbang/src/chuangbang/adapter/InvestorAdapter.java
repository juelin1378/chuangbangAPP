package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import chuangbang.activity.InvestorDetailsActivity;
import chuangbang.activity.R;
import chuangbang.app.ChuangApp;
import chuangbang.entity.Comment;
import chuangbang.entity.InvestorInfo;
import chuangbang.entity.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 评论列表的适配器
 * 
 * @author Administrator
 * 
 */
public class InvestorAdapter extends BaseAdapter {

	private List<InvestorInfo> data;
	private ChuangApp app;
	private DisplayImageOptions options;
	private Context context;
	private ImageLoader io;

	public InvestorAdapter(Context context, List<InvestorInfo> data) {
		if (data == null) {
			this.data = new ArrayList<InvestorInfo>();
		} else {
			this.data = data;
		}
		this.context = context;
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
				.showImageForEmptyUri(R.drawable.avater) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.bitmapConfig(Bitmap.Config.RGB_565).build(); // 构建完成
		app = ChuangApp.getApp();// 获取app对象
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_investor, null);
			holder.tvInvestorName = (TextView) convertView
					.findViewById(R.id.tv_item_investor_name);
			holder.tvVerifiedReason = (TextView) convertView
					.findViewById(R.id.tv_item_verified_reason);
			holder.tvInvestmentDomain = (TextView) convertView
					.findViewById(R.id.tv_item_investment_domain);
			holder.tvInvestmentStage = (TextView) convertView
					.findViewById(R.id.tv_item_investment_stage);
			holder.ivAvater = (ImageView) convertView
					.findViewById(R.id.iv_item_investor_avater);
			holder.btnSendMeeting = (Button) convertView
					.findViewById(R.id.btn_item_investor_send);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final InvestorInfo investor = data.get(position);

		holder.tvInvestorName.setText(investor.getOwner().getNickName());
		holder.tvVerifiedReason.setText(investor.getVerifiedReason());// 签名
		holder.tvInvestmentDomain.setText(investor.getInvestmentDomain());// 标签
		holder.tvInvestmentStage.setText(investor.getInvestmentStage());// 阶段
		if (data.get(position).getOwner().getAvatar() != null)
			app.getImageLoader().displayImage(
					data.get(position).getOwner().getAvatar()
							.getFileUrl(context), holder.ivAvater, options);
		holder.btnSendMeeting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context,
						InvestorDetailsActivity.class);
				intent.putExtra("investor", investor);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		ImageView ivAvater;
		TextView tvInvestorName, tvInvestmentStage, tvInvestmentDomain,
				tvVerifiedReason;
		Button btnSendMeeting;
	}
}
