package com.andersen.filedatatransferagent.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class UserCsvDataTransferController {

  private final UserCsvDataTransferFacade userCsvDataTransferFacade;

  @Operation(
      summary = "Transfers user csv data to the user-service for further migration",
      description = "Used to perform user data migration",
      operationId = "transferUserCsvData")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping(value = "/transfer", consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> transferUserCsvData(
      @RequestPart(name = "users.csv") final MultipartFile csvData
  ) {
    userCsvDataTransferFacade.transferUserCsvData(csvData);
    return ResponseEntity.ok().build();
  }
}
