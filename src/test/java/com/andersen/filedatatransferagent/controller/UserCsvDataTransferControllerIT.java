package com.andersen.filedatatransferagent.controller;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.andersen.filedatatransferagent.ConfiguredIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;

class UserCsvDataTransferControllerIT extends ConfiguredIntegrationTest {

  public static final String FIND_JOB_EXEC_ID = "select job_execution_id "
      + "from batch_job_execution_params "
      + "where parameter_name = 'users.csv'";
  public static final String FIND_JOB_STATUS = "select status "
      + "from batch_job_execution "
      + "where job_execution_id = ?";
  private static final String TRIGGER_PATH = "api/v1/users/csv/transfer";
  public static final String COMPLETED = "COMPLETED";

  public static final long TIMEOUT = 5L;

  @Value("classpath:users.csv")
  private Resource usersCsvResource;

  @Test
  @SneakyThrows
  void givenCsvData_whenTriggerTransfer_ThenBatchTaskExecutedSuccessfully() {
    //GIVEN
    final String filename = requireNonNull(usersCsvResource.getFilename());
    final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(filename, new FileSystemResource(usersCsvResource.getFile()));

    //WHEN
    final RestClient restClient = getRc();
    final ResponseSpec rs = restClient.post()
        .uri(getLocalhostUrl(TRIGGER_PATH))
        .contentType(MULTIPART_FORM_DATA)
        .body(body)
        .retrieve();

    //THEN
    assertEquals(OK, rs.toEntity(Void.class).getStatusCode());
    await().atMost(TIMEOUT, SECONDS).until(this::jobIsCompleted);
  }

  private boolean jobIsCompleted() {
    final Long id = jdbcTemplate.query(FIND_JOB_EXEC_ID, extractJobExecId());
    final String status = jdbcTemplate.query(FIND_JOB_STATUS, getParamSetter(id), extractStatus());
    return COMPLETED.equals(status);
  }

  private PreparedStatementSetter getParamSetter(final Long id) {
    return pss -> pss.setLong(INTEGER_ONE, id);
  }

  private ResultSetExtractor<Long> extractJobExecId() {
    return jRs -> {
      jRs.next();
      return jRs.getLong(INTEGER_ONE);
    };
  }

  private ResultSetExtractor<String> extractStatus() {
    return jRs -> {
      jRs.next();
      return jRs.getString(INTEGER_ONE);
    };
  }
}