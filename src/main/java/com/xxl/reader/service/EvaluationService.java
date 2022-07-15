package com.xxl.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按bookId查询有效短评
     * @param bookId
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 分页查询短评
     * @param page 查询第几页
     * @param rows 每一页的数据行数
     * @return 分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     * 禁用一条评论
     * @param evaluationId
     * @param reason
     * @return 禁用后的评论
     */
    public Evaluation disableEvaluation(Long evaluationId, String reason);
}
