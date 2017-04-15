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
import android.util.Log;

import com.lurencun.android.toolkit.HasContext;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.entity.TopicEntity;

import org.json.JSONException;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-6
 */
public class TopicLoader extends HasContext {

	private Context context;
	/**
	 * @param context
	 */
	public TopicLoader(Context context) {
		super(context);
		this.context = context;
		
	}

	
	public List<TopicEntity> load(String menu,int tiku,int danxuanshu,int duoxuanshu,int panduanshu) throws JSONException{
		ProblemService p = ProblemService.getInstance(context);
	    Log.e("TAG>>>", "load(5a)");
		List<TopicEntity> danxuanList = null;
		if (danxuanshu!=0) {
			danxuanList = p.getRandomData(tiku, 1, danxuanshu);
		}
		List<TopicEntity> duoxuanList;
		if (duoxuanshu!=0) {
			duoxuanList = p.getRandomData(tiku, 2, duoxuanshu);
			danxuanList.addAll(duoxuanList);
		}

		List<TopicEntity> panduanList;
		if(panduanshu!=0) {
			panduanList = p.getRandomData(tiku, 3, panduanshu);
			danxuanList.addAll(panduanList);
		}
		if (danxuanList.size()>0){
			Log.e("TAG--->","loader "+danxuanList.toString());
			return danxuanList;
		}
		return null ;

	}
	
	
	public List<TopicEntity> load(String menu,int tiku,int tixing) throws JSONException{
//		int size = 100;
//		List<TopicEntity> list = new ArrayList<TopicEntity>();
//		for(int i=0;i<size;i++){
//			TopicEntity topic = new TopicEntity();
//			topic.index = i;
//			
//			topic.title = "题目内容题目内容题目内容题目内容题目内容题目内容";
//			topic.tip = "青年毛泽东对新文化运动主将胡适很尊重仰慕，与他有不少交往，也受到他的一些进步思想的影响。1918年8月19日，毛泽东应读师范时的老师、时任北大教授的杨昌济之召来到北京，被推荐到北大图书馆做助理员的工作。";
//			int answerCount = 4;
//			if(i<3){
//				topic.image = "demo_0.jpg" ;
//				topic.type = TopicEntity.TopicType.SINGLE_CHOICE;
//			}else if(i>=3 && i<6){
//				topic.image = "demo_1.jpg" ;
//				topic.type = TopicEntity.TopicType.MULTIPLE_CHOICE;
//			}else{
//				answerCount = 2;
//				topic.image = "demo_2.jpg" ;
//				topic.type = TopicEntity.TopicType.JUDGE;
//			}
//			
//			for(int j=0;j<answerCount;j++){
//				AnswerEntity answer = new AnswerEntity();
//				answer.content = "答案选项内容答案选项内容答案选项内容答案选项内容";
//				topic.answers.add(answer);
//			}
//			
//			list.add(topic);
//		}
//		return list;
		ProblemService p = ProblemService.getInstance(context);
		List<TopicEntity> List;
		Log.e("TAG>>>", "load(3a)");
		List = p.getGroupData(menu,tiku,tixing);
		//Log.e("TAG--->","loader "+List.toString());
//		list.removeAll(list);
//		list.addAll(list);
		return List;
	}
	public List<TopicEntity> load(String table,int tiku) throws JSONException{
		List<TopicEntity> danxuanList;
		danxuanList = this.load(table,tiku,1);
		List<TopicEntity> duoxuanList;
		duoxuanList =  this.load(table,tiku,2);
		danxuanList.addAll(duoxuanList);
		List<TopicEntity> panduanList;
		panduanList =  this.load(table,tiku,3);
		danxuanList.addAll(panduanList);
		
		Log.e("TAG--->","loader "+danxuanList.toString());
		
		return danxuanList;
	}
	public List<TopicEntity> load() throws JSONException{
		return this.load("tiku",1);
	}

}
