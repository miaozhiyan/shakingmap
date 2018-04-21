package com.douyu.supermap.shakingmap.service.interfaces;

import com.douyu.supermap.shakingmap.common.vo.inner.ContentTemplate;
import com.douyu.supermap.shakingmap.common.vo.req.QueryContentReq;

import java.util.List;

/**
 * 检索接口
 */
public interface ISearchService {
    /**
     * 索引目标
     */
    boolean index(Long contentId);

    /**
     *移除目标
     */
    void remove(Long contentId);

    List<ContentTemplate> queryContent(QueryContentReq req);
}
