package com.github.minuk1749.workout_tracker.domain.photo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * 업로드된 이미지 파일을 서버 폴더에 저장/삭제하는 역할만 담당.
 *
 * DB(BodyImage)와 분리해 둔 이유: "파일을 어디에/어떻게 저장하나"는
 * 나중에 로컬 폴더 → AWS S3 같은 클라우드로 바꿀 수 있다.
 * 그때 이 클래스 내부만 고치면 되고 나머지 코드는 안 건드린다.
 *
 * 저장 위치는 application.properties 의 file.upload-dir 값을 사용.
 */
@Service
public class FileStorageService {

    private final Path uploadRoot;

    // application.properties: file.upload-dir=./uploads
    public FileStorageService(@Value("${file.upload-dir:./uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    /** 앱 시작 시 업로드 폴더가 없으면 만든다. */
    @PostConstruct
    void init() {
        try {
            Files.createDirectories(uploadRoot);
        } catch (IOException e) {
            throw new IllegalStateException("업로드 폴더를 만들 수 없습니다: " + uploadRoot, e);
        }
    }

    /**
     * 파일을 저장하고, 외부에서 접근할 URL 경로를 돌려준다(예: /uploads/xxxx.jpg).
     * 파일명이 겹치지 않게 UUID 를 붙인다.
     */
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드된 파일이 비어 있습니다.");
        }
        String original = StringUtils.cleanPath(
                file.getOriginalFilename() == null ? "image" : file.getOriginalFilename());
        String ext = StringUtils.getFilenameExtension(original);
        String storedName = UUID.randomUUID() + (ext != null ? "." + ext : "");

        try {
            Path target = uploadRoot.resolve(storedName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다: " + storedName, e);
        }
        return "/uploads/" + storedName;   // WebConfig 가 이 경로를 실제 폴더에 연결해 준다
    }

    /** DB 기록 삭제 시 실제 파일도 지운다. (없어도 에러는 안 냄) */
    public void delete(String imageUrl) {
        if (imageUrl == null) return;
        String fileName = imageUrl.replaceFirst("^/uploads/", "");
        try {
            Files.deleteIfExists(uploadRoot.resolve(fileName));
        } catch (IOException ignored) {
            // 파일이 이미 없거나 지우기 실패해도 흐름은 막지 않는다.
        }
    }
}
