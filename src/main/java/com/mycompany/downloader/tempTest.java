package com.mycompany.downloader;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class tempTest {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com/playlist?list=PL786bPIlqEjRDXpAKYbzpdTaOYsWyjtCX");
        //driver.get("https://www.youtube.com/playlist?list=PLLbn8ifd399CzWYy8zcgjcwwLXu0Nl_4o");

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        /*jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
        try {
            System.out.println("here");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for (int second = 0; ; second++) {
            if (second >= 30) {
                break;
            }
            jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var x = driver.findElements(By.cssSelector("a[class = 'yt-simple-endpoint inline-block style-scope ytd-thumbnail']"));
        for (var v : x) {
            System.out.println(v.getAttribute("href"));
        }
        System.out.println(x.size());
    }
}
