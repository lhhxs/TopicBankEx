package com.plisexam.admin.test;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.plisexam.admin.test.Util.BaseUI.BaseCompatActivity;
import com.plisexam.admin.test.Util.NetWork.entity.LoginResult;
import com.plisexam.admin.test.Util.NetWork.entity.Result;
import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;
import com.plisexam.admin.test.Util.NetWork.http.HttpMethods;
import com.plisexam.admin.test.Util.NetWork.subscribers.ProgressSubscriber;
import com.plisexam.admin.test.Util.NetWork.subscribers.SubscriberOnNextListener;

public class MainActivity extends BaseCompatActivity {
    /** 外网测试时，需要修改对应的服务器ip和端口 （内网和外网模式的切换开关。） */
    public static final boolean isPoliceNet = false;
    /** 测试数据和真实数据的切换开关。 */
    public static final boolean isTestMode = true;
    public static final int HANDLE_SHOW_MSG = 0x000003;

    private Button start;
    private TextView text;
    private SubscriberOnNextListener mGetLoginOnNext;
    private SubscriberOnNextListener mGetShiTiByPageOnNext;
    private String sid;
    private Toolbar mToolbar;
    private TextView mToolbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        text = (TextView) findViewById(R.id.text1);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbarText = (TextView) findViewById(R.id.tool_bar_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
            mToolbar.setTitle("");
            mToolbarText.setVisibility(View.VISIBLE);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"every body",0).show();
                }
            });
        }
        mGetLoginOnNext = new SubscriberOnNextListener<ResultReturn<LoginResult>>() {
            @Override
            public void onNext(ResultReturn<LoginResult> loginResultResultReturn) {
                text.setText(loginResultResultReturn.toString());
                sid = loginResultResultReturn.getResult().getCustomer().getSid();
                text.append("\n"+sid);
            }

        };
        mGetShiTiByPageOnNext = new SubscriberOnNextListener<ResultReturn<Result>>() {
            @Override
            public void onNext(ResultReturn<Result> ResultReturn) {
                text.setText(ResultReturn.toString());
            }

        };
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != sid && !sid.isEmpty()) {
                    doGetShiTi(sid,"1");
                    Log.e("TAG", "onClick: "+sid );
                }else
                {
                    doRequest();
                }
            }
        });
    }

    private void doRequest(){ 
        HttpMethods.getInstance().getLogin(new ProgressSubscriber(mGetLoginOnNext, MainActivity.this), "lhhxs","lhhxs");

}
    private void doGetShiTi(String sid,String pageid){
        HttpMethods.getInstance().getShiTibyPage(new ProgressSubscriber(mGetShiTiByPageOnNext, MainActivity.this), sid,pageid);

    }
}


