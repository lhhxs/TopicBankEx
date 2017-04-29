package com.lurencun.android.topicbank;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lurencun.android.topicbank.entity.AnswerEntity;
import com.lurencun.android.topicbank.entity.CategoryEntity;
import com.lurencun.android.topicbank.entity.HistoryEntity;
import com.lurencun.android.topicbank.entity.TopicEntity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lurencun.android.topicbank.AppSetting.getJsonToStringArray;

public class ProblemService {
	private ExamDBHelper dbHelper = null;
	private Context mContext;
	private static ProblemService mProblemService;

	public ProblemService(Context context) {
		this.mContext = context;
		
		dbHelper = new ExamDBHelper(mContext);
		try {
			dbHelper.createDataBase();

		} catch (IOException e) {
			e.printStackTrace();
		}
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		Log.e("onUpgrade", "oldv" + db.getVersion());
//		if(db.getVersion()<version) {
//
//
//
//			switch (version) {
//				case 8:
//					boolean dbExist = dbHelper.checkDataBase();
//					if (!dbExist) {
//						//数据库已存在，do nothing
////			mContext.deleteDatabase(DB_PATH + DB_NAME);
//						Log.e("TAG", "数据库不存在，do nothing");
//					} else {
//						File dir = new File(dbHelper.getDbPath());
//						if (!dir.exists()) {
//							dir.mkdirs();
//						}
//						File dbFile = new File(dbHelper.getDbPath() + dbHelper.getDbName());
//						Log.e("TAG", "new File(DB_PATH+ DB_NAME)" + dbFile);
//						if (dbFile.exists()) {
//							dbFile.delete();
//							Log.e("TAG", "new File(DB_PATH+ DB_NAME)" + dbFile);
//						}
//						SQLiteDatabase.openOrCreateDatabase(dbFile, null);
//						Log.e("TAG", "new File(DB_PATH+ DB_NAME)" + dbFile);
//
//						//复制asserts中的db文件到DB_PATH下
//						try {
//							dbHelper.copyDataBase();
//							Log.e("onUpgrade", "newV" + version);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//					break;
//
//			}
//		}
//		db.close();

	}
public static ProblemService getInstance(Context context){

	if(mProblemService ==null)
	{
		mProblemService = new ProblemService(context);
	}

	return  mProblemService;
}
	/**
	 * 得到问题数据
	 * @return
	 * @throws JSONException 
	 */
	public List<TopicEntity> getGroupData(String biao,int tiku,int type) throws JSONException{		
		List<TopicEntity> groupData = new ArrayList<TopicEntity>();
		//File file = new File(context.getFilesDir(), "commonnum.db");
		//SQLiteDatabase db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "db success"+biao);
		if(db.isOpen()){
//			Cursor c = db.rawQuery("select * from ? where tiku = ? and type = ?",new String[] {"tiku",String.valueOf(tiku), String.valueOf(type)});
			String selection = "tiku = ? and type = ?";
			String[] selectionArgs = new  String[]{ String.valueOf(tiku), String.valueOf(type) };
			Cursor c = db.query(biao, new  String[]{"*"}, selection, selectionArgs, null, null, null, null);   
			Log.e("TAG>>>", "db query"+c);
			//if (c.getCount() <= 0){Log.e("TAG>>>", "return NULL topic load ");return groupData;}
			int index = 1;
			while(c.moveToNext()){
				
				TopicEntity problem = new TopicEntity();
				problem.index = index;
				Log.e("TAG>>>>", "extratopic :"+index);
				index++;
				problem.id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
				problem.title = c.getString(c.getColumnIndex("title")).toString();
				problem.tip = c.getString(c.getColumnIndex("correct")).toString();
				if(problem.tip == null || problem.tip.isEmpty()){problem.tip = "";}
//				problem.image = "demo_0.jpg";
				String json_answersString = c.getString(c.getColumnIndex("answers")).toString();
				String[] answerItems = getJsonToStringArray(json_answersString.toString());
				for (int i = 0; i < answerItems.length; i++) {
					AnswerEntity answer = new AnswerEntity();
					answer.content = answerItems[i].toString();
					answer.isChecked = false;
					answer.isCurrent = problem.tip.contains(String.valueOf(i+1));
					if (answer.isCurrent) {
						Log.e("TAG", "answerItems"+i+"is true"+problem.tip);
					}else {
						Log.e("TAG", "answerItems"+i+"is flase"+problem.tip);
					}
					problem.answers.add(answer);
				}

				problem.type=TopicEntity.TopicType.values()[Integer.valueOf(type).intValue()-1];
												
				groupData.add(problem);
				
			}
			c.close();
			db.close();
		}
//		Log.e("TAG", "groupData is add success"+groupData);
		Log.e("TAG>>>>", "extratopic 2");
		return groupData;
	}
	
