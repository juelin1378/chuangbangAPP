package chuangbang.fragment.server;

import chuangbang.activity.ApplyForDevelop;
import chuangbang.activity.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TechDevelopment extends Fragment implements OnClickListener{
	private Button btApply;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_tech_development, null);
		btApply=(Button)view.findViewById(R.id.bt_tech_develope);
		btApply.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getActivity(),ApplyForDevelop.class);
		startActivity(intent);
	}
}
