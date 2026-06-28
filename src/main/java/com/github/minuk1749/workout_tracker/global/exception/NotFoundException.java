package com.github.minuk1749.workout_tracker.global.exception;

/**
 * "그런 데이터 없음"을 의미하는 우리만의 예외.
 * 예: 존재하지 않는 운동 기록 id 로 조회/수정/삭제할 때 던진다.
 *
 * RuntimeException 을 상속하면 try-catch 를 강제하지 않아 코드가 깔끔해진다.
 * 실제 HTTP 404 응답으로 바꿔주는 건 GlobalExceptionHandler 가 담당한다.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
