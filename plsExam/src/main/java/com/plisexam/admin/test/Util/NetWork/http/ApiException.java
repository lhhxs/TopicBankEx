package com.plisexam.admin.test.Util.NetWork.http;

/**
 * Created by lhhxs on 16/3/10.
 */
public class ApiException extends RuntimeException {

    public static final int WRONG_USER_PASSWORD = 14001;
    public static final int FAILED_GETSHITI= 14008;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case WRONG_USER_PASSWORD:
                message = "登陆失败，用户名或密码不正确";
                break;
            case FAILED_GETSHITI:
                message = "获取试题失败";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}

