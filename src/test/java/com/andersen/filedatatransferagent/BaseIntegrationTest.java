package com.andersen.filedatatransferagent;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;

import com.andersen.filedatatransferagent.config.KafkaTestConfig;
import com.andersen.filedatatransferagent.model.user.User;
import java.text.MessageFormat;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.web.client.RestClient;

@ActiveProfiles(profiles = "integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(KafkaTestConfig.class)
@Sql(
    scripts = "classpath:truncate.sql",
    executionPhase = AFTER_TEST_METHOD,
    config = @SqlConfig(transactionMode = ISOLATED))
@SqlMergeMode(MERGE)
public abstract class BaseIntegrationTest {

  private static final String LOCALHOST_URL = "http://localhost:{0}/{1}";

  @LocalServerPort
  protected int port;

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  @SpyBean
  protected KafkaTemplate<String, User> userKafkaTemplate;

  protected String getLocalhostUrl(@NonNull final String uri) {
    return MessageFormat.format(LOCALHOST_URL, String.valueOf(port), uri);
  }

  protected RestClient getRc() {
    return RestClient.builder().build();
  }
}
