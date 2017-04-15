package com.lurencun.android.topicbank.res;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.widget.OnRecyclerItemClickListener;

import java.util.List;


public class CardAnswerAdapter extends RecyclerView.Adapter<CardAnswerAdapter.ViewHolder> {
	 private List<TopicEntity> models; 
	 private LayoutInflater mInflater;
     private OnRecyclerItemClickListener onRecyclerItemClickListener;
	 public CardAnswerAdapter(Context context,List<TopicEntity> topicEntities,OnRecyclerItemClickListener onRecyclerItemClickListener){

		 Log.e("CardAnswerAdapter","适配器构造函数");
	            models = topicEntities;
		 Log.e("CardAnswerAdapter","models："+models.get(1).title.toString());
	        mInflater=LayoutInflater.from(context);
            this.onRecyclerItemClickListener = onRecyclerItemClickListener;
	 }
	 /** 
	     * 创建Item View  然后使用ViewHolder来进行承载 
	     * @param parent 
	     * @param viewType 
	     * @return 
	     */  
	    @Override  
	    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Log.e("CardAnswerAdapter","onCreateViewHolder:"+viewType);
            View view = mInflater.inflate(R.layout.card_item,parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerItemClickListener != null) {
                        onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
                    }
                }
            });
	        ViewHolder viewHolder=new ViewHolder(view);  
	        return viewHolder;  
	    }

	    /**
	     * 进行绑定数据
	     * @param holder
	     * @param position
	     */
	    @Override
	    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.item_img.setImageResource(models.get(position).isdo > 0 ? R.drawable.card_selected : R.drawable.card_unselected);

	        holder.item_tv.setText("第"+String.valueOf(position+1).toString()+"题");
            holder.itemView.setTag(position);
	       }
	   
	    @Override  
	    public int getItemCount() {  
	        return models.size();  
	    }  
	    public static class ViewHolder extends RecyclerView.ViewHolder {  
	        private ImageView item_img;  
	        private TextView item_tv;  
	        public ViewHolder(View view){  
	            super(view);  
	           item_img=(ImageView)view.findViewById(R.id.item_img);
	           item_tv=(TextView) view.findViewById(R.id.item_tv) ; 
	        }  
	    }  
}
