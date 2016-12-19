package com.wlx.mobiletv.impl;

/**
 * 作者：LucianWang
 * 日期：2016/12/14 10:39
 * 邮箱：wlx3079@163.com
 * 描述：倒计时监听
 */

public interface CountDownListener {
    void onFinish();
    void onRun();
    void onRunning();
}
