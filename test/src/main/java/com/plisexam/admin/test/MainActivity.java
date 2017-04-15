package com.plisexam.admin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.plisexam.admin.test.Util.NetWork.entity.LoginResult;
import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;
import com.plisexam.admin.test.Util.NetWork.http.HttpMethods;
import com.plisexam.admin.test.Util.NetWork.subscribers.ProgressSubscriber;
import com.plisexam.admin.test.Util.NetWork.subscribers.SubscriberOnNextListener;

public class MainActivity extends AppCompatActivity {
    /** 外网测试时，需要修改对应的服务器ip和端口 （内网和外网模式的切换开关。） */
    public static final boolean isPoliceNet = false;
    /** 测试数据和真实数据的切换开关。 */
    public static final boolean isTestMode = true;
    public static final int HANDLE_SHOW_MSG = 0x000003;

    private Button start;
    private TextView text;
    private SubscriberOnNextListener mGetLoginOnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        text = (TextView) findViewById(R.id.text1);
        mGetLoginOnNext = new SubscriberOnNextListener<ResultReturn<LoginResult>>() {
            @Override
            public void onNext(ResultReturn<LoginResult> loginResultResultReturn) {
                text.setText(loginResultResultReturn.toString());
            }

        };
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doRequest();
            }
        });
    }

    private void doRequest(){
        HttpMethods.getInstance().getLogin(new ProgressSubscriber(mGetLoginOnNext, MainActivity.this), "lhhxs","lhhxs");

    }
}


