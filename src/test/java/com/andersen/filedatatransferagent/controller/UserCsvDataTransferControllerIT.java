package com.andersen.filedatatransferagent.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.andersen.filedatatransferagent.ConfiguredIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;

class UserCsvDataTransferControllerIT extends ConfiguredIntegrationTest {

  private static final String TRIGGER_PATH = "/api/v1/users/csv/trigger";

  @Test
  void givenCsvData_whenTriggerTransfer_ThenBatchTaskExecutedSuccessfully() {
    final RestClient restClient = getRc();
    final ResponseSpec rs = restClient.post()
        .uri(getLocalhostUrl(TRIGGER_PATH))
        .contentType(MULTIPART_FORM_DATA)
        .body(null)
        .retrieve();
  }
}