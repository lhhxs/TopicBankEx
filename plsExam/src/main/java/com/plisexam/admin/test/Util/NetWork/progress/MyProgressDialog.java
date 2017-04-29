package com.plisexam.admin.test.Util.NetWork.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.plisexam.admin.test.R;

/**
 * Created by admin on 2017/4/16.
 */

public class MyProgressDialog extends ProgressDialog {

    public MyProgressDialog(Context context) {
        super(context,R.style.MyDialog);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

}
