package com.xxl.reader.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.reader.entity.Member;
import com.xxl.reader.mapper.TestMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestServiceTest extends TestCase {

    @Resource
    TestService testService;
    @Resource
    private TestMapper testMapper;
    @Resource MemberService memberService;
    @Test
    public void testBatchImport() {
        com.xxl.reader.entity.Test test = new com.xxl.reader.entity.Test();
        test.setContent("mp测试");
        testMapper.insert(test);
    }

    @Test
    public void testUpdate(){
        com.xxl.reader.entity.Test t = testMapper.selectById(32);
        t.setContent("new mp");
        testMapper.updateById(t);
    }

    @Test
    public void testDelete(){
        testMapper.deleteById(32);
    }

    @Test
    public void testSelect(){
        QueryWrapper<com.xxl.reader.entity.Test> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 33);
        List<com.xxl.reader.entity.Test> list = testMapper.selectList(queryWrapper);
        System.out.println(list);
    }

    @Test
    public void testCreateMember(){
        Member member = memberService.createMember("imooc_1000", "123456", "测试");
        System.out.println(member.getSalt());
    }
}