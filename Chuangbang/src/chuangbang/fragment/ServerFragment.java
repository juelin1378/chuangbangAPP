package chuangbang.fragment;

import chuangbang.activity.R;

import chuangbang.fragment.found.EntrepreneurFragment;
import chuangbang.fragment.found.InvestorFragment;
import chuangbang.fragment.server.IncubatorFragment;
import chuangbang.fragment.server.ProfessionFragment;
import chuangbang.fragment.server.TechDevelopment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ServerFragment extends Fragment implements
		OnCheckedChangeListener, OnPageChangeListener {
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter;
	private RadioGroup rgMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_server, null);
		viewPager = (ViewPager) view.findViewById(R.id.vp_server);
		rgMenu = (RadioGroup) view.findViewById(R.id.rg_group);
		adapter = new InnerPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(adapter);
		rgMenu.setOnCheckedChangeListener(this);
		viewPager.setOnPageChangeListener(this);

		return view;
	}

	private class InnerPagerAdapter extends FragmentPagerAdapter {

		public InnerPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment=null;
			switch (arg0) {
			case 0:
				fragment=new ProfessionFragment();
				break;
			case 1:
				fragment= new TechDevelopment();
				break;
			case 2:
				fragment=new IncubatorFragment();
				break;
			}
				
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch (arg1) {
		case R.id.rb_profession:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb_tech_development:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_incubator:
			viewPager.setCurrentItem(2);
			break;
		}

	}

}
