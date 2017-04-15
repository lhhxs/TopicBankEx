/**
 * 
 */
package com.lurencun.android.topicbank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.widget.CommonAdapter;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.CategoryEntity;
import com.lurencun.android.topicbank.res.ExamAdapter;
import com.lurencun.android.topicbank.res.ExamLoader;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-2-24
 */
public class ExamActivity extends BackUIActivity {

	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		Intent intent = getIntent();
		String title = intent.getStringExtra(AppSetting.MENU_TITLE);
		int value = intent.getIntExtra(AppSetting.MENU_KEY, 0);
		
		TextView titleView = (TextView) findViewById(R.id.app_title);
		titleView.setText(title);
		
		ListView list = (ListView)findViewById(R.id.list_container);
		CommonAdapter<CategoryEntity> adapter = new ExamAdapter(this);
		final List<CategoryEntity> data = new ExamLoader(this).load(value);
		if(data == null || data.size() <= 0){Toast.makeText(getApplicationContext(), "没有试卷！",
			     Toast.LENGTH_SHORT).show();}
		else{
			adapter.updateDataCache(data);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent myIntent = new Intent();
	                Bundle bundle = new Bundle();
	                bundle.putInt("tiku", data.get(arg2).tiku);
	                bundle.putString("biao","tiku");
					bundle.putInt("id",data.get(arg2).id);
	                bundle.putInt("danxuanshu", data.get(arg2).danxuanshu);
	                bundle.putInt("danxuanfen", data.get(arg2).danxuanfen);
	                bundle.putInt("duoxuanshu", data.get(arg2).duoxuanshu);
	                bundle.putInt("duoxuanfen", data.get(arg2).duoxuanfen);
	                bundle.putInt("panduanshu", data.get(arg2).panduanshu);
	                bundle.putInt("panduanfen", data.get(arg2).panduanfen);
	                bundle.putInt("daojishi", data.get(arg2).daojishi);
	                Log.e("TAG>>>>", "tiku,biao " +bundle.getInt("tiku")+bundle.getString("biao"));
	                myIntent.putExtras(bundle);
	                myIntent.setClass(ExamActivity.this,ExtraExamActivity.class);
	                startActivity(myIntent);
	//				ActivitySwitcher.switchTo(CategoryActivity.this, ExtraTopicActivity.class);
				}
			});
		}
	}

	@Override
	protected int getContentViewLayoutId() {
		return R.layout.category;
	}

	@Override
	protected int getBackButtonResId() {
		return R.id.back_button;
	}

	@Override
	protected boolean isConfirmBack() {
		return false;
	}

}
