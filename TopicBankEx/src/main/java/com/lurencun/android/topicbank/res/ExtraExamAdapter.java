
package com.lurencun.android.topicbank.res;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lurencun.android.support.v2.widget.ViewPager;
import com.lurencun.android.support.v2.widget.ViewPagerAdapter;
import com.lurencun.android.toolkit.res.AssetsReader;
import com.lurencun.android.toolkit.util.BitmapScaleUitl;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.AnswerEntity;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.widget.ImageDialog;

import java.util.List;


public class ExtraExamAdapter extends ViewPagerAdapter<TopicEntity> {

	private Vibrator mVirator;
	private Context context;
	private ProblemService p;
	private static Toast mToast =null;
    private Callback mCallback;  

	/** 
     * 自定义接口，用于回调按钮点击事件到Activity 
     * @author LHH 
     * 2017-3-26 
     */  
    public interface Callback {  
        void click(View v);
    }  
	public ExtraExamAdapter(Context context,Callback callback) {
		super(context);
		this.context = context;
		p = ProblemService.getInstance(context);
		mCallback = callback;  
		mVirator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		
	}
	public ExtraExamAdapter(Context context) {
		super(context);
		this.context = context;
		p = new ProblemService(context);
		mVirator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	

	/* (non-Javadoc)
	 * @see com.lurencun.android.support.v2.widget.ViewPagerAdapter#createView(java.lang.Object, int)
	 */
	@Override
	public View createView(TopicEntity data, int position) {
		final ViewGroup nullParent = null;
		ScrollView  topicCell = (ScrollView) mInflater.inflate(R.layout.exam_cell, nullParent);
		TextView index = (TextView)topicCell.findViewById(R.id.exam_index);
		Log.e("TAG>>>>", "extratopic 1");
		TextView content = (TextView)topicCell.findViewById(R.id.exam_content);
		ImageView image = (ImageView)topicCell.findViewById(R.id.exam_imgs);
		LinearLayout answerLayout = (LinearLayout)topicCell.findViewById(R.id.exam_answer_layout);
		final TextView tipContent = (TextView)topicCell.findViewById(R.id.exam_tip);
		final TopicEntity topic = data;
//		final ProblemService p = new ProblemService(context);
		topic.index = position +1;
		topic.id = data.id;
		index.setText(String.format(AppSetting.TOPIC_INDEX, topic.index));
		content.setText(topic.title);
		
		createAnswerGroup(answerLayout,topic,tipContent);
		if(topic.image != null && !topic.image.isEmpty()){
			Bitmap tempImage = BitmapScaleUitl.prorateThumbnail(AssetsReader.readBitmap(mContext, topic.image), 120, 60);
			if ( tempImage.getHeight() > tempImage.getWidth()) {
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				tempImage = Bitmap.createBitmap(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), matrix, true);
			}
			image.setImageBitmap(tempImage);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ImageDialog dialog = new ImageDialog(mContext);
					Bitmap tempImage = AssetsReader.readBitmap(mContext, topic.image);
					if(tempImage != null) {
						if (tempImage.getWidth() > tempImage.getHeight()) {
							Matrix matrix = new Matrix();
							matrix.postRotate(90);
							tempImage = Bitmap.createBitmap(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), matrix, true);
						}
					}
					dialog.setImage(tempImage);
					dialog.show();
				}
			});
		}
		
		final Button addFavourite = (Button)topicCell.findViewById(R.id.exam_answer_add_fav);
