package com.xxl.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
