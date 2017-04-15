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
package com.lurencun.android.topicbank.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-6
 */

public class TopicEntity implements Serializable {
	private static final long serialVersionUID = -1003208444546289165L;
	public TopicEntity(){ /*Required empty bean constructor*/ }
	public enum TopicType{
		SINGLE_CHOICE,
		MULTIPLE_CHOICE,
		JUDGE 
	};

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TopicType getType() {
		return type;
	}

	public void setType(TopicType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getIsdo() {
		return isdo;
	}

	public void setIsdo(int isdo) {
		this.isdo = isdo;
	}

	public List<AnswerEntity> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerEntity> answers) {
		this.answers = answers;
	}

	public boolean isture() {
		return isture;
	}

	public void setIsture(boolean isture) {
		this.isture = isture;
	}

	public int index;
	public int id;
	public TopicType type;
	public String title;
	public String image;
	public String tip;
	public int isdo;
	public List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
	public boolean isture;
}