//		ProblemService p = new ProblemService(context);
		Log.e("ExtraTopic","即将检查是否存在于收藏夹");
		if(p.isExistTable(topic.id, 2)){addFavourite.setText("删除收藏");}
		Log.e("ExtraTopic","检查成功");
		addFavourite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				ProblemService p = new ProblemService(context);
				if (addFavourite.getText().equals("删除收藏"))
				{
					p.delTable(topic.id, 2);
					addFavourite.setText("添加收藏");
					showToast(mContext, "题目已从收藏夹中删除！", Toast.LENGTH_SHORT);
									
				}else {
					p.addFavorite(topic.id);
					addFavourite.setText("删除收藏");
					showToast(mContext, "已将题目添加到收藏夹中！", Toast.LENGTH_SHORT);
				}
				
			}
		});
		Button viewAnswer = (Button)topicCell.findViewById(R.id.exam_answer_view);
		if(topic.type.equals(TopicEntity.TopicType.MULTIPLE_CHOICE)){viewAnswer.setVisibility(View.VISIBLE);}
		viewAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				topic.isture = doclickitem(topic, tipContent);
				mCallback.click(v);
				
			}
		});
		return topicCell;
	}
	
	private void createAnswerGroup(LinearLayout answerLayout,TopicEntity topic,TextView Content){
		if(topic.type.equals(TopicEntity.TopicType.JUDGE)){
			createJudgeView(answerLayout,topic,Content);
		}else if(topic.type.equals(TopicEntity.TopicType.MULTIPLE_CHOICE)){
			createMultipleChoiceView(answerLayout,topic);
			
		}else{
			createSingleChoiceView(answerLayout,topic,Content);
			Log.e("extra topic adapter-->", "title type:"+topic.type);
		}
	}
	
	/**
	 * 创建判断题答案组
	 */
	private void createJudgeView(LinearLayout answerLayout,final TopicEntity topic,final TextView Content){
		List<AnswerEntity> answerSet = topic.answers;
		answerLayout.setOrientation(LinearLayout.HORIZONTAL);
		if(answerSet.size() == 2){
			final ViewGroup nullParent = null;
			RelativeLayout trueSelection = (RelativeLayout) mInflater.inflate(R.layout.answer_judge_cell, nullParent);
			RelativeLayout falseSelection = (RelativeLayout) mInflater.inflate(R.layout.answer_judge_cell, nullParent);
			LayoutParams btnParam = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			btnParam.weight = 1.0f;
			trueSelection.setLayoutParams(btnParam);
			falseSelection.setLayoutParams(btnParam);
			
			final ImageView trueIcon = (ImageView)trueSelection.findViewById(R.id.answer_icon);
			final ImageView falseIcon = (ImageView)falseSelection.findViewById(R.id.answer_icon);
			final int trueIndex = 0;
			final int falseIndex = 1;
			final AnswerEntity trueEntity = answerSet.get(trueIndex);
			final AnswerEntity falseEntity = answerSet.get(falseIndex);
			int trueIconResId = trueEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[trueIndex] :
				AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
			int falseIconResId = falseEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[falseIndex] :
				AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
			trueIcon.setImageResource(trueIconResId);
			falseIcon.setImageResource(falseIconResId);
			
			trueSelection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					trueEntity.isChecked = !trueEntity.isChecked;
					int iconResId = trueEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[trueIndex] :
						AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
					trueIcon.setImageResource(iconResId);
					if(trueEntity.isChecked){
						falseEntity.isChecked = false;
						int releaseIconResId = AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
						falseIcon.setImageResource(releaseIconResId);
						topic.isdo = 1;
					}else {
						topic.isdo =0;
					}	
					topic.isture = doclickitem(topic, Content);
					mCallback.click(v); 
				}
			});
			
			falseSelection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					falseEntity.isChecked = !falseEntity.isChecked;
					int iconResId = falseEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[falseIndex] :
						AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
					falseIcon.setImageResource(iconResId);
					if(falseEntity.isChecked){
						trueEntity.isChecked = false;
						int releaseIconResId = AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
						trueIcon.setImageResource(releaseIconResId);
						topic.isdo = 1;
					}else {
						topic.isdo =0;
					}					
					topic.isture = doclickitem(topic, Content);
					mCallback.click(v);  
				}
			});
			
			answerLayout.addView(trueSelection);
			answerLayout.addView(falseSelection);
		}else{
			Log.e("E","判断题只能有两答案选项！");
		}
	}
	
	/**
	 * 创建多选答案组
	 */
	private void createMultipleChoiceView(LinearLayout answerLayout,final TopicEntity topic){
		List<AnswerEntity> answerSet = topic.answers;
		for(int i=0;i<answerSet.size();i++){
			final ViewGroup nullParent = null;
			RelativeLayout answer = (RelativeLayout) mInflater.inflate(R.layout.answer_selection_cell, nullParent);
			final ImageView icon = (ImageView)answer.findViewById(R.id.answer_icon);
			TextView content = (TextView)answer.findViewById(R.id.answer_content);
			final AnswerEntity answerEntity = answerSet.get(i);
			int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.MultipleChoice.PRESSED[i] :
				AppSetting.AnswerIcons.MultipleChoice.NORMAL_ARRAY[i];
			icon.setImageResource(iconResId);
			content.setText(answerEntity.content);
			answerLayout.addView(answer);
			final int index = i;
			answer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					answerEntity.isChecked = !answerEntity.isChecked;
					int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.MultipleChoice.PRESSED[index] :
						AppSetting.AnswerIcons.MultipleChoice.NORMAL_ARRAY[index];
					icon.setImageResource(iconResId);
					if (answerEntity.isChecked) {
						topic.isdo++;
					}else {
						topic.isdo--;
					}
					
				}
			});
		}
	}
	
	/**
	 * 创建单选答案组
	 */
	private void createSingleChoiceView(LinearLayout answerLayout,final TopicEntity topic,final TextView tipContent){
		final List<AnswerEntity> answerSet = topic.answers;
		final int size = answerSet.size();
		final ImageView[] tempIconArray = new ImageView[size];
		for(int i=0;i<size;i++){
			final ViewGroup nullParent = null;
			RelativeLayout answer = (RelativeLayout) mInflater.inflate(R.layout.answer_selection_cell, nullParent);
			final ImageView icon = (ImageView)answer.findViewById(R.id.answer_icon);
			tempIconArray[i] = icon;
			TextView content = (TextView)answer.findViewById(R.id.answer_content);
			final AnswerEntity answerEntity = answerSet.get(i);
			int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.SingleChoice.PRESSED[i] :
				AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[i];
			icon.setImageResource(iconResId);
			content.setText(answerEntity.content);
			answerLayout.addView(answer);
			
			final int index = i;
			answer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					boolean tempState = !answerEntity.isChecked;
					for(int i=0;i<size;i++){
						tempIconArray[i].setImageResource(AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[i]);
						answerSet.get(i).isChecked = false;
					}
					answerEntity.isChecked = tempState;
					int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.SingleChoice.PRESSED[index] :
						AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[index];
					icon.setImageResource(iconResId);
					if (answerEntity.isChecked) {
						topic.isdo = 1;
					}else {
						topic.isdo = 0;
					}
					topic.isture = doclickitem(topic, tipContent);
					mCallback.click(v);  
				}
			});
		}
	}
	private static void showToast(Context context,String text,int duration) {
		 if (mToast == null) {  
	            mToast = Toast.makeText(context, text, duration);  
	        } else {  
	            mToast.setText(text);  
	            mToast.setDuration(duration);  
	        }  

	        mToast.show();  
	}

	private boolean doclickitem(TopicEntity topic, TextView tipContent) {


//		TipDialog mTipDialog = new TipDialog(mContext);
//		mTipDialog.setTipMsg(topic.tip);
//		mTipDialog.show();

		char[] tiparry = topic.tip.toCharArray();
		boolean isture;
		for (int i=0;i<tiparry.length;i++) {
			if(topic.type.equals(TopicEntity.TopicType.JUDGE)){
				tiparry[i] = AppSetting.JUDE_ITEM[Character.getNumericValue(tiparry[i])];
			}else {
				tiparry[i] = AppSetting.CHOICE_ITEM[Character.getNumericValue(tiparry[i])];
			}
		}
		
		if (AppSetting.checkTopic(topic.answers)) {
			isture = true;
			p.delTable(topic.id, 3);
		}else {
//			tipContent.setText("错了，正确答案是: "+String.valueOf(tiparry));
//			
			p.addErr(topic.id);
			isture =false;
		}
//		tipContent.setVisibility(View.VISIBLE);
		return isture;
	
		
	}
	/* (non-Javadoc)
	 * @see com.lurencun.android.support.v2.widget.ViewPagerAdapter#finishedUpdate(com.lurencun.android.support.v2.widget.ViewPager)
	 */
	@Override
	protected void finishedUpdate(ViewPager container) {
		
	}
	
	/* (non-Javadoc)
	 * @see com.lurencun.android.support.v2.widget.ViewPagerAdapter#startingUpdate(com.lurencun.android.support.v2.widget.ViewPager)
	 */
	@Override
	protected void startingUpdate(ViewPager container) {

		
	}

}
