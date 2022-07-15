package com.xxl.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxl.reader.entity.Book;

public interface BookService {
    /**
     * 分页查询图书
     * @param categoryId 分类编号
     * @param order 排序规则
     * @param page 查询第几页
     * @param rows 每一页的数据行数
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows);

    /**
     * 根据id查询book
     * @param id
     * @return book对象
     */
    public Book selectById(Long id);

    /**
     * 更新图书评分和评价数量
     */
    public void updateEvaluation();

    /**
     * 创建一本图书
     * @param book
     * @return
     */
    public Book createBook(Book book);

    /**
     * 更新图书
     * @param book
     * @return 更新后数据
     */
    public Book updateBook(Book book);

    /**
     * 删除对应图书及相关数据
     * @param bookId
     */
    public void deleteBook(Long bookId);
}
