package cc.ayakurayuki.shortenurl.infrastructure.config;

import java.time.Duration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-11:11
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    CorsRegistration registration = registry.addMapping("/**");

    registration.allowedOriginPatterns(CorsConfiguration.ALL);

    registration.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD");

    registration.allowCredentials(true);

    registration.allowedHeaders(
        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
        HttpHeaders.ACCEPT,
        HttpHeaders.AUTHORIZATION,
        HttpHeaders.CONTENT_LENGTH,
        HttpHeaders.CONTENT_TYPE,
        HttpHeaders.ORIGIN,
        "X-Requested-With",
        "X-Real-Ip"
    );

    registration.exposedHeaders(
        HttpHeaders.CACHE_CONTROL,
        HttpHeaders.CONTENT_LANGUAGE,
        HttpHeaders.CONTENT_LENGTH,
        HttpHeaders.CONTENT_TYPE,
        HttpHeaders.EXPIRES,
        HttpHeaders.LAST_MODIFIED,
        HttpHeaders.PRAGMA
    );

    registration.maxAge(Duration.ofHours(2).toSeconds());
  }

}
