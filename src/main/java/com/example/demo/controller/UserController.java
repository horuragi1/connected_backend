package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
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
}
