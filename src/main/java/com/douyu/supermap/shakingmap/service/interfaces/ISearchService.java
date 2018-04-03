package com.douyu.supermap.shakingmap.service.interfaces;

/**
 * 检索接口
 */
public interface ISearchService {
    boolean index(Long contentId);

    void remove(Long contentId);
}
