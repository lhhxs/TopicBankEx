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
package com.lurencun.android.topicbank.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.v2.widget.ViewPager;
import com.lurencun.android.support.v2.widget.ViewPagerAdapter;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.res.ExtraExamAdapter;
import com.lurencun.android.topicbank.res.ExtraExamAdapter.Callback;
import com.lurencun.android.topicbank.res.TopicLoader;
import com.lurencun.android.topicbank.widget.AnswerCard;
import com.lurencun.android.topicbank.widget.onTestListener;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-6
 */
public class ExtraExamActivity extends BackUIActivity implements Callback, onTestListener {
	private List<TopicEntity> data;
	ViewPager container;
	private TextView titleView;
	private CountDownTimer timer;
	private ImageView showAnswerCard;
	private int errNum;
	private int defen;
	private long time;
	private int weiwancheng;
	private int zongfen;
	private int danxuanfen;
	private int duoxuanfen;
	private int panduanfen;
    private int id;
    private int zongshu;
	private int daojishi;
    private String mAppPackage;
	private  AlertDialog.Builder alertDialog;
	@Override
	protected void overridePendingTransition(Activity activity){
		activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		Bundle myBundle = this.getIntent().getExtras();
        int tiku  = myBundle.getInt("tiku");
        String biao = myBundle.getString("biao");
        int danxuanshu = myBundle.getInt("danxuanshu");
        danxuanfen = myBundle.getInt("danxuanfen");
        int duoxuanshu =myBundle.getInt("duoxuanshu");
        duoxuanfen =myBundle.getInt("duoxuanfen");
        int panduanshu =myBundle.getInt("panduanshu");
        panduanfen =myBundle.getInt("panduanfen");
        daojishi = myBundle.getInt("daojishi");
        id = myBundle.getInt("id");
//        zongfen = danxuanshu*danxuanfen+duoxuanshu*duoxuanfen+panduanshu*panduanfen;
//        zongshu = danxuanshu+duoxuanshu+panduanshu;
        Log.e("倒计时：", String.valueOf(daojishi*1000));
        Log.e("TAG>>", "表名： "+biao);
        LinearLayout cardright = (LinearLayout)findViewById(R.id.option_button);
        cardright.setVisibility(View.VISIBLE);
        container = (ViewPager) findViewById(R.id.topic_container);
		titleView = (TextView) findViewById(R.id.app_title);
		showAnswerCard = (ImageView)findViewById(R.id.app_card);

		showAnswerCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AnswerCard answerCard  = AnswerCard.newInstance("你好", 1, "sad","sad",data);
				answerCard.show(getFragmentManager(), "TestDialog");
				Log.e("ExtraExamActivity","showAnswerCard.setOnClickListener:");
			}
		});
		 alertDialog = new AlertDialog.Builder(this);

//				.setOnCancelListener(new DialogInterface.OnCancelListener() {
//					@Override
//					public void onCancel(DialogInterface dialogInterface) {
//
//					}
//				}
//		 )

		timer = new CountDownTimer(daojishi*1000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				titleView.setText(AppSetting.showTimeCount(millisUntilFinished));
				time = millisUntilFinished;
			}

			@Override
			public void onFinish() {
				titleView.setText("考试结束了");
				comitAnswer("timer");

			}
		};
		timer.start();
//		List<TopicEntity> data;
		try {
			data = new TopicLoader(this).load(biao,tiku,danxuanshu,duoxuanshu,panduanshu);
			
			Log.e("TAG", "data extratopicactivity ");
			if(data == null || data.size() <= 0){Toast.makeText(getApplicationContext(), "没有内容！",
				     Toast.LENGTH_SHORT).show();}
//			Log.e("TAG--->", data.get(0).title);
			ViewPagerAdapter<TopicEntity> adapter = new ExtraExamAdapter(this,this);
			adapter.setData(data);
			container.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected int getContentViewLayoutId() {
		return R.layout.topicex;
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
	private void comitAnswer(String v)
	{
		weiwancheng = 0;
		defen = 0;
        errNum=0;
		zongshu = 0;
        String tip;
		for (TopicEntity topic:data
			 ) {
            if(topic.isdo <= 0 ) weiwancheng++;
			switch (topic.type) {
				case SINGLE_CHOICE:
					if (!topic.isture)
					{
						errNum++;
					}else {
						defen = defen + danxuanfen;
					}
					zongfen += danxuanfen;
					break;

				case MULTIPLE_CHOICE:
					if (!topic.isture)
					{
						errNum++;
					}else {
						defen += duoxuanfen;
					}
					zongfen += duoxuanfen;
					break;
				case JUDGE:
					if (!topic.isture)
					{
						errNum++;
					}else {
						defen += panduanfen;
					}
					zongfen += panduanfen;
					break;

			}

		}
		if(v.equals("card")) {
			if (weiwancheng > 0) {
				tip = "您有" + String.valueOf(weiwancheng) + "道题目没有做完,你确定要交卷吗？";
			} else {
				tip = "您确定要交卷吗？";
			}
			alertDialog.setTitle("提示信息")
					.setMessage(tip)
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					})
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							jiaojuan();

						}
					})
					.create()
					.show();
		}else if(v.equals("timer"))
		{

			alertDialog.setTitle("提示信息")
					.setMessage("考试时间结束，确定后将强制交卷！")
					.setCancelable(false)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							jiaojuan();

						}
					}).create().show();
		}
//        Snackbar.make(getWindow().getDecorView(),"你确定要提交吗",Snackbar.LENGTH_LONG)
//                .setAction("确定",new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                }).show();


	}


	/** 
     * 接口方法，响应ListView按钮点击事件 
     */ 
	@Override
    public void click(View v) {  
       
        container.setCurrentItem(container.getCurrentItem()+1);
    }

	@Override
	public void onTestListener(int uniqueIdentifier, String strName,
			String strHigh) {
		 if (uniqueIdentifier == 1) {  
	            Toast.makeText(getApplicationContext(),  
	                    "姓名:" + strName + ",身高:" + strHigh, Toast.LENGTH_LONG)  
	                    .show();   
	        }  
	}

	public void jiaojuan()
	{
		Intent intent = new Intent(ExtraExamActivity.this, ShowFenActivity.class);
		intent.putExtra("data", (Serializable) data);
		intent.putExtra("zongfen",zongfen);
		intent.putExtra("defen",defen);
		intent.putExtra("errNum",errNum);
		Log.e("ExtraExam","errNum:" +String.valueOf(errNum));
		intent.putExtra("zongshu",data.size());
		intent.putExtra("time",daojishi*1000 - time);
		intent.putExtra("TAG","ExtraExamActivity");
		Gson gson = new Gson();
		String dataJson = gson.toJson(data);

		Object[] args = new Object[]{id,String.valueOf(data.size()),defen,zongfen,errNum,dataJson,String.valueOf(daojishi*1000 - time),System.currentTimeMillis()};
		ProblemService p = ProblemService.getInstance(getApplicationContext());
		p.insertKaoshijilu(args);
		startActivity(intent);
		ExtraExamActivity.this.finish();
	}
	@Override
	public void onCardItemListener(int position) {
		container.setCurrentItem(position);
	}

    @Override
    public void onCardButtonListener() {
        comitAnswer("card");
    }


}
