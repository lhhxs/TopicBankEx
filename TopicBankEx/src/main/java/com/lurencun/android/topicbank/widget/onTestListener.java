package com.lurencun.android.topicbank.widget;

public interface onTestListener {

    /**
     *
     * @param uniqueIdentifier
     *            唯一标识
     * @param strName
     * @param strHigh
     */
    public abstract void onTestListener(int uniqueIdentifier,String strName, String strHigh);
    void onCardItemListener(int position);
    void onCardButtonListener();
}
