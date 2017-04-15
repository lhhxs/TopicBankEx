
package com.lurencun.android.topicbank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.widget.CommonAdapter;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.HistoryEntity;
import com.lurencun.android.topicbank.res.HistoryAdapter;
import com.lurencun.android.topicbank.res.HistoryLoader;

import java.util.List;

public class HistoryActivity extends BackUIActivity {

	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		final ListView list = (ListView)findViewById(R.id.list_container);
		CommonAdapter<HistoryEntity> adapter = new HistoryAdapter(this);
		final List<HistoryEntity> data = new HistoryLoader(this).load();
		if(data == null || data.size() <= 0){
			Toast.makeText(getApplicationContext(), "没有考試！",
					Toast.LENGTH_SHORT).show();}
		else {
			adapter.updateDataCache(data);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
										long arg3) {

					Intent intent = new Intent(HistoryActivity.this, ShowFenActivity.class);
					intent.putExtra("data", data.get(arg2).getTopic());
					intent.putExtra("zongfen",data.get(arg2).getCorrect());
					intent.putExtra("defen",data.get(arg2).getScore());
					intent.putExtra("errNum",data.get(arg2).getMistake());

					intent.putExtra("zongshu",data.get(arg2).getZongshu());
					intent.putExtra("time",data.get(arg2).getShijian());
					intent.putExtra("TAG","HistoryActivity");
					startActivity(intent);
					//				ActivitySwitcher.switchTo(CategoryActivity.this, ExtraTopicActivity.class);
				}
			});
		}
	}

	@Override
	protected int getContentViewLayoutId() {
		return R.layout.history;
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
