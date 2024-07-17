package com.andersen.filedatatransferagent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserCsvDataTransferApi {

  @Operation(
      summary = "Transfers user csv data to the user-service for further migration",
      description = "Used to perform user data migration",
      operationId = "transferUserCsvData")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<Void> transferUserCsvData(final MultipartFile csvData);
}
