package com.plisexam.admin.test.Util.NetWork.HttpServers;

import com.plisexam.admin.test.Util.NetWork.entity.LoginResult;
import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("index/login")
    @FormUrlEncoded
    Call<ResultReturn<LoginResult>> LoginApi(
            @Field("name") String name,
            @Field("pass") String pass);
}
