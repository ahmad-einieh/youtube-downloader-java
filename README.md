# YouTube Downloader — Java

A Java Swing desktop download experiment using `java-youtube-downloader` for video information/files and Selenium-controlled Chrome for playlist discovery.

## Requirements

- JDK 11, Maven, and a graphical desktop session.
- Network access to Maven/JitPack and the requested video service.
- For playlist flows: Chrome and a compatible ChromeDriver available to Selenium, for example through `PATH`. Selenium `4.1.2` predates automatic Selenium Manager driver setup.

Dependencies in [`pom.xml`](pom.xml) include `java-youtube-downloader` `3.0.2` and Selenium `4.1.2`.

## Build and launch

```bash
mvn compile
```

Open the Maven project in a Java IDE and run `com.mycompany.downloader.YdownloaderGUI` with its resolved dependencies on the classpath. No executable-JAR manifest or bundled-dependency packaging is configured in the POM; do not assume `java -jar` works on the ordinary Maven JAR.

## Intended workflow

Enter a video/playlist URL, select an available quality through the GUI controls, choose an output directory, and start downloading. Video handling expects standard watch URLs containing `?v=`; short links and changed URL forms are not parsed robustly.

## Source guide

- [`YdownloaderGUI.java`](src/main/java/com/mycompany/downloader/YdownloaderGUI.java): Swing interface and download actions.
- [`Ydownload.java`](src/main/java/com/mycompany/downloader/Ydownload.java): quality lookup, video downloads, and Selenium playlist scraping.
- [`DownloadVideo.java`](src/main/java/com/mycompany/downloader/DownloadVideo.java): a standalone experiment with a hardcoded video ID and Windows output path; inspect it before running.

This is a legacy experiment. YouTube page selectors and extraction behavior may have changed; available formats, errors, and browser cleanup are not handled comprehensively. Use a disposable output folder and only download material you own or have permission to download under applicable terms and law. Builds and live downloads have not been verified for this documentation refresh.
