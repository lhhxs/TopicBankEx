
package com.lurencun.android.topicbank.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.widget.CommonAdapter;
import com.lurencun.android.support.widget.GalleryFlipper;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.res.TopicAdapter;
import com.lurencun.android.topicbank.res.TopicLoader;

import org.json.JSONException;

import java.util.List;


public class TopicActivity extends BackUIActivity {

	private GalleryFlipper mGallery;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGallery.onFlipperTouchEvent(event);
	}
	
	@Override
	protected void overridePendingTransition(Activity activity){
		activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		mGallery = (GalleryFlipper)findViewById(R.id.topic_container);
		mGallery.setAnimationDuration(300);
		CommonAdapter<TopicEntity> adapter = new TopicAdapter(this);
		List<TopicEntity> data;
		try {
			data = new TopicLoader(this).load();
		
		Log.e("TAG", "data is load success");
		adapter.updateDataCache(data);
		mGallery.setAdapter(adapter);
		mGallery.isGalleryCircular(false);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	@Override
	protected int getContentViewLayoutId() {
		return R.layout.topic;
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
