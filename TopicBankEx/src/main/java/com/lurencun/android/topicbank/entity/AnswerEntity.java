
package com.lurencun.android.topicbank.entity;

import java.io.Serializable;



public class AnswerEntity implements Serializable{
	private static final long serialVersionUID = 4161991966494138482L;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean current) {
		isCurrent = current;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public String content;
	public boolean isCurrent;
	public boolean isChecked;
}
