package com.example.TeamLotto.controller;

import com.example.TeamLotto.service.ApiTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;

@Slf4j
@Controller
@RequestMapping("/api")
public class ApiTestController {

    public final ApiTestService apiTestService;

    public ApiTestController(ApiTestService apiTestService) {
        this.apiTestService = apiTestService;
    }

    @GetMapping("/storeInfo")
    public void requestStoreInfo() throws MalformedURLException {
        // 전체 가게 정보 크롤링 진행 (561페이지까지, 페이지 당 15개의 가게)
        apiTestService.apiRequestList(1);
    }

    @GetMapping("/lottoInfo")
    public void requestLottoInfo(){
        // 정보 크롤링 시 >> 반복문으로 현재 회차까지 Get 하도록 설정
        // 이후 로또 api 업데이트 일시에 맞춰서 스케줄러 설정
        apiTestService.apiLottoList(100);
    }
}
