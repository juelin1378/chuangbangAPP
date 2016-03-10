package chuangbang.activity;

import cn.bmob.v3.Bmob;
import chuangbang.fragment.WhatNewFragmentOne;
import chuangbang.fragment.WhatNewFragmentThree;
import chuangbang.fragment.WhatNewFragmentTow;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class WhatNewActivity extends FragmentActivity{
	private ViewPager viewPager;
	
	private FragmentPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_what_new);
		//Bmob.initialize(this, "f68a522a7ce3b89c222044431b372dae");
		
		viewPager=(ViewPager)findViewById(R.id.vp_what_new);
		adapter=new InnerPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		
	}
	private class InnerPagerAdapter extends FragmentPagerAdapter {

		public InnerPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new WhatNewFragmentOne();
				break;
			case 1:
				fragment = new WhatNewFragmentTow();
				break;
			case 2:
				fragment = new WhatNewFragmentThree();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}
		
	}
}
