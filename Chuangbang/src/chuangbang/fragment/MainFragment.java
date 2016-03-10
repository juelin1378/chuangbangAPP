package chuangbang.fragment;


import chuangbang.activity.R;
import chuangbang.app.ChuangApp;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;



public class MainFragment extends Fragment implements OnPageChangeListener, OnCheckedChangeListener{
	private RadioGroup rgMeun;
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter;
	private Context context;
	private boolean isTouzi;
	private RadioButton rbServer;
	private ChuangApp app;
	//	private int count;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_main, null);
		context=getActivity();
		viewPager=(ViewPager)view.findViewById(R.id.viewpager);
		adapter=new InnerPagerAdapter(getActivity().getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		rgMeun=(RadioGroup)view.findViewById(R.id.rg_group);
		rbServer=(RadioButton)view.findViewById(R.id.rb_server);
		app=ChuangApp.getApp();
		//



		rgMeun.setOnCheckedChangeListener(this);
		viewPager.setOnPageChangeListener(this);
		isTouzi=true;
		//		count=4;
		//		if(!isTouzi){
		//			rbServer.setVisibility(View.GONE);
		//			count=3;
		//		}
		return view;
	}




	/**
	 * 
	 * @author Administrator
	 *
	 */
	private class InnerPagerAdapter extends FragmentPagerAdapter{

		public InnerPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment=null;
			switch (arg0) {
			case 0:
				fragment=new BusinessCircleFragment();
				break;

			case 1:
				fragment=new FoundFragment();
				break;
			case 2:
//				if(isTouzi){
//					fragment=new ServerFragment();	
//				}else{
//					fragment=new MineFragment();
//				}
				fragment=new ServerFragment();
				break;

			case 3:

				fragment=new MineFragment();

				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {

			Log.i("Count", "架设viewpager页面"+app.getMCount());
			//return app.getMCount();
			return 4;



		}

	}


	@Override
	public void onPageScrollStateChanged(int arg0) {


	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {


	}


	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			rgMeun.check(R.id.rb_business_circle);
			break;

		case 1:
			rgMeun.check(R.id.rb_found);
			break;
		case 2:
			if(isTouzi){
				rgMeun.check(R.id.rb_server);

			}else{
				rgMeun.check(R.id.rb_mine);
			}
			break;

		case 3:	
			rgMeun.check(R.id.rb_mine);	
			break;
		}

	}




	/**
	 *�����ť����ҳ��
	 */
	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		switch (id) {
		case R.id.rb_business_circle:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb_found:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_server:

			viewPager.setCurrentItem(2);


			break;
		case R.id.rb_mine:
//			if(isTouzi){
//				viewPager.setCurrentItem(3);	
//
//			}else{
//				viewPager.setCurrentItem(2);
//			}
			viewPager.setCurrentItem(3);
			break;
		}

	}
}
