package com.xxl.reader.service.impl;

import com.xxl.reader.entity.Category;
import com.xxl.reader.service.CategoryService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CategoryServiceImplTest extends TestCase {

    @Resource
    private CategoryService categoryService;
    @Test
    public void testSelectAll() {
        List<Category> all = categoryService.selectAll();
        System.out.println(all);
    }
}