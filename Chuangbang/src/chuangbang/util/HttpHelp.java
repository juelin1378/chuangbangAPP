package chuangbang.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import chuangbang.entity.Project;
import chuangbang.entity.SignUp;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class HttpHelp {
	private HttpUtils http;
	private List<Project> pros;
	
	public void httpSignUp(String url,RequestParams params,final SignUp signUp){
		
		
		http=new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				signUp.setError(arg1);			
			}

			@Override
			public void onSuccess(ResponseInfo<String> info) {
				String result=info.result;
				JSONObject json;
				try {
					json = new JSONObject(result);
					signUp.setObjectId(json.getString("objectId"));
					signUp.setToken(json.getString("sessionToken"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
	
		
	}
	
	public List<Project> httpReadProject(String url,RequestParams params){
		http=new HttpUtils();
		pros=new ArrayList<Project>();
		Log.i("Tag", "发送post开始读取");
		
		http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
						Log.i("Tag", "网络读取项目失败"+arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> info) {
				Log.i("Tag", "读取项目成功");
				String result=info.result;
				Log.i("Tag","result="+result);
				JSONObject json=null;
				Project pro=null;
				try {
					Log.i("Tag", "开始解析");
					json = new JSONObject(result);
					JSONArray results=json.getJSONArray("results");
					
					for(int i=0;i<results.length();i++){
						Log.i("Tag","您吗的的");
						pro=new Project();
						JSONObject value=results.getJSONObject(i);
						pro.setName(value.getString("proName"));
						pro.setDescription(value.getString("proDecription"));
			
						pros.add(pro);
						Log.i("Tag", ".....");
						
						
					}
					Log.i("Tag","pros"+pros.toString());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		return pros;
	}
}
