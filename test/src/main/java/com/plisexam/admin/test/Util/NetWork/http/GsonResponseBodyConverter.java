package com.plisexam.admin.test.Util.NetWork.http;

import android.util.Log;

import com.google.gson.Gson;
import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lhhxs on 2016/3/17.
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
            Log.d("Network", "response>>" + response);
            //Resultreturn 只解析result字段
            ResultReturn resultReturn = gson.fromJson(response, ResultReturn.class);
            //
            if (resultReturn.getCode() == "0") {
                throw new ApiException(100);
            }
            return gson.fromJson(response, type);


    }
}
