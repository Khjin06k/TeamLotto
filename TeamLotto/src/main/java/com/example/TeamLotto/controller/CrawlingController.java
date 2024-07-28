package com.example.TeamLotto.controller;

import com.example.TeamLotto.dto.LottoWinsDto;
import com.example.TeamLotto.service.CrawlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/crawling")
public class CrawlingController {

    public final CrawlingService crawlingService;

    public CrawlingController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @GetMapping("/lottoWins")
    public void lottoWinsList(){
        crawlingService.selectLottoWins();
    }

    @GetMapping("/review")
    public void reviewList(){
        crawlingService.selectReview();
    }
}
