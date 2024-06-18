package com.example.TeamLotto.service;

import com.example.TeamLotto.dto.TestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {
    List<TestDto> selectTestList();

    int insertTest(TestDto testDto);
}