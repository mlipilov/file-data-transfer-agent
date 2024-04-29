package com.andersen.filedatatransferagent.service;

public interface UserCsvReadErrorCollector {

  /**
   * Collects an exception for error handling and logging.
   *
   * @param ex the exception to collect
   */
  void collect(Exception ex);
}
