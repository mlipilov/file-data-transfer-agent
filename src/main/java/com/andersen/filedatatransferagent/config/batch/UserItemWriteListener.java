package com.andersen.filedatatransferagent.config.batch;

import com.andersen.filedatatransferagent.model.user.User;
import com.andersen.filedatatransferagent.service.UserCsvWriteErrorCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserItemWriteListener implements ItemWriteListener<User> {

  private final UserCsvWriteErrorCollector userCsvWriteErrorCollector;

  @Override
  public void onWriteError(
      final @NonNull Exception exception,
      final @NonNull Chunk<? extends User> items
  ) {
    log.error(exception.getMessage(), exception);
    userCsvWriteErrorCollector.collect(exception);
  }
}
