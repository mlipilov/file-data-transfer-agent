package com.andersen.filedatatransferagent;

import java.text.MessageFormat;
import lombok.NonNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

@SpringBootTest
public abstract class BaseIntegrationTest {

  private static final String LOCALHOST_URL = "http://localhost:{0}/{1}";

  @LocalServerPort
  protected int port;

  protected String getLocalhostUrl(@NonNull final String uri) {
    return MessageFormat.format(LOCALHOST_URL, String.valueOf(port), uri);
  }

  protected RestClient getRc() {
    return RestClient.builder().build();
  }
}
