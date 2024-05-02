package com.andersen.filedatatransferagent;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.text.MessageFormat;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class BaseIntegrationTest {

  private static final String LOCALHOST_URL = "http://localhost:{0}/{1}";

  @LocalServerPort
  protected int port;

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  protected String getLocalhostUrl(@NonNull final String uri) {
    return MessageFormat.format(LOCALHOST_URL, String.valueOf(port), uri);
  }

  protected RestClient getRc() {
    return RestClient.builder().build();
  }
}
