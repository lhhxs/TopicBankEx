package com.lurencun.android.topicbank.res;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gigamole.library.ArcProgressStackView;
import com.lurencun.android.topicbank.AppSetting;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.AnswerEntity;
import com.lurencun.android.topicbank.entity.TopicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/3.
 */

public class ShowFenAdapter extends RecyclerView.Adapter<ShowFenAdapter.RecyclerViewHolder>  {
    private List<TopicEntity> datas;
    private long time;
    private static final int IS_HEADER = 2;
    private static final int IS_NORMAL = 1;
    public ShowFenAdapter(List<TopicEntity> datas,long time) {
        this.datas = datas;
        this.time = time;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerViewHolder holder;
        //对不同的flag创建不同的Holder
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_fen, viewGroup, false);
            holder = new RecyclerViewHolder(view,IS_HEADER);
            return holder;
        }else if(viewType==IS_NORMAL){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_cell, viewGroup, false);
            holder = new RecyclerViewHolder(view,IS_NORMAL);
            return holder;
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        //对不同的Item相应不同的操作
        recyclerViewHolder.setIsRecyclable(false);
        if(position!=0&&position!=datas.size()+1&&recyclerViewHolder.viewType==IS_NORMAL){
            datas.get(position).index = position;
            recyclerViewHolder.index.setText(String.format(AppSetting.TOPIC_INDEX, position));
            recyclerViewHolder.content.setText(datas.get(position).title);
            createAnswerGroup(recyclerViewHolder,datas.get(position));
            doclickitem(datas.get(position),recyclerViewHolder.tipContent);

        }
        if(position==0&&recyclerViewHolder.viewType==IS_HEADER){
            //header
            ArrayList<ArcProgressStackView.Model> model = new ArrayList<>();
            recyclerViewHolder.models.setProgress(datas.get(0).id*100/(datas.get(0).isdo==0?1:datas.get(0).isdo));
            model.add(recyclerViewHolder.models);
            recyclerViewHolder.mArcProgressStackView.setModels(model);
            recyclerViewHolder.mArcProgressStackView.setIsShadowed(false);
            recyclerViewHolder.mArcProgressStackView.setIsRounded(true);
            recyclerViewHolder.mArcProgressStackView.setIsAnimated(true);
            recyclerViewHolder.mArcProgressStackView.setInterpolator(new OvershootInterpolator());

            recyclerViewHolder.mArcProgressStackView.animateProgress();
            recyclerViewHolder.mArcProgressStackView.postInvalidate();
            recyclerViewHolder.fen_shu.setText(String.valueOf(datas.get(0).id));
            recyclerViewHolder.shi_jian.setText(AppSetting.showTimeCount(time));
            recyclerViewHolder.errNumView.setText(String.valueOf(datas.get(0).index));
            recyclerViewHolder.trueNumView.setText(String.valueOf(Integer.parseInt(datas.get(0).tip) - datas.get(0).index));
        }

    }
