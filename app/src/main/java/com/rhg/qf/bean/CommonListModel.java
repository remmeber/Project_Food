package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:主页店铺推荐列表模型
 * author：remember
 * time：2016/5/28 16:38
 * email：1013773046@qq.com
 */
public class CommonListModel<T> implements IBaseModel<T> {
    private List<T> entity;

    public void setRecommendShopBeanEntity(
            List<T> entity) {
        this.entity = entity;

    }

    @Override
    public List<T> getEntity() {
        return entity;
    }
}
