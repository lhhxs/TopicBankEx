package com.plisexam.admin.test.Util.BaseUI;

import android.app.Activity;
import android.os.Process;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/4/27.
 */

public class DoubleClickExit {
    private static final String DEFAULT_EXIT_TIP = "再按一次退出应用！";
    private static final int DEFAULT_EXIT_TIME = 2000;
    private Activity mContext;
    private Boolean mIsExit = Boolean.valueOf(false);
    private Boolean mHasTask = Boolean.valueOf(false);

    public DoubleClickExit(Activity context) {
        this.mContext = context;
    }

    public boolean dClickExit(int keyCode, String tip, int waitTime) {
        if(keyCode == 4) {
            if(!this.mIsExit.booleanValue()) {
                this.mIsExit = Boolean.valueOf(true);
                Toast.makeText(this.mContext, tip, 0).show();
                if(!this.mHasTask.booleanValue()) {
                    (new Timer()).schedule(new TimerTask() {
                        public void run() {
                            DoubleClickExit.this.mIsExit = Boolean.valueOf(false);
                            DoubleClickExit.this.mHasTask = Boolean.valueOf(true);
                        }
                    }, (long)waitTime);
                }
            } else {
                this.mContext.finish();
                Process.killProcess(Process.myPid());
            }
        }

        return false;
    }

    public boolean doubleClickExit(int keyCode, int waitTime) {
        return this.dClickExit(keyCode, "再按一次退出应用！", waitTime);
    }

    public boolean doubleClickExit(int keyCode) {
        return this.dClickExit(keyCode, "再按一次退出应用！", 2000);
    }
}
