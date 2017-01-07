package com.rhg.qf.update.interfacedef;


import com.rhg.qf.update.callback.UpdateCheckCallback;

/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>更新检查接口</p>
 */
public interface UpdateChecker {
    void check(UpdateCheckCallback callback);
}
