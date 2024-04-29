package com.andersen.filedatatransferagent.controller;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/v1/csv")
@RequiredArgsConstructor
public class UserCsvDataTransferController {

  private final UserCsvDataTransferFacade userCsvDataTransferFacade;

  @PostMapping("/transfer")
  ResponseEntity<Void> transferUserCsvData(
      @RequestBody @Valid @Size(min = 2, max = 2) final List<MultipartFile> csvData
  ) {
    userCsvDataTransferFacade.transferUserCsvData(csvData);
    return ResponseEntity.ok().build();
  }
}
