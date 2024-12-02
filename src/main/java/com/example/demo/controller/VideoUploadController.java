package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.VideoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
@RequiredArgsConstructor
public class VideoUploadController {
	
	@GetMapping("/register")
	public String reg(HttpSession session) {
		
		return "register";
		
	}

}
