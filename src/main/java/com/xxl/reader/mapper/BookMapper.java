package com.xxl.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {
    /**
     * 更新图书评分/评价数量
     */
    public void updateEvaluation();
}
