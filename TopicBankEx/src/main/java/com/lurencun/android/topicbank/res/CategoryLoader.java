
package com.lurencun.android.topicbank.res;

import android.content.Context;
import android.util.Log;

import com.lurencun.android.toolkit.HasContext;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.entity.CategoryEntity;

import java.util.List;


public class CategoryLoader extends HasContext {

	private Context context;
	public CategoryLoader(Context context) {
		super(context);
		this.context = context;
	}
	
	public List<CategoryEntity> load(int type){
//		int size = 30;
//		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
//		for(int i=0;i<size;i++){
//			CategoryEntity item = new CategoryEntity();
//			item.describe = "2012年计算机等级考试（二级）模拟试题，包含答案！";
//			item.title = "计算机等级考试（二级)模拟";
//			list.add(item);
//		}
		ProblemService p = ProblemService.getInstance(context);
		Log.e("TAG","CategoryLoader，context路径："+AppSetting.getSystemFilePath(context));
		List<CategoryEntity> List;
		List = p.getCategoryData();
		Log.e("TAG--->","Category loader "+List.toString());
		return List;
	}

}
