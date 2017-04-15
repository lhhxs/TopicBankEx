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
package com.lurencun.android.topicbank.res;

import android.content.Context;

import com.lurencun.android.toolkit.HasContext;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.entity.TopicEntity;

import org.json.JSONException;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-9
 */
public class SearchLoader extends HasContext {

	private Context context;
	/**
	 * @param context
	 */
	public SearchLoader(Context context) {
		super(context);
		this.context = context;
	}
	
	public List<TopicEntity> search(String keyword) throws JSONException {
		ProblemService p = ProblemService.getInstance(context);
		List<TopicEntity> result =  p.getSearchData(keyword);
//
//		for(int i=0;i<result.size();i++){
//			ResultEntity re = new ResultEntity();
//			re.title = "搜索关键字《"+keyword+"》标题";
//			re.summary = "这是一个关于搜索关键字《"+keyword+"》结果的描述信息";
//			result.add(re);
//		}
		return result;
	}

}
