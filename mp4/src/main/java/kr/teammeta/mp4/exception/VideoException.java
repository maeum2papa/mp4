package kr.teammeta.mp4.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;


@Slf4j
@RestControllerAdvice
public class VideoException {

    @ExceptionHandler({FileNotFoundException.class})
    public void VideoException(FileNotFoundException e){

        String message = e.getMessage();
        message = message.replace("file [", "[");
        message = message.replace("cannot be resolved in the file system for checking its content length", "");

        log.error("{}를 찾을 수 없습니다.",message);
    }


    @ExceptionHandler({RuntimeException.class})
    public void VideoException(RuntimeException e){
        log.error("허용되지 않은 접근 입니다.");
    }
}
