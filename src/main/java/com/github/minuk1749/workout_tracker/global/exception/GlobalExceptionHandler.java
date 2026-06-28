package com.github.minuk1749.workout_tracker.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통 예외 핸들러 (명세서: 공통 예외 핸들러로 일관된 에러 응답).
 *
 * @RestControllerAdvice = 모든 컨트롤러에서 발생한 예외를 여기서 가로채 처리한다.
 * 컨트롤러/서비스 코드 안에서 try-catch 를 도배할 필요가 없어진다.
 *
 * 이 클래스는 global 패키지에 한 번만 만들어 두면 모든 도메인(운동·인바디·눈바디)이 공유한다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) 데이터를 못 찾았을 때 → 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 2) @Valid 검증 실패 시 → 400 (어떤 필드가 왜 틀렸는지 메시지로)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), message));
    }

    // 3) 잘못된 인자(예: 빈 파일 업로드) → 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 4) 그 밖에 예상 못 한 모든 에러 → 500 (마지막 안전망)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "서버 오류가 발생했습니다."));
    }
}
