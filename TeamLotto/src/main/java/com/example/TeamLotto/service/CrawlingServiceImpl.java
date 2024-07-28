package com.example.TeamLotto.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CrawlingServiceImpl implements CrawlingService{

    static final String id = "webdriver.chrome.driver";
    //static final String path = "/usr/local/bin/chromedriver";
    static final String path = "/Users/gmlwls/Downloads/chromedriver-mac-arm64/chromedriver";

    @Override
    public void selectLottoWins(){
        System.setProperty(id, path);
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        options.addArguments("--disable-popup-blocking");       // 팝업안띄움
        options.addArguments("headless");                       // 브라우저 안띄움
        options.addArguments("--disable-gpu");			// gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); // 이미지 다운 안받음

        String url = "https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645";
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 드롭다운 활용한 크롤링으로 디벨롭 필요
        WebElement productDiv1 = driver.findElement(By.xpath("//table[@class='tbl_data tbl_board_view']/tbody/tr"));
        System.out.println("당첨자 후기 크롤링 : "+"\n" +productDiv1.getText());

        // tbody 안의 tr들 각각 칼럼으로 분리
        try{
            List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                for(int i = 1; i<cells.size()-1; i++){
                    if(i==1) System.out.print("가게명 : "+cells.get(i).getText());
                    if(i==2) System.out.print("구분 : " + cells.get(i).getText());
                    if(i==3) System.out.print("주소 : " + cells.get(i).getText());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
        }
    }

    @Override
    public void selectReview() {
        System.setProperty(id, path);
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        options.addArguments("--disable-popup-blocking");       // 팝업안띄움
        options.addArguments("headless");                       // 브라우저 안띄움
        options.addArguments("--disable-gpu");			// gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); // 이미지 다운 안받음

        String url = "https://dhlottery.co.kr/gameResult.do?method=highWin";
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            driver.get("https://dhlottery.co.kr/gameResult.do?method=highWin");

            WebElement firstLi = driver.findElement(By.cssSelector(".list_pic_summ.list_winner ul li:nth-child(6)")); // 추후 날짜 기준으로 몇번째까지 반복하여 크롤링 할 것인지로 로직 수정 필요
            WebElement actionLink = firstLi.findElement(By.cssSelector(".action a"));
            String hrefValue = actionLink.getAttribute("href");

            WebElement element = driver.findElement(By.xpath("//a[contains(@href, \""+ hrefValue +"\")]"));
            element.click();

            WebElement headerTitle = driver.findElement(By.xpath("//table[@class='tbl_data tbl_board_view']/thead/tr")).findElement(By.className("subject"));
            System.out.println("title : " + headerTitle.getText()); // 제목

            WebElement tbodyContent = driver.findElement(By.xpath("//table[@class='tbl_data tbl_board_view']/tbody/tr"));
            //System.out.println("당첨자 후기 크롤링 : "+"\n" +tbodyContent.getText()); // 텍스트만 태그 없이

            // tr 요소 내의 모든 텍스트와 이미지 링크 가져오기
            List<WebElement> childElements = tbodyContent.findElements(By.xpath(".//*"));
            System.out.println("태그 포함 내용 " + "\n" + childElements.get(0).getAttribute("outerHTML")); // 전체 태그 포함 내용

            for (WebElement child : childElements) {
                // 요소의 outerHTML 가져오기
                if (child.getTagName().equals("img")) {
                    // img 태그의 src 속성 값 가져오기
                    String imgSrc = child.getAttribute("src");
                    System.out.println("이미지 링크: " + imgSrc); // 해당 페이지의 이미지 링크
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
        }
    }
}
