package kr.teammeta.mp4.controller;


import kr.teammeta.mp4.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{filename}")
    public CompletableFuture<ResponseEntity<FileSystemResource>> streamVideo(@PathVariable String filename) {
        return videoService.streamVideoAsync(filename);
    }

}
