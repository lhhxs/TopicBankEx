package com.plisexam.admin.test.Util.NetWork.http;


import com.plisexam.admin.test.Util.NetWork.entity.LoginResult;
import com.plisexam.admin.test.Util.NetWork.entity.ResultReturn;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lhhxs on 16/3/9.
 */
public class HttpMethods {

    public static final String BASE_URL = "http://192.168.0.103:8001/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private LoginService loginService;
    private LoginIndex loginIndex;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(getClient().build())
                //modify by lhhxs 20160317 for 对http请求结果进行统一的预处理 GosnResponseBodyConvert
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();


    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取登陆后返回值
     * @param subscriber  由调用者传过来的观察者对象
     * @param name 登录名
     * @param pass 密码
     */
    public void getLogin(Subscriber<ResultReturn> subscriber, String name, String pass){

//        movieService.getTopMovie(start, count)
//                .map(new HttpResultFunc<List<Subject>>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);

        loginService = retrofit.create(LoginService.class);
        Observable observable = loginService.getLogin(name, pass)
                .map(new HttpResultFunc<LoginResult>());

        toSubscribe(observable, subscriber);
    }
//    public void getLoginFromSid(Subscriber<ResultReturn> subscriber, String sid){
//
//        loginIndex = retrofit.create(LoginIndex.class);
//        Observable observable = loginIndex.getLogin(sid)
//                .map(new HttpResultFunc<LoginResult>());
//
//        toSubscribe(observable, subscriber);
//    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<ResultReturn<T>, ResultReturn<T>> {

        @Override
        public ResultReturn<T> call(ResultReturn<T> resultReturn) {
            if (resultReturn.getCode() == "0") {
                throw new ApiException(100);
            }
            return resultReturn;
        }
    }

    /**
     * 获取 OkHttpClient
     *
     * @return OkHttpClient
     */
    public static OkHttpClient.Builder getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(new OkHttpInterceptor());//日志


    }
}
