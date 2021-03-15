package com.plisexam.admin.test.Entry;

/**
 * Created by admin on 2018/4/8.
 */

public class SimpleQuest {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SimpleQuest{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
