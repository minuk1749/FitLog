package com.github.minuk1749.workout_tracker.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * 업로드한 이미지를 브라우저에서 볼 수 있게 연결해 주는 설정.
 *
 * 예: DB 에 저장된 imageUrl 이 "/uploads/abc.jpg" 라면,
 *     http://localhost:8080/uploads/abc.jpg 로 접근하면
 *     실제 서버 폴더(file.upload-dir)의 abc.jpg 파일을 내려준다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String uploadDir;

    public WebConfig(@Value("${file.upload-dir:./uploads}") String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = Paths.get(uploadDir).toAbsolutePath().normalize().toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);   // 예: file:/.../FitLog/uploads/
    }
}
