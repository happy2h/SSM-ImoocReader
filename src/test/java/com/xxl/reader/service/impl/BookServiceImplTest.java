package com.xxl.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxl.reader.entity.Book;
import com.xxl.reader.service.BookService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest extends TestCase {
    @Resource
    private BookService bookService;

    @Test
    public void paging(){
        IPage<Book> bookIPage = bookService.paging(2L, "quantity",2, 10);
        List<Book> records = bookIPage.getRecords(); // 具体数据
        for(Book b : records){
            System.out.println(b.getBookId() + " " + b.getBookName());
        }
        System.out.println("总页数" + bookIPage.getPages());
        System.out.println("总记录" + bookIPage.getTotal());
    }
}