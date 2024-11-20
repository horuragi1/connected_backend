package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.io.File;
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
        userService.register(username, password);
        Map<String, String> response = new HashMap<>();
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
    
    private final String VIDEO_DIR = "/home/gogi/Desktop/connected/demo/src/main/resources/videos"; // 동영상이 저장된 디렉토리

    @GetMapping("/videos/{videoName}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String videoName) throws Exception {
    	
    	System.out.println("videoname is " + videoName);
    	
        // 동영상 파일 경로 설정
        Path videoPath = Paths.get(VIDEO_DIR, videoName);
        File videoFile = videoPath.toFile();
        
        System.out.println(videoPath);

        // 파일이 존재하는지 확인
        if (!videoFile.exists()) {
        	System.out.println("file not exists!\n");
            return ResponseEntity.notFound().build();
        }
        

        // 파일의 MIME 타입을 확인
        String mimeType = Files.probeContentType(videoPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        
        System.out.println("streaming start!!!\n");

        // 동영상을 스트리밍하기 위해 HTTP 응답을 구성
        Resource videoResource = new FileSystemResource(videoFile);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(mimeType)) // MIME 타입 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + videoName + "\"") // 파일 이름 설정
                .body(videoResource);
    }
}
