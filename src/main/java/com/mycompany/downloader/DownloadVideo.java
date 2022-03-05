package com.mycompany.downloader;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;

import java.io.File;

public class DownloadVideo {


    public static void main(String[] args) {
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo rvi = new RequestVideoInfo("n0cJSrZbjmM");
        Response<VideoInfo > re = downloader.getVideoInfo(rvi);
        VideoInfo vid = re.data();
        //System.out.println(vid.details().title());
        for (int i = 0; i < vid.videoWithAudioFormats().size(); i++)
            System.out.println(vid.videoWithAudioFormats().get(i).height());
        File outputDir;
        try {
            outputDir = new File("D:\\my_videos");
        } catch (Exception e) {
            outputDir = new File("my_videos");
        }
        var sumformat = vid.videoWithAudioFormats();
        VideoFormat format = null;
        System.out.println(sumformat.size());
        for (int i = 0; i < sumformat.size(); i++) {
            if (720 == sumformat.get(i).height()) {
                format = sumformat.get(i);
                //System.out.println(format.height());
            }

            //System.out.println(sumformat.get(i).height());
        }
        if (format == null) format = sumformat.get(sumformat.size() - 1);

        RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                .saveTo(outputDir)
                .renameTo(vid.details().title())
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int i) {
                        System.out.println(i);
                    }

                    @Override
                    public void onFinished(File file) {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }
                });
        /*Response<File> response = downloader.downloadVideoFile(request);

        File data = response.data();*/
        downloader.downloadVideoFile(request).data();


    }
}
