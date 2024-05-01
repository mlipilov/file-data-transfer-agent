package com.andersen.filedatatransferagent.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

  private static final String PATH_PATTERN = "/**";
  private static final String ALLOWED_HEADERS = "*";
  private static final String ALLOWED_METHODS = "*";

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry
        .addMapping(PATH_PATTERN)
        .allowedHeaders(ALLOWED_HEADERS)
        .allowedMethods(ALLOWED_METHODS);
  }
}
