package com.andersen.filedatatransferagent.facade;

import org.springframework.web.multipart.MultipartFile;

public interface UserCsvDataTransferFacade {

  /**
   * Transfers user data from CSV files.
   *
   * @param csvData CSV file containing user data
   */
  void transferUserCsvData(MultipartFile csvData);
}
