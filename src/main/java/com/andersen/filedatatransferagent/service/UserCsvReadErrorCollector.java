package com.andersen.filedatatransferagent.service;


/**
 * This interface defines the contract for collecting exceptions during CSV file reading.
 * Implementations of this interface can be used to handle and log exceptions that occur during CSV file parsing.
 */
public interface UserCsvReadErrorCollector {

  /**
   * Collects an exception for error handling and logging.
   *
   * @param ex the exception to collect
   */
  void collect(Exception ex);
}