	/**
	 * 得到随机问题数据
	 * @return
	 * @throws JSONException 
	 */
	public List<TopicEntity> getRandomData(int tiku,int type,int limit) throws JSONException{		
		List<TopicEntity> groupData = new ArrayList<TopicEntity>();
		//File file = new File(context.getFilesDir(), "commonnum.db");
		//SQLiteDatabase db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		if(db.isOpen()){
//			Cursor c = db.rawQuery("select * from ? where tiku = ? and type = ?",new String[] {"tiku",String.valueOf(tiku), String.valueOf(type)});
			String selection = "tiku = ? and type = ?";
			String[] selectionArgs = new  String[]{ String.valueOf(tiku), String.valueOf(type) };
			Cursor c = db.query("tiku", new  String[]{"*"}, selection, selectionArgs, null, null, "RANDOM()", String.valueOf(limit).toString());   
			Log.e("TAG>>>", "db query"+c);
			//if (c.getCount() <= 0){Log.e("TAG>>>", "return NULL topic load ");return groupData;}
			int index = 1;
			while(c.moveToNext()){
				
				TopicEntity problem = new TopicEntity();
				problem.index = index;
				Log.e("TAG>>>>", "extratopic :"+index);
				index++;
				problem.id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
				problem.title = c.getString(c.getColumnIndex("title")).toString();
				problem.tip = c.getString(c.getColumnIndex("correct")).toString();
				if(problem.tip == null || problem.tip.isEmpty()){problem.tip = "";}
//				problem.image = "demo_0.jpg";
				String json_answersString = c.getString(c.getColumnIndex("answers")).toString();
				String[] answerItems = getJsonToStringArray(json_answersString.toString());
				for (int i = 0; i < answerItems.length; i++) {
					AnswerEntity answer = new AnswerEntity();
					answer.content = answerItems[i].toString();
					answer.isChecked = false;
					answer.isCurrent = problem.tip.contains(String.valueOf(i+1));
					if (answer.isCurrent) {
						Log.e("TAG", "answerItems"+i+"is true"+problem.tip);
					}else {
						Log.e("TAG", "answerItems"+i+"is flase"+problem.tip);
					}
					problem.answers.add(answer);
				}

				problem.type=TopicEntity.TopicType.values()[Integer.valueOf(type).intValue()-1];
												
				groupData.add(problem);
				
			}
			c.close();
			db.close();
		}
//		Log.e("TAG", "groupData is add success"+groupData);
		Log.e("TAG>>>>", "extratopic 2");
		return groupData;
	}

	/**
	 * 得到题库列表数据 序号1
	 * @return
	 */
	public List<CategoryEntity> getCategoryData(){
		List<CategoryEntity> groupData = new ArrayList<CategoryEntity>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "db success"+db.toString());
        if(db.isOpen()){doFillliebiao(db, groupData, 1);}
		return groupData;
	}
	/**
	 * 得到收藏题库列表数据 序号2
	 * @return
	 */
	public List<CategoryEntity> getFavoriteData(){
		List<CategoryEntity> groupData = new ArrayList<CategoryEntity>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "db success");
        if(db.isOpen()){
			doFillliebiao(db,groupData,2);
		}
		return groupData;
	}
	/**
	 * 得到收藏题库列表数据 序号3
	 * @return
	 */
	public List<CategoryEntity> getErrData(){
		List<CategoryEntity> groupData = new ArrayList<CategoryEntity>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "db err success");
        if(db.isOpen()){
			doFillliebiao(db,groupData,3);
		}
		return groupData;
	}
	/**
	 * 得到考试题库列表数据 序号5
	 * @return
	 */
	public List<CategoryEntity> getExamData(){
		List<CategoryEntity> groupData = new ArrayList<CategoryEntity>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "db success");
        if(db.isOpen()){
			doFillliebiao(db,groupData,5);
		}
		return groupData;
	}
	/**
	 * 添加收藏题
	 * @return
	 */
	public void addFavorite(int id)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "addErr success");
        if(db.isOpen()){
			insertTable(db, id, 2);
		}
	}
	/**
	 * 添加错题
	 * @return
	 */
	public void addErr(int id)
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("TAG", "addErr success");
        if(db.isOpen()){
			insertTable(db, id, 3);
		}
	}

	/** 
	  * 在表中删除已存在数据
	  * @param id
	  * @param table
	  * @return void
	  */  
	public void delTable(int id,int table){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
	    String sql = null;
		 switch (table) {
		case 2:
			sql = "delete from shouchang where id=?";
			break;
		case 3:
			sql = "delete from cuoti where id=?";
			break;
		default:
//			db.execSQL("insert into shouchang (title,correct,type,tiku,answers,make_time) select title,correct,type,tiku,answers,'?' as make_time from tiku WHERE not exists (select title,correct,type,tiku,answers,'?' from shouchang WHERE shouchang.id = ?) and tiku.id = ?",args);
			Log.e("TAG", "db tikuliebiao isexsit fall");
			break;
		}
		if(db.isOpen()){
			Object[] args = new Object[]{id};
			db.execSQL(sql,args);
			Log.e("TAG", "db shouchangtikuliebiao delete");
			}
					
		db.close();
	 }
	
	/** 
	  * 题目是否在表中存在
	  * @param id
	  * @param table
	  * @return void
	  */  
	public boolean isExistTable(int id,int table){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
	    String sql = null;
	    boolean result = false;
		 switch (table) {
		case 2:
			sql = "select count(*) from (select * from tiku where id = ?)t1 left outer join shouchang t2 on t1.title = t2.title and t1.type = t2.type and t1.tiku = t2.tiku where t2.id is not null";
			break;
		case 3:
			sql = "select count(*) from (select * from tiku where id = ?)t1 left outer join cuoti t2 on t1.title = t2.title and t1.type = t2.type and t1.tiku = t2.tiku where t2.id is not null";
			break;
		default:
//			db.execSQL("insert into shouchang (title,correct,type,tiku,answers,make_time) select title,correct,type,tiku,answers,'?' as make_time from tiku WHERE not exists (select title,correct,type,tiku,answers,'?' from shouchang WHERE shouchang.id = ?) and tiku.id = ?",args);
			Log.e("TAG", "db tikuliebiao isexsit fall");
			break;
		}
		 if(sql == null||sql.isEmpty()){db.close();return result;}
		if(db.isOpen()){
			Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
			Log.e("isExistTable","查询后获得记录集c:"+c);
			if(c.moveToNext()){
				Log.e("isExistTable","查询后获得记录集c+1:"+c);
				result = c.getInt(0) >0;
				Log.e("isExistTable","获得count数，判断是否存在："+result);
			}
			c.close();
		}
		db.close();
		return result;
	 }
	/** 
	  * 插入题目，从题库中将数据放入对应的表中
	  * @param db 
	  * @param id
	  * @param table
	  * @return void
	  */  
	public void insertTable(SQLiteDatabase db,int id,int table){
		 
		 Object[] args = new Object[]{id};
		 switch (table) {
		case 2:
			db.execSQL("insert into shouchang (id,title,correct,type,tiku,answers,make_time) select t1.id,t1.title,t1.correct,t1.type,t1.tiku,t1.answers,t1.make_time from (select * from tiku where id = ?)t1 left outer join shouchang t2 on t1.title = t2.title and t1.type = t2.type and t1.tiku = t2.tiku where t2.id is null ",args);
			Log.e("TAG", "db shouchangtikuliebiao insert");
			break;
		case 3:
			db.execSQL("insert into cuoti (id,title,correct,type,tiku,answers,make_time) select t1.id,t1.title,t1.correct,t1.type,t1.tiku,t1.answers,t1.make_time from (select * from tiku where id = ?)t1 left outer join cuoti t2 on t1.title = t2.title and t1.type = t2.type and t1.tiku = t2.tiku where t2.id is null",args);
			Log.e("TAG", "db cuotitikuliebiao insert");
			break;
		default:
//			db.execSQL("insert into shouchang (title,correct,type,tiku,answers,make_time) select title,correct,type,tiku,answers,'?' as make_time from tiku WHERE not exists (select title,correct,type,tiku,answers,'?' from shouchang WHERE shouchang.id = ?) and tiku.id = ?",args);
			Log.e("TAG", "db tikuliebiao insert fall");
			break;
		}
		db.close(); 
	 }
	 public void insertKaoshijilu(Object[] args){
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		 String sql = "insert into kaoshijilu (sid,shijuanliebiao,defen,zhengque,cuowu,kaoshitimu,kaoshidaan,make_time) values(?,?,?,?,?,?,?,?)";
		 db.execSQL(sql,args);
		 db.close();
	 };
	/** 
	  * 查询题库列表，将数据放入每个项中
	  * @param db 
	  * @param groupData
	  * @param menu
	  * @return void
	  */  
	 public void doFillliebiao(SQLiteDatabase db,List<CategoryEntity> groupData,int menu){
		 Cursor c = null;
		 switch (menu) {
		case 2:
			c = db.rawQuery("SELECT  t.id,t.tikuliebiao,t.miaoshu,t.jingzhong FROM  tikuliebiao t INNER JOIN (SELECT tiku FROM shouchang GROUP BY tiku ORDER BY tiku) t1 WHERE t.id = t1.tiku",null);
			Log.e("TAG", "db shouchangtikuliebiao query"+c);
			break;
		case 3:
			c = db.rawQuery("SELECT  t.id,t.tikuliebiao,t.miaoshu,t.jingzhong FROM  tikuliebiao t INNER JOIN (SELECT tiku FROM cuoti GROUP BY tiku ORDER BY tiku) t1 WHERE t.id = t1.tiku",null);
			Log.e("TAG", "db cuotitikuliebiao query"+c);
			break;
		case 4:
			c = db.rawQuery("SELECT  t.id,t.tikuliebiao,t.miaoshu,t.jingzhong FROM  tikuliebiao t INNER JOIN (SELECT tiku FROM kaoshijilu GROUP BY tiku ORDER BY tiku) t1 WHERE t.id = t1.tiku",null);
			Log.e("TAG", "db kaoshijilutikuliebiao query"+c);
			break;
		case 5:
			c = db.rawQuery("SELECT  t.id,t.shijuanliebiao,t.tiku,t.miaoshu,t.danxuanshu,t.danxuanfen,t.duoxuanshu,t.duoxuanfen,t.panduanshu,t.panduanfen,t.daojishi FROM  shijuanliebiao t INNER JOIN (SELECT tiku FROM tiku GROUP BY tiku) t1 WHERE t.tiku = t1.tiku",null);
			Log.e("TAG", "db kaoshishijuantikuliebiao query"+c);
			break;
		default:
			Log.e("TAG", "db tikuliebiao query"+c);
			c = db.rawQuery("SELECT  t.id,t.tikuliebiao,t.miaoshu,t.jingzhong FROM tikuliebiao t INNER JOIN (SELECT tiku FROM tiku GROUP BY tiku ORDER BY tiku) t1 on t.id = t1.tiku",null);
			
			break;
		}
		
		
		while(c.moveToNext()&&c.getCount()>0){
			
			CategoryEntity CategoryItem = new CategoryEntity();
			CategoryItem.isFinish = false;
			
			CategoryItem.describe =c.getString(c.getColumnIndex("miaoshu")).toString();
			CategoryItem.id = c.getInt(c.getColumnIndex("id"));
			if(menu == 5)
			{
				CategoryItem.title = c.getString(c.getColumnIndex("shijuanliebiao")).toString();
				CategoryItem.tiku= c.getInt(c.getColumnIndex("tiku"));
				CategoryItem.danxuanfen= c.getInt(c.getColumnIndex("danxuanfen"));
				CategoryItem.danxuanshu= c.getInt(c.getColumnIndex("danxuanshu"));
				CategoryItem.duoxuanfen= c.getInt(c.getColumnIndex("duoxuanfen"));
				CategoryItem.duoxuanshu= c.getInt(c.getColumnIndex("duoxuanshu"));
				CategoryItem.panduanfen= c.getInt(c.getColumnIndex("panduanfen"));
				CategoryItem.panduanshu= c.getInt(c.getColumnIndex("panduanshu"));
				CategoryItem.daojishi= c.getInt(c.getColumnIndex("daojishi"));
			}
			else {
				CategoryItem.title = c.getString(c.getColumnIndex("tikuliebiao")).toString();
			}
			groupData.add(CategoryItem);
			Log.e("TAG", "db liebiao->"+CategoryItem.title);
			
		}
		c.close();
		db.close();
	 }


	/**
	 * 得到歷史考試数据列表
	 * @return
	 * @throws JSONException
	 */
	public List<HistoryEntity> getHistoryDatalist()
	{
		List<HistoryEntity> groupData = new ArrayList<HistoryEntity>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.e("TAG", "db success");
		if(db.isOpen()){
			Cursor c = null;
			c = db.rawQuery("select t.id,t.shijuanliebiao as zongshu ,t1.shijuanliebiao,t.defen,t.zhengque,t.cuowu,t.kaoshidaan,t.make_time from kaoshijilu t inner join shijuanliebiao t1 on t.sid = t1.id order by t.id desc",null);

			while(c.moveToNext()&&c.getCount()>0){

				HistoryEntity HistoryItem = new HistoryEntity();
				HistoryItem.setTopic(c.getInt(c.getColumnIndex("id")));
				HistoryItem.setTitle(c.getString(c.getColumnIndex("shijuanliebiao")).toString());
				int i =c.getInt(c.getColumnIndex("zhengque"));
				HistoryItem.setCorrect(i==0?1:i);
				HistoryItem.setMistake(c.getInt(c.getColumnIndex("cuowu")));
				HistoryItem.setScore(c.getInt(c.getColumnIndex("defen")));
				HistoryItem.setTime(c.getLong(c.getColumnIndex("make_time")));
				String zongshu = c.getString(c.getColumnIndex("zongshu")).toString();
				if(null == zongshu||zongshu.equals("")||zongshu.isEmpty()){
					HistoryItem.setZongshu(0);
				}else {
					HistoryItem.setZongshu(Integer.parseInt(zongshu));
				}
				String Shijian = c.getString(c.getColumnIndex("kaoshidaan")).toString();
				if(null == Shijian||Shijian.equals("")||Shijian.isEmpty()){
					HistoryItem.setShijian(0);
				}else {
					HistoryItem.setShijian(Long.parseLong(Shijian));
				}



				groupData.add(HistoryItem);
				Log.e("TAG", "db liebiao->"+HistoryItem.getTitle());

			}
			c.close();
			db.close();
		}
		return groupData;

	}
	/**
	 * 得到歷史考試数据
	 * @param id
	 * @return
	 * @throws JSONException
	 */
	public String getHistoryDatalist(int id)
	{
		String data = "";
		String[] args = new String[]{String.valueOf(id)};
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.e("TAG", "db success");
		if(db.isOpen()){
			Cursor c = null;
			c = db.rawQuery("select kaoshitimu from kaoshijilu where id= ?",args);

			while(c.moveToNext()&&c.getCount()>0){
				data = c.getString(c.getColumnIndex("kaoshitimu"));
			}
			c.close();
			db.close();
		}
		return data;

	}
	/**
	 * 得到题库搜索数据
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public List<TopicEntity> getSearchData(String str) throws JSONException
	{
		List<TopicEntity> groupData = new ArrayList<TopicEntity>();
		String data = "%"+str+"%";
		String[] args = new String[]{data};
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.e("TAG", "db success");
		if(db.isOpen()){
			Cursor c = null;
			c = db.rawQuery("select t.id,t.title,t1.tikuliebiao,t.correct,t.type,t.tiku,t.answers,t.make_time from tiku t left outer join tikuliebiao t1 on t.tiku = t1.id where t.title like ?",args);
			int index = 1;
			while(c.moveToNext()&&c.getCount()>0){
				TopicEntity problem = new TopicEntity();
				problem.index = index;
				Log.e("TAG>>>>", "extratopic :"+index);
				index++;
				problem.id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
				problem.title = c.getString(c.getColumnIndex("title")).toString();
				problem.tip = c.getString(c.getColumnIndex("correct")).toString();
				if(problem.tip == null || problem.tip.isEmpty()){problem.tip = "";}
//				problem.image = "demo_0.jpg";
				problem.image = c.getString(c.getColumnIndex("tikuliebiao")).toString();
				String json_answersString = c.getString(c.getColumnIndex("answers")).toString();
				String[] answerItems = getJsonToStringArray(json_answersString.toString());
				for (int i = 0; i < answerItems.length; i++) {
					AnswerEntity answer = new AnswerEntity();
					answer.content = answerItems[i].toString();
					answer.isChecked = false;
					answer.isCurrent = problem.tip.contains(String.valueOf(i+1));
					if (answer.isCurrent) {
						Log.e("TAG", "answerItems"+i+"is true"+problem.tip);
					}else {
						Log.e("TAG", "answerItems"+i+"is flase"+problem.tip);
					}
					problem.answers.add(answer);
				}
				int type = c.getInt(c.getColumnIndex("type"));
				problem.type=TopicEntity.TopicType.values()[type-1];

				groupData.add(problem);

			}
			c.close();
			db.close();
		}
		return groupData;

	}

}
