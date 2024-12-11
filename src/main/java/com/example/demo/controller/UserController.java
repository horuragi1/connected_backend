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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestParam("username") String username, @RequestParam("password") String password) {
        logger.info("회원가입 요청 - username: {}, password: {}", username, password);
    	Map<String, String> response = new HashMap<>();

    	if(userService.DuplicatUser(username)) {
    		 response.put("message", "이미 존재하는 사용자 이름입니다.");
            return ResponseEntity.status(400).body(response); // 400 bad request
    	}
    	
    	LocalDate userbirth = LocalDate.now();
    	
        userService.register(username, password, userbirth);
        
        response.put("message", "회원가입 성공");
        return ResponseEntity.ok(response); // JSON 응답
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Optional<User> user = userService.login(username, password);
        Map<String, String> response = new HashMap<>();
        
        if (user.isPresent()) {
            User loggedInUser = user.get();

            // 사용자 생일 가져오기 (생일은 LocalDate로 저장되어 있다고 가정)
            //LocalDate birthDate = loggedInUser.getUserbirth();
            LocalDate today = LocalDate.now();

            // 나이 계산
            //int age = userService.calculateAge(birthDate, today);

            // 나이 업데이트 (User 객체에 나이 필드가 있다고 가정)
            //loggedInUser.setUserage(loggedInUser.getUserage());

            // 사용자 정보 업데이트 (서비스를 통해 DB에 저장)
            userService.updateUser(loggedInUser);

            session.setAttribute("userId", user.get().getId());
            response.put("message", "로그인 성공");
            response.put("status", "success");
            response.put("userId", String.valueOf(user.get().getId()));
            response.put("username", user.get().getUsername());
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
    
    
    
}
