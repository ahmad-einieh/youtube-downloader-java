package com.mycompany.downloader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class tempTest {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com/playlist?list=PLLbn8ifd399AOK8z8UYrdx9YheUl9SUOw");
        var x = driver.findElements(By.cssSelector("a[class = 'yt-simple-endpoint inline-block style-scope ytd-thumbnail']"));
    }
}
