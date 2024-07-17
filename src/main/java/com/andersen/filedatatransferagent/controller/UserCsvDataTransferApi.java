package com.andersen.filedatatransferagent.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.andersen.filedatatransferagent.exception.model.ErrorRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
      @ApiResponse(
          responseCode = "200",
          description = "Successful operation"),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request",
          content = @Content(
              mediaType = APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorRs.class))),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(
              mediaType = APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorRs.class)))
  })
  ResponseEntity<Void> transferUserCsvData(final MultipartFile csvData);
}
