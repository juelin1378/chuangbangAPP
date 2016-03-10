package chuangbang.activity;

import chuangbang.app.ChuangApp;
import chuangbang.entity.PartnerDemand;
import chuangbang.entity.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PartnerActivity extends Activity{
	private PartnerDemand partner;
	private ImageView ivAvater;
	private TextView tvUsername,tvCompany,tvContactPhone,tvTitle,tvText;
	private ChuangApp app;


	private void setView(){
		tvUsername=(TextView)findViewById(R.id.tv_contact_name);
		ivAvater=(ImageView)findViewById(R.id.iv_see_project_avater);
		tvCompany=(TextView)findViewById(R.id.tv_contact_company);
		tvContactPhone=(TextView)findViewById(R.id.tv_contact_phone);
		tvTitle=(TextView)findViewById(R.id.tv_partner_title);
		tvText=(TextView)findViewById(R.id.tv_text);

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partenr_demand);
		setView();
		Intent intent=getIntent();
		partner=(PartnerDemand) intent.getSerializableExtra("partner");

		User user=partner.getAuthor();
		tvUsername.setText(user.getNickName());
		tvCompany.setText(user.getWorkingCompany());
		tvContactPhone.setText("联系方式："+partner.getContactPhone());
		tvTitle.setText(partner.getTitle());
		tvText.setText(partner.getText());
		app=(ChuangApp) getApplication();
		if(user.getAvatar()!=null)
			app.getImageLoader().displayImage(user.getAvatar().getUrl(),ivAvater);


	}

}
