package com.andersen.filedatatransferagent.service;

public interface UserCsvWriteErrorCollector {

  /**
   * Collects an exception for processing.
   *
   * @param exception the exception to be collected
   */
  void collect(Exception exception);
}
