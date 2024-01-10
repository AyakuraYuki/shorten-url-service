package cc.ayakurayuki.shortenurl.infrastructure.config;

import java.nio.charset.StandardCharsets;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.FormContentFilter;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-11:33
 */
@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterFilterRegistrationBean() {
    CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
    filter.setEncoding(StandardCharsets.UTF_8.name());
    filter.setForceRequestEncoding(true);
    filter.setForceResponseEncoding(true);

    FilterRegistrationBean<CharacterEncodingFilter> bean = new FilterRegistrationBean<>();
    bean.setBeanName("characterEncodingFilter");
    bean.setFilter(filter);
    bean.setOrder(1);
    return bean;
  }

  @Bean
  public FilterRegistrationBean<FormContentFilter> formContentFilterFilterRegistrationBean() {
    FormContentFilter filter = new FormContentFilter();

    FilterRegistrationBean<FormContentFilter> bean = new FilterRegistrationBean<>();
    bean.setBeanName("formContentFilter");
    bean.setFilter(filter);
    bean.setOrder(2);
    return bean;
  }

}
