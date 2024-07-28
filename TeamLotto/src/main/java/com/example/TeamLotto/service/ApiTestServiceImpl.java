package com.example.TeamLotto.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ApiTestServiceImpl implements ApiTestService{

    @Override
    public void apiRequestList(int pageInfo) {

        try{
            // Request
            URL url = new URL("https://www.dhlottery.co.kr/store.do?method=sellerInfoPrintResult&searchType=2&nowPage="+pageInfo+"&kind=0&srchVal=&corpYn=N");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            // Response
            BufferedReader br;
            int resultCode = conn.getResponseCode();

            if(resultCode>=200 && resultCode<300){
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            }else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while((line=br.readLine()) != null){
                responseBuilder.append(line);
            }
            br.close();
            conn.disconnect();

            JSONParser jsonData = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonData.parse(responseBuilder.toString());
            JSONArray rowList = (JSONArray) jsonObject.get("arr");

            for(int i = 0; i<15; i++){
                JSONObject data = (JSONObject) rowList.get(i);
                String name = (String)data.get("SHOP_NM");
                String address = (String)data.get("BPLCLOCPLC1")+(String)data.get("BPLCLOCPLC2")+(String)data.get("BPLCLOCPLC3")+(String)data.get("BPLCLOCPLC4");
                Double addLat = Double.parseDouble((String) data.get("ADDR_LAT"));
                Double addLot = Double.parseDouble((String)data.get("ADDR_LOT"));
                String phoneNum = (String)data.get("TELEPHONE");

                log.info(String.format("\n 가게이름 : %s \n 주소 : %s \n 위도 : %f, 경도 : %f \n 전화번호 : %s", name, address, addLat, addLot, phoneNum));
            }

        }catch (MalformedURLException e) {
            log.info("MalformedURLExcepton 입니다.");
            throw new RuntimeException(e);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apiLottoList(int drwNo) {
        try{
            // Request
            URL url = new URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo="+drwNo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            // Response
            BufferedReader br;
            int resultCode = conn.getResponseCode();

            if(resultCode>=200 && resultCode<300){
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            }else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while((line=br.readLine()) != null){
                responseBuilder.append(line);
            }
            br.close();
            conn.disconnect();

            JSONParser jsonData = new JSONParser();
            JSONObject data = (JSONObject) jsonData.parse(responseBuilder.toString());

            Long num1 = (Long) data.get("drwtNo1");
            Long num2 = (Long) data.get("drwtNo2");
            Long num3 = (Long) data.get("drwtNo3");
            Long num4 = (Long) data.get("drwtNo4");
            Long num5 = (Long) data.get("drwtNo5");
            Long num6 = (Long) data.get("drwtNo6");
            Long winAmount = (Long)data.get("firstWinamnt"); // 당첨금
            Long firstPrzwnerCo = (Long)data.get("firstPrzwnerCo"); // 당첨 인원
            String dateString = (String) data.get("drwNoDate"); // 진행일
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);

            log.info(String.format("\n 당첨번호 : %d %d %d %d %d %d \n 당첨금 : %d \n 당첨인원 : %d \n 당첨일 : %s ", num1, num2, num3, num4, num5, num6, winAmount, firstPrzwnerCo, date));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
