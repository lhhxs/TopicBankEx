package com.lurencun.android.topicbank.res;

import android.content.Context;

import com.lurencun.android.toolkit.HasContext;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.entity.HistoryEntity;

import java.util.List;


public class HistoryLoader extends HasContext {

	private Context context;

	public HistoryLoader(Context context) {
		super(context);
		this.context = context;

	}
	
	public List<HistoryEntity> load(){
		ProblemService p = ProblemService.getInstance(context);

		return  p.getHistoryDatalist();
	}

}
