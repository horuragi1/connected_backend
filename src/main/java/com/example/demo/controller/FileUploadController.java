package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Video;
import com.example.demo.service.CommentService;
import com.example.demo.service.VideoService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/videos/";
    private static final String THUMB_DIR = System.getProperty("user.dir") + "/src/main/resources/thumbnails/";
    
    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, 
                             @RequestParam("title") String title) {
        if (file.isEmpty() || !file.getContentType().equals("video/mp4")) {
            return "Invalid file. Please upload an MP4 file.";
        }

        System.out.println("Uploading file: " + title + "\n");

        try {
            // 업로드 폴더가 없으면 생성
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            Long num = videoService.findMaxId() + 1;
            
            System.out.println("num is " + num);

            // 새로운 파일 이름 생성 (UUID를 사용하여 고유한 파일 이름 생성)
            String newFileName = num.toString() + ".mp4";

            // 파일 저장
            String filePath = UPLOAD_DIR + newFileName;
            file.transferTo(new File(filePath));
            
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 0.5초 대기


            
            //db에 비디오 타이틀 저장
            videoService.registerVideo(title,num);
            
            
            // 동영상 전처리 ffmpeg
            
            String i = num.toString(); // 예시: 동영상 파일 이름 (i 값)

            // ffmpeg 명령어
            String command1 = String.format(
            	    "chmod 755 %s%s.mp4",
            	    UPLOAD_DIR, i);
            
            String command2 = String.format(
            	    "ffmpeg -i %s%s.mp4 -codec: copy -start_number 0 -hls_time 2 -hls_list_size 0 -hls_segment_filename %shls/%s_%%d.ts -f hls %shls/%s.m3u8",
            	    UPLOAD_DIR, i, UPLOAD_DIR, i, UPLOAD_DIR, i);
            
            String command3 = String.format(
                    "ffmpeg -i %s%s.mp4 -vframes 1 -q:v 2 %s%s.jpg",
                    UPLOAD_DIR, i, THUMB_DIR, i); // 첫 번째 프레임을 JPEG로 저장

         // 프로세스 실행
            try {
                // ProcessBuilder를 사용하여 명령어 실행
                ProcessBuilder processBuilder1 = new ProcessBuilder(command1.split(" "));
                processBuilder1.inheritIO();  // 콘솔 출력 및 에러를 부모 프로세스로 전달

                Process process1 = processBuilder1.start();

                // 프로세스가 종료될 때까지 대기
                int exitCode1 = process1.waitFor();
                if (exitCode1 == 0) {
                    System.out.println("ffmpeg command executed successfully.");
                    
                    try {
                    	
                    		ProcessBuilder processBuilder2 = new ProcessBuilder(command2.split(" "));
                        processBuilder2.inheritIO();  // 콘솔 출력 및 에러를 부모 프로세스로 전달

                        Process process2 = processBuilder2.start();

                        	// 프로세스가 종료될 때까지 대기
                        int exitCode2 = process2.waitFor();
                        
                        System.out.println("exit code 2 : " + exitCode2);
                        
                        	// 세 번째 명령어 실행 (첫 번째 프레임을 JPEG로 저장)
                        ProcessBuilder processBuilder3 = new ProcessBuilder(command3.split(" "));
                        processBuilder3.inheritIO();  // 콘솔 출력 및 에러를 부모 프로세스로 전달

                        Process process3 = processBuilder3.start();
                        int exitCode3 = process3.waitFor();
                        if (exitCode3 == 0) {
                            System.out.println("First frame captured as JPEG successfully.");
                        } else {
                            System.out.println("JPEG capture failed.");
                        }
                        
                    	
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                } else {
                    System.out.println("ffmpeg command execution failed.");
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            

            // 업로드된 파일 제목과 함께 응답
            return "File uploaded successfully: " + filePath + " with title: " + title;
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed!";
        }
    }
}
