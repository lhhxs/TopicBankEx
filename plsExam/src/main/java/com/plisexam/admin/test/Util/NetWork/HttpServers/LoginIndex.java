package com.plisexam.admin.test.Util.NetWork.HttpServers;


import com.plisexam.admin.test.C.C;
import com.plisexam.admin.test.Util.NetWork.entity.LoginResult;
import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 2017/2/21.
 */

public interface LoginIndex {
    @POST(C.api.index)
    @FormUrlEncoded
    Call<ResultReturn<LoginResult>> getLogin(
            @Field("sid") String sid);
}
