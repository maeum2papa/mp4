package kr.teammeta.mp4.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class VideoService {

    @Value("${video.path}")
    private String videoPath;  // 외부 폴더의 절대 경로

    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> streamVideoAsync(String filename) {

        // 동영상 파일 경로 설정 및 로직
        String videoPath = this.videoPath + filename;

        log.info("{} 시청",videoPath);

        File videoFile = new File(videoPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.set("Content-Disposition", "inline; filename=" + filename);

        return CompletableFuture.completedFuture(ResponseEntity.ok()
                .headers(headers)
                .contentLength(videoFile.length())
                .body(new FileSystemResource(videoFile)));
    }

}
