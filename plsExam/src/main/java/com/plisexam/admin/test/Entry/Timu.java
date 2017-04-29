package com.plisexam.admin.test.Entry;

import android.util.Base64;

import org.json.JSONException;

import static com.plisexam.admin.test.C.C.getJsonToStringArray;

/**
 * Created by admin on 2017/4/16.
 */

public class Timu {
    private int id;

    private String title;

    private int correct;

    private String answers;

    private String uptime;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setCorrect(int correct){
        this.correct = correct;
    }
    public int getCorrect(){
        return this.correct;
    }
    public void setAnswers(String answers){
        this.answers = answers;
    }
    public String getAnswers(){
        return this.answers;
    }
    public void setUptime(String uptime){
        this.uptime = uptime;
    }
    public String getUptime(){
        return this.uptime;
    }

    @Override
    public String toString() {
        String[] answer = null;
        String jsonAnswers = new String(Base64.decode(answers.getBytes(), Base64.DEFAULT));
        String result;
        result = "\nTimu{" +
                "\nid=" + id +
                "\n, title='" + title + '\'' +
                "\n, correct=" + correct +
                "\n, uptime='" + uptime + '\'';
        try {
            answer = getJsonToStringArray(jsonAnswers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (null==answer||answer.length<=0)
        {
            return result+"\n, answers='" + answers + '\'' +
                    '}';
        }
        for (String row:answer
                ) {
            result = result+"\n, answers='"+row+ '\'';

        }
        return result +
                '}';
    }
}
