package com.vixindia;


import com.vixindia.config.ToKenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class VixIndiaApplication {
  private boolean is_authorization_enabled = false;
  public static void main(String[] args) {
    SpringApplication.run(VixIndiaApplication.class, args);
  }
  @Bean
  public FilterRegistrationBean<ToKenFilter> tokenFilterRegistration() {
    FilterRegistrationBean<ToKenFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ToKenFilter());
    registrationBean.addUrlPatterns("/vixindia/*");
    return registrationBean;
  }
}
