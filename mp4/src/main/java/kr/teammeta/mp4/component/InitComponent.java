package kr.teammeta.mp4.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class InitComponent implements CommandLineRunner {

    @Value("${video.path}")
    private String videoPath;  // 외부 폴더의 절대 경로

    @Override
    public void run(String... args) throws Exception {

        Path path = Paths.get(videoPath);

        // 폴더가 존재하지 않으면 폴더를 생성
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        log.info("영상 저장 위치 = {}",videoPath);
    }
}
