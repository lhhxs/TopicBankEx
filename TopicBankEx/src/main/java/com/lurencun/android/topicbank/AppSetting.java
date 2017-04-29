/**
 * 
 */
package com.lurencun.android.topicbank;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lurencun.android.topicbank.entity.AnswerEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-2-24
 */
public final class AppSetting {
	public final static String APP_TAG = "TopicBank";
	public final static String TOPIC_INDEX = "第%d题";
	public final static String MENU_KEY = "VALUE";
	public final static String MENU_TITLE = "TITLE";
	
	public final static String TOPIC_BACK_TIP = "您当前正在考试，确定返回目录界面吗？";
	public final static long[] VIRATOR_PARAMS = new long[] { 10, 50 };
	public final static char[] CHOICE_ITEM = new char[]{'0','A','B','C','D','E','F','G','H','I'};
	public final static char[] JUDE_ITEM = new char[]{'0','√','×'};
	public final static Boolean checkTopic(List<AnswerEntity> answers)
	{
		for(int i = 0; i < answers.size(); i++){
			if(answers.get(i).isChecked ^ answers.get(i).isCurrent){return false;}
			}
		return true;
	}
	
	public final static class AnswerIcons{
		
		/**
		 * 单选
		 * 
		 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
		 * @date 2012-3-6
		 */
		public final static class SingleChoice{
			public final static int[] PRESSED = new int[]{
				R.drawable.answer_a_single_normal,
				R.drawable.answer_b_single_normal,
				R.drawable.answer_c_single_normal,
				R.drawable.answer_d_single_normal,
				R.drawable.answer_e_single_normal,
				R.drawable.answer_f_single_normal,
				R.drawable.answer_g_single_normal
			};
			
			public final static int[] NORMAL_ARRAY = new int[]{
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected
			};
		}
		
		/**
		 * 多选
		 * 
		 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
		 * @date 2012-3-6
		 */
		public final static class MultipleChoice{
			public final static int[] PRESSED = new int[]{
				R.drawable.answer_a,
				R.drawable.answer_b,
				R.drawable.answer_c,
				R.drawable.answer_d,
				R.drawable.answer_e,
				R.drawable.answer_f,
				R.drawable.answer_g
			};
			
			public final static int[] NORMAL_ARRAY= new int[]{
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select
			};
		}
		
		/**
		 * 判断题
		 * 
		 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
		 * @date 2012-3-6
		 */
		public final static class Judge{
			public final static int[] NORMAL_ARRAY = new int[]{
				R.drawable.answer_judge_true_normal,
				R.drawable.answer_judge_false_normal
			};
			
			public final static int[] PRESSED_ARRAY = new int[]{
				R.drawable.answer_judge_true_selected,
				R.drawable.answer_judge_false_selected
			};
		}
		
	}

	public static String getSystemFilePath(Context context) {

		   String cachePath;  
	        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	                || !Environment.isExternalStorageRemovable()) { 
	            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();  
//	            cachePath = context.getExternalCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看  
	        } else {  
	            cachePath = context.getFilesDir().getAbsolutePath();  
//	            cachePath = context.getCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看  
	        }  
	        return cachePath;  
	    
	}



	public static String showTimeCount(long time) {

		System.out.println("time=" + time);
		if (time >= 360000000) {
			return "00:00:00";
		}
		String timeCount = "";
		long hourc = time / 3600000;
		String hour = "0" + hourc;
		System.out.println("hour=" + hour);
		hour = hour.substring(hour.length() - 2, hour.length());
		System.out.println("hour2=" + hour);

		long minuec = (time - hourc * 3600000) / (60000);
		String minue = "0" + minuec;
		System.out.println("minue=" + minue);
		minue = minue.substring(minue.length() - 2, minue.length());
		System.out.println("minue2=" + minue);

		long secc = (time - hourc * 3600000 - minuec * 60000) / 1000;
		String sec = "0" + secc;
		System.out.println("sec=" + sec);
		sec = sec.substring(sec.length() - 2, sec.length());
		System.out.println("sec2=" + sec);
		timeCount = hour + ":" + minue + ":" + sec;
		System.out.println("timeCount=" + timeCount);
		return timeCount;
		
	}
	/**
	 * 将json数组转化为String型
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public static String[] getJsonToStringArray(String str) throws JSONException {
		if(null == str||str.length()<=0){return new String[]{"此题录入问题没有答案选项"};  }

		JSONArray jsonArray = new JSONArray(str);
		String[] arr=new String[jsonArray.length()];
		for(int i=0;i<jsonArray.length();i++){
			arr[i]=jsonArray.getString(i);
			Log.e("TAG",arr[i]);
		}
		return arr;
	}
	
}
