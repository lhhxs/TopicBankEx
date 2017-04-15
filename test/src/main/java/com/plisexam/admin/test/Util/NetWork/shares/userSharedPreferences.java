package com.plisexam.admin.test.Util.NetWork.shares;

import android.content.Context;
import android.content.SharedPreferences;

import com.plisexam.admin.test.C.C;
import com.plisexam.admin.test.Util.NetWork.entity.Customer;


/**
 * Created by admin on 2017/2/21.
 * 用户信息存储本地
 */

public class userSharedPreferences {
    private SharedPreferences mSharedPreferences;
    private long loginTime;
    private long count;
    public userSharedPreferences(Context context) {
        super();
        mSharedPreferences = context.getSharedPreferences(C.dir.userInfofileName, Context.MODE_PRIVATE);
        count = mSharedPreferences.getLong("LoginCount",0);
        loginTime = mSharedPreferences.getLong("LoginTime",0);

    }
    public boolean saveLoginInfo(Customer customer){
       // Customer preCustomer = getLoginInfo();//对数据进行编辑

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong("LoginCount", ++count);            //登录状态
        editor.putLong("LoginTime", System.currentTimeMillis()); //登录时间
        editor.putInt("Id",customer.getId());
        editor.putString("UserName", customer.getName());              //用户名
        editor.putString("Sign",customer.getSign());                //用户状态
        editor.putString("Face",customer.getFace());//用户头像
        editor.putInt("BlogCount",customer.getBlogcount()); //备用字段
        editor.putInt("FansCount",customer.getFanscount()); //备用字段
        editor.putString("UpTime",customer.getUptime());//用户信息更新时间
        editor.putString("Sid",customer.getSid());//用户的session
        editor.putString("FaceUrl",customer.getFaceurl());
        return editor.commit();
    }
    public Customer getLoginUser(Customer customer){

        customer.setId(mSharedPreferences.getInt("Id",customer.getId()));
        customer.setName(mSharedPreferences.getString("UserName", customer.getName()));              //用户名
        customer.setSign(mSharedPreferences.getString("Sign",customer.getSign()));                //用户状态
        customer.setFace(mSharedPreferences.getString("Face",customer.getFace()));//用户头像
        customer.setBlogcount(mSharedPreferences.getInt("BlogCount",customer.getBlogcount()));//备用字段
        customer.setFanscount(mSharedPreferences.getInt("FansCount",customer.getFanscount())); //备用字段
        customer.setUptime(mSharedPreferences.getString("UpTime",customer.getUptime()));//用户信息更新时间
        customer.setSid(mSharedPreferences.getString("Sid",customer.getSid()));//用户的session
        customer.setFaceurl(mSharedPreferences.getString("FaceUrl",customer.getFaceurl()));//用户的session
        return customer;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
