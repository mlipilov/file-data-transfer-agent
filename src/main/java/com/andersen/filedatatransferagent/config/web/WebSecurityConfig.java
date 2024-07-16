package com.andersen.filedatatransferagent.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web security.
 *
 * This class extends WebMvcConfigurer to configure CORS (Cross-Origin Resource Sharing) for the application.
 * It allows defining the allowed headers and methods for incoming requests.
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

  private static final String PATH_PATTERN = "/**";
  private static final String ALLOWED_HEADERS = "*";
  private static final String ALLOWED_METHODS = "*";

  /**
   * Adds Cross-Origin Resource Sharing (CORS) mappings to the CorsRegistry.
   *
   * This method is used to configure CORS for the application. It allows specifying the allowed headers and methods for
   * incoming requests.
   *
   * @param registry the CorsRegistry to which the CORS mappings will be added
   */
  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry
        .addMapping(PATH_PATTERN)
        .allowedHeaders(ALLOWED_HEADERS)
        .allowedMethods(ALLOWED_METHODS);
  }
}
