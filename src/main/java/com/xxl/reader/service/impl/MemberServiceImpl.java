package com.xxl.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.reader.entity.Evaluation;
import com.xxl.reader.entity.Member;
import com.xxl.reader.entity.MemberReadState;
import com.xxl.reader.mapper.EvaluationMapper;
import com.xxl.reader.mapper.MemberMapper;
import com.xxl.reader.mapper.MemberReadStateMapper;
import com.xxl.reader.service.MemberService;
import com.xxl.reader.service.exception.BussinessException;
import com.xxl.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        if(memberList.size() > 0){ // 用户已存在
            throw new BussinessException("M01", "用户名已存在");
        }
        Member newMember = new Member();
        newMember.setUsername(username);
        newMember.setNickname(nickname);
        // 密码加密
        int salt = new Random().nextInt(1000) + 1000;
        String md5 = MD5Utils.md5Digest(password, salt);
        newMember.setPassword(md5);
        newMember.setSalt(salt);
        newMember.setCreateTime(new Date());
        memberMapper.insert(newMember);
        return newMember;
    }

    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member == null){
            throw new BussinessException("M02", "用户不存在");
        }
        String md5 = MD5Utils.md5Digest(password, member.getSalt());
        if(!md5.equals(member.getPassword())){
            throw new BussinessException("M03", "密码错误");
        }
        return member;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        return memberReadStateMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        MemberReadState state = memberReadStateMapper.selectOne(queryWrapper);
        if(state == null){
            state = new MemberReadState();
            state.setMemberId(memberId);
            state.setBookId(bookId);
            state.setReadState(readState);
            state.setCreateTime(new Date());
            memberReadStateMapper.insert(state);
        } else {
            state.setReadState(readState);
            memberReadStateMapper.updateById(state);
        }
        return state;
    }

    @Override
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    @Override
    public Evaluation enjoy(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
