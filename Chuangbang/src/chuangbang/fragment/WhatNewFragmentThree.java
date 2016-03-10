package chuangbang.fragment;


import cn.bmob.v3.BmobUser;
import chuangbang.activity.LoginActivity;
import chuangbang.activity.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class WhatNewFragmentThree extends Fragment implements OnClickListener{
	private Button bt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_what_new03,null);
		
		bt=(Button)view.findViewById(R.id.bt_start);
		bt.setOnClickListener(this);
		return view;
	}

	/**
	 * fragmente
	 */
	
	@Override
	public void onClick(View v) {
	//	 ���ò�����ʾWhat's New
		SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isFirstRun", false);
		editor.commit();
		
		Intent intent = new Intent(getActivity(), LoginActivity.class);
//		BmobUser user=BmobUser.getCurrentUser(getActivity());
//		if(user!=null){
//			
//		}
		// ����MainActivity
		startActivity(intent);
		
		// �رյ�ǰActivity
		getActivity().finish();
		
	}
	
	
}
