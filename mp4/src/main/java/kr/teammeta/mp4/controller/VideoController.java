package kr.teammeta.mp4.controller;


import jakarta.servlet.http.HttpServletRequest;
import kr.teammeta.mp4.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{filename}")
    public CompletableFuture<ResponseEntity<FileSystemResource>> streamVideo(@PathVariable String filename, HttpServletRequest request) {

        String referer = request.getHeader("referer");

        if(referer==null){
            log.error("referer가 없는 접근 입니다.");
            throw new NullPointerException();
        }

        log.info("referer : {}",referer);

        if(referer.equals("https://v1.collaborland.kr/")) {
            log.error("잘못된 접근 = {}",referer);
            throw new RuntimeException();
        }

        if(!referer.contains("collaborland.kr")){
            log.error("잘못된 접근 = {}",referer);
            throw new RuntimeException();
        }

        return videoService.streamVideoAsync(filename);
    }

}
