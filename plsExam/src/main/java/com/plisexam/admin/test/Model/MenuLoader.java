package com.plisexam.admin.test.Model;

import android.content.Context;

import com.plisexam.admin.test.Entry.MenuEntity;
import com.plisexam.admin.test.Util.BaseTool.ResJSONHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2017/4/29.
 */

public class MenuLoader extends ResJSONHandler<MenuEntity> {

    private String mAppPackage;
    public MenuLoader(Context context) {
        super(context);
        mAppPackage = mContext.getPackageName();
    }

    @Override
    protected MenuEntity convert(JSONObject var1) throws JSONException {
        return null;
    }
}
