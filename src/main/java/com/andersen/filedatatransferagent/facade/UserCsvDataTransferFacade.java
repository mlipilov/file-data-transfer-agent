package com.andersen.filedatatransferagent.facade;

import org.springframework.web.multipart.MultipartFile;


/**
 * The UserCsvDataTransferFacade interface provides a way to transfer user data from CSV files.
 */
public interface UserCsvDataTransferFacade {

  /**
   * Transfers user data from CSV files.
   *
   * @param csvData CSV file containing user data
   */
  void transferUserCsvData(MultipartFile csvData);
}
