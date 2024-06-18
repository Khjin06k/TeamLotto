package com.example.TeamLotto.controller;

import com.example.TeamLotto.dto.TestDto;
import com.example.TeamLotto.service.TestService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/test")
@Controller
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    // list 출력
    @GetMapping("testList")
    public ResponseEntity<List<TestDto>> selectTestList(){
        return new ResponseEntity<>(testService.selectTestList(), HttpStatus.OK);
    }

    // 값 생성
    @PostMapping("insertTest")
    public ResponseEntity<Integer> insertTest(@Valid @RequestBody TestDto testDto){
        return new ResponseEntity<>(testService.insertTest(testDto), HttpStatus.CREATED);
    }
}
