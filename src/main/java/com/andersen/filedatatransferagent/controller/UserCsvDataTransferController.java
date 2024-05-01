package com.andersen.filedatatransferagent.controller;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/csv")
@RequiredArgsConstructor
public class UserCsvDataTransferController {

  private final UserCsvDataTransferFacade userCsvDataTransferFacade;

  @PostMapping("/transfer")
  ResponseEntity<Void> transferUserCsvData(@RequestBody final MultipartFile csvData) {
//    userCsvDataTransferFacade.transferUserCsvData(csvData);
    return ResponseEntity.ok().build();
  }
}
