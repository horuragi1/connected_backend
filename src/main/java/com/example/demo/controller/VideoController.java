package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.VideoService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 모든 도메인에서의 요청 허용
@RequiredArgsConstructor
public class VideoController {
	
	
	//private final String VIDEO_DIR = "/home/gogi/Desktop/connected/demo/src/main/resources/videos"; // 동영상이 저장된 디렉토리

    // 비디오 파일이 저장된 폴더 경로
    
    String currentPath = System.getProperty("user.dir");
    
    
    //private static final String VIDEO_DIRECTORY = "/home/gogi/Desktop/connected/demo/src/main/resources/videos/";
    private String VIDEO_DIRECTORY = currentPath + "/src/main/resources/videos/";

    /*@GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(
            @PathVariable String filename,
            @RequestHeader(value = "Range", required = false) String rangeHeader,
            HttpServletResponse response) throws IOException {

    	System.out.println("현재 경로: " + currentPath);
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
    }*/
    
    private String THUMBNAIL_DIRECTORY = currentPath + "/src/main/resources/thumbnails/";
    
 // 썸네일 생성 및 반환
    @GetMapping("/thumbnail/{filename}")
    public ResponseEntity<Resource> getVideoThumbnail(@PathVariable String filename) {
    	
    	System.out.println("썸네일!!! " + filename);
    	
        try {
            // 원본 비디오 파일 경로
            String videoFilePath = VIDEO_DIRECTORY + filename;

            // 썸네일 저장 경로
            String thumbnailFilePath = THUMBNAIL_DIRECTORY + filename.replace(".mp4", ".jpg");

            // 디렉토리가 없으면 생성
            File thumbnailDir = new File(THUMBNAIL_DIRECTORY);
            if (!thumbnailDir.exists()) {
                thumbnailDir.mkdirs();
            }

            // 썸네일 파일이 이미 존재하는 경우 반환
            File thumbnailFile = new File(thumbnailFilePath);
            if (!thumbnailFile.exists()) {
                // FFmpeg 명령어 실행
                String command = String.format(
                        "ffmpeg -i %s -ss 00:00:01 -vframes 1 -q:v 2 %s",
                        videoFilePath,
                        thumbnailFilePath
                );

                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            }

            // 썸네일 파일 리소스 반환
            Resource thumbnailResource = new FileSystemResource(thumbnailFilePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(thumbnailResource);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private final VideoService videoService;
    
    //비디오 제목 반환 api
    @GetMapping("/video_title/{filename}")
    public ResponseEntity<String> getVideoTitle(@PathVariable String filename) {
        try {
            // filename을 Long 타입 id로 변환
            Long id = Long.parseLong(filename);
            System.out.println("filename is " + filename + ", id is " + id);

            // 비디오 제목 조회
            String rtr = videoService.findTitleById(id);
            System.out.println("rtr is " + rtr);

            // 비디오 제목이 존재하면 200 OK와 제목 반환
            if (rtr != null) {
                return ResponseEntity.ok(rtr);
            } else {
                // 비디오 제목이 없으면 404 Not Found 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Video title not found for id: " + id);
            }
        } catch (NumberFormatException e) {
            // filename이 Long으로 변환 불가능한 경우 400 Bad Request 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid ID format: " + filename);
        } catch (Exception e) {
            // 기타 예외 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    @GetMapping("/num_of_videos")
    public ResponseEntity<Long> getVideoNums() {
    	
    	Long rtr = videoService.findMaxId();
    	
    	return ResponseEntity.ok(rtr);
    	
    }

    @GetMapping("/description/{videoId}")
    public ResponseEntity<String> getDescription(@PathVariable Long videoId){
        String description = videoService.findDescriptionById(videoId);

        return ResponseEntity.ok(description);
    }
    
    @GetMapping("/hls/{filename}")
    public ResponseEntity<Resource> getHlsFile(@PathVariable String filename) {
        String currentPath = System.getProperty("user.dir");
        String hlsDirectory = currentPath + "/src/main/resources/videos/hls/";
        File hlsFile = new File(hlsDirectory + filename);
        
        System.out.println("path is " + hlsFile);

        // 파일이 존재하지 않으면 404 반환
        if (!hlsFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 파일 리소스를 생성하여 반환
        Resource resource = new FileSystemResource(hlsFile);
        HttpHeaders headers = new HttpHeaders();
        if (filename.endsWith(".m3u8")) {
            headers.setContentType(MediaType.valueOf("application/vnd.apple.mpegurl"));
        } else if (filename.endsWith(".ts")) {
            headers.setContentType(MediaType.valueOf("video/mp2t"));
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }
    
    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(
            @PathVariable String filename,
            @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
    	
    	System.out.println("pd : " + filename);
    	
    	String currentPath = System.getProperty("user.dir");
        String hlsDirectory = currentPath + "/src/main/resources/videos/";
    	
        File videoFile = new File(hlsDirectory + filename);
        long fileLength = videoFile.length();

        // Range 처리
        if (rangeHeader != null) {
            String[] ranges = rangeHeader.replace("bytes=", "").split("-");
            long start = Long.parseLong(ranges[0]);
            long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileLength - 1;
            long contentLength = end - start + 1;

            FileInputStream inputStream = new FileInputStream(videoFile);
            inputStream.skip(start);
            byte[] buffer = new byte[(int) contentLength];
            inputStream.read(buffer, 0, buffer.length);
            inputStream.close();

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength)
                    .body(new ByteArrayResource(buffer));
        }

        // 전체 파일 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(new FileSystemResource(videoFile));
    }
    

}
