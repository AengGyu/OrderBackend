package com.aengpyo.orderservice.exception;

import com.aengpyo.orderservice.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        log.info("handleCommonException {} {}", e.getMessage(), e.getClass());
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException ex) {
        log.info("handleNotFoundException {} {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("해당 URL을 찾을 수 없습니다."));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequestBody(Exception ex) {
        log.info("handleInvalidRequestBody {} {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("요청이 올바르지 않습니다."));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedMethod(HttpRequestMethodNotSupportedException ex) {
        log.info("handleNotSupportedMethod {} {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse("요청 메소드가 올바르지 않습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.info("handleGeneralException {} {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(500).body(new ErrorResponse("서버 내부 오류"));
    }
}
