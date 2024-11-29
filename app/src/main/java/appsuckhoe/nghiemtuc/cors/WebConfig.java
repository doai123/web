package appsuckhoe.nghiemtuc.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho phép tất cả các endpoint
                .allowedOrigins("http://localhost:3000","http://localhost:5173","http://172.16.209.233","https://ditcuchungmay.linkpc.net") // Địa chỉ front-end của bạn
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Phương thức cho phép
                .allowedHeaders("*") // Cho phép tất cả các header
                .allowCredentials(true); // Cho phép gửi cookie
    }
}

