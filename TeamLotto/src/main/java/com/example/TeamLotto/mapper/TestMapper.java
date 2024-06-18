package com.example.TeamLotto.mapper;

import com.example.TeamLotto.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDto> selectTestList();

    int insertTest(TestDto testDto);
}
