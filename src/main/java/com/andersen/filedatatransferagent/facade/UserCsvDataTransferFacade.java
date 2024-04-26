package com.andersen.filedatatransferagent.facade;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UserCsvDataTransferFacade {

  /**
   * Transfers user data from CSV files.
   *
   * @param csvData a list of CSV files containing user data
   */
  void transferUserCsvData(List<MultipartFile> csvData);
}
