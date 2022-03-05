package com.mycompany.downloader;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Ydownload {
    public static int j = 0;

    public void getInfo(JComboBox<String> q, String url) {
        if (!url.contains("playlist")) {
            if (url.contains("list")) {
                var videoID = url.substring(url.indexOf("?v=") + 3, url.indexOf("&list"));
                YoutubeDownloader downloader = new YoutubeDownloader();
                RequestVideoInfo rvi = new RequestVideoInfo(videoID);
                Response<VideoInfo> re = downloader.getVideoInfo(rvi);
                VideoInfo vid = re.data();
                q.removeAllItems();
                for (int i = 0; i < vid.videoWithAudioFormats().size(); i++) {
                    q.addItem(String.valueOf(vid.videoWithAudioFormats().get(i).height()));
                    System.out.println(vid.videoWithAudioFormats().get(i).height());
                }
            } else {
                var videoID = url.substring(url.indexOf("?v=") + 3);
                YoutubeDownloader downloader = new YoutubeDownloader();
                RequestVideoInfo rvi = new RequestVideoInfo(videoID);
                Response<VideoInfo> re = downloader.getVideoInfo(rvi);
                VideoInfo vid = re.data();
                q.removeAllItems();
                for (int i = 0; i < vid.videoWithAudioFormats().size(); i++) {
                    q.addItem(String.valueOf(vid.videoWithAudioFormats().get(i).height()));
                    System.out.println(vid.videoWithAudioFormats().get(i).height());
                }
            }
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            WebDriver driver = new ChromeDriver(options);
           /* FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            WebDriver driver = new FirefoxDriver(options);*/
            driver.get(url);
            var x = driver.findElements(By.cssSelector("a[class = 'yt-simple-endpoint inline-block style-scope ytd-thumbnail']"));
            String yy = x.get(1).getAttribute("href");
            String k = yy.substring(yy.indexOf("?v=") + 3, yy.indexOf("&list="));
            YoutubeDownloader downloader = new YoutubeDownloader();
            RequestVideoInfo rvi = new RequestVideoInfo(k);
            Response<VideoInfo> re = downloader.getVideoInfo(rvi);
            VideoInfo vid = re.data();
            q.removeAllItems();
            for (int i = 0; i < vid.videoWithAudioFormats().size(); i++) {
                q.addItem(String.valueOf(vid.videoWithAudioFormats().get(i).height()));
                //System.out.println(vid.videoWithAudioFormats().get(i).height());
            }
        }

    }

    public void downloadVideo(String url, String path, Integer quality) {
        String videoID;
        if (url.contains("list")) {
            videoID = url.substring(url.indexOf("?v=") + 3, url.indexOf("&list"));
        } else {
            videoID = url.substring(url.indexOf("?v=") + 3);
        }
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo rvi = new RequestVideoInfo(videoID);
        Response<VideoInfo> re = downloader.getVideoInfo(rvi);
        VideoInfo vid = re.data();
        File outputDir;
        try {
            outputDir = new File(path);
        } catch (Exception e) {
            outputDir = new File("my_videos");
        }
        var sumformat = vid.videoWithAudioFormats();
        VideoFormat format = null;
        for (int i = 0; i < sumformat.size(); i++) {
            if (quality.equals(sumformat.get(i).height())) {
                format = sumformat.get(i);
            }
        }

        if (format == null) {
            format = sumformat.get(sumformat.size() - 1);
            System.out.println("format null");

        }
        RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                .saveTo(outputDir)
                .renameTo(vid.details().title())
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int i) {
                        System.out.println(i);
                        j++;
                    }

                    @Override
                    public void onFinished(File file) {
                        System.out.println("finish");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("try again");

                    }
                });
        downloader.downloadVideoFile(request).data();


    }

    public void downloadPlaylist(String url, String path, Integer quality) {
        File outputDir;
        try {
            outputDir = new File(path);
        } catch (Exception e) {
            outputDir = new File("my_videos");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        /*FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        WebDriver driver = new FirefoxDriver(options);*/
        driver.get(url);
        var x = driver.findElements(By.cssSelector("a[class = 'yt-simple-endpoint inline-block style-scope ytd-thumbnail']"));
        String videoID;
        int numberOfVideo = 1;
        for (var y : x) {
            //System.out.println(y.getAttribute("href"));
            String sumURL = y.getAttribute("href");
            if (sumURL == null) continue;
            try {
                videoID = sumURL.substring(sumURL.indexOf("?v=") + 3, sumURL.indexOf("&list="));
            } catch (Exception e) {
                //System.out.println(e);
                continue;
            }

            YoutubeDownloader downloader = new YoutubeDownloader();
            RequestVideoInfo rvi = new RequestVideoInfo(videoID);
            Response<VideoInfo> re = downloader.getVideoInfo(rvi);
            VideoInfo vid = re.data();
            var sumformat = vid.videoWithAudioFormats();
            VideoFormat format = null;
            for (int i = 0; i < sumformat.size(); i++) {
                if (quality.equals(sumformat.get(i).height())) {
                    format = sumformat.get(i);
                }
            }
            if (format == null) {
                format = sumformat.get(sumformat.size() - 1);
                //System.out.println("format null");
            }
            String nameOfVideo = "" + numberOfVideo++ + " - " + vid.details().title();
            RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                    .saveTo(outputDir)
                    .renameTo(nameOfVideo)
                    .callback(new YoutubeProgressCallback<File>() {
                        @Override
                        public void onDownloading(int i) {
                            System.out.println(i);
                        }

                        @Override
                        public void onFinished(File file) {
                            System.out.println("finish");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            System.out.println("try again");

                        }
                    });
            downloader.downloadVideoFile(request).data();
        }
        driver.close();
    }
}
