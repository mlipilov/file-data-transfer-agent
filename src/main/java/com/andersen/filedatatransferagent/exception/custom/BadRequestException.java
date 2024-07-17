package com.andersen.filedatatransferagent.exception.custom;

public class BadRequestException extends RuntimeException {

  public BadRequestException(final String message) {
    super(message);
  }
}
