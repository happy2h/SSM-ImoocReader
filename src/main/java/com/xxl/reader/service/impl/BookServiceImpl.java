package com.xxl.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.reader.entity.Book;
import com.xxl.reader.entity.Evaluation;
import com.xxl.reader.entity.MemberReadState;
import com.xxl.reader.mapper.BookMapper;
import com.xxl.reader.mapper.EvaluationMapper;
import com.xxl.reader.mapper.MemberReadStateMapper;
import com.xxl.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("BookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {

    /**
     * 分页查询图书
     * @param categoryId 分类编号
     * @param order 排序规则
     * @param page 查询第几页
     * @param rows 每一页的数据行数
     * @return 分页对象
     */

    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if(categoryId != null && categoryId != -1){
            queryWrapper.eq("category_id", categoryId);
        }
        if(order != null){
            if(order.equals("quantity")){
                queryWrapper.orderByDesc("evaluation_quantity");
            } else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        return bookMapper.selectPage(p, queryWrapper);
    }

    /**
     * 根据ID查询
     */
    @Override
    public Book selectById(Long id) {
        return bookMapper.selectById(id);
    }

    /**
     * 更新图书评分和评价数量
     */
    @Override
    @Transactional
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book; // 返回比传入的多了一个图书编号
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<>();
        mrsQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);
        QueryWrapper<Evaluation> evaQueryWrapper = new QueryWrapper<>();
        evaQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaQueryWrapper);
    }
}
