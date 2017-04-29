package com.plisexam.admin.test.View;


import android.os.Bundle;

import com.plisexam.admin.test.Util.BaseUI.BaseCompatActivity;

/**
 * Created by admin on 2017/4/29.
 */

public class MenuActivity extends BaseCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hideTitleBar();
        this.bindDoubleClickExit();
    }
}