private void createAnswerGroup(RecyclerViewHolder recyclerViewHolder, TopicEntity topic) {
    if(topic.type.equals(TopicEntity.TopicType.JUDGE)){

        createJudgeView(recyclerViewHolder,topic);
    }else if(topic.type.equals(TopicEntity.TopicType.MULTIPLE_CHOICE)){
        createMultipleChoiceView(recyclerViewHolder,topic);

    }else{
        createSingleChoiceView(recyclerViewHolder,topic);
        Log.e("extra topic adapter-->", "title type:"+topic.type);
    }

    }

    /**
     * 创建判断题答案组
     * @param recyclerViewHolder
     * @param topic
     */
    private void createJudgeView(RecyclerViewHolder recyclerViewHolder,final TopicEntity topic)
    {

        List<AnswerEntity> answerSet = topic.answers;
        recyclerViewHolder.answerLayout.setOrientation(LinearLayout.HORIZONTAL);
        if(answerSet.size() == 2){
            RelativeLayout trueSelection = (RelativeLayout) View.inflate(recyclerViewHolder.topicCell.getContext(),R.layout.answer_judge_cell,null);
            RelativeLayout falseSelection = (RelativeLayout) View.inflate(recyclerViewHolder.topicCell.getContext(),R.layout.answer_judge_cell,null);
            LinearLayout.LayoutParams btnParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            btnParam.weight = 1.0f;
            trueSelection.setLayoutParams(btnParam);
            falseSelection.setLayoutParams(btnParam);

             ImageView trueIcon = (ImageView)trueSelection.findViewById(R.id.answer_icon);
             ImageView falseIcon = (ImageView)falseSelection.findViewById(R.id.answer_icon);
             int trueIndex = 0;
             int falseIndex = 1;
             AnswerEntity trueEntity = answerSet.get(trueIndex);
             AnswerEntity falseEntity = answerSet.get(falseIndex);
            int trueIconResId = trueEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[trueIndex] :
                    AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
            int falseIconResId = falseEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[falseIndex] :
                    AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
            trueIcon.setImageResource(trueIconResId);
            falseIcon.setImageResource(falseIconResId);
            recyclerViewHolder.answerLayout.addView(trueSelection);
            recyclerViewHolder.answerLayout.addView(falseSelection);


        }else{
            Log.e("E","判断题只能有两答案选项！");
        }
    }
    private void createMultipleChoiceView(RecyclerViewHolder recyclerViewHolder,TopicEntity topic) {
        List<AnswerEntity> answerSet = topic.answers;
        for(int i=0;i<answerSet.size();i++) {
            RelativeLayout answer = (RelativeLayout) View.inflate(recyclerViewHolder.topicCell.getContext(), R.layout.answer_selection_cell, null);
            ImageView icon = (ImageView) answer.findViewById(R.id.answer_icon);
            TextView content = (TextView) answer.findViewById(R.id.answer_content);
            AnswerEntity answerEntity = answerSet.get(i);
            int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.MultipleChoice.PRESSED[i] :
                    AppSetting.AnswerIcons.MultipleChoice.NORMAL_ARRAY[i];
            icon.setImageResource(iconResId);
            content.setText(answerEntity.content);
            recyclerViewHolder.answerLayout.addView(answer);

        }

    }
    private void createSingleChoiceView(RecyclerViewHolder recyclerViewHolder,TopicEntity topic) {
        List<AnswerEntity> answerSet = topic.answers;

         int size = answerSet.size();
         ImageView[] tempIconArray = new ImageView[size];
        for(int i=0;i<size;i++) {
            RelativeLayout answer = (RelativeLayout) View.inflate(recyclerViewHolder.topicCell.getContext(), R.layout.answer_selection_cell, null);
            ImageView icon = (ImageView) answer.findViewById(R.id.answer_icon);
            tempIconArray[i] = icon;
            TextView content = (TextView) answer.findViewById(R.id.answer_content);
            AnswerEntity answerEntity = answerSet.get(i);
            int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.SingleChoice.PRESSED[i] :
                    AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[i];
            icon.setImageResource(iconResId);
            content.setText(answerEntity.content);
            recyclerViewHolder.answerLayout.addView(answer);

        }

    }



        @Override
    public int getItemCount() {
        return datas.size();

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else {
            return IS_NORMAL;
        }
    }


    public class RecyclerViewHolder  extends RecyclerView.ViewHolder{

        ScrollView topicCell;
        TextView index;
        TextView content;
        ImageView image;
        LinearLayout answerLayout ;
        TextView tipContent;
        TextView fen_shu;
        TextView shi_jian;
        TextView errNumView;
        TextView trueNumView;
        ArcProgressStackView mArcProgressStackView;
        ArcProgressStackView.Model models;
        public int viewType;
        public RecyclerViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if(viewType==IS_HEADER){
                mArcProgressStackView = (ArcProgressStackView) itemView.findViewById(R.id.apsv);
                fen_shu = (TextView)itemView.findViewById(R.id.fen_shu);
                shi_jian = (TextView)itemView.findViewById(R.id.shi_jian);
                trueNumView = (TextView)itemView.findViewById(R.id.zheng_que);
                errNumView = (TextView)itemView.findViewById(R.id.cuo_wu);
                final String[] startColors = itemView.getResources().getStringArray(R.array.polluted_waves);
                final String[] bgColors = itemView.getResources().getStringArray(R.array.medical_express);

                models = new ArcProgressStackView.Model("正确率", 0, Color.parseColor(bgColors[1]), Color.parseColor(startColors[1]));


            }
            if(viewType==IS_NORMAL){
                 topicCell = (ScrollView) itemView;
                 index = (TextView)topicCell.findViewById(R.id.show_index);
                 content = (TextView)topicCell.findViewById(R.id.show_content);
                 image = (ImageView)topicCell.findViewById(R.id.show_imgs);
                 answerLayout = (LinearLayout)topicCell.findViewById(R.id.show_answer_layout);
                 tipContent = (TextView)topicCell.findViewById(R.id.show_tip);
            }
        }


    }
    private void doclickitem(TopicEntity topic, TextView tipContent) {


        char[] tiparry = topic.tip.toCharArray();

        for (int i=0;i<tiparry.length;i++) {
            if(topic.type.equals(TopicEntity.TopicType.JUDGE)){
                tiparry[i] = AppSetting.JUDE_ITEM[Character.getNumericValue(tiparry[i])];
            }else {
                tiparry[i] = AppSetting.CHOICE_ITEM[Character.getNumericValue(tiparry[i])];
            }
        }

        if (AppSetting.checkTopic(topic.answers)) {
            String str = "恭喜，答对了，答案是: "+String.valueOf(tiparry);
            SpannableStringBuilder sp = new  SpannableStringBuilder(str);
            sp.setSpan(new ForegroundColorSpan(0xFF64DD17), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
            sp.setSpan(new AbsoluteSizeSpan(20, true), 0, str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
            tipContent.setText(sp);
        }else {
            String str = "错了，正确答案是: "+String.valueOf(tiparry);
            SpannableStringBuilder sp = new  SpannableStringBuilder(str);
            sp.setSpan(new ForegroundColorSpan(0xFFFF0000), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
            sp.setSpan(new AbsoluteSizeSpan(20, true), 0, str.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
            tipContent.setText(sp);
        }


    }
}
