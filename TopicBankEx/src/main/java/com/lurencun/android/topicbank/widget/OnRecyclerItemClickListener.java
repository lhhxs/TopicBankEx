package com.lurencun.android.topicbank.widget;

import android.view.View;

/**
 * 自定义RecyclerView 中item view点击回调方法
 */
public interface OnRecyclerItemClickListener{
	/**
	 * item view 回调方法
	 * @param view  被点击的view
	 * @param position 点击索引
	 */
	void onItemClick(View view, int position);
}
