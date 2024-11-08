package WebConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 특정 URL 패턴에 대해 CORS를 허용
        registry.addMapping("/api/**") // /api/로 시작하는 모든 요청에 대해
                .allowedOrigins("http://10.1.182.104:8081") // 허용할 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
                .allowedHeaders("*"); // 모든 헤더 허용
    }
}
