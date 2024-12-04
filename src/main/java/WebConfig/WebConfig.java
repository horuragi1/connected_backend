package WebConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Spring 서버 전역적으로 CORS 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {
 @Override
 public void addCorsMappings(CorsRegistry registry) {
     registry.addMapping("/**")
             .allowedOrigins("http:172.30.1.37:8080","http:localhost:8080","http:172.30.1.37:3000","http:localhost:3000","http:221.151.162.142:8080","221.151.162.142:3000","http:10.1.9.89:8080")
             .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP method
             .allowCredentials(true); // 쿠키 인증 요청 허용
 }
}
