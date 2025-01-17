package com.andersen.filedatatransferagent.controller;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.andersen.filedatatransferagent.ConfiguredIntegrationTest;
import com.andersen.filedatatransferagent.model.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;

class UserCsvDataTransferControllerIT extends ConfiguredIntegrationTest {

  private static final String FIND_JOB_EXEC_ID = "select job_execution_id "
      + "from batch_job_execution_params "
      + "where parameter_name = 'users.csv'";

  private static final String FIND_JOB_STATUS = "select status "
      + "from batch_job_execution "
      + "where job_execution_id = ?";
  private static final String FIND_FAILED_RECORDS = "select ucte.id "
      + "from user_csv_transfer_errors ucte";

  private static final String TRIGGER_PATH = "api/v1/users/csv/transfer";
  private static final String FAILED = "FAILED";

  private static final long TIMEOUT = 10L;
  private static final String FILENAME = "users.csv";
  public static final String WRITE_ERROR_MSG = "error";

  @Value("classpath:users.csv")
  private Resource usersCsvResource;
  @Value("classpath:users-incorrect.csv")
  private Resource usersIncorrectCsvResource;

  @Test
  @SneakyThrows
  void givenCsvData_whenTriggerTransfer_AndThereIsReadError_ThenSaveErrorInTheDb() {
    //GIVEN
    final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(FILENAME, new FileSystemResource(usersIncorrectCsvResource.getFile()));

    //WHEN
    final RestClient restClient = getRc();
    final ResponseSpec rs = restClient.post()
        .uri(getLocalhostUrl(TRIGGER_PATH))
        .contentType(MULTIPART_FORM_DATA)
        .body(body)
        .retrieve();

    //THEN
    assertEquals(OK, rs.toEntity(Void.class).getStatusCode());
    await().atMost(TIMEOUT, TimeUnit.SECONDS).until(this::jobIsFailed);

    final List<Long> resultIds = new ArrayList<>();
    jdbcTemplate.query(FIND_FAILED_RECORDS, addFailedRecordIds(resultIds));
    assertEquals(INTEGER_ONE, resultIds.size());
  }

  @Test
  @Transactional(transactionManager = "kafkaTransactionManager")
  @SneakyThrows
  void givenCsvData_whenTriggerTransfer_AndThereIsWriteError_ThenSaveErrorInTheDb() {
    when(userKafkaTemplate.sendDefault(any(), any(User.class)))
        .thenThrow(new RuntimeException(WRITE_ERROR_MSG));

    //GIVEN
    final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(FILENAME, new FileSystemResource(usersCsvResource.getFile()));

    //WHEN
    final RestClient restClient = getRc();
    final ResponseSpec rs = restClient.post()
        .uri(getLocalhostUrl(TRIGGER_PATH))
        .contentType(MULTIPART_FORM_DATA)
        .body(body)
        .retrieve();

    //THEN
    assertEquals(OK, rs.toEntity(Void.class).getStatusCode());
    await().atMost(TIMEOUT, TimeUnit.SECONDS).until(this::jobIsFailed);

    final List<Long> resultIds = new ArrayList<>();
    jdbcTemplate.query(FIND_FAILED_RECORDS, addFailedRecordIds(resultIds));
    assertEquals(INTEGER_ONE, resultIds.size());
  }

  private boolean jobIsFailed() {
    final String status = getJobStatus();
    return FAILED.equals(status);
  }

  private String getJobStatus() {
    final Long id = jdbcTemplate.query(FIND_JOB_EXEC_ID, extractJobExecId());
    return jdbcTemplate.query(FIND_JOB_STATUS, getParamSetter(id), extractStatus());
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

  private RowCallbackHandler addFailedRecordIds(final List<Long> resultIds) {
    return fRs -> {
      do {
        resultIds.add(fRs.getLong(INTEGER_ONE));
      } while (fRs.next());
    };
  }
}