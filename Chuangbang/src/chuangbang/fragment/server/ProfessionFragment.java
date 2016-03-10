package chuangbang.fragment.server;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import chuangbang.activity.R;
import chuangbang.entity.Project;
import chuangbang.entity.ServiceDevelop;
import chuangbang.entity.User;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

public class ProfessionFragment extends Fragment implements OnClickListener{
	private Button btnApplicatServer;
	private List<Project> pros;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_profession, null);
		btnApplicatServer = (Button) view.findViewById(R.id.btn_applicat_server);
		btnApplicatServer.setOnClickListener(this);
		query();
		return view;
	}
	@Override
	public void onClick(View arg0) {
		Log.i("pf", "当前的项目数："+pros.size());
		String[] items = null;
		if(pros.size()==3){
			items = new String[]{pros.get(0).getName(),pros.get(1).getName(),pros.get(2).getName()};
		}else if(pros.size()==2){
			items = new String[]{pros.get(0).getName(),pros.get(1).getName()};
		}else if(pros.size()==1){
			items = new String[]{pros.get(0).getName()};
		}else if(pros.size()==0){
			Toast.makeText(getActivity(), "你没有项目可投，请先去创建项目吧", Toast.LENGTH_LONG).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("选择你投资的项目");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				User user = BmobUser.getCurrentUser(getActivity(), User.class);
				Log.i("abc", "当前用户："+user.getObjectId());
				ServiceDevelop sdevelop = new ServiceDevelop();
				sdevelop.setApplicant(user);
				sdevelop.setProject(pros.get(which));
				sdevelop.setState(1);
				sdevelop.save(getActivity(), new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "已发起申请", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "发起申请失败，请检查网络", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		builder.create().show();
	}
	private void query() {
		pros = new ArrayList<Project>();
		User user = BmobUser.getCurrentUser(getActivity(), User.class);
		BmobQuery<Project> query = new BmobQuery<Project>();
		query.addWhereEqualTo("owner", user);
		query.findObjects(getActivity(), new FindListener<Project>() {
			
			@Override
			public void onSuccess(List<Project> projects) {
				// TODO Auto-generated method stub
				//Log.i("pf", "当前的项目数："+projects.size());
				pros.clear();
				pros.addAll(projects);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
