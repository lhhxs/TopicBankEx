/**
 * Copyright (C) 2012  TopicBankEx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lurencun.android.topicbank.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.v2.widget.ViewPager;
import com.lurencun.android.support.v2.widget.ViewPagerAdapter;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.res.ExtraTopicAdapter;
import com.lurencun.android.topicbank.res.TopicLoader;

import org.json.JSONException;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-6
 */
public class ExtraTopicActivity extends BackUIActivity {

	@Override
	protected void overridePendingTransition(Activity activity){
		activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		Bundle myBundle = this.getIntent().getExtras();
        int tiku  = myBundle.getInt("tiku");
        String biao = myBundle.getString("biao");
        Log.e("TAG>>", "表名： "+biao);
		ViewPager container = (ViewPager) findViewById(R.id.topic_container);
		List<TopicEntity> data;
		try {
			data = new TopicLoader(this).load(biao,tiku);
			
			Log.e("TAG", "data extratopicactivity ");
			if(data == null || data.size() <= 0){Toast.makeText(getApplicationContext(), "没有内容！",
				     Toast.LENGTH_SHORT).show();}
//			Log.e("TAG--->", data.get(0).title);
			ViewPagerAdapter<TopicEntity> adapter = new ExtraTopicAdapter(this);
			adapter.setData(data);
			container.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected int getContentViewLayoutId() {
		return R.layout.topicex;
	}

	@Override
	protected int getBackButtonResId() {
		return R.id.back_button;
	}

	@Override
	protected boolean isConfirmBack() {
		return true;
	}
	
	@Override
	protected String getTipMessage(){
		return AppSetting.TOPIC_BACK_TIP;
	}
	
	
	
}
