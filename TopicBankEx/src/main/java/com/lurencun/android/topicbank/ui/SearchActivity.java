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

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.widget.CommonAdapter;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.res.SearchAdapter;
import com.lurencun.android.topicbank.res.SearchLoader;

import org.json.JSONException;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-8
 */
public class SearchActivity extends BackUIActivity {

	private List<TopicEntity> data = null;
	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			ListView list = (ListView) findViewById(R.id.list_container);
			CommonAdapter<TopicEntity> adapter = new SearchAdapter(this);

			try {
				data = new SearchLoader(this)
                        .search(query);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			adapter.updateDataCache(data);
			list.setAdapter(adapter);
		}
	}

	@Override
	protected int getContentViewLayoutId() {
		return R.layout.search;
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
