package com.xxl.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.reader.entity.Book;
import com.xxl.reader.entity.Evaluation;
import com.xxl.reader.entity.Member;
import com.xxl.reader.mapper.BookMapper;
import com.xxl.reader.mapper.EvaluationMapper;
import com.xxl.reader.mapper.MemberMapper;
import com.xxl.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 按bookId查询有效短评
     * @param bookId
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for(Evaluation eva : evaluationList){
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            Book book = bookMapper.selectById(eva.getBookId());
            eva.setBook(book);
        }
        return evaluationList;
    }

    @Override
    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<>(page, rows);

        // 执行单表的分页查询
        Page<Evaluation> evaluationPage = evaluationMapper.selectPage(p, new QueryWrapper<>());
        // 得到包含book和member属性的list
        List<Evaluation> records = evaluationPage.getRecords();
        for(Evaluation eva : records){
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            Book book = bookMapper.selectById(eva.getBookId());
            eva.setBook(book);
        }
        evaluationPage.setRecords(records);
        return evaluationPage;
    }

    @Override
    public Evaluation disableEvaluation(Long evaluationId, String reason) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setState("disable");
        evaluation.setDisableReason(reason);
        evaluation.setDisableTime(new Date());
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }


}
