package com.plisexam.admin.test.View;


import android.os.Bundle;
import android.widget.GridView;

import com.plisexam.admin.test.R;
import com.plisexam.admin.test.Util.BaseUI.BaseCompatActivity;

/**
 * Created by admin on 2017/4/29.
 */

public class MenuActivity extends BaseCompatActivity {
    private GridView mGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hideTitleBar();
        this.bindDoubleClickExit();
        setContentView(R.layout.menu_activity);
        bind();
    }

    private void bind() {
        mGridView = (GridView) findViewById(R.id.menu_grid);
    }
}
