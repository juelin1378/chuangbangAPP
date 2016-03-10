package chuangbang.fragment.server;

import chuangbang.activity.ApplyForDevelop;
import chuangbang.activity.ApplyIncubator;
import chuangbang.activity.IntellectualProperty;
import chuangbang.activity.LegalAdvice;
import chuangbang.activity.MakeAccount;
import chuangbang.activity.R;
import chuangbang.activity.RegistCompany;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class IncubatorFragment extends Fragment implements OnClickListener{
	private Button btApplyIncubator,btRegistCompany,btMakeAccount,btIntellectual,btLegal,btServiceDevelop;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_incubator,null);
		/*
		 * 绑定控件
		 */
		btApplyIncubator=(Button)view.findViewById(R.id.btn_incubation_incubator);
		btRegistCompany=(Button)view.findViewById(R.id.btn_registered_company);
		btMakeAccount=(Button)view.findViewById(R.id.btn_make_account);
		btIntellectual=(Button)view.findViewById(R.id.btn_intellectual_property);//知识产权
		btLegal=(Button)view.findViewById(R.id.btn_legal_advice);//法律咨询
		btServiceDevelop=(Button)view.findViewById(R.id.btn_service_develop);
		
		/*
		 * 添加监听
		 */
		btApplyIncubator.setOnClickListener(this);
		btRegistCompany.setOnClickListener(this);
		btMakeAccount.setOnClickListener(this);
		btIntellectual.setOnClickListener(this);
		btLegal.setOnClickListener(this);
		//btServiceDevelop.setOnClickListener(this);
		return view;
		
	}
	
	/**
	 * 
	 */
	
	@Override
	public void onClick(View arg0) {
		Intent intent=null;
		switch (arg0.getId()) {
		//进驻孵化器
		case R.id.btn_incubation_incubator:
			intent=new Intent(getActivity(),ApplyIncubator.class);
			startActivity(intent);
			break;
			//注册公司
		case R.id.btn_registered_company:
			intent=new Intent(getActivity(),RegistCompany.class);
			startActivity(intent);
			break;
			//会计做账
		case R.id.btn_make_account:
			intent=new Intent(getActivity(),MakeAccount.class);
			startActivity(intent);
			break;
			//知识产权
		case R.id.btn_intellectual_property:
			intent=new Intent(getActivity(),IntellectualProperty.class);
			startActivity(intent);
			break;
			//法律咨询
		case R.id.btn_legal_advice:
			intent=new Intent(getActivity(),LegalAdvice.class);
			startActivity(intent);
			break;
		case R.id.btn_service_develop:
			intent=new Intent(getActivity(),ApplyForDevelop.class);
			startActivity(intent);
			break;
		}
		
	}

}
