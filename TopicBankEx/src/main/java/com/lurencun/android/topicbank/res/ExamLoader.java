/**
 * 
 */
package com.lurencun.android.topicbank.res;

import android.content.Context;
import android.util.Log;

import com.lurencun.android.toolkit.HasContext;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.entity.CategoryEntity;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-2-24
 */
public class ExamLoader extends HasContext {

	private Context context;
	public ExamLoader(Context context) {
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
		List<CategoryEntity> List;
		List = p.getExamData();
		Log.e("TAG--->","examlist loader "+List.toString());
		return List;
	}

}
