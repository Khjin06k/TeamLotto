package com.example.TeamLotto.service;

import com.example.TeamLotto.dto.TestDto;
import com.example.TeamLotto.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TestServiceImpl implements TestService{
    private final SqlSession sqlSession;

    public TestServiceImpl(SqlSession ss){
        this.sqlSession = ss;
    }

    // list 조회
    @Transactional(readOnly = true)
    public List<TestDto> selectTestList() {
        TestMapper tm = sqlSession.getMapper(TestMapper.class);
        return tm.selectTestList();
    }

    // 값 생성 및 결과값 반환
    @Transactional
    public int insertTest(TestDto testDto) {
        TestMapper tm = sqlSession.getMapper(TestMapper.class);
        return tm.insertTest(testDto);
    }
}
