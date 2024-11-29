package com.example.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class VideoController {
    // 비디오 파일이 저장된 폴더 경로
    private static final String VIDEO_DIRECTORY = "/home/gogi/Desktop/connected/demo/src/main/resources/videos/";

    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(
            @PathVariable String filename,
            @RequestHeader(value = "Range", required = false) String rangeHeader,
            HttpServletResponse response) throws IOException {

        System.out.println(filename + " is requested!!!");

        // 파일 경로 생성
        File videoFile = new File(VIDEO_DIRECTORY + filename);

        // 파일이 존재하지 않을 경우 404 반환
        if (!videoFile.exists()) {
            System.out.println("file does not exist!!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 파일 리소스 생성
        Resource videoResource = new FileSystemResource(videoFile);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "video/mp4"); // 동영상 파일 MIME 타입
        headers.add("Accept-Ranges", "bytes");

        // 범위 요청 처리
        if (rangeHeader != null) {
            long fileLength = videoFile.length();
            String[] ranges = rangeHeader.replace("bytes=", "").split("-");
            long start = Long.parseLong(ranges[0]);
            long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileLength - 1;

            headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

            // 비디오 파일 스트리밍
            try (FileInputStream inputStream = new FileInputStream(videoFile);
                 OutputStream out = response.getOutputStream()) {

                inputStream.skip(start);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    if (Thread.interrupted()) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .build();
        }

        System.out.println("file returned!!!");

        // 전체 파일 반환 (파일 스트리밍)
        try (FileInputStream inputStream = new FileInputStream(videoFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (Thread.interrupted()) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }
}
