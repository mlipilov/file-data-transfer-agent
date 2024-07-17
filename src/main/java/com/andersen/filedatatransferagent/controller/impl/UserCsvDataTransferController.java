package com.andersen.filedatatransferagent.controller.impl;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.andersen.filedatatransferagent.controller.UserCsvDataTransferApi;
import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/csv")
@RequiredArgsConstructor
@Tag(name = "User Migration API")
public class UserCsvDataTransferController implements UserCsvDataTransferApi {

  private final UserCsvDataTransferFacade userCsvDataTransferFacade;

  @PostMapping(value = "/transfer", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> transferUserCsvData(
      @RequestPart(name = "users.csv") final MultipartFile csvData
  ) {
    userCsvDataTransferFacade.transferUserCsvData(csvData);
    return ResponseEntity.ok().build();
  }
}
