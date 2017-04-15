package com.lurencun.android.topicbank.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.res.CardAnswerAdapter;

import java.util.List;

@SuppressLint("ValidFragment")
public class AnswerCard extends DialogFragment {
	private int mUniqueFlag = -1;
	private RecyclerView gallery_recycler;
	private onTestListener mOnListener;

	private Context context;
	List<TopicEntity> data;
	protected Button mButtonPositive;



	@SuppressLint("ValidFragment")
	public AnswerCard(List<TopicEntity> data)
	{
		super();

		this.data = data;
		Log.e("AnswerCard","gouzhaohanshu:"+data.toString());
	}

	public static AnswerCard newInstance(String title, int unique,
			String strName, String strHigh,List<TopicEntity> data) {
		AnswerCard tDialog = new AnswerCard(data);
		Bundle args = new Bundle();
		args.putString("SelectTemplateTitle", title);
		args.putInt("MultipleTemplate", unique);
		tDialog.setArguments(args);
		Log.e("AnswerCard","newInstance:"+tDialog.toString());
		return tDialog;

	}



	// 旋转时候保存
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}


	@Override
	public Dialog onCreateDialog(Bundle saveInstanceState) {
		String title = getArguments().getString("SelectTemplateTitle");
		mUniqueFlag = getArguments().getInt("MultipleTemplate");
		
		AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity(),R.style.Dialog_FullScreen);


		// 添加xml布局
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.answer_card, null);
		setupUI(view);
		Log.e("AnswerCard","onCreateDialog:"+view.toString());
		// 旋转后,恢复数据
		if (saveInstanceState != null) {

		}
		Builder.setView(view);

		// 创建对话框
		AlertDialog dialog = (AlertDialog) Builder.create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	private void setupUI(View view) {
		if (view == null)
			return;
		ImageView backView = (ImageView) view.findViewById(R.id.back_view);
		backView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerCard.this.dismiss();
			}
		});
		Button commit = (Button) view.findViewById(R.id.button_commit);
		commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				mOnListener.onCardButtonListener();

//				((Activity)context).finish();
			}
		});
		TextView titleTextView = (TextView)view.findViewById(R.id.app_title);
		titleTextView.setText("答题卡");
		//初始化RecyclerView控件
		gallery_recycler=(RecyclerView)view.findViewById(R.id.recyclerview);
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		commit.measure(w, h);
		int height =commit.getMeasuredHeight();
		LinearLayout ly = (LinearLayout) view.findViewById(R.id.cardlinear);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);  		lp.setMargins(0,0,0,height);
		ly.setLayoutParams(lp);
		Log.e("AnswerCard","初始化recycler:"+gallery_recycler.toString());
		//固定高度
		gallery_recycler.setHasFixedSize(true);
		//创建布局管理器
		GridLayoutManager girdLayoutManager=new GridLayoutManager(context,5);
		//设置横向
//		girdLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
	  //设置布局管理器
	    gallery_recycler.setLayoutManager(girdLayoutManager);
	  //创建适配器

		CardAnswerAdapter adapter=new CardAnswerAdapter(context, data, new OnRecyclerItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				mOnListener.onCardItemListener(position);
				Log.e("OnRecyclerItem","您点击Item:"+position);
				AnswerCard.this.dismiss();
			}
		});
		Log.e("onAttach","创建适配器CardAnswerAdapter:"+adapter.toString());
		//绑定适配器
		if (null ==adapter )
		{
			Log.e("onAttach","适配器adapter为空:"+adapter.toString());
		}
		gallery_recycler.setAdapter(adapter);

		Log.e("AnswerCard","setupUI:"+view.toString());
	    
		//		meditTextName.setText(strName);
//		meditTextHigh.setText(strHigh);
	}

	// onAttach是关联activity的,用接口回调
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		try {
			mOnListener = (onTestListener) activity;
		} catch (ClassCastException e) {
			dismiss();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setStyle(DialogFragment.STYLE_NO_TITLE,0);
	}
    @Override
	public void onResume() {
        super.onResume();
        
        Window mWindow = getDialog().getWindow();
//        mWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity =Gravity.CENTER;
//        mLayoutParams.dimAmount = 0.0f;
       
        mWindow.setAttributes(mLayoutParams);
    }
    
}
