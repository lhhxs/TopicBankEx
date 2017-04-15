package com.plisexam.admin.test.Util.NetWork.entity;

/**
 * {
 *"code":"10000",
 *"message":"Login ok",
 *"result":{
 *"Customer":{
 *"id":1,
 *"name":"james",
 *"sign":"Happying",
 *"face":"1",
 *"blogcount":3,
 *"fanscount":0,
 *"uptime":"2011-11-29 18:11:24",
 *"sid":"6pui7p2mr8pdtsrkae1ggnsjtoo2c61a"
 }
 }
 }
 * Created by admin on 2017/2/14.
 */

public class Customer {
    private int id;

    private String name;

    private String sign;

    private String face;

    private int blogcount;

    private int fanscount;

    private String uptime;

    private String sid;

    private String faceurl;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setSign(String sign){
        this.sign = sign;
    }
    public String getSign(){
        return this.sign;
    }
    public void setFace(String face){
        this.face = face;
    }
    public String getFace(){
        return this.face;
    }
    public void setBlogcount(int blogcount){
        this.blogcount = blogcount;
    }
    public int getBlogcount(){
        return this.blogcount;
    }
    public void setFanscount(int fanscount){
        this.fanscount = fanscount;
    }
    public int getFanscount(){
        return this.fanscount;
    }
    public void setUptime(String uptime){
        this.uptime = uptime;
    }
    public String getUptime(){
        return this.uptime;
    }
    public void setSid(String sid){
        this.sid = sid;
    }
    public String getSid(){
        return this.sid;
    }

    @Override
    public String toString() {
        return "\nCustomer.id = " + id
                +"\nCustomer.name = "+name
                +"\nCustomer.sign = "+sign
                +"\nCustomer.face = "+face
                +"\nCustomer.blogcount = "+blogcount
                +"\nCustomer.fanscount = "+fanscount
                +"\nCustomer.uptime = "+uptime
                +"\nCustomer.faceurl = "+faceurl
                +"\nCustomer.sid = "+sid+ " | ";
    }

    public String getFaceurl() {
        return faceurl;
    }

    public void setFaceurl(String faceurl) {
        this.faceurl = faceurl;
    }
}
