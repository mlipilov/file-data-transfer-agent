package com.andersen.filedatatransferagent.facade.impl;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCsvDataTransferFacadeImpl implements UserCsvDataTransferFacade {

  @Async
  @Override
  public void transferUserCsvData(final List<MultipartFile> csvData) {

  }
}
