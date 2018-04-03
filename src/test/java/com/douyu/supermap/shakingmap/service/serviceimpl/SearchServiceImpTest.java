package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.superman.shakingmap.parent.ShakingmapApplicationTests;
import com.douyu.supermap.shakingmap.common.entity.Content;
import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.vo.inner.ContentTemplate;
import com.douyu.supermap.shakingmap.dao.ContentRepository;
import com.douyu.supermap.shakingmap.dao.UserRepository;
import com.douyu.supermap.shakingmap.service.interfaces.ISearchService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SearchServiceImpTest extends ShakingmapApplicationTests{
    @Autowired
    private ISearchService searchService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Test
    public void testIndex(){
        Long contentId = 4L;
        boolean b = searchService.index(contentId);
        Assert.assertTrue(b);
    }
}