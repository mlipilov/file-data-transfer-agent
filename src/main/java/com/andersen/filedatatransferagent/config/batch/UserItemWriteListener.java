package com.andersen.filedatatransferagent.config.batch;

import com.andersen.filedatatransferagent.model.user.User;
import com.andersen.filedatatransferagent.service.UserCsvWriteErrorCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserItemWriteListener is an implementation of the ItemWriteListener interface
 * for handling write errors during the writing of User items.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserItemWriteListener implements ItemWriteListener<User> {

  private final UserCsvWriteErrorCollector userCsvWriteErrorCollector;

  @Override
  @Transactional(transactionManager = "transactionManager")
  public void onWriteError(
      final @NonNull Exception exception,
      final @NonNull Chunk<? extends User> items
  ) {
    log.error(exception.getMessage(), exception);
    userCsvWriteErrorCollector.collect(exception);
  }
}
