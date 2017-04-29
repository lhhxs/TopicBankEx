package com.plisexam.admin.test.Entry;

import android.graphics.Bitmap;

/**
 * Created by admin on 2017/4/29.
 */

public class MenuEntity {
    private Class<?> nextUI;
    private Bitmap icon;
    private int value;
    private String title;

    public Class<?> getNextUI() {
        return nextUI;
    }

    public void setNextUI(Class<?> nextUI) {
        this.nextUI = nextUI;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
