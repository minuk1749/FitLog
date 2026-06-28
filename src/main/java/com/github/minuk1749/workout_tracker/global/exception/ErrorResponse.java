package com.github.minuk1749.workout_tracker.global.exception;

import java.time.LocalDateTime;

/**
 * 에러가 났을 때 클라이언트에 내려줄 "일관된" 에러 응답 모양.
 * 모든 에러가 같은 형식으로 나가면 프론트가 처리하기 쉽다(명세서: 일관된 에러 응답).
 *
 * 예시 JSON:
 * { "status": 404, "message": "운동 기록을 찾을 수 없습니다. id=5", "timestamp": "..." }
 */
public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message, LocalDateTime.now());
    }
}
