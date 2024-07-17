package com.andersen.filedatatransferagent.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.andersen.filedatatransferagent.exception.custom.BadRequestException;
import com.andersen.filedatatransferagent.exception.model.ErrorRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

  private static final String UNEXPECTED_ERROR = "Unexpected error";

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorRs> handleGeneralException(final Exception ex) {
    log.error("Unhandled exception", ex);
    final ErrorRs errorRs = new ErrorRs();
    errorRs.setReason(UNEXPECTED_ERROR);

    return new ResponseEntity<>(errorRs, INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<ErrorRs> handleBadRequestException(final BadRequestException ex) {
    log.error("Bad request", ex);
    final ErrorRs errorRs = new ErrorRs();
    errorRs.setReason(ex.getMessage());

    return new ResponseEntity<>(errorRs, BAD_REQUEST);
  }
}
