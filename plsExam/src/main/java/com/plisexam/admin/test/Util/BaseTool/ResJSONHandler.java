package com.plisexam.admin.test.Util.BaseTool;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/29.
 */

public abstract class ResJSONHandler<T> extends HasContext {

    public ResJSONHandler(Context context) {
        super(context);
    }
    public List<T> load(int resId) {
        String[] data = this.mContext.getResources().getStringArray(resId);
        ArrayList result = new ArrayList();

        for(int i = 0; i < data.length; ++i) {
            JSONTokener jsonParser = new JSONTokener(data[i]);

            try {
                JSONObject e = (JSONObject)jsonParser.nextValue();
                result.add(this.convert(e));
            } catch (Exception var7) {
                Log.e("ResLoader", " ===> 解释JSON发生异常：" + var7.getMessage());
            }
        }

        return result;
    }
    protected abstract T convert(JSONObject var1) throws JSONException;
}
