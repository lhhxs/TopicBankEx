package com.plisexam.admin.test.Util.NetWork.HttpServers;

import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;
import com.plisexam.admin.test.Util.NetWork.entity.Result;
import com.plisexam.admin.test.Entry.Timu;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/16.
 */

public interface ShiTiService {
    @POST("timu/viewAllTimu")
    @FormUrlEncoded
    Observable<ResultReturn<Result<Timu>>> getShiTiByPage(@Field("sid") String sid, @Field("pageid") String pageid);


}
