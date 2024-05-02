package com.andersen.filedatatransferagent.controller;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

import com.andersen.filedatatransferagent.ConfiguredIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;

class UserCsvDataTransferControllerIT extends ConfiguredIntegrationTest {

  private static final String TRIGGER_PATH = "api/v1/users/csv/transfer";

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
  }
}