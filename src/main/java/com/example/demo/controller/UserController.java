package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestParam String username, @RequestParam String password) {
    	
    	Map<String, String> response = new HashMap<>();
    	
    	if(userService.DuplicatUser(username)) {
    		 response.put("message", "이미 존재하는 사용자 이름입니다.");
            return ResponseEntity.status(400).body(response); // 400 bad request
    	}
    	
        userService.register(username, password);
        
        response.put("message", "회원가입 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Optional<User> user = userService.login(username, password);
        Map<String, String> response = new HashMap<>();
        
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            response.put("message", "로그인 성공");
            response.put("status", "success");
            return ResponseEntity.ok(response); // 로그인 성공 시 JSON 응답
        } else {
            response.put("message", "로그인 실패: 잘못된 사용자명 또는 비밀번호");
            response.put("status", "failure");
            return ResponseEntity.status(401).body(response); // 로그인 실패 시 401 응답
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }
    
    //private final String VIDEO_DIR = "/home/gogi/Desktop/connected/demo/src/main/resources/videos"; // 동영상이 저장된 디렉토리

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
