package com.andersen.filedatatransferagent.config.batch;

import static com.andersen.filedatatransferagent.model.OperationType.READ;

import com.andersen.filedatatransferagent.model.user.User;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseItemReadListener implements ItemReadListener<User> {

  public static final String SAVE_ERR_SQL = "insert into user_csv_transfer_errors('occurred_at', 'error_description', 'operation-type') values (?, ?, ?)";
  private final JdbcTemplate jdbcTemplate;

  @Override
  public void onReadError(@NonNull final Exception ex) {
    log.error(ex.getMessage(), ex);
    if (ex.getCause() instanceof FlatFileParseException flatFileEx) {
      final PreparedStatementCreator creator = psc -> {
        final PreparedStatement preparedStatement = psc.prepareStatement(SAVE_ERR_SQL);
        preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setString(2, ex.getMessage());
        preparedStatement.setString(3, READ.name());
        return preparedStatement;
      };
      final PreparedStatementCallback<Boolean> callback = PreparedStatement::execute;
      jdbcTemplate.execute(creator, callback);
    }
  }
}
