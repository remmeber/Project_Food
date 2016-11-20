package com.rhg.qf.impl;

/**
 * 作者：rememberon 2016/6/17
 * 邮箱：1013773046@qq.com
 */
public interface ShareListener {
    void shareSuccess(String message);

    void shareFailed(String message, String content);

    void shareCancel(String message);
}
