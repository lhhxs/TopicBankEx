package com.plisexam.admin.test.Util.NetWork.entity;

import java.util.List;

/**
 * Created by admin on 2017/4/16.
 */

public class Result<T> {
    private List<T> list ;

    public void setlist(List<T> list){
        this.list = list;
    }
    public List<T> getlist(){
        return this.list;
    }

    @Override
    public String toString() {

        return "Result{" +
                "list=" + list +
                '}';
    }
}
