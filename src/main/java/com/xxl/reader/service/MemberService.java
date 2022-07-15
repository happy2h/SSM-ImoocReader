package com.xxl.reader.service;

import com.xxl.reader.entity.Evaluation;
import com.xxl.reader.entity.Member;
import com.xxl.reader.entity.MemberReadState;

public interface MemberService {
    /**
     * 会员注册
     * @param username
     * @param password
     * @param nickname
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname);

    /**
     * 检查用户登录
     * @param username
     * @param password
     * @return
     */
    public Member checkLogin(String username, String password);

    /**
     * 获取阅读的状态
     * @param memberId
     * @param bookId
     * @return 阅读状态对象(想看,看过)
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);

    /**
     * 发布新的短评
     * @param memberId
     * @param bookId
     * @param score
     * @param content
     * @return
     */
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content);

    /**
     * 点评点赞
     * @param evaluationId
     * @return
     */
    public Evaluation enjoy(Long evaluationId);
}
