package com.andersen.filedatatransferagent.config.batch;

import com.andersen.filedatatransferagent.model.user.User;
import java.sql.PreparedStatement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserItemWriteListener implements ItemWriteListener<User> {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void onWriteError(
      final @NonNull Exception exception,
      final @NonNull Chunk<? extends User> items
  ) {
    log.error(exception.getMessage(), exception);
    final PreparedStatementCreator creator = psc -> psc.prepareStatement("");
    final PreparedStatementCallback<Boolean> callback  = PreparedStatement::execute;
    jdbcTemplate.execute(creator, callback);
  }
}
