package com.plisexam.admin.test.Util.NetWork.entity;

public class ResultReturn<T> {
    private  int code;

    private String message;

    private T result;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setResult(T result){
        this.result = result;
    }
    public T getResult(){
        return this.result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n code=" + code + "\n message=" + message );
        if (null != result) {
            sb.append("\n result:" + result.toString());
        }
        return sb.toString();
    }
}
