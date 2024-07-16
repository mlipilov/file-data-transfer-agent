package com.andersen.filedatatransferagent.config.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The OpenApiConfig class is a configuration class for setting up OpenAPI documentation. It defines
 * beans for creating a custom OpenAPI object and a grouped OpenAPI for public APIs.
 */
@Configuration
public class OpenApiConfig {

  private static final String TITLE = "File-Data-Transfer-Agent API";
  private static final String VERSION = "1.0";
  private static final String DESCRIPTION = "API documentation for the "
      + "File-Data-Transfer-Agent microservice";

  /**
   * This method returns a custom OpenAPI object with specific information about the API.
   *
   * @return The custom OpenAPI object.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title(TITLE)
            .version(VERSION)
            .description(DESCRIPTION));
  }
}
