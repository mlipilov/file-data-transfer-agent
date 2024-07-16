package com.andersen.filedatatransferagent.service;


/**
 * UserCsvWriteErrorCollector is an interface for collecting write errors that occur
 * during CSV file writing for user entities.
 */
public interface UserCsvWriteErrorCollector {

  /**
   * Collects an exception for processing.
   *
   * @param exception the exception to be collected
   */
  void collect(Exception exception);
}
