package com.lurencun.android.topicbank.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lurencun.android.topicbank.ProblemService;
import com.lurencun.android.topicbank.R;
import com.lurencun.android.topicbank.entity.TopicEntity;
import com.lurencun.android.topicbank.res.ShowFenAdapter;
import com.lurencun.android.topicbank.widget.ItemDivider;

import java.util.List;

/**
 * Created by admin on 2017/3/27.
 */

public class ShowFenActivity extends AppCompatActivity  {

    List<TopicEntity> data = null;
    private RecyclerView mRecyclerView;
    private ImageView backImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        init();

    }

    private void init() {
        // Get APSV
        Intent intent = getIntent();
        String TAG = intent.getStringExtra("TAG");
        int zongfen = intent.getIntExtra("zongfen", 1);
        int defen = intent.getIntExtra("defen", 1);
        int errNum = intent.getIntExtra("errNum", 0);
        Log.e("ShowFen","errNum:" +String.valueOf(errNum));
        int zongshu = intent.getIntExtra("zongshu", 0);
        long time = intent.getLongExtra("time", 0);
        TopicEntity topic = new TopicEntity();
        topic.isdo = zongfen;
        topic.id = defen;
        topic.index = errNum;
        topic.tip = String.valueOf(zongshu);

        if(null != TAG && TAG.length()>0 &&TAG.equals("ExtraExamActivity")) {
            data = (List<TopicEntity>) intent.getSerializableExtra("data");
            data.add(0,topic);
        }else  if(null != TAG && TAG.length()>0 &&TAG.equals("HistoryActivity")) {
            ProblemService p = ProblemService.getInstance(getApplicationContext());
            String dataJson = p.getHistoryDatalist(intent.getIntExtra("data", 0));
            if (null == dataJson||dataJson.isEmpty()||dataJson.equals("")||dataJson.length()<=0)
            {
                Toast.makeText(getApplicationContext(), "没有内容！",
                        Toast.LENGTH_SHORT).show();
            }else {
                Gson gson = new Gson();
                data = gson.fromJson(dataJson, new TypeToken<List<TopicEntity>>() {
                }.getType());
            }
            data.add(0,topic);
        }



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new ItemDivider().setDividerWith(30).setDividerColor(Color.LTGRAY));
        mRecyclerView.setAdapter(new ShowFenAdapter(data,time));
        backImageView = (ImageView) findViewById(R.id.back_view);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


}

}
