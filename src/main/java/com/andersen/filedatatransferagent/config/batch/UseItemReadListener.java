package com.andersen.filedatatransferagent.config.batch;

import com.andersen.filedatatransferagent.model.user.User;
import com.andersen.filedatatransferagent.service.UserCsvReadErrorCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseItemReadListener implements ItemReadListener<User> {

  private final UserCsvReadErrorCollector userCsvReadErrorCollector;

  /**
   * This method is called when an error occurs during reading of an item.
   *
   * @param ex the exception that occurred during the read operation
   */
  @Override
  public void onReadError(@NonNull final Exception ex) {
    log.error(ex.getMessage(), ex);
    userCsvReadErrorCollector.collect(ex);
  }
}
