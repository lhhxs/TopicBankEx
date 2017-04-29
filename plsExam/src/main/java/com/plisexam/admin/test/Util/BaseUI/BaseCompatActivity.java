package com.plisexam.admin.test.Util.BaseUI;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.plisexam.admin.test.R;

/**
 * Created by admin on 2017/4/27.
 */

public abstract class BaseCompatActivity extends AppCompatActivity {
    private boolean mIsFullScreen = false;
    private int mExitWaitTime = 2000;
    private DoubleClickExit mExitTip;

    public BaseCompatActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);



    }
    public void toggleFullScreen() {
        this.manualFullScreen(!this.mIsFullScreen);
    }

    public final void manualFullScreen(boolean isFullScreen) {
        this.mIsFullScreen = isFullScreen;
        WindowManager.LayoutParams params;
        if(isFullScreen) {
            params = this.getWindow().getAttributes();
            params.flags |= 1024;
            this.getWindow().setAttributes(params);
            this.getWindow().addFlags(512);
        } else {
            params = this.getWindow().getAttributes();
            params.flags &= -1025;
            this.getWindow().setAttributes(params);
            this.getWindow().clearFlags(512);
        }

    }

    public final void hideTitleBar() {
        this.requestWindowFeature(1);
    }

    public final void setScreenVertical() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public final void setScreenHorizontal() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public final void bindDoubleClickExit() {
        this.mExitTip = new DoubleClickExit(this);
    }

    public final void bindDoubleClickExit(int time) {
        this.mExitTip = new DoubleClickExit(this);
        this.mExitWaitTime = time;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mExitTip != null?this.mExitTip.doubleClickExit(keyCode, this.mExitWaitTime):super.onKeyDown(keyCode, event);
    }
}

