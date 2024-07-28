package com.example.TeamLotto.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

@Service
public interface ApiTestService {
    void apiRequestList(int pageInfo) throws MalformedURLException;

    void apiLottoList(int drwNo);
}
