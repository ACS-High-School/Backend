package acs.b3o.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${cors.allowedOrigins}")
  private String allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(allowedOrigins)
        .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
        .allowCredentials(true) // 쿠키를 포함한 요청 허용
        .allowedHeaders("*"); // 허용할 헤더 지정
  }
}
